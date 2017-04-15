package javagames.util.geom;

import javagames.util.Vector2f;

public interface BoundingShape
{

	/**
	 * Checks intersection with another shape.
	 * @param otherShape -Bounding Shape to compare against
	 * @return			true if the two shapes intersect
	 */
	public boolean intersects(BoundingShape otherShape);
	
	public boolean contains(Vector2f point);
	
}
