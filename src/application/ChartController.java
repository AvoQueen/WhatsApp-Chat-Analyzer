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
import settings.Language;
import settings.LanguageType;

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
	
	@FXML
	void reloadCharts() {
		resetChartData();
		reloadCurrentChart();
	}

	private void changeLanguage(LanguageType type)
	{	
		final Language language = Main.getPreLoadedLanguage(type);
		
		language.updateMenuBar(menubar);
		language.updateTabPane(tabpane);
		
		language.updateChart(piechart, "piechart");
		language.updateChart(barchart1, "barchart1");
		language.updateChart(barchart2, "barchart2");
	}
	
	@FXML
	void changeToGerman()
	{
		changeLanguage(LanguageType.GERMAN);
	}
	
	@FXML
	void changeToEnglish()
	{
		changeLanguage(LanguageType.ENGLISH);
	}
	
	@FXML
	void changeToFrench()
	{
		changeLanguage(LanguageType.FRENCH);
	}
}
