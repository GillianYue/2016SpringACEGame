package mainPac;

import java.awt.Graphics;


import javax.swing.JPanel;

public class MovingBackground{
	Background pic1, pic2;
	int pic1Loc, pic2Loc;
	int PicWidth; double movingSpeed;
	
	public MovingBackground(String fileName, double MovingSpeed, JPanel myPanel){
		pic1 = new Background(fileName, myPanel);
		pic2 = new Background(fileName, myPanel);
		PicWidth = pic1.getBackgroundWidth();
		//initializing the starting locations of pic1 & pic2
		pic1Loc = 0;
		pic2Loc = PicWidth;
		movingSpeed=MovingSpeed;
	}
	
	public void updateMovingBackground(Graphics g){

		pic1.drawBackground(g, pic1Loc, 0, null);
		pic2.drawBackground(g, pic2Loc, 0, null);
	}

	public double getMovingSpeed(){
		return movingSpeed;
	}
	
	public void moveBackground(String leftOrRight){
		if (leftOrRight.equals("left")){
		pic1Loc -= 20 * movingSpeed;
		pic2Loc -= 20 * movingSpeed;
		if(PicWidth*-1 - pic1Loc >=0){
			pic1Loc = PicWidth - (PicWidth*-1 - pic1Loc);
		}
		if(PicWidth*-1 - pic2Loc >=0){
			pic2Loc = PicWidth - (PicWidth*-1 - pic2Loc);
		}
		}else if(leftOrRight.equals("right")){
		pic1Loc += 20 * movingSpeed;
		pic2Loc += 20 * movingSpeed;
		if(pic1Loc - PicWidth>=0){
			pic1Loc =  -1*PicWidth + (pic1Loc - PicWidth);
		}
		if(pic2Loc - PicWidth>=0){
			pic2Loc =  -1*PicWidth + (pic2Loc - PicWidth);
		}
		}
		
		
	}
	
}
