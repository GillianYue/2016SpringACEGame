package enemies;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import character.CharacterPanel;
import mainPac.ImageLoader;
import map.MapPanel;

public class enemy extends character.Character{
	Timer walkTimer; int picWidth, picHeight;
	boolean display;
	//enemies that are defeated are removed from the enemies list
	//enemies that go beyond the screen's bounds are just not drawn and not processed
	public enemy(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp);
		facingDirection=facing;
		display=true;
		walkTimer = new Timer (300, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
		rotateWalkingStatus();
			}
			
		});
	}

	public void drawCharacter(Graphics g){
		if(checkAlive() && display){
		if(facingDirection==1){
		g.drawImage(myImage, x, y, x+picWidth,
				y+picHeight, (myStatus%3)*45, (myStatus/3)*45,
				(myStatus%3)*45+45, (myStatus/3)*45+45, null);
		}else if(facingDirection==-1){
			g.drawImage(myImage, x+45, y, x,
					y+45, (myStatus%3)*45, (myStatus/3)*45,
					(myStatus%3)*45+45, (myStatus/3)*45+45, null);
		}
		}else if(!checkAlive()){
			CharacterPanel.enemies.remove(this);
		}
	}
	
	public void coordinatesSyncMapToScreen (){
		myMapX=(x/10+MapPanel.currmapMinX);
		myMapY=y/10;
	}
	
	public void startWalkingTimer(){
		walkTimer.start();
	}
	
	public void stopWalkingTimer(){
		walkTimer.stop();
	}
	
	public void setDisplay(boolean tOrf){
		display=tOrf;
	}
	
	public boolean checkAlive(){
		if(HP>0){
		return true;
		}else{
			return false;
		}
	}
	
	public boolean enemyOnScreen(){
		return display;
	}
	

}
