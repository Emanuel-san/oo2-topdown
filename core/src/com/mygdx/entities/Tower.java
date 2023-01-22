package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.AI.TowerAI;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Constant;
import com.mygdx.helper.EntityType;

public class Tower extends GameEntity{
    private final TextureAtlas atlas;
    private Texture currentFrame;
    private int level;
    private StringBuilder regionFinder;
    private final TowerAI ai;

    public Tower(float x, float y, float width, float height, World world, TextureAtlas atlas, EntityManager entityManager) {
        super(x, y, width, height);
        this.atlas = atlas;
        body = BodyHelper.createPolygonBody(x,y,width,height,true, world, this, EntityType.TOWER);
        body.getFixtureList().first().setSensor(true);
        createSensorFixture(150 / Constant.PPM);
        ai = new TowerAI(entityManager, entityManager.getPlayerBase(), this);
        damage = 1;
        level = 1;
        regionFinder = new StringBuilder();
    }

    @Override
    public void update() {
        ai.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.draw(texture, body.getPosition().x - 12, body.getPosition().y - 8);
//        batch.draw(currentFrame,
//                body.getPosition().x - 24 / Constant.PPM,
//                body.getPosition().y - 24 / Constant.PPM,
//                0,
//                0,
//                currentFrame.getWidth(),
//                currentFrame.getHeight(),
//                1/Constant.PPM,
//                1/Constant.PPM, 0, 0, 0,
//                currentFrame.getWidth(), currentFrame.getHeight(), false, false
//        );
    }

    private void createSensorFixture(float radius){
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    public TowerAI getTowerAI() {
        return ai;
    }
    public int getDamage(){
        return damage;
    }
}
