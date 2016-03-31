package character;

import java.awt.Graphics;


import javax.swing.JPanel;

public class CharacterPanel extends JPanel{
	
	private int pWidth, pHeight;
	public Bird bird;
	
	public CharacterPanel(int width, int height){
		pWidth=width;
		pHeight=height;
		this.setOpaque(false);
		bird= new Bird("Pics/bird.png", 200, 300);
	}
	
	public void paintComponent(Graphics g){
		bird.drawCharacter(g); 
	}


}
