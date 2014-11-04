package com.youtube.invaders.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.entity.Entity;
import com.youtube.invaders.screen.GameScreen;

/**
 * Esqueleto base para crear enemigos
 * 
 * @author Daniel
 *
 */
public abstract class Enemy extends Entity {
	// ==============================================================
	// Texture Atlas Management
	// =============================================================

	/**
	 * Velocidad de movimiento del enemigo. Por defecto: 1.
	 */
	protected float speed = 1;

	private String path = "enemy0.png";
	private static int width = 43;
	private static int height = 29;
	private static int frameDuration = 1;
	private Animation enemyAnimation = null;

	/**
	 * Constructor de enemigos.
	 * 
	 * @param pos
	 *            Posicion inicial del enemigo
	 * @param direction
	 *            Direccion en que se mueve el enemigo
	 */
	public Enemy(Vector2 pos, Vector2 direction) {

		super(pos, direction);

		enemyAnimation = loadAnimation(path, width, height, frameDuration);
		currentFrame = enemyAnimation.getKeyFrame(0, true);

	}

	public Enemy(Vector2 pos, Vector2 direction, String path) {
		super(pos, direction);
		this.path = path;
		enemyAnimation = loadAnimation(path, width, height, frameDuration);
		currentFrame = enemyAnimation.getKeyFrame(0, true);
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

	/**
	 * Método que genera la direccion en que se va a mover el enemigo. Por
	 * defecto, los enemigos se dirigen inexorablemente hacia el personaje
	 * jugador.
	 * 
	 * @return Vector con la nueva direccion del enemigo.
	 */
	protected Vector2 getNewDirection() {
		Vector2 playerPos = GameScreen.getEntityManager().getAnimatedPlayer()
				.getPosition();
		float playerX = 0;
		float playerY = 0;
		if (playerPos != null) {
			playerX = playerPos.x;
			playerY = playerPos.y;
		}

		float newX = playerX - pos.x;
		float newY = playerY - pos.y;
		Vector2 newDirection = new Vector2(newX, newY);
		newDirection.nor();
		return newDirection;
	}

}
