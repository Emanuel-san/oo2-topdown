package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Destroyable;

public class Base extends GameEntity implements Destroyable {

    private final Texture baseTexture;

    public Base(float x, float y, float width, float height, World world) {
        super(x,y,width, height);
        this.body = BodyHelper.createBody(x, y, width, height, true, false, world, this);
        this.speed = 0;
        this.baseTexture = new Texture(Gdx.files.internal("topdown_shooter/other/base.png"));
    }

    @Override
    public void update() {
        if(isDestroyed){
            //System.out.println("Base destroyed");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(baseTexture, body.getPosition().x - 24, body.getPosition().y - 24);
    }

    @Override
    public void reduceHealth(int amount) {

    }

    @Override
    public void destroy() {
        //System.out.println("Base destroyed");
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
