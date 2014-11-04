package com.youtube.invaders.entity.gui.projectiles;

import com.badlogic.gdx.math.Vector2;

public class FireBall extends Projectile {
	private static String path = "fireball.png";
	private static int width = 12;
	private static int height = 14;
	private static int frameDuration = 1;

	public FireBall(Vector2 pos) {
		super(pos, new Vector2(0, 5));
		animation = loadAnimation(path, width, height, frameDuration);
		currentFrame = animation.getKeyFrame(0, true);
	}

}
