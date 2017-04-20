package javagames.util.geom;


import javagames.util.Vector2f;

public class BoundingBox extends BoundingShape 
{
	
	protected Vector2f min;
	protected Vector2f max;
	
	public Vector2f minCpy;
	public Vector2f maxCpy;
	
	
	public BoundingBox()
	{
		this(new Vector2f(), new Vector2f());
	}
	
	public BoundingBox(Vector2f min, Vector2f max)
	{
		position = new Vector2f();
		this.min = new Vector2f(min);
		this.max = new Vector2f(max);
		this.minCpy = new Vector2f(min);
		this.maxCpy = new Vector2f(max);
	}
	
	@Override
	public void setPosition(Vector2f position)
	{
		this.position = position;
		this.minCpy = min.add(position);
		this.maxCpy = max.add(position);
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
		return intersectAABB(otherBox.minCpy, otherBox.maxCpy);
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
		if(minCpy.x > maxB.x || minB.x > maxCpy.x) return false;
		//Vertical Check
		if(minCpy.y > maxB.y || minB.y > maxCpy.y) return false;
		
		return true;
	}
	
	/**
	 * Intersection test with a circle
	 * @param circle -circle to test
	 * @return true if the circle intersects with this.
	 */
	public boolean intersectsCircle(BoundingCircle circle)
	{
		return intersectsCircle(circle.position, circle.radius);
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
		if(center.x < minCpy.x) d+= (center.x - minCpy.x) * (center.x - minCpy.x);
		if(center.x > maxCpy.x) d+= (center.x - maxCpy.x) *(center.x - maxCpy.x);
		if(center.y < minCpy.y) d+= (center.y - minCpy.y) * (center.y - minCpy.y);
		if(center.y > maxCpy.y) d+= (center.y - maxCpy.y) * (center.y - maxCpy.y);
		
		return d < radius*radius;
	}
	
	@Override
	public boolean contains(Vector2f point) 
	{
		return point.x > minCpy.x && point.x < maxCpy.x && point.y > minCpy.y && point.y < maxCpy.y;
	}

	/**
	 * Copies min x and y values into outMin
	 * @param outMin- Vector2f which will contain copy of min values.
	 */
	public void minCopy(Vector2f outMin)
	{
		outMin.x = minCpy.x;
		outMin.y = minCpy.y;
	}

	/**
	 * Copies max x and y values into outMax
	 * @param outMax- Vector2f which will contain copy of max values.
	 */
	public void maxCopy(Vector2f outMax)
	{	
		outMax.x = maxCpy.x;
		outMax.y = maxCpy.y;
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
		return String.format("Min: %s Max: %s", minCpy, maxCpy);
	}
}
