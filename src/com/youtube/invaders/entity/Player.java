package com.youtube.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.yutube.invaders.camera.OrthoCamera;

public class Player extends Entity {

	private EntityManager entityManager;
	private long lastFire;
	private OrthoCamera camera;

	public Player(Vector2 pos, Vector2 direction, EntityManager entityManager,
			OrthoCamera camera) {
		super(TextureManager.PLAYER, pos, direction);
		this.entityManager = entityManager;
		this.camera = camera;
		TextureRegion AR = (TextureManager.instance.atlas.findRegion("player"));
		sprite = new Sprite(AR);
	}

	@Override
	public void update() {

		pos.add(direction);

		int dir = 0;

		if (Gdx.input.isTouched()) {
			Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(),
					Gdx.input.getY());
			if (touch.x > MainGame.WIDTH / 2)
				dir = 2;

			else
				dir = 1;
		}

		if (Gdx.input.isKeyPressed(Keys.A) || dir == 1)
			setDirection(-300, 0);
		else if (Gdx.input.isKeyPressed(Keys.D) || dir == 2)
			setDirection(300, 0);
		else
			setDirection(0, 0);

		// if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			if (System.currentTimeMillis() - lastFire >= 500) {

				entityManager.addEntity(new Missile(new Vector2(pos.x
						+ TextureManager.PLAYER.getWidth() / 4, pos.y)));
				lastFire = System.currentTimeMillis();
			}
		}
	}

}
