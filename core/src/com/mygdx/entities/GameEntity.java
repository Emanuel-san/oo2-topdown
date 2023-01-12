package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;


public abstract class GameEntity {

    protected float x,y,velX,velY,speed;
    protected float width, height;
    protected int health, damage;
    protected Body body;

    protected boolean killed;
    protected boolean isDestroyed;

    public GameEntity(float x, float y, float width, float height){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
        this.health = 0;
        this.damage = 0;
        this.killed = false;
        isDestroyed = false;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public Body getBody(){return body;}

    public int getDamage(){return damage;}
    public abstract boolean isKilled();

    public boolean isDestroyed() {
        return isDestroyed;
    }
    public void destroy(){
        isDestroyed = true;
    }
}
