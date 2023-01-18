package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.EntityType;

public class Tower extends GameEntity{
    Texture texture;

    public Tower(float x, float y, float width, float height, World world, Texture texture) {
        super(x, y, width, height);
        this.texture = texture;
        body = BodyHelper.createBody(x,y,width,height,true, world, this, EntityType.TOWER);
        body.getFixtureList().first().setSensor(true);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
