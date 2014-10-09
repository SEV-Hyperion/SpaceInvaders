package com.youtube.invaders.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.yutube.invaders.camera.OrthoCamera;

public class GameOverScreen extends Screen {
	private OrthoCamera camera;
	private Texture texture;
	protected Sprite sprite;

	public GameOverScreen(boolean won) {

		if (won) {
			texture = TextureManager.PLAYER;
			TextureRegion AR = (TextureManager.instance.atlas
					.findRegion("player"));
			sprite = new Sprite(AR);
		} else {

			texture = TextureManager.ENEMY0;
			TextureRegion AR = (TextureManager.instance.atlas.findRegion("enemy3"));
			 sprite = new Sprite(AR);
		}
		MainGame.score=0;
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
		// entityManager.render(sb);
		//sb.draw(texture, MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
		sb.draw(sprite, MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
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
