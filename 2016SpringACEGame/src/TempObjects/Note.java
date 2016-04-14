package TempObjects;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import character.Bird;
import character.CharacterPanel;
import mainPac.ImageLoader;

public class Note extends tempObject implements ActionListener{

	int count=0; Bird myBird; CharacterPanel characterPanel;
	public Note(int MX, int MY, ImageLoader il, int direction, Bird bird, CharacterPanel cp) {
		super(MX, MY, il, direction);
		myImage=il.notes.getSubimage((int)(Math.random()/0.5)*10,(int)(Math.random()/0.5)*10, 10, 10);
		characterPanel = cp;
		myBird=bird;
		myT = new Timer (60, this);
		myT.start();
	}

	public void updatePos(){
		mx+=Direction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		updatePos();
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
