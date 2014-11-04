package com.youtube.invaders.entity.gui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.entity.Entity;

public class Missile extends Entity {

	private static String path = "missile.png";
	private static int width = 12;
	private static int height = 25;
	private static int frameDuration = 1;
	private Animation missileAnimation = null;

	public Missile(Vector2 pos) {
		super(pos, new Vector2(0, 5));

		missileAnimation = loadAnimation(path, width, height, frameDuration);
		currentFrame = missileAnimation.getKeyFrame(0, true);
	}

	@Override
	public void update() {

		pos.add(direction);
	}

	public boolean checkEnd() {
		if (pos.y > MainGame.HEIGHT
				+ missileAnimation.getKeyFrame(stateTime).getRegionHeight())
			return true;
		return false;

	}

}
