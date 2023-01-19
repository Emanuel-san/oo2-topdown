package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.AI.EnemyAI;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Killable;
import com.mygdx.helper.EntityType;

public class Enemy extends GameEntity implements Killable {
    private final Texture texture;
    private final EnemyAI ai;
    private int scoreValue;

    public Enemy(float x, float y, float width, float height, World world, Texture texture, int damage, EntityManager entityManager) {
        super(x,y,width, height);
        this.body = BodyHelper.createPolygonBody(x, y, width, height, false, world, this, EntityType.ENEMY);
        this.speed = 50f;
        this.health = 5;
        this.damage = damage;
        this.texture = texture;
        this.ai = new EnemyAI(entityManager.getPlayer(), entityManager.getPlayerBase(), this);
        scoreValue = 50;
    }

    @Override
    public void update() {
        ai.update();
        velX = ai.getDirection().x * speed;
        velY = ai.getDirection().y * speed;
        body.setLinearVelocity(velX, velY);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 8, body.getPosition().y - 8);
    }

    @Override
    public void reduceHealth(int amount) {
        health -= amount;
        //System.out.println("Health reduced by " + amount + " and is now " + health);
        if(health <= 0){
            this.kill();
        }
    }

    @Override
    public void kill() {
        killed = true;
        //System.out.println("Im dead");
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public float getVelocityX(){
        return velX;
    }
    public float getVelocityY(){
        return velY;
    }
}
