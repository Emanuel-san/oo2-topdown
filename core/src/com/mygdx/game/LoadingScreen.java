package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class LoadingScreen implements Screen {
    private final TopDown game;

    public LoadingScreen(TopDown game){
        this.game = game;
    }

    @Override
    public void show() {
        game.getAssetManager().load("topdown_shooter/characters/1.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        if(game.getAssetManager().update()){
            game.setScreen(new GameScreen(game.getCamera(), game.getInputManager()));
        }else{
            System.out.println("Still Loading...");
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
