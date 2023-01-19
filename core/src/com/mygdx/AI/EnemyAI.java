package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.Base;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.Player;

public class EnemyAI extends AI{
    Player player;
    Base playerBase;
    Enemy controlledEnemy;
    public EnemyAI(Player player, Base playerBase, Enemy enemy){
        this.player = player;
        this.playerBase = playerBase;
        controlledEnemy = enemy;

    }
    @Override
    public void updateTarget(Vector2 direction){
        Vector2 playerPos = player.getBody().getPosition();
        Vector2 basePos = playerBase.getBody().getPosition();
        Vector2 myPos = controlledEnemy.getBody().getPosition();
        float distanceToPlayer = distance(myPos, playerPos);
        float distanceToBase = distance(myPos, basePos);
        if(distanceToPlayer < distanceToBase){
            direction.set(playerPos.x - myPos.x, playerPos.y - myPos.y);
            normalize(direction, distanceToPlayer);
        }else{
            direction.set(basePos.x - myPos.x, basePos.y - myPos.y);
            normalize(direction, distanceToBase);
        }
    }
}
