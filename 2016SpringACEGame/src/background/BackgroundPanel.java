package background;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import mainPac.ImageLoader;

public class BackgroundPanel extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	
	Background forest; public MovingBackground mountains;
	private int pWidth, pHeight; 
	
	public BackgroundPanel(int width, int height, ImageLoader il){
		this.setBackground(Color.CYAN);
		forest= new Background (this, il, il.sun);
		mountains = new MovingBackground (1.5 , this, il, il.mountains);
		pWidth=width; 
		pHeight=height;
		forest.setBackgroundSize(pWidth, pHeight, forest.getBackgroundImage());
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		forest.drawBackground(g, 0, 0, null);
		mountains.updateMovingBackground(g);
		
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
		System.out.println(e.getX()+" "+e.getY());
		
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
