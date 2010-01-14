package gui.cmt;

//import gui.ahp.AlternativePanel;
import gui.examples.ConsequenceModelingExample;
import gui.examples.SystemLevels;
import layout.SpringUtilities;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.util.*;

//import javax.swing.*;

import java.io.*;
import java.awt.event.*;

//import adt.ahp.Alternative;
//import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import adt.cmt.*;

/**
 * <code>CMTLoadShedPanel</code> is the panel that allows users to specify the amount
 * of load shed during an outage.
 * @author  Lozanne Chavez 
 * @version February 2008
 */

public class CMTLoadShedPanel extends JPanel implements DocumentListener, ActionListener, MouseListener { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CMTSystem s;
	CMT window;
	SystemComponent sc;
	CMTSystemHealthPanel comphealth;
	
	int width = 400;
	int height = 500;
	
	//CMTPerformanceMeasuresPanel pmp;
	
	
	ScrollPaneLayout spl;
	
	CMTLoadShedPanel (CMTSystem s, CMT window, SystemComponent sc) { 
	   //super(new BoxLayout(this, BoxLayout.Y_AXIS));
	   this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	   this.s = s;
	   this.window = window;
	   
	   //this.spl=new ScrollPaneLayout();
	   //spl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	   //spl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	   //this.setLayout(spl);
	   
	   
	   JPanel p1 = new JPanel(new SpringLayout());	  
	   //	 Lay out the panel.

	   this.add(p1);
	   
	   HashMap loads = (HashMap)s.getLoadComponents();
	   Iterator it = loads.keySet().iterator();

	   JLabel dir = new JLabel("Enter the percentage of load that was shed for each:");
	   // add text fields for each load component
	   while (it.hasNext()) {		   
		   String name = (String)it.next();
 //System.out.println("name is " + name);
		   SystemComponent l = (SystemComponent)loads.get(name);
		   // add a text field for each load component
		   JLabel lbl = new JLabel(l.getName() + ":");
		   p1.add(lbl);
		   JTextField tf = new JTextField();
		   p1.add(tf);
	   }
	   
	   //	 Lay out the panel.
	   SpringUtilities.makeCompactGrid(p1,
			   loads.size(), 2, //rows, cols
	                                   6, 6,        //initX, initY
	                                   6, 6);       //xPad, yPad
	   
	   
	   Border etchedBdr = BorderFactory.createEtchedBorder();
	   Border titledBdr = BorderFactory.createTitledBorder(etchedBdr, " Load Shed ");
	   Border emptyBdr  = BorderFactory.createEmptyBorder(5,5,5,5);
	   Border compoundBdr=BorderFactory.createCompoundBorder(emptyBdr,titledBdr);
	   Border compoundBdr2=BorderFactory.createCompoundBorder(compoundBdr, emptyBdr);
	   this.setBorder(compoundBdr2);
	}
	
/*		   if (sc.getLoadShed().size() > 0) {
			   int numAffectedComponents = sc.getLoadShed().size() + 5;
			   //p3.setLayout(new GridLayout(numAffectedComponents, 2));
			   p3.setLayout(new SpringLayout());
			   Iterator it = (Iterator)sc.getLoadShed().keySet().iterator();
			   p3.add(new JLabel("Affected Components:"));
			   p3.add(new JLabel(""));
			   while (it.hasNext()) {
				   String key = (String)it.next();
				   int val = sc.getLoadShed(key);
				   p3.add(new JLabel("Component: " + key));
				   p3.add(new JLabel("Amount Load Shed: " + String.valueOf(val) + "%"));
				   window.map.setSelectedComponent(s.getComponent(key));
			   }
			   
			   
			   SpringUtilities.makeCompactGrid(p3,
                       numAffectedComponents, 2, //rows, cols
                       6, 6,        //initX, initY
                       6, 6);       //xPad, yPad			   
		   
		   }
		   */
	   
	
		
