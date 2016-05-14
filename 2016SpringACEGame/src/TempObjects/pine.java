package TempObjects;

import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class pine extends tree{

	public pine(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.pine;
		for(int h=0; h<=3; h++){
			MapPanel.map[MX+1][MY+h]=100;//want collision detection to work
			MapPanel.map[MX+2][MY+h]=100;
			MapPanel.map[MX+3][MY+h]=100;
		}
	}
	
	public Rectangle getBounds(){
	return new Rectangle(10*(mx-MapPanel.currmapMinX)+10,10*my, 20, myImage.getHeight());
	}
	
}
