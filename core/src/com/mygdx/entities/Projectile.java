package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Destroyable;

import static com.mygdx.helper.Constant.PPM;

public class Projectile extends GameEntity implements Destroyable {

    private final Texture texture;

    public Projectile(float x, float y, float width, float height, Texture texture, World world, int damage, Vector3 unprojectedPos) {
        super(width, height);
        this.body = BodyHelper.createBody(x, y, width, height, false, true, world, this);
        this.body.setBullet(true);
        this.x = x;
        this.y = y;
        this.speed = 20f*PPM;
        this.damage = damage;
        this.texture = texture;
        this.calculateVelocityTrajectory(unprojectedPos);
        this.body.setLinearVelocity(velX * speed, velY * speed);
    }
    private void calculateVelocityTrajectory(Vector3 unprojectedPos){
        float angle = (float) Math.atan2(unprojectedPos.y - this.y, unprojectedPos.x - this.x);

        this.velX = (float) Math.cos(angle);
        this.velY = (float) Math.sin(angle);
    }

    @Override
    public void update() {
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
        //System.out.println("Projectile destroyed");
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public int getDamage(){
        return damage;
    }

}
