package mainPac;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Character {

	public Character(){
		
	}
	
	public static Image loadImage(String fileName){
		Image image = null;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream input;
		input= cl.getResourceAsStream(fileName);
		try {
			image=ImageIO.read(input);
			System.out.println("IOsuccess!"+fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
