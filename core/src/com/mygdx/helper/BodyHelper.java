package com.mygdx.helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.entities.EntityType;

public class BodyHelper {

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world){
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
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }
}
