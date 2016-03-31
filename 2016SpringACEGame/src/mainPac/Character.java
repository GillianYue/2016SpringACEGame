package mainPac;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Character {
	int x, y;
	BufferedImage myImage;
	int facingDirection;	//1 is not flipped; -1 is flipped
	
	public Character(String fileName, int initialX, int initialY){
		x=initialX;
		y=initialY;
		facingDirection=1; //Default facing right
		loadImage(fileName, myImage);
	}
	
	public void drawCharacter(Graphics g){
		g.drawImage(myImage, x, y, x+facingDirection*myImage.getWidth(),
				y+
				myImage.getHeight(),
				0, 0, myImage.getWidth(), myImage.getHeight(), null);
		//for now it draws the whole image
	}
	
	public void loadImage(String fileName, BufferedImage image){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream input;
		input= cl.getResourceAsStream(fileName);
		try {
			myImage=ImageIO.read(input);
			System.out.println("IOsuccess! "+fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void faceRight(){
		facingDirection=1;
	}
	
	public void faceLeft(){
		facingDirection=-1;
	}
}
