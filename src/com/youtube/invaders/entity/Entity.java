package com.youtube.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase abstracta que recoge las caracter�sticas b�sicas de una entidad
 * (jugador, enemigo, obst�culo, etc.).
 * 
 * @author Daniel
 *
 */
public abstract class Entity {

	/**
	 * Posici�n absoluta de la entidad en el mapa
	 */
	protected Vector2 pos;
	/**
	 * Direcci�n en que se mueve la entidad
	 */
	protected Vector2 direction;

	/**
	 * Frame actual del personaje
	 */
	public TextureRegion currentFrame;
	/**
	 * Indica el tiempo subjetivo del estado del personaje. Sirve para saber que
	 * frame pintar en ese momento,
	 */
	protected float stateTime;

	/**
	 * Constructor de entidades con tipo predeterminado = -1.
	 * 
	 * @param pos
	 *            Posici�n inicial de la entidad
	 * @param direction
	 *            Direccion en que se est� moviendo la entidad
	 */
	public Entity(Vector2 pos, Vector2 direction) {
		this.pos = pos;
		this.direction = direction;
	}

	/**
	 * Actualiza el estado de la entidad
	 */
	public abstract void update();

	/**
	 * Dibuja la entidad
	 * 
	 * @param sb
	 *            SpriteBatch que se encarga de dibujar la entidad.
	 */
	public void render(SpriteBatch sb) {
		sb.draw(currentFrame, pos.x, pos.y);
	}

	/**
	 * Devuelve el vector de posicion de la entidad. Es bidimensional (x, y).
	 * 
	 * @return Vector que indica la posicion de la entidad
	 */
	public Vector2 getPosition() {
		return pos;
	}

	/**
	 * Cambia la direcci�n de la entidad, multiplicada por los segundos pasados
	 * con el frame anterior
	 * 
	 * @param x
	 *            Coordenada x
	 * @param y
	 *            Coordenada y
	 */
	public void setDirection(float x, float y) {
		direction.set(x, y);
		direction.mul(Gdx.graphics.getDeltaTime());

	}

	/**
	 * Devuelve un rect�ngulo cuyas dimensiones se ajustan al tama�o de la
	 * entidad, en la posicion de dicha entidad.
	 * 
	 * @return Rectangulo con las dimensiones de la entidad y su misma posici�n.
	 */
	public Rectangle getBounds() {
		if (currentFrame == null) {
			System.out.println("NULL FRAME");
		}
		return new Rectangle(pos.x, pos.y, currentFrame.getRegionWidth(),
				currentFrame.getRegionHeight());
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * Carga una animaci�n dados los par�metros. No tiene control de errores as�
	 * que sed buenos!
	 * 
	 * @param path
	 *            Nombre del archivo que contiene la animacion, interno a la
	 *            aplicacion
	 * @param width
	 *            Ancho de la animacion
	 * @param height
	 *            Alto de la anumacion
	 * @param frameDuration
	 *            Duracion de cada frame de la animacion
	 * @return Una Animacion cargada segun los parametros de entrada.
	 */
	public Animation loadAnimation(String path, int width, int height,
			float frameDuration) {
		TextureRegion[] frames = new TextureRegion[2];
		Texture sheet = new Texture(Gdx.files.internal(path));
		TextureRegion[][] tmp = TextureRegion.split(sheet, width, height);
		int index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		return new Animation(frameDuration, frames);
	}

	public Animation loadComplexAnimation(String[] paths, int width,
			int height, float frameDuration) {
		TextureRegion[] frames = new TextureRegion[paths.length];
		int index = 0;
		for (String path : paths) {
			Texture sheet = new Texture(Gdx.files.internal(path));
			TextureRegion[][] tmp = TextureRegion.split(sheet, width, height);
			for (int i = 0; i < 1; i++) {
				for (int j = 0; j < 1; j++) {
					frames[index++] = tmp[i][j];
				}
			}
		}

		return new Animation(frameDuration, frames);
	}

}
