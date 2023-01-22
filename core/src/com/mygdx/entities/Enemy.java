package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.AI.EnemyAI;
import com.mygdx.helper.*;

import java.util.HashMap;

public class Enemy extends GameEntity implements Killable {
    private final HashMap<Direction, Animation<TextureRegion>> animations;
    private final EnemyAI ai;
    private int scoreValue;
    private TextureRegion currentFrame;
    private Direction currentDirectionToTarget = Direction.DOWN;
    private Vector2 velocityVector;

    public Enemy(float x, float y, float width, float height, World world, HashMap<Direction, Animation<TextureRegion>> animations, int damage, EntityManager entityManager) {
        super(x,y,width, height);
        this.body = BodyHelper.createPolygonBody(x, y, width, height, false, world, this, EntityType.ENEMY);
        this.speed = 10f;
        this.health = 5;
        this.damage = damage;
        this.animations = animations;
        this.ai = new EnemyAI(entityManager.getPlayer(), entityManager.getPlayerBase(), this);
        velocityVector = new Vector2();
        scoreValue = 50;
        stateTime = 0;
    }

    @Override
    public void update() {
        ai.update();
        currentDirectionToTarget = getDirectionToTarget();
        velX = ai.getDirectionVector().x ;
        velY = ai.getDirectionVector().y ;
        velocityVector.set(velX, velY);
        body.setLinearVelocity(velX * speed, velY * speed);
    }

    @Override
    public void render(SpriteBatch batch) {
        setCurrentFrame();
        batch.draw(currentFrame,
                body.getPosition().x - 8 / Constant.PPM,
                body.getPosition().y - 8 / Constant.PPM,
                0, 0,
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                1 / Constant.PPM,
                1 / Constant.PPM, 0
        );
    }

    @Override
    public void reduceHealth(int amount) {
        health -= amount;
        if(health <= 0){
            this.kill();
        }
    }

    @Override
    public void kill() {
        killed = true;
    }
    private void setCurrentFrame(){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animations.get(currentDirectionToTarget).getKeyFrame(stateTime);
        if(animations.get(currentDirectionToTarget).isAnimationFinished(stateTime)){
            stateTime = 0;
        }
    }
    private Direction getDirectionToTarget(){
        float angleEnemyToTarget = velocityVector.angleDeg();
        if(angleEnemyToTarget > 315 || angleEnemyToTarget < 45) { // 45 - 315 degrees
            return Direction.SIDE_RIGHT;
        }
        else if(angleEnemyToTarget > 225){ // 225 - 315 degrees
            return Direction.DOWN;
        }
        else if(angleEnemyToTarget > 135){ // 135 - 225 degrees
            return Direction.SIDE_LEFT;
        }
        else{ // 45 - 135 degrees
            return Direction.UP;
        }
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public float getVelocityX(){
        return velX * speed;
    }
    public float getVelocityY(){
        return velY * speed;
    }
}
