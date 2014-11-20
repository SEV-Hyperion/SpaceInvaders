package com.youtube.invaders.entity.gui.unanimated;

import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.entity.Entity;

public abstract class AbstractBlock extends Entity{

	public AbstractBlock(Vector2 pos, Vector2 direction) {
		super(pos, new Vector2(0, 0));
	}

	public AbstractBlock(Vector2 pos) {
		super(pos, new Vector2(0, 0));
	}

	@Override
	public void update() {
		// stand still
		
	}
	
}
