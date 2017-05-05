package javagames.util.geom;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public abstract class BoundingShape
{
	protected Vector2f 	position = new Vector2f();
	protected String	collisionChannel = "DEFAULT";
	protected Map<String,String> collisionResponse;
	
	public void setPosition(Vector2f position)
	{
		this.position = new Vector2f(position);
	}
	
	public Vector2f getPosition()
	{
		return new Vector2f(position);
	}
	
	public void setCollisionChannel(String channel)
	{
		collisionChannel = channel;
	}
	
	public String getCollisionChannel()
	{
		return collisionChannel;
	}
	
	public void setCollisionResponseTo(String channel, String response)
	{
		collisionResponse.put(channel.toUpperCase(), response.toUpperCase());
	}
	
	public String getCollisionResponseTo(String other)
	{
		return collisionResponse.get(other);
	}
	
	/**
	 * Checks intersection with another shape.
	 * @param otherShape -Bounding Shape to compare against
	 * @return			true if the two shapes intersect
	 */
	public abstract boolean intersects(BoundingShape otherShape);
	
	public abstract boolean contains(Vector2f point);

	public abstract void render(Graphics g, Matrix3x3f view);
}
