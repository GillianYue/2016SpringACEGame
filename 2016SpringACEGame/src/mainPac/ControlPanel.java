package mainPac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import background.BackgroundPanel;
import background.MovingBackground;
import character.Bird;
import character.CharacterPanel;
import map.MapPanel;
import map.Unit;

public class ControlPanel extends JPanel implements KeyListener, ActionListener{
	Set<Integer> pressedKeyCode = new HashSet<Integer>();
	char pressedKeyChar;
	Bird bird; MovingBackground BG;
	int pwidth;
	Timer t, t2;
	double gravity=9.8; 
	ImageLoader Il;
	boolean lost;
	CharacterPanel characterPanel;
	MapPanel mapPanel;
	
	public ControlPanel(MapPanel mp, BackgroundPanel bp, CharacterPanel character, ImageLoader il){
		bird=character.bird;
		BG=bp.mountains;
		characterPanel=character;
		mapPanel=mp;
		Il=il;
		this.setFocusable(true);
		addKeyListener(this);
		pwidth=bp.getWidth();
		t = new Timer (40, this);
		t.start();
		t2 = new Timer (50, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				
			}
			
		});
		}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeyCode.add(e.getKeyCode());
			updateMapInterval(pressedKeyCode);
			moveTheBird(pressedKeyCode);
			moveTheBackground(pressedKeyCode);
		}
	

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeyCode.remove(e.getKeyCode());
		bird.returnToOriginalStatus();
	}

	
	public void moveTheBird(Set<Integer> set){
		if(set.size()>1){
			if(set.contains(KeyEvent.VK_RIGHT)&& set.contains(KeyEvent.VK_SPACE)){
				if(bird.getScreenX()<pwidth-50){
					bird.faceRight();
					if(bird.getScreenX() < 500){//if it's in the middle of the panel
					bird.rightJump();
					}else if((MapPanel.currmapMaxX+8> MapPanel.mapMaxX)
						&& (bird.getScreenX() >= 500)){
							bird.rightJump();
						}
					}
			
			}else if(set.contains(KeyEvent.VK_LEFT)&&set.contains(KeyEvent.VK_SPACE)){
				if(bird.getScreenX()>5){
					bird.faceLeft();
					if(bird.getScreenX()>200){//if it's in the middle of the panel
					bird.leftJump();
					}else if((MapPanel.currmapMinX -8< MapPanel.mapMinX)
							&& (bird.getScreenX() <= 200)){
						bird.leftJump();
					}
					}
			}else if(set.contains(KeyEvent.VK_SHIFT)&&set.contains(KeyEvent.VK_LEFT)){
				if(bird.getScreenX()>5){
					bird.faceLeft();
					if(bird.getScreenX()>200){//if it's in the middle of the panel
					bird.moveNStepLeft(2);
					}else if((MapPanel.currmapMinX == MapPanel.mapMinX)
							&& (bird.getScreenX() <= 200)){
						bird.moveNStepLeft(2);
					}
					}
			}else if(set.contains(KeyEvent.VK_SHIFT) &&set.contains(KeyEvent.VK_RIGHT)){
				if(bird.getScreenX()<pwidth-50){
					bird.faceRight();
					if(bird.getScreenX() < 500){//if it's in the middle of the panel
					bird.moveNStepRight(2);
					}else if((MapPanel.currmapMaxX == MapPanel.mapMaxX)
						&& (bird.getScreenX() >= 500)){
							bird.moveNStepRight(1);
						}
					}
			}
			
		}else{
		if(set.contains(KeyEvent.VK_RIGHT)){
			if(bird.getScreenX()<pwidth-50){
			bird.faceRight();
			if(bird.getScreenX() < 500){//if it's in the middle of the panel
			bird.moveNStepRight(1);
			}else if((MapPanel.currmapMaxX == MapPanel.mapMaxX)
				&& (bird.getScreenX() >= 500)){
					bird.moveNStepRight(1);
				}
			}
		}
		if(set.contains(KeyEvent.VK_LEFT)){
			if(bird.getScreenX()>5){
			bird.faceLeft();
			if(bird.getScreenX()>200){//if it's in the middle of the panel
			bird.moveNStepLeft(1);
			}else if((MapPanel.currmapMinX == MapPanel.mapMinX)
					&& (bird.getScreenX() <= 200)){
				bird.moveNStepLeft(1);
			}
			}
		}
		if(set.contains(KeyEvent.VK_SPACE)){
			bird.jump();
		}
	}
	}
	
	public void moveTheBackground(Set<Integer> set){
//since this is the background, it moves the opposite way of the character's moving direction
		if(bird.hVelo==0){
		if((set.contains(KeyEvent.VK_RIGHT)) && (MapPanel.currmapMaxX < MapPanel.mapMaxX)
				&& (bird.getScreenX() >= 500)){
			BG.moveBackground("left");
		}
		if((set.contains(KeyEvent.VK_LEFT)) && (MapPanel.currmapMinX > MapPanel.mapMinX)
				&& (bird.getScreenX() <= 200)){
			BG.moveBackground("right");
		}
		}
	}
	
	public void updateMapInterval(Set<Integer> set){
		if(bird.hVelo==0){
		if((set.contains(KeyEvent.VK_RIGHT)) && (set.contains(KeyEvent.VK_SPACE)) &&
			(MapPanel.currmapMaxX < MapPanel.mapMaxX) && (bird.getScreenX() >= 500)	
			&& (MapPanel.currmapMaxX+8<=MapPanel.mapMaxX)){
	
			bird.jump();
			MapPanel.currmapMinX+=8;
			MapPanel.currmapMaxX+=8;
		}else{ 
			if((set.contains(KeyEvent.VK_RIGHT)) && (MapPanel.currmapMaxX < MapPanel.mapMaxX)
				&& (bird.getScreenX() >= 500)){
			bird.rotateWalkingStatus();
			MapPanel.currmapMinX+=1;
			MapPanel.currmapMaxX+=1;
		}
			
		if((set.contains(KeyEvent.VK_LEFT)) && (MapPanel.currmapMinX > MapPanel.mapMinX)
				&& (bird.getScreenX() <= 200)){
			bird.rotateWalkingStatus();
			MapPanel.currmapMinX-=1;
			MapPanel.currmapMaxX-=1;
			bird.setMapX(bird.getMapX()-1);
		}
		}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		bird.setScreeny(bird.getScreenY()-(int)(bird.velocity*0.3));
		
		bird.setScreenX(bird.getScreenX()-(int)(bird.hVelo));
		bird.setMapX((bird.getScreenX()/10)+MapPanel.currmapMinX);
		if(bird.hVelo!=0){
			
			if(bird.hVelo>0){
				if(bird.hVelo>5){
			 bird.hVelo-=2;
				}else{
					bird.hVelo-=1;
				}
			}else{
				if(bird.hVelo<-5){
			bird.hVelo+=2;
				}else{
					bird.hVelo+=1;
				}
			}
		}
		
		bird.setMapY(bird.getScreenY()/10);
		
		if(bird.falling){
			bird.fall();
		}
		checkForLose();
		if(lost){
			restartLevel();
			lost=false;
		}
		
		if(bird.getScreenX()%10<=5){
		if(MapPanel.map[bird.getMapX()+1][bird.getMapY()+4]>0 ||
				MapPanel.map[bird.getMapX()+1][bird.getMapY()+5]>0	){
			//if it's a terrain unit
	Unit unitToTest = (MapPanel.map[bird.getMapX()+1][bird.getMapY()+4]>0) ? 
 MapPanel.units[bird.getMapX()+1-MapPanel.currmapMinX][bird.getMapY()+4]:
	 MapPanel.units[bird.getMapX()+1-MapPanel.currmapMinX][bird.getMapY()+5];
			if(bird.collisionWithUnit(unitToTest)){ //check the unit
	bird.setScreeny(bird.getScreenY() - (int)bird.recCollisionWithUnit(unitToTest).getHeight()); 
	//pushes the character back to its desired position
				bird.changeStatus(0);
				bird.velocity=0;
				bird.setOnGround(true);
				}
		}
		}else if(bird.getScreenX()%10>5){
			if(MapPanel.map[bird.getMapX()+2][bird.getMapY()+4]>0 ||
					MapPanel.map[bird.getMapX()+2][bird.getMapY()+5]>0	){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[bird.getMapX()+2][bird.getMapY()+4]>0) ? 
	 MapPanel.units[bird.getMapX()+2-MapPanel.currmapMinX][bird.getMapY()+4]:
		 MapPanel.units[bird.getMapX()+2-MapPanel.currmapMinX][bird.getMapY()+5];
	 if(bird.collisionWithUnit(unitToTest)){ //check the unit
bird.setScreeny(bird.getScreenY() - (int)bird.recCollisionWithUnit(unitToTest).getHeight()); 
	//pushes the character back to its desired position
			bird.changeStatus(0);
			bird.velocity=0;
			bird.setOnGround(true);
			}
			}
		}
		if(MapPanel.map[bird.getMapX()+1][(int)((bird.getScreenY()+45)/10)]==0){
			bird.setOnGround(false);
		}else if(MapPanel.map[bird.getMapX()+1][(int)((bird.getScreenY()+45)/10)]>0){
			bird.setOnGround(true);
		}
		//bird.printMyCoordinates();
}
	
	public void checkForLose(){
		if(bird.getMapY()>55){
			System.out.println("you lose!!!!!");
		   lost=true;
		}
	}
	
	public void restartLevel(){
		bird.resetCharacter();
		mapPanel.resetMapPanel();
	}
}
