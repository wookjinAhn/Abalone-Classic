package abaloneClassic;

public abstract class TwoPlayerTurnGame implements TurnGameInterface
{
	private final GamePlayer firstPlayer;
	private final GamePlayer secondPlayer;

	protected GamePlayer currentPlayer;
	protected GamePlayer nextPlayer;
	
	public TwoPlayerTurnGame(GamePlayer firstPlayer, GamePlayer secondPlayer)
	{
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		currentPlayer = firstPlayer;
		nextPlayer = secondPlayer;
	}

	@Override
	public void changePlayer()
	{
		if (currentPlayer == firstPlayer)
		{
			currentPlayer = secondPlayer;
			nextPlayer = firstPlayer;
		}
		else
		{
			currentPlayer = firstPlayer;
			nextPlayer = secondPlayer;
		}
		
		System.out.print("-------------------\n");
		System.out.print(currentPlayer.getPlayerType() + " Turn ! \n");
	}
}
