package TempObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import mainPac.ImageLoader;
import map.MapPanel;

public class tempObject {

	protected int mx, my;
	protected int Direction;
	protected BufferedImage myImage;
	private int secondsILast;
	
	Timer myT;
	
	public tempObject(int MX, int MY, ImageLoader il, int direction){
		Direction=direction;
		mx=MX;
		my=MY;
	}
	
	public void disappear(){
		myImage.flush();
		myT.stop();
	}
	
	public void paintObject(Graphics g){
		g.drawImage(myImage, 10*(mx-MapPanel.currmapMinX), 10*my, null);
	}
}
