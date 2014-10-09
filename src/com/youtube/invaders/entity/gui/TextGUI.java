package com.youtube.invaders.entity.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.TextureManager;
import com.youtube.invaders.entity.Entity;
import com.yutube.invaders.camera.OrthoCamera;

public class TextGUI extends Entity {
	public AssetFonts fonts;

	public TextGUI(Vector2 pos, Vector2 direction) {
		super(TextureManager.PLAYER, pos, direction);
		TextureRegion AR = (TextureManager.instance.atlas.findRegion("player"));
		sprite = new Sprite(AR);
		fonts = new AssetFonts();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch sb) {

		if (MainGame.debugMode) {
			int fps = Gdx.graphics.getFramesPerSecond();
			if (fps > 50)
				fonts.font.setColor(0, 1.0f - (50 - fps) * 0.5f, 0, 1);
			else if (fps > 30)
				fonts.font.setColor(1, 1, 0, 1);
			else
				fonts.font.setColor(1, 0, 0, 1);

			fonts.font.draw(sb, "FPS: " + fps, 10, 300);
		}
		fonts.font.setColor(0, 1, 1, 1);
		fonts.font.draw(sb, "Score: " + MainGame.score, 10, 780);
	}

	public void dispose() {

		fonts.font.dispose();
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
