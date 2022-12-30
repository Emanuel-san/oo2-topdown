package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.helper.ContactType;

import static com.mygdx.helper.Constant.PPM;

public class Projectile extends GameEntity {
    private final Texture texture;

    public Projectile(float width, float height, Body body, Texture texture) {
        super(width, height, body);
        this.speed = 5f*PPM;
        velX = 1;
        this.texture = texture;
    }

    @Override
    public void update() {
        body.setLinearVelocity(velX*speed, velY);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 3, body.getPosition().y - 3);
    }
}
