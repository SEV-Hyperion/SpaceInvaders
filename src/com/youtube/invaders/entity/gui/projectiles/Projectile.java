package com.youtube.invaders.entity.gui.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.entity.Entity;

public class Projectile extends Entity {

	protected Animation animation;

	public Projectile(Vector2 pos, Vector2 direction) {
		super(pos, direction);

	}

	@Override
	public void update() {
		pos.add(direction);
	}

	/**
	 * Comprueba si el proyectil a alcanzado el final de la pantalla. Deberia
	 * comprobar los 4 limites, pero aun no sabemos disparar bien :S
	 * 
	 * @return true si est� fuera del mapa, false si esta dentro
	 */
	public boolean checkEnd() {
		if (pos.y > MainGame.HEIGHT
				+ animation.getKeyFrame(stateTime).getRegionHeight())
			return true;
		return false;

	}
}
