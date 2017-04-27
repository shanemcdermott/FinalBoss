package javagames.kanto;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javagames.g2d.Animation;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.game.Avatar;
import javagames.game.GameObject;
import javagames.game.GameObjectFactory;
import javagames.state.LoadingState;
import javagames.state.TitleMenuState;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.util.Vector2f;

public class KantoLoadingState extends LoadingState 
{
	
	


	public KantoLoadingState()
	{
		super();
		displayString = "Kanto";

	}
	
	
	
	@Override
	protected void enterNextState() 
	{
		KantoRoamingState next = new KantoRoamingState();
		
		getController().setState(next);
	}



	@Override
	public void loadGameObjects() {
		// TODO Auto-generated method stub
		
	}

}
