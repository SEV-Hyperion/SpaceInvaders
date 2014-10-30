package com.youtube.invaders;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.youtube.invaders.screen.GameScreen;
import com.youtube.invaders.screen.ScreenManager;

public class MainGame implements ApplicationListener {

	private SpriteBatch batch;
	public static int WIDTH = 480, HEIGHT = 800;

	public static boolean debugMode = true;
	// =========================================================================
	public static boolean drawDebugOutline = false; // Texture atlas Management
	public static boolean rebuildAtlas = true; // Texture atlas Management
	public static final String TEXTURE_ATLAS_OBJECTS = "/images/spaceinvaders.pack";
	// =========================================================================

	// =========================================================================
	// GUI Management
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	public static int score =0;

	// =========================================================================
	// Lives Management
	public static final int LIVES_START = 5;
	public int lives =0;
	// =========================================================================

	@Override
	public void create() {

		// =================================================
		// Texture Atlas Management
		TextureManager.instance.init(new AssetManager());
		// =================================================

		batch = new SpriteBatch();
		ScreenManager.setScreen(new GameScreen());

	}

	@Override
	public void dispose() {
		if (ScreenManager.getCurrentscreen() != null)
			ScreenManager.currentScreen.dispose();
		batch.dispose();

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.

		if (ScreenManager.getCurrentscreen() != null)
			ScreenManager.getCurrentscreen().update();

		if (ScreenManager.getCurrentscreen() != null)
			ScreenManager.getCurrentscreen().render(batch);
		// batch.setProjectionMatrix(camera.combined);
		batch.begin();
			// Nothing  right now....
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		if (ScreenManager.getCurrentscreen() != null)
			ScreenManager.getCurrentscreen().resize(width, height);
	}

	@Override
	public void pause() {

		if (ScreenManager.getCurrentscreen() != null)
			ScreenManager.getCurrentscreen().pause();
	}

	@Override
	public void resume() {

		if (ScreenManager.getCurrentscreen() != null)
			ScreenManager.getCurrentscreen().resume();
	}
}
