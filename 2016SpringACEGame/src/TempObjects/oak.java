package TempObjects;

import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class oak extends tree{

	public oak(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.oak;
		for(int h=0; h<=3; h++){
			if(h!=0){
			MapPanel.map[MX+1][MY+h]=100;
			MapPanel.map[MX+3][MY+h]=100;
			}//want collision detection to work
			MapPanel.map[MX+2][MY+h]=100;
		}
	}

	public Rectangle getBounds(){
		return new Rectangle(10*(mx-MapPanel.currmapMinX)+5, 10*my+5, 30, myImage.getHeight()-5);
		}
}
