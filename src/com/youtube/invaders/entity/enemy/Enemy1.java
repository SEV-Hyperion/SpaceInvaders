package com.youtube.invaders.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;

public class Enemy1 extends Enemy {
	// ==============================================================
	// Texture Atlas Management
	// =============================================================
	// public static Texture texture;

	private float speed = 5;
	public Enemy1(Vector2 pos, Vector2 direction, int type) {

		super( pos, direction, type);
		TextureRegion AR = (TextureManager.instance.atlas.findRegion("enemy1"));
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
	private  Texture getTextureEnemy() {

		TextureRegion AR = (TextureManager.instance.atlas.findRegion("enemy3"));
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
