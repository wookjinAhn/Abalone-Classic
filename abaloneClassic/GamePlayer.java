package abaloneClassic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public abstract class GamePlayer
{
	private final int playerID;
	private Scanner scanner;
	private int playColor = 0;		// black = 1, white = 2 
	private ArrayList<String> availableAlphabet = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"));
	private int lineDirection = -1; 
	private int moveDirection = -1;
	
	public GamePlayer(int playerID)
	{
		this.playerID = playerID;
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public int getPlayColor()		// playColor
	{		
		return playColor;		
	}
	
	public void setPlayColor(String playColor)
	{
		String strBlack = "black";
		String strB = "b";
		String strWhite = "White";
		String strW = "W";
		
		if ((playColor.equalsIgnoreCase(strBlack)) || (playColor.equalsIgnoreCase(strB)))
		{
			this.playColor = 1;
			System.out.print(getPlayerType() + "Choose BLACK ●! First Turn.\n\n");
		}
		else if ((playColor.equalsIgnoreCase(strWhite)) || (playColor.equalsIgnoreCase(strW)))
		{
			this.playColor = 2;
			System.out.print(getPlayerType() + "Choose WHITE ○! Second Turn.\n\n");
		}
		else
		{
			System.out.print(getPlayerType() + "Have to Choose Right Color\n");
			scanner = new Scanner(System.in);
			String strPlayColor = scanner.nextLine();
			this.setPlayColor(strPlayColor);
		}
	}
	
	public void setPlayColor(int playColor)
	{
		this.playColor = playColor;
	}
	
	public ArrayList<String> getAlphabet()
	{
		return availableAlphabet;
	}
	
	public int getLineDirection() 
	{
		return lineDirection;
	}
	
	public void setLineDirection(int lineDirection)
	{
		this.lineDirection = lineDirection;
	}
	
	public int getMoveDirection()
	{
		return moveDirection;
	}
	
	public void setMovdDirection(int moveDirection)
	{
		this.moveDirection = moveDirection;
	}

	public abstract ArrayList<String> getArrInput();

	public abstract ArrayList<String> play(ArrayList<String> alphabet);
	
	public abstract String getPlayerType();
}
