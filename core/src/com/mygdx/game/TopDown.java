package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class TopDown extends Game {

	private int widthScreen, heightScreen;
	private OrthographicCamera camera;
	private AssetManager assetManager;


	
	@Override
	public void create () {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, widthScreen, heightScreen); //Set camera zoom here eg. screenwidth/float
		this.assetManager = new AssetManager();
		//setScreen(new GameScreen(camera));
		setScreen(new LoadingScreen(this));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
