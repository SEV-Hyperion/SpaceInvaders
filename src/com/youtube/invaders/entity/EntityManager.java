package com.youtube.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.camera.OrthoCamera;
import com.youtube.invaders.entity.enemy.Enemy;
import com.youtube.invaders.entity.enemy.Enemy0;
import com.youtube.invaders.entity.enemy.Enemy1;
import com.youtube.invaders.entity.enemy.Enemy2;
import com.youtube.invaders.entity.enemy.Enemy3;
import com.youtube.invaders.entity.enemy.EnemyWarrior;
import com.youtube.invaders.entity.gui.LivesDisplay;
import com.youtube.invaders.entity.gui.TextGUI;
import com.youtube.invaders.entity.gui.projectiles.Projectile;
import com.youtube.invaders.entity.player.AnimatedPlayer;
import com.youtube.invaders.entity.player.Player;
import com.youtube.invaders.screen.GameOverScreen;
import com.youtube.invaders.screen.ScreenManager;

/**
 * Clase encargada de gestionar las entidades usadas por el programa. Las crea,
 * actualiza, pinta y destruye. <br>
 * Almacena el personaje, el contador de vidas y el contador de FPS.<br>
 * ¿Deberia ser Singleton? Pues igual no. Como la base de la app son las Screen,
 * esta puede ser instancia cuantas veces queramos.
 * 
 * @author Daniel
 *
 */

public class EntityManager {

	public Entity animatedPlayer;
	LivesDisplay livesDisplay;
	TextGUI textGUI;

	public Sound killed, hit;
	// public AssetFonts fonts;

	private Array<Entity> entities = new Array<Entity>();

	public static EntityManager em;

