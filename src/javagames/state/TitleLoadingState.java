package javagames.state;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.combat.Avatar;
import javagames.g2d.Animation;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.util.Vector2f;

public class TitleLoadingState extends LoadingState 
{
	
	@Override
	protected void enterNextState() 
	{
		getController().setState(new TitleMenuState());
	}

}
