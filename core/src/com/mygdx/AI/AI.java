package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;

public abstract class AI {

    private float distance(Vector2 fromObj, Vector2 toObj){
        float xDiff = toObj.x - fromObj.x;
        float yDiff = toObj.y - fromObj.y;

        return (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    private void normalize(Vector2 vector, float distance){
        if(distance > 0){
            vector.x /= distance;
            vector.y /= distance;
        }
    }
    public Vector2 updateDirectionPlayerOrBase(Vector2 objectFrom, Vector2 player, Vector2 base, Vector2 direction){
        float distanceToPlayer = distance(objectFrom, player);
        float distanceToBase = distance(objectFrom, base);
        if(distanceToPlayer < distanceToBase){
            direction.set(player.x - objectFrom.x, player.y - objectFrom.y);
            normalize(direction, distanceToPlayer);
        }else{
            direction.set(base.x - objectFrom.x, base.y - objectFrom.y);
            normalize(direction, distanceToBase);
        }
        return direction;
    }
}
