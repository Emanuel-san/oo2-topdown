package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends GameEntity{

    public Player(float width, float height, Body body){
        super(width, height, body);
        this.speed = 4f;
        System.out.println(body.getPosition().x);
        System.out.println(body.getPosition().y);

    }

    @Override
    public void update() {
        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
