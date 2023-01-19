package com.mygdx.helper.processors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.entities.Player;
import com.mygdx.game.GameScreen;

public class PlayerInputProcessor implements InputProcessor {
    Player player;
    GameScreen screen;
    boolean leftMouseDown, rightMouseDown;


    public PlayerInputProcessor(GameScreen screen, Player player){
        this.screen = screen;
        this.player = player;
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
            screen.getEntityManager().createTower(player.getBody().getPosition().x + 32, player.getBody().getPosition().y + 32);
        }
        if (keycode == Input.Keys.E) {
            screen.getEntityManager().createEnemy(player.getBody().getPosition().x + 32, player.getBody().getPosition().y + 32);
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
        if (button == Input.Buttons.LEFT) {
            leftMouseDown = true;
        } else if (button == Input.Buttons.RIGHT) {
            rightMouseDown = true;
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
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean isLeftMouseDown() {
        return leftMouseDown;
    }
}
