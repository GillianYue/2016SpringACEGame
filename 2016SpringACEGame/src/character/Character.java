package character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import mainPac.Game;
import mainPac.ImageLoader;

public class Character {
	int x, y; //this is where the bird is on the panel
	int mapX, mapY; //the character's coordinates on the actual map
	BufferedImage myImage;
	int facingDirection;	//1 is not flipped; -1 is flipped
	
	public Character(int initialX, int initialY, ImageLoader il){
		x=initialX;
		y=initialY;
		
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
		if(x<Game.fWidth-55){
		x+=10;
		}
		mapX+=1; //on the map it moved one unit to the right. one unit is 10 pixels
		System.out.println("mapX: "+mapX);
	}
	
	public void moveOneStepLeft(){
		if(x>10){
		x-=10;
		}
		mapX-=1;
		System.out.println("mapX: "+mapX);
	}
	
}
