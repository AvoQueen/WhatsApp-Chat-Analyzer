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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartMenuController implements Initializable {

	@FXML
	Button loadFile;

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

		Parent root = FXMLLoader.load(getClass().getResource("simpleBarGraph.fxml"));
		Scene scene = new Scene(root, 1280, 800);

		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void exitApplication(ActionEvent event)
	{
		Platform.exit();
	}

}
