package javagames.state;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.sound.BlockingClip;
import javagames.sound.BlockingDataLine;
import javagames.sound.LoopEvent;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.combat.buffs.BuffManager;
import javagames.g2d.Sprite;
import javagames.game.GameObject;
import javagames.util.Utility;
import javagames.util.Vector2f;
import javagames.util.XMLUtility;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingCircle;
import javagames.util.geom.BoundingGroup;


//TODO: XML/ GameObject Loading
public abstract class LoadingState extends State 
{
	protected String displayString = GameConstants.APP_TITLE;
	protected Element xml;
	protected ArrayList<String> loaded;
	
	private ExecutorService threadPool;
	protected List<Callable<Boolean>> loadTasks;
	private List<Future<Boolean>> loadResults;
	private int numberOfTasks;
	private float percent;
	private float wait;

	
	protected abstract void enterNextState();
	
	@Override
	public void enter()
	{
		controller.setAttribute("loading-state", this);
		threadPool = Executors.newCachedThreadPool();
		loaded = new ArrayList<String>();
		loadTasks = new ArrayList<Callable<Boolean>>();
		try
		{
			xml = ResourceLoader.loadXML(this.getClass(), displayString.replaceAll("\\s+","")+".xml");
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.exit(-1);
		}

		Element soundXML = XMLUtility.getElement(xml, "sounds");
		Element imageXML = XMLUtility.getElement(xml, "images");
		
		
		//Load Background Image
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception 
			{
				Sprite sprite = ResourceLoader.loadSprite(this.getClass(), XMLUtility.getElement(imageXML, "background"));
				Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
				sprite.scaleImage( viewport );
				controller.setAttribute( "background", sprite );
					
				return Boolean.TRUE;
			}
		});
		
		//Load Foreground Image
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception 
			{
				Sprite sprite = ResourceLoader.loadSprite(this.getClass(), imageXML, "foreground");
				if(sprite == null)
					return Boolean.TRUE;
				
				Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
				sprite.scaleImage( viewport );
				controller.setAttribute( "foreground", sprite );
							
				return Boolean.TRUE;
			}
		});
		
		//Load Sound FX
		for (Element sound : XMLUtility.getElements(soundXML, "sound"))
		{
			if(sound.getAttribute("type").equals("cue"))
			{
				loadTasks.add(new Callable<Boolean>() 		
				{
					@Override
					public Boolean call() throws Exception 
					{
						byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), sound.getAttribute("file"));
						SoundCue restartClip =
								new SoundCue( new BlockingDataLine(soundBytes));
						restartClip.initialize();
						restartClip.open();
						controller.setAttribute( sound.getAttribute("name"), restartClip );
						return Boolean.TRUE;
					}
				
				});
			}
			else if(sound.getAttribute("type").equals("loop"))
			{
				loadTasks.add(new Callable<Boolean>() 
				{
					@Override
					public Boolean call() throws Exception 
					{
						byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), sound.getAttribute("file"));
						SoundLooper clip =
								new SoundLooper( new BlockingDataLine( soundBytes ) );
							clip.initialize();
							clip.open();
							controller.setAttribute(sound.getAttribute("name"), clip );
							return Boolean.TRUE;
					}
				});
			}
			else if(sound.getAttribute("type").equals("ambience"))
			{
				//Load Ambience
				loadTasks.add( new Callable<Boolean>() 
				{
					@Override
					public Boolean call() throws Exception {
						byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), sound.getAttribute("file"));
						// Java 7.0
						LoopEvent loopEvent = new LoopEvent(
							new BlockingClip( soundBytes ) );
						loopEvent.initialize();
						controller.setAttribute( "ambience", loopEvent );
						return Boolean.TRUE;
					} 
				});
			}
		}
		
		//Load Buffs
		BuffManager.initialize();
	
		loadGameObjects();
		
		loadResults = new ArrayList<Future<Boolean>>();
		for(Callable<Boolean> task : loadTasks)
		{
			loadResults.add(threadPool.submit(task));
		}
		
		numberOfTasks = loadResults.size();
		if(numberOfTasks == 0)
		{
			numberOfTasks = 1;
		}
	}

	public void loadGameObjects()
	{
		Element spawnElement = XMLUtility.getElement(xml, "spawnpoint");
		if(spawnElement != null)
		{	
			Vector2f spawn = XMLUtility.getVector2f(spawnElement);
			controller.setAttribute("spawnPoint", spawn);
		}
		
		Element objectXML = XMLUtility.getElement(xml, "objects");
		if(objectXML == null) return;
		//Load Objects
		for (Element object : XMLUtility.getElements(objectXML, "object"))
		{	
			loadTasks.add( new Callable<Boolean>() 
			{
				@Override
				public Boolean call() throws Exception 
				{
					
					GameObject gameObject = XMLUtility.loadGameObject(this.getClass(), object);
					if(gameObject!= null)
					{
						System.out.println(gameObject.getName() + "Loaded.");
						loaded.add(gameObject.getName());			
						controller.setAttribute(gameObject.getName(), gameObject);
					}
					else
					{
						System.err.println(object.getAttribute("name") + " failed to load.");
					}
					return Boolean.TRUE;
				}
			});
		}
		

		loadBarriers();
	}
	
	
	public void loadBarriers()
	{
		Element objectXML = XMLUtility.getElement(xml, "blockinggroup");
		
		if(objectXML == null)
		{
			System.out.println("No blocking group found. Skipping");
			return;
		}
	
		BoundingGroup bounds = new BoundingGroup();
		for (Element barrier : XMLUtility.getElements(objectXML, "box"))
		{

			float halfWidth = 0.5f * Float.parseFloat(barrier.getAttribute("width"));
			float halfHeight = 0.5f * Float.parseFloat(barrier.getAttribute("height"));
			
			Vector2f min = new Vector2f(-halfWidth, -halfHeight);
			Vector2f max = new Vector2f(halfWidth, halfHeight);
			BoundingBox bound = new BoundingBox(min,max);
			Vector2f position = XMLUtility.getVector2f(barrier);
			bound.setPosition(position);
			bounds.addShape(bound);
		}
		for (Element barrier : XMLUtility.getElements(objectXML, "circle"))
		{

			float radius = Float.parseFloat(barrier.getAttribute("radius"));
			Vector2f position = XMLUtility.getVector2f(barrier);
			BoundingCircle bound = new BoundingCircle(position, radius);
			bounds.addShape(bound);
		}
		GameObject go = new GameObject("Bounds", bounds);
		loaded.add("Bounds");			
		controller.setAttribute("Bounds", go);
	}

	@Override
	public void updateObjects(float delta)
	{
		// remove finished tasks
		Iterator<Future<Boolean>> it = loadResults.iterator();
		while (it.hasNext()) 
		{
			Future<Boolean> next = it.next();
			if (next.isDone()) 
			{
				try 
				{
					if (next.get()) 
					{
						it.remove();
					}
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
		}
		
		//update the progress bar
		percent = (numberOfTasks - loadResults.size()) / (float) numberOfTasks;
		if(percent >= 1.0f)
		{
			threadPool.shutdown();
			wait += delta;
		}
		//Finished Loading
		if(wait > 1.0f && threadPool.isShutdown())
		{
			enterNextState();
		}
	}
	
	@Override
	public void render(Graphics2D g, Matrix3x3f view) 
	{
		super.render(g, view);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(Color.GREEN);
		Utility.drawCenteredString(g, app.getScreenWidth(),
				app.getScreenHeight() / 3, displayString);
		int vw = (int) (app.getScreenWidth() * .9f);
		int vh = (int) (app.getScreenWidth() * .05f);
		int vx = (app.getScreenWidth() - vw) / 2;
		int vy = (app.getScreenWidth() - vh) / 2;
		// fill in progress
		g.setColor(Color.GRAY);
		int width = (int) (vw * percent);
		g.fillRect(vx, vy, width, vh);
		// draw border
		g.setColor(Color.GREEN);
		g.drawRect(vx, vy, vw, vh);
	}
}
