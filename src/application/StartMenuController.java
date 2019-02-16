package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartMenuController implements Initializable {

	@FXML
	Button loadFile;
	
	@FXML
	MenuBar menubar;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		System.out.println("Initialized StartMenu Controller!");
	}

	public void loadFileAndProceed(ActionEvent event) throws IOException
	{
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		File file;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");
		fileChooser.setInitialDirectory(new File("chats"));

		file = fileChooser.showOpenDialog(stage);

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);

		if (file == null)
		{
			return;
		}

		Main.initWhatsAppFile(file);

		Parent root = FXMLLoader.load(getClass().getResource("DiagramsTest.fxml"));
		Scene scene = new Scene(root, 1280, 800);

		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void exitApplication(ActionEvent event)
	{
		Platform.exit();
	}
	
	@FXML
	void changetoGerman() {
		Menu file = menubar.getMenus().get(0);
		Menu settings = menubar.getMenus().get(1);
		Menu help = menubar.getMenus().get(2);
		
		file.setText("Datei");
		file.getItems().get(0).setText("Öffne...");
		file.getItems().get(2).setText("Verlassen");
		
		settings.setText("Einstellungen");
		settings.getItems().get(0).setText("Sprache");
		((Menu)settings.getItems().get(0)).getItems().get(0).setText("Englisch");
		((Menu)settings.getItems().get(0)).getItems().get(1).setText("Deutsch");
		
		help.setText("Hilfe");
		help.getItems().get(0).setText("Über");
	}
	
	@FXML
	void changetoEnglish() {
		Menu file = menubar.getMenus().get(0);
		Menu settings = menubar.getMenus().get(1);
		Menu help = menubar.getMenus().get(2);
		
		file.setText("File");
		file.getItems().get(0).setText("Open...");
		file.getItems().get(2).setText("Quit");
		
		settings.setText("Settings");
		settings.getItems().get(0).setText("Language");
		((Menu)settings.getItems().get(0)).getItems().get(0).setText("English");
		((Menu)settings.getItems().get(0)).getItems().get(1).setText("German");
		
		help.setText("Help");
		help.getItems().get(0).setText("About");
	}
}
