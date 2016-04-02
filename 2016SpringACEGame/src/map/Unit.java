package map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import mainPac.ImageLoader;

public class Unit {
	//tiny pieces of units that consist the map of the game.
	//each piece is an imaginary square that's 10*10 pixels
	private int Screenx, Screeny;
	private int mapX, mapY;
	int whatamI; 
	BufferedImage myImage;
	//0: nothing; 1: terrain; 2: terrainwithlayer; 
	public Unit (int screenX, int screenY, int whatAmI, ImageLoader il) {
		Screenx=screenX;
		Screeny=screenY;
		whatamI=whatAmI;
		if(whatAmI==1){
			myImage=il.terrainpiece;
		}
		if(whatAmI==2){
			myImage=il.terrainlayer;
			System.out.println("hey");
		}
	}
	
	public boolean drawUnit (Graphics g){
		if(whatamI==0){
			return false;
		}else{
		g.drawImage(myImage, Screenx, Screeny, null);
		return true;
	}
	}

}