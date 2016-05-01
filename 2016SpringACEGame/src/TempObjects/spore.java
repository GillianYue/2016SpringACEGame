package TempObjects;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import enemies.mushroom;
import mainPac.ImageLoader;
import map.MapPanel;

public class spore extends tempObject{
	int xD, yD; int count; mushroom MyM;
	public spore(int sporeNum, int MX, int MY, mushroom myMushroom,
			ImageLoader il, int whichShroom, int xDirection, int yDirection) {
		super(MX, MY, il, 1);
		MyM=myMushroom;
		xD=xDirection;
		yD=yDirection;
		myImage=il.spore.getSubimage(whichShroom*10, 0, 10, 10);
		myT=new Timer(300, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mx+=xD;
				my+=yD;
				count++;
				if(count==3){
					disappear();
					myMushroom.mySpores[sporeNum]=null;
				}
			}
			
		});
		myT.start();
	}

	
}
