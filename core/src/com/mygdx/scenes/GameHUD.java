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
    private final Stage stage;
    private final Table scoreCoinTable;
    private final Table playerBaseHealthTable;
    private final Label scoreCount;
    private final Label coinsCount;
    private final Label playerHealth;
    private final Label baseHealth;

    public GameHUD (EntityManager entityManager){
        this.entityManager = entityManager;
        stage = new Stage();
        scoreCoinTable = new Table();
        scoreCoinTable.setFillParent(true);
        stage.addActor(scoreCoinTable);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f);
        Label scoreLabel = new Label("SCORE", new Label.LabelStyle(font, Color.WHITE));
        Label coinsLabel = new Label("COINS", new Label.LabelStyle(font, Color.YELLOW));
        scoreCount = new Label(Integer.toString(entityManager.getPlayer().getScore()), new Label.LabelStyle(font, Color.WHITE));
        coinsCount = new Label(Integer.toString(entityManager.getPlayer().getCoins()), new Label.LabelStyle(font, Color.YELLOW));
        scoreCoinTable.align(Align.top);
        scoreCoinTable.add(scoreLabel).expandX().pad(10, 10, 0, 0).align(Align.left);
        scoreCoinTable.add(coinsLabel).expandX().pad(10, 0, 0, 10).align(Align.right);
        scoreCoinTable.row();
        scoreCoinTable.add(scoreCount).expandX().pad(0, 10, 0, 0).align(Align.left);
        scoreCoinTable.add(coinsCount).expandX().pad(0, 0, 0, 10).align(Align.right);
        //scoreCoinTable.debug();
        playerBaseHealthTable = new Table();
        playerBaseHealthTable.setFillParent(true);
        stage.addActor(playerBaseHealthTable);
        Label playerHealthLabel = new Label("PLAYER", new Label.LabelStyle(font, Color.WHITE));
        Label baseHealthLabel = new Label("BASE", new Label.LabelStyle(font, Color.WHITE));
        playerHealth = new Label(Integer.toString(entityManager.getPlayer().getHealth()), new Label.LabelStyle(font, Color.WHITE));
        baseHealth = new Label(Integer.toString(entityManager.getPlayerBase().getHealth()), new Label.LabelStyle(font, Color.WHITE));
        playerBaseHealthTable.align(Align.bottom);
        playerBaseHealthTable.add(playerHealthLabel).expandX().pad(10, 10, 0, 0).align(Align.left);
        playerBaseHealthTable.add(baseHealthLabel).expandX().pad(10, 0, 0, 10).align(Align.right);
        playerBaseHealthTable.row();
        playerBaseHealthTable.add(playerHealth).expandX().pad(0, 10, 0, 0).align(Align.left);
        playerBaseHealthTable.add(baseHealth).expandX().pad(0, 0, 0, 10).align(Align.right);
        playerBaseHealthTable.debug();
    }

    public void update(){
        scoreCount.setText(Integer.toString(entityManager.getPlayer().getScore()));
        coinsCount.setText(Integer.toString(entityManager.getPlayer().getCoins()));
        scoreCoinTable.pack();
        playerHealth.setText(Integer.toString(entityManager.getPlayer().getHealth()));
        baseHealth.setText(Integer.toString(entityManager.getPlayerBase().getHealth()));
        playerBaseHealthTable.pack();

    }

    public void render(){
        stage.act();
        stage.draw();
    }

    public void dispose(){
        stage.dispose();
    }
}
