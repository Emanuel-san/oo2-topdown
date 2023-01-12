package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Destroyable;
import com.mygdx.helper.EntityType;

public class Coin extends GameEntity implements Destroyable {
    private int value = 1;
    public Coin(float x, float y, float width, float height, World world) {

        super(x, y, width, height);
        this.body = BodyHelper.createBody(x, y, width, height, true, false, world, this, EntityType.COIN);

    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public boolean isKilled() {
        return false;
    }

    @Override
    public void reduceHealth(int amount) {

    }

    @Override
    public void kill() {

    }
}
