package character;

import java.awt.Graphics;
import java.awt.Rectangle;

import TempObjects.Note;
import mainPac.ImageLoader;
import map.MapPanel;

public class Bird extends Character{
	//our protagonist for now..
	//STATUS DATA:
	//0 normal, 0-2 walking,3-5 blank, 6 attack, 7 jump, 8 fall
	public boolean noteOn;
	ImageLoader Il;
	Note note;
	
	public Bird(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp){
		super(initialmapX, initialmapY, il, cp);
		myImage = il.tori;
		Il=il;
		characterName = "Chirpy";
		numOfWalkingStatus=3; //0, 1, 2
		individualWidth=15; 
		individualHeight=40;
	}
	
	@Override
	public void drawCharacter(Graphics g){
		if(facingDirection==1){
		g.drawImage(myImage, x, y, x+45,
				y+45, (myStatus%3)*45, (myStatus/3)*45,
				(myStatus%3)*45+45, (myStatus/3)*45+45, null);
		}else if(facingDirection==-1){
			g.drawImage(myImage, x+45, y, x,
					y+45, (myStatus%3)*45, (myStatus/3)*45,
					(myStatus%3)*45+45, (myStatus/3)*45+45, null);
		}
		if(noteOn){
			//draw the note and move the note!!
			note.paintObject(g);
		}else{
			note=null;
		}
	}
	
	@Override
	public void moveNStepRight(int steps){
		if(onGround){
		rotateWalkingStatus();
		}
		x+=10*steps;
		myMapX+=1*steps; //on the map it moved one unit to the right. one unit is 10 pixels
	}
	
	@Override
	public void moveNStepLeft(int steps){
		if(onGround){
		rotateWalkingStatus();
		}
		x-=10*steps;
		myMapX-=1*steps;
	}
	
	public void jump(){ //jump once
		if(onGround){
		myStatus=7;
		velocity=40;
		falling=true;
		}
	}
	
	public void rightJump(){
		if(onGround ){
			hVelo=-16;
			velocity=40;
			myStatus=7;
			falling=true;
			}
	}
	
	public void leftJump(){
		if(onGround){
			hVelo=16;
			velocity=40;
			myStatus=7;
			falling=true;
			}
	}
	public void fall(){ //fall is called a lot
		if(velocity<0 && !onGround){
		myStatus=8;
		}
		if(!onGround){
		velocity-=0.5*gravity;
		}
	}
	
	public void attack(){
		if(!noteOn){
		myStatus=6;
		if(facingDirection==1){
		note = new Note(myMapX+4, myMapY+2, Il, facingDirection, this, characterPanel);
		}else{
		note = new Note(myMapX-1, myMapY+2, Il, facingDirection, this, characterPanel);
		}
		noteOn=true;
		}
	}
	
	@Override
	public Rectangle getMyBounds(){
		return new Rectangle(x+15, y+5, individualWidth, individualHeight);
	}
}
