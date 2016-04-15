package character;

import java.awt.Graphics;


import javax.swing.JPanel;

import enemies.flower;
import mainPac.ImageLoader;
import map.MapPanel;
import map.Unit;

public class CharacterPanel extends JPanel{
	
	private int pWidth, pHeight;
	public Bird bird; public Frog frog;
	public int[][] enemies;
	public int initialX=20, initialY=35;
	ImageLoader Il;
	
	public CharacterPanel(int width, int height, ImageLoader il){
		pWidth=width;
		pHeight=height;
		Il=il;
		enemies= new int [MapPanel.mapMaxX][MapPanel.mapMaxY];
		enemies[50][36]=1;//flower monster
		bird= new Bird(initialX, initialY, il, this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		bird.drawCharacter(g);
	}


}
