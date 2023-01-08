package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.EnemyManager;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Destroyable;

import java.util.List;

public class Spawner extends GameEntity implements Destroyable {
    private final Texture baseTexture;

    private final EnemyManager enemyManager;
    private boolean recentSpawn = false;
    private final Timer timer = new Timer();
    private final Timer.Task task = new Timer.Task() {
        @Override
        public void run() {
            recentSpawn = false;
        }
    };
    private float spawnDelay;
    public Spawner(float x, float y, float width, float height, World world, EnemyManager enemyManager) {
        super(x, y, width, height);
        this.enemyManager = enemyManager;
        this.spawnDelay = 10.0f;
        this.health = 10;
        this.body = BodyHelper.createBody(x, y, width, height, true, false, world, this);
        this.baseTexture = new Texture(Gdx.files.internal("topdown_shooter/other/base2.png"));

    }


    @Override
    public void update() {
        if(!recentSpawn){
            enemyManager.createEnemy(body.getPosition().x, body.getPosition().y - 40);
            recentSpawn = true;
            timer.scheduleTask(task, spawnDelay);
        }

    }

    @Override
    public void render(SpriteBatch batch, float deltaTime) {
        batch.draw(baseTexture, body.getPosition().x - 24, body.getPosition().y - 24);
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
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
