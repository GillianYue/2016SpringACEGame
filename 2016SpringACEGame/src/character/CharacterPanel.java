package character;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import enemies.enemy;
import enemies.flower;
import mainPac.ImageLoader;
import map.MapPanel;
import map.Unit;

public class CharacterPanel extends JPanel{
	
	private int pWidth, pHeight;
	public Bird bird; public Frog frog;
	public int[][] enemiesData;
	public int initialX=20, initialY=35;
	ImageLoader Il;
	public static ArrayList<enemy> enemies, enemiesGarbage;
	public static character.Character mainCharacter;
	
	public CharacterPanel(int width, int height, ImageLoader il){
		pWidth=width;
		pHeight=height;
		Il=il;
		enemies = new ArrayList<enemy>();
		enemiesGarbage = new ArrayList<enemy>();
		enemiesData= new int [MapPanel.mapMaxX][MapPanel.mapMaxY];
		bird= new Bird(initialX, initialY, il, this);
		mainCharacter=bird;
	}
	

	public void paintComponent(Graphics g){
		bird.drawCharacter(g);
		for(int Umapx=MapPanel.currmapMinX; Umapx<MapPanel.currmapMaxX; Umapx++){
			for(int Umapy=MapPanel.mapMinY; Umapy<MapPanel.mapMaxY; Umapy++){
				int enemyNum = enemiesData[Umapx][Umapy];
	if(enemyNum>0){//start of making the enemy
		if(enemyNum==1){//flower
			enemies.add(new flower(Umapx, Umapy, Il, this,-1));
			enemiesData[Umapx][Umapy]=enemyNum*-1;
			//make enemy and set the int to negative so it isnt made again	
		}
	}//end of making the enemy
	
			}
		}
	for(enemy e:enemies){
		if(e.getMapX()<MapPanel.currmapMinX || e.getMapX()>MapPanel.currmapMaxX){
			e.setDisplay(false);
		}else{
			e.setDisplay(true);
		}
		e.drawCharacter(g);
	}
	for (enemy e:enemiesGarbage) {
		enemies.remove(e);
	}
	enemiesGarbage.clear();
	}//end paint


}
