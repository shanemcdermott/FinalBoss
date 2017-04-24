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
import javagames.g2d.Sprite;
import javagames.util.Utility;
import javagames.util.Vector2f;
import javagames.util.XMLUtility;


//TODO: XML/ GameObject Loading
public abstract class LoadingState extends State 
{
	protected String displayString = GameConstants.APP_TITLE;
	
	protected String backgroundFileName = "space_background_600x600.png";
	protected String ambienceFileName = "AMBIENCE_alien.wav";
	
	//AttributeName, FileName
	protected Map<String, String> soundCues;
	protected Map<String, String> soundLoops;
	
	
	private ExecutorService threadPool;
	protected List<Callable<Boolean>> loadTasks;
	private List<Future<Boolean>> loadResults;
	private int numberOfTasks;
	private float percent;
	private float wait;
	
	public LoadingState()
	{
		//Add any Sound Cues here
		soundCues = Collections.synchronizedMap(new HashMap<String, String>());
		addSoundCues();
		
		//Add any looping sounds here
		soundLoops = Collections.synchronizedMap(new HashMap<String, String>());
		addSoundLoops();
	}
	
	public abstract void addSoundCues();
	public abstract void addSoundLoops();

	public abstract void loadGameObjects();
	
	protected abstract void enterNextState();
	
	@Override
	public void enter()
	{
		controller.setAttribute("loading-state", this);
		threadPool = Executors.newCachedThreadPool();
		loadTasks = new ArrayList<Callable<Boolean>>();
		
		//Load Background Image
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception 
			{
			
				BufferedImage image = ResourceLoader.loadImage(LoadingState.class, backgroundFileName);
			
				Vector2f worldTopLeft = new Vector2f
				(
					-GameConstants.WORLD_WIDTH * 2.0f,
					GameConstants.WORLD_HEIGHT * 2.0f 
				);
				
				Vector2f worldBottomRight = new Vector2f
				(
					GameConstants.WORLD_WIDTH * 2.0f,
					-GameConstants.WORLD_HEIGHT * 2.0f 
				);
				
				Sprite sprite =	new Sprite( image, worldTopLeft, worldBottomRight );
				
				Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
				
				sprite.scaleImage( viewport );
				
				controller.setAttribute( "background", sprite );
				
				return Boolean.TRUE;
			}
		});
		
		//Load Ambience
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception {
				byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), ambienceFileName);
				// Java 7.0
				LoopEvent loopEvent = new LoopEvent(
					new BlockingClip( soundBytes ) );
				// Java 6.0
				// LoopEvent loopEvent = new LoopEvent(
				// new BlockingDataLine(
				// soundBytes ) );
				loopEvent.initialize();
				controller.setAttribute( "ambience", loopEvent );
				return Boolean.TRUE;
			} 
		});
		
		//Load Sound FX
		for (Map.Entry<String, String> entry : soundCues.entrySet())
		{
			loadTasks.add(new Callable<Boolean>() 
			{
				@Override
				public Boolean call() throws Exception 
				{
					byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), entry.getValue());
					SoundCue restartClip =
						new SoundCue( new BlockingDataLine(soundBytes));
					restartClip.initialize();
					restartClip.open();
					controller.setAttribute( entry.getKey(), restartClip );
					return Boolean.TRUE;
				}
				
			});
		}
		
		//Load Sound FX
		for (Map.Entry<String, String> entry : soundLoops.entrySet())
		{
			loadTasks.add(new Callable<Boolean>() 
			{
				@Override
				public Boolean call() throws Exception 
				{
					byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), entry.getValue());
					SoundLooper clip =
							new SoundLooper( new BlockingDataLine( soundBytes ) );
						clip.initialize();
						clip.open();
						controller.setAttribute(entry.getKey(), clip );
						return Boolean.TRUE;
				}
			});
		}
		
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
