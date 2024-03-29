package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class LoadingScreen extends ScreenAdapter {
    private final TopDown game;

    public LoadingScreen(TopDown game){
        this.game = game;
    }

    @Override
    public void show() {
        game.getAssetManager().load("topdown_shooter/char1.atlas", TextureAtlas.class);
        game.getAssetManager().load("topdown_shooter/coin.atlas", TextureAtlas.class);
        game.getAssetManager().load("topdown_shooter/monster.atlas", TextureAtlas.class);
        game.getAssetManager().load("topdown_shooter/cannon.atlas", TextureAtlas.class);
        game.getAssetManager().load("topdown_shooter/characters/2.png", Texture.class);
        game.getAssetManager().load("topdown_shooter/other/bulleta.png", Texture.class);
        game.getAssetManager().load("topdown_shooter/other/base.png", Texture.class);
        game.getAssetManager().load("topdown_shooter/other/base2.png", Texture.class);
        game.getAssetManager().load("topdown_shooter/towers/cannon/1_left.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        if(game.getAssetManager().update()){
            game.setScreen(new GameScreen(game.getCamera(), game.getAssetManager()));
        }else{
            System.out.println("Loading...");
        }
    }
}
