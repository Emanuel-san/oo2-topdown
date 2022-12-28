package com.mygdx.helper;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.GameScreen;

public class TiledMapLoader {
    private TiledMap tiledMap;
    private GameScreen screen;

    public TiledMapLoader(GameScreen screen){
        this.screen = screen;
    }
    public OrthogonalTiledMapRenderer setupMap(){
        tiledMap = new TmxMapLoader().load("map0.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
    private void parseMapObjects(MapObjects mapObjects){

    }
}
