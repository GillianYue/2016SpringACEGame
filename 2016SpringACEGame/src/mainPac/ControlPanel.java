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

import TempObjects.tempObject;
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
	public character.Character mainCharacter;
	XMLReader xml;
	/*
	 * in coordinates sync the character's status is constantly reset
	 */
	
	public ControlPanel(MapPanel mp, BackgroundPanel bp, CharacterPanel character, ImageLoader il,
			XMLReader XML){
		bird=character.bird;
		frog=character.frog;
		xml=XML;
		BG=bp.mountains;
		mapPanel=mp;
		Il=il;
		this.setFocusable(true);
		this.requestFocus();
		addKeyListener(this);
		mainCharacter=character.mainCharacter;
		pwidth=bp.getWidth();
		t = new Timer (50, this);
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
		mainCharacter.squat=false;
		}else if(e.getKeyCode()==KeyEvent.VK_A){
			if(mainCharacter.squat){
		mainCharacter.changeStatus(3);
			}else{
				mainCharacter.changeStatus(0);
			}
		}
		if(pressedKeyCode.contains(KeyEvent.VK_RIGHT) || 
				pressedKeyCode.contains(KeyEvent.VK_LEFT)){
		mainCharacter.walking=false;
		}
		
		pressedKeyCode.remove(e.getKeyCode());
	}

	
	public void moveTheBird(Set<Integer> set){
		if(set.size()>1){
			if(set.contains(KeyEvent.VK_RIGHT)&& set.contains(KeyEvent.VK_SPACE)){
				if(mainCharacter.getScreenX()<pwidth-50){
					mainCharacter.faceRight();
					if(mainCharacter.getScreenX() < 500){//if it's in the middle of the panel
					mainCharacter.rightJump();
					}else if((MapPanel.currmapMaxX+8> MapPanel.mapMaxX)
						&& (mainCharacter.getScreenX() >= 500)){
							mainCharacter.rightJump();
						}
					}
			
			}else if(set.contains(KeyEvent.VK_LEFT)&&set.contains(KeyEvent.VK_SPACE)){
				if(mainCharacter.getScreenX()>5){
					mainCharacter.faceLeft();
					if(mainCharacter.getScreenX()>200){//if it's in the middle of the panel
					mainCharacter.leftJump();
					}else if((MapPanel.currmapMinX -8< MapPanel.mapMinX)
							&& (mainCharacter.getScreenX() <= 200)){
						mainCharacter.leftJump();
					}
					}
			}else if(set.contains(KeyEvent.VK_SHIFT)&&set.contains(KeyEvent.VK_LEFT)){
				if(mainCharacter.getScreenX()>5){
					mainCharacter.faceLeft();
					if(mainCharacter.getScreenX()>200){//if it's in the middle of the panel
					mainCharacter.moveNStepLeft(2);
					mainCharacter.walking=true;
					}else if((MapPanel.currmapMinX == MapPanel.mapMinX)
							&& (mainCharacter.getScreenX() <= 200)){
						mainCharacter.moveNStepLeft(2);
						mainCharacter.walking=true;
					}
					}
			}else if(set.contains(KeyEvent.VK_SHIFT) &&set.contains(KeyEvent.VK_RIGHT)){
				if(mainCharacter.getScreenX()<pwidth-50){
					mainCharacter.faceRight();
					if(mainCharacter.getScreenX() < 500){//if it's in the middle of the panel
					mainCharacter.moveNStepRight(2);
					mainCharacter.walking=true;
					}else if((MapPanel.currmapMaxX == MapPanel.mapMaxX)
						&& (mainCharacter.getScreenX() >= 500)){
							mainCharacter.moveNStepRight(2);
							mainCharacter.walking=true;
						}
					}
			}  if(set.contains(KeyEvent.VK_DOWN) && set.contains(KeyEvent.VK_A)){
				if(mainCharacter.equals(bird)){
				bird.squatAttack();
				}
				Timer tempT = new Timer (1000, new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						mainCharacter.attacking=false;
					}
				});
				tempT.setRepeats(false);
				tempT.start();
			}
			
		}else{
		if(set.contains(KeyEvent.VK_RIGHT)){
			if(mainCharacter.getScreenX()<pwidth-50){
			mainCharacter.faceRight();
			if(mainCharacter.getScreenX() < 500){//if it's in the middle of the panel
			mainCharacter.moveNStepRight(1);
			mainCharacter.walking=true;
			}else if((MapPanel.currmapMaxX == MapPanel.mapMaxX)
				&& (mainCharacter.getScreenX() >= 500)){
					mainCharacter.moveNStepRight(1);
					mainCharacter.walking=true;
				}
			}
		}
		if(set.contains(KeyEvent.VK_LEFT)){
			if(mainCharacter.getScreenX()>5){
			mainCharacter.faceLeft();
			if(mainCharacter.getScreenX()>200){//if it's in the middle of the panel
			mainCharacter.moveNStepLeft(1);
			mainCharacter.walking=true;
			}else if((MapPanel.currmapMinX == MapPanel.mapMinX)
					&& (mainCharacter.getScreenX() <= 200)){
				mainCharacter.moveNStepLeft(1);
				mainCharacter.walking=true;
			}
			}
		}
		if(set.contains(KeyEvent.VK_DOWN)){
			if(mainCharacter.equals(bird)){
			bird.squat();
			}
		}
		if(set.contains(KeyEvent.VK_SPACE)){
			mainCharacter.jump();
		}
		if(set.contains(KeyEvent.VK_A)){
			mainCharacter.attack();
			Timer tempT = new Timer (1000, new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					mainCharacter.attacking=false;
				}
			});
			tempT.setRepeats(false);
			tempT.start();
		}
	}
	}
	
	public void moveTheBackground(Set<Integer> set){
//since this is the background, it moves the opposite way of the character's moving direction
		if(mainCharacter.hVelo==0){
		if(mainCharacter.onGround &&
				(set.contains(KeyEvent.VK_RIGHT)) && (MapPanel.currmapMaxX < MapPanel.mapMaxX)
				&& (mainCharacter.getScreenX() >= 500)){
			BG.moveBackground("left");
		}
		if(mainCharacter.onGround &&
				(set.contains(KeyEvent.VK_LEFT)) && (MapPanel.currmapMinX > MapPanel.mapMinX)
				&& (mainCharacter.getScreenX() <= 200)){
			BG.moveBackground("right");
		}
		}
	}
	
	public void updateMapInterval(Set<Integer> set){
		if(mainCharacter.hVelo==0){
		if(mainCharacter.onGround && (set.contains(KeyEvent.VK_RIGHT)) && (set.contains(KeyEvent.VK_SPACE)) &&
 (mainCharacter.getScreenX() >= 500)&& (MapPanel.currmapMaxX+8 <=MapPanel.mapMaxX) && 
 !mainCharacter.diagJumping){
			MapPanel.currmapMinX+=8;
			MapPanel.currmapMaxX+=8;
			mainCharacter.setScreenX(mainCharacter.getScreenX()-80);
			mainCharacter.rightJump();
			for(enemy e:CharacterPanel.enemies){
				e.setScreenX(e.getScreenX()-80);
			}
		}else if(mainCharacter.onGround && 
				(set.contains(KeyEvent.VK_LEFT)) && (set.contains(KeyEvent.VK_SPACE)) &&
	 (mainCharacter.getScreenX() <=200)	 && (MapPanel.currmapMinX-8 >=MapPanel.mapMinX) &&
	 !mainCharacter.diagJumping){
			mainCharacter.jump();
			MapPanel.currmapMinX-=8;
			MapPanel.currmapMaxX-=8;
			for(enemy e:CharacterPanel.enemies){
				e.setScreenX(e.getScreenX()+80);
			}
		}	else{ 
		}
			if(mainCharacter.onGround && 
					(set.contains(KeyEvent.VK_RIGHT)) && (MapPanel.currmapMaxX < MapPanel.mapMaxX)
				&& (mainCharacter.getScreenX() >= 500)){
			mainCharacter.walking=true;
			mainCharacter.rotateWalkingStatus();
			MapPanel.currmapMinX+=1;
			MapPanel.currmapMaxX+=1;
			for(enemy e:CharacterPanel.enemies){
				e.setScreenX(e.getScreenX()-10);
			}
		}
			
		if(mainCharacter.onGround && 
				(set.contains(KeyEvent.VK_LEFT)) && (MapPanel.currmapMinX > MapPanel.mapMinX)
				&& (mainCharacter.getScreenX() <= 200)){
			mainCharacter.walking=true;
			mainCharacter.rotateWalkingStatus();
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
		enemyCombo();
		//bird.printMyStatus();
		//bird.printMyCoordinates();
		
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
	
	public void enemyCombo(){
		for(enemy e: CharacterPanel.enemies){
			e.addGarbage();
		}
		if(CharacterPanel.enemiesGarbage.size()>0){
			for (enemy e:CharacterPanel.enemiesGarbage) {
				CharacterPanel.enemies.remove(e);
			}
			CharacterPanel.enemiesGarbage.clear();
			}
		for(enemy e: CharacterPanel.enemies){
			if(e.walking){
				e.startWalkingTimer();
			}else{
				e.stopWalkingTimer();
			}
			e.coordinatesSyncMapToScreen();
			if(e.gravityApplies()){
			collisionDetectionForEnemy(e);
			gravityForEnemies(e);
			}
			e.coordinatesSyncMapToScreen();
		}
	}
	
	public void gravityForEnemies(enemy e){
		if(e.onDisplay()){
		e.setScreeny(e.getScreenY()-(int)(e.velocity*0.3));
		e.setScreenX(e.getScreenX()-(int)(e.hVelo*0.3));
		if(e.hVelo!=0){
			if(e.hVelo>0){
				if(e.hVelo>5){
			 e.hVelo-=2;
				}else{
				e.hVelo-=1;
				}
			}else{
				if(e.hVelo<-5){
			e.hVelo+=2;
				}else{
					e.hVelo+=1;
				}
			}
		}
		}
	}
	
	public void gravityMainCharacter(character.Character e){
		e.setScreeny(e.getScreenY()-(int)(e.velocity*0.3));
		e.fall();
		e.setScreenX(e.getScreenX()-(int)(e.hVelo));
	if(e.hVelo!=0){
			if(e.hVelo>0){
				if(e.hVelo>5){
			 e.hVelo-=2;
				}else{
				e.hVelo-=1;
				}
			}else{
				if(e.hVelo<-5){
			e.hVelo+=2;
				}else{
					e.hVelo+=1;
				}
			}
		}
	}
	
	
	public void coordinatesSync (character.Character e){
		e.setMapX((e.getScreenX()/10)+MapPanel.currmapMinX);
		e.setMapY(e.getScreenY()/10);
		if(!e.walking && !e.squat && !e.jumping && !e.injured && !e.attacking && !e.diagJumping){
			e.changeStatus(0);
		}
	}
	
	public void collisionDetection(character.Character e){
	
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
				
				//start horizontal testing: Right
				if(MapPanel.map[e.getMapX()+4][e.getMapY()+1]>0 ||
						MapPanel.map[e.getMapX()+4][e.getMapY()+2]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+4][e.getMapY()+1]>0) ? 
		 MapPanel.units[e.getMapX()+4-MapPanel.currmapMinX][e.getMapY()+1]:
			 MapPanel.units[e.getMapX()+4-MapPanel.currmapMinX][e.getMapY()+2];
					if(e.collisionWithUnit(unitToTest)){
						//check the unit
						if(e.hVelo>0){
							e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
										}else if(e.hVelo<0){
							e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); }
			//pushes the character back to its desired position
						if(e.hVelo!=0)
						e.hVelo=0;
						}
				}else if(MapPanel.map[e.getMapX()+3][e.getMapY()+1]>0 ||
						MapPanel.map[e.getMapX()+3][e.getMapY()+2]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+3][e.getMapY()+1]>0) ? 
		 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+1]:
			 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+2];
					if(e.collisionWithUnit(unitToTest)){ 
					//check the unit
						if(e.hVelo>0){
							e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
							}else if(e.hVelo<0){
							e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); }
			//pushes the character back to its desired position
					if(e.hVelo!=0)
						e.hVelo=0;
						}
				}
				if(MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0 ||
						MapPanel.map[e.getMapX()+2][e.getMapY()+2]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+2][e.getMapY()+1]>0) ? 
		 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+1]:
			 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+2];
					if(e.collisionWithUnit(unitToTest)){ 
					//check the unit
						if(e.hVelo>0){
							e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 		
							}else if(e.hVelo<0){
							e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
		}
			//pushes the character back to its desired position
					if(e.hVelo!=0)
						e.hVelo=0;
						}
				}else if(MapPanel.map[e.getMapX()+1][e.getMapY()+1]>0 ||
						MapPanel.map[e.getMapX()+1][e.getMapY()+2]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+1][e.getMapY()+1]>0) ? 
		 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+1]:
			 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+2];
					if(e.collisionWithUnit(unitToTest)){ 
					//check the unit
						if(e.hVelo>0){
							e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 			
							}else if(e.hVelo<0){	
							e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
			}
			//pushes the character back to its desired position
					if(e.hVelo!=0)
						e.hVelo=0;
						}
				}	

					if(MapPanel.map[e.getMapX()][e.getMapY()+1]>0 ||
							MapPanel.map[e.getMapX()][e.getMapY()+2]>0){
						//if it's a terrain unit
				Unit unitToTest = (MapPanel.map[e.getMapX()][e.getMapY()+1]>0) ? 
			 MapPanel.units[e.getMapX()-MapPanel.currmapMinX][e.getMapY()+1]:
				 MapPanel.units[e.getMapX()-MapPanel.currmapMinX][e.getMapY()+2];
						if(e.collisionWithUnit(unitToTest)){
					//check the unit
						if(e.hVelo>0){
			e.setScreenX(e.getScreenX() + (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
			}else if(e.hVelo<0){
			e.setScreenX(e.getScreenX() - (int)e.recCollisionWithUnit(unitToTest).getWidth()); 
	
			}
				//pushes the character back to its desired position
						if(e.hVelo!=0)
							e.hVelo=0;
							}
					}
					//end of horizontal testing
				
				if(	MapPanel.map[e.getMapX()+2][e.getMapY()+5]==0 &&
						MapPanel.map[e.getMapX()+3][e.getMapY()+5]==0	
						&& MapPanel.map[e.getMapX()+1][e.getMapY()+5]==0 &&
						MapPanel.map[e.getMapX()+2][e.getMapY()+4]==0){
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
			if(e.injurable()){
			e.HP-=1;
			System.out.println(e.characterName+" is injured!!!");
			}
			mainCharacter.setScreeny(mainCharacter.getScreenY() - cHeight); 
			mainCharacter.velocity=40;
			
		}else if(cWidth<=cHeight){
				if(e.damageColliding() && !mainCharacter.injured){
				mainCharacter.hVelo=mainCharacter.myDirection()*10;
				System.out.println("tori is injured!!!!");
				mainCharacter.HP-=1;
				mainCharacter.injured=true;
				mainCharacter.changeStatus(5);//bird's status 5 is injured
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
		}//end check collision with enemy
		for(tempObject o: MapPanel.objects){
			if( mainCharacter.collisionWithObject(o)){
	int cWidth=(int)mainCharacter.recCollisionWithObj(o).getWidth();
	int cHeight=(int)mainCharacter.recCollisionWithObj(o).getHeight();
		if(cWidth>cHeight){
			mainCharacter.setScreeny(mainCharacter.getScreenY() - cHeight); 
			mainCharacter.velocity=0;
			mainCharacter.setOnGround(true);
			mainCharacter.jumping=false;
		}else if(cWidth<=cHeight){
			if(mainCharacter.hVelo>0){
	mainCharacter.setScreenX(mainCharacter.getScreenX() + cWidth);
	}else if(mainCharacter.hVelo<0){
	mainCharacter.setScreenX(mainCharacter.getScreenX() - cWidth);
				}
			mainCharacter.hVelo=0;
		}
			}
		}//end check collision with objects
		if(mainCharacter.HP==0){
			System.out.println("you lose!!!");
			lost=true;
		}
		}
	}
	
	public void restartLevel(){
		mainCharacter.HP=mainCharacter.maxHP;
		mainCharacter.resetCharacter();
		mapPanel.resetMapPanel();
	    lost=false;
	    CharacterPanel.enemies.clear();
	    MapPanel.objects.clear();
	    xml.loadLevel1();
	}
	
		public void collisionDetectionForEnemy(enemy e){
		try{
			if(!e.onGround){
				e.fall();
			}	
			int extraX = (int)(e.picWidth/10); int extraY = (int)(e.picHeight/10);
			if(e.myDirection()==1){
			//start horizontal testing: Right
			if(MapPanel.map[e.getMapX()+extraX][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+extraX][e.getMapY()+3]>0){
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
			}else if(MapPanel.map[e.getMapX()+extraX-1][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+extraX-1][e.getMapY()+3]>0){
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
			}else if(MapPanel.map[e.getMapX()+extraX-2][e.getMapY()+1]>0 ||
					MapPanel.map[e.getMapX()+extraX-2][e.getMapY()+2]>0){
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
			//start ground collision test: 
			if(MapPanel.map[e.getMapX()+1][e.getMapY()+extraY]>0 ||
					MapPanel.map[e.getMapX()+1][e.getMapY()+extraY+1]>0){
				//if it's a terrain unit
		Unit unitToTest = (MapPanel.map[e.getMapX()+1][e.getMapY()+extraY]>0) ? 
	 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+extraY]:
		 MapPanel.units[e.getMapX()+1-MapPanel.currmapMinX][e.getMapY()+extraY+1];
				if(e.collisionWithUnit(unitToTest)){ //check the unit
			e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
		//pushes the character back to its desired position
					e.velocity=0;
					e.setOnGround(true);
					e.changeStatus(0);
					}
			}	
			
				if(MapPanel.map[e.getMapX()+2][e.getMapY()+extraY]>0 ||
						MapPanel.map[e.getMapX()+2][e.getMapY()+extraY+1]>0){
					//if it's a terrain unit
			Unit unitToTest = (MapPanel.map[e.getMapX()+2][e.getMapY()+extraY]>0) ? 
		 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+extraY]:
			 MapPanel.units[e.getMapX()+2-MapPanel.currmapMinX][e.getMapY()+extraY+1];
					if(e.collisionWithUnit(unitToTest)){ //check the unit
			e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
			//pushes the character back to its desired position
						e.velocity=0;
						e.setOnGround(true);
						e.changeStatus(0);
						}
				}	
				
					if(MapPanel.map[e.getMapX()+3][e.getMapY()+extraY]>0 ||
							MapPanel.map[e.getMapX()+3][e.getMapY()+extraY+1]>0	){
						//if it's a terrain unit
				Unit unitToTest = (MapPanel.map[e.getMapX()+3][e.getMapY()+extraY]>0) ? 
			 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+extraY]:
				 MapPanel.units[e.getMapX()+3-MapPanel.currmapMinX][e.getMapY()+extraY+1];
			 if(e.collisionWithUnit(unitToTest)){ //check the unit
			e.setScreeny(e.getScreenY() - (int)e.recCollisionWithUnit(unitToTest).getHeight()); 
			//pushes the character back to its desired position
					e.velocity=0;
					e.setOnGround(true);
					e.changeStatus(0);
					}
					}
					//end of lower units check'
					//start check collision with objects
					for(tempObject o: MapPanel.objects){
						if(e.collisionWithObject(o)){
				int cWidth=(int)e.recCollisionWithObj(o).getWidth();
				int cHeight=(int)e.recCollisionWithObj(o).getHeight();
					if(cWidth>cHeight){
						e.setScreeny(e.getScreenY() - cHeight); 
						e.velocity=0;
						e.setOnGround(true);
						e.jumping=false;
					}else if(cWidth<=cHeight){
						if(e.myDirection()==-1 && e.getMapX()>=o.getObjMX()){
						e.setScreenX(e.getScreenX() - e.myDirection()*cWidth);
						}else if(e.myDirection()==1 && e.getMapX()<=o.getObjMX()){
						e.setScreenX(e.getScreenX() - e.myDirection()*cWidth);
						}
					}
						}
					}//end check collision with objects
					if(	MapPanel.map[e.getMapX()+2][e.getMapY()+extraY+1]==0 &&
							MapPanel.map[e.getMapX()+3][e.getMapY()+extraY+1]==0	
							&& MapPanel.map[e.getMapX()+1][e.getMapY()+extraY+1]==0){
						e.setOnGround(false);
		}
		}catch(Exception p){
		}
		}
}
