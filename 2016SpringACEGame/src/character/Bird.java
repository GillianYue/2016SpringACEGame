package character;

import java.awt.Graphics;

import mainPac.ImageLoader;

public class Bird extends Character{
	//our protagonist for now..
	//STATUS DATA:
	//0 normal, 0-2 walking,3-5 blank, 6 attack, 7 jump, 8 fall
	
	public Bird(int initialmapX, int initialmapY, ImageLoader il){
		super(initialmapX, initialmapY, il);
		myImage = il.tori;
		characterName = "Chirpy";
		numOfWalkingStatus=3; //0, 1, 2
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
	}
	
	@Override
	public void moveOneStepRight(){
		rotateWalkingStatus();
		x+=10;
		myMapX+=1; //on the map it moved one unit to the right. one unit is 10 pixels
	}
	
	@Override
	public void moveOneStepLeft(){
		rotateWalkingStatus();
		x-=10;
		myMapX-=1;
	}
	
	public void jump(){ //jump once
		myStatus=7;
		velocity=40;
		falling=true;
	}
	
	public void fall(){ //fall is called a lot
		if(velocity<0){
		myStatus=8;
		}
		velocity-=0.5*gravity;
	}
	
}
