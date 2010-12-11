package controller;

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.*;
import utilities.*;

public class SpaceInvader extends JFrame implements WindowListener{

	 private static SIProps props = SIProps.getInstance();
	 private SpaceInvaderPanel mop;        // where the worm is drawn
	 private JTextField jtfTime;  // displays time spent in game

	  public SpaceInvader(long period)
	  { super("Space Invaders - Jason Garnett");
	    makeGUI(period);

	    addWindowListener( this );
	    pack();
	    setResizable(false);
	    setVisible(true);
	  } 


	  private void makeGUI(long period)
	  {
	    Container c = getContentPane();   

	    mop = new SpaceInvaderPanel(this, period);
	    c.add(mop, "Center");

	    JPanel ctrls = new JPanel();  
	    ctrls.setLayout( new BoxLayout(ctrls, BoxLayout.X_AXIS));

	    jtfTime = new JTextField("Time Spent: 0 secs");
	    jtfTime.setEditable(false);
	    ctrls.add(jtfTime);

	    c.add(ctrls, "South");
	  }  // end of makeGUI()

	  public void setTimeSpent(long t)
	  {  jtfTime.setText("Time Spent: " + t + " secs"); }
	  

	  // ----------------- window listener methods -------------

	  public void windowActivated(WindowEvent e) 
	  { mop.resumeGame();  }

	  public void windowDeactivated(WindowEvent e) 
	  {  mop.pauseGame();  }


	  public void windowDeiconified(WindowEvent e) 
	  {  mop.resumeGame();  }

	  public void windowIconified(WindowEvent e) 
	  {  mop.pauseGame(); }


	  public void windowClosing(WindowEvent e)
	  {  mop.stopGame();  }


	  public void windowClosed(WindowEvent e) {}
	  public void windowOpened(WindowEvent e) {}
	  

	  // ----------------------------------------------------

	  public static void main(String args[])
	  { 
	    int fps = Integer.parseInt(props.getProperty("DEFAULT_FPS")); // get default fps from the properties file

	    long period = (long) 1000.0/fps;
	    System.out.println("fps: " + fps + "; period: " + period + " ms");

	    new SpaceInvader(period*1000000L);    // ms --> nanosecs 
	  }
	
}
