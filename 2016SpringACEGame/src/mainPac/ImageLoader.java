package mainPac;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {
	public BufferedImage bird, icon, mountains, sun, terrainpiece, terrainlayer, tori, kero,
	notes, flower, hearts, pine, oak, stone, log;
	
	public ImageLoader(){
		bird=loadImage("Pics/bird.png", bird);
		icon=loadImage("Pics/icon.png", icon);
		mountains=loadImage("Pics/mountains.png", mountains);
		sun=loadImage("Pics/sun.png", sun);
		terrainpiece=loadImage("Pics/terrainpiece.png", terrainpiece);
		terrainlayer=loadImage("Pics/terrainlayer.png", terrainlayer);
		tori=loadImage("Pics/tori.png", tori);
		kero=loadImage("Pics/kero.png", kero);
		notes=loadImage("Pics/notes.png", notes);
		flower=loadImage("Pics/flowermonsters.png", flower);
		hearts=loadImage("Pics/hearts.png", hearts);
		pine=loadImage("Pics/pine.png", pine);
		oak=loadImage("Pics/oak.png", oak);
		stone=loadImage("Pics/stone.png", stone);
		log=loadImage("Pics/log.png", log);
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
