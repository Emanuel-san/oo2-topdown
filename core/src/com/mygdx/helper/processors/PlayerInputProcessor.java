package com.mygdx.helper.processors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.entities.Player;
import com.mygdx.game.GameScreen;
import com.mygdx.game.TowerPlacer;
import com.mygdx.helper.Constant;

public class PlayerInputProcessor implements InputProcessor {
    private final Player player;
    private final GameScreen screen;
    private OrthographicCamera camera;
    boolean leftMouseDown, rightMouseDown;
    boolean towerPlacementActive = false;

    private int mousePosX = 0, mousePosY = 0;

    private final Vector3 mousePos;



    public PlayerInputProcessor(GameScreen screen, Player player, OrthographicCamera camera){
        this.camera = camera;
        this.screen = screen;
        this.player = player;
        mousePos = new Vector3();
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D) {
            player.setPlayerVelocityX(1);
        }
        else if (keycode == Input.Keys.A) {
            player.setPlayerVelocityX(-1);
        }
        else if (keycode == Input.Keys.W){
            player.setPlayerVelocityY(1);
        }
        else if (keycode == Input.Keys.S) {
            player.setPlayerVelocityY(-1);
        }
        if (keycode == Input.Keys.Q) {
            if(towerPlacementActive){
                towerPlacementActive = false;
            }
            else{
                towerPlacementActive = true;
            }

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D) {
            player.setPlayerVelocityX(-1);
        } else if (keycode == Input.Keys.A) {
            player.setPlayerVelocityX(1);
        } else if (keycode == Input.Keys.W) {
            player.setPlayerVelocityY(-1);
        } else if (keycode == Input.Keys.S) {
            player.setPlayerVelocityY(1);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT && towerPlacementActive) {
            if(screen.getPlacer().placeTower()){
                towerPlacementActive = false;
            }
        }
        else if(button == Input.Buttons.LEFT){
            leftMouseDown = true;
        }
        else if (button == Input.Buttons.RIGHT) {
            rightMouseDown = true;
            towerPlacementActive = false;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            leftMouseDown = false;
        } else if (button == Input.Buttons.RIGHT) {
            rightMouseDown = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mousePosX = screenX;
        mousePosY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePosX = screenX;
        mousePosY = screenY;
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean isLeftMouseDown() {
        return leftMouseDown;
    }

    public boolean isTowerPlacementActive() {
        return towerPlacementActive;
    }
    public Vector3 getUnprojectedMousePos() {
        return camera.unproject(mousePos.set(mousePosX, mousePosY, 0));
    }
}
