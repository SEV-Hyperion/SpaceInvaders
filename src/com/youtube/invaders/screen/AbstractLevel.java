package com.youtube.invaders.screen;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.camera.OrthoCamera;
import com.youtube.invaders.entity.Entity;
import com.youtube.invaders.entity.EntityManager;
import com.youtube.invaders.entity.enemy.Enemy;
import com.youtube.invaders.entity.enemy.Enemy0;
import com.youtube.invaders.entity.enemy.Enemy1;
import com.youtube.invaders.entity.enemy.Enemy2;
import com.youtube.invaders.entity.enemy.Enemy3;
import com.youtube.invaders.entity.enemy.EnemyWarrior;
import com.youtube.invaders.entity.enemy.EnemyWarriorStanding;
import com.youtube.invaders.utils.Conf;

public class AbstractLevel extends Screen {

	public int width;
	public int height;
	
	
	private OrthoCamera camera;
	private OrthographicCamera cameraGUI;
	public Sound gameLoopSound;
	private static EntityManager entityManager = null;

	private String level = "LEVEL_1_1";
	private String map = "map_level_1_1";
	private String sound = "sounds/l2outro.ogg";
	private String title = "Tutorial";
	private String nextLevel = "level_1_2";
	private Sprite sprite;

	/**
	 * 
	 * @param level
	 */
	public AbstractLevel(String level) {
		this.level = level;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		camera = new OrthoCamera();

		Entity[] entities = loadLevelInfo();
		// poner el titulo en la barra arriba :D
		Gdx.graphics.setTitle(title);// TODO remove if necessary
		entityManager = new EntityManager(entities, camera);
		entityManager.nextLevel=nextLevel;
		// gameLoopSound = Gdx.audio.newSound(Gdx.files.internal(sound));
		// gameLoopSound.loop();

		cameraGUI = new OrthographicCamera(MainGame.VIEWPORT_GUI_WIDTH,
				MainGame.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true);
		cameraGUI.update();

		/**
		 * map support testing
		 **/
		TextureRegion region = TextureManager.instance.atlas.findRegion(map);
		sprite = new Sprite(region);
		//sprite.setSize(MainGame.VIEWPORT_GUI_HEIGHT,
				//MainGame.VIEWPORT_GUI_WIDTH);
		//sprite.rotate90(false);
		sprite.setPosition(0, -MainGame.VIEWPORT_GUI_WIDTH / 2);
		sprite.setOrigin(MainGame.VIEWPORT_GUI_HEIGHT / 2,
				MainGame.VIEWPORT_GUI_WIDTH / 2);
		height=sprite.getRegionHeight();
		width=sprite.getRegionWidth();
		
	}

	/**
	 * Create the required entities for current level here
	 * 
	 * @return entities
	 */
	private Entity[] loadLevelInfo() {

		JsonReader reader = Json.createReader(Gdx.files.internal(
				level + ".json").read());
		JsonObject a = reader.readObject();
		title = a.getString("title");
		map=a.getString("map");
		sound = a.getString("sound");
		nextLevel=a.getString("next_level");
		JsonArray enemies = a.getJsonArray("enemies");
		Entity[] entities = new Entity[enemies.size()];
		for (int i = 0; i < enemies.size(); i++) {
			JsonObject o = enemies.getJsonObject(i);
			int type = o.getInt("type");
			JsonObject pos = o.getJsonObject("pos");
			int pos_x = pos.getInt("x");
			int pos_y = pos.getInt("y");
			JsonObject dir = o.getJsonObject("dir");
			int dir_x = dir.getInt("x");
			int dir_y = dir.getInt("y");

			entities[i] = loadEnemy(type, new Vector2(pos_x, pos_y),
					new Vector2(dir_x, dir_y));
		}

		return entities;
	}

	private Enemy loadEnemy(int type, Vector2 pos, Vector2 direction) {
		Enemy en = null;
		System.out.println("Creating enemy type: " + type);
		switch (type) {
		case 0:
			en = new Enemy0(pos, direction);
			break;
		case 1:
			en = new Enemy1(pos, direction);
			break;
		case 2:
			en = new Enemy2(pos, direction);
			break;
		case 3:
			en = new Enemy3(pos, direction);
			break;
		case 4:
			en = new EnemyWarrior(pos, direction);
			break;
		case 5:
			en = new EnemyWarriorStanding(pos, direction);
		}
		return en;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		camera.update();
		cameraGUI.update();
		entityManager.update();

	}

	@Override
	public void render(SpriteBatch sb) {

		// TODO this should be flexible.
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(camera.combined);
		sb.enableBlending(); // Enable blending in the game screen
		sb.begin();
		sprite.draw(sb);
		entityManager.render(sb);
		sb.end();

	}

	@Override
	public void resize(int width, int height) {
		camera.resize();

		cameraGUI.viewportHeight = MainGame.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = MainGame.VIEWPORT_GUI_HEIGHT / (float) height
				* (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2,
				cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		// gameLoopSound.dispose();
		this.camera = null;
		this.cameraGUI = null;
		entityManager.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		// gameLoopSound.stop();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		// gameLoopSound.loop();
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
}
