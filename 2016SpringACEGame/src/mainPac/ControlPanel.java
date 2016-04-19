package mainPac;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import background.BackgroundPanel;
import background.MovingBackground;
import character.Bird;
import character.CharacterPanel;
import character.Frog;
import enemies.enemy;
import map.MapPanel;
import map.Unit;

public class ControlPanel extends JPanel implements KeyListener, ActionListener{
	Set<Integer> pressedKeyCode = new HashSet<Integer>();
	char pressedKeyChar;
	Bird bird; Frog frog; MovingBackground BG;
	int pwidth;
	Timer t;
	double gravity=9.8; 
	ImageLoader Il;
	boolean lost;
	MapPanel mapPanel;
	character.Character mainCharacter;
	/*
	 * in coordinates sync the character's status is constantly reset
	 */
	public ControlPanel(MapPanel mp, BackgroundPanel bp, CharacterPanel character, ImageLoader il){
		bird=character.bird;
		frog=character.frog;
		BG=bp.mountains;
		mapPanel=mp;
		Il=il;
		this.setFocusable(true);
		addKeyListener(this);
		mainCharacter=bird;
		pwidth=bp.getWidth();
		t = new Timer (40, this);
		t.start();
		
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
		if(pressedKeyCode.contains(KeyEvent.VK_DOWN)){
		bird.squat=false;
		}else if(e.getKeyCode()==KeyEvent.VK_A){
			if(bird.squat){
		bird.changeStatus(3);
			}else{
				bird.changeStatus(0);
			}
		}
		if(pressedKeyCode.contains(KeyEvent.VK_RIGHT) || 
				pressedKeyCode.contains(KeyEvent.VK_LEFT)){
		bird.walking=false;
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
 (bird.getScreenX() >= 500)	&& (MapPanel.currmapMaxX+8 <=MapPanel.mapMaxX)){
	
			bird.jump();
			MapPanel.currmapMinX+=8;
			MapPanel.currmapMaxX+=8;
			for(enemy e:CharacterPanel.enemies){
				e.setScreenX(e.getScreenX()-80);
			}
		}else if((set.contains(KeyEvent.VK_LEFT)) && (set.contains(KeyEvent.VK_SPACE)) &&
	 (bird.getScreenX() <=200)	 && (MapPanel.currmapMinX-8 >=MapPanel.mapMinX)){
			bird.jump();
			MapPanel.currmapMinX-=8;
			MapPanel.currmapMaxX-=8;
			for(enemy e:CharacterPanel.enemies){
				e.setScreenX(e.getScreenX()+80);
			}
		}	else{ 
		}
			if((set.contains(KeyEvent.VK_RIGHT)) && (MapPanel.currmapMaxX < MapPanel.mapMaxX)
				&& (bird.getScreenX() >= 500)){
			bird.rotateWalkingStatus();
			MapPanel.currmapMinX+=1;
			MapPanel.currmapMaxX+=1;
			for(enemy e:CharacterPanel.enemies){
				e.setScreenX(e.getScreenX()-10);
			}
		}
			
		if((set.contains(KeyEvent.VK_LEFT)) && (MapPanel.currmapMinX > MapPanel.mapMinX)
				&& (bird.getScreenX() <= 200)){
			bird.rotateWalkingStatus();
			MapPanel.currmapMinX-=1;
			MapPanel.currmapMaxX-=1;
			for(enemy e:CharacterPanel.enemies){
				e.setScreenX(e.getScreenX()+10);
			}
		}
		}
		}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		mainCharacterCombo (mainCharacter);
		enemyCombo(CharacterPanel.enemies);
		checkForLose();
		if(lost){
			restartLevel();
			lost=false;
		}
		
}
	
	public void mainCharacterCombo (character.Character mc){
		gravityMainCharacter(mc); 
		coordinatesSync(mc);
		collisionDetection(mc);
	}
	
	public void enemyCombo(ArrayList<enemy> eList){
		for(enemy e: eList){
			if(e.walking){
				e.startWalkingTimer();
			}else{
				e.stopWalkingTimer();
			}
			collisionDetectionForEnemy(e);
			e.coordinatesSyncMapToScreen();
			gravityForEnemies(e);
			e.coordinatesSyncMapToScreen();
		}
	}
	
	public void gravityForEnemies(enemy e){
		e.setScreeny(e.getScreenY()-(int)(e.velocity*0.3));
		e.setScreenX(e.getScreenX()-(int)(e.hVelo*0.3));
		
	}
	
	public void gravityMainCharacter(character.Character e){
		e.setScreeny(e.getScreenY()-(int)(e.velocity*0.3));
		e.fall();
	if(e.characterName.equals("Chirpy")){
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
	
	public void coordinatesSync (character.Character e){
		e.setMapX((e.getScreenX()/10)+MapPanel.currmapMinX);
		e.setMapY(e.getScreenY()/10);
		if(!e.walking && !e.squat && !e.jumping && !e.injured){
			e.changeStatus(0);
		}
	}
	
	public void collisionDetection(character.Character e){
	
			
		if(e.myDirection()==1){
		//start horizontal testing: Right
		if(MapPanel.map[e.getMapX()+4][e.getMapY()+1]>0 ||
				MapPanel.map[e.getMapX()+4][e.getMapY()+3]>0){
			//if it's a terrain unit
	Unit unitToTest = (MapPanel.map[e.getMapX()+4][e.getMapY()+1]>0) ? 
 MapPanel.units[e.getMapX()+4-MapPanel.currmapMinX][e.getMapY()+1]:
	 MapPanel.units[e.getMapX()+4-MapPanel.currmapMinX][e.getMapY()+3];
			if(e.collisionWithUnit(unitToTest)){ //check the unit
e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
	//pushes the character back to its desired position
				if(e.hVelo<0)
				e.hVelo=0;
				}
		}else if(MapPanel.map[e.getMapX()+3][e.getMapY()+1]>0 ||
				MapPanel.map[e.getMapX()+3][e.getMapY()+3]>0){
			//if it's a terrain unit
	Unit unitToTest = (MapPanel.map[e.getMapX()+3][e.getMapY()+1]>0) ? 
 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+1]:
	 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+3];
			if(e.collisionWithUnit(unitToTest)){ //check the unit
e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
	//pushes the character back to its desired position
			if(e.hVelo<0)
				e.hVelo=0;
				}
		}else if(MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0 ||
				MapPanel.map[e.getMapX()+2][e.getMapY()+2]>0){
			//if it's a terrain unit
	Unit unitToTest = (MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0) ? 
 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+1]:
	 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+2];
			if(e.collisionWithUnit(unitToTest)){ //check the unit
e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
	//pushes the character back to its desired position
			if(e.hVelo<0)
				e.hVelo=0;
				}
		}	
		//end of horizontal testing
		}else{
			//start horizontal testing:Left
			if(MapPanel.map[e.getMapX()][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()][e.getMapY()+3]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()][e.getMapY()+1]>0) ? 
	 MapPanel.units[e.getMapX()-MapPanel.currmapMinX][e.getMapY()+1]:
		 MapPanel.units[e.getMapX()-MapPanel.currmapMinX][e.getMapY()+3];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(e.hVelo>0)
					e.hVelo=0;
					}
			}else if(MapPanel.map[e.getMapX()+1][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+1][e.getMapY()+3]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+1][e.getMapY()+1]>0) ? 
	 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+1]:
		 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+3];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(e.hVelo>0)
					e.hVelo=0;
					}
			}else if(MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+2][e.getMapY()+2]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0) ? 
	 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+1]:
		 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+2];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(e.hVelo>0)
					e.hVelo=0;
					}
			}	
			//end of horizontal testing
		}
		
		//start upper unit testing
		if(MapPanel.map[e.getMapX()+1][e.getMapY()]>0 ||
				MapPanel.map[e.getMapX()+1][e.getMapY()-1]>0	){
			//check the upper units
	Unit unitToTest2 = (MapPanel.map[e.getMapX()+1][e.getMapY()]>0) ? 
 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()]:
	 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()-1];
			if(e.collisionWithUnit(unitToTest2)){ //check the unit
	e.setScreeny(e.getScreenY() + (int)e.recCollisionWithUnit(unitToTest2).getHeight()); 
	//pushes the character back to its desired position
				e.velocity=0;
				}
		}
		if(MapPanel.map[e.getMapX()+2][e.getMapY()]>0 ||
				MapPanel.map[e.getMapX()+2][e.getMapY()-1]>0	){
			//check the upper units
	Unit unitToTest2 = (MapPanel.map[e.getMapX()+2][e.getMapY()]>0) ? 
 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()]:
	 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()-1];
			if(e.collisionWithUnit(unitToTest2)){ //check the unit
	e.setScreeny(e.getScreenY() + (int)e.recCollisionWithUnit(unitToTest2).getHeight()); 
	//pushes the character back to its desired position
				e.velocity=0;
				}
		}
		if(MapPanel.map[e.getMapX()+3][e.getMapY()]>0 ||
				MapPanel.map[e.getMapX()+3][e.getMapY()-1]>0	){
			//check the upper units
	Unit unitToTest2 = (MapPanel.map[e.getMapX()+3][e.getMapY()]>0) ? 
 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()]:
	 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()-1];
			if(e.collisionWithUnit(unitToTest2)){ //check the unit
	e.setScreeny(e.getScreenY() + (int)e.recCollisionWithUnit(unitToTest2).getHeight()); 
	//pushes the character back to its desired position
				e.velocity=0;
				}
	}
		//end upper unit testing
		
	//	start ground collision test: 
		if(MapPanel.map[e.getMapX()+1][e.getMapY()+4]>0 ||
				MapPanel.map[e.getMapX()+1][e.getMapY()+5]>0){
			//if it's a terrain unit
	Unit unitToTest = (MapPanel.map[e.getMapX()+1][e.getMapY()+4]>0) ? 
 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+4]:
	 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+5];
			if(e.collisionWithUnit(unitToTest)){ //check the unit
			e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
	//pushes the character back to its desired position
				e.velocity=0;
				e.setOnGround(true);
				e.jumping=false;
				}
		}	
		
			if(MapPanel.map[e.getMapX()+2][e.getMapY()+4]>0 ||
					MapPanel.map[e.getMapX()+2][e.getMapY()+5]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+2][e.getMapY()+4]>0) ? 
	 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+4]:
		 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+5];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
		//pushes the character back to its desired position
					e.velocity=0;
					e.setOnGround(true);
					e.jumping=false;
					}
			}	
			
				if(MapPanel.map[e.getMapX()+3][e.getMapY()+4]>0 ||
						MapPanel.map[e.getMapX()+3][e.getMapY()+5]>0	){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+3][e.getMapY()+4]>0) ? 
		 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+4]:
			 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+5];
		 if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
		//pushes the character back to its desired position
				e.velocity=0;
				e.setOnGround(true);
				e.jumping=false;
				}
				}
				//end of lower units check'
				if(	MapPanel.map[e.getMapX()+2][e.getMapY()+5]==0 &&
						MapPanel.map[e.getMapX()+3][e.getMapY()+5]==0	
						&& MapPanel.map[e.getMapX()+1][e.getMapY()+5]==0){
					e.setOnGround(false);
	}
	}
	
	public void checkForLose(){
		if(mainCharacter.getMapY()>50){
			System.out.println("you lose!!!!!");
		   lost=true;
		}else{
		for(enemy e: CharacterPanel.enemies){
			if(e.enemyOnScreen() && mainCharacter.collisionWithEnemy(e)){
	int cWidth=(int)mainCharacter.recCollisionWithEnemy(e).getWidth();
	int cHeight=(int)mainCharacter.recCollisionWithEnemy(e).getHeight();
		if(cWidth>cHeight){
			e.HP-=1;
			System.out.println(e.characterName+" is injured!!!");
			mainCharacter.setScreeny(mainCharacter.getScreenY() - cHeight); 
		}else if(cWidth<=cHeight){
				System.out.println("tori is injured!!!!");
				mainCharacter.HP-=1;
				mainCharacter.injured=true;
				mainCharacter.changeStatus(5);//bird's status 5 is injured
				mainCharacter.hVelo=mainCharacter.myDirection()*10;
				Timer tempT = new Timer (1000, new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						mainCharacter.injured=false;
					}
				});
				tempT.setRepeats(false);
				tempT.start();
				mainCharacter.velocity=0;
		}
			}
		}
		if(mainCharacter.HP==0){
			System.out.println("you lose!!!");
			lost=true;
		}
		}
	}
	
	public void restartLevel(){
		mainCharacter.resetCharacter();
		mapPanel.resetMapPanel();
	    lost=false;
	}
	
		public void collisionDetectionForEnemy(enemy e){
			
			if(!e.onGround){
				e.fall();
			}	
			if(e.myDirection()==1){
			//start horizontal testing: Right
			if(MapPanel.map[e.getMapX()+4][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+4][e.getMapY()+3]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+4][e.getMapY()+1]>0) ? 
	 MapPanel.units[e.getMapX()+4-MapPanel.currmapMinX][e.getMapY()+1]:
		 MapPanel.units[e.getMapX()+4-MapPanel.currmapMinX][e.getMapY()+3];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
					if(e.hVelo<0)
					e.hVelo=0;
					}
			}else if(MapPanel.map[e.getMapX()+3][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+3][e.getMapY()+3]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+3][e.getMapY()+1]>0) ? 
	 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+1]:
		 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+3];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(e.hVelo<0)
					e.hVelo=0;
					}
			}else if(MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+2][e.getMapY()+2]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0) ? 
	 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+1]:
		 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+2];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(e.hVelo<0)
					e.hVelo=0;
					}
			}	else if(MapPanel.map[e.getMapX()+1][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+1][e.getMapY()+2]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+1][e.getMapY()+1]>0) ? 
	 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+1]:
		 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+2];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
	e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
		//pushes the character back to its desired position
				if(e.hVelo<0)
					e.hVelo=0;
					}
			}
			//end of horizontal testing
			}else{
				//start horizontal testing:Left
				if(MapPanel.map[e.getMapX()][e.getMapY()+1]>0 ||
						MapPanel.map[e.getMapX()][e.getMapY()+3]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()][e.getMapY()+1]>0) ? 
		 MapPanel.units[e.getMapX()-MapPanel.currmapMinX][e.getMapY()+1]:
			 MapPanel.units[e.getMapX()-MapPanel.currmapMinX][e.getMapY()+3];
					if(e.collisionWithUnit(unitToTest)){ //check the unit
		e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
			//pushes the character back to its desired position
					if(e.hVelo>0)
						e.hVelo=0;
						}
				}else if(MapPanel.map[e.getMapX()+1][e.getMapY()+1]>0 ||
						MapPanel.map[e.getMapX()+1][e.getMapY()+3]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+1][e.getMapY()+1]>0) ? 
		 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+1]:
			 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+3];
					if(e.collisionWithUnit(unitToTest)){ //check the unit
		e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
			//pushes the character back to its desired position
					if(e.hVelo>0)
						e.hVelo=0;
						}
				}else if(MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0 ||
						MapPanel.map[e.getMapX()+2][e.getMapY()+2]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0) ? 
		 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+1]:
			 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+2];
					if(e.collisionWithUnit(unitToTest)){ //check the unit
		e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
			//pushes the character back to its desired position
					if(e.hVelo>0)
						e.hVelo=0;
						}
				}	
				//end of horizontal testing
			}
	//		
	//		//start upper unit testing
	//		if(MapPanel.map[e.getMapX()+1][e.getMapY()]>0 ||
	//				MapPanel.map[e.getMapX()+1][e.getMapY()-1]>0	){
	//			//check the upper units
	//	Unit unitToTest2 = (MapPanel.map[e.getMapX()+1][e.getMapY()]>0) ? 
	// MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()]:
	//	 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()-1];
	//			if(e.collisionWithUnit(unitToTest2)){ //check the unit
	//	e.setScreeny(e.getScreenY() + (int)e.recCollisionWithUnit(unitToTest2).getHeight()); 
	//	//pushes the character back to its desired position
	//				e.velocity=0;
	//				}
	//		}
	//		if(MapPanel.map[e.getMapX()+2][e.getMapY()]>0 ||
	//				MapPanel.map[e.getMapX()+2][e.getMapY()-1]>0	){
	//			//check the upper units
	//	Unit unitToTest2 = (MapPanel.map[e.getMapX()+2][e.getMapY()]>0) ? 
	// MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()]:
	//	 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()-1];
	//			if(e.collisionWithUnit(unitToTest2)){ //check the unit
	//	e.setScreeny(e.getScreenY() + (int)e.recCollisionWithUnit(unitToTest2).getHeight()); 
	//	//pushes the character back to its desired position
	//				e.velocity=0;
	//				}
	//		}
	//		if(MapPanel.map[e.getMapX()+3][e.getMapY()]>0 ||
	//				MapPanel.map[e.getMapX()+3][e.getMapY()-1]>0	){
	//			//check the upper units
	//	Unit unitToTest2 = (MapPanel.map[e.getMapX()+3][e.getMapY()]>0) ? 
	// MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()]:
	//	 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()-1];
	//			if(e.collisionWithUnit(unitToTest2)){ //check the unit
	//	e.setScreeny(e.getScreenY() + (int)e.recCollisionWithUnit(unitToTest2).getHeight()); 
	//	//pushes the character back to its desired position
	//				e.velocity=0;
	//				}
	//	}
	//		//end upper unit testing
	//		
			//start ground collision test: 
			if(MapPanel.map[e.getMapX()+1][e.getMapY()+4]>0 ||
					MapPanel.map[e.getMapX()+1][e.getMapY()+5]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+1][e.getMapY()+4]>0) ? 
	 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+4]:
		 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+5];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
			e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
		//pushes the character back to its desired position
					e.velocity=0;
					e.setOnGround(true);
					e.changeStatus(0);
					}
			}	
			
				if(MapPanel.map[e.getMapX()+2][e.getMapY()+4]>0 ||
						MapPanel.map[e.getMapX()+2][e.getMapY()+5]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+2][e.getMapY()+4]>0) ? 
		 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+4]:
			 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+5];
					if(e.collisionWithUnit(unitToTest)){ //check the unit
			e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
			//pushes the character back to its desired position
						e.velocity=0;
						e.setOnGround(true);
						e.changeStatus(0);
						}
				}	
				
					if(MapPanel.map[e.getMapX()+3][e.getMapY()+4]>0 ||
							MapPanel.map[e.getMapX()+3][e.getMapY()+5]>0	){
						//if it's a terrain unit
				Unit unitToTest = (MapPanel.map[e.getMapX()+3][e.getMapY()+4]>0) ? 
			 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+4]:
				 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+5];
			 if(e.collisionWithUnit(unitToTest)){ //check the unit
			e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
			//pushes the character back to its desired position
					e.velocity=0;
					e.setOnGround(true);
					e.changeStatus(0);
					}
					}
					//end of lower units check'
					if(	MapPanel.map[e.getMapX()+2][e.getMapY()+5]==0 &&
							MapPanel.map[e.getMapX()+3][e.getMapY()+5]==0	){
						e.setOnGround(false);
		}
		}
}
