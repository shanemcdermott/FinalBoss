package javagames.combat;

import javagames.combat.buffs.Buff;
import javagames.g2d.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javagames.g2d.Sprite;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class Pawn extends LivingObject {

	// Everything an object needs to be buffable
	protected int maxHealth;
	protected int maxMana;
	protected int currentMana;
	protected int strength;
	protected int speed;
	protected int magic;
	protected int level;

	// variables used to keep track of values going into the negatives.
	protected int excessMaxHealth;
	protected int excessMaxMana;
	protected int excessStrength;
	protected int excessSpeed;
	protected int excessMagic;

	protected List<Buff> buffs;

	protected float speedScale;

	public Pawn(String name, SpriteSheet sprite) {
		super(name, sprite);
		initialize();
	}

	public Pawn(String name, SpriteSheet sprite, BoundingShape bounds) {
		super(name, bounds, sprite);
		initialize();
	}

	private void initialize() {
		speedScale = 1.f;
		startAnimation("WalkDown");

		excessMaxHealth = 0;
		excessMaxMana = 0;
		excessStrength = 0;
		excessSpeed = 0;
		excessMagic = 0;

		buffs = new ArrayList<Buff>();
		
		getJobStats();
	}

	protected void getJobStats() {

		maxHealth = currentHealth = 100;
		maxMana = currentMana = 100;
		strength = 10;
		speed = 10;
		magic = 10;
		level = 1;
		
	}
	
	public void addBuff(Buff buff) {
		// If this object already has a specific buff, just reset it
		for (Buff b : buffs) {
			if (buff.equals(b)) {
				b.reset();
				return;
			}
		}

		// Otherwise add it
		buffs.add(buff);
	}

	public void displayBuffs(Graphics g, int x, int y) {
		for (Buff buff : buffs) {
			String str = String.format("%s: %.1f", buff.getName(),
					buff.getTimeRemaining());
			g.drawString(str, x, y);
			y += 20;
		}

	}

	public void updateBuffs(float deltaTime) {

		// Use an iterator to iterate over buffs. Required since we will be
		// removing while iterating.
		Iterator<Buff> iter = buffs.iterator();

		while (iter.hasNext()) {
			Buff buff = iter.next();

			// Update the buff
			buff.update(deltaTime);
			buff.processBuff(this);

			// See if it expired. If it did, remove it and move to the next
			// buff. Regain lost stats or lose bonus stats.
			if (buff.expired()) {
				buff.removeBuff(this);
				iter.remove();
				continue;
			}
		}
	}

	public int buffCount() {
		return buffs.size();
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getCurrentMana() {
		return currentMana;
	}

	public int getStrength() {
		return strength;
	}

	public int getSpeed() {
		return speed;
	}

	public int getMagic() {
		return magic;
	}

	public int getLevel() {
		return level;
	}

	public void setMaxHealth(int value) {
		maxHealth = value;
	}

	public void setCurrentHealth(int value) {
		currentHealth = value;
	}

	public void setMaxMana(int value) {
		maxMana = value;
	}

	public void setMana(int value) {
		currentMana = value;
	}

	public void setStrength(int value) {
		strength = value;
	}

	public void setSpeed(int value) {
		speed = value;
	}

	public void setMagic(int value) {
		magic = value;
	}

	public int getExcessMaxHealth() {
		return excessMaxHealth;
	}

	public int getExcessMaxMana() {
		return excessMaxMana;
	}

	public int getExcessStrength() {
		return excessStrength;
	}

	public int getExcessSpeed() {
		return excessSpeed;
	}

	public int getExcessMagic() {
		return excessMagic;
	}

	public void setExcessMaxHealth(int value) {
		excessMaxHealth = value;
	}

	public void setExcessMaxMana(int value) {
		excessMaxMana = value;
	}

	public void setExcessStrength(int value) {
		excessStrength = value;
	}

	public void setExcessSpeed(int value) {
		excessSpeed = value;
	}

	public void setExcessMagic(int value) {
		excessMagic = value;
	}

	public void startAction(String actionName) {
		if (actionName.contains("Walk")) {
			move(actionName);
		} else if (states.containsKey(actionName)) {
			setState(actionName);
		} else {
			System.out.printf("%s is not a recognized action!\n", actionName);
		}
	}

	protected void stopMoving() {
		if (currentState.equals("Idle")) {
			String oldAnim = ((SpriteSheet) sprite).getCurrentAnimation();
			((SpriteSheet) sprite).startAnimation(oldAnim.replaceAll("Walk",
					"Stand"));
		}

		physics.stopMotion();
	}

	protected void move(String direction) {
		if (currentState.equals("Idle")) {
			((SpriteSheet) sprite).startAnimation(direction);
		}

		physics.move(Vector2f.parse(direction.replace("Move", "")).mul(
				speedScale));
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		updateBuffs(deltaTime);
	}
	
	@Override
	public void draw( Graphics2D g, Matrix3x3f view, Vector2f posOffset ) {
		super.draw(g, view, posOffset);
		
		float w = sprite.getWidth();
		float h = sprite.getHeight();
		
		float xOffset = w / 2;
		float yOffset = h / 2 + 0.25f;
		
		Vector2f tl = position.sub(posOffset);
		//Vector2f tl = position.sub(posOffset).sub(new Vector2f(xOffset, -yOffset));
		Vector2f healthBarTL = tl.sub(new Vector2f(xOffset, -yOffset));
		Vector2f healthBarRedBR = healthBarTL.add( new Vector2f( w, -0.2f) );
		Vector2f healthBarGreenBR = healthBarTL.add( new Vector2f( w * ( (float) currentHealth / maxHealth ), -0.2f) );
		healthBarTL = view.mul(healthBarTL);
		healthBarRedBR = view.mul(healthBarRedBR);
		healthBarGreenBR = view.mul(healthBarGreenBR);
		
		g.setColor(Color.RED);
		g.fillRect((int)healthBarTL.x, (int)(healthBarTL.y), (int)(healthBarRedBR.x - healthBarTL.x), (int)(healthBarRedBR.y - healthBarTL.y));
		g.setColor(Color.GREEN);
		g.fillRect((int)healthBarTL.x, (int)(healthBarTL.y), (int)(healthBarGreenBR.x - healthBarTL.x), (int)(healthBarGreenBR.y - healthBarTL.y));
		
		yOffset += 0.2f;
		xOffset -= 0.1f;
		for ( Buff buff : buffs ) {
			buff.render(g, view, tl.sub(new Vector2f(xOffset, -yOffset)), rotation);
			xOffset -= 0.25f;
		}
	}

}
