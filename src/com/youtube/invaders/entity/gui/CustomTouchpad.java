package com.youtube.invaders.entity.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.yutube.invaders.camera.OrthoCamera;

public class CustomTouchpad extends Touchpad {

	private static TouchpadStyle touchpadStyle = new TouchpadStyle();
	private Skin touchpadSkin = new Skin();
	private Drawable touchBackground;
	private Drawable touchKnob;

	public CustomTouchpad(OrthoCamera camera) {
		// TODO Auto-generated constructor stub
		super(10, touchpadStyle);

		// Create camera
		float aspectRatio = (float) Gdx.graphics.getWidth()
				/ (float) Gdx.graphics.getHeight();
		camera.setToOrtho(false, 10f * aspectRatio, 10f);

		// Create a touchpad skin
		touchpadSkin = new Skin();
		// Set background image
		touchpadSkin.add("touchBackground",
				new Texture(Gdx.files.internal("touchBackground.png")));
		// Set knob image
		touchpadSkin.add("touchKnob",
				new Texture(Gdx.files.internal("touchKnob.png")));
		// Create TouchPad Style
		touchpadStyle = new TouchpadStyle();
		// Create Drawable's from TouchPad skin
		touchBackground = touchpadSkin.getDrawable("touchBackground");
		touchKnob = touchpadSkin.getDrawable("touchKnob");
		// Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		// Create new TouchPad with the created style
		// super(10, touchpadStyle);
		this.setStyle(touchpadStyle);
		// setBounds(x,y,width,height)
		this.setBounds(15, 15, 200, 200);

		// Create a Stage and add TouchPad

	}
}
