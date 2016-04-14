package character;

import java.awt.Graphics;


import javax.swing.JPanel;

import mainPac.ImageLoader;

public class CharacterPanel extends JPanel{
	
	private int pWidth, pHeight;
	public Bird bird; public Frog frog;
	public int initialX=20, initialY=35;
	
	public CharacterPanel(int width, int height, ImageLoader il){
		pWidth=width;
		pHeight=height;
		this.setOpaque(false);
		bird= new Bird(initialX, initialY, il, this);
	}
	
	public void paintComponent(Graphics g){
		bird.drawCharacter(g);
	}


}
