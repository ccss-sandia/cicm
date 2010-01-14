package gui.cmt;
//import adt.cmt.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

//import javax.swing.text.*;

//import adt.*;
import adt.ahp.Hierarchy;
import adt.cmt.CMTSystem;
import adt.cmt.Level;
import adt.cmt.PerformanceLevel;
import gui.examples.*;

/**
 * <code>CMTSystemHealthPanel</code> is the panel that displays
 * the overall system health.
 * @author  lc
 * @version February 2008
 */

public class CMTSystemHealthPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector systemHealthLevels;
	private Vector performanceLevels;
	private int[][] healthLookup;
	
	private CMTColorBar cb;
	private JLabel lbl, lblLostRev;
	private JTextPane tp;
		
	private int category = 0;
	//private double performanceIndex = 0;
	//private double threatLevel = 0;
	
	//private CMTSystem s;
	private String type;
	private String title;
	/**
	 * <code>CMTSystemHealthPanel</code> is the panel that will hold the overall
	 * system status.
	 * 
	 * @param healthLookup          int[performanceLevel][threatLevel] lookup table for overall health 
	 * @param systemHealthLevels	Vector containing the different system health levels
	 * @param performanceLevels     Vector containing the different performance levels
	 */
	 CMTSystemHealthPanel (int[][] healthLookup, Vector shLvls, Vector performanceLevels, CMTSystem s, String type) {
		this.setLayout(new BorderLayout());
		this.type = type;
		this.systemHealthLevels = shLvls;
		this.performanceLevels = performanceLevels;
		this.healthLookup = healthLookup;
		//this.s = s;
		
		// add the color status
		cb = new CMTColorBar();
	    this.add(cb, BorderLayout.WEST);
	    
	    JPanel p = new JPanel(new BorderLayout());
	    
	    // add the performance index --- for testing
	    if (type == "System") {
	    	title = "Total Cost of Outage:";
	    }
	    else {
	    	title = "Total Cost of Component Loss:";
	    }
		lbl = new JLabel(type + " Status:");
		p.add(lbl, BorderLayout.WEST);
		
		// add the status description
		tp = new JTextPane();
		tp.setEditable(false);
		tp.setPreferredSize(new Dimension(300,30));
		tp.setMinimumSize(new Dimension(10,10));
		tp.setAutoscrolls(true);
		tp.setBackground(this.getBackground());
		this.add(tp, BorderLayout.CENTER);	
		
		lblLostRev = new JLabel(title);
		p.add(lblLostRev, BorderLayout.EAST);
		
		this.add(p, BorderLayout.NORTH);

	}

	 
	private int lookupHealth(int x, int y) {
		return healthLookup[x][y];
	}	
	
	public void clear() {
		updateHealth(0,0,0);
	}
	  
	/**
	 * <code>updateHealth</code> updates the system health display
	 * @param pi  performance index
	 * @param tl  threat level
	 */
	public void updateHealth(double pi, double tl, int lostRev) {
		lbl.setText(type + " Status: " + pi);
		lblLostRev.setText(title + " $" + lostRev + "  ");
		
		int perfLvl = getPerformanceLevel(pi);
		if (perfLvl >=0 && (int)tl >= 0) {
			category = lookupHealth(perfLvl,(int)tl);
		}
		
		String col = ((Level)systemHealthLevels.elementAt(category)).getCategory();
//System.out.println("==>" + type + " pi:" + pi + " tl:" + tl + " color:" + col);
		Color c = Color.gray;
		if (col == "Red")
			c = Color.red;
		else if (col == "Orange")
			c = Color.orange;
		else if (col == "Yellow")
			c = Color.yellow;
		else if (col == "Blue")
			c = Color.blue;
		else if (col == "Green")
			c = Color.green;		
		cb.updateColor(c);
		
		String desc = ((Level)systemHealthLevels.elementAt(category)).getDescription();
		tp.setText(desc);
			
		repaint();
	}
	
	private int getPerformanceLevel(double pi) {
		int performanceLevel = 0;
		for (int i=0; i < performanceLevels.size(); i++) {
			double min = ((PerformanceLevel)performanceLevels.elementAt(i)).getMin();
			double max = ((PerformanceLevel)performanceLevels.elementAt(i)).getMax();
			if (pi >= min && pi <= max) {
				performanceLevel = i; //((PerformanceLevel)performanceLevels.elementAt(i)).getLevel();
			}
		}
		return performanceLevel;
	}
	
	public Dimension getpreferredSize(){
	    return new Dimension(800,200);  
	}

	public Dimension getMinimumSize(){
	    return new Dimension(800,200);  
	}	
	
	public Color getColor() {
		return cb.getColor();
	}
	
	public static void main(String args[]){
	    SystemLevels lvls = new SystemLevels(); 
	    
		ConsequenceModelingExample test = new ConsequenceModelingExample();
	    Hierarchy h =new Hierarchy();
	    h=test.getHierarchyExample();
	    CMTSystem m = new CMTSystem(h);
	    
	    
	    CMTSystemHealthPanel sh = new CMTSystemHealthPanel(m.getHealthTable(), lvls.getSystemHealthLevels(), lvls.getPerformanceLevels(),m,"System");
	
	    sh.updateHealth(.056, 2.0,0);
		JFrame mainFrame = new JFrame("SystemHealth test");
	    mainFrame.setSize(300, 400);
		mainFrame.setContentPane(sh);
		mainFrame.setVisible(true);
	}
}


	
	

