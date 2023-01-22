package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.Constant;
import com.mygdx.helper.Killable;

public class Base extends GameEntity implements Killable {

    private final Texture baseTexture;

    public Base(float x, float y, float width, float height, World world) {
        super(x,y,width, height);
        this.health = 1000;
        this.body = BodyHelper.createPolygonBody(x, y, width, height, true, world, this);
        this.baseTexture = new Texture(Gdx.files.internal("topdown_shooter/other/base.png"));
    }

    @Override
    public void update() {
    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.draw(baseTexture, x - 24, y - 24);
        batch.draw(baseTexture,
                body.getPosition().x - 24 / Constant.PPM,
                body.getPosition().y - 24 / Constant.PPM,
                0,
                0,
                baseTexture.getWidth(),
                baseTexture.getHeight(),
                1/Constant.PPM,
                1/Constant.PPM, 0, 0, 0,
                baseTexture.getWidth(), baseTexture.getHeight(), false, false
        );
    }

    @Override
    public void reduceHealth(int amount) {
        health-= amount;
        if(health <= 0){
            kill();
        }
    }

    @Override
    public void kill() {
        System.out.println("Base destroyed");
        killed = true;
    }
}
