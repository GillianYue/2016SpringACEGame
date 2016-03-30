package mainPac;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel implements KeyListener, MouseListener{
//TODO make keyListener work
	private static final long serialVersionUID = 1L;
	Background forest; MovingBackground mountains;
	int pWidth, pHeight;
	
	public BackgroundPanel(int width, int height){
		forest= new Background ("Pics/sun.png", this);
		mountains = new MovingBackground ("Pics/mountains.png", 1.5 , this);
		pWidth=width; 
		pHeight=height;
		addKeyListener(this);
		addMouseListener(this);
	}
	
	
	public void paintComponent(Graphics g){
		forest.setBackgroundSize(pWidth, pHeight, forest.getBackgroundImage());
		forest.drawBackground(g, 0, 0, null);
		mountains.updateMovingBackground(g);
		this.setBackground(Color.GRAY);
		System.out.println("trying to repaint");
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("hey key typed");
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("hey key pressed");
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyChar()+"hey key released");
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mountains.pic1Loc -= 20 * mountains.getMovingSpeed();
		mountains.pic2Loc -= 20 * mountains.getMovingSpeed();
		if(mountains.PicWidth*-1 - mountains.pic1Loc >=0){
			mountains.pic1Loc = mountains.PicWidth - (mountains.PicWidth*-1 - mountains.pic1Loc);
		}
		if(mountains.PicWidth*-1 - mountains.pic2Loc >=0){
			mountains.pic2Loc = mountains.PicWidth - (mountains.PicWidth*-1 - mountains.pic2Loc);
		}
	
		repaint();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
