package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.helper.AnimationHelper;
import com.mygdx.helper.Constant;
import com.mygdx.helper.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityManager {
    private final GameScreen screen;
    private final List<GameEntity> entities;
    private final List<GameEntity> newEntities;
    private Animation<TextureRegion> coinAnimation;
    private final HashMap<Direction, Animation<TextureRegion>> enemyAnimationMap;
    private Player player;
    private Base playerBase;

    public EntityManager(GameScreen screen){
        this.screen = screen;
        entities = new ArrayList<>();
        newEntities = new ArrayList<>();
        enemyAnimationMap = new HashMap<>();
        constructGraphicAssets();
    }

    public void update(){
        for (GameEntity entity : entities) {
            //Destroy world body and spawn a coin if a killable entity has been killed
            if (entity.isKilled()) {
                createCoin(entity.getBody().getPosition().x, entity.getBody().getPosition().y);
                screen.getWorld().destroyBody(entity.getBody());
                entity.destroy();
            }
            //Destroy world body if entity is set to be destroyed
            else if(entity.isDestroyed()) {
                screen.getWorld().destroyBody(entity.getBody());
            }
            else {
                entity.update();
            }
        }
        entities.removeIf(GameEntity::isDestroyed);
        entities.addAll(newEntities);
        newEntities.clear();
    }
    public void render(SpriteBatch batch){
        for (GameEntity entity: entities){
            entity.render(batch);
        }
    }
    public void createPlayerBase(float x, float y, float width, float height){
        playerBase = new Base(x,y,width,height,screen.getWorld());
        entities.add(playerBase);
    }
    public void createPlayer(float x, float y, float width, float height){
        player = new Player(x,y,width,height,screen,this);
        entities.add(player);
    }
    public void createProjectile(float x, float y, float width, float height, int damage, Vector2 targetPosition){
        newEntities.add(new Projectile(
            x, y, width, height,
            screen.getAssetManager().get("topdown_shooter/other/bulleta.png", Texture.class),
            screen.getWorld(), damage, targetPosition)
        );
    }
    public void createEnemy(float x, float y, float width, float height){
        newEntities.add(new Enemy(x, y, width, height, screen.getWorld(), enemyAnimationMap, 1, this));
    }
    public void createSpawner(float x, float y, float width, float height){
        entities.add(new Spawner(x, y, width, height, screen.getWorld(), this,
                screen.getAssetManager().get("topdown_shooter/other/base2.png", Texture.class))
        );
    }
    public void createCoin(float x, float y){
        newEntities.add(new Coin(x, y, 16  / Constant.PPM, 16  / Constant.PPM, screen.getWorld(), coinAnimation));
    }
    public void createTower(float x, float y, float width, float height){
        newEntities.add(new Tower(
                x, y, width, height,
                screen.getWorld(),
                screen.getAssetManager().get("topdown_shooter/cannon.atlas", TextureAtlas.class),
                this)
        );
    }

    public List<GameEntity> getEntities() {
        return entities;
    }

    private void constructGraphicAssets(){
        coinAnimation = AnimationHelper
                .animateRegion(
                        screen.getAssetManager()
                                .get("topdown_shooter/coin.atlas", TextureAtlas.class)
                                .findRegion("coin2"), 8, 0.1f);

        enemyAnimationMap.put(
                Direction.UP,
                AnimationHelper.animateRegion(
                        screen.getAssetManager()
                                .get("topdown_shooter/monster.atlas", TextureAtlas.class)
                                .findRegion("slime1_back"),
                        4, 0.1f)
        );
        enemyAnimationMap.put(
                Direction.DOWN,
                AnimationHelper.animateRegion(
                        screen.getAssetManager()
                                .get("topdown_shooter/monster.atlas", TextureAtlas.class)
                                .findRegion("slime1_front"),
                        4, 0.1f)
        );
        enemyAnimationMap.put(
                Direction.SIDE_LEFT,
                AnimationHelper.animateRegion(
                        screen.getAssetManager()
                                .get("topdown_shooter/monster.atlas", TextureAtlas.class)
                                .findRegion("slime1_side"),
                        4, 0.1f)
        );
        enemyAnimationMap.put(
                Direction.SIDE_RIGHT,
                AnimationHelper.flippedAnimation(enemyAnimationMap.get(Direction.SIDE_LEFT), 4, 0.1f));
    }

    public Player getPlayer() {
        return player;
    }

    public Base getPlayerBase() {
        return playerBase;
    }
}
