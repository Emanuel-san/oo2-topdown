package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.Base;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.Player;

public class EnemyAI{
    private final Player player;
    private final Base playerBase;
    private final Enemy controlledEnemy;
    private final Vector2 playerPos = new Vector2(), myPos = new Vector2(), basePos = new Vector2(), directionVector = new Vector2();
    public EnemyAI(Player player, Base playerBase, Enemy enemy){
        this.player = player;
        this.playerBase = playerBase;
        controlledEnemy = enemy;
    }
    public void update(){
        updateTarget();
    }
    /**
     * Update the target to move towards whichever is closer, player or the player base.
     */
    private void updateTarget(){
        playerPos.set(player.getBody().getPosition());
        basePos.set(playerBase.getBody().getPosition());
        myPos.set(controlledEnemy.getBody().getPosition());
        float distanceToPlayer = myPos.dst2(playerPos);
        float distanceToBase = myPos.dst2(basePos);
        // Player is closer
        if(distanceToPlayer < distanceToBase){
            directionVector.set(playerPos.x - myPos.x, playerPos.y - myPos.y);
            directionVector.nor();
        }
        // Base is closer
        else{
            directionVector.set(basePos.x - myPos.x, basePos.y - myPos.y);
            directionVector.nor();
        }
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }
}
