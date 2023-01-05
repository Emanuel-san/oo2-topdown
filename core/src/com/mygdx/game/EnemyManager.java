package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.Projectile;
import com.mygdx.entities.Spawner;
import com.mygdx.helper.BodyHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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


        //spawnEnemy();
    }
    public void update(){
        for(Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
            Enemy enemy = enemyIterator.next();
            if(enemy.isDestroyed()){
                screen.getWorld().destroyBody(enemy.getBody());
                enemyIterator.remove();
            }
        }
        for(Iterator<Spawner> spawnerIterator = spawners.iterator(); spawnerIterator.hasNext();){
            Spawner spawner = spawnerIterator.next();
            if(spawner.isDestroyed()){
                screen.getWorld().destroyBody(spawner.getBody());
                spawnerIterator.remove();
            }
            spawner.update();
        }
    }

    public void createEnemy(float x, float y){
        enemies.add(new Enemy(x, y, 16, 16, screen.getWorld(), enemyTexture));
    }
    private float distance(Vector2 obj1, Vector2 obj2){
        float xDiff = obj2.x - obj1.x;
        float yDiff = obj2.y - obj1.y;

        return (float)Math.sqrt(xDiff * xDiff + yDiff + yDiff);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Spawner> getSpawners() {
        return spawners;
    }
}
