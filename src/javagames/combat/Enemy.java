package javagames.combat;

import javagames.g2d.SpriteSheet;
import javagames.util.geom.BoundingShape;

public class Enemy extends Pawn 
{
	
	public Enemy(String name, SpriteSheet sprite)
	{
		super(name,sprite);
	}
	
	public Enemy(String name, SpriteSheet sprite, BoundingShape bounds) {
		super(name, sprite, bounds);
		// TODO Auto-generated constructor stub
	}

	public int getExperienceReward()
	{
		return level;
	}
	
}
