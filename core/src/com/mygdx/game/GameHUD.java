package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class GameHUD{

    private Stage stage;
    private Table table;

    public GameHUD (){
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        addLabel("Score: " + 1000, Color.WHITE, Align.left);
        addLabel("Coins: " + 100000, Color.YELLOW, Align.right);
        table.align(Align.top);
        //table.debug();
    }

    public void addLabel(String text, Color color, int align){
        Label label = new Label(text, new Label.LabelStyle(new BitmapFont(), color));
        label.getStyle().font.getData().setScale(2);
        table.add(label).expandX().pad(10f).align(align);
    }

    public void render(){
        stage.act();
        stage.draw();
    }

    public void dispose(){
        stage.dispose();
    }
}
