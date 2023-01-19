package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.TiledMapLoader;
import com.mygdx.scenes.GameHUD;

public class GameScreen extends ScreenAdapter {
    public static GameScreen SCREEN;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private EntityManager entityManager;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMapLoader mapLoader;

    private InputMultiplexer inputMultiplexer;
    private GameHUD hud;
    private Vector3 mousePos;
    private Vector3 unprojectedMousePos;

    public GameScreen(OrthographicCamera camera, AssetManager assetManager){
        SCREEN = this;
        this.camera = camera;
        this.assetManager = assetManager;
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true); //topdown, no gravity
        entityManager = new EntityManager(this);
        world.setContactListener(new CollisionManager(entityManager));
        box2DDebugRenderer = new Box2DDebugRenderer();
        mousePos = new Vector3();
        unprojectedMousePos = new Vector3();
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        mapLoader = new TiledMapLoader(entityManager, world);
        mapRenderer = mapLoader.setupMap();

        entityManager.getPlayer().setCamera(camera);

        hud = new GameHUD(entityManager);
    }

    private void update(){
        world.step(1/60f, 6, 2); //60fps
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        updateUnprojectedMousePos();
        entityManager.update();
        hud.update();
    }

    private void cameraUpdate(){
        Vector3 position = camera.position;
        position.x = entityManager.getPlayer().getBody().getPosition().x;
        position.y = entityManager.getPlayer().getBody().getPosition().y;
        float smoothFactor = 0.1f;
        camera.position.set(
                position.x * smoothFactor + camera.position.x * (1 - smoothFactor),
                position.y * smoothFactor + camera.position.y * (1 - smoothFactor),
                0
                );
        camera.update();
    }
    private void updateUnprojectedMousePos(){
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        unprojectedMousePos = camera.unproject(mousePos);
    }


    @Override
    public void render(float delta){
        this.update();

        //clear screen before we render again
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.render();

        batch.begin();
        entityManager.render(batch);
        batch.end();

        hud.render();

        box2DDebugRenderer.render(world,camera.combined);
    }

    public World getWorld() {
        return world;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Vector3 getUnprojectedMousePos() {
        return unprojectedMousePos;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }
}
