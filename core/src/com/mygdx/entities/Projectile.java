package com.mygdx.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.EntityType;

import static com.mygdx.helper.Constant.PPM;

public class Projectile extends GameEntity {

    private final Texture texture;

    public Projectile(float x, float y, float width, float height, Texture texture, World world, int damage, Vector3 unprojectedPos) {
        super(x, y, width, height);
        this.body = BodyHelper.createBody(x, y, width, height, false, world, this, EntityType.PROJECTILE);
        this.body.setBullet(true);
        body.getFixtureList().first().setSensor(true);
        this.speed = 100f*PPM;
        this.damage = damage;
        this.texture = texture;
        this.setProjectileTrajectory(unprojectedPos);
    }
    private void setProjectileTrajectory(Vector3 unprojectedPos){

        float angle = (float) Math.atan2(unprojectedPos.y - this.y, unprojectedPos.x - this.x);

        this.velX = (float) Math.cos(angle);
        this.velY = (float) Math.sin(angle);
        this.body.setLinearVelocity(velX * speed, velY * speed);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 3, body.getPosition().y - 3);
    }
}
