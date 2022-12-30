package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.helper.ContactType;

public class Base extends GameEntity{

    private final Texture base;
    private final ContactType type;

    public Base(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 0;
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.base = new Texture(Gdx.files.internal("topdown_shooter/other/base.png"));
        this.type = ContactType.BASE;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(base, body.getPosition().x - 24, body.getPosition().y - 24);
    }
}
