package enemies;

import character.CharacterPanel;
import mainPac.ImageLoader;

public class bug extends enemy{

	public bug(int initialmapX, int initialmapY, ImageLoader il, CharacterPanel cp, int facing) {
		super(initialmapX, initialmapY, il, cp, facing);
		HP=1;
		characterName="bug";
		picWidth=20;
		picHeight=20;
		myImage=il.bugs.getSubimage((int)(Math.random()/0.51)*20
				,(int)(Math.random()/0.51)*20, 20, 20);
		individualWidth=20;
		individualHeight=20;
	}

	@Override
	public void individualMoving(){
		if(display){
		if(CharacterPanel.mainCharacter.getScreenX()>x){
			x+=1;
			facingDirection=1;
		}else{
			x-=1;
			facingDirection=-1;
		}
		if(CharacterPanel.mainCharacter.getScreenY()>y){
			y+=1;
		}else{
			y-=1;
		}
		}
	}
	
	public boolean gravityApplies(){
		return false;	
		}
	
	public boolean canJumpOnTop(){
		return false;
	}
	
}
