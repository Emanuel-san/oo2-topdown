package com.mygdx.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.entities.Projectile;
import com.mygdx.game.GameScreen;

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
        Body body = BodyHelper.createBody(
                screen.getPlayer().getBody().getPosition().x + 16,
                screen.getPlayer().getBody().getPosition().y,
                6,6,false, screen.getWorld()
        );
        projectileList.add(new Projectile(6, 6, body, bullet));
    }

    public List<Projectile> getProjectileList() {
        return projectileList;
    }
}
