package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.entities.*;
import com.mygdx.entities.Killable;


public class CollisionManager implements ContactListener {
    private final Player player;

    public CollisionManager(Player player){
        this.player = player;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a == null || b == null){return;}
        if(a.getUserData() == null || b.getUserData() == null){return;}
        if(a.getUserData() instanceof Projectile){
            if(!b.isSensor()) {
                projectileContact((Projectile) a.getUserData(), b.getUserData());
            }
            return;
        }
        else if(b.getUserData() instanceof Projectile){
            if(!a.isSensor()) {
                projectileContact((Projectile) b.getUserData(), a.getUserData());
            }
            return;
        }
        if(a.getUserData() instanceof Coin && b.getUserData() instanceof Player){
            coinContact((Coin) a.getUserData());
            return;
        }
        else if(b.getUserData() instanceof Coin && a.getUserData() instanceof Player){
            coinContact((Coin) b.getUserData());
            return;
        }
        if(a.getUserData() instanceof Enemy){
            enemyContact((Enemy) a.getUserData(), b.getUserData());
        }
        else if(b.getUserData() instanceof Enemy){
            enemyContact((Enemy) b.getUserData(), a.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object objectA = contact.getFixtureA().getUserData();
        Object objectB = contact.getFixtureB().getUserData();
        if(fixtureA.isSensor() && objectA instanceof Tower){
            if(objectB instanceof Enemy && !contact.isTouching()){
                ((Tower) objectA).getTowerAI().removeTargetFromProximity((Enemy) objectB);
            }
        }
        else if(fixtureB.isSensor() && objectB instanceof Tower){
            if(objectA instanceof Enemy && !contact.isTouching()){
                ((Tower) objectB).getTowerAI().removeTargetFromProximity((Enemy) objectA);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void enemyContact(Enemy enemy, Object otherObj){
        if(otherObj instanceof Base){
            enemy.destroy();
            ((Base) otherObj).reduceHealth(enemy.getDamage());
        }
        else if(otherObj instanceof Player && !((Player) otherObj).isGodMode()){
            enemy.destroy();
            ((Player) otherObj).reduceHealth(enemy.getDamage());
        }
        else if(otherObj instanceof Tower && ((Tower) otherObj).getBody().getFixtureList().get(1).isSensor()){
            ((Tower) otherObj).getTowerAI().addTargetToProximity(enemy);
        }
    }

    private void projectileContact(Projectile projectile, Object otherObj){
        projectile.destroy();
        if(otherObj instanceof Killable){
            if(otherObj instanceof Enemy){
                ((Enemy) otherObj).reduceHealth(projectile.getDamage());
                if(((Enemy) otherObj).isKilled()){
                    player.addScore(((Enemy) otherObj).getScoreValue());
                }
            }
            if(otherObj instanceof Spawner){
                ((Spawner) otherObj).reduceHealth(projectile.getDamage());
                if(((Spawner) otherObj).isKilled()){
                    player.addScore(((Spawner) otherObj).getScoreValue());
                }
                else {
                    player.addScore(1);
                }
            }
        }
    }
    private void coinContact(Coin coin){
        coin.destroy();
        this.player.addCoins(coin.getValue());
    }
}
