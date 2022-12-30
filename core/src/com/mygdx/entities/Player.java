package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.helper.ContactType;
import com.mygdx.game.ProjectileManager;

import static com.mygdx.helper.Constant.PPM;

public class Player extends GameEntity{
    private final Texture character;
    private ProjectileManager projectileManager;
    private boolean keyDown;
    private final Timer timer = new Timer();
    private final Task task = new Task() {
        @Override
        public void run() {
            keyDown = false;
        }
    };


    public Player(float width, float height, Body body){
        super(width, height, body);
        this.speed = 4f*PPM;
        character = new Texture(Gdx.files.internal("topdown_shooter/characters/1.png"));
        keyDown = false;
    }

    @Override
    public void update() {
        x = body.getPosition().x;
        y = body.getPosition().y;

        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(character, body.getPosition().x - 8, body.getPosition().y - 8);
    }
    private void checkUserInput() {
        velX = 0;
        velY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            velY = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY = -1;
        }
        body.setLinearVelocity(velX*speed, velY*speed);

        if(Gdx.input.isTouched() && !keyDown){
            projectileManager.createProjectile();
            keyDown = true;
            timer.scheduleTask(task, 0.5f);
        }
    }

    public void setProjectileManager(ProjectileManager projectileManager) {
        this.projectileManager = projectileManager;
    }
}
