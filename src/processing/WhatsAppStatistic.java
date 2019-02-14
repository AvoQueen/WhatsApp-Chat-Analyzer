package processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class WhatsAppStatistic {
	private static final String[] hours = { "00:00am", "01:00am", "02:00am", "03:00am", "04:00am", "05:00am", "06:00am",
			"07:00am", "08:00am", "09:00am", "10:00am", "11:00am", "12:00am", "01:00 pm", "02:00 pm", "03:00 pm",
			"04:00 pm", "05:00 pm", "06:00 pm", "07:00 pm", "08:00 pm", "09:00 pm", "10:00 pm", "11:00 pm" };

	private WhatsAppStatistic()
	{
	}

	
	public static Series<String, Number> getSimpleBarChartData(WhatsappFile wafile)
	{
		final int[] messages = new int[24];
		Arrays.fill(messages, 0);

		ArrayList<String> time = wafile.getColumn(1);

		time.forEach(t ->
		{
			final int hour = Integer.parseInt(t.substring(0, 2));
			messages[hour]++;
		});

		XYChart.Series<String, Number> data = new XYChart.Series<String, Number>();

		for (int i = 0; i < 24; i++)
		{
			data.getData().add(new Data<String, Number>(WhatsAppStatistic.hours[i], messages[i]));
		}

		return data;
	}
	
	public static ObservableList<Series<String, Number>> getAdvancedBarChartData(WhatsappFile wafile)
	{
		
		String[] user = wafile.getUser();
		final HashMap<String, int[]> messages = new HashMap<String, int[]>();

		for (int i = 0; i < user.length; i++)
		{
			messages.put(user[i], new int[24]);
		}

		String[][] data = wafile.getBody();

		for (int i = 0; i < data.length; i++)
		{
			final int hour = Integer.parseInt(data[i][1].substring(0, 2));
			messages.get(data[i][2])[hour]++;
		}

		final ObservableList<Series<String, Number>> ChartData = FXCollections.observableList(new ArrayList<Series<String, Number>>());

		for (int i = 0; i < user.length; i++)
		{
			XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
			
			series.setName(user[i]);
			for (int j = 0; j < 24; j++)
			{
				series.getData().add(new Data<String, Number>(WhatsAppStatistic.hours[j], messages.get(user[i])[j]));
			}
			ChartData.add(series);
		}

		return ChartData;
	}
	
	public static ObservableList<javafx.scene.chart.PieChart.Data> getPieChartData(WhatsappFile wafile)
	{

		final String[] user = wafile.getUser();
		final int[] messages = new int[user.length];
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		final ArrayList<String> data = wafile.getColumn(2);

		for (int i = 0; i < user.length; i++)
		{
			map.put(user[i], i);
		}

		data.forEach(line ->
		{
			messages[map.get(line)]++;
		});

		final ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		for (int i = 0; i < user.length; i++)
		{
			pieChartData.add(new PieChart.Data(user[i], messages[i]));
		}

		return pieChartData;
	}	

}
