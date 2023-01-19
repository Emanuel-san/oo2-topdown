package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;

public abstract class AI {
    protected Vector2 directionVector;

    public AI(){
        directionVector = new Vector2();
    }

    protected float distance(Vector2 fromObj, Vector2 toObj){
        float xDiff = toObj.x - fromObj.x;
        float yDiff = toObj.y - fromObj.y;

        return (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    protected void normalize(Vector2 vector, float distance){
        if(distance > 0){
            vector.x /= distance;
            vector.y /= distance;
        }
    }
    public abstract void update();
    protected abstract void updateTarget();

    public Vector2 getDirectionVector() {
        return directionVector;
    }
}
