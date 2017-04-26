package javagames.game;

import javagames.g2d.SpriteSheet;
import javagames.util.geom.BoundingShape;

public abstract class GreaterPawn extends Pawn {

	
	public GreaterPawn(String name, SpriteSheet sprite)
	{
		super(name,sprite);
	}
	
	public GreaterPawn(String name, SpriteSheet sprite, BoundingShape bounds) 
	{
		super(name, sprite, bounds);
		// TODO Auto-generated constructor stub
	}

	
	protected abstract void ultimateAction();

}
