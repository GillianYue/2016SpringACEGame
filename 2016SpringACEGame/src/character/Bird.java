package character;

import mainPac.ImageLoader;

public class Bird extends Character{
	//our protagonist for now..
	
	public Bird(int initialX, int initialY, ImageLoader il){
		super(initialX, initialY, il);
		myImage = il.bird;
		characterName = "Chirpy";
	}
}
