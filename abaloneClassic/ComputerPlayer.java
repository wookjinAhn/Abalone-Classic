package abaloneClassic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

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
			return true;
		}
		else	// 2개 이상
		{
			for (int i = 0; i < arrAscii.size(); i++)
			{
				if (i < (arrAscii.size() - 1))	// Alphabet
				{
					if (arrAscii.get(i) < 97 || arrAscii.get(i) > 97 + alphabet.size())
					{
						return true;
					}
				}
				else	// Direction
				{
					if (arrAscii.get(i) < 49 || arrAscii.get(i) > 54)
					{
						return true;
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
				return true;
			}
			
			// Ascii -> String
			for (int i = 0; i < arrAscii.size(); i++)
			{
				int intInput = arrAscii.get(i);
				char charInput = (char)intInput;
				arrInput.add(Character.toString(charInput));
			}
			return false;
		}
	}

	public void makePair()
	{
		String strFirst = arrInput.get(0);
		Pair firstIndex = new Pair();
		
		String nextPlayerColor;
		
		if (getPlayColor() == 1)
		{
			nextPlayerColor = "○";
		}
		else
		{
			nextPlayerColor = "●";
		}
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if (board[i][j].equals(strFirst)) 
				{
					firstIndex.setPair(i,j);
					//System.out.print("firstIndex : " + firstIndex.getRowInt() + ", " + firstIndex.getColumnInt() + "\n");
					arrPair.add(firstIndex);
				}
			}
		}
		//System.out.print("firstIndex : " + firstIndex.getRowInt() + ", " + firstIndex.getColumnInt() + "\n");
		int row = firstIndex.getRowInt();
		int column = firstIndex.getColumnInt();
		switch(getLineDirection())
		{
		case 1: // 가로
			if (board[row][column - 2] != nextPlayerColor && board[row][column - 2] != "·" && board[row][column - 2] != " ")
			{
				Pair index = new Pair();
				index.setPair(row, column - 2);
				//System.out.print("case 1 0 -2 | " + index.getRowInt() + ", " + index.getColumnInt() + "\n");
				arrPair.add(index);
			}
			if (board[row][column + 2] != nextPlayerColor && board[row][column + 2] != "·" && board[row][column + 2] != " ")
			{
				Pair index = new Pair();
				index.setPair(row, column + 2);
				//System.out.print("case 1 0 +2 | " + index.getRowInt() + ", " + index.getColumnInt() + "\n");
				arrPair.add(index);
			}
			break;
			
		case 2: // 왼쪽 아래
			if (board[row - 1][column + 1] != nextPlayerColor && board[row - 1][column + 1] != "·" && board[row - 1][column + 1] != " ")
			{
				Pair index = new Pair();
				index.setPair(row - 1, column + 1);
				//System.out.print("case 2 -1 +1 | " + index.getRowInt() + ", " + index.getColumnInt() + "\n");
				arrPair.add(index);
			}
			if (board[row + 1][column - 1] != nextPlayerColor && board[row + 1][column - 1] != "·" && board[row + 1][column - 1] != " ")
			{
				Pair index = new Pair();
				index.setPair(row + 1, column - 1);
				//System.out.print("case 2 +1 -1 | " + index.getRowInt() + "," + index.getColumnInt() + "\n");
				arrPair.add(index);
			}
			break;
			
		case 3: // 오른쪽 아래
			if (board[row - 1][column - 1] != nextPlayerColor && board[row - 1][column - 1] != "·" && board[row - 1][column - 1] != " ")
			{
				Pair index = new Pair();
				index.setPair(row - 1, column - 1);
				//System.out.print("case 3 -1 -1 | " + index.getRowInt() + ", " + index.getColumnInt() + "\n");
				arrPair.add(index);
			}
			if (board[row + 1][column + 1] != nextPlayerColor && board[row + 1][column + 1] != "·" && board[row + 1][column + 1] != " ")
			{
				Pair index = new Pair();
				index.setPair(row + 1, column + 1);
				//System.out.print("case 3 +1 +1 | " + index.getRowInt() + ", " + index.getColumnInt() + "\n");
				arrPair.add(index);
			}
			break;
		}
		
		IndexComparator indexComparator = new IndexComparator();
		Collections.sort(arrPair, indexComparator);	
	}
	
	@Override
	public void play()
	{
		arrInput.clear();
		arrPair.clear();
		showAvailableChoose();
		
		int minAlphabet = 97;
		int maxAlphabet = minAlphabet + getAlphabet().size() - 1;
		int minLineDirection = 1;
		int maxLineDirection = 3;
		int minMoveDirection = 1;
		int maxMoveDirection = 6;
		
		Random randomSelecter = new Random();
		
		int firstAlphabet = minAlphabet + randomSelecter.nextInt(maxAlphabet - minAlphabet + 1);
		int lineDirection = minLineDirection + randomSelecter.nextInt(maxLineDirection - minLineDirection + 1);
		setLineDirection(lineDirection);
		int moveDirection = minMoveDirection + randomSelecter.nextInt(maxMoveDirection - minMoveDirection + 1);
		setMoveDirection(moveDirection);
		
		char charFirstAlphabet = (char)firstAlphabet;
		arrInput.add(Character.toString(charFirstAlphabet));
		//System.out.print("Choose : " + charFirstAlphabet + "\n");
		
		makePair();
	}

}