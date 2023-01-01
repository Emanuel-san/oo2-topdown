package com.mygdx.helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.entities.GameEntity;

public class BodyHelper {

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, boolean isProjectile, World world, GameEntity entity){
        //Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true; //False is default in Box2D
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        //Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.isSensor = isProjectile;
        body.createFixture(fixtureDef).setUserData(entity);
        shape.dispose();
        return body;
    }
    public static void removeBody(World world, Body body){
        world.destroyBody(body);
        body = null; //set body to null and let GC take care of it.
    }
}
