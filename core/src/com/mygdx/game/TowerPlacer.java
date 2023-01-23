package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Constant;
import com.mygdx.helper.EntityType;
import com.mygdx.helper.processors.PlayerInputProcessor;

public class TowerPlacer {
    private boolean isActive;
    private Body body;
    private final PlayerInputProcessor inputProcessor;


    public TowerPlacer(PlayerInputProcessor inputProcessor, World world){
        isActive = false;
        this.inputProcessor = inputProcessor;
        body = BodyHelper.createPolygonBody(inputProcessor.getUnprojectedMousePos().x, inputProcessor.getUnprojectedMousePos().y,
                16/Constant.PPM, 16/Constant.PPM, false, world, this);
        body.getFixtureList().get(0).setSensor(true);
    }

    public void update(){
        body.setTransform(inputProcessor.getUnprojectedMousePos().x, inputProcessor.getUnprojectedMousePos().y, 0);
    }
    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(body.getPosition().x - 8/Constant.PPM, body.getPosition().y - 8/Constant.PPM, 16 / Constant.PPM,16 / Constant.PPM);
    }
}
