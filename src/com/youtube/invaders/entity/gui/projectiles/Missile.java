package com.youtube.invaders.entity.gui.projectiles;

import com.badlogic.gdx.math.Vector2;

public class Missile extends Projectile {

	private static String path = "missile.png";
	private static int width = 12;
	private static int height = 25;
	private static int frameDuration = 1;

	public Missile(Vector2 pos) {
		super(pos, new Vector2(0, 5));

		animation = loadAnimation(path, width, height, frameDuration);
		currentFrame = animation.getKeyFrame(0, true);
	}

}
