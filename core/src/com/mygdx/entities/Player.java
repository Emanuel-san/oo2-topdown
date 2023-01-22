package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    private final PlayerInputProcessor inputProcessor;
    private OrthographicCamera camera;
    private Vector3 mousePos;


    public Player(float x, float y, float width, float height,GameScreen screen, EntityManager entityManager){
        super(x, y, width, height);
        this.body = BodyHelper.createPolygonBody(x, y, width, height, false, screen.getWorld(), this, EntityType.PLAYER);
        this.body.setUserData(this);
        this.speed = 25f;
        this.damage = 1;
        this.godMode = false;
        this.health = 100;
        this.entityManager = entityManager;
        recentlyShot = false;
        killed = false;
        projectileSpawnDirection = new Vector2();
        mousePos = new Vector3();
        animationMap = new HashMap<>();
        inputProcessor = new PlayerInputProcessor(screen,this);
        screen.getInputMultiplexer().addProcessor(inputProcessor);
        TextureAtlas atlas = screen.getAssetManager().get("topdown_shooter/char1.atlas", TextureAtlas.class);

        animationMap.put(Direction.UP, AnimationHelper.animateRegion(atlas.findRegion("1_north"), 4, 0.5f));
        animationMap.put(Direction.DIAGONAL_UP_RIGHT, AnimationHelper.animateRegion(atlas.findRegion("1_diagup"), 4, 0.5f));
        animationMap.put(Direction.SIDE_RIGHT, AnimationHelper.animateRegion(atlas.findRegion("1_side"), 4, 0.5f));
        animationMap.put(Direction.DIAGONAL_DOWN_RIGHT, AnimationHelper.animateRegion(atlas.findRegion("1_diagdown"), 4, 0.5f));
        animationMap.put(Direction.DOWN, AnimationHelper.animateRegion(atlas.findRegion("1_south"), 4, 0.5f));
        animationMap.put(Direction.DIAGONAL_DOWN_LEFT, AnimationHelper.flippedAnimation(animationMap.get(Direction.DIAGONAL_DOWN_RIGHT), 4, 0.5f));
        animationMap.put(Direction.DIAGONAL_UP_LEFT, AnimationHelper.flippedAnimation(animationMap.get(Direction.DIAGONAL_UP_RIGHT), 4, 0.5f));
        animationMap.put(Direction.SIDE_LEFT, AnimationHelper.flippedAnimation(animationMap.get(Direction.SIDE_RIGHT), 4, 0.5f));
    }

    @Override
    public void update() {
        x = body.getPosition().x;
        y = body.getPosition().y;
        body.setLinearVelocity(velX*speed, velY*speed);
        getCurrentUnprojectedMousePosition();
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
    private void getCurrentUnprojectedMousePosition(){
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        mousePos = camera.unproject(mousePos);
    }
    private void setCurrentFrame(){
        Direction currentDirection = getDirection();
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animationMap.get(currentDirection).getKeyFrame(stateTime);
        if(animationMap.get(currentDirection).isAnimationFinished(stateTime)){
            stateTime = 0;
        }
    }
    private void shootProjectile(){
        if(inputProcessor.isLeftMouseDown() && !recentlyShot){
            entityManager.createProjectile(
                    projectileSpawnDirection.x,
                    projectileSpawnDirection.y,
                    6 / Constant.PPM,
                    6 / Constant.PPM,
                    damage,
                    new Vector2(mousePos.x, mousePos.y));
            recentlyShot = true;
            timer.scheduleTask(task, 0.15f);
        }
    }
    private Direction getDirection(){
        float anglePlayerToMouse = (float) Math.atan2(mousePos.y - this.y, mousePos.x - this.x);
        if(anglePlayerToMouse > 2.8 || anglePlayerToMouse <= -2.8){
            projectileSpawnDirection.set(this.x - 16 / Constant.PPM, this.y);
            return Direction.SIDE_LEFT;
        }
        else if(anglePlayerToMouse > 2){
            projectileSpawnDirection.set(this.x - 16 / Constant.PPM, this.y + 16  / Constant.PPM);
            return Direction.DIAGONAL_UP_LEFT;
        }
        else if(anglePlayerToMouse > 1.17){
            projectileSpawnDirection.set(this.x, this.y + 16 / Constant.PPM);
            return Direction.UP;
        }
        else if(anglePlayerToMouse > 0.5){
            projectileSpawnDirection.set(this.x + 16 / Constant.PPM, this.y + 16 / Constant.PPM);
            return Direction.DIAGONAL_UP_RIGHT;
        }
        else if(anglePlayerToMouse > -0.34){
            projectileSpawnDirection.set(this.x + 16 / Constant.PPM, this.y);
            return Direction.SIDE_RIGHT;
        }
        else if(anglePlayerToMouse > -1.2){
            projectileSpawnDirection.set(this.x + 16 / Constant.PPM, this.y - 16 / Constant.PPM);
            return Direction.DIAGONAL_DOWN_RIGHT;
        }
        else if(anglePlayerToMouse > -1.8){
            projectileSpawnDirection.set(this.x, this.y - 16 / Constant.PPM);
            return Direction.DOWN;
        }
        else{
            projectileSpawnDirection.set(this.x - 16 / Constant.PPM, this.y - 16 / Constant.PPM);
            return Direction.DIAGONAL_DOWN_LEFT;
        }
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

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
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


