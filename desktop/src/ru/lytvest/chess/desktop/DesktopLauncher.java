package ru.lytvest.chess.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import ru.lytvest.chess.Starter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;

		//TexturePacker.process(settings, "../../source",".","skin.atlas");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 16 * 80; // 16 * 90;
		config.height = 9 * 80 ;// 9 * 90;
		config.x = 10;
		config.y = 10;
		new LwjglApplication(new Starter(), config);
	}
}
