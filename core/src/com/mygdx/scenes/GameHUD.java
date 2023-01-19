package com.mygdx.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.entities.EntityManager;

public class GameHUD{
    private final EntityManager entityManager;
    private Stage stage;
    private final Table table;
    private final Label scoreCount;
    private final Label coinsCount;

    public GameHUD (EntityManager entityManager){
        this.entityManager = entityManager;
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Label scoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label coinsLabel = new Label("COINS", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        scoreCount = new Label(Integer.toString(entityManager.getPlayer().getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinsCount = new Label(Integer.toString(entityManager.getPlayer().getCoins()), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        table.align(Align.top);
        table.add(scoreLabel).expandX().pad(10, 10, 0, 0).align(Align.left);
        table.add(coinsLabel).expandX().pad(10, 0, 0, 10).align(Align.right);
        table.row();
        table.add(scoreCount).expandX().pad(0, 10, 0, 0).align(Align.left);
        table.add(coinsCount).expandX().pad(0, 0, 0, 10).align(Align.right);
        table.debug();
    }

    public void update(){
        scoreCount.setText(Integer.toString(entityManager.getPlayer().getScore()));
        coinsCount.setText(Integer.toString(entityManager.getPlayer().getCoins()));
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
