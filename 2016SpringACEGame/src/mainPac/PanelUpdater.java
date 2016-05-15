package mainPac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelUpdater{
	
	JLayeredPane jlp;
	
	public PanelUpdater(JLayeredPane JLP){
	    jlp=JLP;
		
	}
	
	public void paintPanels(){
		jlp.repaint();
	}

}
