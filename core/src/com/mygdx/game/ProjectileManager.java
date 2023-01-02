package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Projectile;
import com.mygdx.game.GameScreen;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.ContactType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectileManager {
    private Vector3 tmpMousePos;
    private Vector3 tmpUnprojectedPos;
    private final GameScreen screen;
    private final OrthographicCamera camera;
    private final Texture bullet;
    private final List<Projectile> projectileList = new ArrayList<>();

    public ProjectileManager(GameScreen screen, OrthographicCamera camera){
        this.screen = screen;
        this.camera = camera;
        this.tmpMousePos = new Vector3();
        this.tmpUnprojectedPos = new Vector3();
        this.bullet = new Texture(Gdx.files.internal("topdown_shooter/other/bulleta.png"));
    }
    public void update(World world){
        //projectileList.removeIf(projectile -> projectile.isDestroyed()); java 8 or greater
        for(Iterator<Projectile> projectileIterator = projectileList.iterator(); projectileIterator.hasNext();){
            Projectile projectile = projectileIterator.next();
            if(projectile.isDestroyed()){
                BodyHelper.removeBody(world, projectile.getBody());
                projectileIterator.remove();
            }
            //projectile.update();
        }
    }
    public void createProjectile(float spawnPointX, float spawnPointY, int damage){
        tmpMousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        tmpUnprojectedPos = camera.unproject(tmpMousePos);

        projectileList.add(new Projectile(
                spawnPointX, spawnPointY, 6, 6, bullet, screen.getWorld(),
                damage, tmpUnprojectedPos)
        );
    }


    public List<Projectile> getProjectileList() {
        return projectileList;
    }

}
