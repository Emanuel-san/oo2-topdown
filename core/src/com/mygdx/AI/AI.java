package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;

public abstract class AI {

    float distance(Vector2 fromObj, Vector2 toObj){
        float xDiff = toObj.x - fromObj.x;
        float yDiff = toObj.y - fromObj.y;

        return (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    void normalize(Vector2 vector, float distance){
        if(distance > 0){
            vector.x /= distance;
            vector.y /= distance;
        }
    }
}
