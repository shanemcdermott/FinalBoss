package finalBoss;

import javagames.util.StateFramework;

public class FinalBossGame extends StateFramework 
{
	
	public FinalBossGame()
	{
		super();
		bDrawDebug = true;
	}
	
	public static void main(String[] args)
	{
		launchApp(new FinalBossGame());
	}

}
