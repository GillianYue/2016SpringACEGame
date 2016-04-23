package TempObjects;

import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class oak extends tree{

	public oak(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.oak;
	}

	public Rectangle getBounds(){
		return new Rectangle(10*(mx-MapPanel.currmapMinX)+5, 10*my+5, 20, myImage.getHeight()-5);
		}
}
