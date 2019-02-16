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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import processing.WhatsAppStatistic;

public class ChartController implements Initializable {

	@FXML
	private MenuBar menubar;
	
	@FXML
	private TabPane tabpane;

	@FXML
	private BarChart<String, Number> barchart1;

	@FXML
	private BarChart<String, Number> barchart2;

	@FXML
	private PieChart piechart;

	private boolean loadedPieChart = false,
			loadedBarChart1 = false,
			loadedBarChart2 = false,
			loadedStatistcis = false;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		System.out.println("Initialized Chart Controller!");
		loadPieChart();
	}
	
	void loadCharts() {
		loadPieChart();
		loadSimpleBarChart();
		loadAdvancedBarChart();
	}
	
	void reloadCurrentChart() {
		final int index = tabpane.getSelectionModel().getSelectedIndex();
		
		switch(index)
		{
		case 0:
			loadPieChart();
			break;
		case 1:
			loadSimpleBarChart();
			break;
		case 2:
			loadAdvancedBarChart();
			break;
		case 3:
			break;
		}
	}

	@FXML
	private void loadPieChart()
	{
		if (loadedPieChart)
		{
			return;
		}

		loadedPieChart = true;
		piechart.getData().addAll(WhatsAppStatistic.getPieChartData(Main.getWhatsAppFile()));

	}

	@FXML
	private void loadSimpleBarChart()
	{
		if (loadedBarChart1)
		{
			return;
		}
		
		loadedBarChart1 = true;
		barchart1.getData().add(WhatsAppStatistic.getSimpleBarChartData(Main.getWhatsAppFile()));
	}

	@FXML
	private void loadAdvancedBarChart()
	{
		if(loadedBarChart2)
		{
			return;
		}
		
		loadedBarChart2 = true;
		barchart2.getData().addAll(WhatsAppStatistic.getAdvancedBarChartData(Main.getWhatsAppFile()));
	}

	@FXML
	void exitApplication(ActionEvent event)
	{
		Platform.exit();
	}

	@FXML
	void openNewFile(ActionEvent event)
	{
//		// abusing the chart to get the scene reference...
		Stage stage = (Stage) piechart.getScene().getWindow();
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
		reloadCurrentChart();
	}

	private void resetChartData()
	{
		loadedPieChart = false;
		loadedBarChart1 = false;
		loadedBarChart2 = false;
		
		piechart.getData().clear();
		barchart1.getData().clear();
		barchart2.getData().clear();
	}
//
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
		
		tabpane.getTabs().get(0).setText("Kreisdiagramm");
		piechart.setTitle("Gesprächsanteile");
		
		tabpane.getTabs().get(1).setText("Einfaches Balkendiagramm");
		barchart1.setTitle("Zeit-Nachrichten Diagramm [Einfach]");
		
		tabpane.getTabs().get(2).setText("Fortgeschrittenes Balkendiagramm");
		barchart2.setTitle("Zeit-Nachrichten Diagramm [Fortgeschritten]");
	}
//
	@FXML
	void changetoEnglish()
	{
//		Menu file = menubar.getMenus().get(0);
//		Menu settings = menubar.getMenus().get(1);
//		Menu help = menubar.getMenus().get(2);
//
//		file.setText("File");
//		file.getItems().get(0).setText("Open...");
//		file.getItems().get(2).setText("Quit");
//
//		settings.setText("Settings");
//		settings.getItems().get(0).setText("Language");
//		((Menu) settings.getItems().get(0)).getItems().get(0).setText("English");
//		((Menu) settings.getItems().get(0)).getItems().get(1).setText("German");
//		settings.getItems().get(2).setText("Size");
//
//		help.setText("Help");
//		help.getItems().get(0).setText("About");
//
//		chartTitles = new String[] { 
//				"Share of Conversation",
//				"Time-Message Diagram [Simple]",
//				"Time-Message Diagram [Advanced]" };
//		
//		chartIdentifier = new String[] { 
//				"Pie-Chart",
//				"Simple Bar-Chart",
//				"Advanced Bar-Chart" };
//		
//		combobox.setItems(
//				FXCollections.observableArrayList(chartIdentifier[1], chartIdentifier[2], chartIdentifier[0], "Statistics"));
//		
//		changeChart();
	}
}
