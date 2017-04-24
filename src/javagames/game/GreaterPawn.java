package javagames.game;

import javagames.g2d.SpriteSheet;

public abstract class GreaterPawn extends Pawn {

	public GreaterPawn(String name, SpriteSheet sprite) {
		super(name, sprite);
		// TODO Auto-generated constructor stub
	}

	
	protected abstract void ultimateAction();

}
