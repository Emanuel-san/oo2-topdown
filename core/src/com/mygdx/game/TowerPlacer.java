package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.EntityManager;
import com.mygdx.helper.Constant;
import com.mygdx.helper.processors.PlayerInputProcessor;
import java.util.ArrayList;
import java.util.List;

public class TowerPlacer {
    private boolean legalPlacement;
    private final EntityManager entityManager;
    private final PlayerInputProcessor inputProcessor;
    private final List<Vector2> towerCenters;
    private final Vector2 mousePos = new Vector2();


    public TowerPlacer(PlayerInputProcessor inputProcessor, EntityManager entityManager){
        legalPlacement = true;
        this.inputProcessor = inputProcessor;
        this.entityManager = entityManager;
        towerCenters = new ArrayList<>();
    }


    public void update(){
        if(inputProcessor.isTowerPlacementActive()) {
            legalPlacement = true;
            mousePos.set(inputProcessor.getUnprojectedMousePos().x, inputProcessor.getUnprojectedMousePos().y);
            for (Vector2 towerPos : towerCenters) {
                if (towerPos.dst(mousePos) < 150 / Constant.PPM) {
                    legalPlacement = false;
                }
            }
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        if(inputProcessor.isTowerPlacementActive()) {
            shapeRenderer.setColor(legalPlacement ? Color.GREEN : Color.RED);
            shapeRenderer.rect(inputProcessor.getUnprojectedMousePos().x - 8 / Constant.PPM,
                    inputProcessor.getUnprojectedMousePos().y - 8 / Constant.PPM,
                    16 / Constant.PPM, 16 / Constant.PPM
            );
            //debugPlacerSquare(shapeRenderer);
        }
    }
    private void debugPlacerSquare(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(Color.GREEN);
        for(Vector2 towerPos: towerCenters){
            if(towerPos.dst(mousePos) < 150/Constant.PPM){
                shapeRenderer.setColor(Color.RED);
            }
            shapeRenderer.line(mousePos, towerPos);
            shapeRenderer.setColor(Color.GREEN);
        }
    }
    public boolean placeTower(){
        if(legalPlacement){
            towerCenters.add(new Vector2(inputProcessor.getUnprojectedMousePos().x, inputProcessor.getUnprojectedMousePos().y));
            entityManager.createTower(inputProcessor.getUnprojectedMousePos().x,
                    inputProcessor.getUnprojectedMousePos().y,
                    16 / Constant.PPM,
                    16 / Constant.PPM);
            return true;
        }
        else {
            return false;
        }
    }
}
