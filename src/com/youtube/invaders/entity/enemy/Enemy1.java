package com.youtube.invaders.entity.enemy;

import com.badlogic.gdx.math.Vector2;

public class Enemy1 extends Enemy {
	private static String path = "enemy1.png";

	public Enemy1(Vector2 pos, Vector2 direction) {

		super(pos, direction, path);
		speed = 5;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Enemy 1");
		return sb.toString();
	}

}
