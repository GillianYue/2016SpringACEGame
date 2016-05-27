package enemies;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import TempObjects.fruit;
import TempObjects.leaf;
import character.CharacterPanel;
import mainPac.ImageLoader;
import map.MapPanel;

public class treeBoss extends lastBoss{
	
	leaf[] rainbowLeaf;
	int leafOnAttack;
	int attackingTeam, lastTeam;
	boolean attack;
	ImageLoader Il;
	protected boolean[] fruitSpotTaken;
	
	public treeBoss(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp, facing);
		myImage=il.treeBoss;
		characterName="RAINBOW";
		picWidth=80;
		picHeight=80;
		Il=il;
		HP=10;
		fruitSpotTaken = new boolean[11];
		for(int a=0; a<11; a++){
			fruitSpotTaken[a]=false;
			System.out.println(fruitSpotTaken[0]);
		}
		rainbowLeaf = new leaf[30];
		for(int a=0; a<10; a++){
	rainbowLeaf[a]=new leaf((initialmapX-MapPanel.currmapMinX)*10+(int)(Math.random()*30),
			initialmapY*10+(int)(Math.random()*30)
			,il, 1,  this, a);
		}
		for(int a=10; a<20; a++){
	rainbowLeaf[a]=new leaf((initialmapX-MapPanel.currmapMinX)*10+40+(int)(Math.random()*30),
			initialmapY*10+(int)(Math.random()*30)
			,il, -1, this, a);
		}
		for(int a=20; a<25; a++){
			rainbowLeaf[a]=new leaf((initialmapX-MapPanel.currmapMinX)*10+(int)(Math.random()*40),
					initialmapY*10-(int)(Math.random()*10)
					,il, 1, this, a);
		}
		for(int a=25; a<30; a++){
			rainbowLeaf[a]=new leaf((initialmapX-MapPanel.currmapMinX)*10+40+(int)(Math.random()*40),
					initialmapY*10-(int)(Math.random()*10)
					,il, -1, this, a);
		}
		lastTeam=0;
		attackingTeam=5;
		attack=true;
		System.out.println(fruitSpotTaken[0]+"hmph");
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
	
	@Override
	public void onslaught(){
		Timer leafRegenerator = new Timer(1000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean nextAssault=true;
				for(int a=lastTeam; a<attackingTeam; a++){
					if(rainbowLeaf[a]!=null){
					 nextAssault=false;
					 break;
					}
				}
				if(nextAssault){
					attack=false;
					lastTeam=attackingTeam;
					if(attackingTeam+5<=30){
					attackingTeam+=5;
					}else{
						HP=0;
					}
					Timer atk = new Timer(5000, new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
						attack=true;
						}
					});
					atk.setRepeats(false);
					atk.start();
				}
				
				if(attack){
				if(rainbowLeaf[leafOnAttack]!=null && !rainbowLeaf[leafOnAttack].functional)
				rainbowLeaf[leafOnAttack].trackMainCharacter();
				if(leafOnAttack<attackingTeam-1){
				leafOnAttack++;
				}else{
					
				}
				}
			
				if(Math.random()<0.1){
					int posNum = (int)(Math.random()/0.1);
					generateFruitatPosition(posNum);
				}
			}
		});
		leafRegenerator.start();
	}
	
	public void generateFruitatPosition(int position){
		if(!fruitSpotTaken[position]){
		MapPanel.objects.add(new fruit(317+5*position, Math.abs(position-5)*5-10, Il, 1));
		fruitSpotTaken[position]=true;
		}
	}
	
	public void killOneLeaf(int leafNum){
		if(rainbowLeaf[leafNum]!=null){
		rainbowLeaf[leafNum].functional=false;
		rainbowLeaf[leafNum]=null;
		}
	}
	
	
	@Override
	public void killAllLeaves(){
		for(int a=0; a<rainbowLeaf.length; a++){
			killOneLeaf(a);
			System.out.println("leaves killed");
		}
	}
	
}
