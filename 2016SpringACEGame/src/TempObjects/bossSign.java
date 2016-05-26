package TempObjects;

import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class bossSign extends tempObject{

	public bossSign(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.bossSign;
		
	}

	
	public Rectangle getBounds(){
	return new Rectangle(0,0,0,0);
	}
}
