package com.mygdx.AI;

import com.mygdx.entities.Enemy;
import com.mygdx.game.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class TowerAI extends AI{
    List<Enemy> enemiesInRange;
    EntityManager entityManager;

    public TowerAI(EntityManager manager){
        entityManager = manager;
        enemiesInRange = new ArrayList<>();
    }

    public void addEnemyInRange(Enemy enemy){
        enemiesInRange.add(enemy);
        System.out.println(enemy + " in range for " + this);
    }
    public void removeEnemyInRange(Enemy enemy){
        if(enemiesInRange.remove(enemy)){
            System.out.println(enemy + " out of range for " + this);
        }
    }
}
