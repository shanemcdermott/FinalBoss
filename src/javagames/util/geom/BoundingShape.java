package javagames.util.geom;

import javagames.util.Vector2f;

public abstract class BoundingShape
{
	protected Vector2f position;
	
	public void setPosition(Vector2f position)
	{
		this.position = new Vector2f(position);
	}
	
	public Vector2f getPosition()
	{
		return new Vector2f(position);
	}

	/**
	 * Checks intersection with another shape.
	 * @param otherShape -Bounding Shape to compare against
	 * @return			true if the two shapes intersect
	 */
	public abstract boolean intersects(BoundingShape otherShape);
	
	public abstract boolean contains(Vector2f point);
	
}
