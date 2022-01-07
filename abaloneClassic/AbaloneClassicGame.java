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
				{" ", " ", " ", " ", "+", " ", "+", " ", "○", " ", "○", " ", "○", " ", "+", " ", "+", " ", " ", " ", " "}, // 3		//   + + ○ ○ ○ + +  
				{" ", " ", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", " ", " "}, // 4		//  + + + + + + + + 
				{" ", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", " "}, // 5		// + + + + + + + + +
				{" ", " ", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", " ", " "}, // 6		//  + + + + + + + + 
				{" ", " ", " ", " ", "+", " ", "+", " ", "●", " ", "●", " ", "●", " ", "+", " ", "+", " ", " ", " ", " "}, // 7		//   + + ● ● ● + +  
				{" ", " ", " ", " ", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", " ", " ", " ", " "}, // 8		//    ● ● ● ● ● ●   
				{" ", " ", " ", " ", " ", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", " ", " ", " ", " ", " "}, // 9		//     ● ● ● ● ●    
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}  // 10
		};
	private ArrayList<Pair> arrPair = new ArrayList<Pair>();
	
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

		if (currentPlayer.getPlayColor() == 1)
		{
			secondPlayer.setPlayColor(2);
		}
		else
		{
			secondPlayer.setPlayColor(1);
			System.out.print("turn change !\n");
			changePlayer();
		}
	}
		
	public void visualize()
	{
		int playColor = currentPlayer.getPlayColor();
		ArrayList<String> alphabet = (ArrayList<String>)currentPlayer.getAlphabet().clone();	// Shallow copy
		
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
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
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
	
	// 2. 문자가 2개 이상이면 한 줄로 입력 되었는지.
	public boolean checkInLine(ArrayList<String> arrInput)
	{				
		for (int itarr = 0; itarr < arrInput.size() - 1; itarr++)	// 마지막 direction 빼고 비교
		{
			String strIt = arrInput.get(itarr);
			for (int i = 0; i < board.length; i++)
			{
				for (int j = 0; j < board[i].length; j++)
				{
					if (board[i][j].equals(strIt))	// 문자 같으면 좌표값 arrPair에 
					{
						Pair index = new Pair(i,j);	// (행, 열)
						arrPair.add(index);
					}
				}
			}
		}
		
		// 행 기준으로 정렬
		IndexComparator indexComparator = new IndexComparator();
		Collections.sort(arrPair, indexComparator);	
				
		// 0 : 하나일 때 == 아무 방향 움직이기 가능
		// 1 : 가로
		// 2 : 왼쪽 아래
		// 3 : 오른쪽 아래
		// 하나
		if (arrPair.size() == 1)
		{
			currentPlayer.setLineDirection(0);
			return true;
		}
		
		// 둘 셋, 
		int garoCount = 1;
		int leftDiagonalCount = 1;
		int rightDiagonalCount = 1;
		for (int i = 0; i < arrPair.size() - 1; i++)
		{
			int firstRow = arrPair.get(i).getRowInt();
			int firstColumn = arrPair.get(i).getColumnInt();
			int secondRow = arrPair.get(i+1).getRowInt();
			int secondColumn = arrPair.get(i+1).getColumnInt();
			// 가로
			if (secondRow == firstRow && secondColumn == firstColumn + 2)
			{
				garoCount++;
			}
			// 왼쪽 아래
			else if (secondRow == firstRow + 1 && secondColumn == firstColumn - 1)
			{
				leftDiagonalCount++;
			}
			// 오른쪽 아래
			else if (secondRow == firstRow + 1 && secondColumn == firstColumn + 1)
			{
				rightDiagonalCount++;
			}
		}
				
		if (garoCount == arrPair.size())
		{
			currentPlayer.setLineDirection(1);
			return true;
		}
		else if (leftDiagonalCount == arrPair.size())
		{
			currentPlayer.setLineDirection(2);
			return true;
		}
		else if (rightDiagonalCount == arrPair.size())
		{
			currentPlayer.setLineDirection(3);
			return true;
		}
		else // 직선이 아닐 때
		{
			arrPair.clear();
			return false;
		}
	} // End public boolean checkInLine(ArrayList<String> arrInput)
		
	// 1. 아무것도 없을 때. 
	// 2. 상대방 돌이 있을 때
	// 2 - 1 상대방 돌 뒤에 아무것도 없을 때.
	// 2 - 2 상대방 돌 뒤에 내 돌이 있을 때.
	public boolean checkMovable(ArrayList<Pair> arrPair)
	{
		String nextPlayerColor;
		int lineDirection = currentPlayer.getLineDirection();
		int moveDirection = currentPlayer.getMoveDirection();
		int row = 0;
		int column = 0;
		int count = 0;
		
		if (currentPlayer.getPlayColor() == 1)
		{
			nextPlayerColor = "○";
		}
		else
		{
			nextPlayerColor = "●";
		}
		
		switch(lineDirection)
		{
		case 0:
			row = arrPair.get(0).getRowInt();
			column = arrPair.get(0).getColumnInt();
			switch(moveDirection)
			{
			case 1:
				if (board[row - 1][column - 1] == "+")
				{
					return true;
				}
				return false;
			case 2:
				if (board[row - 1][column + 1] == "+")
				{
					return true;
				}
				return false;
			case 3:
				if (board[row][column - 2] == "+")
				{
				return true;
				}
				return false;
			case 4:
				if (board[row][column + 2] == "+")
				{
					return true;
				}
				return false;
			case 5:
				if (board[row + 1][column - 1] == "+")
				{
					return true;
				}
				return false;
			case 6:
				if (board[row + 1][column + 1] == "+")
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
					
					if (board[row][column] ==  "+")
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
					
					if (board[row][column] ==  "+")
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
					if (board[row][column - 2] == "+")  // + ● ●
					{
						return true;
					}
					else if(checkAlphabet(board[row][column - 2]) || board[row][column - 2] == " ")	// ● ● ●
					{
						return false;
					}
					else if(board[row][column - 2] == nextPlayerColor)	
					{
						if (board[row][column - 4] == " " || board[row][column - 4] == "+")	// ○ ● ●  or  + ○ ● ●  
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
					if (board[row][column - 2] == "+")	// + ● ● ●
					{
						return true;
					}
					else if (checkAlphabet(board[row][column - 2]) || board[row][column - 2] == " ")	// ● ● ● ●
					{
						return false;
					}
					else if (board[row][column - 2] == nextPlayerColor)	// ○ ● ● ●
					{
						if (board[row][column - 4] == " " ||board[row][column - 4] == "+")	// + ○ ● ● ●
						{
							return true;
						}
						else if (checkAlphabet(board[row][column - 4]))	// ● ○ ● ● ●
						{
							return false;
						}
						else if (board[row][column - 4] == nextPlayerColor)	// ○ ○ ● ● ●
						{
							if (board[row][column - 6] == " " || board[row][column - 6] == "+")	// ○ ○ ● ● ●  or  + ○ ○ ● ● ●
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
					if (board[row][column + 2] == "+")  // ● ● +
					{
						return true;
					}
					else if(checkAlphabet(board[row][column + 2]) || board[row][column + 2] == " ")	// ● ● ●
					{
						return false;
					}
					else if(board[row][column + 2] == nextPlayerColor)	
					{
						if (board[row][column + 4] == " " || board[row][column + 4] == "+")	// ● ● ○  or  ● ● ○ + 
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
					if (board[row][column + 2] == "+")	// ● ● ● +
					{
						return true;
					}
					else if (checkAlphabet(board[row][column + 2]) || board[row][column + 2] == " ")	// ● ● ● ●
					{
						return false;
					}
					else if (board[row][column + 2] == nextPlayerColor)	// ● ● ● ○
					{
						if (board[row][column + 4] == " " || board[row][column + 4] == "+")	// ● ● ● ○ +
						{
							return true;
						}
						else if (checkAlphabet(board[row][column + 4]))	// ● ● ● ○ ●
						{
							return false;
						}
						else if (board[row][column + 4] == nextPlayerColor)	// ● ● ● ○ ○
						{
							if (board[row][column + 6] == " " || board[row][column + 6] == "+")	// ● ● ● ○ ○  or  ● ● ● ○ ○ +
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
					
					if (board[row][column] ==  "+")
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
					
					if (board[row][column] ==  "+")
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
					
					if (board[row][column] == "+")
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
					if (board[row - 1][column + 1] == "+")
					{
						return true;
					}
					else if (checkAlphabet(board[row - 1][column + 1]) || board[row - 1][column + 1] == " ")
					{
						return false;
					}
					else if (board[row - 1][column + 1] == nextPlayerColor)
					{
						if (board[row - 2][column + 2] == " " || board[row - 2][column + 2] == "+")
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
					if (board[row - 1][column + 1] == "+")
					{
						return true;
					}
					else if (checkAlphabet(board[row - 1][column + 1]) || board[row - 1][column + 1] == " ")
					{
						return false;
					}
					else if (board[row - 1][column + 1] == nextPlayerColor)
					{
						if (board[row - 2][column + 2] == " " || board[row - 2][column + 2] == "+")
						{
							return true;
						}
						else if (checkAlphabet(board[row - 2][column + 2]))
						{
							return false;
						}
						else if (board[row - 2][column + 2] == nextPlayerColor)
						{
							if (board[row - 3][column + 3] == " " || board[row - 3][column + 3] == "+")
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
					
					if (board[row][column] == "+")
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
					
					if (board[row][column] == "+")
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
					if (board[row + 1][column - 1] == "+")
					{
						return true;
					}
					else if (checkAlphabet(board[row + 1][column - 1]) || board[row + 1][column - 1] == " ")
					{
						return false;
					}
					else if (board[row + 1][column - 1] == nextPlayerColor)
					{
						if (board[row + 2][column - 2] == " " || board[row + 2][column - 2] == "+")
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
					if (board[row + 1][column - 1] == "+")
					{
						return true;
					}
					else if (checkAlphabet(board[row + 1][column - 1]) || board[row + 1][column - 1] == " ")
					{
						return false;
					}
					else if (board[row + 1][column - 1] == nextPlayerColor)
					{
						if (board[row + 2][column - 2] == " " || board[row + 2][column - 2] == "+")
						{
							return true;
						}
						else if (checkAlphabet(board[row + 2][column - 2]))
						{
							return false;
						}
						else if (board[row + 2][column - 2] == nextPlayerColor)
						{
							if (board[row + 3][column - 3] == " " || board[row + 3][column - 3] == "+")
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
					
					if (board[row][column] == "+")
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
					if (board[row - 1][column - 1] == "+")  // ● ● +
					{
						return true;
					}
					else if(checkAlphabet(board[row - 1][column - 1]) || board[row][column + 2] == " ")	// ● ● ●
					{
						return false;
					}
					else if(board[row - 1][column - 1] == nextPlayerColor)	
					{
						if (board[row - 2][column - 2] == " " || board[row - 2][column - 2] == "+")	// ● ● ○  or  ● ● ○ + 
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
					if (board[row - 1][column - 1] == "+")
					{
						return true;
					}
					else if (checkAlphabet(board[row - 1][column - 1]) || board[row - 1][column - 1] == " ")
					{
						return false;
					}
					else if (board[row - 1][column - 1] == nextPlayerColor)
					{
						if (board[row - 2][column - 2] == " " || board[row - 2][column - 2] == "+")
						{
							return true;
						}
						else if (checkAlphabet(board[row - 2][column - 2]))
						{
							return false;
						}
						else if (board[row - 2][column - 2] == nextPlayerColor)
						{
							if (board[row - 3][column - 3] == " " || board[row - 3][column - 3] == "+")
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
					
					if (board[row][column] == "+")
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
					
					if (board[row][column] == "+")
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
					
					if (board[row][column] == "+")
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
					
					if (board[row][column] == "+")
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
					if (board[row + 1][column + 1] == "+")
					{
						return true;
					}
					else if (checkAlphabet(board[row + 1][column + 1]) || board[row + 1][column + 1] == " ")
					{
						return false;
					}
					else if (board[row + 1][column + 1] == nextPlayerColor)
					{
						if (board[row + 2][column + 2] == " " || board[row + 2][column + 2] == "+")
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
					if (board[row + 1][column + 1] == "+")
					{
						return true;
					}
					else if (checkAlphabet(board[row + 1][column + 1]) || board[row + 1][column + 1] == " ")
					{
						return false;
					}
					else if (board[row + 1][column + 1] == nextPlayerColor)
					{
						if (board[row + 2][column + 2] == " " || board[row + 2][column + 2] == "+")
						{
							return true;
						}
						else if (checkAlphabet(board[row + 2][column + 2]))
						{
							return false;
						}
						else if (board[row + 2][column + 2] == nextPlayerColor)
						{
							if (board[row + 3][column + 3] == " " || board[row + 3][column + 3] == "+")
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
		System.out.print("arrPair : \n");
		for (int i = 0; i < arrPair.size(); i++)
		{
			System.out.print("( " + arrPair.get(i).getRowInt() + ", " + arrPair.get(i).getColumnInt() + " )\n");
		}
		
		String nextPlayerColor;
		int lineDirection = currentPlayer.getLineDirection();
		int moveDirection = currentPlayer.getMoveDirection();
		int row = 0;
		int column = 0;
		
		if (currentPlayer.getPlayColor() == 1)
		{
			nextPlayerColor = "○";
		}
		else
		{
			nextPlayerColor = "●";
		}
		
		// 일단 방해물이 없다고 가정하고 움직임.
		switch(lineDirection)
		{
		case 0:	// 1개 Pair == 아무 방향 이동 가능
			switch(moveDirection)
			{
			case 1:
				System.out.print("Swith case : 0, 1\n");
				row = arrPair.get(0).getRowInt() - 1;
				column = arrPair.get(0).getColumnInt() - 1;

				if (board[row][column] == "+") // 빈공간이면
				{
					board[row][column] = Integer.toString(1);
					board[row + 1][column + 1] = "+";
				}
				
			case 2:
				System.out.print("Swith case : 0, 2\n");
				row = arrPair.get(0).getRowInt() - 1;
				column = arrPair.get(0).getColumnInt() + 1;
				
				if (board[row][column] == "+")
				{
					board[row][column] = Integer.toString(1);
					board[row + 1][column - 1] = "+";
				}
				
			case 3:
				System.out.print("Swith case : 0, 3\n");
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt() - 2;
				
				if (board[row][column] == "+")
				{
					board[row][column] = Integer.toString(1);
					board[row][column + 2] = "+";
				}
				
			case 4:
				System.out.print("Swith case : 0, 4\n");
				row = arrPair.get(0).getRowInt();
				column = arrPair.get(0).getColumnInt() + 2;
				
				if (board[row][column] == "+")
				{
					board[row][column] = Integer.toString(1);
					board[row][column - 2] = "+";
				}
				
			case 5:
				System.out.print("Swith case : 0, 5\n");
				row = arrPair.get(0).getRowInt() + 1;
				column = arrPair.get(0).getColumnInt() - 1;
				
				if (board[row][column] == "+")
				{
					board[row][column] = Integer.toString(1);
					board[row - 1][column + 1] = "+";
				}
				
			case 6:
				System.out.print("Swith case : 0, 6\n");
				row = arrPair.get(0).getRowInt() + 1;
				column = arrPair.get(0).getColumnInt() + 1;
				
				if (board[row][column] == "+")
				{
					board[row][column] = Integer.toString(1);
					board[row - 1][column - 1] = "+";
				}
			}
			break; // End switch(lineDirection == 2)
		
		case 1: // 가로
			switch(moveDirection)	//  1 2 
			{						// 3 ● 4
			case 1:					//  5 6
				System.out.print("Swith case : 1, 1\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column + 1] = "+";
				}
				break;
				
			case 2:
				System.out.print("Swith case : 1, 2\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column - 1] = "+";
				}
				break;
				
			case 3:
				System.out.print("Swith case : 1, 3\n");
				
				if (arrPair.size() == 2)
				{
					if (board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 2] == nextPlayerColor)
					{
						board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 4] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 2] == nextPlayerColor && board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 4] == nextPlayerColor)
					{
						board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 6] = nextPlayerColor;
					}
					else if (board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 2] == nextPlayerColor && (board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 4] == "+" || board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 4] == " "))
					{
						board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 4] = nextPlayerColor;
					}
				}
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() - 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column + 2] = "+";
				}
				break;
				
			case 4:
				System.out.print("Swith case : 1, 4\n");
				
				if (arrPair.size() == 2)
				{
					if (board[arrPair.get(arrPair.size() - 1).getRowInt()][arrPair.get(arrPair.size() - 1).getColumnInt() + 2] == nextPlayerColor)
					{
						board[arrPair.get(arrPair.size() - 1).getRowInt()][arrPair.get(arrPair.size() - 1).getColumnInt() + 4] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[arrPair.get(arrPair.size() - 1).getRowInt()][arrPair.get(arrPair.size() - 1).getColumnInt() + 2] == nextPlayerColor && board[arrPair.get(arrPair.size() - 1).getRowInt()][arrPair.get(arrPair.size() - 1).getColumnInt() + 4] == nextPlayerColor)
					{
						board[arrPair.get(arrPair.size() - 1).getRowInt()][arrPair.get(arrPair.size() - 1).getColumnInt() + 6] = nextPlayerColor;
					}
					else if (board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 2] == nextPlayerColor && (board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 4] == "+" || board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 4] == " "))
					{
						board[arrPair.get(0).getRowInt()][arrPair.get(0).getColumnInt() - 4] = nextPlayerColor;
					}
				}
				
				for (int i = arrPair.size() - 1; i >= 0; i--)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() + 2;

					board[row][column] = Integer.toString(i);
					board[row][column - 2] = "+";
				}
				break;
				
			case 5:
				System.out.print("Swith case : 1, 5\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column + 1] = "+";
				}
				break;
				
			case 6:
				System.out.print("Swith case : 1, 6\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column - 1] = "+";
				}
				break;
			}
			break; // End switch(lineDirection == 1)
			
		case 2: // 왼쪽 아래					(5,7)
			switch(moveDirection)	//  1 2 	(6,6)
			{						// 3 ● 4	(7,5)
			case 1:					//  5 6		(8,4)	(9,3)
				System.out.print("Swith case : 2, 1\n");
				
				for (int i = 0;  i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column + 1] = "+";
				}				
				break;
				
			case 2:				// (6,6)
				System.out.print("Swith case : 2, 2\n");
				
				if (arrPair.size() == 2)
				{
					if (board[arrPair.get(0).getRowInt() - 1][arrPair.get(0).getColumnInt() + 1] == nextPlayerColor)
					{
						board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() + 2] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[arrPair.get(0).getRowInt() - 1][arrPair.get(0).getColumnInt() + 1] == nextPlayerColor && board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() + 2] == nextPlayerColor)
					{
						board[arrPair.get(0).getRowInt() - 3][arrPair.get(0).getColumnInt() + 3] = nextPlayerColor;
					}
					else if (board[arrPair.get(0).getRowInt() - 1][arrPair.get(0).getColumnInt() + 1] == nextPlayerColor && (board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() + 2] == "+" || board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() + 2] == " "))
					{
						board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() + 2] = nextPlayerColor;
					}
				}
								
				for (int i = 0;  i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column - 1] = "+";
				}				
				break;
				
			case 3:
				System.out.print("Swith case : 2, 3\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() - 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column + 2] = "+";
				}		
				break;
				
			case 4:
				System.out.print("Swith case : 2, 4\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() + 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column - 2] = "+";
				}	
				break;
			case 5:
				System.out.print("Swith case : 2, 5\n");
				
				if (arrPair.size() == 2)
				{
					if (board[arrPair.get(arrPair.size() - 1).getRowInt() + 1][arrPair.get(arrPair.size() - 1).getColumnInt() - 1] == nextPlayerColor)
					{
						board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() - 2] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[arrPair.get(arrPair.size() - 1).getRowInt() + 1][arrPair.get(arrPair.size() - 1).getColumnInt() - 1] == nextPlayerColor && board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() - 2] == nextPlayerColor)
					{
						board[arrPair.get(arrPair.size() - 1).getRowInt() + 3][arrPair.get(arrPair.size() - 1).getColumnInt() - 3] = nextPlayerColor;
					}
					else if (board[arrPair.get(arrPair.size() - 1).getRowInt() + 1][arrPair.get(arrPair.size() - 1).getColumnInt() - 1] == nextPlayerColor && (board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() - 2] == "+" || board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() - 2] == " "))
					{
						board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() - 2] = nextPlayerColor;
					}
				}
				
				for (int i = arrPair.size() - 1;  i >= 0 ; i--)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column + 1] = "+";
				}				
				break;
				
			case 6:
				System.out.print("Swith case : 2, 6\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column - 1] = "+";
				}	
				break;
			}
			break; // End switch(lineDirection == 2)
		
		case 3: // 오른쪽 아래
			switch(moveDirection)
			{
			case 1:	
				System.out.print("Swith case : 3, 1\n");
				
				if (arrPair.size() == 2)
				{
					if (board[arrPair.get(0).getRowInt() - 1][arrPair.get(0).getColumnInt() - 1] == nextPlayerColor)
					{
						board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() - 2] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[arrPair.get(0).getRowInt() - 1][arrPair.get(0).getColumnInt() - 1] == nextPlayerColor && board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() - 2] == nextPlayerColor)
					{
						board[arrPair.get(0).getRowInt() - 3][arrPair.get(0).getColumnInt() - 3] = nextPlayerColor;
					}
					else if (board[arrPair.get(0).getRowInt() - 1][arrPair.get(0).getColumnInt() - 1] == nextPlayerColor && (board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() - 2] == "+" || board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() - 2] == " "))
					{
						board[arrPair.get(0).getRowInt() - 2][arrPair.get(0).getColumnInt() - 2] = nextPlayerColor;
					}
				}
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column + 1] = "+";
				}
				break;
			case 2:
				System.out.print("Swith case : 3, 2\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() - 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row + 1][column - 1] = "+";
				}
				break;
			case 3:
				System.out.print("Swith case : 3, 3\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() - 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column + 2] = "+";
				}
				break;
			case 4:
				System.out.print("Swith case : 3, 4\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt();
					column = arrPair.get(i).getColumnInt() + 2;
					
					board[row][column] = Integer.toString(i);
					board[row][column - 2] = "+";
				}
				break;
			case 5:
				System.out.print("Swith case : 3, 5\n");
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() - 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column + 1] = "+";
				}
				break;
			case 6:
				System.out.print("Swith case : 3, 6\n");
				
				if (arrPair.size() == 2)
				{
					if (board[arrPair.get(arrPair.size() - 1).getRowInt() + 1][arrPair.get(arrPair.size() - 1).getColumnInt() + 1] == nextPlayerColor)
					{
						board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() + 2] = nextPlayerColor;
					}
				}
				else 
				{
					if (board[arrPair.get(arrPair.size() - 1).getRowInt() + 1][arrPair.get(arrPair.size() - 1).getColumnInt() + 1] == nextPlayerColor && board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() + 2] == nextPlayerColor)
					{
						board[arrPair.get(arrPair.size() - 1).getRowInt() + 3][arrPair.get(arrPair.size() - 1).getColumnInt() + 3] = nextPlayerColor;
					}
					else if (board[arrPair.get(arrPair.size() - 1).getRowInt() + 1][arrPair.get(arrPair.size() - 1).getColumnInt() + 1] == nextPlayerColor && (board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() + 2] == "+" || board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() + 2] == " "))
					{
						board[arrPair.get(arrPair.size() - 1).getRowInt() + 2][arrPair.get(arrPair.size() - 1).getColumnInt() + 2] = nextPlayerColor;
					}
				}
				
				for (int i = arrPair.size() - 1; i >= 0; i--)
				{
					row = arrPair.get(i).getRowInt() + 1;
					column = arrPair.get(i).getColumnInt() + 1;
					
					board[row][column] = Integer.toString(i);
					board[row - 1][column - 1] = "+";
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
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
			
			if (board[10][i] != " ")
			{
				board[10][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
		}
		
		for (int i = 0; i < 4; i++)
		{
			if (board[1][i] != " ")
			{
				board[1][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
			if (board[9][i] != " ")
			{
				board[9][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
		}
		
		for (int i = 0; i < 3; i++)
		{
			if (board[2][i] != " ")
			{
				board[2][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
			if (board[8][i] != " ")
			{
				board[8][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
		}

		for (int i = 0; i < 2; i++)
		{
			if (board[3][i] != " ")
			{
				board[3][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
			if (board[7][i] != " ")
			{
				board[7][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
		}

		for (int i = 0; i < 1; i++)
		{
			if (board[4][i] != " ")
			{
				board[4][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
			if (board[6][i] != " ")
			{
				board[6][i] = " ";
				nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
				return;
			}
		}
		
		if (board[5][0] != " ")
		{
			board[5][0] = " ";
			nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
			return;
		}
		
		System.out.print("5, 15 : " + board[5][20]);
		if (board[5][15] != " ")
		{
			board[5][15] = " ";
			nextPlayer.getAlphabet().remove(nextPlayer.getAlphabetLastIndex());
			return;
		}
		
	}
	
	
	// input 값이 제대로 들어왔는지 체크. 
	// 3. 이동 방향으로 이동할 수 있는지.
	public void play()	
	{		
		
		ArrayList<String> arrInput = currentPlayer.play(currentPlayer.getAlphabet());

		if(!checkInLine(arrInput))	// 1줄이 아닐 때
		{
			System.out.print("Your Input is Not a Line!\n");
			System.out.print("Choose Again!\n\n");
			currentPlayer.getArrInput().clear();
			play();
		}
		else // 한 줄이 맞을 때.
		{
			System.out.print("\n");
			System.out.print(currentPlayer.getPlayerType() + " Input is a Line!\n\n");
			//System.out.print("Direction : " + moveDirection + "\n");
			
			

			int moveDirection = Integer.parseInt(arrInput.get(arrInput.size() - 1));
			currentPlayer.setMovdDirection(moveDirection);
			
			if (!checkMovable(arrPair))	// 움직일 수 없을 때
			//if (false)
			{
				System.out.print("But You Can't Move!\n");
				System.out.print("Choose Again!\n\n");
				currentPlayer.getArrInput().clear();
				play();
			}
			else // 움직일 수 있을 때
			{
				System.out.print("Move !\n");
				moveLine(arrPair);
			}

		}	
	} // public void play()
	
	public boolean checkGameOver()
	{
		// 상대방 돌이 남아있는가
		int playColor = currentPlayer.getPlayColor();
		String oppositePlayColor;
		
		if (playColor == 1)
		{
			oppositePlayColor = "○";
		}
		else
		{
			oppositePlayColor = "●";
		}
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if (board[i][j] == oppositePlayColor)
				{
					return false;
				}
			}
		}
		return true;
	} // public boolean checkGameOver()
	
	public void changePlay() 
	{
		checkOut();
		
		int playColor = currentPlayer.getPlayColor();
		ArrayList<String> alphabet = (ArrayList<String>)currentPlayer.getAlphabet().clone();	// Shallow copy
		
		if (playColor == 1)		// black Color
		{
			for (int i = 0; i < board.length; i++)
			{
				for (int j = 0; j < board[i].length; j++)
				{
					if (board[i][j] != "+" && board[i][j] != " " && board[i][j] != "○")
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
					if (board[i][j] != "+" && board[i][j] != " " && board[i][j] != "●")
					{
						board[i][j] = "○";
					}
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
		System.out.print("\n");
		
		arrPair.clear();
		changePlayer();
	}
} // public void changePlay() 

