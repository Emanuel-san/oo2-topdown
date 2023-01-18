package com.mygdx.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameScreen;

public class GameHUD{
    private GameScreen screen;
    private Stage stage;
    private Table table;
    private Label scoreCount;
    private Label coinsCount;
    private Label scoreLabel;
    private Label coinsLabel;

    public GameHUD (GameScreen screen){
        this.screen = screen;
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        scoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinsLabel = new Label("COINS", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        scoreCount = new Label(Integer.toString(screen.getPlayer().getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinsCount = new Label(Integer.toString(screen.getPlayer().getCoins()), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        table.align(Align.top);
        table.add(scoreLabel).expandX().pad(10, 10, 0, 0).align(Align.left);
        table.add(coinsLabel).expandX().pad(10, 0, 0, 10).align(Align.right);
        table.row();
        table.add(scoreCount).expandX().pad(0, 10, 0, 0).align(Align.left);
        table.add(coinsCount).expandX().pad(0, 0, 0, 10).align(Align.right);
        table.debug();
    }

    public void update(){
        scoreCount.setText(Integer.toString(screen.getPlayer().getScore()));
        coinsCount.setText(Integer.toString(screen.getPlayer().getCoins()));
        table.pack();
    }

    public void render(){
        stage.act();
        stage.draw();
    }

    public void dispose(){
        stage.dispose();
    }
}
