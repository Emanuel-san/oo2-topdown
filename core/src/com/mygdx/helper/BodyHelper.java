package com.mygdx.helper;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.entities.GameEntity;

public class BodyHelper {

    public static Body createPolygonBody(float x, float y, float width, float height, boolean isStatic, World world, GameEntity entity){
        //Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        //Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture(fixtureDef).setUserData(entity);
        shape.dispose();
        return body;
    }
    public static Body createPolygonBody(float x, float y, float width, float height, boolean isStatic, World world, Object fixtureData){
        //Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        //Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture(fixtureDef).setUserData(fixtureData);
        shape.dispose();
        return body;
    }
    public static Body createPolygonBody(float x, float y, float width, float height, boolean isStatic, World world, GameEntity entity, EntityType type){

        short playerCategory = 0x0001, coinCategory = 0x0002, enemyCategory = 0x0003, projectileCategory = 0x0004;
        //towerCategory = 0x0005;

        //Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        Filter filter = new Filter();
        //Filter who can collide with whom.
        switch (type) {
            case PROJECTILE -> {
                filter.categoryBits = projectileCategory;
                filter.maskBits = enemyCategory;
            }
            case PLAYER -> {
                filter.categoryBits = playerCategory;
                filter.maskBits = (short) (enemyCategory | coinCategory);
            }
            case COIN -> {
                filter.categoryBits = coinCategory;
                filter.maskBits = playerCategory;
            }
            case ENEMY -> {
                filter.categoryBits = enemyCategory;
                filter.maskBits = (short) (playerCategory | projectileCategory);
            }
//            case TOWER -> {
//                filter.categoryBits = towerCategory;
//                filter.maskBits = 0x0006;
//            }
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.set(filter);
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture(fixtureDef).setUserData(entity);
        shape.dispose();
        return body;
    }
}
