package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.*;
import com.mygdx.helper.TiledMapLoader;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    public static GameScreen SCREEN;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private ProjectileManager projectileManager;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMapLoader mapLoader;

    private InputManager inputManager;

    private Player player;
    private final EnemyManager enemyManager;
    private Base playerBase;

    public GameScreen(OrthographicCamera camera, InputManager inputManager){
        SCREEN = this;
        this.camera = camera;
        this.inputManager = inputManager;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), true); //topdown, no gravity
        this.world.setContactListener(new CollisionManager());
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.projectileManager = new ProjectileManager(this, camera);
        this.enemyManager = new EnemyManager(this);

        this.mapLoader = new TiledMapLoader(this);
        this.mapRenderer = mapLoader.setupMap();



    }

    private void update(){
        world.step(1/60f, 6, 2); //60fps
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        player.update();
        playerBase.update();
        projectileManager.update();
        enemyManager.update();
    }

    private void cameraUpdate(){
        Vector3 position = camera.position;
        position.x = player.getBody().getPosition().x;
        position.y = player.getBody().getPosition().y;
        float smoothFactor = 0.1f;
        camera.position.set(
                position.x * smoothFactor + camera.position.x * (1 - smoothFactor),
                position.y * smoothFactor + camera.position.y * (1 - smoothFactor),
                0
                );
        camera.update();
    }

    @Override
    public void render(float delta){
        this.update();

        //clear screen before we render again
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        batch.begin();
        player.render(batch);
        for(Projectile projectile : projectileManager.getProjectileList()){
            projectile.render(batch);
        }
        for(Spawner spawner : enemyManager.getSpawners()){
            spawner.render(batch);
        }
        for(Enemy enemy : enemyManager.getEnemies()){
            enemy.render(batch);
        }
        playerBase.render(batch);
        batch.end();

        box2DDebugRenderer.render(world,camera.combined);
    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.player.setProjectileManager(projectileManager);
    }
    public void setPlayerBase(Base playerBase) {
        this.playerBase = playerBase;
    }

    public Player getPlayer() {
        return player;
    }

    public Base getPlayerBase() {
        return playerBase;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
