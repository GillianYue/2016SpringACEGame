package mainPac;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Game {

	public static void main(String[] args) throws IOException {
		int fWidth=700, fHeight=500;
		JFrame gameFrame= new JFrame(); 
		
		
		gameFrame.setSize(fWidth, fHeight);
		gameFrame.setResizable(false);
		addIconImage("Pics/icon.png", gameFrame);
		
		BackgroundPanel backgroundPanel = new BackgroundPanel(fWidth,fHeight);
		//background image is added in background panel
		backgroundPanel.setSize(fWidth, fHeight);
		
		gameFrame.add(backgroundPanel);
		gameFrame.setVisible(true);
		backgroundPanel.repaint();
		
		gameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void addIconImage(String icon, JFrame jframe){
		jframe.setIconImage(loadImage(icon));//test
		System.out.println("trying to add icon");
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
