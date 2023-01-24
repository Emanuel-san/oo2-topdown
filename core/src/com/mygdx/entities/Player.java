package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameScreen;
import com.mygdx.helper.*;
import com.mygdx.helper.processors.PlayerInputProcessor;

import java.util.HashMap;

public class Player extends GameEntity implements Killable {
    private TextureRegion currentFrame;
    private final HashMap<Direction, Animation<TextureRegion>> animationMap;
    private final EntityManager entityManager;
    private int score = 0, coins = 0;
    private boolean godMode;
    private boolean recentlyShot;
    private final Timer timer = new Timer();
    private final Timer.Task task = new Timer.Task() {
        @Override
        public void run() {
            recentlyShot = false;
        }
    };
    private final Vector2 projectileSpawnDirection;
    private Direction currentDirection;

    private PlayerInputProcessor inputProcessor;


    public Player(float x, float y, float width, float height,GameScreen screen, EntityManager entityManager){
        super(x, y, width, height);
        body = BodyHelper.createPolygonBody(x, y, width, height, false, screen.getWorld(), this, FilterType.PLAYER);
        body.setUserData(this);
        speed = 25f / Constant.PPM;
        damage = 1;
        godMode = false;
        health = 100;
        this.entityManager = entityManager;
        recentlyShot = false;
        killed = false;
        projectileSpawnDirection = new Vector2();
        animationMap = new HashMap<>();
        TextureAtlas atlas = screen.getAssetManager().get("topdown_shooter/char1.atlas", TextureAtlas.class);
        constructAnimationsMap(atlas);
    }

    @Override
    public void update() {
        x = body.getPosition().x;
        y = body.getPosition().y;
        body.setLinearVelocity(velX*speed, velY*speed);
        setDirection();
        shootProjectile();
    }

    @Override
    public void render(SpriteBatch batch) {
        setCurrentFrame();
        batch.draw(currentFrame,
                body.getPosition().x - 10 / Constant.PPM,
                body.getPosition().y - 9 / Constant.PPM,
                0, 0,
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                1 / Constant.PPM,
                1 / Constant.PPM, 0
                );
    }
    private void setCurrentFrame(){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animationMap.get(currentDirection).getKeyFrame(stateTime);
        if(animationMap.get(currentDirection).isAnimationFinished(stateTime)){
            stateTime = 0;
        }
    }
    private void shootProjectile(){
        if(inputProcessor.isLeftMouseDown() && !recentlyShot){
            entityManager.createProjectile(
                    projectileSpawnDirection,
                    6 / Constant.PPM,
                    6 / Constant.PPM,
                    damage,
                    new Vector2(inputProcessor.getUnprojectedMousePos().x, inputProcessor.getUnprojectedMousePos().y));
            recentlyShot = true;
            timer.scheduleTask(task, 0.15f);
        }
    }
    private void setDirection(){
        double anglePlayerToMouse = Math.atan2(inputProcessor.getUnprojectedMousePos().y - this.y, inputProcessor.getUnprojectedMousePos().x - this.x);
        anglePlayerToMouse = Math.toDegrees(anglePlayerToMouse);
        if(anglePlayerToMouse > 158 || anglePlayerToMouse <= -158){
            currentDirection = Direction.SIDE_LEFT;
            projectileSpawnDirection.set(this.x - 16 / Constant.PPM, this.y);
        }
        else if(anglePlayerToMouse > 113){
            currentDirection = Direction.DIAGONAL_UP_LEFT;
            projectileSpawnDirection.set(this.x - 16 / Constant.PPM, this.y + 16  / Constant.PPM);
        }
        else if(anglePlayerToMouse > 68){
            currentDirection = Direction.UP;
            projectileSpawnDirection.set(this.x, this.y + 16 / Constant.PPM);
        }
        else if(anglePlayerToMouse > 23){
            currentDirection = Direction.DIAGONAL_UP_RIGHT;
            projectileSpawnDirection.set(this.x + 16 / Constant.PPM, this.y + 16 / Constant.PPM);
        }
        else if(anglePlayerToMouse > -23){
            currentDirection = Direction.SIDE_RIGHT;
            projectileSpawnDirection.set(this.x + 16 / Constant.PPM, this.y);
        }
        else if(anglePlayerToMouse > -68){
            currentDirection = Direction.DIAGONAL_DOWN_RIGHT;
            projectileSpawnDirection.set(this.x + 16 / Constant.PPM, this.y - 16 / Constant.PPM);
        }
        else if(anglePlayerToMouse > -113){
            currentDirection = Direction.DOWN;
            projectileSpawnDirection.set(this.x, this.y - 16 / Constant.PPM);
        }
        else{
            currentDirection = Direction.DIAGONAL_DOWN_LEFT;
            projectileSpawnDirection.set(this.x - 16 / Constant.PPM, this.y - 16 / Constant.PPM);
        }
    }

    private void constructAnimationsMap(TextureAtlas atlas){
        animationMap.put(Direction.UP, AnimationHelper.animateRegion(atlas.findRegion("1_north"), 4, 0.5f));
        animationMap.put(Direction.DIAGONAL_UP_RIGHT, AnimationHelper.animateRegion(atlas.findRegion("1_diagup"), 4, 0.5f));
        animationMap.put(Direction.SIDE_RIGHT, AnimationHelper.animateRegion(atlas.findRegion("1_side"), 4, 0.5f));
        animationMap.put(Direction.DIAGONAL_DOWN_RIGHT, AnimationHelper.animateRegion(atlas.findRegion("1_diagdown"), 4, 0.5f));
        animationMap.put(Direction.DOWN, AnimationHelper.animateRegion(atlas.findRegion("1_south"), 4, 0.5f));
        animationMap.put(Direction.DIAGONAL_DOWN_LEFT, AnimationHelper.flippedAnimation(animationMap.get(Direction.DIAGONAL_DOWN_RIGHT), 4, 0.5f));
        animationMap.put(Direction.DIAGONAL_UP_LEFT, AnimationHelper.flippedAnimation(animationMap.get(Direction.DIAGONAL_UP_RIGHT), 4, 0.5f));
        animationMap.put(Direction.SIDE_LEFT, AnimationHelper.flippedAnimation(animationMap.get(Direction.SIDE_RIGHT), 4, 0.5f));
    }

    public boolean isGodMode() {
        return godMode;
    }

    @Override
    public void reduceHealth(int amount) {
        health -= amount;
    }

    @Override
    public void kill() {

    }
    public void setPlayerVelocityX(int velocity){
        velX = velX + velocity * speed;
    }
    public void setPlayerVelocityY(int velocity){
        velY = velY + velocity * speed;
    }

    public void setInputProcessor(PlayerInputProcessor processor) {
        this.inputProcessor = processor;
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


