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
		gameFactory.setWorld(displayString);
		
		backgroundFileName = "map.png";
		ambienceFileName = "AMBIENCE_alien.wav";
	}
	
	@Override
	public void addSoundCues() {
		soundCues.put("explosion", "EXPLOSION_large_01.wav");
		soundCues.put("fire-clip", "WEAPON_scifi_fire_02.wav");
		soundCues.put("cue-gui-confirm", "WEAPON_scifi_fire_02.wav");
		soundCues.put("cue-gui-cancel", "EXPLOSION_large_01.wav");
	}

	@Override
	public void addSoundLoops() 
	{
		soundLoops.put("thruster", "DRONE9RE.WAV");
		soundLoops.put("loop-gameOver", "DRONE9RE.WAV");

	}
	
	@Override
	protected void enterNextState() 
	{
		getController().setState(new KantoRoamingState());
	}

	@Override
	public void loadGameObjects() 
	{
		
		//Load Background Image
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception 
					{
					
						BufferedImage image = ResourceLoader.loadImage(LoadingState.class, backgroundFileName);
					
						Vector2f worldTopLeft = new Vector2f
						(
							-GameConstants.WORLD_WIDTH / 2.0f,
							GameConstants.WORLD_HEIGHT / 2.0f 
						);
						
						Vector2f worldBottomRight = new Vector2f
						(
							GameConstants.WORLD_WIDTH / 2.0f,
							-GameConstants.WORLD_HEIGHT / 2.0f 
						);
						
						Sprite sprite =	new Sprite( image, worldTopLeft, worldBottomRight );
						
						Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
						
						sprite.scaleImage( viewport );
						
						controller.setAttribute( "background", sprite );
						
						return Boolean.TRUE;
					}
				});

		//Load Tree
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception 
			{
				
				GameObject tree = gameFactory.createBarrier("Tree");
				controller.setAttribute( "Tree", tree);
				
				return Boolean.TRUE;
			}
		});
		
		
		
		//Load Avatar
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception 
			{
			
				Avatar avatar = gameFactory.createAvatar("Nihil");
				controller.setAttribute( "avatar", avatar);
				
				return Boolean.TRUE;
			}
		});
		
	}

}
