package com.youtube.invaders.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.entity.Entity;
import com.youtube.invaders.screen.GameScreen;

public abstract class Enemy extends Entity {
	// ==============================================================
	// Texture Atlas Management
	// =============================================================
	// public static Texture texture;

	protected float speed = 1;
	protected TextureRegion AR;
	
	public Enemy(Vector2 pos, Vector2 direction, int type) {

		super(TextureManager.ENEMY0, pos, direction, type);
		AR = (TextureManager.instance.atlas.findRegion("enemy"));
		sprite = new Sprite(AR);

	}

	@Override
	public void update() {
		pos.add(getNewDirection().mul(speed));
		if (pos.y <= -TextureManager.ENEMY0.getHeight()) {
			float x = MathUtils.random(0, MainGame.WIDTH
					- TextureManager.ENEMY0.getWidth());

			pos.set(x, MathUtils.random(MainGame.HEIGHT, MainGame.HEIGHT * 2));
		}

	}

	protected Vector2 getNewDirection() {
		Vector2 playerPos = GameScreen.getEntityManager().getAnimatedPlayer().getPosition();
		float playerX=0;
		float playerY=0;
		if(playerPos!= null){
			playerX = playerPos.x;
			playerY = playerPos.y;
		}
		
		float newX = playerX-pos.x;
		float newY = playerY-pos.y;
		Vector2 newDirection = new Vector2(newX, newY);
		newDirection.nor();
		return newDirection;
	}

	private  Texture getTextureEnemy() {
		sprite = new Sprite(AR);
		//return s.getTexture();

		switch (type) {
		case 0:
			return TextureManager.ENEMY0;
		case 1:
			return TextureManager.ENEMY1;
		case 2:
			return TextureManager.ENEMY2;
		case 3:
			return TextureManager.ENEMY3;
		default:
			return TextureManager.ENEMY0;
		}

		/*
		 * TextureRegion AR =
		 * (TextureManager.instance.atlas.findRegion("missile")); Sprite s = new
		 * Sprite(AR); return s.getTexture();
		 */

		/*
		 * System.out.println("======================");
		 * System.out.println(AR.getRegionWidth());
		 * System.out.println(AR.getRegionHeight());
		 * 
		 * 
		 * System.out.println(AR.getTexture().getWidth());
		 * System.out.println(AR.getTexture().getHeight());
		 * 
		 * System.out.println(s.getTexture().getWidth());
		 * System.out.println(s.getTexture().getHeight());
		 * 
		 * return s.getTexture();
		 * 
		 * //return AR.getTexture();
		 */

	}
}
