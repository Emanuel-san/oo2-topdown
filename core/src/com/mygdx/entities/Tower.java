package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.AI.TowerAI;
import com.mygdx.game.EntityManager;
import com.mygdx.game.GameScreen;
import com.mygdx.helper.BodyHelper;
import com.mygdx.helper.EntityType;

public class Tower extends GameEntity{
    private Texture texture;
    private TowerAI ai;
    private Vector2 direction;

    public Tower(float x, float y, float width, float height, World world, Texture texture, GameScreen screen) {
        super(x, y, width, height);
        direction = new Vector2();
        this.texture = texture;
        body = BodyHelper.createPolygonBody(x,y,width,height,true, world, this, EntityType.TOWER);
        body.getFixtureList().first().setSensor(true);
        createSensorFixture(150);
        ai = new TowerAI(screen.getEntityManager(), screen.getPlayerBase(), this);
    }

    @Override
    public void update() {
        ai.updateTarget(direction);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 12, body.getPosition().y - 8);
    }

    private void shootProjectile(){
        if(ai.isGotTarget()){

        }
    }

    private void createSensorFixture(float radius){
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    public TowerAI getTowerAI() {
        return ai;
    }
}
