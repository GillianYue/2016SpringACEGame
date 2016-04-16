package enemies;

import java.awt.Graphics;

import character.CharacterPanel;
import mainPac.ImageLoader;

public class flower extends enemy{

	public flower(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp, facing);
		myImage=il.flower.getSubimage(0, 0, 90, 45);
		characterName="Pansy";
		picWidth=45;
		picHeight=45;
		individualWidth=45;
		individualHeight=45;
		numOfWalkingStatus=2;
		hVelo=5;
		walking=true;
	}


}
