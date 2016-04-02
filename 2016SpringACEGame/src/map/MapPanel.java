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
	
	private int[][] map; //gives the unit the whatamI data
	ImageLoader Il;
	
	public MapPanel(int width, int height, ImageLoader il){
		this.setOpaque(false);
		pWidth = width;
		pHeight = height;
		Il=il;
		mapMinX=0;  mapMinY=0; 
		mapMaxX=100;  mapMaxY=50; //sets the overall max and min for the map
		currmapMinX=0; currmapMaxX=pWidth/10; 
		map = new int [mapMaxX][mapMaxY];
		for(int mapx=mapMinX; mapx<mapMaxX; mapx++){
			for(int mapy=mapMinY; mapy<mapMaxY; mapy++){//coordinates on map
				map[mapx][mapy]=0; //nothing
			}
		}
		for(int tl=0; tl<70; tl++){
			map[tl][43]=2;
			for(int t=44; t<50; t++){
			map[tl][t]=1; //terrain pieces	
			}
		}
		for(int e=70; e<80; e++){
			map[e][20]=2;
			map[e][21]=1;
		}
		for(int ee=80; ee<100; ee++){
			map[ee][35]=2;
			map[ee][36]=2;
			map[ee][37]=1;
			map[ee][38]=1;
			map[ee][39]=1;
			map[ee][40]=1;
		}
	}
	
	public void updateUnits (Graphics g){ //draws the part of the map that should be displayed on screen
		for(int Umapx=currmapMinX; Umapx<currmapMaxX; Umapx++){
			for(int Umapy=mapMinY; Umapy<mapMaxY; Umapy++){
				if (map[Umapx][Umapy]==1){
					Unit tempUnit = new Unit (MapToScreenConverter(Umapx), Umapy*10, 1, Il);
					tempUnit.drawUnit(g);
				}else if(map[Umapx][Umapy]==2){
					Unit tempUnit = new Unit (MapToScreenConverter(Umapx), Umapy*10, 2, Il);
					tempUnit.drawUnit(g);
				}
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
}
