package character;

import java.awt.Graphics;


import javax.swing.JPanel;

import mainPac.ImageLoader;

public class CharacterPanel extends JPanel{
	
	private int pWidth, pHeight;
	public Bird bird;
	
	public CharacterPanel(int width, int height, ImageLoader il){
		pWidth=width;
		pHeight=height;
		this.setOpaque(false);
		bird= new Bird(20, 38, il);
	}
	
	public void paintComponent(Graphics g){
		bird.drawCharacter(g); 
	}


}
