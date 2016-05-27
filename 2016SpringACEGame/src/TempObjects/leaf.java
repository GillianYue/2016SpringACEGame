package TempObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

import javax.swing.Timer;

import character.Bird;
import character.CharacterPanel;
import enemies.treeBoss;
import mainPac.ImageLoader;
import map.MapPanel;

public class leaf extends tempObject{
	protected int myColorNum, num;
	protected double myDirection; 
	//myDirection: difference between the angle of the leaf and the horizontal line
	treeBoss myTree;
	protected int sX, sY;
	public boolean functional;
	
	public leaf(int X, int Y, ImageLoader il, int facing, treeBoss myT, int myNum) {
		super(X/10, Y/10, il, facing);
		myTree = myT;
		sX = X;
		sY = Y;
		num=myNum;
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
	
	public Rectangle getMyBounds(){
		return new Rectangle(sX, sY, 10, 10);
	}
	
	public void trackMainCharacter(){
		functional=true;
		Timer mover = new Timer(100, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(functional){
				if(CharacterPanel.mainCharacter.getScreenX()>sX){
					sX+=2;
					Direction=1;
				}else{
					sX-=2;
					Direction=-1;
				}
				if(CharacterPanel.mainCharacter.getScreenY()>sY){
					sY+=1;
				}else{
					sY-=1;
				}
				if(getMyBounds().intersects(CharacterPanel.mainCharacter.getNoteBounds())){
					myTree.killOneLeaf(num);
					CharacterPanel.mainCharacter.killNote();
					
				}
				
				if(getMyBounds().intersects(CharacterPanel.mainCharacter.getMyBounds())){
					if(!CharacterPanel.mainCharacter.injured){
					CharacterPanel.mainCharacter.getHurt();
					CharacterPanel.mainCharacter.changeStatus(5);
					}
				}
				}
			}
		});
		mover.start();
	}
	
}
