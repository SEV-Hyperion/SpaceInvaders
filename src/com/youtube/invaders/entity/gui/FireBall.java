package com.youtube.invaders.entity.gui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.entity.Entity;

public class FireBall extends Entity {
	private static String path = "fireball.png";
	private static int width = 12;
	private static int height = 25;
	private static int frameDuration = 1;
	private Animation fireballAnimation = null;

	public FireBall(Vector2 pos) {
		super(pos, new Vector2(0, 5));
		fireballAnimation = loadAnimation(path, width, height, frameDuration);
		currentFrame = fireballAnimation.getKeyFrame(0, true);
	}

	@Override
	public void update() {
		pos.add(direction);
	}

	public boolean checkEnd() {
		if (pos.y > MainGame.HEIGHT
				+ fireballAnimation.getKeyFrame(stateTime).getRegionHeight())
			return true;
		return false;

	}
}
