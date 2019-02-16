package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import processing.WhatsAppStatistic;

public class ChartController implements Initializable {

	@FXML
	private MenuBar menubar;

	@FXML
	private BarChart<String, Number> barchart;

	@FXML
	private CategoryAxis x;

	@FXML
	private NumberAxis y;

	@FXML
	private ComboBox<String> combobox;

	@FXML
	private PieChart piechart;

	private Series<String, Number> simpleData;
	private ObservableList<Series<String, Number>> advancedData;
	private ObservableList<PieChart.Data> pieData;

	private String[] chartTitles = new String[] { 
			"Share of Conversation",
			"Time-Message Diagram [Simple]",
			"Time-Message Diagram [Advanced]" };
	
	private String[] chartIdentifier = new String[] { 
			"Pie-Chart",
			"Simple Bar-Chart",
			"Advanced Bar-Chart" };

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		System.out.println("Initialized Chart Controller!");

		combobox.setItems(
				FXCollections.observableArrayList("Simple Bar-Chart", "Advanced Bar-Chart", "Pie-Chart", "Statistics"));

		combobox.getSelectionModel().select(0);

		piechart.setVisible(false);

		Series<String, Number> data = WhatsAppStatistic.getSimpleBarChartData(Main.getWhatsAppFile());
		barchart.getData().add(data);
	}

	@FXML
	void changeChart()
	{
		String selected = combobox.getSelectionModel().getSelectedItem();
		
		if(selected == null) {
			combobox.getSelectionModel().select(0);
			changeChart();
			return;
		}

		if (selected.equals(chartIdentifier[0]))
			initializePieChart();
		else if (selected.equals(chartIdentifier[1]))
			initializeSimpleBarChart();
		else if (selected.equals(chartIdentifier[2])) initializeAdvancedBarChart();

	}

	private void initializePieChart()
	{
		barchart.setVisible(false);
		piechart.setVisible(true);
		piechart.setTitle(chartTitles[0]);

		if (pieData != null)
		{
			return;
		}

		pieData = WhatsAppStatistic.getPieChartData(Main.getWhatsAppFile());
		piechart.getData().clear();
		piechart.getData().addAll(pieData);

	}

	private void initializeSimpleBarChart()
	{
		barchart.setVisible(true);
		piechart.setVisible(false);
		barchart.setTitle(chartTitles[1]);

		if (simpleData != null)
		{
			if (!barchart.getData().contains(simpleData))
			{
				barchart.getData().removeAll(advancedData);
				barchart.getData().add(simpleData);
			}
			return;
		}

		simpleData = WhatsAppStatistic.getSimpleBarChartData(Main.getWhatsAppFile());
		barchart.getData().clear();
		barchart.getData().add(simpleData);
	}

	private void initializeAdvancedBarChart()
	{
		barchart.setVisible(true);
		piechart.setVisible(false);
		barchart.setTitle(chartTitles[2]);

		// if (advancedData != null)
		// {
		// if (!barchart.getData().containsAll(advancedData))
		// {
		// barchart.getData().remove(simpleData);
		// barchart.getData().addAll(advancedData);
		// //barchart.getData().addAll(advancedData);
		// }
		// return;
		// }

		advancedData = WhatsAppStatistic.getAdvancedBarChartData(Main.getWhatsAppFile());
		barchart.getData().clear();
		barchart.getData().addAll(advancedData);
	}

	@FXML
	void exitApplication(ActionEvent event)
	{
		Platform.exit();
	}

	@FXML
	void openNewFile(ActionEvent event)
	{
		// abusing the chart to get the scene reference...
		Stage stage = (Stage) barchart.getScene().getWindow();
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
		resetChartData();
		changeChart();
	}

	private void resetChartData()
	{
		simpleData = null;
		advancedData = null;
		pieData = null;
	}

	@FXML
	void changetoGerman()
	{
		Menu file = menubar.getMenus().get(0);
		Menu settings = menubar.getMenus().get(1);
		Menu help = menubar.getMenus().get(2);

		file.setText("Datei");
		file.getItems().get(0).setText("Öffne...");
		file.getItems().get(2).setText("Verlassen");

		settings.setText("Einstellungen");
		settings.getItems().get(0).setText("Sprache");
		((Menu) settings.getItems().get(0)).getItems().get(0).setText("Englisch");
		((Menu) settings.getItems().get(0)).getItems().get(1).setText("Deutsch");
		settings.getItems().get(2).setText("Größe");

		help.setText("Hilfe");
		help.getItems().get(0).setText("Über");

		chartTitles = new String[] { 
				"Gesprächsanteile",
				"Zeit-Nachrichten Diagramm [Einfach]",
				"Zeit-Nachrichten Diagramm [Fortgeschritten]" };
		
		chartIdentifier = new String[] { 
				"Kreisdiagramm",
				"Einfaches Balkendiagramm",
				"Fortgeschrittenes Balkendiagramm" };
		
		combobox.setItems(
				FXCollections.observableArrayList(chartIdentifier[1], chartIdentifier[2], chartIdentifier[0], "Statistiken"));
		
		changeChart();
	}

	@FXML
	void changetoEnglish()
	{
		Menu file = menubar.getMenus().get(0);
		Menu settings = menubar.getMenus().get(1);
		Menu help = menubar.getMenus().get(2);

		file.setText("File");
		file.getItems().get(0).setText("Open...");
		file.getItems().get(2).setText("Quit");

		settings.setText("Settings");
		settings.getItems().get(0).setText("Language");
		((Menu) settings.getItems().get(0)).getItems().get(0).setText("English");
		((Menu) settings.getItems().get(0)).getItems().get(1).setText("German");
		settings.getItems().get(2).setText("Size");

		help.setText("Help");
		help.getItems().get(0).setText("About");

		chartTitles = new String[] { 
				"Share of Conversation",
				"Time-Message Diagram [Simple]",
				"Time-Message Diagram [Advanced]" };
		
		chartIdentifier = new String[] { 
				"Pie-Chart",
				"Simple Bar-Chart",
				"Advanced Bar-Chart" };
		
		combobox.setItems(
				FXCollections.observableArrayList(chartIdentifier[1], chartIdentifier[2], chartIdentifier[0], "Statistics"));
		
		changeChart();
	}
}
