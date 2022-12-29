package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.helper.Constant.PPM;

public class Projectile extends GameEntity {

    public Projectile(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 5f*PPM;
        velX = 1;
    }

    @Override
    public void update() {
        body.setLinearVelocity(velX*speed, velY);
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
