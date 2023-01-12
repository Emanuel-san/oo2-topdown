package com.mygdx.helper;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHelper {

    public static Animation<TextureRegion> animateRegion(TextureRegion region, int noOfFrames){
        TextureRegion[][] frames = region.split(region.getRegionWidth() / noOfFrames, region.getRegionHeight());

        TextureRegion[] animationFrames = new TextureRegion[frames.length * frames[0].length];
        int index = 0;
        for (int i = 0; i < frames.length; i++) {
            for (int j = 0; j < frames[i].length; j++) {
                animationFrames[index++] = frames[i][j];
            }
        }
        return new Animation<>(0.5f, animationFrames);
    }

    public static Animation<TextureRegion> flippedAnimation(Animation<TextureRegion> animation, int noOfFrames){
        TextureRegion[] toCopy = animation.getKeyFrames();
        TextureRegion[] flipped = new TextureRegion[noOfFrames];

        for(int i = 0; i < toCopy.length; i++){
            flipped[i] = new TextureRegion(toCopy[i]);
            flipped[i].flip(true, false);
        }

        return new Animation<>(0.5f, flipped);
    }
}
