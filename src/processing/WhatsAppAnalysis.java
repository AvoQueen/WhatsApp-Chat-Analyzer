package processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WhatsAppAnalysis {
	private User mostMessages;
	private User longestMessage;
	private double averageMessageLength = 0;
	private String mostActiveDate;
	private int totalMessages = 0;

	private double averageMinute = 0;
	private double averageHour = 0;
	private double averageDay = 0;
	private double averageMonth = 0;
	private double averageYear = 0;

	private String dateDelimiter;

	private class User {
		String name;
		double amount;

		public User(String name, double messages)
		{
			this.name = name;
			this.amount = messages;
		}
	}

	public WhatsAppAnalysis(WhatsappFile wafile)
	{
		dateDelimiter = wafile.isEnglishFormatting() ? "/" : "\\.";
		mostMessages = new User("Nobody?", 0);
		longestMessage = new User("Nobody?", 0);

		final String[] user = wafile.getUser();
		final int[] messages = new int[user.length];
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		final String[][] data = wafile.getBody();
		
		totalMessages = data.length;

		for (int i = 0; i < user.length; i++)
		{
			map.put(user[i], i);
		}

		for (int i = 0; i < totalMessages; i++)
		{
			final int MessageLength = data[i][3].length();
			
			addToDate(data[i][0]);
			addToTime(data[i][1]);
			averageMessageLength += MessageLength;
			messages[map.get(data[i][2])]++;
			
			if(MessageLength > longestMessage.amount)
			{
				System.out.println("LONGEST MESSAGE: "+data[i][0]);
				longestMessage.amount = MessageLength;
				longestMessage.name = data[i][2];
			}
			
		}
		
		for (int i = 0; i < user.length; i++)
		{
			if(messages[i] > mostMessages.amount)
			{
				mostMessages.amount = messages[i];
				mostMessages.name = user[i];
			}
		}

		calculateAverageDivide(totalMessages);
		
		double mostMessagesInPercent = Math.round((mostMessages.amount / totalMessages) * 1E6) / 1E4;
		System.out.println(mostMessages.name + " has written the most messages with a total of "+ mostMessages.amount+ " out of "+totalMessages+" messages = ["+mostMessagesInPercent+"%].");
		System.out.println(longestMessage.name + " has written the longest message with a length of "+ longestMessage.amount+ " characters.");
		System.out.println("The average Message length is "+averageMessageLength+" characters.");
		System.out.println("The average Time is "+Math.round(averageHour)+":"+Math.round(averageMinute));
		System.out.println("The average Date is "+Math.round(averageDay)+"."+Math.round(averageMonth)+"."+Math.round(averageYear));
	}

	private void addToDate(String date)
	{
		String[] d = date.split(dateDelimiter);
		averageDay += Integer.parseInt(d[0]);
		averageMonth += Integer.parseInt(d[1]);
		averageYear += Integer.parseInt(d[2]);
	}

	private void addToTime(String time)
	{
		String[] t = time.split(":");
		averageHour += Integer.parseInt(t[0]);
		averageMinute += Integer.parseInt(t[1]);
	}

	private void calculateAverageDivide(int amount)
	{
		averageDay /= amount;
		averageHour /= amount;
		averageMinute /= amount;
		averageMonth /= amount;
		averageYear /= amount;
		averageMessageLength /= amount;
	}
	
	public VBox getStatistic(int width, int height) {
		VBox vb = new VBox(10);
		vb.setPrefSize(width, height);
		Label mostM = new Label();
		Label longestM = new Label();
		Label avL = new Label();
		Label avT = new Label();
		Label avD = new Label();
		
		double mostMessagesInPercent = Math.round((mostMessages.amount / totalMessages) * 1E6) / 1E4;
		mostM.setText(mostMessages.name + " has written the most messages with a total of "+ mostMessages.amount+ " out of "+totalMessages+" messages = ["+mostMessagesInPercent+"%].");
		longestM.setText(longestMessage.name + " has written the longest message with a length of "+ longestMessage.amount+ " characters.");
		avL.setText("The average Message length is "+averageMessageLength+" characters.");
		avT.setText("The average Time is "+Math.round(averageHour)+":"+Math.round(averageMinute));
		avD.setText("The average Date is "+Math.round(averageDay)+"."+Math.round(averageMonth)+"."+Math.round(averageYear));
		
		vb.setStyle("-fx-font-size: "+(height / 20 - 20)+";");
		
		mostM.setPrefSize(width - 20, height / 5 - 20);
		longestM.setPrefSize(width - 20, height / 5 - 20);
		avL.setPrefSize(width - 20, height / 5 - 20);
		avT.setPrefSize(width - 20, height / 5 - 20);
		avD.setPrefSize(width - 20, height / 5 - 20);
		
		vb.getChildren().addAll(mostM, longestM, avL, avT, avD);
		
		return vb;
	}
}
