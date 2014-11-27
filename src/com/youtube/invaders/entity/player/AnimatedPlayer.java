package com.youtube.invaders.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.camera.OrthoCamera;
import com.youtube.invaders.entity.Entity;
import com.youtube.invaders.entity.EntityManager;
import com.youtube.invaders.entity.gui.CustomTouchpad;
import com.youtube.invaders.entity.gui.projectiles.FireBall;
import com.youtube.invaders.entity.gui.projectiles.Missile;

public class AnimatedPlayer extends Entity {
	// =======================================================================
	// It WAS basicallly , a Player
	// =======================================================================
	private final int CHARACTER_SPEED = 250;
	private EntityManager entityManager;
	private long lastFire;
	private static final Vector2 DIRECCION_DERECHA = new Vector2(0, 5);
	private static final Vector2 DIRECCION_IZQUIERDA = new Vector2(0, -5);
	private Vector2 direccionProyectil = DIRECCION_DERECHA;

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

		// TODO change how the loadAnimationMethods work. They should use
		// TextureManager instead of loading the texture directly.
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

	// reload support
	boolean reloading = false;
	int timeToReload = 0;
	long initialTime = 0;

	@Override
	public void update() {

		pos.add(direction);

		// camera.update();

		setBoundedDirection();

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

		if (reloading) {
			if (System.currentTimeMillis() < (initialTime + timeToReload)) {
				return;
			} else {
				reloading = false;
				currentMagazine = MAGAZINE_SIZE;
				return;
			}

		}

		setProyectileDireccion();

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			// disparo principal.
			// lose ammo on fire.
			disparoPrincipal();

		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			// disparo secundario.
			// lose ammo on fire.
			disparoSecundario();
		}

		else if (Gdx.input.isKeyPressed(Keys.R)) {
			// manual reload
			timeToReload = (RELOAD_TIME / MAGAZINE_SIZE) * currentMagazine;
			initialTime = System.currentTimeMillis();

			reloading = true;
		}
	}

	/**
	 * The player won't leave the map.
	 */
	private void setBoundedDirection() {

		float x = getPosition().x;
		float newX = touchpad.getKnobPercentX() * CHARACTER_SPEED;
		// if (x <= 0) {
		// newX = 1;
		// } else if (x >= MainGame.VIEWPORT_GUI_WIDTH) {
		// newX = - 1;
		// }

		float y = getPosition().y;
		float newY = touchpad.getKnobPercentY() * CHARACTER_SPEED;
		// if (y <= 0){
		// newY = 1;
		// }else if (y >= MainGame.VIEWPORT_GUI_HEIGHT) {
		// newY = -1;
		// }
		setDirection(newX, newY);
	}

	private void disparoPrincipal() {
		if (System.currentTimeMillis() - lastFire >= 500 && currentMagazine > 0) {
			entityManager.addEntity(new Missile(new Vector2(pos.x + width / 4,
					pos.y), direccionProyectil));
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

	private void disparoSecundario() {
		if (System.currentTimeMillis() - lastFire >= 500 && currentMagazine > 0) {

			entityManager.addEntity(new FireBall(new Vector2(pos.x + width / 4,
					pos.y), direccionProyectil));
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

	private void setProyectileDireccion() {
		if (touchpad.getKnobPercentY() < 0) {
			direccionProyectil = DIRECCION_IZQUIERDA;
		}

		if (touchpad.getKnobPercentY() > 0) {
			direccionProyectil = DIRECCION_DERECHA;
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
	}

}