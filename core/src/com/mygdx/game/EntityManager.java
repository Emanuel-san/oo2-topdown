package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.entities.*;
import com.mygdx.helper.AnimationHelper;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private GameScreen screen;
    private List<GameEntity> entities;
    private List<GameEntity> newEntities;
    private Animation<TextureRegion> coinAnimation;

    public EntityManager(GameScreen screen){
        this.screen = screen;
        entities = new ArrayList<>();
        newEntities = new ArrayList<>();
        loadAnimations();
    }

    public void update(){
            for (GameEntity entity : entities) {
                if (entity.isKilled()) {
                    createCoin(entity.getBody().getPosition().x, entity.getBody().getPosition().y);
                    screen.getWorld().destroyBody(entity.getBody());
                    entity.destroy();
                } else if(entity.isDestroyed()) {
                    screen.getWorld().destroyBody(entity.getBody());
                } else {
                    entity.update();
                }
            }
            entities.addAll(newEntities);
            newEntities.clear();
            entities.removeIf(GameEntity::isDestroyed);
    }
    public void render(SpriteBatch batch){
        for (GameEntity entity: entities){
            entity.render(batch);
        }
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
    public void createCoin(float x, float y){
        newEntities.add(new Coin(x, y, 16, 16, screen.getWorld(), coinAnimation));
    }

    public List<GameEntity> getEntities() {
        return entities;
    }

    private void loadAnimations(){
        coinAnimation = AnimationHelper
                .animateRegion(screen
                                .getAssetManager()
                                .get("topdown_shooter/coin.atlas", TextureAtlas.class)
                                .findRegion("coin2"),
                        8, 0.1f);
    }
}
