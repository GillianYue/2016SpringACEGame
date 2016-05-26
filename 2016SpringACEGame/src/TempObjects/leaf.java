package TempObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

import enemies.treeBoss;
import mainPac.ImageLoader;
import map.MapPanel;

public class leaf extends tempObject{
	protected int myColorNum;
	protected double myDirection; 
	//myDirection: difference between the angle of the leaf and the horizontal line
	treeBoss myTree;
	protected int sX, sY;
	
	public leaf(int X, int Y, ImageLoader il, int facing, treeBoss myT) {
		super(X/10, Y/10, il, facing);
		myTree = myT;
		sX = X;
		sY = Y;
		myColorNum = (int)(Math.random()/0.126);
		myImage=il.leaf.getSubimage(myColorNum*10, 0, 10, 10);
	}

	public void paintObject(Graphics g){
		if(Direction==1){
			g.drawImage(myImage, sX, sY, 
					sX+10,
					sY+10, 0, 0, 10, 10, null);
			}else if(Direction==-1){
				g.drawImage(myImage, sX+10, sY, 
						sX,
						sY+10, 0, 0, 10, 10, null);
			}
	}
	
	public void trackMainCharacter(){
		
	}
}
