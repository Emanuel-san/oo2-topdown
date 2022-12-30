package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.ContactType;

import static com.mygdx.helper.Constant.PPM;

public class Projectile extends GameEntity implements Destroyable {
    private final Texture texture;
    private boolean isDestroyed;

    public Projectile(float x, float y, float width, float height, Texture texture, World world) {
        super(width, height);
        this.body = BodyHelper.createBody(x, y, width, height, false, world, this);
        this.x = x;
        this.y = y;
        this.speed = 5f*PPM;
        velX = 1;
        this.texture = texture;
        this.isDestroyed = false;
    }

    @Override
    public void update() {
        body.setLinearVelocity(velX*speed, velY);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 3, body.getPosition().y - 3);
    }

    @Override
    public void reduceHealth(int amount) {

    }

    @Override
    public void destroy() {
        System.out.println("Im destroyed");
        isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
