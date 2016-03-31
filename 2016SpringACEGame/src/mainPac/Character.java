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
	
	public Character(String fileName, int initialX, int initialY){
		x=initialX;
		y=initialY;
		loadImage(fileName, myImage);
	}
	
	public void drawCharacter(Graphics g, int flippedOrNot){
		g.drawImage(myImage, x, y, null);
		g.drawImage(myImage, x, y, x+flippedOrNot*myImage.getWidth(),
				y, 0, 0, myImage.getWidth(), myImage.getHeight(), null);
		//for now, the character can not be tilted because dy2 is set to y
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
	
}
