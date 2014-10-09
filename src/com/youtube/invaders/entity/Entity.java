package com.youtube.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected Texture texture;
	protected Vector2 pos, direction;
	protected static  int type;
	protected  Sprite sprite;
	public Entity(Texture texture, Vector2 pos, Vector2 direction) {
		this.texture = texture;
		this.pos = pos;
		this.direction = direction;
		this.type = -1;
		
	}
	
	public Entity(Texture texture, Vector2 pos, Vector2 direction, int type) {
		this.texture = texture;
		this.pos = pos;
		this.direction = direction;
		Entity.type = type;
	}

	public abstract void update();

	public void render(SpriteBatch sb) {
		sb.draw(sprite, pos.x, pos.y);
		//sb.draw(texture, pos.x, pos.y);
	}

	public Vector2 getPosition() {
		return pos;

	}

	
	public void setDirection(float x, float y)
	{
		direction.set(x,y);
		direction.mul(Gdx.graphics.getDeltaTime());
		
	}
	public Rectangle getBounds()
	{
	
		return new Rectangle(pos.x,pos.y,texture.getWidth(), texture.getHeight());
		
	}

	
}
