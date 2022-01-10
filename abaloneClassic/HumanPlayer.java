package abaloneClassic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class HumanPlayer extends GamePlayer
{
	private Scanner scanner;
	private String name;
	
	
	public HumanPlayer(int playerID)
	{
		super(playerID);
	}

	public HumanPlayer(int playerID, String name)
	{
		super(playerID);
		this.name = name;
	}
	
	@Override
	public String getPlayerType()
	{
		return "Human(" + name + ")";
	}
	
	// input 값이 제대로 들어왔는지 체크.
	// 1. 문자 다음 숫자
	// 2. 문자 1개 ~ 3개, 숫자 1개
	// 3. 문자 범위 벗어났는지, 숫자 범위 벗어났는지.
	// 4. 중복으로 들어왔는지
	@Override
	public boolean checkRightInput(ArrayList<Integer> arrAscii, ArrayList<String> alphabet)
	{	
		if (arrAscii.size() < 2)	// 2개 미만
		{
			System.out.print("\n");
			System.out.print("You Should Input More Then 2!\n");
			System.out.print("Please Input Again!");
			return false;
		}
		else	// 2개 이상
		{
			for (int i = 0; i < arrAscii.size(); i++)
			{
				if (i < (arrAscii.size() - 1))	// Alphabet
				{
					if (arrAscii.get(i) < 97 || arrAscii.get(i) > 97 + alphabet.size())
					{
						System.out.print("\n");
						System.out.print("Your Input is Incorrect!\n");
						System.out.print("Please Input Again!");
						return false;
					}
				}
				else	// Direction
				{
					if (arrAscii.get(i) < 49 || arrAscii.get(i) > 54)
					{
						System.out.print("\n");
						System.out.print("Your Input is Incorrect!\n");
						System.out.print("Please Input Again!");
						return false;
					}
				}
			}
			
			// 중복 입력 비교
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int i = 0; i < arrAscii.size(); i++)
			{
				if(!temp.contains(arrAscii.get(i)))
					temp.add(arrAscii.get(i));
			}
			
			if (temp.size() != arrAscii.size())
			{
				System.out.print("\n");
				System.out.print("Your Input is Duplicate!\n");
				System.out.print("Please Input Again!");
				return false;
			}
			
			// Ascii -> String
			for (int i = 0; i < arrAscii.size(); i++)
			{
				int intInput = arrAscii.get(i);
				char charInput = (char)intInput;
				arrInput.add(Character.toString(charInput));
			}
			return true;
		}
	}

	public boolean checkOneLine()
	{
		arrPair.clear();
		
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
		if (arrPair.size() == 1)	// 하나
		{
			this.setLineDirection(0);
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
			this.setLineDirection(1);
			//System.out.print("Garo\n");
			return true;
		}
		else if (leftDiagonalCount == arrPair.size())
		{
			this.setLineDirection(2);
			//System.out.print("LeftDiagonal\n");
			return true;
		}
		else if (rightDiagonalCount == arrPair.size())
		{
			this.setLineDirection(3);
			//System.out.print("RightDiagonal\n");
			return true;
		}
		else // 직선이 아닐 때
		{
			arrPair.clear();
			//System.out.print("Not a Line\n");
			return false;
		}
	}
	
	@Override
	public void play()
	{
		boolean bRightInput = false;
		boolean bOneLine = false;
		
		while (!bRightInput || !bOneLine)
		{
			arrInput.clear();
			showAvailableChoose();
			scanner = new Scanner(System.in);
			
			// input값 받아서 띄어쓰기 기준으로 각각 값을 넣음.  
			String strInput = scanner.nextLine();
			String[] strArrInput = strInput.split(" ");
			
			// input -> Ascii code
			ArrayList<Integer> arrAscii = new ArrayList<Integer>();
			for (int i = 0; i < strArrInput.length; i++)
			{
				byte[] byteAscii = strArrInput[i].getBytes(StandardCharsets.US_ASCII);
				arrAscii.add((int)byteAscii[0]);
			} // End input -> Ascii code
			
			bRightInput = checkRightInput(arrAscii, availableAlphabet);	// arrInput 
			bOneLine = checkOneLine();	// arrPair
		}
		setMoveDirection(Integer.parseInt(arrInput.get(arrInput.size() - 1)));
		
		return;
	}

}