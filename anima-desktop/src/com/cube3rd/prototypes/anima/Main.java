package com.cube3rd.prototypes.anima;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "anima";
		cfg.useGL20 = false;
		cfg.width = Anima.VIRTUAL_WIDTH;
		cfg.height = Anima.VIRTUAL_HEIGHT;
		
		new LwjglApplication(new Anima(), cfg);
	}
}
