package mainPac;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CharacterPanel extends JPanel{
	
	private int pWidth, pHeight;
	Bird bird;
	
	public CharacterPanel(int width, int height){
		pWidth=width;
		pHeight=height;
		this.setOpaque(false);
		bird= new Bird("Pics/bird.png", 20, 300);
	}
	
	public void paintComponent(Graphics g){
		bird.drawCharacter(g, 1);//not flipped; positive
		System.out.println("characterPanel trying to paint");
	}
	
}
