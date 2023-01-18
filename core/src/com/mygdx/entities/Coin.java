package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.EntityType;

public class Coin extends GameEntity {
    private Animation<TextureRegion> coinAnimation;
    private int value = 1;
    public Coin(float x, float y, float width, float height, World world, Animation<TextureRegion> animation) {
        super(x, y, width, height);
        this.body = BodyHelper.createPolygonBody(x, y, width, height, true, world, this, EntityType.COIN);
        coinAnimation = animation;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(coinAnimation.getKeyFrame(stateTime), body.getPosition().x - 8, body.getPosition().y - 8);
        if(coinAnimation.isAnimationFinished(stateTime)){
            stateTime = 0;
        }
    }

    public int getValue() {
        return value;
    }
}
