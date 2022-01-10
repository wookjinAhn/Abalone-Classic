package abaloneClassic;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Collections;

public class AbaloneClassicGame extends TwoPlayerTurnGame
{
	private Scanner scanner;
	private String[][] board = 
		{
			//    0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19   20
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}, // 0
				{" ", " ", " ", " ", " ", " ", "○", " ", "○", " ", "○", " ", "○", " ", "○", " ", " ", " ", " ", " ", " "}, // 1		//     ○ ○ ○ ○ ○    
				{" ", " ", " ", " ", " ", "○", " ", "○", " ", "○", " ", "○", " ", "○", " ", "○", " ", " ", " ", " ", " "}, // 2		//    ○ ○ ○ ○ ○ ○   
				{" ", " ", " ", " ", "·", " ", "·", " ", "○", " ", "○", " ", "○", " ", "·", " ", "·", " ", " ", " ", " "}, // 3		//   + + ○ ○ ○ + +  
				{" ", " ", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", " ", " "}, // 4		//  + + + + + + + + 
				{" ", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", " "}, // 5		// + + + + + + + + +
				{" ", " ", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", "·", " ", " ", " "}, // 6		//  + + + + + + + + 
				{" ", " ", " ", " ", "·", " ", "·", " ", "●", " ", "●", " ", "●", " ", "·", " ", "·", " ", " ", " ", " "}, // 7		//   + + ● ● ● + +  
				{" ", " ", " ", " ", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", " ", " ", " ", " "}, // 8		//    ● ● ● ● ● ●   
				{" ", " ", " ", " ", " ", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", " ", " ", " ", " ", " "}, // 9		//     ● ● ● ● ●    
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}  // 10
		};
	private ArrayList<Pair> arrPair = new ArrayList<Pair>();
	
	// Constructor
	public AbaloneClassicGame(GamePlayer firstPlayer, GamePlayer secondPlayer)
	{
		super(firstPlayer, secondPlayer);
		
		System.out.print("Avalone Classic\n");
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}

		System.out.println("Which Color do " + currentPlayer.getPlayerType() + " want to play? : White or Black");
		scanner = new Scanner(System.in);
		String strPlayColor = scanner.nextLine();
		
		currentPlayer.setPlayColor(strPlayColor);

		if (currentPlayer.getPlayerColor() == "●")
		{
			secondPlayer.setPlayColor(2);
			secondPlayer.setPlayerColor("○");
			secondPlayer.setNextPlayerColor("●");
		}
		else
		{
			secondPlayer.setPlayColor(1);
			secondPlayer.setPlayerColor("●");
			secondPlayer.setNextPlayerColor("○");
			System.out.print("turn change !\n");
			changePlayer();
		}
	}
		
	public void visualize()
	{
		int playColor = currentPlayer.getPlayColor();
		ArrayList<String> alphabet = (ArrayList<String>)currentPlayer.getAlphabet().clone();	// Shallow copy
		/*
		// Color Stone -> Alphabet
		if (playColor == 1)		// black Color
		{
			for (int i = 0; i < board.length; i++)
			{
				for (int j = 0; j < board[i].length; j++)
				{
					if (board[i][j] == "●")
					{
						board[i][j] = alphabet.get(0);
						alphabet.remove(0);
					}
				}
			}
		}
		else	// white color
		{
			for (int i = 0; i < board.length; i++)
			{
				for (int j = 0; j < board[i].length; j++)
				{
					if (board[i][j] == "○")
					{
						board[i][j] = alphabet.get(0);
						alphabet.remove(0);
					}
				}
			}
		}
		*/
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if (board[i][j] == currentPlayer.getPlayerColor())
				{
					board[i][j] = alphabet.get(0);
					alphabet.remove(0);
				}
			}
		}
		
		currentPlayer.setBoard(board);
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}
	} // End public void visualize()

	// 
	public boolean checkAlphabet(String board)
	{
		byte[] byteAscii = board.getBytes(StandardCharsets.US_ASCII);
		
		if (byteAscii[0] >= 97 && byteAscii[0] <=110)
		{
			return true;
		}
		return false;
	}

	// 1. 아무것도 없을 때. 
	// 2. 상대방 돌이 있을 때
	// 2 - 1 상대방 돌 뒤에 아무것도 없을 때.
	// 2 - 2 상대방 돌 뒤에 내 돌이 있을 때.
	public boolean checkMovable(ArrayList<Pair> arrPair)
	{
		String nextPlayerColor = currentPlayer.getNextPlayerColor();
		int lineDirection = currentPlayer.getLineDirection();
		int moveDirection = currentPlayer.getMoveDirection();
		int row = 0;
		int column = 0;
		int count = 0;
		/*
		if (currentPlayer.getPlayColor() == 1)
		{
			nextPlayerColor = "○";
		}
		else
		{
			nextPlayerColor = "●";
		}
		*/
		switch(lineDirection)
		{
		case 0:
			row = arrPair.get(0).getRowInt();
			column = arrPair.get(0).getColumnInt();
			switch(moveDirection)
			{
			case 1:
				if (board[row - 1][column - 1] == "·")
				{
					return true;
				}
				return false;
			case 2:
				if (board[row - 1][column + 1] == "·")
				{
					return true;
				}
				return false;
			case 3:
				if (board[row][column - 2] == "·")
				{
				return true;
				}
				return false;
			case 4:
				if (board[row][column + 2] == "·")
				{
					return true;
				}
				return false;
			case 5:
				if (board[row + 1][column - 1] == "·")
				{
					return true;
				}
				return false;
			case 6:
				if (board[row + 1][column + 1] == "·")
				{
					return true;
				}
				return false;
			}
			
		case 1:
			switch(moveDirection)
			{
			case 1:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					if (board[row][column] ==  "·")
					{
						count++;
					}
				}
				
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 2:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					if (board[row][column] ==  "·")
					{
						count++;
					}
				}
				
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 3:
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row][column - 2] == "·")  // + ● ●
					{
						return true;
					}
					else if(checkAlphabet(board[row][column - 2]) || board[row][column - 2] == " ")	// ● ● ●
					{
						return false;
					}
					else if(board[row][column - 2] == nextPlayerColor)	
					{
						if (board[row][column - 4] == " " || board[row][column - 4] == "·")	// ○ ● ●  or  + ○ ● ●  
						{
							return true;
						}
						else	// ○ ○ ● ●  or  ● ○ ● ●
						{
							return false;
						}
					}
				}
				else if (arrPair.size() == 3)
				{
					if (board[row][column - 2] == "·")	// + ● ● ●
					{
						return true;
					}
					else if (checkAlphabet(board[row][column - 2]) || board[row][column - 2] == " ")	// ● ● ● ●
					{
						return false;
					}
					else if (board[row][column - 2] == nextPlayerColor)	// ○ ● ● ●
					{
						if (board[row][column - 4] == " " ||board[row][column - 4] == "·")	// + ○ ● ● ●
						{
							return true;
						}
						else if (checkAlphabet(board[row][column - 4]))	// ● ○ ● ● ●
						{
							return false;
						}
						else if (board[row][column - 4] == nextPlayerColor)	// ○ ○ ● ● ●
						{
							if (board[row][column - 6] == " " || board[row][column - 6] == "·")	// ○ ○ ● ● ●  or  + ○ ○ ● ● ●
							{
								return true;
							}
							else	// ○ ○ ○ ● ● ●  or  ● ○ ○ ● ● ●
							{
								return false;
							}
						}
					}
				}
				
			case 4:
				row = arrPair.get(arrPair.size() - 1).getRowInt();
				column = arrPair.get(arrPair.size() - 1).getColumnInt();
								
				if (arrPair.size() == 2)
				{
					if (board[row][column + 2] == "·")  // ● ● +
					{
						return true;
					}
					else if(checkAlphabet(board[row][column + 2]) || board[row][column + 2] == " ")	// ● ● ●
					{
						return false;
					}
					else if(board[row][column + 2] == nextPlayerColor)	
					{
						if (board[row][column + 4] == " " || board[row][column + 4] == "·")	// ● ● ○  or  ● ● ○ + 
						{
							return true;
						}
						else	// ○ ○ ● ●  or  ● ○ ● ●
						{
							return false;
						}
					}
				}
				else if (arrPair.size() == 3)
				{
					if (board[row][column + 2] == "·")	// ● ● ● +
					{
						return true;
					}
					else if (checkAlphabet(board[row][column + 2]) || board[row][column + 2] == " ")	// ● ● ● ●
					{
						return false;
					}
					else if (board[row][column + 2] == nextPlayerColor)	// ● ● ● ○
					{
						if (board[row][column + 4] == " " || board[row][column + 4] == "·")	// ● ● ● ○ +
						{
							return true;
						}
						else if (checkAlphabet(board[row][column + 4]))	// ● ● ● ○ ●
						{
							return false;
						}
						else if (board[row][column + 4] == nextPlayerColor)	// ● ● ● ○ ○
						{
							if (board[row][column + 6] == " " || board[row][column + 6] == "·")	// ● ● ● ○ ○  or  ● ● ● ○ ○ +
							{
								return true;
							}
							else	// ● ● ● ○ ○ ○  or  ● ● ● ○ ○ ●
							{
								return false;
							}
						}
					}
				}
				
			case 5:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					if (board[row][column] ==  "·")
					{
						count++;
					}
				}
				
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 6:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					if (board[row][column] ==  "·")
					{
						count++;
					}
				}
				
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
			}
		case 2:
			switch(moveDirection)
			{
			case 1:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					if (board[row][column] == "·")
					{
						count++;
					}
				}
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 2:
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row - 1][column + 1] == "·")
					{
						return true;
					}
					else if (checkAlphabet(board[row - 1][column + 1]) || board[row - 1][column + 1] == " ")
					{
						return false;
					}
					else if (board[row - 1][column + 1] == nextPlayerColor)
					{
						if (board[row - 2][column + 2] == " " || board[row - 2][column + 2] == "·")
						{
							return true;
						}
						else
						{
							return false;
						}
					}
				}
				else if (arrPair.size() == 3)
				{
					if (board[row - 1][column + 1] == "·")
					{
						return true;
					}
					else if (checkAlphabet(board[row - 1][column + 1]) || board[row - 1][column + 1] == " ")
					{
						return false;
					}
					else if (board[row - 1][column + 1] == nextPlayerColor)
					{
						if (board[row - 2][column + 2] == " " || board[row - 2][column + 2] == "·")
						{
							return true;
						}
						else if (checkAlphabet(board[row - 2][column + 2]))
						{
							return false;
						}
						else if (board[row - 2][column + 2] == nextPlayerColor)
						{
							if (board[row - 3][column + 3] == " " || board[row - 3][column + 3] == "·")
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}
				}
				
			case 3:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() - 2;
					
					if (board[row][column] == "·")
					{
						count++;
					}
				}
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 4:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() + 2;
					
					if (board[row][column] == "·")
					{
						count++;
					}
				}
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 5:
				
				row = arrPair.get(arrPair.size() - 1).getRowInt();
				column = arrPair.get(arrPair.size() - 1).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row + 1][column - 1] == "·")
					{
						return true;
					}
					else if (checkAlphabet(board[row + 1][column - 1]) || board[row + 1][column - 1] == " ")
					{
						return false;
					}
					else if (board[row + 1][column - 1] == nextPlayerColor)
					{
						if (board[row + 2][column - 2] == " " || board[row + 2][column - 2] == "·")
						{
							return true;
						}
						else
						{
							return false;
						}
					}
				}
				else if (arrPair.size() == 3)
				{
					if (board[row + 1][column - 1] == "·")
					{
						return true;
					}
					else if (checkAlphabet(board[row + 1][column - 1]) || board[row + 1][column - 1] == " ")
					{
						return false;
					}
					else if (board[row + 1][column - 1] == nextPlayerColor)
					{
						if (board[row + 2][column - 2] == " " || board[row + 2][column - 2] == "·")
						{
							return true;
						}
						else if (checkAlphabet(board[row + 2][column - 2]))
						{
							return false;
						}
						else if (board[row + 2][column - 2] == nextPlayerColor)
						{
							if (board[row + 3][column - 3] == " " || board[row + 3][column - 3] == "·")
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}
				}
				
			case 6:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					if (board[row][column] == "·")
					{
						count++;
					}
				}
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			}
		case 3:
			switch(moveDirection)
			{
			case 1:
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row - 1][column - 1] == "·")  // ● ● +
					{
						return true;
					}
					else if(checkAlphabet(board[row - 1][column - 1]) || board[row][column + 2] == " ")	// ● ● ●
					{
						return false;
					}
					else if(board[row - 1][column - 1] == nextPlayerColor)	
					{
						if (board[row - 2][column - 2] == " " || board[row - 2][column - 2] == "·")	// ● ● ○  or  ● ● ○ + 
						{
							return true;
						}
						else	// ○ ○ ● ●  or  ● ○ ● ●
						{
							return false;
						}
					}		
				}
				else if (arrPair.size() == 3)
				{
					if (board[row - 1][column - 1] == "·")
					{
						return true;
					}
					else if (checkAlphabet(board[row - 1][column - 1]) || board[row - 1][column - 1] == " ")
					{
						return false;
					}
					else if (board[row - 1][column - 1] == nextPlayerColor)
					{
						if (board[row - 2][column - 2] == " " || board[row - 2][column - 2] == "·")
						{
							return true;
						}
						else if (checkAlphabet(board[row - 2][column - 2]))
						{
							return false;
						}
						else if (board[row - 2][column - 2] == nextPlayerColor)
						{
							if (board[row - 3][column - 3] == " " || board[row - 3][column - 3] == "·")
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}			
				}
				
			case 2:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					if (board[row][column] == "·")
					{
						count++;
					}
				}
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 3:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() - 2;
					
					if (board[row][column] == "·")
					{
						count++;
					}
				}
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 4:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() + 2;
					
					if (board[row][column] == "·")
					{
						count++;
					}
				}
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 5:
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					if (board[row][column] == "·")
					{
						count++;
					}
				}
				if (count == arrPair.size())
				{
					return true;
				}
				return false;
				
			case 6:
				row = arrPair.get(arrPair.size() - 1).getRowInt();
				column = arrPair.get(arrPair.size() - 1).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row + 1][column + 1] == "·")
					{
						return true;
					}
					else if (checkAlphabet(board[row + 1][column + 1]) || board[row + 1][column + 1] == " ")
					{
						return false;
					}
					else if (board[row + 1][column + 1] == nextPlayerColor)
					{
						if (board[row + 2][column + 2] == " " || board[row + 2][column + 2] == "·")
						{
							return true;
						}
						else
						{
							return false;
						}
					}
				}
				else if (arrPair.size() == 3)
				{
					if (board[row + 1][column + 1] == "·")
					{
						return true;
					}
					else if (checkAlphabet(board[row + 1][column + 1]) || board[row + 1][column + 1] == " ")
					{
						return false;
					}
					else if (board[row + 1][column + 1] == nextPlayerColor)
					{
						if (board[row + 2][column + 2] == " " || board[row + 2][column + 2] == "·")
						{
							return true;
						}
						else if (checkAlphabet(board[row + 2][column + 2]))
						{
							return false;
						}
						else if (board[row + 2][column + 2] == nextPlayerColor)
						{
							if (board[row + 3][column + 3] == " " || board[row + 3][column + 3] == "·")
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}
				}
				
			}
			
		default:
			return true;	
		}
	} // End public boolean checkMovable()

	public void moveLine(ArrayList<Pair> arrPair)
	{
		String nextPlayerColor = currentPlayer.getNextPlayerColor();
		int lineDirection = currentPlayer.getLineDirection();
		int moveDirection = currentPlayer.getMoveDirection();
		int row = 0;
		int column = 0;
		/*
		if (currentPlayer.getPlayColor() == 1)
		{
			nextPlayerColor = "○";
		}
		else
		{
			nextPlayerColor = "●";
		}
		*/
		// 일단 방해물이 없다고 가정하고 움직임.
		switch(lineDirection)
		{
		case 0:	// 1개 Pair == 아무 방향 이동 가능
			switch(moveDirection)
			{
			case 1:
				//System.out.print("Swith case : 0, 1\n");
				row = arrPair.get(0).getRowInt() - 1;
				column = arrPair.get(0).getColumnInt() - 1;

				if (board[row][column] == "·") // 빈공간이면
				{
					board[row][column] = Integer.toString(1);
					board[row + 1][column + 1] = "·";
				}
				break;
				
			case 2:
				//System.out.print("Swith case : 0, 2\n");
				row = arrPair.get(0).getRowInt() - 1;
				column = arrPair.get(0).getColumnInt() + 1;
				
				if (board[row][column] == "·")
				{
					board[row][column] = Integer.toString(1);
					board[row + 1][column - 1] = "·";
				}
				break;
				
			case 3:
				//System.out.print("Swith case : 0, 3\n");
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt() - 2;
				
				if (board[row][column] == "·")
				{
					board[row][column] = Integer.toString(1);
					board[row][column + 2] = "·";
				}
				break;
				
			case 4:
				//System.out.print("Swith case : 0, 4\n");
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt() + 2;
				
				if (board[row][column] == "·")
				{
					board[row][column] = Integer.toString(1);
					board[row][column - 2] = "·";
				}
				break;
				
			case 5:
				//System.out.print("Swith case : 0, 5\n");
				row = arrPair.get(0).getRowInt() + 1;
				column = arrPair.get(0).getColumnInt() - 1;
				
				if (board[row][column] == "·")
				{
					board[row][column] = Integer.toString(1);
					board[row - 1][column + 1] = "·";
				}
				break;
				
			case 6:
				//System.out.print("Swith case : 0, 6\n");
				row = arrPair.get(0).getRowInt() + 1;
				column = arrPair.get(0).getColumnInt() + 1;
				
				if (board[row][column] == "·")
				{
					board[row][column] = Integer.toString(1);
					board[row - 1][column - 1] = "·";
				}
				break;
			}
			break; // End switch(lineDirection == 2)
		
		case 1: // 가로
			switch(moveDirection)	//  1 2 
			{						// 3 ● 4
			case 1:					//  5 6
				//System.out.print("Swith case : 1, 1\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column + 1] = "·";
				}
				break;
				
			case 2:
				//System.out.print("Swith case : 1, 2\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column - 1] = "·";
				}
				break;
				
			case 3:
				//System.out.print("Swith case : 1, 3\n");
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row][column - 2] == nextPlayerColor)
					{
						board[row][column - 4] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[row][column - 2] == nextPlayerColor && board[row][column - 4] == nextPlayerColor)
					{
						board[row][column - 6] = nextPlayerColor;
					}
					else if (board[row][column - 2] == nextPlayerColor && (board[row][column - 4] == "·" || board[row][column - 4] == " "))
					{
						board[row][column  - 4] = nextPlayerColor;
					}
				}
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() - 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column + 2] = "·";
				}
				break;
				
			case 4:
				//System.out.print("Swith case : 1, 4\n");
				row = arrPair.get(arrPair.size() - 1).getRowInt();
				column = arrPair.get(arrPair.size() - 1).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row][column + 2] == nextPlayerColor)
					{
						board[row][column + 4] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[row][column + 2] == nextPlayerColor && board[row][column + 4] == nextPlayerColor)
					{
						board[row][column + 6] = nextPlayerColor;
					}
					else if (board[row][column + 2] == nextPlayerColor && (board[row][column + 4] == "·" || board[row][column + 4] == " "))
					{
						board[row][column + 4] = nextPlayerColor;
					}
				}
				
				for (int i = arrPair.size() - 1; i >= 0; i--)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() + 2;

					board[row][column] = Integer.toString(i);
					board[row][column - 2] = "·";
				}
				break;
				
			case 5:
				//System.out.print("Swith case : 1, 5\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column + 1] = "·";
				}
				break;
				
			case 6:
				//System.out.print("Swith case : 1, 6\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column - 1] = "·";
				}
				break;
			}
			break; // End switch(lineDirection == 1)
			
		case 2: // 왼쪽 아래					(5,7)
			switch(moveDirection)	//  1 2 	(6,6)
			{						// 3 ● 4	(7,5)
			case 1:					//  5 6		(8,4)	(9,3)
				//System.out.print("Swith case : 2, 1\n");
				
				for (int i = 0;  i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column + 1] = "·";
				}				
				break;
				
			case 2:				// (6,6)
				//System.out.print("Swith case : 2, 2\n");
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row - 1][column + 1] == nextPlayerColor)
					{
						board[row - 2][column + 2] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[row - 1][column + 1] == nextPlayerColor && board[row - 2][column + 2] == nextPlayerColor)
					{
						board[row - 3][column + 3] = nextPlayerColor;
					}
					else if (board[row - 1][column + 1] == nextPlayerColor && (board[row - 2][column + 2] == "·" || board[row][column + 2] == " "))
					{
						board[row - 2][column + 2] = nextPlayerColor;
					}
				}
								
				for (int i = 0;  i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column - 1] = "·";
				}				
				break;
				
			case 3:
				//System.out.print("Swith case : 2, 3\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() - 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column + 2] = "·";
				}		
				break;
				
			case 4:
				//System.out.print("Swith case : 2, 4\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() + 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column - 2] = "·";
				}	
				break;
			case 5:
				//System.out.print("Swith case : 2, 5\n");
				row = arrPair.get(arrPair.size() - 1).getRowInt();
				column = arrPair.get(arrPair.size() - 1).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row + 1][column - 1] == nextPlayerColor)
					{
						board[row + 2][column - 2] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[row + 1][column - 1] == nextPlayerColor && board[row + 2][column - 2] == nextPlayerColor)
					{
						board[row + 3][column - 3] = nextPlayerColor;
					}
					else if (board[row + 1][column - 1] == nextPlayerColor && (board[row + 2][column - 2] == "·" || board[row + 2][column - 2] == " "))
					{
						board[row + 2][column - 2] = nextPlayerColor;
					}
				}
				
				for (int i = arrPair.size() - 1;  i >= 0 ; i--)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column + 1] = "·";
				}				
				break;
				
			case 6:
				//System.out.print("Swith case : 2, 6\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column - 1] = "·";
				}	
				break;
			}
			break; // End switch(lineDirection == 2)
		
		case 3: // 오른쪽 아래
			switch(moveDirection)
			{
			case 1:	
				//System.out.print("Swith case : 3, 1\n");
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt();
				
				if (arrPair.size() == 2)
				{
					if (board[row - 1][column - 1] == nextPlayerColor)
					{
						board[row - 2][column - 2] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[row - 1][column - 1] == nextPlayerColor && board[row - 2][column - 2] == nextPlayerColor)
					{
						board[row - 3][column - 3] = nextPlayerColor;
					}
					else if (board[row - 1][column - 1] == nextPlayerColor && (board[row - 2][column - 2] == "·" || board[row - 2][column - 2] == " "))
					{
						board[row - 2][column - 2] = nextPlayerColor;
					}
				}
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column + 1] = "·";
				}
				break;
			case 2:
				//System.out.print("Swith case : 3, 2\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column - 1] = "·";
				}
				break;
			case 3:
				//System.out.print("Swith case : 3, 3\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() - 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column + 2] = "·";
				}
				break;
			case 4:
				//System.out.print("Swith case : 3, 4\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() + 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column - 2] = "·";
				}
				break;
			case 5:
				//System.out.print("Swith case : 3, 5\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column + 1] = "·";
				}
				break;
			case 6:
				//System.out.print("Swith case : 3, 6\n");
				row = arrPair.get(arrPair.size() - 1).getRowInt();
				column = arrPair.get(arrPair.size() - 1).getColumnInt();
				if (arrPair.size() == 2)
				{
					if (board[row + 1][column + 1] == nextPlayerColor)
					{
						board[row + 2][column + 2] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[row + 1][column + 1] == nextPlayerColor && board[row + 2][column + 2] == nextPlayerColor)
					{
						board[row + 3][column + 3] = nextPlayerColor;
					}
					else if (board[row + 1][column + 1] == nextPlayerColor && (board[row + 2][column + 2] == "·" || board[row + 2][column + 2] == " "))
					{
						board[row + 2][column + 2] = nextPlayerColor;
					}
				}
				
				for (int i = arrPair.size() - 1; i >= 0; i--)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column - 1] = "·";
				}
				break;
			}
			break; // End switch(lineDirction == 3)
		}
	}
	
	public void checkOut()
	{
		for (int i = 0;  i < 20; i ++)
		{
			if (board[0][i] != " ")
			{
				board[0][i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
			
			if (board[10][i] != " ")
			{
				board[10][i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
		}
		
		for (int i = 0; i < 5; i++)
		{
			if (board[1][i] != " " || board[1][20 - i] != " ")
			{
				board[1][i] = " ";
				board[1][20 - i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
			if (board[9][i] != " " || board[9][20 - i] != " ")
			{
				board[9][i] = " ";
				board[9][20 - i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
		}
		
		for (int i = 0; i < 4; i++)
		{
			if (board[2][i] != " " || board[2][20 - i] != " ")
			{
				board[2][i] = " ";
				board[2][20 - i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
			if (board[8][i] != " " || board[8][20 - i] != " ")
			{
				board[8][i] = " ";
				board[8][20 - i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
		}

		for (int i = 0; i < 3; i++)
		{
			if (board[3][i] != " " || board[3][20 - i] != " ")
			{
				board[3][i] = " ";
				board[2][20 - i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
			if (board[7][i] != " " || board[7][20 - i] != " ")
			{
				board[7][i] = " ";
				board[7][20 - i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
		}

		for (int i = 0; i < 2; i++)
		{
			if (board[4][i] != " " || board[4][20 - i] != " ")
			{
				board[4][i] = " ";
				board[4][20 - i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
			if (board[6][i] != " " || board[6][20 - i] != " ")
			{
				board[6][i] = " ";
				board[6][20 - i] = " ";
				nextPlayer.removeLastAlphabet();
				return;
			}
		}
		
		if (board[5][0] != " " || board[5][20] != " ")
		{
			board[5][0] = " ";
			board[5][20] = " ";
			nextPlayer.removeLastAlphabet();
			return;
		}
		
	}
	
	// input 값이 제대로 들어왔는지 체크. 
	// 3. 이동 방향으로 이동할 수 있는지.
	public void play()	
	{						
		currentPlayer.play();
		
//		ArrayList<String> arrInput = currentPlayer.getArrInput();
//		int moveDirection = Integer.parseInt(arrInput.get(arrInput.size() - 1));
//		currentPlayer.setMovdDirection(moveDirection);

		if (!checkMovable(currentPlayer.getArrPair()))	// 움직일 수 없을 때
		{
			play();
		}
		else // 움직일 수 있을 때
		{
			//System.out.print("lineDirection : " + currentPlayer.getLineDirection() + "\n");
			//System.out.print("moveDirection : " + currentPlayer.getMoveDirection() + "\n");
			//System.out.print("Pair size : " + currentPlayer.getArrPair().size() + " | ");
			//for (int i = 0; i < currentPlayer.getArrPair().size(); i++)
			//{
			//	System.out.print("(" + currentPlayer.getArrPair().get(i).getRowInt() + ", " + currentPlayer.getArrPair().get(i).getColumnInt() + ") ");
			//}
			//System.out.print("\n");
			moveLine(currentPlayer.getArrPair());
		}
	} // public void play()
	
	public boolean checkGameOver()
	{
		// 상대방 돌을 6개 먼저 밀어내는 사람이 이김. == 상대방 돌이 8개가 남아있으면 
		if (nextPlayer.getAlphabet().size() == 8)
		{
			return true;
		}
		return false;
	} // public boolean checkGameOver()
	
	public void changePlay() 
	{
		//int playerColor = currentPlayer.getPlayColor();
		String playerColor = currentPlayer.getPlayerColor();
		String nextPlayerColor = currentPlayer.getNextPlayerColor();
		//ArrayList<String> alphabet = (ArrayList<String>)currentPlayer.getAlphabet().clone();	// Shallow copy
		/*
		if (playColor == 1)		// black Color
		{
			for (int i = 0; i < board.length; i++)
			{
				for (int j = 0; j < board[i].length; j++)
				{
					if (board[i][j] != "·" && board[i][j] != " " && board[i][j] != "○")
					{
						board[i][j] = "●";
					}
				}
			}
		}
		else	// white color
		{
			for (int i = 0; i < board.length; i++)
			{
				for (int j = 0; j < board[i].length; j++)
				{
					if (board[i][j] != "·" && board[i][j] != " " && board[i][j] != "●")
					{
						board[i][j] = "○";
					}
				}
			}
		}
		*/
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if (board[i][j] != "·" && board[i][j] != " " && board[i][j] != nextPlayerColor)
				{
					board[i][j] = playerColor;
				}
			}
		}
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}
		
		arrPair.clear();
		
		// sleep 1.5 second
		try{
		    Thread.sleep(1500);
		}catch(InterruptedException e){
		    e.printStackTrace();
		}
		
		changePlayer();
	}
} // public void changePlay() 

