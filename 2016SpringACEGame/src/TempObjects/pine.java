package TempObjects;

import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class pine extends tree{

	public pine(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.pine;
		
	}
	
	public Rectangle getBounds(){
	return new Rectangle(10*(mx-MapPanel.currmapMinX)+10,10*my, 10, myImage.getHeight());
	}
	
}
