package TempObjects;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import enemies.mushroom;
import mainPac.ImageLoader;
import map.MapPanel;

public class spore extends tempObject{
	int xD, yD; int count; mushroom MyM; character.Character mainC; spore me;
	public spore(int sporeNum, int MX, int MY, mushroom myMushroom,
			ImageLoader il, int whichShroom, int xDirection, int yDirection, character.Character c) {
		super(MX, MY, il, 1);
		MyM=myMushroom;
		xD=xDirection;
		yD=yDirection;
		mainC=c;
		me=this;
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
				}else{
					if(mainC.collisionWithObject(me) && !mainC.injured){
						mainC.hVelo=mainC.myDirection()*10;
						mainC.HP-=1;
						mainC.injured=true;
						mainC.changeStatus(5);//bird's status 5 is injured
						Timer tempT = new Timer (1000, new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
								mainC.injured=false;
							}
						});
						tempT.setRepeats(false);
						tempT.start();
					
				}
			}
			}
		});
		myT.start();
	}

	
}
