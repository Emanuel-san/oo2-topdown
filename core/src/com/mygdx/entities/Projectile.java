package com.mygdx.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.EntityType;

public class Projectile extends GameEntity {

    private final Texture texture;
    public Projectile(float x, float y, float width, float height, Texture texture, World world, int damage, Vector2 targetPos) {
        super(x, y, width, height);
        this.body = BodyHelper.createPolygonBody(x, y, width, height, false, world, this, EntityType.PROJECTILE);
        this.body.setBullet(true);
        this.speed = 120f;
        this.damage = damage;
        this.texture = texture;
        this.setProjectileTrajectory(targetPos);
    }
    private void setProjectileTrajectory(Vector2 target){

        float angle = (float) Math.atan2(target.y - this.y, target.x - this.x);

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
