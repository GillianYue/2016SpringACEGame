package mainPac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelUpdater implements ActionListener{
	Timer t;
	JPanel [] allPanels;
	JPanel  temp; 
	JLayeredPane jlp;
	
	public PanelUpdater(JLayeredPane JLP){
	    jlp=JLP;
		t = new Timer (50, this);
		t.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		jlp.repaint();
	}

}
