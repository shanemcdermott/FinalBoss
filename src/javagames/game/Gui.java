package javagames.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sun.corba.se.impl.ior.GenericTaggedComponent;

import javagames.combat.Avatar;
import javagames.combat.CombatAction;
import javagames.g2d.ImageUtility;
import javagames.util.ResourceLoader;


public class Gui {

	private CombatAction[] actions = new CombatAction[4];
	private BufferedImage[] cooldowns = new BufferedImage[4];
	private BufferedImage[] spells = new BufferedImage[4];
	private String draaknar[] = {"FlameSpit.png", "ThickSkin.png", "DragonBreath.png", "Transcendence.png"};
	private String queenZeal[] = {"Skygate.png", "PointFlare.png", "DarkGear.png", "Halation.png"};
	private String goonthoro[] = {"LightningAttack.png", "FireBalls.png", "Monotoro.png", "Undead.png"};
	private String nihil[] = {"Pestilence.png", "EssenceDrain.png", "EldritchRunes.png", "EldritchGateway.png"};
	private int xOffset = 650;
	private Avatar avatar;

	public Gui(Avatar avatar) {
		this.avatar = avatar;
		init();
	}
	
	private void init() {
		if(avatar.name == "Draaknar"){
			loadSpells(draaknar);
		}else if(avatar.name == "Queen Zeal"){
			loadSpells(queenZeal);
		}else if(avatar.name == "Goonthro"){
			loadSpells(goonthoro);
		}else if(avatar.name == "Nihil"){
			loadSpells(nihil);
		}
		
		avatar.getActions(actions);
	}

	public void loadSpells(String[] ava) 
	{

		try {
			for(int i = 0; i < 4; i++){
				spells[i] = ResourceLoader.loadImage(GameObjectFactory.class, ava[i]);
				cooldowns[i] = ImageUtility.colorImage(spells[i], new Color(128,128,128,200));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update()
	{
		
		//Check if spell is on cool down to shade the appropriate spell number
		
	}
	
	
	public void draw(Graphics2D g) 
	{
			for(int i = 0; i < spells.length; i ++) 
			{	
				if(actions[i].canEnter())
				{
					g.drawImage(spells[i], xOffset, 800, null);
				}
				else
				{
					g.drawImage(cooldowns[i], xOffset, 800, null);
				}
				g.setColor(Color.WHITE);
				g.drawString(Integer.toString(i + 1), xOffset, 810);
				xOffset += 70;
			}

			xOffset = 650;
	}
}
