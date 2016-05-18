package TempObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import mainPac.ImageLoader;
import map.MapPanel;

public class spring extends tempObject{
	//1 being stepped on
	public spring(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.spring;
		status=0;
		objWidth=20;
		objHeight=20;
	}

	@Override
	public void paintObject(Graphics g){
		g.drawImage(myImage.getSubimage(status*30, 0, 30, 30),
				10*(mx-MapPanel.currmapMinX), 10*my, null);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(10*(mx-MapPanel.currmapMinX)+5,10*my+10,
				objWidth, objHeight);
	}
}
