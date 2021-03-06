package character;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import audio.BGMPlayer;
import enemies.bug;
import enemies.enemy;
import enemies.flower;
import enemies.mushroom;
import enemies.treeBoss;
import enemies.treeMonster;
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
	
	public CharacterPanel(int width, int height, ImageLoader il, BGMPlayer bgm){
		pWidth=width;
		pHeight=height;
		Il=il;
		enemies = new ArrayList<enemy>();
		enemiesGarbage = new ArrayList<enemy>();
		enemiesData= new int [MapPanel.mapMaxX][MapPanel.mapMaxY];
		bird= new Bird(initialX, initialY, il, this, bgm);
		mainCharacter=bird;
	}
	

	public void paintComponent(Graphics g){
		mainCharacter.drawCharacter(g);
		for(int Umapx=MapPanel.currmapMinX; Umapx<MapPanel.currmapMaxX; Umapx++){
			for(int Umapy=MapPanel.mapMinY; Umapy<MapPanel.mapMaxY; Umapy++){
				int enemyNum = enemiesData[Umapx][Umapy];
	if(enemyNum>0){//start of making the enemy
		if(enemyNum==1){//flower
			enemies.add(new flower(Umapx, Umapy, Il, this, (Math.random()>0.5)? 1:-1 ));
		}else if(enemyNum==2){
			enemies.add(new mushroom(Umapx, Umapy, Il, this, 1));
		}else if(enemyNum==3){
			enemies.add(new treeMonster(Umapx, Umapy, Il, this, 1));
		}else if(enemyNum==4){
			enemies.add(new treeBoss(Umapx, Umapy, Il, this, 1));
		}else if(enemyNum==5){
			enemies.add(new bug(Umapx, Umapy, Il, this, 1));
		}
		
		enemiesData[Umapx][Umapy]=enemyNum*-1;
		//make enemy and set the int to negative so it isnt made again	
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
	}//end paint


}
