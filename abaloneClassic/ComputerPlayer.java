package abaloneClassic;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends GamePlayer
{
	private final Random randomSelecter = new Random();
	

	private ArrayList<String> arrInput = new ArrayList<String>();

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

	@Override
	public ArrayList<String> play()
	{
		arrInput.clear();
		
		int minAlphabet = 97;
		int maxAlphabet = 110;

		ArrayList<Integer> arrAscii = new ArrayList<Integer>();
		
		boolean check = true;
		
		while (check)
		{
			showAvailableChoose();
			
			int chooseNum = 1 + randomSelecter.nextInt(3);
			
			for (int i = 0; i < chooseNum; i++)
			{
				int chooseAlphabet = minAlphabet + randomSelecter.nextInt(maxAlphabet - minAlphabet + 1);
				arrAscii.add(chooseAlphabet);
			}
			int chooseDirection = 1 + randomSelecter.nextInt(6);
			arrAscii.add(chooseDirection);
			
			check = checkRightInput(arrAscii, availableAlphabet);
		}
		
		return arrInput;
	}

}