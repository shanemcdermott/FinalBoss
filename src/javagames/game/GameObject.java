package javagames.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;


import javagames.util.Matrix3x3f;
import javagames.util.Utility;
import javagames.g2d.Sprite;
import javagames.util.Vector2f;
import javagames.util.XMLUtility;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingCircle;
import javagames.util.geom.BoundingShape;

public class GameObject
{
	
	protected String name;
	protected ArrayList<String> tags;
		
	protected BoundingShape bounds;
	
	
	protected Matrix3x3f transform;
	protected Vector2f scale;
	protected float rotation;
	protected Vector2f position;
	
	public GameObject(String name)
	{
		this(name, new BoundingBox(new Vector2f(-0.5f, -0.5f), new Vector2f(0.5f,0.5f)));
	}
	
	public GameObject(String name, BoundingShape bounds)
	{
		this.name = name;
		this.bounds = bounds;
		tags = new ArrayList<String>();
		transform = Matrix3x3f.identity();
		scale = new Vector2f(1.f,1.f);
		rotation = 0.f;
		position = new Vector2f();
	}
	
	public void reset(){}
	
	public String getName()
	{
		return name;
	}
	
	public void addTag(String...cTags)
	{
		for(String s : cTags)
		{
			this.tags.add(s);
		}
	}
	
	public ArrayList<String> getTags()
	{
		return new ArrayList<String>(tags);
	}
	
	public boolean hasTag(String tag)
	{
		return tags.contains(tag);
	}
	
	public boolean isActive()
	{
		return true;
	}
	
	public void update(float deltaTime)
	{
		updateTransform();
		bounds.setPosition(position);
	}
	
	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}
	
	public void setPosition(Vector2f position)
	{
		this.position = new Vector2f(position);
	}
	
	public Vector2f getWorldPosition()
	{
		return Matrix3x3f.scale(scale).mul(position);
	}
	
	public Vector2f getPosition()
	{
		return position;
	}
	
	public Matrix3x3f getWorldTransform()
	{
		Matrix3x3f wTransform = Matrix3x3f.scale(scale);
		wTransform = wTransform.mul(Matrix3x3f.rotate(rotation));
		wTransform = wTransform.mul(Matrix3x3f.translate(position));
		return wTransform;
	}
	
	public Vector2f getForwardVector()
	{
		return Vector2f.down();
	}
	
	private void updateTransform()
	{
		transform = getWorldTransform();
	}
	
	public void draw(Graphics2D g, Matrix3x3f view)
	{
		g.setColor(Color.GREEN);
		Vector2f pos = view.mul(position);
		Utility.drawString(g, (int)pos.x,(int)pos.y, name);
		bounds.render(g, view);
	}
	
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		Vector2f pos = view.mul(position.sub(posOffset));
		g.setColor(Color.GREEN);
		Utility.drawString(g, (int)pos.x,(int)pos.y, tags);
		bounds.setPosition(position.sub(posOffset));
		bounds.render(g, view);
	}

	public String getCollisionResponseTo(String channel)
	{
		return bounds.getCollisionResponseTo(channel);
	}
	
	public String getCollisionChannel()
	{
		return bounds.getCollisionChannel();
	}
	
	public BoundingShape getBounds()
	{
		return bounds;
	}
	
	public boolean intersects(BoundingShape otherShape) 
	{
		return bounds.intersects(otherShape);
	}

	
	public boolean contains(Vector2f point) 
	{
		return bounds.contains(point);
	}
	
	//Called when another object overlaps with this object
	public void onBeginOverlap(GameObject other)
	{}

	//Called when another object stops overlapping with this object
	public void onEndOverlap(GameObject other)
	{}
	
	@Override
	public String toString()
	{
		return name;
	}
}
