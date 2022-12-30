package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.entities.Projectile;
import com.mygdx.game.GameScreen;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.ContactType;

import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {
    private GameScreen screen;
    private Texture bullet;
    private final List<Projectile> projectileList = new ArrayList<>();

    public ProjectileManager(GameScreen screen){
        this.screen = screen;
        this.bullet = new Texture(Gdx.files.internal("topdown_shooter/other/bulleta.png"));
    }
    public void createProjectile(){
        projectileList.add(new Projectile(
                screen.getPlayer().getBody().getPosition().x + 16,
                screen.getPlayer().getBody().getPosition().y,
                6, 6, bullet, screen.getWorld()));
    }

    public List<Projectile> getProjectileList() {
        return projectileList;
    }
}
