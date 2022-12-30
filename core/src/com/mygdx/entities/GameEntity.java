package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;


public abstract class GameEntity {

    protected float x,y,velX,velY,speed;
    protected float width, height;
    protected Body body;

    protected boolean isDestroyed = false;

    public GameEntity(float width, float height){
        this.width = width;
        this.height = height;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public Body getBody(){return body;}

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
