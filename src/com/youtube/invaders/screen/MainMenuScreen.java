package com.youtube.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.camera.OrthoCamera;
import com.youtube.invaders.utils.Conf;

public class MainMenuScreen extends AbstractLevel {
	
	public MainMenuScreen(String level) {
		super(level);
		// TODO Auto-generated constructor stub
	}

	public MainMenuScreen() {
		super("main_menu");
	}

//	private OrthoCamera camera;
//	private OrthographicCamera cameraGUI;
//	public Sound gameLoopSound;
//	private Sprite sprite;

//	@Override
//	public void create() {
//		// TODO Auto-generated method stub
//		Gdx.graphics.setTitle("Main Menu");
//		camera = new OrthoCamera();
//		cameraGUI = new OrthographicCamera(MainGame.VIEWPORT_GUI_WIDTH,
//				MainGame.VIEWPORT_GUI_HEIGHT);
//		cameraGUI.position.set(0, 0, 0);
//		cameraGUI.setToOrtho(true);
//		cameraGUI.update();
//		gameLoopSound = Gdx.audio.newSound(Gdx.files.internal("sounds/majoras-theme.mp3"));
//		gameLoopSound.loop();
//		
//		
//		TextureRegion region = TextureManager.instance.atlas.findRegion("map");
//		sprite = new Sprite(region);
//		
//		
//		sprite.setSize(MainGame.VIEWPORT_GUI_HEIGHT,
//				MainGame.VIEWPORT_GUI_WIDTH);
//		sprite.rotate90(false);
//		sprite.setPosition(0, 0);
//		
//	}
//
//	@Override
//	public void update() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void render(SpriteBatch sb) {
//		
//		Gdx.gl.glClearColor(1, 0, 1, 1);
//		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//		sb.setProjectionMatrix(camera.combined);
//		sb.enableBlending(); // Enable blending in the game screen
//		sb.begin();
//		sprite.draw(sb);
//		sb.end();
//
//	}
//
//	@Override
//	public void resize(int width, int height) {
//		camera.resize();
//
//		cameraGUI.viewportHeight = MainGame.VIEWPORT_GUI_HEIGHT;
//		cameraGUI.viewportWidth = MainGame.VIEWPORT_GUI_HEIGHT / (float) height
//				* (float) width;
//		cameraGUI.position.set(cameraGUI.viewportWidth / 2,
//				cameraGUI.viewportHeight / 2, 0);
//		cameraGUI.update();
//	}
//
//	@Override
//	public void dispose() {
////		gameLoopSound.dispose();
//		this.camera = null;
//		this.cameraGUI = null;
//	}
//
//	@Override
//	public void pause() {
//		// TODO Auto-generated method stub
////		gameLoopSound.stop();
//	}
//
//	@Override
//	public void resume() {
//		// TODO Auto-generated method stub
////		gameLoopSound.loop();
//	}

}
