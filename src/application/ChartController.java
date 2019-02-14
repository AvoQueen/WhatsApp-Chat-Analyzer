package application;

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
import processing.WhatsAppStatistic;

public class ChartController implements Initializable {

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
	
	//private BarChart simple_, advanced_;
	//private PieChart piechart_;

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
		switch (selected)
		{
		case "Simple Bar-Chart":
			initializeSimpleBarChart();
			break;
		case "Advanced Bar-Chart":
			initializeAdvancedBarChart();
			break;
		case "Pie-Chart":
			initializePieChart();
			break;
		}
	}

	private void initializePieChart()
	{
		barchart.setVisible(false);
		piechart.setVisible(true);

		piechart.getData().clear();
		final ObservableList<PieChart.Data> data = WhatsAppStatistic.getPieChartData(Main.getWhatsAppFile());
		piechart.getData().addAll(data);

	}

	private void initializeSimpleBarChart()
	{
		barchart.setVisible(true);
		piechart.setVisible(false);

		barchart.setTitle("Time-Message Diagram [Simple]");
		barchart.getData().clear();
		final Series<String, Number> simple = WhatsAppStatistic.getSimpleBarChartData(Main.getWhatsAppFile());
		barchart.getData().add(simple);
	}

	private void initializeAdvancedBarChart()
	{
		barchart.setVisible(true);
		piechart.setVisible(false);

		barchart.setTitle("Time-Message Diagram [Advanced]");
		barchart.getData().clear();
		ObservableList<Series<String, Number>> advanced = WhatsAppStatistic
				.getAdvancedBarChartData(Main.getWhatsAppFile());
		barchart.getData().addAll(advanced);
	}

	@FXML
	void exitApplication(ActionEvent event)
	{
		// barchart.setVisible(false);
		Platform.exit();
	}
}
