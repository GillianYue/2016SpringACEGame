package enemies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import TempObjects.spore;
import character.CharacterPanel;
import mainPac.ImageLoader;
import map.MapPanel;

public class mushroom extends enemy{
	int mushroomNum; mushroom me;
	Timer sporeTimer;
	public spore[] mySpores;
	public mushroom(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp, facing);
		mushroomNum=(int)(Math.random()/0.34);
		myImage=il.mushroom.getSubimage(0,mushroomNum*30, 90, 30
				);
		myStatus=1;
		me=this;
		mySpores= new spore[3];
		characterName="mushroom";
		hVelo=0;
		numOfWalkingStatus=3;
		walking=true;
		picWidth=30;
		picHeight=30;
		HP=2;
		sporeTimer = new Timer (2500, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			mySpores[0]=new spore (0, myMapX+1, myMapY-1, me, il, mushroomNum, 0, -1, cp.mainCharacter);
			mySpores[1]=new spore (1, myMapX-1, myMapY, me,  il, mushroomNum, -1, 0, cp.mainCharacter);
			mySpores[2]=new spore (2, myMapX+4, myMapY, me, il, mushroomNum, 1, 0, cp.mainCharacter);
			}
			
		});
		sporeTimer.start();
	}
	

	@Override
	public void drawCharacter(Graphics g){
		if(checkAlive() && display){
		g.drawImage(myImage.getSubimage(myStatus*30, 0, picWidth, picHeight),
				x, y, null);
//		if(!sporeTimer.isRunning()){
//			sporeTimer.start();
//		}
		for(int i=0; i<=2; i++){
			if(mySpores[i]!=null)
			mySpores[i].paintObject(g);
		}
		}else if (!display){
			sporeTimer.stop();
		}
		
	}
	
	@Override
	public Rectangle getMyBounds(){
	return new Rectangle(x+10, y+10, 10, 20);
	}
	
	@Override
	public boolean gravityApplies(){
		return false;
	}
	
	@Override
	public boolean injurable(){
		return false;
	}
	
	@Override
	public boolean damageColliding(){
		return false;
	}
}
