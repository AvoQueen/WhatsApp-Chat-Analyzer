package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import processing.Reader;
import processing.WhatsappFile;
import settings.Language;
import settings.LanguageSelector;
import settings.LanguageType;;

public class Main extends Application {
	private static WhatsappFile whatsappfile;
	private static boolean FileLoaded = false;
	private static Language german;
	private static Language english;
	private static Language french;

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
		Scene scene = new Scene(root, 640, 280);

		primaryStage.setTitle("WhatsApp Chat Analyzer");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		german = LanguageSelector.getLanguage(LanguageType.GERMAN);
		english = LanguageSelector.getLanguage(LanguageType.ENGLISH);
		french = LanguageSelector.getLanguage(LanguageType.FRENCH);
		launch(args);
	}

	public static void initWhatsAppFile(File file)
	{
		final String path = file.getAbsolutePath().replace("\\\\", "/");
		Main.whatsappfile = new WhatsappFile(path);
		Main.FileLoaded = true;
	}
	
	public static WhatsappFile getWhatsAppFile() {
		return whatsappfile;
	}
	
	public static boolean isFileLoaded() {
		return Main.FileLoaded;
	}
	
	public static Language getPreLoadedLanguage(LanguageType type) {
		switch(type)
		{
		case GERMAN:
			return german;
		case ENGLISH:
			return english;
		case FRENCH:
			return french;
		default:
			return null;
		}
	}
	
	public static Language getPreLoadedLanguageENGLISH() {
		return english;
	}
	
	public static Language getPreLoadedLanguageFRENCH() {
		return french;
	}
}
