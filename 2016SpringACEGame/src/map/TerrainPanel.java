package map;

import java.awt.Graphics;

import javax.swing.JPanel;

import mainPac.ImageLoader;

public class TerrainPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pWidth, pHeight; 
	int[][] map; //gives the unit the whatamI data
	ImageLoader Il;
	
	public TerrainPanel(int width, int height, ImageLoader il){
		this.setOpaque(false);
		pWidth = width;
		pHeight = height;
		Il=il;
		
		map = new int [70][50];
		for(int mapx=0; mapx<=69; mapx++){
			for(int mapy=0; mapy<=49; mapy++){//coordinates on map
				map[mapx][mapy]=0; //nothing
			}
		}
		for(int t=0; t<=69; t++){
			map[t][43]=1; //terrain pieces
		}
		

	}
	
	public void updateUnits (Graphics g){
		for(int mapx=0; mapx<=69; mapx++){
			//should only read what's going to be displayed on screen (but in map coordinates)
			for(int mapy=0; mapy<=49
					; mapy++){
				if (map[mapx][mapy]==1){
					Unit tempUnit = new Unit (mapx*10, mapy*10, 1, Il);
					tempUnit.drawUnit(g);
				}
			}
		}
	}
	
	public void paintComponent(Graphics g){
		updateUnits(g);
	}
	
}
