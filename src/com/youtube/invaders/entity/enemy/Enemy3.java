package com.youtube.invaders.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.TextureManager;

public class Enemy3 extends Enemy {
	// ==============================================================
	// Texture Atlas Management
	// =============================================================
	// public static Texture texture;

	public Enemy3(Vector2 pos, Vector2 direction, int type) {

		super( pos, direction, type);
		AR = (TextureManager.instance.atlas.findRegion("enemy3"));
		sprite = new Sprite(AR);
		speed = 3;
	}

}
