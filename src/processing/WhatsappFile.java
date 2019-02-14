package processing;

import java.util.ArrayList;
import java.util.Arrays;

public class WhatsappFile {
	private static final String sEncryptionDisclaimerENG = "Messages to this chat and calls are now secured with end-to-end encryption. Tap for more info.";
	private static final String sEncryptionDisclaimerGER = "Nachrichten in diesem Chat sowie Anrufe sind jetzt mit Ende-zu-Ende-Verschlüsselung geschätzt. Tippe für mehr Infos.";

	private String mPath;
	private ArrayList<String> mContent;

	private String[][] mBody;
	private String[] mHeader;
	private String[] mUser;
	private int mMedia = 0;

	// 0 = German, 1 = English
	private int mLanguage = 0;

	private boolean hasEncriptionDisclaimer = true;

	public WhatsappFile(String path)
	{
		mPath = path;
		setHeader(new String[] { "Date", "Time", "User", "Message" });

		this.processContent();
	}

	private void processContent()
	{
		mContent = getCleanedContent();
		checkEncryptionDisclaimer();
		mBody = new String[mContent.size()][4];

		ArrayList<String> user = new ArrayList<String>();

		for (int i = 0; i < mBody.length; i++)
		{
			/*
			 * The English formatting increases the length of the date by 2 because it uses
			 * the whole year [2019] instead of the short version [19]. Thats why i am
			 * shifting every substring 2 to the right when it the formatting is English
			 */
			String currentLine = mContent.get(i);
			// set the Date
			mBody[i][0] = currentLine.substring(0, 8 + mLanguage * 2);
			// set the Time
			mBody[i][1] = currentLine.substring(10 + mLanguage * 2, 15 + mLanguage * 2);
			// set Name and Message
			setUsernameAndMessage(currentLine.substring(18 + mLanguage * 2), i);

			if (!user.contains(mBody[i][2]))
			{
				user.add(mBody[i][2]);
			}

			// System.out.println(Arrays.toString(mBody[i]));
		}
		mUser = new String[user.size()];
		mUser = user.toArray(mUser);
	}

	private ArrayList<String> getCleanedContent()
	{
		ArrayList<String> dirtyContent = Reader.getContent(mPath);
		ArrayList<String> cleanContent = new ArrayList<String>();

		checkLanguage(dirtyContent);

		dirtyContent.forEach(line ->
		{
			// [Date, Time - User: Message] has to be at least 19 characters long
			final String NewMessageCheck = (line.length() > 18) ? line.substring(2, 3) : "";

			/*
			 * Check if the third character is a dot [Date format is dd.mm.yy]. If it is a
			 * dot then it is most likely a new message and it is added to the
			 * cleanedContent. If it isn't a dot then it is added to the last message with a
			 * line break.
			 */
			String comparator = (mLanguage == 0) ? "." : "/";

			if (NewMessageCheck.equals(comparator))
			{
				cleanContent.add(line);
			} else
			{
				final int lastIndex = cleanContent.size() - 1;
				cleanContent.set(lastIndex, cleanContent.get(lastIndex).concat("\n" + line));
			}
		});

		return cleanContent;
	}

	private void checkLanguage(ArrayList<String> content)
	{
		if (content.isEmpty())
		{
			return;
		}
		// Dates are separated by a dot in Germany
		mLanguage = (content.get(0).substring(2, 3).equals(".")) ? 0 : 1;
	}

	private void setUsernameAndMessage(String line, int index)
	{
		final String[] splitContent = line.split(": ");

		/*
		 * If the length of the first index is equal to the length of the whole
		 * Username-and-Message-Segment that means that there is no Username defined
		 * 
		 * Example: "Name: Message" => [Name, Message] whereas "Message" => [Message]
		 */
		if (splitContent[0].length() == line.length())
		{
			mBody[index][2] = "WhatsAppInfoMessage";
			mBody[index][3] = line;
			return;
		}

		mBody[index][2] = splitContent[0];

		final int UsernameLength = splitContent[0].length();

		// The Message is the everything minus the Username and the delimiter.
		mBody[index][3] = line.substring(UsernameLength + 2);
	}

	private void checkEncryptionDisclaimer()
	{
		// TODO: Not only check the first line for a disclaimer
		final String firstLine = mContent.get(0);

		if (!firstLine.contains(sEncryptionDisclaimerENG) && !firstLine.contains(sEncryptionDisclaimerGER))
		{
			hasEncriptionDisclaimer = false;
			// I don't know if I want to remove the InfoMessages or leave them
			// mContent.remove(0);
		}
	}

	public String getEncryptionDisclaimer()
	{
		if (!hasEncriptionDisclaimer)
		{
			return "This chat does not have an encryption disclaimer!";
		}
		return (mLanguage == 0) ? sEncryptionDisclaimerGER : sEncryptionDisclaimerENG;
	}

	public String[] getHeader()
	{
		return mHeader;
	}

	public void setHeader(String[] mHeader)
	{
		this.mHeader = mHeader;
	}

	public ArrayList<String> getColumn(int index)
	{
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < mBody.length; i++)
		{
			result.add(mBody[i][index]);
		}

		return result;
	}

	public String[][] getBody()
	{
		return mBody;
	}

	public String[] getUser()
	{
		return mUser;
	}
	
	public boolean isEnglishFormatting() {
		return mLanguage == 1;
	}
}
