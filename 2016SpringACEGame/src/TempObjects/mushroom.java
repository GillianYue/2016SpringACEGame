package TempObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import character.CharacterPanel;
import enemies.enemy;
import mainPac.ImageLoader;
import map.MapPanel;

public class mushroom extends tempObject{

	private int mushroomStatus; //0, 1, 2
	
	public mushroom(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.mushroom.getSubimage(0,(int)(Math.random()/0.34)*30, 90, 30
				);
		objWidth=30;
		objHeight=30;
		mushroomStatus=1;
	}

	@Override
	public void paintObject(Graphics g){
		g.drawImage(myImage.getSubimage(mushroomStatus*30, 0, objWidth, objHeight),
				10*(mx-MapPanel.currmapMinX), 10*my, null);
	}
	
	@Override
	public Rectangle getBounds(){
	return new Rectangle(10*(mx-MapPanel.currmapMinX)+10,10*my+8,10, objHeight);
	}
}
