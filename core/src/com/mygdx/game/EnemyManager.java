package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.entities.Enemy;
import com.mygdx.entities.Spawner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyManager {
    private List<Enemy> enemies;
    private List<Spawner> spawners;
    private final Texture enemyTexture;

    final private GameScreen screen;

    public EnemyManager(GameScreen screen){
        this.enemies = new ArrayList<>();
        this.spawners = new ArrayList<>();
        this.screen = screen;
        this.enemyTexture = new Texture(Gdx.files.internal("topdown_shooter/characters/2.png"));
    }
    public void update(){
        for(Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
            Enemy enemy = enemyIterator.next();
            if(enemy.isDestroyed()){
                screen.getWorld().destroyBody(enemy.getBody());
                enemyIterator.remove();
            }else{
                enemy.update();
            }
        }
        for(Iterator<Spawner> spawnerIterator = spawners.iterator(); spawnerIterator.hasNext();){
            Spawner spawner = spawnerIterator.next();
            if(spawner.isDestroyed()){
                screen.getWorld().destroyBody(spawner.getBody());
                spawnerIterator.remove();
            }else{
                spawner.update();
            }

        }
    }

    public void createEnemy(float x, float y){
        enemies.add(new Enemy(x, y, 16, 16, screen.getWorld(), enemyTexture, 1));
    }


    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Spawner> getSpawners() {
        return spawners;
    }
}
