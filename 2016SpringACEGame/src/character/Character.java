package character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import mainPac.Game;
import mainPac.ImageLoader;
import map.MapPanel;
import map.Unit;

public class Character {
	public int x, y; //this is where the bird is on the panel
	public int myMapX, myMapY; //the character's coordinates on the actual map
	public double gravity=9.8;
	BufferedImage myImage;
	int facingDirection;	//1 is not flipped; -1 is flipped
	String characterName;
	int myStatus=0; //the total number of status depends on the individual character
	int numOfWalkingStatus;
	public int velocity=0;
	public boolean falling=true;
	public int individualWidth, individualHeight;
	CharacterPanel characterPanel;
	
	public Character(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp){
		myMapX=initialmapX;
		myMapY=initialmapY;
		characterPanel=cp;
		x=myMapX*10;
		y=myMapY*10;
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
	
	public void rotateWalkingStatus(){
		if(myStatus==numOfWalkingStatus-1){
			myStatus=0;
		}
		myStatus++;
	}
	
	public void changeStatus(int status){
		myStatus=status;
	}
	
	public void returnToOriginalStatus(){
		myStatus=0;
	}
	
	public Rectangle getMyBounds(){
		return new Rectangle(x, y, individualWidth, individualHeight);
	}
	
	public boolean checkCollision(Unit unit){
		if (getMyBounds().intersects(unit.getBounds())){
			return true;
		}else{
			return false;
		}
	}
	
	public void resetCharacter(){
		myMapX=characterPanel.initialX;
		myMapY=characterPanel.initialY;
		x=myMapX*10;
		y=myMapY*10;
		facingDirection=1;
	}
	
	public void printMyCoordinates(){
		System.out.println(characterName+" mx "+myMapX+" my "+myMapY+" sx "+x+" sy "+y);
	}
}
