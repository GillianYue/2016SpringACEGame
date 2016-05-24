package TempObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import character.CharacterPanel;
import mainPac.ImageLoader;
import map.MapPanel;

public class fruit extends tempObject{
	boolean up; int Ydif;
	public fruit(int MX, int MY, ImageLoader il, int direction) {
		super(MX, MY, il, direction);
		myImage=il.fruits.getSubimage((int)(Math.random()/0.5)*20, (int)(Math.random()/0.5)*20,
				20, 20);
		up=false;
		Timer floating = new Timer (100, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(up){
					Ydif+=1;
					if(Ydif>=5)
					up=false;
				}else{
					Ydif-=1;
					if(Ydif<=0)
					up=true;
				}
			}
			
		});
		floating.start();
	}

	
	public Rectangle getBounds(){
		return new Rectangle(10*(mx-MapPanel.currmapMinX),10*my,
				20, 20);
	}
	
	public void paintObject(Graphics g){
		
		g.drawImage(myImage, 10*(mx-MapPanel.currmapMinX), 10*my-Ydif, null);
	}
	
}
