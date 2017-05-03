package javagames.util.geom;

import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class BoundingGroup extends BoundingShape 
{
	public Vector<BoundingShape> shapes;
	public Vector<Vector2f> shapeOffsets;
	
	public BoundingGroup()
	{
		this(new Vector<BoundingShape>());
	}
	
	public BoundingGroup(BoundingShape root)
	{
		this.shapes = new Vector<BoundingShape>();
		this.shapeOffsets = new Vector<Vector2f>();
		collisionResponse =  Collections.synchronizedMap(new HashMap<String, String>());
		collisionResponse.put("DEFAULT", "BLOCK");
		addShape(root);
	}
	
	public BoundingGroup(List<BoundingShape> shapes)
	{
		this.shapes = new Vector<BoundingShape>(shapes);
		this.shapeOffsets = new Vector<Vector2f>(shapes.size());
		for(int i = 0; i < shapes.size(); i++)
		{
			shapeOffsets.set(i,shapes.get(i).getPosition());
		}
		collisionResponse =  Collections.synchronizedMap(new HashMap<String, String>());
		collisionResponse.put("DEFAULT", "BLOCK");
	}
	
	public void addShape(BoundingShape shape)
	{
		shapes.add(shape);
		shapeOffsets.add(shape.getPosition());
	}
	
	@Override
	public void setPosition(Vector2f position)
	{
		this.position = new Vector2f(position);
	}
	
	@Override
	public boolean intersects(BoundingShape otherShape) 
	{
		int i = 0;
		for( BoundingShape s : shapes)
		{
			s.setPosition(shapeOffsets.get(i++).add(getPosition()));
			if(s.intersects(otherShape))
				return true;
		}
		return false;
	}

	@Override
	public boolean contains(Vector2f point) {
		int i = 0;
		for( BoundingShape s : shapes)
		{
			s.setPosition(shapeOffsets.get(i++).add(getPosition()));
			if(s.contains(point))
				return true;
		}
		return false;
	}

	@Override
	public void render(Graphics g, Matrix3x3f view) 
	{
		for(BoundingShape s : shapes)
		{
			s.render(g, view);
		}

	}

}
