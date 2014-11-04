package com.youtube.invaders.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.entity.EntityManager;
import com.yutube.invaders.camera.OrthoCamera;

public class GameOverScreen extends Screen {
	private OrthoCamera camera;
	// private Texture texture;
	private TextureRegion texture;

	public GameOverScreen(boolean won) {

		if (won) {
			texture = EntityManager.getAnimatedPlayer().getCurrentFrame();
		} else {

			texture = EntityManager.getEnemies().get(0).getCurrentFrame();
		}
		MainGame.score = 0;
	}

	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();

	}

	@Override
	public void update() {

		camera.update();
	}

	@Override
	public void render(SpriteBatch sb) {

		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(texture, MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
		sb.end();
	}

	@Override
	public void resize(int width, int height) {

		camera.resize();
	}

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

}