	public EntityManager(int amount, OrthoCamera camera) {
		em = this;

		animatedPlayer = new AnimatedPlayer(new Vector2(230, 15), new Vector2(
				0, 0), this, camera);

		livesDisplay = new LivesDisplay(new Vector2(MainGame.WIDTH / 2,
				MainGame.HEIGHT
						- ((int) (1.5 * animatedPlayer.currentFrame
								.getRegionHeight()))), new Vector2(0, 0), this,
				camera);

		textGUI = new TextGUI(
				new Vector2(MainGame.WIDTH / 2,
						MainGame.HEIGHT
								- ((int) (1.5 * animatedPlayer.currentFrame
										.getRegionHeight()))),
				new Vector2(0, 0));

		killed = Gdx.audio.newSound(Gdx.files.internal("sounds/killed.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));

		for (int i = 0; i < amount; i++) {
			float x = MathUtils.random(0, MainGame.WIDTH - Enemy.height);
			float y = (MathUtils.random(MainGame.HEIGHT, MainGame.HEIGHT * 10));

			float speed = MathUtils.random(2, 5);
			int type = MathUtils.random(0, 5);
			Enemy enemy;
			switch (type) {
			case 0:
				enemy = new Enemy0(new Vector2(x, y), new Vector2(0, -speed));
				break;
			case 1:
				enemy = new Enemy1(new Vector2(x, y), new Vector2(0, -speed));
				break;
			case 2:
				enemy = new Enemy2(new Vector2(x, y), new Vector2(0, -speed));
				break;
			case 3:
				enemy = new Enemy3(new Vector2(x, y), new Vector2(0, -speed));
				break;
			case 4:
				enemy = new EnemyWarrior(new Vector2(x, y), new Vector2(0,
						-speed));
			default:
				enemy = new EnemyWarrior(new Vector2(x, y), new Vector2(0,
						-speed));

			}
			entities.add(enemy);
		}

		// fonts = new AssetFonts();
	}

	public EntityManager(Entity[] levelEntities, OrthoCamera camera) {
		em = this;
		animatedPlayer = new AnimatedPlayer(new Vector2(230, 15), new Vector2(
				0, 0), this, camera);

		livesDisplay = new LivesDisplay(new Vector2(MainGame.WIDTH / 2,
				MainGame.HEIGHT
						- ((int) (1.5 * animatedPlayer.currentFrame
								.getRegionHeight()))), new Vector2(0, 0), this,
				camera);

		// textGUI = new TextGUI(
		// new Vector2(MainGame.WIDTH / 2,
		// MainGame.HEIGHT
		// - ((int) (1.5 * animatedPlayer.currentFrame
		// .getRegionHeight()))),
		// new Vector2(0, 0));

		killed = Gdx.audio.newSound(Gdx.files.internal("sounds/killed.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));
		System.out.println("Entities on array PRE: " + entities.size);
		entities.clear();
		entities.addAll(levelEntities);
		System.out.println("Entities on array POST: " + entities.size);
	}

	/**
	 * Actualiza todas la entidades gestionadas por ésta. <br>
	 * Llama a checkCollisions() para manejar las colisiones entre entidades.
	 */
	public void update() {
		for (Entity e : entities)
			e.update();
		animatedPlayer.update();
		livesDisplay.update();
		// textGUI.update();
		checkCollisions();
	}

	/**
	 * Dibuja todas las entidades.<br>
	 * También elimina los misiles si se han salido de la pantalla.
	 * 
	 * @param sb
	 */
	public void render(SpriteBatch sb) {
		for (Entity e : entities)
			e.render(sb);
		for (Projectile p : getProjectiles())
			if (p.checkEnd())
				entities.removeValue(p, false);

		animatedPlayer.render(sb);
		livesDisplay.render(sb);
		// textGUI.render(sb);

	}

	/**
	 * Comprueba las colisiones entre entidades gestionadas por la
	 * EntityManager.<br>
	 * Destruye los misiles al impactar junto con los enemigos. Quita vidas al
	 * chocar y llama al GameOver si es necesario.
	 */
	private void checkCollisions() {
		for (Enemy e : getEnemies()) {
			for (Projectile m : getProjectiles()) {
				if (e.getBounds().overlaps(m.getBounds())) {
					System.out.println("Enemies remaining PRE: "
							+ getEnemies().size);
					entities.removeValue(e, false);
					entities.removeValue(m, false); // destroy missile on hit
					MainGame.score += 1;
					killed.play();
					System.out.println("Enemies remaining POST: "
							+ getEnemies().size);
					if (getEnemies().size <= 0) {
						killed.dispose();
						System.out.println("Won with "
								+ livesDisplay.getLives() + " lives");
						System.out.println("Enemies remaining: "
								+ getEnemies().size);
						// end, Game Won
						ScreenManager.setScreen(new GameOverScreen(true)); // TODO if won, show winning screen and send to NEXT_LEVEL
						return;
					}
				}
			}

			if (e.getBounds().overlaps(animatedPlayer.getBounds())) {
				// Collision with enemy. Decrement Live Counter and erase enemy
				System.out.println("Hit by " + e);
				e.pos = new Vector2(800, 800);
				// entities.removeValue(e, false);
				System.out.println("You have " + livesDisplay.getLives()
						+ " lives");

				livesDisplay.setLives(livesDisplay.getLives() - 1);
				hit.play();
				if (livesDisplay.getLives() == 0) {
					System.out.println("Lost with " + livesDisplay.getLives()
							+ " lives");
					System.out.println("Enemies remaining: "
							+ getEnemies().size);
					// If we ran out of lives, game is over, lost
					ScreenManager.setScreen(new GameOverScreen(false));// TODO ok this is correct behaviour
					return;
				}
			}
		}
	}

	/**
	 * Devuelve un array con todas la entidades que sean Enemigos.
	 * 
	 * @return Array de Enemy
	 */
	public Array<Enemy> getEnemies() {
		Array<Enemy> ret = new Array<Enemy>();
		for (Entity e : entities) {
			if (e instanceof Enemy)
				ret.add((Enemy) e);
		}
		return ret;
	}

	/**
	 * Devuelve un array con todos los proyectiles
	 * 
	 * @return Array de Projectile
	 */
	private Array<Projectile> getProjectiles() {
		Array<Projectile> ret = new Array<Projectile>();
		for (Entity m : entities) {
			if (m instanceof Projectile)
				ret.add((Projectile) m);
		}
		return ret;
	}

	/**
	 * Comprueba las condiciones de fin de partida.
	 * 
	 * @return True si se dan las condiciones de GAMEOVER. FALSE en cualquier
	 *         otro caso.
	 */
	public boolean gameOver() {
		return ((getEnemies().size <= 0) || (livesDisplay.getLives() == 0));
	}

	/**
	 * Añade una entidad al EntityManager
	 * 
	 * @param e
	 *            Entidad que va a ser gestionada por el {@link EntityManager}
	 */
	public void addEntity(Entity e) {
		entities.add(e);
	}

	/**
	 * Libera los recursos innecesarios
	 */
	public void dispose() {
		this.entities.clear();
		this.entities=null;
		this.animatedPlayer=null;
		this.livesDisplay=null;
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

	/**
	 * Devuelve el jugador gestionado por el {@link EntityManager}
	 * 
	 * @return
	 */
	public Entity getAnimatedPlayer() {
		return animatedPlayer;
	}

}
