package javagames.game;

import java.awt.Graphics2D;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public interface ObjectState extends Ownable
{
	public void update(float deltaTime);
	public void reset();
	public boolean canEnter();
	public void enter();
	public void exit();
	public void setOwner(MultiStateObject owner);
	public String getName();
	public GameObject getEffect();
}
