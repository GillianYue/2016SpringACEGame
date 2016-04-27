package map;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mainPac.ImageLoader;

public class Unit {
	//tiny pieces of units that consist the map of the game.
	//each piece is an imaginary square that's 10*10 pixels
	private int Screenx, Screeny;
	private int mapX, mapY;
	int whatamI; 
	private int myBlank, myPicWidth;
	BufferedImage myImage;
	ImageLoader Il;
	//0: nothing; 1: terrain; 2: terrainwithlayer; 
	public Unit (int screenx, int screeny, int whatAmI, ImageLoader il, int MapX, int MapY) {
		Screenx=screenx;
		Screeny=screeny;
		mapX=MapX;
		mapY=MapY;
		whatamI=whatAmI;
		Il=il;
		if(whatAmI==2){
			myImage=il.terrainpiece;
			myPicWidth=myImage.getWidth();
		}
		if(whatAmI==1){
			myImage=il.terrainlayer;
			myPicWidth=myImage.getWidth();
		}
//		if(whatAmI==3){
//			myImage=il.terrainlayeredge;
//			myBlank=8;
//			myPicWidth=2;
//		}
//		if(whatAmI==4){
//			myImage=il.terrainpieceedge;
//		}
		if(whatAmI==5){
			myImage=il.stone;
			myPicWidth=myImage.getWidth();
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

	public void printMyCoordinates(){
		System.out.println("mx "+mapX+" my "+mapY+" sx "+Screenx+" sy "+Screeny);
	}

	public void changeMyType(int whatAmI){
		if(whatamI!=whatAmI){
		whatamI=whatAmI;
		if(whatAmI==1){
			myImage=Il.terrainpiece;
		}
		if(whatAmI==2){
			myImage=Il.terrainlayer;
		}
		}
	}
	public Rectangle getBounds(){
		try{
		return new Rectangle(Screenx+myBlank, Screeny, myPicWidth, myImage.getHeight());
		}catch(Exception e){
		//	System.out.println("getBounds did not work."+mapX+" "+mapY);
		return null;
		}
	}
	
	public int getMapX(){
		return mapX;
	}
	
	public int getMapY(){
		return mapY;
	}
	
	public int getScreenX(){
		return Screenx;
	}
	
	public int getScreenY(){
		return Screeny;
	}
}