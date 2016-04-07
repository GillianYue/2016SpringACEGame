package mainPac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import background.BackgroundPanel;
import background.MovingBackground;
import character.Bird;
import character.CharacterPanel;
import map.MapPanel;
import map.Unit;

public class ControlPanel extends JPanel implements KeyListener, ActionListener{
	int pressedKeyCode;
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
		t = new Timer (60, this);
		t.start();
		}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeyCode=e.getKeyCode();
			updateMapInterval(pressedKeyCode);
			moveTheBird(pressedKeyCode);
			moveTheBackground(pressedKeyCode);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		bird.returnToOriginalStatus();
	}

	
	public void moveTheBird(int KeyCode){
		if(KeyCode == KeyEvent.VK_RIGHT){
			if(bird.getScreenX()<pwidth-50){
			bird.faceRight();
			if(bird.getScreenX() < 500){//if it's in the middle of the panel
			bird.moveOneStepRight();
			}else if((MapPanel.currmapMaxX == MapPanel.mapMaxX)
				&& (bird.getScreenX() >= 500)){
					bird.moveOneStepRight();
				}
			}
		}
		if(KeyCode == KeyEvent.VK_LEFT){
			if(bird.getScreenX()>5){
			bird.faceLeft();
			if(bird.getScreenX()>200){//if it's in the middle of the panel
			bird.moveOneStepLeft();
			}else if((MapPanel.currmapMinX == MapPanel.mapMinX)
					&& (bird.getScreenX() <= 200)){
				bird.moveOneStepLeft();
			}
			}
		}
		if(KeyCode == KeyEvent.VK_SPACE){
			//TODO figure out how pressing two key works
			bird.jump();
		}
	}
	
	public void moveTheBackground(int KeyCode){
//since this is the background, it moves the opposite way of the character's moving direction
		if((KeyCode == KeyEvent.VK_RIGHT) && (MapPanel.currmapMaxX < MapPanel.mapMaxX)
				&& (bird.getScreenX() >= 500)){
			BG.moveBackground("left");
		}
		if((KeyCode == KeyEvent.VK_LEFT) && (MapPanel.currmapMinX > MapPanel.mapMinX)
				&& (bird.getScreenX() <= 200)){
			BG.moveBackground("right");
		}
	}
	
	public void updateMapInterval(int KeyCode){
		if((KeyCode == KeyEvent.VK_RIGHT) && (MapPanel.currmapMaxX < MapPanel.mapMaxX)
				&& (bird.getScreenX() >= 500)){
			bird.rotateWalkingStatus();
			MapPanel.currmapMinX+=1;
			MapPanel.currmapMaxX+=1;
			bird.myMapX+=1;
		}
		if((KeyCode == KeyEvent.VK_LEFT) && (MapPanel.currmapMinX > MapPanel.mapMinX)
				&& (bird.getScreenX() <= 200)){
			bird.rotateWalkingStatus();
			MapPanel.currmapMinX-=1;
			MapPanel.currmapMaxX-=1;
			bird.myMapX-=1;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//bird.myMapY -= (int)((bird.velocity*0.5)/10);
	
		bird.y -= (int)(bird.velocity*0.5);
		bird.myMapY = bird.y /10 ;
		
		if(bird.falling){
			bird.fall();
		}
		checkForLose();
		if(lost){
			restartLevel();
			lost=false;
		}
		if(MapPanel.map[bird.myMapX+1][bird.myMapY+4]>0 ||
				MapPanel.map[bird.myMapX+2][bird.myMapY+4]>0){
		if(bird.checkCollision(new Unit(bird.x+10, bird.y+40, 1, Il, bird.myMapX+1, bird.myMapY+4)) ||
			bird.checkCollision(new Unit(bird.x+20, bird.y+40, 1, Il, bird.myMapX+2, bird.myMapY+4))){
			//the units used to checkCollision here are Imaginary Units...
			bird.velocity=0;
			bird.changeStatus(0);
	
		}
	
	}
		//bird.printMyCoordinates();
}
	
	public void checkForLose(){
		if(bird.myMapY>55){
			System.out.println("you lose!!!!!");
			lost=true;
		}
	}
	
	public void restartLevel(){
		bird.resetCharacter();
		mapPanel.resetMapPanel();
	}
}
