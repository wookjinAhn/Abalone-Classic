package abaloneClassic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public abstract class GamePlayer
{
	private final int playerID;
	private Scanner scanner;
	private int playColor = 0;		// black = 1, white = 2 
	private int lineDirection = -1; 
	private int moveDirection = -1;
	
	protected String[][]board;
	protected ArrayList<String> availableAlphabet = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"));
	protected ArrayList<String> arrInput = new ArrayList<String>();
	protected ArrayList<Pair> arrPair = new ArrayList<Pair>();
	
	public GamePlayer(int playerID)
	{
		this.playerID = playerID;
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public int getPlayColor()		
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
			System.out.print(getPlayerType() + "Choose BLACK ●! First Turn.\n");
		}
		else if ((playColor.equalsIgnoreCase(strWhite)) || (playColor.equalsIgnoreCase(strW)))
		{
			this.playColor = 2;
			System.out.print(getPlayerType() + "Choose WHITE ○! Second Turn.\n");
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
	
	public void setMoveDirection(int moveDirection)
	{
		this.moveDirection = moveDirection;
	}
	
	public String[][] getBoard()
	{
		return board;
	}
	
	public void setBoard(String[][] board)
	{
		this.board = board;
	}
	
	public ArrayList<String> getAlphabet()
	{
		return availableAlphabet;
	}
	
	public int getAlphabetLastIndex()
	{
		return availableAlphabet.size() - 1;
	}
	
	public void removeLastAlphabet()
	{
		this.availableAlphabet.remove(availableAlphabet.size() - 1);
	}
	
	public ArrayList<String> getArrInput()
	{
		return arrInput;
	}
	
	public ArrayList<Pair> getArrPair()
	{
		return arrPair;
	}

	
	public void showAvailableChoose()
	{
		//System.out.print("Choose Alphabet and Direction\n");
		System.out.print("Alphabet : ");
		for (int i = 0; i < this.getAlphabet().size(); i++)
		{
			System.out.print(availableAlphabet.get(i) + " ");
		}
		System.out.print(" | size : " + availableAlphabet.size() + "\n");
//		System.out.print("Direction :  1 2 \n");
//		if (this.getPlayColor() == 1)
//		{
//			System.out.print("            3 ● 4\n");
//		}
//		else
//		{
//			System.out.print("            3 ○ 4\n");
//		}
//		System.out.print("             5 6 \n");
	}
	
	public abstract void play();
	
	public abstract boolean checkRightInput(ArrayList<Integer> arrAscii, ArrayList<String> alphabet);
	
	public abstract String getPlayerType();
}
