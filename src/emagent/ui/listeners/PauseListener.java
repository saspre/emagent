package emagent.ui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PauseListener implements ActionListener {
	private boolean isPaused = false;
	
	public boolean isPaused() {
		return isPaused;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		isPaused = !isPaused;
		JButton btn = (JButton) arg0.getSource();
		if(isPaused)
		{
			btn.setText("Resume");
		} else {
			btn.setText("Pause");
		}
		
	}

}
