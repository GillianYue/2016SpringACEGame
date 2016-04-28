package map;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import TempObjects.mushroom;
import TempObjects.oak;
import TempObjects.pine;
import TempObjects.tempObject;
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
	
	public static int[][] map, objData; //gives the unit the whatamI data: 1 layer, 2 terrain
	public static ArrayList<tempObject> objects, objGarbage;
	public static Unit[][] units;
	ImageLoader Il;
	
	public MapPanel(int width, int height, ImageLoader il){
		this.setOpaque(false);
		pWidth = width;
		pHeight = height;
		Il=il;
		mapMinX=0;  mapMinY=0; 
		mapMaxX=1000;  mapMaxY=60; //sets the overall max and min for the map
		currmapMinX=0; currmapMaxX=pWidth/10; 
		map = new int [mapMaxX][mapMaxY];
		objData = new int [mapMaxX][mapMaxY];
		objects = new ArrayList<tempObject>();
		objGarbage = new ArrayList<tempObject>();
		units = new Unit [currmapMaxX][mapMaxY]; 
		for(int mapx=mapMinX; mapx<mapMaxX; mapx++){
			for(int mapy=mapMinY; mapy<mapMaxY; mapy++){//coordinates on map
				map[mapx][mapy]=0; //nothing
			}
		}
	

	}
	
	public void updateMap (Graphics g){ //draws the part of the map that should be displayed on screen
		for(int Umapx=currmapMinX; Umapx<currmapMaxX; Umapx++){
			for(int Umapy=mapMinY; Umapy<mapMaxY; Umapy++){
		units[Umapx-currmapMinX][Umapy-mapMinY] = new Unit (MapToScreenConverter(Umapx), Umapy*10, 
						map[Umapx][Umapy], Il, Umapx, Umapy);
		units[Umapx-currmapMinX][Umapy-mapMinY].drawUnit(g);
		int objNum = objData [Umapx][Umapy];
		if(objNum>0){
			if(objNum==1){
		objects.add(new pine(Umapx, Umapy, Il, 1));
			}else if(objNum==2){
		objects.add(new oak(Umapx, Umapy, Il, 1));
			}else if(objNum==3){
				objects.add(new TempObjects.log(Umapx, Umapy, Il, 1));
			}else if(objNum==4){
				objects.add(new mushroom(Umapx, Umapy, Il, 1));
			}
			objData [Umapx][Umapy] *=-1;
		}
			}
		}
	}
	@Override
	public void paintComponent(Graphics g){
		updateMap(g);
		for(tempObject o:objects){
			if(o.getObjMX()>=MapPanel.currmapMinX && o.getObjMX()<=MapPanel.currmapMaxX){
			o.paintObject(g);
		}else{//if it's not in the screen range
			objGarbage.add(o);
		}
		}
		if(objGarbage.size()>0){
		for(tempObject o:objGarbage){
			objData[o.getObjMX()][o.getObjMY()]*=-1;
			objects.remove(o);
		}
		objGarbage.clear();
		}
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
