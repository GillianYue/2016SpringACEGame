package map;

import java.awt.Graphics;

import javax.swing.JPanel;

import mainPac.ImageLoader;

public class MapPanel extends JPanel{
	/**
	 * right now the map data is generated here: int[][] map
	 * There are three types of coordinates in this class:
	 * 1. screen coordinates: X [0,700] Y [0,500] 
	 * 2. map coordinates: X [0,a] Y [0,50] 
	 * 3. current map coordinates: X [b,b+70] Y[0,50](for now) 
	 * current map coordinates represents which section of map coordinates should be loaded on screen
	 */
	public static int mapMinX, mapMaxX, mapMinY, mapMaxY, currmapMinX, currmapMaxX;
	private static final long serialVersionUID = 1L;
	private int pWidth, pHeight; 
	
	public static int[][] map; //gives the unit the whatamI data
	public static Unit[][] units;
	ImageLoader Il;
	
	public MapPanel(int width, int height, ImageLoader il){
		this.setOpaque(false);
		pWidth = width;
		pHeight = height;
		Il=il;
		mapMinX=0;  mapMinY=0; 
		mapMaxX=100;  mapMaxY=60; //sets the overall max and min for the map
		currmapMinX=0; currmapMaxX=pWidth/10; 
		map = new int [mapMaxX][mapMaxY];
		units = new Unit [currmapMaxX][mapMaxY]; 
		for(int mapx=mapMinX; mapx<mapMaxX; mapx++){
			for(int mapy=mapMinY; mapy<mapMaxY; mapy++){//coordinates on map
				map[mapx][mapy]=0; //nothing
			}
		}
		for(int tl=0; tl<80; tl++){
			map[tl][43]=1;
			for(int t=44; t<50; t++){
			map[tl][t]=2; //terrain pieces	
			}
			if(tl==30){
				for(int y=39; y<=42; y++){
				map[tl][y]=2;
				}
			}
		}
		for(int s=35; s<45; s++){
			if(s>36 && s<44){
			map[s][37]=1;
			map[s][38]=2;
			}
			
		}
		
		for(int e=50; e<80; e++){
			map[e][33]=1;
			map[e][34]=2;
		}
		for(int ee=80; ee<100; ee++){
		
			map[ee][38]=1;
			map[ee][39]=2;
			map[ee][40]=2;
		}
	}
	
	public void updateUnits (Graphics g){ //draws the part of the map that should be displayed on screen
		for(int Umapx=currmapMinX; Umapx<currmapMaxX; Umapx++){
			for(int Umapy=mapMinY; Umapy<mapMaxY; Umapy++){
		units[Umapx-currmapMinX][Umapy-mapMinY] = new Unit (MapToScreenConverter(Umapx), Umapy*10, 
						map[Umapx][Umapy], Il, Umapx, Umapy);
		units[Umapx-currmapMinX][Umapy-mapMinY].drawUnit(g);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		updateUnits(g);
	}
	
	public int MapToScreenConverter(int mymapXPos){
/*		this converts the given current map x coordinates to 
		corresponding values on screen coordinates. 
		for example, if a unit is at (12,10) on the real map
		and the map's current displayed interval is [10,80]
		then the unit should be printed at (20,100) on screen (10*(12-10),10*10)
		Just in case i get confused in the future */
		return ((mymapXPos - currmapMinX)*10);
	}
	
	public void resetMapPanel(){
		currmapMinX=0; currmapMaxX=pWidth/10; 
	}
}
