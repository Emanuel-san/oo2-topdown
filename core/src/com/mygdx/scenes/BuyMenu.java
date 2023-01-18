package com.mygdx.scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.GameScreen;

public abstract class BuyMenu {
    private GameScreen screen;
    private Stage stage;
    private Table table;

    public BuyMenu(GameScreen screen){
        this.screen = screen;
        stage = new Stage();
        table = new Table();
    }
}
