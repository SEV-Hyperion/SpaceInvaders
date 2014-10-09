package com.youtube.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.entity.enemy.Enemy;
import com.youtube.invaders.entity.enemy.Enemy0;
import com.youtube.invaders.entity.enemy.Enemy1;
import com.youtube.invaders.entity.enemy.Enemy2;
import com.youtube.invaders.entity.enemy.Enemy3;
import com.youtube.invaders.entity.gui.LivesDisplay;
import com.youtube.invaders.entity.gui.Missile;
import com.youtube.invaders.entity.gui.TextGUI;
import com.youtube.invaders.entity.player.AnimatedPlayer;
import com.youtube.invaders.entity.player.Player;
import com.youtube.invaders.screen.GameOverScreen;
import com.youtube.invaders.screen.ScreenManager;
import com.yutube.invaders.camera.OrthoCamera;

public class EntityManager {

	Player player;
	AnimatedPlayer animatedPlayer;
	LivesDisplay livesDisplay;
	TextGUI textGUI;

	public Sound killed, hit;
	// public AssetFonts fonts;

	private final Array<Entity> entities = new Array<Entity>();

	public EntityManager(int amount, OrthoCamera camera) {

		animatedPlayer = new AnimatedPlayer(new Vector2(230, 15), new Vector2(
				0, 0), this, camera);

		livesDisplay = new LivesDisplay(new Vector2(MainGame.WIDTH / 2,
				MainGame.HEIGHT
						- ((int) (1.5 * animatedPlayer.sprite.getHeight()))),
				new Vector2(0, 0), this, camera);

		textGUI = new TextGUI(new Vector2(MainGame.WIDTH / 2, MainGame.HEIGHT
				- ((int) (1.5 * animatedPlayer.sprite.getHeight()))),
				new Vector2(0, 0));

		killed = Gdx.audio.newSound(Gdx.files.internal("sounds/killed.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));

		for (int i = 0; i < amount; i++) {
			float x = MathUtils.random(0, MainGame.WIDTH
					- TextureManager.ENEMY0.getWidth());
			float y = (MathUtils.random(MainGame.HEIGHT, MainGame.HEIGHT * 10));

			float speed = MathUtils.random(2, 5);
			int type = MathUtils.random(0, 4);
			Enemy enemy;
			switch (type) {
			case 0:
				enemy = (Enemy) (new Enemy0(new Vector2(x, y), new Vector2(0,
						-speed), type));
				break;

			case 1:
				enemy = (Enemy) (new Enemy1(new Vector2(x, y), new Vector2(0,
						-speed), type));
				break;
			case 2:
				enemy = (Enemy) (new Enemy2(new Vector2(x, y), new Vector2(0,
						-speed), type));
				break;
			case 3:
				enemy = (Enemy) (new Enemy3(new Vector2(x, y), new Vector2(0,
						-speed), type));
				break;
			default:
				enemy = (Enemy) (new Enemy3(new Vector2(x, y), new Vector2(0,
						-speed), type));

			}
			entities.add(enemy);
		}

		// fonts = new AssetFonts();
	}

	public void update() {

		for (Entity e : entities)
			e.update();
		// player.update();
		animatedPlayer.update();
		livesDisplay.update();
		// textGUI.update();
		checkCollisions();
	}

	public void render(SpriteBatch sb) {

		for (Entity e : entities)
			e.render(sb);
		for (Missile m : getMissiles())
			if (m.checkEnd())
				entities.removeValue(m, false);
		
		animatedPlayer.render(sb);
		livesDisplay.render(sb);
		textGUI.render(sb);

		

	}

	private void checkCollisions() {
		for (Enemy e : getEnemies()) {
			for (Missile m : getMissiles()) {
				if (e.getBounds().overlaps(m.getBounds())) {
					entities.removeValue(e, false);
					// entities.removeValue(m, false);
					MainGame.score += 1;
					killed.play();
					if (gameOver()) {
						killed.dispose();
						// end, Game Won
						ScreenManager.setScreen(new GameOverScreen(true));

					}
				}
			}

			if (e.getBounds().overlaps(animatedPlayer.getBounds())) {

				// Collision with enemy. Decrement Live Counter and erase enemy
				entities.removeValue(e, false);
				livesDisplay.setLives(livesDisplay.getLives() - 1);
				hit.play();
				if (livesDisplay.getLives() == 0) {
					// If we ran out of lives, game is over, lost
					ScreenManager.setScreen(new GameOverScreen(false));
				}
			}

		}
	}

	private Array<Enemy> getEnemies() {
		Array<Enemy> ret = new Array<Enemy>();
		for (Entity e : entities) {
			if (e instanceof Enemy)
				ret.add((Enemy) e);

		}

		return ret;
	}

	private Array<Missile> getMissiles() {
		Array<Missile> ret = new Array<Missile>();
		for (Entity m : entities) {
			if (m instanceof Missile)
				ret.add((Missile) m);
		}

		return ret;
	}

	public boolean gameOver() {
		return ((getEnemies().size <= 0) || (livesDisplay.getLives() == 0));
	}

	public void addEntity(Entity e) {
		entities.add(e);

	}

	public void dispose() {

	}

	// GUI Management
	public class AssetFonts {

		public final BitmapFont font;

		public AssetFonts() {
			font = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"),
					false);

			font.setScale(1.0f);
			font.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}

}
