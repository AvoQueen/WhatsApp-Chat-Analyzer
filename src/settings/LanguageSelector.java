package settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import processing.Reader;

public abstract class LanguageSelector {

	private static String[] keywords = new String[] { "file", "open", "close", "settings", "language", "english",
			"german", "size", "help", "about", "piecharttab", "piechart", "barcharttab1", "barchart1", "barcharttab2",
			"barchart2" };

	public static Language getLanguage(LanguageType language)
	{
		ArrayList<String> data;

		switch (language)
		{
		case GERMAN:
			data = Reader.getContent("src/languages/german.txt");
			break;
		case ENGLISH:
			data = Reader.getContent("src/languages/english.txt");
			break;
		case FRENCH:
			data = Reader.getContent("src/languages/french.txt");
			break;
		default:
			data = new ArrayList<String>();
		}

		HashMap<String, String> cleanData = LanguageSelector.cleanLanguageData(data);

		if (isValidLanguagePack(cleanData))
		{
			return new Language(cleanData);
		} else
		{
			return null;
		}
	}

	private static HashMap<String, String> cleanLanguageData(ArrayList<String> data)
	{
		HashMap<String, String> cleanData = new HashMap<>();
		data.forEach(line ->
		{
			if (!line.equals("") && line.contains("="))
			{
				final String[] keyAndName = line.split("=");
				cleanData.put(keyAndName[0], keyAndName[1]);
			}
		});

		return cleanData;
	}

	private static boolean isValidLanguagePack(HashMap<String, String> data)
	{
		ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(LanguageSelector.keywords));

		for (String line : data.keySet())
		{
			if (keywords.contains(line))
			{
				keywords.remove(line);
			}
		}

		if (keywords.isEmpty())
		{
			System.out.println("LanguagePack is valid!");
			return true;
		} else
		{
			System.out.println("LanguagePack is invalid!");
			return false;
		}
	}

	private static String containsKeyword(String line)
	{
		for (String keyword : LanguageSelector.keywords)
		{
			if (line.contains(keyword))
			{
				return keyword;
			}
		}
		return "none";
	}
}
