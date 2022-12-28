package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.TiledMapLoader;

import static com.mygdx.helper.Constant.PPM;

public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMapLoader mapLoader;

    public GameScreen(OrthographicCamera camera){
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false); //topdown, no gravity
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.mapLoader = new TiledMapLoader(this);
        this.mapRenderer = mapLoader.setupMap();
    }

    private void update(){
        world.step(1/60f, 6, 2); //60fps
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
    }

    @Override
    public void render(float delta){
        this.update();

        //clear screen before we render again
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        batch.begin();

        batch.end();

        box2DDebugRenderer.render(world,camera.combined);
    }

    public World getWorld() {
        return world;
    }
}
