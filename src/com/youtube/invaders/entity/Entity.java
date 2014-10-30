package com.youtube.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	 * Textura que usar� la entidad
	 */
	public Texture texture;
	/**
	 * Posici�n absoluta de la entidad en el mapa
	 */
	protected Vector2 pos;
	/**
	 * Direcci�n en que se mueve la entidad
	 */
	protected Vector2 direction;
	/**
	 * Tipo de entidad. Ej. enemigo, personaje, etc. Actualmente no se usa
	 * (creo). <br>
	 * <br>
	 * Tipos de entidad:
	 * <ul>
	 * <li>-1: INDETERMINADO</li>
	 * <li>0: INDETERMINADO</li>
	 * </ul>
	 */
	protected static int type;
	/**
	 * Sprite utilizado por la entidad. Similar a {@link Entity.texture} <br>
	 * {@linkplain com.youtube.invaders.entity.Entity.texture}
	 */
	protected Sprite sprite;

	/**
	 * Constructor de entidades con tipo predeterminado = -1.
	 * 
	 * @param texture
	 *            Textura que usar� la entidad
	 * @param pos
	 *            Posici�n inicial de la entidad
	 * @param direction
	 *            Direccion en que se est� moviendo la entidad
	 */
	public Entity(Texture texture, Vector2 pos, Vector2 direction) {
		this.texture = texture;
		this.pos = pos;
		this.direction = direction;
		this.type = -1;
	}

	/**
	 * Constructor de entidades con tipo predeterminado = -1.
	 * 
	 * @param texture
	 *            Textura que usar� la entidad
	 * @param pos
	 *            Posici�n inicial de la entidad
	 * @param direction
	 *            Direccion en que se est� moviendo la entidad
	 * @param type
	 *            Tipo de entidad que se va a crear (v�ase Entity.type)
	 */
	public Entity(Texture texture, Vector2 pos, Vector2 direction, int type) {
		this.texture = texture;
		this.pos = pos;
		this.direction = direction;
		Entity.type = type;
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
		sb.draw(sprite, pos.x, pos.y);
		// sb.draw(texture, pos.x, pos.y);
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
		return new Rectangle(pos.x, pos.y, texture.getWidth(),
				texture.getHeight());
	}

}
