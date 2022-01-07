package abaloneClassic;

import java.awt.CheckboxGroup;
import java.util.Random;

public class AbaloneClassicDemo
{
	public static void main(String[] args)
	{
		HumanPlayer man = new HumanPlayer(1, "WJ");
		HumanPlayer woman = new HumanPlayer(2, "YJ");
		ComputerPlayer computer = new ComputerPlayer(2);
		
		AbaloneClassicGame game = new AbaloneClassicGame(man, woman);

		int maxChoose = 3;
		int minAlphabet = 97;
		int maxAlphabet = 110;
		int minDirection = 1;
		int maxDirection = 6;
		
		Random randomSelecter = new Random();
		
		// 랜덤값 잘 나오나 테스트
		for(int i = 0; i < 3; i++)
		{
			int chooseAlphabet = minAlphabet + randomSelecter.nextInt(maxAlphabet - minAlphabet + 1);
			System.out.print(chooseAlphabet + " ");
		}
		int chooseDirection = minDirection + randomSelecter.nextInt(maxDirection - minDirection + 1);
		System.out.print(chooseDirection + "\n");
		
		for (int i = 0; i < 20; i ++)
		{
			System.out.print("man alphabet : " + man.getAlphabet().size() + "\n");
			System.out.print("woman alphabet : " + woman.getAlphabet().size() + "\n");
			game.visualize();
			game.play();
			if (!game.checkGameOver())
			{
				game.changePlay();
			}
		}
	}
}
