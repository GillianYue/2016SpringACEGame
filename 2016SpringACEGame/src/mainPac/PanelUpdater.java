package mainPac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelUpdater implements ActionListener{
	Timer t;
	JPanel [] allPanels;
	JPanel  tempCharacter; 
	public PanelUpdater(JPanel character){
	    tempCharacter = character;
		t = new Timer (80, this);
		t.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		tempCharacter.repaint();//somehow...this updates both of the panels...
	}

}
