package mainPac;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {
	public BufferedImage bird, icon, mountains, sun, terrainpiece;
	
	public ImageLoader(){
		bird=loadImage("Pics/bird.png", bird);
		icon=loadImage("Pics/icon.png", icon);
		mountains=loadImage("Pics/mountains.png", mountains);
		sun=loadImage("Pics/sun.png", sun);
		terrainpiece=loadImage("Pics/terrainpiece.png", terrainpiece);
	}
	
	public BufferedImage loadImage(String fileName, BufferedImage image){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream input;
		input= cl.getResourceAsStream(fileName);
		try {
			image=ImageIO.read(input);
			System.out.println("IOsuccess! "+fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	
}
