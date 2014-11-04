package com.youtube.invaders.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.entity.Entity;
import com.youtube.invaders.entity.EntityManager;
import com.youtube.invaders.entity.gui.CustomTouchpad;
import com.youtube.invaders.entity.gui.FireBall;
import com.youtube.invaders.entity.gui.Missile;
import com.yutube.invaders.camera.OrthoCamera;

public class AnimatedPlayer extends Entity {
	// =======================================================================
	// It WAS basicallly , a Player
	// =======================================================================
	private final int CHARACTER_SPEED = 250;
	private EntityManager entityManager;
	private long lastFire;

	private OrthoCamera camera;

	/**
	 * Conjunto de frames que forman la secuencia animada del personaje
	 * moviendose hacia "arriba"/"derecha".
	 */
	private Animation playerUpAnimation;

	private Animation playerDownAnimation;

	/**
	 * Tamaño del caragador
	 */
	private static int MAGAZINE_SIZE = 5;
	/**
	 * Municion actual
	 */
	private int currentMagazine = 5;

	/**
	 * Tiempo de recarga, en milisegundos
	 */
	private static int RELOAD_TIME = 2000;

	// animation frames data
	String[] pathsUpAnimation = { "playerOneUp_1.png", "playerOneUp_2.png" };
	String[] pathsDownAnimation = { "playerOneDown_1.png",
			"playerOneDown_2.png" };
	int width = 32;
	int height = 32;
	float frameDuration = 0.500f;// original 0,025f

	// touchpad items
	private Touchpad touchpad;
	private Stage stage;
	private SpriteBatch batch;

	/**
	 * Constructor de AnimatedPlayer (personajes animado).
	 * 
	 * @param pos
	 *            Posicion original del personaje
	 * @param direction
	 *            Direccion en que se esta moviendo el personaje. (0, 0) ->
	 *            quieto
	 * @param entityManager
	 *            EntityManager que gestionará al personaje.
	 * @param camera
	 *            Cámara asociada al personaje
	 */
	public AnimatedPlayer(Vector2 pos, Vector2 direction,
			EntityManager entityManager, OrthoCamera camera) {
		super(pos, direction);
		this.entityManager = entityManager;
		this.camera = camera;

		playerUpAnimation = loadComplexAnimation(pathsUpAnimation, 32, 32,
				frameDuration);

		playerDownAnimation = loadComplexAnimation(pathsDownAnimation, 32, 32,
				frameDuration);

		stateTime = 0f;

		currentFrame = playerUpAnimation.getKeyFrame(stateTime, true);

		createTouchpad(camera);

	}

	/**
	 * @param camera
	 */
	private void createTouchpad(OrthoCamera camera) {
		touchpad = new CustomTouchpad(camera);
		batch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true, batch);
		stage.addActor(touchpad);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void update() {

		pos.add(direction);

		// camera.update();

		setDirection(touchpad.getKnobPercentX() * CHARACTER_SPEED,
				touchpad.getKnobPercentY() * CHARACTER_SPEED);

		stateTime += Gdx.graphics.getDeltaTime(); // #15

		if (currentFrame == null) {
			currentFrame = playerUpAnimation.getKeyFrame(stateTime, true); // #16
		}
		if (touchpad.getKnobPercentY() < 0) {
			// face down
			currentFrame = playerDownAnimation.getKeyFrame(stateTime, true);
		}
		if (touchpad.getKnobPercentY() > 0) {
			// face up
			currentFrame = playerUpAnimation.getKeyFrame(stateTime, true);
		}
		// currentFrame = playerAnimation.getKeyFrame(stateTime, true); // #16

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			// lose ammo on fire
			if (System.currentTimeMillis() - lastFire >= 500
					&& currentMagazine > 0) {
				entityManager.addEntity(new Missile(new Vector2(pos.x
						+ TextureManager.PLAYER.getWidth() / 4, pos.y)));
				lastFire = System.currentTimeMillis();
				currentMagazine--;
			} else {
				// auto reload
				if (System.currentTimeMillis() - lastFire >= RELOAD_TIME
						&& currentMagazine == 0) {
					currentMagazine = MAGAZINE_SIZE;
				}
			}
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			// disparo secundario
			// lose ammo on fire
			if (System.currentTimeMillis() - lastFire >= 500
					&& currentMagazine > 0) {
				entityManager.addEntity(new FireBall(new Vector2(pos.x
						+ TextureManager.PLAYER.getWidth() / 4, pos.y)));
				lastFire = System.currentTimeMillis();
				currentMagazine--;
			} else {
				// auto reload
				if (System.currentTimeMillis() - lastFire >= RELOAD_TIME
						&& currentMagazine == 0) {
					currentMagazine = MAGAZINE_SIZE;
				}
			}
		}

		else if (Gdx.input.isKeyPressed(Keys.R)) {
			// manual reload
			int timeToReload = (RELOAD_TIME / MAGAZINE_SIZE) * currentMagazine;
			long initialTime = System.currentTimeMillis();
			while (System.currentTimeMillis() < (initialTime + timeToReload)) {
				// do nothing
				// TODO it does <i>literally nothing</i>, so it stops the main
				// thread for its duration
			}
			currentMagazine = MAGAZINE_SIZE;
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.draw(currentFrame, pos.x, pos.y);
		camera.position.set(pos.x, pos.y, 0);
		camera.update();
		touchpad.draw(sb, 0);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		// sb.draw(sprite, pos.x, pos.y);
		// sb.draw(texture, pos.x, pos.y);
		// sb.draw(sprite, pos.x, pos.y);
		// sb.draw(texture, pos.x, pos.y);
	}

}