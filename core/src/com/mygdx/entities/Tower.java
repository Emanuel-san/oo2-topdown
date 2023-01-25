package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.AI.TowerAI;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Constant;
import com.mygdx.helper.Direction;

import java.util.HashMap;

public class Tower extends GameEntity{
    public static class UpgradeMultiplierException extends Exception {
        public UpgradeMultiplierException(String message) {
            super(message);
        }
    }
    private final TextureAtlas atlas;
    private TextureRegion currentFrame;
    private HashMap<Direction, TextureRegion> towerTextures;
    private int level;
    private final TowerAI ai;
    private float radius;

    public Tower(float x, float y, float width, float height, World world, TextureAtlas atlas, EntityManager entityManager) {
        super(x, y, width, height);
        this.atlas = atlas;
        radius = 150;
        body = BodyHelper.createPolygonBody(x,y,width,height,true, world, this);
        createCircleSensorFixture();
        ai = new TowerAI(entityManager, entityManager.getPlayerBase(), this);
        damage = 1;
        level = 1;
        towerTextures = new HashMap<>();
        parseTexturesToLevel();
    }

    @Override
    public void update() {
        ai.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        if(ai.isGotTarget()){
            currentFrame = towerTextures.get(ai.getCurrentDirection());
        }
        batch.draw(currentFrame,
                body.getPosition().x - 12 / Constant.PPM,
                body.getPosition().y - 8 / Constant.PPM,
                0, 0,
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                1 / Constant.PPM,
                1 / Constant.PPM, 0
        );
    }

    private void createCircleSensorFixture(){
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constant.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    private void parseTexturesToLevel(){
        towerTextures.clear();
        towerTextures.put(Direction.UP, atlas.findRegion(level + "_up"));
        towerTextures.put(Direction.DIAGONAL_UP_RIGHT, atlas.findRegion(level + "_rightup"));
        towerTextures.put(Direction.SIDE_RIGHT, atlas.findRegion(level + "_right"));
        towerTextures.put(Direction.DIAGONAL_DOWN_RIGHT, atlas.findRegion(level + "_rightdown"));
        towerTextures.put(Direction.DOWN, atlas.findRegion(level + "_down"));
        towerTextures.put(Direction.DIAGONAL_DOWN_LEFT, atlas.findRegion(level + "_leftdown"));
        towerTextures.put(Direction.SIDE_LEFT, atlas.findRegion(level + "_left"));
        towerTextures.put(Direction.DIAGONAL_UP_LEFT, atlas.findRegion(level + "_leftup"));
        currentFrame = towerTextures.get(Direction.SIDE_RIGHT);
    }

    public void upgrade(float multiplier) throws UpgradeMultiplierException{
        if(multiplier > 1){
            throw new UpgradeMultiplierException("Multiplier when upgrading can not be higher then 1");
        }
        else if(multiplier < 0){
            throw new UpgradeMultiplierException("Multiplier when upgrading can not be lower then 0");
        }
        level += 1;
        damage += 1;
        radius = radius + radius * multiplier;
        ai.upgrade(multiplier);

        Fixture fixture = body.getFixtureList().get(1);
        body.destroyFixture(fixture);
        createCircleSensorFixture();
        parseTexturesToLevel();
    }
    public boolean isMaxLevel(){
        return level >= 3;
    }

    public TowerAI getTowerAI() {
        return ai;
    }
    public int getDamage(){
        return damage;
    }
}
