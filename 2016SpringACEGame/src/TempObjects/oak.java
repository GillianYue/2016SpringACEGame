package TempObjects;

import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class oak extends tree{

	public oak(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.oak;
	for(int h=0; h<=4; h++){
		MapPanel.map[MX+1][MY+h]=100;
		MapPanel.map[MX+2][MY+h]=100;
	}
	}

	public Rectangle getBounds(){
		return new Rectangle(10*(mx-MapPanel.currmapMinX)+5, 10*my+5, 30, myImage.getHeight()-5);
		}
}