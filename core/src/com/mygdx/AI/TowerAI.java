package com.mygdx.AI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.entities.Base;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.Tower;
import com.mygdx.entities.EntityManager;
import com.mygdx.helper.Constant;
import com.mygdx.helper.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TowerAI extends AI{
    private final List<Enemy> enemiesInProximity;
    private final List<Enemy> destroyedEnemiesInProximity;
    private final EntityManager entityManager;
    private final HashMap<Direction, Vector2> projectileSpawnVectors;
    private final Base base;

    private Enemy currentTarget;
    private Direction currentDirection;
    private final Vector2 leadingPoint;
    private boolean gotTarget;
    private final Tower tower;

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
        this.tower = tower;
        gotTarget = false;
        recentlyShot = false;
        leadingPoint = new Vector2();
        projectileSpawnVectors = new HashMap<>();
        constructSpawnVectorMap();
    }
    @Override
    public void update(){
        updateTarget();
        setDirection();
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
            targetLeading();
            entityManager.createProjectile(
                    projectileSpawnVectors.get(currentDirection),
                    6 / Constant.PPM,
                    6 / Constant.PPM,
                    tower.getDamage(),
                    leadingPoint);
            recentlyShot = true;
            timer.scheduleTask(task, 0.5f);
        }
    }

    /**
     * Calculates the predicted vector point to lead a target
     */
    private void targetLeading(){
        Vector2 targetPosition = currentTarget.getBody().getPosition();
        float distanceTowerToTarget = distance(tower.getBody().getPosition(), targetPosition);
        float timeOfFlight = distanceTowerToTarget / (400f / Constant.PPM); // timeOfFlight = distance / projectile speed
        //return lead point
        leadingPoint.set(
                targetPosition.x + currentTarget.getVelocityX() * timeOfFlight,
                targetPosition.y + currentTarget.getVelocityY() * timeOfFlight);
    }
    private void setDirection(){
        double angleToTarget = Math.toDegrees(Math.atan2(
                leadingPoint.x - tower.getBody().getPosition().x,
                leadingPoint.y - tower.getBody().getPosition().y)
        );

        if(angleToTarget > 158 || angleToTarget < -158){
            currentDirection = Direction.DOWN;
        }
        else if(angleToTarget > 113){
            currentDirection = Direction.DIAGONAL_DOWN_RIGHT;
        }
        else if(angleToTarget > 68){
            currentDirection = Direction.SIDE_RIGHT;
        }
        else if(angleToTarget > 23){
            currentDirection = Direction.DIAGONAL_UP_RIGHT;
        }
        else if(angleToTarget > -23){
            currentDirection = Direction.UP;
        }
        else if(angleToTarget > -68){
            currentDirection = Direction.DIAGONAL_UP_LEFT;
        }
        else if(angleToTarget > -113){
            currentDirection = Direction.SIDE_LEFT;
        }
        else {
            currentDirection = Direction.DIAGONAL_DOWN_LEFT;
        }
    }

    private void constructSpawnVectorMap(){
        projectileSpawnVectors.put(Direction.SIDE_LEFT, new Vector2(tower.getBody().getPosition().x - 16 / Constant.PPM, tower.getBody().getPosition().y));
        projectileSpawnVectors.put(Direction.DIAGONAL_UP_LEFT, new Vector2(tower.getBody().getPosition().x - 16 / Constant.PPM, tower.getBody().getPosition().y + 16 / Constant.PPM));
        projectileSpawnVectors.put(Direction.UP, new Vector2(tower.getBody().getPosition().x, tower.getBody().getPosition().y + 16 / Constant.PPM));
        projectileSpawnVectors.put(Direction.DIAGONAL_UP_RIGHT, new Vector2(tower.getBody().getPosition().x + 16 / Constant.PPM, tower.getBody().getPosition().y + 16 / Constant.PPM));
        projectileSpawnVectors.put(Direction.SIDE_RIGHT, new Vector2(tower.getBody().getPosition().x + 16 / Constant.PPM, tower.getBody().getPosition().y));
        projectileSpawnVectors.put(Direction.DIAGONAL_DOWN_RIGHT, new Vector2(tower.getBody().getPosition().x + 16 / Constant.PPM, tower.getBody().getPosition().y - 16 / Constant.PPM));
        projectileSpawnVectors.put(Direction.DOWN, new Vector2(tower.getBody().getPosition().x, tower.getBody().getPosition().y - 16 / Constant.PPM));
        projectileSpawnVectors.put(Direction.DIAGONAL_DOWN_LEFT, new Vector2(tower.getBody().getPosition().x - 16 / Constant.PPM, tower.getBody().getPosition().y - 16 / Constant.PPM));
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
    public Direction getCurrentDirection() {
        return currentDirection;
    }
    public boolean isGotTarget() {
        return gotTarget;
    }
}