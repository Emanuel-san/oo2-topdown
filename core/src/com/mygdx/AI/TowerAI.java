package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.Base;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.Tower;
import com.mygdx.game.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class TowerAI extends AI{
    private List<Enemy> enemiesInRange;
    private List<Enemy> destroyedEnemiesInRange;
    private EntityManager entityManager;
    private Base playerBase;

    private Enemy currentTarget;
    private boolean gotTarget;
    private Tower controlledTower;

    public TowerAI(EntityManager manager, Base base, Tower tower){
        entityManager = manager;
        enemiesInRange = new ArrayList<>();
        destroyedEnemiesInRange = new ArrayList<>();
        playerBase = base;
        controlledTower = tower;
        gotTarget = false;
    }
    @Override
    public void updateTarget(Vector2 direction){
        Vector2 basePos = playerBase.getBody().getPosition();
        Vector2 myPos = controlledTower.getBody().getPosition();
        if(enemiesInRange.isEmpty()){
            gotTarget = false;
        }
        for(Enemy enemy : enemiesInRange){
            if(!enemy.isDestroyed()) {
                if (!gotTarget) {
                    currentTarget = enemy;
                    gotTarget = true;
                }
                float currentTargetDistanceToBase = distance(currentTarget.getBody().getPosition(), basePos);
                float enemyDistanceToBase = distance(enemy.getBody().getPosition(), basePos);
                if (enemyDistanceToBase < currentTargetDistanceToBase) {
                    currentTarget = enemy;
                }
            }
            else{
                System.out.println("Enemy killed in sensor zone");
                destroyedEnemiesInRange.add(enemy);
            }
        }
        if(gotTarget){
            Vector2 targetVector = currentTarget.getBody().getPosition();
            direction.set(targetVector.x - myPos.x, targetVector.y - myPos.y);
            normalize(direction, distance(myPos, targetVector));
        }
        clearDestroyedEnemiesFromList();
    }
    private void clearDestroyedEnemiesFromList(){
        for(Enemy enemy: destroyedEnemiesInRange){
            enemiesInRange.remove(enemy);
        }
        destroyedEnemiesInRange.clear();
    }

    public void addEnemyInRange(Enemy enemy){
        enemiesInRange.add(enemy);
        System.out.println(enemy + " IN RANGE FOR " + this);
    }
    public void removeEnemyOutOfRange(Enemy enemy){
        if(enemiesInRange.remove(enemy)){
            System.out.println(enemy + " OUT OF RANGE FOR " + this);
        }
    }

    public boolean isGotTarget() {
        return gotTarget;
    }
}
