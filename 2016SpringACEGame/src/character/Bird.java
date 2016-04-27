package character;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import TempObjects.Hearts;
import TempObjects.Note;
import mainPac.ImageLoader;
import map.MapPanel;

public class Bird extends Character{
	//our protagonist for now..
	//STATUS DATA:
	//0 normal, 0-2 walking,3 squat, 4 sattack, 5 injured, 6 attack, 7 jump, 8 fall
	public boolean noteOn;
	ImageLoader Il;
	Note note;
	Hearts hearts;
	
	public Bird(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp){
		super(initialmapX, initialmapY, il, cp);
		myImage = il.tori;
		Il=il;
		HP=6; maxHP=6;
		hearts= new Hearts(0, 0, il, 1, this);
		characterName = "Chirpy";
		numOfWalkingStatus=3; //0, 1, 2
		individualWidth=35; 
		individualHeight=40;
	}
	
	@Override
	public void drawCharacter(Graphics g){
		hearts.drawHearts(g);
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
		hVelo=-5*steps; //on the map it moved one unit to the right. one unit is 10 pixels
		if(walking && onGround && !jumping){
			rotateWalkingStatus();
		}
	}
	
	@Override
	public void moveNStepLeft(int steps){
		hVelo=5*steps;
		if(walking && onGround && !jumping){
			rotateWalkingStatus();
		}
	}
	
	public void jump(){ //jump once
		if(onGround){
		jumping=true;
		myStatus=7;
		velocity=40;
		}
	}
	
	public void rightJump(){
		if(onGround){
			hVelo=-16;
			velocity=40;
			myStatus=7;
			}
	}
	
	public void leftJump(){
		if(onGround){
			hVelo=16;
			velocity=40;
			myStatus=7;
			}
	}
	public void fall(){ //fall is called a lot
		if(velocity<0 && !onGround ){
		myStatus=8;
		}
		if(!onGround){
		velocity-=0.5*gravity;
		}
	}
	
	@Override
	public void returnToOriginalStatus(){
		myStatus=0;
		squat=false;
		walking=false;
		jumping=false;
	}
	
	public void squat(){
		squat=true;
		myStatus=3;
	}
	
	public void squatAttack(){
		myStatus=4;
		if(facingDirection==1){
			note = new Note(myMapX+5, myMapY+3, Il, facingDirection, this, characterPanel);
			}else{
			note = new Note(myMapX-1, myMapY+3, Il, facingDirection, this, characterPanel);
			}
			noteOn=true;
		attacking=true;
	}
	
	public void attack(){
		if(!noteOn){
		myStatus=6;
		if(facingDirection==1){
		note = new Note(myMapX+5, myMapY+2, Il, facingDirection, this, characterPanel);
		}else{
		note = new Note(myMapX-1, myMapY+2, Il, facingDirection, this, characterPanel);
		}
		noteOn=true;
		}
		attacking=true;
		
	}
	
	public void printMyStatus(){
		System.out.println("HP: "+HP+"; OnGround: "+onGround+"; status: "+
	myStatus+"; walkin: "+walking+"; jumpin: "+jumping+"; squattin: "+squat);
	}
	
	@Override
	public Rectangle getMyBounds(){
		return new Rectangle(x+5, y+5, individualWidth, individualHeight);
	}
}
