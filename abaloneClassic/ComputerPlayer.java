package abaloneClassic;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends GamePlayer
{
	private final Random randomSelecter = new Random();

	public ComputerPlayer(int playerID)
	{
		super(playerID);
	}

	@Override
	public String getPlayerType()
	{
		return "AI";
	}

	@Override
	public ArrayList<String> play(ArrayList<String> alphabet)
	{
		System.out.println("Hey AI, where would you put your piece?");
		showAvailableColumns(availableColumns);

		int numberOfAvailableColumns = availableColumns.size();
		int randomNumber = randomSelecter.nextInt(numberOfAvailableColumns);

		Integer computerSelection = availableColumns.get(randomNumber);
		try
		{
			Thread.sleep(500);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("I WOULD PUT MY PIECE ON COLUMN [" + computerSelection + "] !");
		return computerSelection;
	}

}