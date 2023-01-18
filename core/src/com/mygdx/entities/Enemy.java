package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.AI.EnemyAI;
import com.mygdx.game.GameScreen;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Killable;
import com.mygdx.helper.EntityType;

import static com.mygdx.helper.Constant.PPM;

public class Enemy extends GameEntity implements Killable {
    private Texture texture;

    private final EnemyAI ai;
    private Vector2 direction;
    private int scoreValue;

    public Enemy(float x, float y, float width, float height, World world, Texture texture, int damage, GameScreen screen) {
        super(x,y,width, height);
        this.body = BodyHelper.createPolygonBody(x, y, width, height, false, world, this, EntityType.ENEMY);
        this.speed = 3f*PPM;
        this.health = 5;
        this.damage = damage;
        this.texture = texture;
        this.ai = new EnemyAI(screen.getPlayer(), screen.getPlayerBase(), this);
        this.direction = new Vector2();
        scoreValue = 50;
    }

    @Override
    public void update() {
        ai.updateDirectionPlayerOrBase(direction);
        velX = direction.x;
        velY = direction.y;
        body.setLinearVelocity(velX * speed, velY * speed);
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
            this.kill();
        }
    }

    @Override
    public void kill() {
        killed = true;
        System.out.println("Im dead");
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
