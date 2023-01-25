package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.EntityManager;
import com.mygdx.helper.Constant;
import com.mygdx.helper.TiledMapLoader;
import com.mygdx.helper.processors.PlayerInputProcessor;
import com.mygdx.scenes.GameHUD;

import java.util.concurrent.ConcurrentSkipListMap;

public class GameScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final World world;
    private final EntityManager entityManager;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final GameHUD hud;
    private final TowerManager placer;
    float mapWidth, mapHeight;

    public GameScreen(OrthographicCamera camera, AssetManager assetManager){
        this.camera = camera;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        world = new World(new Vector2(0, 0), true); //topdown, no gravity
        entityManager = new EntityManager(world, assetManager);
        box2DDebugRenderer = new Box2DDebugRenderer();

        TiledMapLoader mapLoader = new TiledMapLoader(entityManager, world);
        mapRenderer = mapLoader.setupMap();
        mapWidth = mapRenderer.getMap().getProperties().get("width", Integer.class) * mapRenderer.getMap().getProperties().get("tilewidth", Integer.class);
        mapHeight = mapRenderer.getMap().getProperties().get("height", Integer.class) * mapRenderer.getMap().getProperties().get("tileheight", Integer.class);

        world.setContactListener(new CollisionManager(entityManager.getPlayer()));
        placer = new TowerManager(entityManager, world);
        PlayerInputProcessor inputProcessor = new PlayerInputProcessor(placer, entityManager.getPlayer(), camera);
        Gdx.input.setInputProcessor(inputProcessor);
        placer.setInputProcessor(inputProcessor);
        entityManager.getPlayer().setInputProcessor(inputProcessor);

        hud = new GameHUD(entityManager.getPlayer(), entityManager.getPlayerBase());
    }
    private void update(){
        world.step(1/60f, 6, 2); //60fps
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        placer.update();
        entityManager.update();
        hud.update();
    }

    private void cameraUpdate(){
        camera.position.x = entityManager.getPlayer().getBody().getPosition().x;
        camera.position.y = entityManager.getPlayer().getBody().getPosition().y;
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth/2, mapWidth/Constant.PPM - camera.viewportWidth/2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight/2, mapHeight/Constant.PPM - camera.viewportHeight/2);
        camera.update();
    }
    @Override
    public void render(float delta){
        this.update();

        //clear screen before we render again
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        placer.render(shapeRenderer);
        shapeRenderer.end();
        batch.begin();
        entityManager.render(batch);
        batch.end();

        hud.render();

        //box2DDebugRenderer.render(world,camera.combined);
    }
}
