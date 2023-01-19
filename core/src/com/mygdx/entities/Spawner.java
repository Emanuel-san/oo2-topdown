package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Killable;

public class Spawner extends GameEntity implements Killable {
    private final Texture baseTexture;

    private final EntityManager entityManager;
    private boolean recentSpawn = false;
    private int scoreValue;
    private final Timer timer = new Timer();
    private final Timer.Task task = new Timer.Task() {
        @Override
        public void run() {
            recentSpawn = false;
        }
    };
    private float spawnDelay;
    public Spawner(float x, float y, float width, float height, World world, EntityManager entityManager, Texture texture) {
        super(x, y, width, height);
        this.entityManager = entityManager;
        this.spawnDelay = 10.0f;
        this.health = 10;
        this.body = BodyHelper.createPolygonBody(x, y, width, height, true, world, this);
        this.baseTexture = texture;
        scoreValue = 1000;
    }


    @Override
    public void update() {
        if(!recentSpawn){
            entityManager.createEnemy(body.getPosition().x, body.getPosition().y - 40);
            recentSpawn = true;
            timer.scheduleTask(task, spawnDelay);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(baseTexture, body.getPosition().x - 24, body.getPosition().y - 24);
    }

    @Override
    public void reduceHealth(int amount) {
        health -= amount;
        if(health <= 0){
            this.kill();
        }
    }

    @Override
    public void kill() {
        killed = true;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
