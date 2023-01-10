package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;

public class EnemyAI extends AI{
    public void updateDirectionPlayerOrBase(Vector2 objectFrom, Vector2 player, Vector2 base, Vector2 direction){
        float distanceToPlayer = distance(objectFrom, player);
        float distanceToBase = distance(objectFrom, base);
        if(distanceToPlayer < distanceToBase){
            direction.set(player.x - objectFrom.x, player.y - objectFrom.y);
            normalize(direction, distanceToPlayer);
        }else{
            direction.set(base.x - objectFrom.x, base.y - objectFrom.y);
            normalize(direction, distanceToBase);
        }
    }
}
