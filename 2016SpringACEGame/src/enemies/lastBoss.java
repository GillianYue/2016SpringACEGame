package enemies;

import character.CharacterPanel;
import mainPac.ImageLoader;

public class lastBoss extends enemy{
	/*
	 * although lastBosses are made children of enemy here
	 * they should function separately from the other enemies
	 */
	boolean vulnerable;
	
	public lastBoss(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp, facing);
		vulnerable=false;
	}

	public boolean injurable(){
		return vulnerable;
	}
	
	public boolean gravityApplies(){
	return false;	
	}
	
	public void killAllLeaves(){
		
	}
	
	public void onslaught(){
	}
	
}
