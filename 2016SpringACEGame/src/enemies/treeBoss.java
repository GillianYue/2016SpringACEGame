package enemies;

import java.awt.Graphics;

import TempObjects.leaf;
import character.CharacterPanel;
import mainPac.ImageLoader;
import map.MapPanel;

public class treeBoss extends lastBoss{
	leaf[] rainbowLeaf;
	public treeBoss(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp, facing);
		myImage=il.treeBoss;
		characterName="RAINBOW";
		picWidth=80;
		picHeight=80;
		HP=10;
		rainbowLeaf = new leaf[30];
		for(int a=0; a<10; a++){
	rainbowLeaf[a]=new leaf((initialmapX-MapPanel.currmapMinX)*10+(int)(Math.random()*30),
			initialmapY*10+(int)(Math.random()*30)
			,il, 1,  this);
		}
		for(int a=10; a<20; a++){
	rainbowLeaf[a]=new leaf((initialmapX-MapPanel.currmapMinX)*10+40+(int)(Math.random()*30),
			initialmapY*10+(int)(Math.random()*30)
			,il, -1, this);
		}
		for(int a=20; a<25; a++){
			rainbowLeaf[a]=new leaf((initialmapX-MapPanel.currmapMinX)*10+(int)(Math.random()*40),
					initialmapY*10-(int)(Math.random()*10)
					,il, 1, this);
		}
		for(int a=25; a<30; a++){
			rainbowLeaf[a]=new leaf((initialmapX-MapPanel.currmapMinX)*10+40+(int)(Math.random()*40),
					initialmapY*10-(int)(Math.random()*10)
					,il, -1, this);
		}
	}

	@Override
	public void drawCharacter(Graphics g){
		if(checkAlive() && display){
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
		for(leaf l: rainbowLeaf){
			if(l!=null)
			l.paintObject(g);
		}
		
	}
}
