package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.entities.Base;
import com.mygdx.entities.Enemy;
import com.mygdx.entities.GameEntity;
import com.mygdx.entities.Projectile;
import com.mygdx.helper.ContactType;


public class CollisionManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a == null || b == null){return;}
        if(a.getUserData() == null || b.getUserData() == null){return;}
        if(a.getUserData() instanceof Projectile){
            if(b.getUserData() == ContactType.WALL){
                //System.out.println("Wall was hit");
                ((Projectile) a.getUserData()).destroy();

                return;
            }
            projectileHit((Projectile) a.getUserData(), (GameEntity) b.getUserData());
        }
        else if(b.getUserData() instanceof Projectile){
            if(a.getUserData() == ContactType.WALL){
                //System.out.println("Wall was hit");
                ((Projectile) b.getUserData()).destroy();
                return;
            }
            projectileHit((Projectile) b.getUserData(), (GameEntity) a.getUserData());
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

    private void projectileHit(Projectile projectile, GameEntity otherEntity){
        projectile.destroy();
        if(otherEntity instanceof Base){
            ((Base) otherEntity).destroy();
        }
        if(otherEntity instanceof Enemy){
            ((Enemy) otherEntity).reduceHealth(projectile.getDamage());
        }
    }
}
