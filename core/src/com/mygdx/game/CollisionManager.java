package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.entities.Base;
import com.mygdx.entities.Player;
import com.mygdx.entities.Projectile;
import com.mygdx.game.GameScreen;

import java.util.List;

public class CollisionManager implements ContactListener {
    private List<Projectile> projectileList;
    private Player player;
    private Base base;

    public CollisionManager(List<Projectile> list, Player player, Base base){
        this.projectileList = list;
        this.player = player;
        this.base = base;
    }

    @Override
    public void beginContact(Contact contact) {

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
}
