package com.youtube.invaders.entity.gui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.entity.Entity;


// TODO add texture
public class FireBall extends Entity {

	public FireBall(Vector2 pos) {
		super(TextureManager.FIREBALL, pos, new Vector2(0, 5));
		TextureRegion AR = (TextureManager.instance.atlas.findRegion("fireball"));
		sprite = new Sprite(AR);
	}

	@Override
	public void update() {
		pos.add(direction);
	}

	public boolean checkEnd() {
		if (pos.y > MainGame.HEIGHT + TextureManager.FIREBALL.getHeight())
			return true;
		return false;

	}
}
