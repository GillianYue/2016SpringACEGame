package mainPac;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import background.BackgroundPanel;
import character.CharacterPanel;
import map.TerrainPanel;

public class Game {
	public static BufferedImage Icon;
	public static int fWidth=700, fHeight=500;
	
	public static void main(String[] args) throws IOException {
		JFrame gameFrame= new JFrame(); 
		
		gameFrame.setSize(fWidth, fHeight);
	
		gameFrame.setResizable(false);
		addIconImage("Pics/icon.png", gameFrame);
	
		ImageLoader imageLoader = new ImageLoader();
		
		JLayeredPane jlp = new JLayeredPane();
	
		BackgroundPanel backgroundPanel = new BackgroundPanel(fWidth,fHeight, imageLoader);
		backgroundPanel.setSize(fWidth, fHeight);
		
		CharacterPanel characterPanel = new CharacterPanel(fWidth, fHeight, imageLoader);
		characterPanel.setSize(fWidth, fHeight);
		
		TerrainPanel terrainPanel = new TerrainPanel (fWidth, fHeight, imageLoader);
		terrainPanel.setSize(fWidth, fHeight);
		
		jlp.add(backgroundPanel, Integer.valueOf(1));
		jlp.add(characterPanel, Integer.valueOf(2));
		jlp.add(terrainPanel, Integer.valueOf(3));
		jlp.setVisible(true);

		gameFrame.add(jlp);
		gameFrame.setVisible(true);
		
		ControlPanel controlPanel = new ControlPanel(backgroundPanel, characterPanel);
		gameFrame.add(controlPanel);
		
		PanelUpdater pu = new PanelUpdater (characterPanel);
		
		gameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void addIconImage(String icon, JFrame jframe){
		loadImage(icon, Icon);
		jframe.setIconImage(Icon);//test
		System.out.println("trying to add icon");
	}
	
	public static void loadImage(String fileName, BufferedImage image){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream input;
		input= cl.getResourceAsStream(fileName);
		try {
			image=ImageIO.read(input);
			System.out.println("IOsuccess!"+fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
