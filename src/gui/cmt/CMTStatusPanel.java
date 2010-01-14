// Graphical User Interface
package gui.cmt;

//imports
import gui.examples.*;

import javax.swing.*;          //This is the final package name.
import javax.swing.border.*;
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
//import javax.swing.event.*;
//import javax.swing.table.TableModel;
//import javax.swing.tree.*;



import java.awt.*;
//import java.awt.event.*;
//import java.util.*;
//import java.io.*;
//import java.net.*;



// Abstract Data Type
//import adt.*;
//import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import adt.cmt.*;
import graph.*;
/**
 * <code>CMTStatusPanel</code> is the panel with system status.
 * @author  lc
 * @version February 2008
 */
public class CMTStatusPanel extends JPanel {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//ATTRIBUTES
  public CMTSystemHealthPanel health;
  private CMTSystem s;
  private BarChart b;
  private CMT window;
  
  public CMTStatusPanel(CMT window, CMTSystem s, String type) {
    super(new BorderLayout());
    //this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.s = s;
    this.window = window;
    
    health = new CMTSystemHealthPanel(s.getHealthTable(),s.getSystemHealthLevels(), s.getPerformanceLevels(), s, type);  
    b = new BarChart("Risk Analysis", 0, 100);
    b.setMaxLabel("1");
    
    JPanel p = new JPanel(new BorderLayout());
    JPanel scale = new JPanel();
    scale.setLayout(new BoxLayout(scale, BoxLayout.Y_AXIS));
	
	scale.add(new JLabel("Scale:"));
	scale.add(new JLabel("High"));
	JButton b1 = new JButton(" ");
	b1.setBackground(Color.green);
	b1.setEnabled(false);
	
	JButton b2 = new JButton(" ");
	b2.setBackground(Color.blue);
	b2.setEnabled(false);
	JButton b3 = new JButton(" ");
	b3.setBackground(Color.yellow);
	b3.setEnabled(false);
	JButton b4 = new JButton(" ");
	b4.setBackground(Color.orange);
	b4.setEnabled(false);
	JButton b5 = new JButton(" ");
	b5.setBackground(Color.red);
	b5.setEnabled(false);
	scale.add(b5);
	scale.add(b4);
	scale.add(b3);
	scale.add(b2);
	scale.add(b1);
	scale.add(new JLabel("Low"));
    
	p.add(b, BorderLayout.CENTER);
	p.add(scale, BorderLayout.EAST);
	
    health.setSize(100,30);
    b.setSize(100,50);
    
    this.add(health, BorderLayout.NORTH);
    this.add(p, BorderLayout.SOUTH);
    
    Border etchedBdr = BorderFactory.createEtchedBorder();
    Border titledBdr = BorderFactory.createTitledBorder(etchedBdr, " " + type + " Status ");
    Border emptyBdr  = BorderFactory.createEmptyBorder(5,5,5,5);
    Border compoundBdr=BorderFactory.createCompoundBorder(emptyBdr,titledBdr);
    Border compoundBdr2=BorderFactory.createCompoundBorder(compoundBdr, emptyBdr);
    this.setBorder(compoundBdr2);
  }
  
  public BarChart getBarChart() {
	  return this.b;
  }

  
  /* <code>updateHealth</code> updates the system health
   * @param pi   performance index
   * @param tl   threat level
   */
  public void updateHealth(double pi, double tl) {
	health.updateHealth(pi, tl, s.getSystemLostRevenue());
	if (s.getSelectedComponent() != null) {
		double comppi = s.getComponent(s.getSelectedComponent()).getPI();
//System.out.println("color is ===> " + window.cp.comphealth.getColor().toString());	
		if (b.containsComponent(s.getSelectedComponent())) {
			b.update(s.getSelectedComponent(), (int)comppi, window.cp.comphealth.getColor());		
		} else {	
			b.add(s.getSelectedComponent(), (int)comppi, window.cp.comphealth.getColor());
		}
	}
  }
  
  public void removeComponent(String name) {
	  b.remove(name);
  }
    
  public Dimension getpreferredSize(){
    return new Dimension(150,30);  
  }

  public Dimension getMinimumSize(){
    return new Dimension(150,30);  
  }
  
  public static void main(String[] args) {
    // create a frame
    ConsequenceModelingExample ex = new ConsequenceModelingExample();
    Hierarchy h = ex.getHierarchyExample();
    
    SystemLevels lvls = new SystemLevels();
    
    CMTSystem s = new CMTSystem(h);
    s.setSystemHealthLevels(lvls.getSystemHealthLevels());
    s.setPerformanceLevels(lvls.getPerformanceLevels());
    
    JFrame mainFrame = new JFrame("Status test");
    mainFrame.setSize(450,520);
    mainFrame.setContentPane(new CMTStatusPanel(null, s, "System"));
    //mainFrame.pack();
    mainFrame.setVisible(true);
    
  }
}
