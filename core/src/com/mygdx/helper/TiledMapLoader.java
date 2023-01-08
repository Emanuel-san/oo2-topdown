package com.mygdx.helper;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.entities.Base;
import com.mygdx.entities.Player;
import com.mygdx.entities.Spawner;
import com.mygdx.game.GameScreen;

public class TiledMapLoader {
    private final GameScreen screen;

    public TiledMapLoader(GameScreen screen){
        this.screen = screen;
    }
    public OrthogonalTiledMapRenderer setupMap(){
        TiledMap tiledMap = new TmxMapLoader().load("map0.tmx");
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
    private void parseMapObjects(MapObjects mapObjects){
        for(MapObject mapObject : mapObjects){
            if(mapObject instanceof PolygonMapObject){
                createBoundaryWall((PolygonMapObject) mapObject);
            }
            if(mapObject instanceof RectangleMapObject){
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if(rectangleName.equals("player")){
                    screen.setPlayer(new Player(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            screen.getWorld(),
                            screen.getAssetManager().get("topdown_shooter/char1.atlas", TextureAtlas.class))
                    );
                }
                if(rectangleName.equals("base")){
                    screen.setPlayerBase(new Base(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            screen.getWorld())
                    );
                }
                if(rectangleName.equals("spawner")){
                    screen.getEnemyManager().getSpawners().add(new Spawner(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            screen.getWorld(),
                            screen.getEnemyManager()
                    ));
                }
            }
        }
    }
    private void createBoundaryWall(PolygonMapObject polygonMapObject){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = screen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000).setUserData("Wall");
        shape.dispose();
    }
    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for(int i = 0; i < vertices.length / 2; i++){
            Vector2 current = new Vector2(vertices[i * 2], vertices[i * 2 + 1]);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}
