package character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import mainPac.Game;
import mainPac.ImageLoader;
import map.MapPanel;

public class Character {
	int x, y; //this is where the bird is on the panel
	private int myMapX, myMapY; //the character's coordinates on the actual map
	BufferedImage myImage;
	int facingDirection;	//1 is not flipped; -1 is flipped
	String characterName;
	
	public Character(int initialX, int initialY, ImageLoader il){
		x=initialX;
		y=initialY;
		myMapX=initialX/10;
		myMapY=initialY/10;
		
		facingDirection=1; //Default facing right
		//myImage is not loaded in this class. Load the image in its child classes
	}
	
	public void drawCharacter(Graphics g){
		if(facingDirection==1){
		g.drawImage(myImage, x, y, x+myImage.getWidth(),
				y+	myImage.getHeight(),
				0, 0, myImage.getWidth(), myImage.getHeight(), null);}
		//for now it draws the whole image
		else if(facingDirection==-1){
			g.drawImage(myImage, x+myImage.getWidth(), y, x,
					y+	myImage.getHeight(),
					0, 0, myImage.getWidth(), myImage.getHeight(), null);
		}
	}
	
	
	public void faceRight(){
		facingDirection=1;
	}
	
	public void faceLeft(){
		facingDirection=-1;
	}
	
	public void moveOneStepRight(){
		x+=10;
		myMapX+=1; //on the map it moved one unit to the right. one unit is 10 pixels
	}
	
	public void moveOneStepLeft(){
		x-=10;
		myMapX-=1;
	}
	
	public int getMapX(){
		return myMapX;
	}
	
	public int getMapY(){
		return myMapY;
	}
	
	public int getScreenX(){
		return x;
	}
	
	public int getScreenY(){
		return y;
	}
}
