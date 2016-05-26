package mainPac;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Spring;
import javax.swing.Timer;

import TempObjects.fruit;
import TempObjects.spring;
import TempObjects.tempObject;
import audio.BGMPlayer;
import background.BackgroundPanel;
import background.MovingBackground;
import character.Bird;
import character.CharacterPanel;
import character.Frog;
import enemies.enemy;
import map.MapPanel;
import map.Unit;

public class ControlPanel extends JPanel implements KeyListener, ActionListener{
	public static int CurrentLevel;
	Set<Integer> pressedKeyCode = new HashSet<Integer>();
	char pressedKeyChar;
	Bird bird; Frog frog; MovingBackground BG;
	int pwidth;
	Timer t;
	double gravity=9.8; 
	ImageLoader Il;
	boolean lost, inBossBattle;
	MapPanel mapPanel;
	public character.Character mainCharacter;
	XMLReader xml;
	BGMPlayer bgm;
	PanelUpdater panelDraw;
	/*
	 * in coordinates sync the character's status is constantly reset
	 */
	
	public ControlPanel(MapPanel mp, BackgroundPanel bp, CharacterPanel character, ImageLoader il,
			XMLReader XML, BGMPlayer BGMplayer, PanelUpdater pu){
		bird=character.bird;
		frog=character.frog;
		xml=XML;
		BG=bp.mountains;
		mapPanel=mp;
		Il=il;
		CurrentLevel=1;
		bgm=BGMplayer;
		panelDraw=pu;
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
			moveTheBird(pressedKeyCode);
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
					mainCharacter.faceRight();
					mainCharacter.rightJump();
					
			}else if(set.contains(KeyEvent.VK_LEFT)&&set.contains(KeyEvent.VK_SPACE)){
					mainCharacter.faceLeft();
					mainCharacter.leftJump();
				
			}else if(set.contains(KeyEvent.VK_SHIFT)&&set.contains(KeyEvent.VK_LEFT)){
					mainCharacter.faceLeft();
					mainCharacter.moveNStepLeft(2);
					mainCharacter.walking=true;
					
			}else if(set.contains(KeyEvent.VK_SHIFT) &&set.contains(KeyEvent.VK_RIGHT)){
					mainCharacter.faceRight();
					mainCharacter.moveNStepRight(2);
					mainCharacter.walking=true;

			}  if(set.contains(KeyEvent.VK_DOWN) && set.contains(KeyEvent.VK_A)){
				if(mainCharacter.equals(bird)){
				bird.squatAttack();
				bgm.playchirp();
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
			mainCharacter.faceRight();
			mainCharacter.moveNStepRight(1);
			mainCharacter.walking=true;
			
		}
		if(set.contains(KeyEvent.VK_LEFT)){
			mainCharacter.faceLeft();
			mainCharacter.moveNStepLeft(1);
			mainCharacter.walking=true;
 
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
			bgm.playchirp();
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
	
	public void moveTheBackground(){
			BG.moveBackground(-1);
	}
	
	public void updateMapInterval(){
			if(mainCharacter.getScreenX()>600){
				MapPanel.currmapMinX+=45;
				MapPanel.currmapMaxX+=45;
				mainCharacter.setScreenX(mainCharacter.getScreenX()-450);
			for(enemy e: CharacterPanel.enemies){
				e.setScreenX(e.getScreenX()-450);
			}
			
			}else
			if(mainCharacter.getScreenX()<100){
				MapPanel.currmapMinX-=45;
				MapPanel.currmapMaxX-=45;
				mainCharacter.setScreenX(mainCharacter.getScreenX()+450);
				for(enemy e: CharacterPanel.enemies){
					e.setScreenX(e.getScreenX()+450);
				}
			}
		}
		
	
		
	

	@Override
	public void actionPerformed(ActionEvent e) {
		mainCharacterCombo (mainCharacter);
		enemyCombo();
		if(!inBossBattle){
		updateMapInterval();
		moveTheBackground();
		checkForBoss();
		}else{
			if(bgm.currentClip!=1){//boss theme music
				bgm.changeTo(1);
			}
		}
		//bird.printMyStatus();
		//bird.printMyCoordinates();
		panelDraw.paintPanels();
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
			e.individualMoving();
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
		if(e.getScreenY()-(int)(e.velocity*0.3)>0){
		e.setScreeny(e.getScreenY()-(int)(e.velocity*0.3));
		}
		e.fall();
		e.setScreenX(e.getScreenX()-(int)(e.hVelo));
	if(e.hVelo!=0){
			if(e.hVelo>1){
				if(e.hVelo>5){
			 e.hVelo-=2;
				}else{
				e.hVelo-=1;
				}
			}else if(e.hVelo<-1){
				if(e.hVelo<-5){
			e.hVelo+=2;
				}else{
					e.hVelo+=1;
				}
			}else{
				e.hVelo=0;
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
			if(e.onDisplay() && mainCharacter.collisionWithEnemy(e)){
	int cWidth=(int)mainCharacter.recCollisionWithEnemy(e).getWidth();
	int cHeight=(int)mainCharacter.recCollisionWithEnemy(e).getHeight();
		if(cWidth>cHeight){
			if(e.injurable() && e.canJumpOnTop()){
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
			if(o.getClass()!=spring.class && o.getClass()!=fruit.class){
			mainCharacter.setScreeny(mainCharacter.getScreenY() - cHeight); 
			mainCharacter.velocity=0;
			mainCharacter.setOnGround(true);
			mainCharacter.jumping=false;
			}else if(o.getClass()==spring.class){
				mainCharacter.setScreeny(mainCharacter.getScreenY() - cHeight); 
				mainCharacter.velocity=100;
				o.changeStatus(1);
				Timer tempT = new Timer (1000, new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						o.changeStatus(0);
					}
				});
				tempT.setRepeats(false);
				tempT.start();
			}else if(o.getClass()==fruit.class){
				MapPanel.objGarbage.add(o);
				if(mainCharacter.HP<6){
				mainCharacter.HP +=1;
				}
				MapPanel.objData[o.getObjMX()][o.getObjMY()]=0;
			}
		}else if(cWidth<=cHeight){
			System.out.println("bumpin into obj!!!");
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
	    bgm.changeTo(0);
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
		
		public boolean checkForBoss(){
			if(mainCharacter.getMapX()>331){
				inBossBattle=true;
				for(int wall=30; wall<43; wall++){
				MapPanel.map[315][wall]=100;
				}
				for(int wall=30; wall<43; wall++){
					MapPanel.map[383][wall]=100;
					}
				return true;
			}else{
				return false;
			}
		}
}
