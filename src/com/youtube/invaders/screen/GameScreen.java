package com.youtube.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.entity.EntityManager;
import com.youtube.invaders.entity.Player;
import com.yutube.invaders.camera.OrthoCamera;

public class GameScreen extends Screen {

	private OrthoCamera camera;
	private OrthographicCamera cameraGUI;
	// private OrthographicCamera cameraFPS;

	public Sound gameLoopSound;

	private EntityManager entityManager;

	@Override
	public void create() {
		// System.out.println("Created");
		camera = new OrthoCamera();
		entityManager = new EntityManager(100, camera);
		gameLoopSound = Gdx.audio.newSound(Gdx.files
				.internal("sounds/gameloopsound.mp3"));
		gameLoopSound.loop();

		cameraGUI = new OrthographicCamera(MainGame.VIEWPORT_GUI_WIDTH,
				MainGame.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true);
		cameraGUI.update();

	}

	@Override
	public void update() {

		camera.update();
		cameraGUI.update();
		entityManager.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		// System.out.println("Render");
		sb.setProjectionMatrix(camera.combined);
		sb.enableBlending(); // Enable blending in the game screen
		sb.begin();
		entityManager.render(sb);
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
		// System.out.println("Resized");
		camera.resize();

		cameraGUI.viewportHeight = MainGame.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = MainGame.VIEWPORT_GUI_HEIGHT / (float) height
				* (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2,
				cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();

	}

	@Override
	public void dispose() {
		System.out.println("Disposed");
		gameLoopSound.dispose();

	}

	@Override
	public void pause() {
		System.out.println("Paused");
		gameLoopSound.stop();

	}

	@Override
	public void resume() {
		System.out.println("Resumed");
		gameLoopSound.loop();
	}

}
