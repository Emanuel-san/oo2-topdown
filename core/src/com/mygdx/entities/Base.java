package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.ContactType;

public class Base extends GameEntity{

    private final Texture base;

    public Base(float rectX, float rectY, float width, float height, World world) {
        super(width, height);
        this.body = BodyHelper.createBody(
                rectX + width / 2,
                rectY + height / 2,
                width, height, true, world, this
        );
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.speed = 0;
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.base = new Texture(Gdx.files.internal("topdown_shooter/other/base.png"));
    }

    @Override
    public void update() {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(base, body.getPosition().x - 24, body.getPosition().y - 24);
    }
}
