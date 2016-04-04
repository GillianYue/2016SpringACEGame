package mainPac;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import background.BackgroundPanel;
import background.MovingBackground;
import character.Bird;
import character.CharacterPanel;
import map.MapPanel;

public class ControlPanel extends JPanel implements KeyListener{
	int pressedKeyCode;
	Bird bird; MovingBackground BG;
	int pwidth;
	
	public ControlPanel(BackgroundPanel backgroundP, CharacterPanel character){
		bird=character.bird;
		BG=backgroundP.mountains;
		this.setFocusable(true);
		addKeyListener(this);
		pwidth=backgroundP.getWidth();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeyCode=e.getKeyCode();
		if(pressedKeyCode == KeyEvent.VK_RIGHT || pressedKeyCode == KeyEvent.VK_LEFT){
			updateMapInterval(pressedKeyCode);
			moveTheBird(pressedKeyCode);
			moveTheBackground(pressedKeyCode);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
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
			MapPanel.currmapMinX+=1;
			MapPanel.currmapMaxX+=1;
		}
		if((KeyCode == KeyEvent.VK_LEFT) && (MapPanel.currmapMinX > MapPanel.mapMinX)
				&& (bird.getScreenX() <= 200)){
			MapPanel.currmapMinX-=1;
			MapPanel.currmapMaxX-=1;
		}
	}
}
