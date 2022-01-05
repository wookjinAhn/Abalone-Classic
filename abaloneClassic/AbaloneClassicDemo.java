package abaloneClassic;

import java.awt.CheckboxGroup;

public class AbaloneClassicDemo
{
	public static void main(String[] args)
	{
		HumanPlayer man = new HumanPlayer(1, "WJ");
		HumanPlayer woman = new HumanPlayer(2, "YJ");
		
		AbaloneClassicGame game = new AbaloneClassicGame(man, woman);
		
		for (int i = 0; i < 10; i ++)
		{
			game.visualize();
			game.play();
			if (!game.checkGameOver())
			{
				game.changePlay();
			}
		}
	}
}
