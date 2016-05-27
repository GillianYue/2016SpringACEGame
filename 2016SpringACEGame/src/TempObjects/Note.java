package TempObjects;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import audio.BGMPlayer;
import character.Bird;
import character.CharacterPanel;
import enemies.enemy;
import mainPac.ImageLoader;

public class Note extends tempObject implements ActionListener{

	int count=0; Bird myBird; CharacterPanel characterPanel;
	public Note(int MX, int MY, ImageLoader il, int direction, Bird bird, CharacterPanel cp, BGMPlayer bgm) {
		super(MX, MY, il, direction);
		myImage=il.notes.getSubimage((int)(Math.random()/0.5)*10,(int)(Math.random()/0.5)*10, 10, 10);
		characterPanel = cp;
		myBird=bird;
		myT = new Timer (60, this);
		myT.start();
		bgm.playchirp();
	}

	public void updatePos(){
		mx+=Direction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updatePos();
		for(enemy E: CharacterPanel.enemies){
			if(collideEnemy(E)){
				if(E.injurable()){
				E.HP-=1;
				System.out.println(E.characterName+" is injured!!!");
				disappear();
				if(E.checkAlive()){
				E.moveScreen(characterPanel.mainCharacter.myDirection(), 2);
				}
				}
			}
		}
		count+=1;
		if(count>=6){
			disappear();
		}
	}
	
	@Override
	public void disappear(){
	myImage.flush();
	myT.stop();
	myBird.noteOn=false;
	}
	
	
}
