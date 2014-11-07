package com.youtube.invaders.entity.gui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.camera.OrthoCamera;
import com.youtube.invaders.entity.Entity;
import com.youtube.invaders.entity.EntityManager;

public class LivesDisplay extends Entity {

	private EntityManager entityManager;

	private int lives;
	private OrthoCamera camera;
	private Animation livesAnimation;
	private static String path = "playerOneUp_1.png";
	private static int width = 12;
	private static int height = 25;
	private static int frameDuration = 1;

	public LivesDisplay(Vector2 pos, Vector2 direction,
			EntityManager entityManager, OrthoCamera camera) {
		super(pos, direction);
		this.entityManager = entityManager;
		this.camera = camera;

		livesAnimation = loadAnimation(path, width, height, frameDuration);

		currentFrame = livesAnimation.getKeyFrame(0, true);
		lives = MainGame.LIVES_START;
	}

	@Override
	public void update() {
//		System.out.println("Current lives: "+lives);
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int li) {
		lives = li;
	}

	@Override
	public void render(SpriteBatch sb) {
		for (int i = 0; i < MainGame.LIVES_START; i++) {
			if (this.lives - 2 < i)
				sb.setColor(
						0.5f + (0.15f * (MainGame.LIVES_START - (this.lives - 1))),
						0.0f, 0.0f, 0.5f);
			sb.draw(currentFrame, MainGame.WIDTH - (50 * (i + 1)), pos.y);
			sb.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			camera.update();
		}

	}
}
