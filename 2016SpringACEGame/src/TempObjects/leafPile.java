package TempObjects;

import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class leafPile extends tempObject{

	public leafPile(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.leafPile;
		for(int l=0; l<=5; l++){
		MapPanel.map[MX+l][MY+1]=99;//don't want collision detection to actually work
		}
	}

	public Rectangle getBounds(){
	return new Rectangle(10*(mx-MapPanel.currmapMinX)+10,10*my+10, 30, myImage.getHeight()-10);
	}
}
