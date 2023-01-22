package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.entities.Base;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.Tower;
import com.mygdx.entities.EntityManager;
import com.mygdx.helper.Constant;

import java.util.ArrayList;
import java.util.List;

public class TowerAI extends AI{
    private final List<Enemy> enemiesInProximity;
    private final List<Enemy> destroyedEnemiesInProximity;
    private final EntityManager entityManager;
    private final Base base;

    private Enemy currentTarget;
    private boolean gotTarget;
    private final Tower controlledTower;

    private boolean recentlyShot;
    private final Timer timer = new Timer();
    private final Timer.Task task = new Timer.Task() {
        @Override
        public void run() {
            recentlyShot = false;
        }
    };

    public TowerAI(EntityManager manager, Base base, Tower tower){
        super();
        entityManager = manager;
        enemiesInProximity = new ArrayList<>();
        destroyedEnemiesInProximity = new ArrayList<>();
        this.base = base;
        controlledTower = tower;
        gotTarget = false;
        recentlyShot = false;
    }
    @Override
    public void update(){
        updateTarget();
        shootProjectile();
    }

    /**
     * Updates a towers current target that's in its proximity zone whichever is closest to the player base or
     * stops shooting if there is no target in its proximity.
     */
    @Override
    protected void updateTarget(){
        Vector2 basePos = base.getBody().getPosition();
        if(enemiesInProximity.isEmpty()){
            gotTarget = false;
        }
        else if(!enemiesInProximity.contains(currentTarget)){
            currentTarget = enemiesInProximity.get(0);
        }
        for(Enemy enemy : enemiesInProximity){
            if(!enemy.isDestroyed()) {
                if (!gotTarget) {
                    currentTarget = enemy;
                    gotTarget = true;
                }
                float currentTargetDistanceToBase = distance(currentTarget.getBody().getPosition(), basePos);
                float enemyDistanceToBase = distance(enemy.getBody().getPosition(), basePos);
                // Compare which target in proximity is closest to base
                if (enemyDistanceToBase < currentTargetDistanceToBase) {
                    currentTarget = enemy;
                }
            }
            else{
                //Target was killed inside the proximity zone
                destroyedEnemiesInProximity.add(enemy);
            }
        }
        clearDestroyedEnemiesFromList();
    }
    private void shootProjectile(){
        if(gotTarget && !recentlyShot){
            entityManager.createProjectile(
                    controlledTower.getBody().getPosition().x,
                    controlledTower.getBody().getPosition().y,
                    6 / Constant.PPM,
                    6 / Constant.PPM,
                    controlledTower.getDamage(),
                    targetLeading());
            recentlyShot = true;
            timer.scheduleTask(task, 0.5f);
        }
    }

    /**
     * Calculates the predicted vector point to lead the target
     * @return predicted vector
     */
    private Vector2 targetLeading(){
        Vector2 targetPosition = currentTarget.getBody().getPosition();
        float distanceTowerToTarget = distance(controlledTower.getBody().getPosition(), targetPosition);
        float timeOfFlight = distanceTowerToTarget / (400f / Constant.PPM); // timeOfFlight = distance / projectile speed
        //return lead point
        return new Vector2(
                targetPosition.x + currentTarget.getVelocityX() * timeOfFlight,
                targetPosition.y + currentTarget.getVelocityY() * timeOfFlight);
    }

    /**
     * Remove all targets from the list that were destroyed inside the proximity zone
     */
    private void clearDestroyedEnemiesFromList(){
        for(Enemy enemy: destroyedEnemiesInProximity){
            enemiesInProximity.remove(enemy);
        }
        destroyedEnemiesInProximity.clear();
    }

    public void addTargetToProximity(Enemy enemy){
        enemiesInProximity.add(enemy);
    }
    public void removeTargetFromProximity(Enemy enemy){
        enemiesInProximity.remove(enemy);
    }

    public Vector2 getCurrentTargetPosition() {
        return currentTarget.getBody().getPosition();
    }
}