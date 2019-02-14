package application;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import processing.WhatsappFile;;

public class Main extends Application {
	private static WhatsappFile whatsappfile;
	private static boolean FileLoaded = false;

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
}
