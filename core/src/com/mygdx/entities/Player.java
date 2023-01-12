package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.GameScreen;
import com.mygdx.helper.*;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.helper.Constant.PPM;

public class Player extends GameEntity implements Destroyable {
    private TextureRegion currentFrame;
    private List<Animation<TextureRegion>> animations;
    private GameScreen screen;
    private float anglePlayerToMouse = 0;
    private int score = 0, coins = 0, currenDirection = 0;
    private boolean keyDown;
    private final Timer timer = new Timer();
    private final Task task = new Task() {
        @Override
        public void run() {
            keyDown = false;
        }
    };
    private boolean godMode;
    private Vector2 projectileDirection;


    public Player(float x, float y, float width, float height, TextureAtlas atlas, GameScreen screen){
        super(x, y, width, height);
        this.body = BodyHelper.createBody(x, y, width, height, false, false, screen.getWorld(), this, EntityType.PLAYER);
        this.body.setUserData(this);
        this.speed = 3f*PPM;
        this.damage = 1;
        this.godMode = false;
        this.health = 100;
        this.keyDown = false;
        this.screen = screen;
        killed = false;
        projectileDirection = new Vector2();
        animations = new ArrayList<>();

        animations.add(AnimationHelper.animateRegion(atlas.findRegion("1_north"), 4, 0.5f));
        animations.add(AnimationHelper.animateRegion(atlas.findRegion("1_diagup"), 4, 0.5f));
        animations.add(AnimationHelper.animateRegion(atlas.findRegion("1_side"), 4, 0.5f));
        animations.add(AnimationHelper.animateRegion(atlas.findRegion("1_diagdown"), 4, 0.5f));
        animations.add(AnimationHelper.animateRegion(atlas.findRegion("1_south"), 4, 0.5f));
        animations.add(AnimationHelper.flippedAnimation(animations.get(3), 4, 0.5f));
        animations.add(AnimationHelper.flippedAnimation(animations.get(2), 4, 0.5f));
        animations.add(AnimationHelper.flippedAnimation(animations.get(1), 4, 0.5f));
    }

    @Override
    public void update() {
        x = body.getPosition().x;
        y = body.getPosition().y;
        checkUserInput();
        anglePlayerToMouse = (float) Math.atan2(screen.getUnprojectedMousePos().y - this.y, screen.getUnprojectedMousePos().x - this.x);
    }

    @Override
    public void render(SpriteBatch batch) {
        getCurrentFrame();
        batch.draw(currentFrame, body.getPosition().x - 8, body.getPosition().y - 8);
    }
    private void getCurrentFrame(){
        currenDirection = getDirection();
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animations.get(currenDirection).getKeyFrame(stateTime);
        if(animations.get(currenDirection).isAnimationFinished(stateTime)){
            stateTime = 0;
        }
    }
    private int getDirection(){
        if(anglePlayerToMouse > 2.8 || anglePlayerToMouse <= -2.8){
            projectileDirection.set(this.x - 16, this.y);
            return Direction.SIDE_LEFT;
        }
        else if(anglePlayerToMouse > 2){
            projectileDirection.set(this.x - 16, this.y + 16);
            return Direction.DIAGONAL_UP_LEFT;
        }
        else if(anglePlayerToMouse > 1.17){
            projectileDirection.set(this.x, this.y + 16);
            return Direction.UP;
        }
        else if(anglePlayerToMouse > 0.5){
            projectileDirection.set(this.x + 16, this.y + 16);
            return Direction.DIAGONAL_UP;
        }
        else if(anglePlayerToMouse > -0.34){
            projectileDirection.set(this.x + 16, this.y);
            return Direction.SIDE;
        }
        else if(anglePlayerToMouse > -1.2){
            projectileDirection.set(this.x + 16, this.y - 16);
            return Direction.DIAGONAL_DOWN;
        }
        else if(anglePlayerToMouse > -1.8){
            projectileDirection.set(this.x, this.y - 16);
            return Direction.DOWN;
        }
        else{
            projectileDirection.set(this.x - 16, this.y - 16);
            return Direction.DIAGONAL_DOWN_LEFT;
        }
    }
    private void checkUserInput() {
        velX = 0;
        velY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            velY = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY = -1;
        }
        body.setLinearVelocity(velX*speed, velY*speed);

        if(Gdx.input.isTouched() && !keyDown){
            screen.getEntityManager().createProjectile(projectileDirection.x, projectileDirection.y, damage);
            keyDown = true;
            timer.scheduleTask(task, 0.1f);
        }
    }

    public boolean isGodMode() {
        return godMode;
    }

    @Override
    public void reduceHealth(int amount) {
        health -= amount;
        System.out.println("Player health: " + health);
    }

    @Override
    public void kill() {

    }
    public int getScore() {
        return score;
    }

    public int getCoins() {
        return coins;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }
}


