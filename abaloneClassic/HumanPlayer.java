package abaloneClassic;

import java.util.ArrayList;
import java.util.Arrays;
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

	@Override
	public ArrayList<String> play()
	{
		boolean check = false;
		
		while (!check)
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
			
			check = checkRightInput(arrAscii, availableAlphabet);
		}
		return arrInput;
	}

}