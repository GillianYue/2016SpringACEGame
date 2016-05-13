package enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import character.CharacterPanel;
import mainPac.ImageLoader;

public class treeMonster extends enemy{

	public treeMonster(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp, facing);
		myImage=il.treeMonster.getSubimage(
				0,(int)(Math.random()/0.34)*60, 90, 60
				);
		HP=4;
		characterName="tree";
		picWidth=45;
		picHeight=60;
		individualWidth=30;
		individualHeight=50;
		numOfWalkingStatus=2;
	}

	
	public void hVeloRandomizer(){
		if(hVelo==0){
		double random=Math.random();
		if(random<0.1){
			facingDirection=1;
		hVelo=8;
		walking=true;
		}else if(random>0.9){
			facingDirection=-1;
			hVelo=-8;
			walking=true;
		}else{
			walking=false;
		}
		}
	}
	
	@Override
	public Rectangle getMyBounds(){
		return new Rectangle(x+7, y+10, individualWidth, individualHeight);
	}
	
	@Override
	public void drawCharacter(Graphics g){
		if(checkAlive() && display){
			hVeloRandomizer();
		if(facingDirection==1){
		g.drawImage(myImage, x, y, x+picWidth,
				y+picHeight, (myStatus%3)*picWidth, (myStatus/3)*picHeight,
				(myStatus%3)*picWidth+picWidth, (myStatus/3)*picHeight+picHeight, null);
		}else if(facingDirection==-1){
			g.drawImage(myImage, x+picWidth, y, x,
					y+picHeight, (myStatus%3)*picWidth, (myStatus/3)*picHeight,
					(myStatus%3)*picWidth+picWidth, (myStatus/3)*picHeight+picHeight, null);
		}
		}
	}
}
