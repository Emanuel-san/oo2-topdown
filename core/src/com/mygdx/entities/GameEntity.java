package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public abstract class GameEntity {

    protected float x,y,velX,velY,speed;
    protected float width, height;
    protected int health, damage;
    protected Body body;

    protected boolean isDestroyed;

    public GameEntity(float width, float height){
        this.width = width;
        this.height = height;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
        this.health = 0;
        this.damage = 0;
        this.isDestroyed = false;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public Body getBody(){return body;}
}
