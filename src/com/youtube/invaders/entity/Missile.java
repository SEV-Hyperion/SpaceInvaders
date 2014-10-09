package com.youtube.invaders.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;

public class Missile extends Entity {

	public Missile(Vector2 pos) {
		super(TextureManager.MISSILE, pos, new Vector2(0, 5));
		TextureRegion AR = (TextureManager.instance.atlas.findRegion("missile"));
		sprite = new Sprite(AR);

	}

	@Override
	public void update() {

		pos.add(direction);
	}

	public boolean checkEnd() {
		if (pos.y > MainGame.HEIGHT + TextureManager.MISSILE.getHeight())
			return true;
		return false;

	}

}
