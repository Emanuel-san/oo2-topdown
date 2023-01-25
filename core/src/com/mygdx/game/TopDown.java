package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.helper.Constant;

public class TopDown extends Game {

	private int widthScreen, heightScreen;
	private OrthographicCamera camera;
	private AssetManager assetManager;


	/**
	 * Top-down shooter/ tower defense, main purpose of the game was to learn libGdx and Box2D so gameplay in itself is
	 * lacking. Made during CS course OOP2 at Högsklan på Åland.
	 * @author Emanuel Sanchez
	 */
	@Override
	public void create () {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, widthScreen/Constant.PPM, heightScreen/Constant.PPM); //Set camera zoom here eg. screenwidth/float
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