	public Dimension getpreferredSize(){
	    return new Dimension(width,height);  
	}

	public Dimension getMinimumSize(){
	    return new Dimension(width,height);  
	}	
	
	public void mousePressed(MouseEvent e) {
//System.out.println("CMTLoadShedPanel mouse pressed");		
		/*
		//String comp = map.getSelectedComponent();
		String comp = s.getSelectedComponent();
		System.out.println(comp + " was selected in main");
		//s.setSelectedComponent(comp);
		//status.sus.setComponent(comp);
		Vector comps = (Vector)s.getSystemComponents();
		SystemComponent sc = s.getComponent(comp);
		double weight = sc.getPI();
//		status.health.updateHealth(weight, 2);
 
 */
		//System.out.println("CMT:  Something happened...");
		// update the susceptibility
		
		//status.sus.setComponent(s.getSelectedComponent());
		SystemComponent sc = s.getComponent(s.getSelectedComponent());
		//System.out.println("selected component is " + sc.getName());
		if (sc != null) {
			//double pi = sc.getPI() * 100;
	//		s.setComponentSusceptibility(s.getSelectedComponent(), (int)s.getSystemThreatLevel());         
			//health.updateDisplay();
			//int p = (int)pi;
			//cp.setComponent(sc);
		}
	}

	public void mouseReleased(MouseEvent e) { } // do nothing
	public void mouseClicked(MouseEvent e) { } // do nothing
	public void mouseEntered(MouseEvent e) { } // do nothing
	public void mouseExited(MouseEvent e) { } // do nothing
	
	
	public void actionPerformed(ActionEvent e) {
		//int index = threatLevels.getSelectedIndex();
	//	if (index >= 0) {
		//	sc.setThreatLevel(index);

//		}
	}
	public void removeUpdate(DocumentEvent event) {}
	
	public void changedUpdate(DocumentEvent event){ }
	public void insertUpdate(DocumentEvent event) {
	
//System.out.println("###########################################################");
		String source = (String)event.getDocument().getProperty("name");
//System.out.println("!!!!! Updated " + source);		
	//	boolean requiresUpdate = false;
	//    if (source == "name") {
	 //     sc.setName(name.getText());
	   // } 
	}	
	public static void main(String args[]){
		//File coords = new File("c:/documents and settings/lmchave/desktop/scada tool/images/IEEE RTS-96 Grid Coordinates.txt");
		 
		//CMTSystem s = new CMTSystem(null);
		//s.setImageFile("C:/Documents and Settings/lmchave/Desktop/SCADA Tool/images/IEEE RTS-96 Grid.jpg");
		//s.setCoordsFile(coords);
		
		ConsequenceModelingExample test = new ConsequenceModelingExample();
		Hierarchy h =new Hierarchy();
		h=test.getHierarchyExample();
		    
		CMTSystem m = new CMTSystem(h);
		
		
		m.setImageFile("C:/Documents and Settings/lmchave/Desktop/SCADA Tool/images/IEEE RTS-96 Grid.jpg");
		m.setComponentParametersFile(new File("C:/Documents and Settings/lmchave/Desktop/SCADA Tool/images/IEEE RTS-96 Grid Coordinates.txt"));
		    
		SystemLevels lvls = new SystemLevels();
		m.setPerformanceLevels(lvls.getPerformanceLevels());
		m.setSystemHealthLevels(lvls.getSystemHealthLevels());
		m.loadComponentParameters(new File("C:/Documents and Settings/lmchave/Desktop/SCADA Tool/images/IEEE RTS-96 Grid Coordinates.txt"));
	//	System.out.println("loaded " + m.getSystemComponents().size() + " components");
		SystemComponent sc = m.getComponent("T4");

		    
		 
		JFrame mainFrame = new JFrame("Load Shed test");
		mainFrame.setSize(600, 525);
		mainFrame.setContentPane(new CMTLoadShedPanel(m, null, sc));
		mainFrame.setVisible(true);		 
	}
}
