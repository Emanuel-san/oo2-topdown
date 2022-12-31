package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.helper.Destroyable;

public class Enemy extends GameEntity implements Destroyable {

    public Enemy(float width, float height) {
        super(width, height);
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
