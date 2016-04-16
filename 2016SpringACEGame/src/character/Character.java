package character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import enemies.enemy;
import mainPac.Game;
import mainPac.ImageLoader;
import map.MapPanel;
import map.Unit;

public class Character {
	protected int x, y; //this is where the bird is on the panel
	protected int myMapX, myMapY; //the character's coordinates on the actual map
	public double gravity=10;
	protected BufferedImage myImage;
	protected int facingDirection=1;	//1 is not flipped; -1 is flipped
	public String characterName;
	protected int myStatus=0; //the total number of status depends on the individual character
	protected int numOfWalkingStatus;
	public int velocity=0;
	public double hVelo=0;
	public int individualWidth, individualHeight;
	CharacterPanel characterPanel;
	public boolean onGround, walking, squat, jumping;
	public int HP;
	
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
	
	public void moveNStepRight(int steps){
		hVelo=-5*steps; //on the map it moved one unit to the right. one unit is 10 pixels
	}
	
	public void moveNStepLeft(int steps){
		hVelo=5*steps;
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
	
	public void setMapX(int mx){
		myMapX=mx;
	}
	
	public void setMapY(int my){
		myMapY=my;
	}
	
	public int myDirection(){
		return facingDirection;
	}
	
	public void setScreenX(int sx){
		x=sx;
	}
	
	public void setScreeny(int sy){
		y=sy;
	}
	
	public void rotateWalkingStatus(){
		if(myStatus>=numOfWalkingStatus-1){
			myStatus=0;
		}else{
		myStatus++;
		}
	}
	
	public void changeStatus(int status){
		myStatus=status;
	}
	
	public void returnToOriginalStatus(){
		myStatus=0;
	}
	
	public int getStatus(){
		return myStatus;
	}
	
	public Rectangle getMyBounds(){
		return new Rectangle(x, y, individualWidth, individualHeight);
	}
	
	public boolean collisionWithUnit(Unit unit){
		try{
		if (getMyBounds().intersects(unit.getBounds())){
			return true;
		}else{
			return false;
		}
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean collisionWithEnemy(enemy e){
		try{
			if (getMyBounds().intersects(e.getMyBounds())){
				return true;
			}else{
				return false;
			}
			}catch(Exception p){
				return false;
			}
	}
	
	public Rectangle recCollisionWithEnemy(enemy e){
		return getMyBounds().intersection(e.getMyBounds());
	}
	
	public Rectangle recCollisionWithUnit(Unit unit){
		return getMyBounds().intersection(unit.getBounds());
	}
	
	public void fall(){ //fall is called a lot
		if(!onGround){
		velocity-=0.5*gravity;
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
		System.out.println(characterName+" mx "+myMapX+" my "+myMapY+" sx "+x+" sy "+y+" onG "
				+onGround);
	}
	
	public void setOnGround(boolean og){
		onGround=og;
	}
}
