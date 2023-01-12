package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.GameEntity;
import com.mygdx.entities.Projectile;
import com.mygdx.entities.Spawner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityManager {

    private GameScreen screen;
    private List<GameEntity> entities;
    private List<GameEntity> newEntities;

    public EntityManager(GameScreen screen){
        this.screen = screen;
        entities = new ArrayList<>();
        newEntities = new ArrayList<>();
    }

    public void update(){
            for (GameEntity entity : entities) {
                if (entity.isKilled()) {
                    screen.getWorld().destroyBody(entity.getBody());
                    entity.destroy();
                } else {
                    entity.update();
                }
            }
            entities.addAll(newEntities);
            newEntities.clear();
            entities.removeIf(GameEntity::isDestroyed);
    }
    public void createProjectile(float x, float y, int damage){
        entities.add(new Projectile(
                x, y, 6, 6,
                screen.getAssetManager().get("topdown_shooter/other/bulleta.png", Texture.class),
                screen.getWorld(), damage, screen.getUnprojectedMousePos())
        );
    }
    public void createEnemy(float x, float y){
        newEntities.add(new Enemy(x, y, 16, 16, screen.getWorld(),
                screen.getAssetManager().get("topdown_shooter/characters/2.png", Texture.class), 1)
        );
    }
    public void createSpawner(float x, float y, float width, float height){
        entities.add(new Spawner(x, y, width, height, screen.getWorld(), this,
                screen.getAssetManager().get("topdown_shooter/other/base2.png", Texture.class))
        );
    }

    public List<GameEntity> getEntities() {
        return entities;
    }
}
