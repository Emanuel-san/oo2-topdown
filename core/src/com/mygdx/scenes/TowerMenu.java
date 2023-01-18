package com.mygdx.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.GameScreen;

public class TowerMenu extends BuyMenu {

    Texture texture;

    public TowerMenu(GameScreen screen) {
        super(screen);
        texture = screen.getAssetManager().get("topdown_shooter/towers/cannon/1_left.png", Texture.class);
    }
}
