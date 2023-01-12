package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.helper.AnimationHelper;

public class LoadingScreen implements Screen {
    private final TopDown game;

    public LoadingScreen(TopDown game){
        this.game = game;
    }

    @Override
    public void show() {
        game.getAssetManager().load("topdown_shooter/char1.atlas", TextureAtlas.class);
        game.getAssetManager().load("topdown_shooter/characters/2.png", Texture.class);
        game.getAssetManager().load("topdown_shooter/other/bulleta.png", Texture.class);
        game.getAssetManager().load("topdown_shooter/other/base2.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        if(game.getAssetManager().update()){
            game.setScreen(new GameScreen(game.getCamera(), game.getAssetManager()));
        }else{
            System.out.println("Loading...");
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
