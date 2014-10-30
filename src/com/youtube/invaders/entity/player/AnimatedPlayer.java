package com.youtube.invaders.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.entity.Entity;
import com.youtube.invaders.entity.EntityManager;
import com.youtube.invaders.entity.gui.FireBall;
import com.youtube.invaders.entity.gui.Missile;
import com.yutube.invaders.camera.OrthoCamera;

public class AnimatedPlayer extends Entity {
	// =======================================================================
	// It is basicallly , a Player
	// =======================================================================
	private final int CHARACTER_SPEED = 250;
	private EntityManager entityManager;
	private long lastFire;
	private Stage stage;
	private SpriteBatch batch;
	private Touchpad touchpad;
	private TouchpadStyle touchpadStyle = new TouchpadStyle();
	private Skin touchpadSkin = new Skin();
	private Drawable touchBackground;
	private Drawable touchKnob;

	private OrthoCamera camera;

	private static final int FRAME_COLS = 6;
	/**
	 * 
	 */
	private static final int FRAME_ROWS = 5;

	/**
	 * Conjunto de frames que forman una secuencia animada del personaje
	 */
	Animation playerUpAnimation;
	/**
	 * Conjunto fusionado de texturas que forman el personaje. <br>
	 * ¿Podría liberarse después de ser usado?
	 */
	Texture playerSheet;
	/**
	 * Conjunto de frames individuales <br>
	 * ¿Podría liberarse después de ser usado?
	 */
	TextureRegion[] playerFrames;
	// SpriteBatch spriteBatch;
	/**
	 * Frame actual del personaje
	 */
	TextureRegion currentFrame;
	/**
	 * Indica el tiempo subjetivo del estado del personaje. Sirve para saber que
	 * frame pintar en ese momento,
	 */
	float stateTime;
	/**
	 * Tamaño del caragador
	 */
	private static int MAGAZINE_SIZE = 5;
	/**
	 * Municion actual
	 */
	private int currentMagazine = 5;
	
	private Animation playerDownAnimation;
	/**
	 * Tiempo de recarga, en milisegundos
	 */
	private static int RELOAD_TIME = 2000;

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
		super(TextureManager.PLAYER, pos, direction);
//		super(null, pos, direction);
		this.entityManager = entityManager;
		this.camera = camera;
		TextureRegion AR = (TextureManager.instance.atlas.findRegion("player"));
		sprite = new Sprite(AR);
		// ============================================================================
		// Animation
//		playerSheet = new Texture(Gdx.files.internal("fly_animation_sheet.png"));
//		playerSheet = new Texture(Gdx.files.internal("CharacterSinFondo.png"));
//		TextureRegion[][] tmp = TextureRegion.split(playerSheet,
//				playerSheet.getWidth() / FRAME_COLS, playerSheet.getHeight()
//						/ FRAME_ROWS);
//		playerFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
//		TextureRegion[][] tmp = TextureRegion.split(playerSheet,
//				32, 32);
//		playerFrames = new TextureRegion[1];
//		int index = 0;
//		for (int i = 0; i < FRAME_ROWS; i++) {
//			for (int j = 0; j < FRAME_COLS; j++) {
//				playerFrames[index++] = tmp[i][j];
//			}
//		}
		
		
		// personaje hacia arriba
		playerFrames = new TextureRegion[2];
		//imagen 1
		playerSheet = new Texture(Gdx.files.internal("playerOneUp_1.png"));
		TextureRegion[][] tmp = TextureRegion.split(playerSheet,
				32, 32);
		int index = 0;	
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				playerFrames[index++] = tmp[i][j];
			}
		}
		
		//imagen 2
		playerSheet = new Texture(Gdx.files.internal("playerOneUp_2.png"));
		tmp = TextureRegion.split(playerSheet,
				32, 32);
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				playerFrames[index++] = tmp[i][j];
			}
		}
		playerUpAnimation = new Animation(0.500f, playerFrames);
		
		
		// personaje hacia abajo
		playerFrames = new TextureRegion[2];
		// imagen 1
		playerSheet = new Texture(Gdx.files.internal("playerOneDown_1.png"));
		tmp = TextureRegion.split(playerSheet,
				32, 32);
		index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				playerFrames[index++] = tmp[i][j];
			}
		}
		playerSheet = new Texture(Gdx.files.internal("playerOneDown_2.png"));
		tmp = TextureRegion.split(playerSheet,
				32, 32);
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				playerFrames[index++] = tmp[i][j];
			}
		}
		playerDownAnimation = new Animation(0.500f, playerFrames);// original 0.025f
		
		
		
		stateTime = 0f;

		currentFrame = playerUpAnimation.getKeyFrame(stateTime, true);
		generateJoystick(camera);

	}

	/**
	 * Incluye un joystick que será seguido por la cámara del personaje.
	 * 
	 * @param camera
	 *            Cámara del personaje
	 */
	private void generateJoystick(OrthoCamera camera) {

		batch = new SpriteBatch();
		// Create camera
		float aspectRatio = (float) Gdx.graphics.getWidth()
				/ (float) Gdx.graphics.getHeight();
		camera.setToOrtho(false, 10f * aspectRatio, 10f);

		// Create a touchpad skin
		touchpadSkin = new Skin();
		// Set background image
		touchpadSkin.add("touchBackground",
				new Texture(Gdx.files.internal("touchBackground.png")));
		// Set knob image
		touchpadSkin.add("touchKnob",
				new Texture(Gdx.files.internal("touchKnob.png")));
		// Create TouchPad Style
		touchpadStyle = new TouchpadStyle();
		// Create Drawable's from TouchPad skin
		touchBackground = touchpadSkin.getDrawable("touchBackground");
		touchKnob = touchpadSkin.getDrawable("touchKnob");
		// Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		// Create new TouchPad with the created style
		touchpad = new Touchpad(10, touchpadStyle);
		// setBounds(x,y,width,height)
		touchpad.setBounds(15, 15, 200, 200);

		// Create a Stage and add TouchPad
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true, batch);
		stage.addActor(touchpad);
		Gdx.input.setInputProcessor(stage);

		// ============================================================================
	}

	@Override
	public void update() {

		pos.add(direction);

		// camera.update();

		setDirection(touchpad.getKnobPercentX() * CHARACTER_SPEED,
				touchpad.getKnobPercentY() * CHARACTER_SPEED);

		stateTime += Gdx.graphics.getDeltaTime(); // #15

//		if (currentFrame == null){
//			currentFrame = playerUpAnimation.getKeyFrame(stateTime, true); // #16
//		}
		if (touchpad.getKnobPercentY() < 0) {
			//face down
			currentFrame = playerDownAnimation.getKeyFrame(stateTime, true);
		}
		if(touchpad.getKnobPercentY() >0){
			// face up
			currentFrame = playerUpAnimation.getKeyFrame(stateTime, true);
		}
//		currentFrame = playerAnimation.getKeyFrame(stateTime, true); // #16

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
//		sb.draw(sprite, pos.x, pos.y);
//		sb.draw(texture, pos.x, pos.y);
		// sb.draw(sprite, pos.x, pos.y);
		// sb.draw(texture, pos.x, pos.y);
	}

}