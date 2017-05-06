package javagames.game;

import java.util.ArrayList;

import javagames.g2d.Sprite;
import javagames.util.geom.BoundingShape;

public class World extends GameObject
{
	protected ArrayList<GameObject> objects;
	private ArrayList<Sprite> renderLayers;
	private BoundingShape bounds;
	
	public World(BoundingShape bounds)
	{
		this.bounds = bounds;
	}

	public void addObject(GameObject inObject)
	{
		objects.add(inObject);
	}
	
}
