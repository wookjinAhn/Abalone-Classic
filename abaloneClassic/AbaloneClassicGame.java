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
			//    0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   11
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				{" ", " ", " ", " ", "○", " ", "○", " ", "○", " ", "○", " ", "○", " ", " ", " ", " "}, // 0		//     ○ ○ ○ ○ ○    
				{" ", " ", " ", "○", " ", "○", " ", "○", " ", "○", " ", "○", " ", "○", " ", " ", " "}, // 1		//    ○ ○ ○ ○ ○ ○   
				{" ", " ", "+", " ", "+", " ", "○", " ", "○", " ", "○", " ", "+", " ", "+", " ", " "}, // 2		//   + + ○ ○ ○ + +  
				{" ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " "}, // 3		//  + + + + + + + + 
				{"+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+"}, // 4		// + + + + + + + + +
				{" ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " ", "+", " "}, // 5		//  + + + + + + + + 
				{" ", " ", "+", " ", "+", " ", "●", " ", "●", " ", "●", " ", "+", " ", "+", " ", " "}, // 6		//   + + ● ● ● + +  
				{" ", " ", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", " ", " "}, // 7		//    ● ● ● ● ● ●   
				{" ", " ", " ", " ", "●", " ", "●", " ", "●", " ", "●", " ", "●", " ", " ", " ", " "}, // 8		//     ● ● ● ● ●    
				{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}
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
	}

	// 2. 문자가 2개 이상이면 한 줄로 입력 되었는지.
	public boolean checkInLine(ArrayList<String> arrInput)
	{
		int moveDirection = Integer.parseInt(arrInput.get(arrInput.size()));
		currentPlayer.setMovdDirection(moveDirection);
				
		//System.out.print("\ncheckInLine\n"); 

		for (int itarr = 0; itarr < arrInput.size() - 1; itarr++)	// 마지막 direction 빼고 비교
		{
			String strIt = arrInput.get(itarr);
			//System.out.print("itarr " + itarr + " : " + strIt + "\n");
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

		//System.out.print("arrPair.size() : " + arrPair.size() + "\n");
		//for (int i =0; i < arrPair.size(); i++)
		//{
		//	System.out.print("arrPair : " + arrPair.get(i).getRow() + ", " + arrPair.get(i).getColumn() + "\n"); 
		//}	
		
		// check inline
		int lineDirection = -1;
		
		for (int i = 0; i < arrPair.size() - 1; i++)
		{
			int firstRow = arrPair.get(i).getRowInt();
			int firstColumn = arrPair.get(i).getColumnInt();
			int secondRow = arrPair.get(i+1).getRowInt();
			int secondColumn = arrPair.get(i+1).getColumnInt();
			// 가로로 한 줄
			if (secondRow == firstRow)
			{
				if (secondColumn == firstColumn + 2)
				{
					lineDirection = 0;
					currentPlayer.setLineDirection(lineDirection);
					System.out.print("\n");
					System.out.print("Choose Garo!\n");
				}
				else 
				{
					lineDirection = -1;
				}
			}

			if (secondRow == firstRow + 1)
			{
				System.out.print("\n");
				System.out.print("Choose Diagonal!\n");
				// 왼쪽 밑 대각선으로 한 줄
				if (secondColumn == firstColumn - 1)
				{
					lineDirection = 1;
					currentPlayer.setLineDirection(lineDirection);
					System.out.print("\n");
					System.out.print("Choose left Diagonal!\n");
				}
				// 오른쪽 밑 대각선으로 한 줄
				else if  (secondColumn == firstColumn + 1)
				{
					lineDirection = 2;
					currentPlayer.setLineDirection(lineDirection);
					System.out.print("\n");
					System.out.print("Choose right Diagonal!\n");
				}
				else
				{
					lineDirection  = -1;
				}
			}
		}
		
		if (lineDirection == -1)
		{
			for (int i = 0; i < arrPair.size(); i++)
			{
				arrPair.remove(i);
			}
			return false;
		}
		else
		{
			return true;
		}
	}
	
	// arrPair : 움직일 거 좌표 (row, column)
	// line : 어떻게 한줄로 만들어져 있는가
	// direction : 움직일 방향
	public void moveLine(ArrayList<Pair> arrPair, int line, int direction)
	{
		System.out.print("arrPair : " );
		for (int i = 0; i < arrPair.size(); i++)
		{
			System.out.print("( " + arrPair.get(i).getRowInt() + ", " + arrPair.get(i).getColumnInt() + " )\n");
		}
		
		int moveRowIndex = 0;
		int moveColumnIndex = 0;
		
		// 일단 방해물이 없다고 가정하고 움직임.
		switch(line)
		{
		case 0: // 가로
			switch(direction)	//  1 2 
			{					// 3 ● 4
			case 1:				//  5 6
				System.out.print("Swith case : 0, 1\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					moveRowIndex = arrPair.get(i).getRowInt() - 1;
					moveColumnIndex = arrPair.get(i).getColumnInt() - 1;
					
					board[moveRowIndex][moveColumnIndex] = Integer.toString(i);
					board[moveRowIndex + 1][moveColumnIndex + 1] = "+";
				}
				break;
				
			case 2:
				System.out.print("Swith case : 0, 2\n");
				for (int i = 0; i < arrPair.size(); i++)
				{
					moveRowIndex = arrPair.get(i).getRowInt() - 1;
					moveColumnIndex = arrPair.get(i).getColumnInt() + 1;
					System.out.print("\n");
					System.out.print("Move Index : ( " + moveRowIndex + ", " + moveColumnIndex + " )\n");
					board[moveRowIndex][moveColumnIndex] = Integer.toString(i);
				}
				break;
				
			case 3:
				System.out.print("Swith case : 0, 3\n");
				moveRowIndex = arrPair.get(0).getRowInt();
				moveColumnIndex = arrPair.get(0).getColumnInt() - 2;
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					board[moveRowIndex][moveColumnIndex + i * 2] = Integer.toString(i);
				}
				board[moveRowIndex][moveColumnIndex + 2 * arrPair.size()] = "+";
				break;
				
			case 4:
				System.out.print("Swith case : 0, 4\n");
				moveRowIndex = arrPair.get(0).getRowInt();
				moveColumnIndex = arrPair.get(0).getColumnInt() + 2;

				for (int i = 0; i < arrPair.size(); i++)
				{
					board[moveRowIndex][moveColumnIndex + i * 2] = Integer.toString(i);
				}
				board[moveRowIndex][moveColumnIndex - 2] = "+";
				break;
				
			case 5:
				System.out.print("Swith case : 0, 5\n");
				
				break;
				
			case 6:
				System.out.print("Swith case : 0, 6\n");
				
				break;
			}
		case 1: // 왼쪽 아래					(5,7)
			switch(direction)	//  1 2 	(6,6)
			{					// 3 ● 4	(7,5)
			case 1:				//  5 6		(8,4)	(9,3)
				System.out.print("Swith case : 1, 1\n");
				
				break;
			case 2:				// (6,6)
				System.out.print("Swith case : 1, 2\n");
				moveRowIndex = arrPair.get(0).getRowInt() - 1;			// 5
				moveColumnIndex = arrPair.get(0).getColumnInt() + 1;	// 7
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					board[moveRowIndex + i][moveColumnIndex - i] = Integer.toString(i);
				}
				board[moveRowIndex + arrPair.size()][moveColumnIndex - arrPair.size()] = "+";
				break;
			case 3:
				System.out.print("Swith case : 1, 3\n");
				
				break;
			case 4:
				System.out.print("Swith case : 1, 4\n");
				
				break;
			case 5:
				System.out.print("Swith case : 1, 5\n");
				moveRowIndex = arrPair.get(0).getRowInt() + 1;		
				moveColumnIndex = arrPair.get(0).getColumnInt() -1;
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					board[moveRowIndex + i][moveColumnIndex - i] = Integer.toString(i);
				}
				board[moveRowIndex - 1][moveColumnIndex + 1] = "+";
				break;
			case 6:
				System.out.print("Swith case : 1, 6\n");
				
				break;
			}
			break;
		
		case 2: // 오른쪽 아래
			switch(direction)
			{
			case 1:	
				System.out.print("Swith case : 2, 1\n");
				moveRowIndex = arrPair.get(0).getRowInt() - 1;			
				moveColumnIndex = arrPair.get(0).getColumnInt() - 1;	
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					board[moveRowIndex + i][moveColumnIndex + i] = Integer.toString(i);
				}
				board[moveRowIndex + arrPair.size()][moveColumnIndex + arrPair.size()] = "+";
				break;
			case 2:
				System.out.print("Swith case : 2, 2\n");
				
				break;
			case 3:
				System.out.print("Swith case : 2, 3\n");
				
				break;
			case 4:
				System.out.print("Swith case : 2, 4\n");
				
				break;
			case 5:
				System.out.print("Swith case : 2, 5\n");
				
				break;
			case 6:
				System.out.print("Swith case : 2, 6\n");
				moveRowIndex = arrPair.get(0).getRowInt() + 1;			
				moveColumnIndex = arrPair.get(0).getColumnInt() + 1;	
				
				for (int i = 0; i < arrPair.size(); i++)
				{
					board[moveRowIndex + i][moveColumnIndex + i] = Integer.toString(i);
				}
				board[moveRowIndex - 1][moveColumnIndex - 1] = "+";
				break;
			}
			break;
		}
	}
	
	
	// input 값이 제대로 들어왔는지 체크. 
	// 3. 이동 방향으로 이동할 수 있는지.
	public void play()	// play algorithm
	{		
		
		ArrayList<String> arrInput = currentPlayer.play(currentPlayer.getAlphabet());
		
		//for (int i = 0; i < arrInput.size(); i++)
		//{
		//	System.out.print(arrInput.get(i) + " ");
		//}
		
		if(!checkInLine(arrInput))	// 1줄이 아닐 때
		{
			System.out.print("Your Input is Not a Line!\n");
			System.out.print("Choose Again!\n\n");
			currentPlayer.getArrInput().clear();
			play();
		}
		else // 한 줄이 맞을 때.
		{
			int moveDirection = Integer.parseInt(arrInput.get(arrInput.size() - 1));
			System.out.print("\n");
			System.out.print(currentPlayer.getPlayerType() + " Input is a Line!\n\n");
			//System.out.print("Direction : " + moveDirection + "\n");

			moveLine(arrPair, currentPlayer.getLineDirection(), moveDirection);
			//moveLine(ArrayList<Pair> arrPair, int line, int direction)
			// 1. 아무것도 없을 때. 
			// 2. 상대방 돌이 있을 때
			// 2 - 1 상대방 돌 뒤에 아무것도 없을 때.
			// 2 - 2 상대방 돌 뒤에 내 돌이 있을 때.
			
			// 1 2 방향인 경우 위
			
			// 3 4 방향인 경우 좌 우
			
			// 5 6 방향인 경우 밑
		}
		
	}
	
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
	}
	
	public void changePlay() 
	{
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
	
	
}

