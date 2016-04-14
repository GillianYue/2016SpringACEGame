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
import character.Frog;
import map.MapPanel;
import map.Unit;

public class ControlPanel extends JPanel implements KeyListener, ActionListener{
	Set<Integer> pressedKeyCode = new HashSet<Integer>();
	char pressedKeyChar;
	Bird bird; Frog frog; MovingBackground BG;
	int pwidth;
	Timer t, t2;
	double gravity=9.8; 
	ImageLoader Il;
	boolean lost;
	CharacterPanel characterPanel;
	MapPanel mapPanel;
	character.Character mainCharacter;
	
	public ControlPanel(MapPanel mp, BackgroundPanel bp, CharacterPanel character, ImageLoader il){
		bird=character.bird;
		frog=character.frog;
		BG=bp.mountains;
		characterPanel=character;
		mapPanel=mp;
		Il=il;
		this.setFocusable(true);
		addKeyListener(this);
		mainCharacter=bird;
		pwidth=bp.getWidth();
		t = new Timer (40, this);
		t.start();
		t2 = new Timer (10, new ActionListener(){

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
		if(pressedKeyCode.contains(e.getKeyCode()==KeyEvent.VK_DOWN)&&
				pressedKeyCode.contains(e.getKeyCode()==KeyEvent.VK_A)){
		bird.returnToOriginalStatus();
		}else if(e.getKeyCode()==KeyEvent.VK_A){
			if(bird.squat){
		bird.changeStatus(3);
			}else{
				bird.changeStatus(0);
			}
		}else{
		bird.returnToOriginalStatus();	
		}
		
		pressedKeyCode.remove(e.getKeyCode());
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
					bird.walking=true;
					}else if((MapPanel.currmapMinX == MapPanel.mapMinX)
							&& (bird.getScreenX() <= 200)){
						bird.moveNStepLeft(2);
						bird.walking=true;
					}
					}
			}else if(set.contains(KeyEvent.VK_SHIFT) &&set.contains(KeyEvent.VK_RIGHT)){
				if(bird.getScreenX()<pwidth-50){
					bird.faceRight();
					if(bird.getScreenX() < 500){//if it's in the middle of the panel
					bird.moveNStepRight(2);
					bird.walking=true;
					}else if((MapPanel.currmapMaxX == MapPanel.mapMaxX)
						&& (bird.getScreenX() >= 500)){
							bird.moveNStepRight(2);
							bird.walking=true;
						}
					}
			}  if(set.contains(KeyEvent.VK_DOWN) && set.contains(KeyEvent.VK_A)){
				bird.squatAttack();
			}
			
		}else{
		if(set.contains(KeyEvent.VK_RIGHT)){
			if(bird.getScreenX()<pwidth-50){
			bird.faceRight();
			if(bird.getScreenX() < 500){//if it's in the middle of the panel
			bird.moveNStepRight(1);
			bird.walking=true;
			}else if((MapPanel.currmapMaxX == MapPanel.mapMaxX)
				&& (bird.getScreenX() >= 500)){
					bird.moveNStepRight(1);
					bird.walking=true;
				}
			}
		}
		if(set.contains(KeyEvent.VK_LEFT)){
			if(bird.getScreenX()>5){
			bird.faceLeft();
			if(bird.getScreenX()>200){//if it's in the middle of the panel
			bird.moveNStepLeft(1);
			bird.walking=true;
			}else if((MapPanel.currmapMinX == MapPanel.mapMinX)
					&& (bird.getScreenX() <= 200)){
				bird.moveNStepLeft(1);
				bird.walking=true;
			}
			}
		}
		if(set.contains(KeyEvent.VK_DOWN)){
			bird.squat();
		}
		if(set.contains(KeyEvent.VK_SPACE)){
			bird.jump();
		}
		if(set.contains(KeyEvent.VK_A)){
			bird.attack();
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
		mainCharacterCombo (mainCharacter);
		checkForLose();
		if(lost){
			restartLevel();
			lost=false;
		}
		
		
}
	
	public void mainCharacterCombo (character.Character c){
		gravity(c); 
		coordinatesSync(c);
		collisionDetection(c);
	}
	
	public void gravity(character.Character c){
		c.setScreeny(c.getScreenY()-(int)(c.velocity*0.3));
	if(c.characterName.equals("Chirpy")){
		bird.setScreenX(bird.getScreenX()-(int)(bird.hVelo));
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
	}
	}
	
	public void coordinatesSync (character.Character c){
		c.setMapX((c.getScreenX()/10)+MapPanel.currmapMinX);
		c.setMapY(c.getScreenY()/10);
		if(!c.walking && !c.squat){
			c.changeStatus(0);
		}
	}
	
	public void collisionDetection(character.Character c){
	
		if(c.falling){
			c.fall();
		}	
		if(c.myDirection()==1){
		//start horizontal testing: Right
		if(MapPanel.map[c.getMapX()+4][c.getMapY()+1]>0 ||
				MapPanel.map[c.getMapX()+4][c.getMapY()+3]>0){
			//if it's a terrain unit
	Unit unitToTest = (MapPanel.map[c.getMapX()+4][c.getMapY()+1]>0) ? 
 MapPanel.units[c.getMapX()+4-MapPanel.currmapMinX][c.getMapY()+1]:
	 MapPanel.units[c.getMapX()+4-MapPanel.currmapMinX][c.getMapY()+3];
			if(c.collisionWithUnit(unitToTest)){ //check the unit
c.setScreenX(c.getScreenX() - (int)c.recCollisionWithUnit(unitToTest).getWidth()); 
	//pushes the character back to its desired position
				if(c.hVelo<0)
				c.hVelo=0;
				}
		}else if(MapPanel.map[c.getMapX()+3][c.getMapY()+1]>0 ||
				MapPanel.map[c.getMapX()+3][c.getMapY()+3]>0){
			//if it's a terrain unit
	Unit unitToTest = (MapPanel.map[c.getMapX()+3][c.getMapY()+1]>0) ? 
 MapPanel.units[c.getMapX()+3-MapPanel.currmapMinX][c.getMapY()+1]:
	 MapPanel.units[c.getMapX()+3-MapPanel.currmapMinX][c.getMapY()+3];
			if(c.collisionWithUnit(unitToTest)){ //check the unit
c.setScreenX(c.getScreenX() - (int)c.recCollisionWithUnit(unitToTest).getWidth()); 
	//pushes the character back to its desired position
			if(c.hVelo<0)
				c.hVelo=0;
				}
		}else if(MapPanel.map[c.getMapX()+2][c.getMapY()+1]>0 ||
				MapPanel.map[c.getMapX()+2][c.getMapY()+2]>0){
			//if it's a terrain unit
	Unit unitToTest = (MapPanel.map[c.getMapX()+2][c.getMapY()+1]>0) ? 
 MapPanel.units[c.getMapX()+2-MapPanel.currmapMinX][c.getMapY()+1]:
	 MapPanel.units[c.getMapX()+2-MapPanel.currmapMinX][c.getMapY()+2];
			if(c.collisionWithUnit(unitToTest)){ //check the unit
c.setScreenX(c.getScreenX() - (int)c.recCollisionWithUnit(unitToTest).getWidth()); 
	//pushes the character back to its desired position
			if(c.hVelo<0)
				c.hVelo=0;
				}
		}	
		//end of horizontal testing
		}else{
			//start horizontal testing:Left
			if(MapPanel.map[c.getMapX()][c.getMapY()+1]>0 ||
					MapPanel.map[c.getMapX()][c.getMapY()+3]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[c.getMapX()][c.getMapY()+1]>0) ? 
	 MapPanel.units[c.getMapX()-MapPanel.currmapMinX][c.getMapY()+1]:
		 MapPanel.units[c.getMapX()-MapPanel.currmapMinX][c.getMapY()+3];
				if(c.collisionWithUnit(unitToTest)){ //check the unit
	c.setScreenX(c.getScreenX() + (int)c.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(c.hVelo>0)
					c.hVelo=0;
					}
			}else if(MapPanel.map[c.getMapX()+1][c.getMapY()+1]>0 ||
					MapPanel.map[c.getMapX()+1][c.getMapY()+3]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[c.getMapX()+1][c.getMapY()+1]>0) ? 
	 MapPanel.units[c.getMapX()+1-MapPanel.currmapMinX][c.getMapY()+1]:
		 MapPanel.units[c.getMapX()+1-MapPanel.currmapMinX][c.getMapY()+3];
				if(c.collisionWithUnit(unitToTest)){ //check the unit
	c.setScreenX(c.getScreenX() + (int)c.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(c.hVelo>0)
					c.hVelo=0;
					}
			}else if(MapPanel.map[c.getMapX()+2][c.getMapY()+1]>0 ||
					MapPanel.map[c.getMapX()+2][c.getMapY()+2]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[c.getMapX()+2][c.getMapY()+1]>0) ? 
	 MapPanel.units[c.getMapX()+2-MapPanel.currmapMinX][c.getMapY()+1]:
		 MapPanel.units[c.getMapX()+2-MapPanel.currmapMinX][c.getMapY()+2];
				if(c.collisionWithUnit(unitToTest)){ //check the unit
	c.setScreenX(c.getScreenX() + (int)c.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(c.hVelo>0)
					c.hVelo=0;
					}
			}	
			//end of horizontal testing
		}
		
		//start upper unit testing
		if(MapPanel.map[c.getMapX()+1][c.getMapY()]>0 ||
				MapPanel.map[c.getMapX()+1][c.getMapY()-1]>0	){
			//check the upper units
	Unit unitToTest2 = (MapPanel.map[c.getMapX()+1][c.getMapY()]>0) ? 
 MapPanel.units[c.getMapX()+1-MapPanel.currmapMinX][c.getMapY()]:
	 MapPanel.units[c.getMapX()+1-MapPanel.currmapMinX][c.getMapY()-1];
			if(c.collisionWithUnit(unitToTest2)){ //check the unit
	c.setScreeny(c.getScreenY() + (int)c.recCollisionWithUnit(unitToTest2).getHeight()); 
	//pushes the character back to its desired position
				c.velocity=0;
				}
		}
		if(MapPanel.map[c.getMapX()+2][c.getMapY()]>0 ||
				MapPanel.map[c.getMapX()+2][c.getMapY()-1]>0	){
			//check the upper units
	Unit unitToTest2 = (MapPanel.map[c.getMapX()+2][c.getMapY()]>0) ? 
 MapPanel.units[c.getMapX()+2-MapPanel.currmapMinX][c.getMapY()]:
	 MapPanel.units[c.getMapX()+2-MapPanel.currmapMinX][c.getMapY()-1];
			if(c.collisionWithUnit(unitToTest2)){ //check the unit
	c.setScreeny(c.getScreenY() + (int)c.recCollisionWithUnit(unitToTest2).getHeight()); 
	//pushes the character back to its desired position
				c.velocity=0;
				}
		}
		if(MapPanel.map[c.getMapX()+3][c.getMapY()]>0 ||
				MapPanel.map[c.getMapX()+3][c.getMapY()-1]>0	){
			//check the upper units
	Unit unitToTest2 = (MapPanel.map[c.getMapX()+3][c.getMapY()]>0) ? 
 MapPanel.units[c.getMapX()+3-MapPanel.currmapMinX][c.getMapY()]:
	 MapPanel.units[c.getMapX()+3-MapPanel.currmapMinX][c.getMapY()-1];
			if(c.collisionWithUnit(unitToTest2)){ //check the unit
	c.setScreeny(c.getScreenY() + (int)c.recCollisionWithUnit(unitToTest2).getHeight()); 
	//pushes the character back to its desired position
				c.velocity=0;
				}
	}
		//end upper unit testing
		
		//start ground collision test: 
		boolean check1=false, check2=false;
			if(MapPanel.map[c.getMapX()+2][c.getMapY()+4]>0 ||
					MapPanel.map[c.getMapX()+2][c.getMapY()+5]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[c.getMapX()+2][c.getMapY()+4]>0) ? 
	 MapPanel.units[c.getMapX()+2-MapPanel.currmapMinX][c.getMapY()+4]:
		 MapPanel.units[c.getMapX()+2-MapPanel.currmapMinX][c.getMapY()+5];
				if(c.collisionWithUnit(unitToTest)){ //check the unit
	c.setScreeny(c.getScreenY() - (int)c.recCollisionWithUnit(unitToTest).getHeight()); 
		//pushes the character back to its desired position
					c.velocity=0;
					c.setOnGround(true);
					check1=true;
					}
			}	
			
				if(MapPanel.map[c.getMapX()+3][c.getMapY()+4]>0 ||
						MapPanel.map[c.getMapX()+3][c.getMapY()+5]>0	){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[c.getMapX()+3][c.getMapY()+4]>0) ? 
		 MapPanel.units[c.getMapX()+3-MapPanel.currmapMinX][c.getMapY()+4]:
			 MapPanel.units[c.getMapX()+3-MapPanel.currmapMinX][c.getMapY()+5];
		 if(c.collisionWithUnit(unitToTest)){ //check the unit
	c.setScreeny(c.getScreenY() - (int)c.recCollisionWithUnit(unitToTest).getHeight()); 
		//pushes the character back to its desired position
				c.velocity=0;
				c.setOnGround(true);
				check2=true;
				}
				}
				//end of lower units check'
				if(	MapPanel.map[c.getMapX()+2][c.getMapY()+5]==0){
					c.setOnGround(false);
	}
	}
	
	public void checkForLose(){
		if(mainCharacter.getMapY()>55){
			System.out.println("you lose!!!!!");
		   lost=true;
		}
	}
	
	public void restartLevel(){
		bird.resetCharacter();
		mapPanel.resetMapPanel();
	}
}
