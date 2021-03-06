package com.youtube.invaders.screen;


public class ScreenManager {

	public static Screen currentScreen;

	public static void setScreen(Screen screen) {
		if (currentScreen != null) {
			currentScreen.dispose();
		}
		currentScreen = screen;
		currentScreen.create();
	}

	public static Screen getCurrentscreen() {
		return currentScreen;
	}

}
