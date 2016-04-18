package enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import character.CharacterPanel;
import mainPac.ImageLoader;

public class flower extends enemy{

	public flower(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp, facing);
		myImage=il.flower.getSubimage(
				0,(int)(Math.random()/0.34)*45, 90, 45
				);
		HP=1;
		characterName="Pansy";
		picWidth=45;
		picHeight=45;
		individualWidth=25;
		individualHeight=30;
		numOfWalkingStatus=2;
		hVelo=5;
		walking=true;
	}

	@Override
	public Rectangle getMyBounds(){
		return new Rectangle(x+10, y+15, individualWidth, individualHeight);
	}
}
