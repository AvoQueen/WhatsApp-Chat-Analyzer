package processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
	protected String mPath;
	protected ArrayList<String> lines;

	public Reader(String path)
	{
		mPath = path;
	}

	public void read() throws IOException
	{
		final File file;
		final FileReader reader;
		final BufferedReader br;

		String line;

		file = new File(mPath);
		reader = new FileReader(file);
		br = new BufferedReader(reader);

		lines.clear();

		while ((line = br.readLine()) != null)
		{
			lines.add(line);
		}

		br.close();
	};
	
	public String[] getHeader(String delimeter) {
		return lines.get(0).split(delimeter);
	}

	public static ArrayList<String> getContent(String path)
	{
		final FileReader reader;
		final BufferedReader br;
		final ArrayList<String> lines;

		String line;
		lines = new ArrayList<String>();

		try
		{
			reader = new FileReader(path);
			br = new BufferedReader(reader);
			
			while ((line = br.readLine()) != null)
			{
				lines.add(line);
			}
			
			br.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return lines;
	}

	public String getmPath()
	{
		return mPath;
	}

	public void setPath(String mPath)
	{
		this.mPath = mPath;
	}
}
