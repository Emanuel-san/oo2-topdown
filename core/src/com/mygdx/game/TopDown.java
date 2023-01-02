package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TopDown extends Game {

	private int widthScreen, heightScreen;
	private OrthographicCamera camera;
	private InputManager inputManager;


	
	@Override
	public void create () {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, widthScreen/3f, heightScreen/3f);
		this.inputManager = new InputManager();
		Gdx.input.setInputProcessor(inputManager);
		setScreen(new GameScreen(camera, inputManager));
	}

}
