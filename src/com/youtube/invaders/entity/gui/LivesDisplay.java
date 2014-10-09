package com.youtube.invaders.entity.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.entity.Entity;
import com.youtube.invaders.entity.EntityManager;
import com.yutube.invaders.camera.OrthoCamera;

public class LivesDisplay extends Entity {

	private EntityManager entityManager;

	private int lives;
	private OrthoCamera camera;

	public LivesDisplay(Vector2 pos, Vector2 direction,
			EntityManager entityManager, OrthoCamera camera) {
		super(TextureManager.PLAYER, pos, direction);
		this.entityManager = entityManager;
		this.camera = camera;
		TextureRegion AR = (TextureManager.instance.atlas.findRegion("player"));
		sprite = new Sprite(AR);
		lives = MainGame.LIVES_START;
	}

	@Override
	public void update() {

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
			if (this.lives-2 < i)
				sb.setColor(0.5f+ (0.15f*(MainGame.LIVES_START-(this.lives-1))), 0.0f, 0.0f, 0.5f);
			sb.draw(sprite, MainGame.WIDTH - (50 * (i + 1)), pos.y);
			sb.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}

	}
}
