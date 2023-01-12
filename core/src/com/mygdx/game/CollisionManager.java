package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.entities.*;


public class CollisionManager implements ContactListener {

    GameScreen screen;

    public CollisionManager(GameScreen screen){
        this.screen = screen;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a == null || b == null){return;}
        if(a.getUserData() == null || b.getUserData() == null){return;}
        if(a.getUserData() instanceof Projectile){
            projectileContact((Projectile) a.getUserData(), b.getUserData());
            return;
        }
        else if(b.getUserData() instanceof Projectile){
            projectileContact((Projectile) b.getUserData(), a.getUserData());
            return;
        }
        if(a.getUserData() instanceof Coin && b.getUserData() instanceof Player){
            coinContact((Coin) a.getUserData(), (Player) b.getUserData());
        }
        else if(b.getUserData() instanceof Coin && a.getUserData() instanceof Player){
            coinContact((Coin) b.getUserData(), (Player) a.getUserData());
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
        if(otherObj instanceof Player && !((Player) otherObj).isGodMode()){
            enemy.destroy();
            ((Player) otherObj).reduceHealth(enemy.getDamage());
        }
    }

    private void projectileContact(Projectile projectile, Object otherObj){
        projectile.destroy();
        if(otherObj instanceof Enemy){
            ((Enemy) otherObj).reduceHealth(projectile.getDamage());
            if(((Enemy) otherObj).isKilled()){
                screen.getPlayer().addScore(((Enemy) otherObj).getScoreValue());
            }
        }
        if(otherObj instanceof Spawner){
            ((Spawner) otherObj).reduceHealth(projectile.getDamage());
            if(((Spawner) otherObj).isKilled()){
                screen.getPlayer().addScore(((Spawner) otherObj).getScoreValue());
            } else {
                screen.getPlayer().addScore(1);
            }
        }
    }
    private void coinContact(Coin coin, Player player){
        coin.destroy();
        player.addCoins(coin.getValue());
    }
}
