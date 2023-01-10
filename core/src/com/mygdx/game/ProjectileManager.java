package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.entities.Projectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectileManager {
    private final GameScreen screen;
    private final Texture bullet;
    private final List<Projectile> projectileList = new ArrayList<>();

    public ProjectileManager(GameScreen screen){
        this.screen = screen;
        this.bullet = new Texture(Gdx.files.internal("topdown_shooter/other/bulleta.png"));
    }
    public void update(){
        //projectileList.removeIf(projectile -> projectile.isDestroyed()); java 8 or greater
        for(Iterator<Projectile> projectileIterator = projectileList.iterator(); projectileIterator.hasNext();){
            Projectile projectile = projectileIterator.next();
            if(projectile.isDestroyed()){
                screen.getWorld().destroyBody(projectile.getBody());
                projectileIterator.remove();
            }
            //projectile.update();
        }
    }
    public void createProjectile(float spawnPointX, float spawnPointY, int damage){


        projectileList.add(new Projectile(
                spawnPointX, spawnPointY, 6, 6, bullet, screen.getWorld(),
                damage, screen.getUnprojectedMousePos())
        );
    }


    public List<Projectile> getProjectileList() {
        return projectileList;
    }

}
