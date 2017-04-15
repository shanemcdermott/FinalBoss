package javagames.util.geom;


import javagames.util.Vector2f;

public class BoundingBox implements BoundingShape 
{

	public Vector2f min;
	public Vector2f max;
	
	public BoundingBox()
	{
		this(new Vector2f(), new Vector2f());
	}
	
	public BoundingBox(Vector2f min, Vector2f max)
	{
		this.min = min;
		this.max = max;
	}
	
	@Override
	public boolean intersects(BoundingShape otherShape) 
	{
		if(otherShape instanceof BoundingBox)
			return intersectAABB((BoundingBox) otherShape);
		else if(otherShape instanceof BoundingCircle)
			return intersectsCircle((BoundingCircle) otherShape);
			
		System.err.println("otherShape is not recognized!");
		return false;
	}
	
	/**
	 * Intersection test with another box
	 * @param 	otherBox other BoundingBox to test against
	 * @return 	true if otherBox intersects with this.
	 */
	public boolean intersectAABB(BoundingBox otherBox)
	{	
		return intersectAABB(otherBox.min, otherBox.max);
	}
	
	
	/**
	 * Intersection test with another box
	 * @param minB Bottom Left corner of AABB
	 * @param maxB Top Right corner of AABB
	 * @return true if this intersects with other AABB
	 */
	public boolean intersectAABB(Vector2f minB, Vector2f maxB)
	{
		//Horizontal Check
		if(min.x > maxB.x || minB.x > max.x) return false;
		//Vertical Check
		if(min.y > maxB.y || minB.y > max.y) return false;
		
		return true;
	}
	
	/**
	 * Intersection test with a circle
	 * @param circle -circle to test
	 * @return true if the circle intersects with this.
	 */
	public boolean intersectsCircle(BoundingCircle circle)
	{
		return intersectsCircle(circle.center, circle.radius);
	}
	
	/**
	 * Intersection test with a circle
	 * @param center	-Center of circle to test against
	 * @param radius	-Radius of circle to test against
	 * @return			true if the circle intersects
	 */
	public boolean intersectsCircle(Vector2f center, float radius)
	{
		float d = 0.f;
		if(center.x < min.x) d+= (center.x - min.x) * (center.x - min.x);
		if(center.x > max.x) d+= (center.x - max.x) *(center.x - max.x);
		if(center.y < min.y) d+= (center.y - min.y) * (center.y - min.y);
		if(center.y > max.y) d+= (center.y - max.y) * (center.y - max.y);
		
		return d < radius*radius;
	}
	
	@Override
	public boolean contains(Vector2f point) 
	{
		return point.x > min.x && point.x < max.x && point.y > min.y && point.y < max.y;
	}

	/**
	 * Copies min x and y values into outMin
	 * @param outMin- Vector2f which will contain copy of min values.
	 */
	public void minCopy(Vector2f outMin)
	{
		outMin.x = min.x;
		outMin.y = min.y;
	}

	/**
	 * Copies max x and y values into outMax
	 * @param outMax- Vector2f which will contain copy of max values.
	 */
	public void maxCopy(Vector2f outMax)
	{	
		outMax.x = max.x;
		outMax.y = max.y;
	}
	
	/**
	 * Copies min and max into outMin and outMax
	 * @param outMin- Vector2f which will contain copy of min values.
	 * @param outMax- Vector2f which will contain copy of max values.
	 */
	public void getAABB(Vector2f outMin, Vector2f outMax)
	{
		minCopy(outMin);
		maxCopy(outMax);
	}

	@Override
	public String toString()
	{
		return String.format("Min: %s Max: %s", min, max);
	}
}
