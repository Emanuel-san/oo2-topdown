package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.helper.Destroyable;

public class Spawner extends GameEntity implements Destroyable {

    public Spawner(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void reduceHealth(int amount) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isDestroyed() {
        return false;
    }
}
