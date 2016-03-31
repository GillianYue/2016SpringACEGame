package mainPac;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class ControlPanel extends JPanel implements KeyListener{
	int pressedKeyCode;
	boolean isPressed;
	Bird bird; MovingBackground BG;
	
	public ControlPanel(BackgroundPanel background, CharacterPanel character){
		bird=character.bird;
		BG=background.mountains;
		this.setFocusable(true);
		addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		isPressed=true;
		pressedKeyCode=e.getKeyCode();
		moveTheBird();
		moveTheBackground();
		System.out.println("controlPanel: keypressed!");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isPressed=false;
		System.out.println("controlPanel: keyreleased!");
	}

	public boolean keyIsPressed(){
		return isPressed;
	}
	
	public int getCurrentKeyCode(){
		if (keyIsPressed()){
		return pressedKeyCode;
		}else{
			return -1000;
		}
	}
	
	public void moveTheBird(){
		if(getCurrentKeyCode() == KeyEvent.VK_RIGHT){
			bird.faceRight();
		}
		if(getCurrentKeyCode() == KeyEvent.VK_LEFT){
			bird.faceLeft();
		}
	}
	
	public void moveTheBackground(){
		if(getCurrentKeyCode() == KeyEvent.VK_RIGHT){
			BG.moveBackground("left");
		}
		if(getCurrentKeyCode() == KeyEvent.VK_LEFT){
			BG.moveBackground("right");
		}
	}
}
