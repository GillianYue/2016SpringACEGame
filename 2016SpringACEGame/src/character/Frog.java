package character;

import mainPac.ImageLoader;

public class Frog extends Character{
	public Frog(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp){
		super(initialmapX, initialmapY, il, cp);
		myImage = il.kero;
		characterName = "Kero";
		numOfWalkingStatus=3; //0, 1, 2
		individualWidth=45; 
		individualHeight=45;
	}
}
