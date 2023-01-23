package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.Base;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.GameEntity;
import com.mygdx.entities.Player;

public class EnemyAI extends AI{
    private final Player player;
    private final Base playerBase;
    private final Enemy controlledEnemy;
    private GameEntity currentTarget;

    Vector2 playerPos = new Vector2(), myPos = new Vector2(), basePos = new Vector2();
    public EnemyAI(Player player, Base playerBase, Enemy enemy){
        this.player = player;
        this.playerBase = playerBase;
        controlledEnemy = enemy;
        currentTarget = player;

    }
    @Override
    public void update(){
        updateTarget();
    }
    /**
     * Update the target to move towards whichever is closer, player or the player base.
     */
    @Override
    protected void updateTarget(){
        playerPos.set(player.getBody().getPosition());
        basePos.set(playerBase.getBody().getPosition());
        myPos.set(controlledEnemy.getBody().getPosition());
        float distanceToPlayer = myPos.dst(playerPos);
        float distanceToBase = myPos.dst(basePos);
        // Player is closer
        if(distanceToPlayer < distanceToBase){
            directionVector.set(playerPos.x - myPos.x, playerPos.y - myPos.y);
            normalize(directionVector, distanceToPlayer);
            currentTarget = player;
        }
        // Base is closer
        else{
            directionVector.set(basePos.x - myPos.x, basePos.y - myPos.y);
            normalize(directionVector, distanceToBase);
            currentTarget = playerBase;
        }
    }

    public GameEntity getCurrentTarget() {
        return currentTarget;
    }
}
