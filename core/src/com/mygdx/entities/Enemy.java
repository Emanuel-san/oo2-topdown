package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Destroyable;

import static com.mygdx.helper.Constant.PPM;

public class Enemy extends GameEntity implements Destroyable {
    private Texture texture;

    public Enemy(float x, float y, float width, float height, World world, Texture texture) {
        super(x,y,width, height);
        this.body = BodyHelper.createBody(x, y, width, height, false, false, world, this);
        this.speed = 20f*PPM;
        this.velX = 0;
        this.health = 5;
        this.texture = texture;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 8, body.getPosition().y - 8);
    }

    @Override
    public void reduceHealth(int amount) {
        health -= amount;
        System.out.println("Health reduced by " + amount + " and is now " + health);
        if(health <= 0){
            this.destroy();
        }
    }

    @Override
    public void destroy() {
        isDestroyed = true;
        System.out.println("Im dead");
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
