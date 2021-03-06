package mainPac;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;

import audio.BGMPlayer;
import background.BackgroundPanel;
import character.CharacterPanel;
import map.MapPanel;

public class Game {
	public static BufferedImage Icon;
	public static int fWidth=700, fHeight=500;
	
	public static void main(String[] args) throws IOException {
		JFrame gameFrame= new JFrame(); 
		
		gameFrame.setSize(fWidth, fHeight);
	
		gameFrame.setResizable(false);
		addIconImage("Pics/icon.png", gameFrame);
	
		ImageLoader imageLoader = new ImageLoader();
		
		BGMPlayer bgm = new BGMPlayer();
		
		JLayeredPane jlp = new JLayeredPane();
		
		BackgroundPanel backgroundPanel = new BackgroundPanel(fWidth,fHeight, imageLoader);
		backgroundPanel.setSize(fWidth, fHeight);
		
		MapPanel mapPanel = new MapPanel (fWidth, fHeight, imageLoader);
		mapPanel.setSize(fWidth, fHeight); mapPanel.setOpaque(false);
		
		CharacterPanel characterPanel = new CharacterPanel(fWidth, fHeight, imageLoader, bgm);
		characterPanel.setSize(fWidth, fHeight); characterPanel.setOpaque(false);
		
		XMLReader xmlReader = new XMLReader(mapPanel, characterPanel);
		
		jlp.add(backgroundPanel, Integer.valueOf(1));
		jlp.add(characterPanel, Integer.valueOf(2));
		jlp.add(mapPanel, Integer.valueOf(3));
		jlp.setVisible(true);

		
		gameFrame.add(jlp);
		gameFrame.setVisible(true);
		
		PanelUpdater pu = new PanelUpdater (jlp);
		
		ControlPanel controlPanel = new ControlPanel(mapPanel, backgroundPanel, characterPanel,
				imageLoader, xmlReader, bgm, pu);
		gameFrame.add(controlPanel);
		
		
		controlPanel.requestFocus();
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
