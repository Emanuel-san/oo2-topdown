package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Base extends GameEntity{

    private Texture base;

    public Base(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 0;
        x = body.getPosition().x;
        y = body.getPosition().y;
        base = new Texture(Gdx.files.internal("topdown_shooter/other/base.png"));
    }

    @Override
    public void update() {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(base, body.getPosition().x - 24, body.getPosition().y - 24);
    }
}
