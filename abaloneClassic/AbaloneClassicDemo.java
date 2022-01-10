package abaloneClassic;

import java.awt.CheckboxGroup;
import java.util.ArrayList;
import java.util.Random;

public class AbaloneClassicDemo
{
	public static void main(String[] args)
	{
		HumanPlayer man = new HumanPlayer(1, "WJ");
		HumanPlayer woman = new HumanPlayer(2, "YJ");
		ComputerPlayer computer1 = new ComputerPlayer(1);
		ComputerPlayer computer2 = new ComputerPlayer(2);
		
		AbaloneClassicGame game = new AbaloneClassicGame(computer1, computer2);

		//for (int i = 0; i < 4; i++)
		while (!game.checkGameOver())
		{
			game.visualize();
			game.play();
			game.checkOut();
			if (!game.checkGameOver())
			{
				game.changePlay();
			}
		}
		System.out.print("Game Over!\n");
		System.out.print(game.currentPlayer.getPlayerType() + " Win !");
	}
}
