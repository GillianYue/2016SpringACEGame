package background;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import mainPac.ImageLoader;

public class MovingBackground{
	BufferedImage pic1, pic2;
	int pic1Loc, pic2Loc;
	int PicWidth; double movingSpeed;
	
	public MovingBackground(double MovingSpeed, JPanel myPanel, ImageLoader il){
		pic1 = il.mountains;
		pic2 = il.mountains;
		PicWidth = pic1.getWidth();
		//initializing the starting locations of pic1 & pic2
		pic1Loc = 0;
		pic2Loc = PicWidth;
		movingSpeed=MovingSpeed;
	}
	
	public void updateMovingBackground(Graphics g){

		g.drawImage(pic1, pic1Loc, 0, null);
		g.drawImage(pic2, pic2Loc, 0, null);
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