package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.Projectile;
import com.mygdx.helper.BodyHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO -Enemy spawners

public class EnemyManager {
    private List<Enemy> enemies;


    final private GameScreen screen;
    private final Texture texture;

    public EnemyManager(GameScreen screen){
        this.enemies = new ArrayList<>();
        this.screen = screen;
        this.texture = new Texture(Gdx.files.internal("topdown_shooter/characters/2.png"));
        spawnEnemy();
    }
    public void update(World world){
        for(Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
            Enemy enemy = enemyIterator.next();
            if(enemy.isDestroyed()){
                BodyHelper.removeBody(world, enemy.getBody());
                enemyIterator.remove();
            }
        }

    }
    private void spawnEnemy(){
        enemies.add(new Enemy(
                screen.getPlayer().getBody().getPosition().x + 50,
                screen.getPlayer().getBody().getPosition().y,
                16,16, screen.getWorld(), texture)
        );
        enemies.add(new Enemy(
                screen.getPlayer().getBody().getPosition().x + 50,
                screen.getPlayer().getBody().getPosition().y + 30,
                16,16, screen.getWorld(), texture)
        );
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
