package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.EntityManager;
import com.mygdx.entities.Tower;
import com.mygdx.helper.Constant;
import com.mygdx.helper.processors.PlayerInputProcessor;
import java.util.ArrayList;
import java.util.List;

public class TowerManager {
    private boolean legalPlacementPosition, legalUpgradePosition;
    private final EntityManager entityManager;
    private PlayerInputProcessor inputProcessor;
    private final World world;
    private final List<Vector2> towerVectorPoints;
    private Tower currentUpgradeTarget;
    private final Vector2 mousePos = new Vector2(), towerPos = new Vector2(), lowerBound = new Vector2(), upperBound = new Vector2();
    private final Rectangle towerPositionRect = new Rectangle();


    public TowerManager(EntityManager entityManager, World world){
        this.world = world;
        legalPlacementPosition = true;
        this.entityManager = entityManager;
        towerVectorPoints = new ArrayList<>();
    }
    private void chkMouseOnTowerPosition(){
        lowerBound.set(mousePos.x - (float)16/2/Constant.PPM, mousePos.y - (float)16/2/Constant.PPM);
        upperBound.set(mousePos.x + (float)16/2/Constant.PPM, mousePos.y + (float)16/2/Constant.PPM);

        QueryCallback callback = fixture -> {
            towerPos.set(fixture.getBody().getPosition());
            towerPositionRect.set(towerPos.x - 8 / Constant.PPM, towerPos.y - 8 / Constant.PPM, 16 / Constant.PPM,16 / Constant.PPM);
            if(fixture.getUserData() instanceof Tower && towerPositionRect.contains(mousePos)){
                legalUpgradePosition = true;
                currentUpgradeTarget = (Tower) fixture.getUserData();
                return false;
            }
            return true;
        };
        world.QueryAABB(callback, lowerBound.x, lowerBound.y, upperBound.x, upperBound.y);

    }

    public void update(){
        if(inputProcessor.isTowerPlacementActive() || inputProcessor.isTowerUpgradeActive()) {
            mousePos.set(inputProcessor.getUnprojectedMousePos().x, inputProcessor.getUnprojectedMousePos().y);
            legalPlacementPosition = true;
            legalUpgradePosition = false;
            for (Vector2 towerPos : towerVectorPoints) {
                if(towerPos.dst(mousePos) < 150 / Constant.PPM){
                    legalPlacementPosition = false;
                }
            }
            chkMouseOnTowerPosition();
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        if(inputProcessor.isTowerPlacementActive()) {
            shapeRenderer.setColor(legalPlacementPosition ? Color.GREEN : Color.RED);
            shapeRenderer.rect(inputProcessor.getUnprojectedMousePos().x - 8 / Constant.PPM,
                    inputProcessor.getUnprojectedMousePos().y - 8 / Constant.PPM,
                    16 / Constant.PPM, 16 / Constant.PPM
            );

            for(Vector2 towerPos: towerVectorPoints) {
                shapeRenderer.setColor(Color.GREEN);
                shapeRenderer.circle(towerPos.x, towerPos.y, 150/Constant.PPM);
                //debugPlacerSquare(shapeRenderer);
            }
        }
        if(inputProcessor.isTowerUpgradeActive()){
            shapeRenderer.setColor(legalUpgradePosition ? Color.GREEN : Color.RED);
            shapeRenderer.rect(inputProcessor.getUnprojectedMousePos().x - 8 / Constant.PPM,
                    inputProcessor.getUnprojectedMousePos().y - 8 / Constant.PPM,
                    16 / Constant.PPM, 16 / Constant.PPM
            );
        }
    }
    private void debugPlacerSquare(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(Color.GREEN);
        if(towerPos.dst(mousePos) < 150/Constant.PPM){
            shapeRenderer.setColor(Color.RED);
        }
        shapeRenderer.line(mousePos, towerPos);
        shapeRenderer.setColor(Color.GREEN);
    }
    public boolean placeTower(){
        if(legalPlacementPosition){
            towerVectorPoints.add(new Vector2(mousePos.x, mousePos.y));
            entityManager.createTower(mousePos.x, mousePos.y, 16 / Constant.PPM, 16 / Constant.PPM);
            return true;
        }
        else {
            return false;
        }
    }
    public boolean upgradeTower(){
        if(legalUpgradePosition && !currentUpgradeTarget.isMaxLevel()){
            System.out.println("Tower is being upgraded");
            try {
                currentUpgradeTarget.upgrade(0.5f);
            } catch (Tower.UpgradeMultiplierException e) {
                System.err.println("Error: " + e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    public void setInputProcessor(PlayerInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }
}
