package com.youtube.invaders.entity.gui.projectiles;

import com.badlogic.gdx.math.Vector2;

public class FireBall extends Projectile {
	private static String path = "fireball.png";
	private static int width = 12;
	private static int height = 14;
	private static int frameDuration = 1;

	/**
	 * 
	 * @param pos la direccion de movimiento del proyectil
	 */
	public FireBall(Vector2 pos, Vector2 direccion) {
		super(pos, direccion);
		animation = loadAnimation(path, width, height, frameDuration);
		currentFrame = animation.getKeyFrame(0, true);
	}

}
