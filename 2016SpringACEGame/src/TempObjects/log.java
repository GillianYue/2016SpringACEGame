package TempObjects;

import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class log extends tempObject{

	public log(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.log;
		for(int l=0; l<=4; l++){
		MapPanel.map[MX+l][MY]=100;
		}
	}

	public Rectangle getBounds(){
	return new Rectangle(10*(mx-MapPanel.currmapMinX)+10,10*my+5, 30, myImage.getHeight()-5);
	}
}
