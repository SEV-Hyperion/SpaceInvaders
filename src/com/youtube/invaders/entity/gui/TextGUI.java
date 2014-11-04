package com.youtube.invaders.entity.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.youtube.invaders.MainGame;
import com.youtube.invaders.entity.Entity;

public class TextGUI extends Entity {
	public AssetFonts fonts;
	private Animation textGUIAnimation;
	private static String path = "playerOneUp_1.png";
	private static int width = 12;
	private static int height = 25;
	private static int frameDuration = 1;

	public TextGUI(Vector2 pos, Vector2 direction) {
		super(pos, direction);

		textGUIAnimation = loadAnimation(path, width, height, frameDuration);
		currentFrame = textGUIAnimation.getKeyFrame(0, true);

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
