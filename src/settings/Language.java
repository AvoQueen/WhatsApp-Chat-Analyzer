package settings;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.chart.Chart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

public class Language {
	private HashMap<String, String> data;

	public Language(HashMap<String, String> content)
	{
		data = content;
	}

	public void updateMenuBar(MenuBar menubar)
	{
		final Menu file = menubar.getMenus().get(0);
		final Menu settings = menubar.getMenus().get(1);
		final Menu help = menubar.getMenus().get(2);

		updateFileMenu(file);
		updateSettingsMenu(settings);
		updateHelpMenu(help);
	}

	private void updateFileMenu(Menu file)
	{
		file.setText(data.get("file"));
		file.getItems().get(0).setText(data.get("open"));
		file.getItems().get(2).setText(data.get("close"));
	}

	private void updateSettingsMenu(Menu settings)
	{
		settings.setText(data.get("settings"));
		settings.getItems().get(0).setText(data.get("language"));
		((Menu) settings.getItems().get(0)).getItems().get(0).setText(data.get("english"));
		((Menu) settings.getItems().get(0)).getItems().get(1).setText(data.get("german"));
		settings.getItems().get(2).setText(data.get("size"));
	}

	private void updateHelpMenu(Menu help)
	{
		help.setText(data.get("help"));
		help.getItems().get(0).setText(data.get("about"));
	}
	
	public void updateTabPane(TabPane tabpane)
	{
		tabpane.getTabs().get(0).setText(data.get("piecharttab"));
		
		tabpane.getTabs().get(1).setText(data.get("barcharttab1"));
		
		tabpane.getTabs().get(2).setText(data.get("barcharttab2"));
	}
	
	public void updateChart(Chart chart, String key) {
		chart.setTitle(data.get(key));
	}
}
