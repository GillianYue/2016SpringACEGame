package mainPac;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel implements MouseListener{
//TODO make keyListener work
	private static final long serialVersionUID = 1L;
	
	Background forest; MovingBackground mountains;
	private int pWidth, pHeight; 
	
	public BackgroundPanel(int width, int height){
		this.setBackground(Color.GRAY);
		forest= new Background ("Pics/sun.png", this);
		mountains = new MovingBackground ("Pics/mountains.png", 1.5 , this);
		pWidth=width; 
		pHeight=height;
		forest.setBackgroundSize(pWidth, pHeight, forest.getBackgroundImage());

		addMouseListener(this);
	}
	
	
	public void paintComponent(Graphics g){
		forest.drawBackground(g, 0, 0, null);
		mountains.updateMovingBackground(g);
		System.out.println("backgroundPanel trying to paint");

	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	
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
