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
import adt.util.Round;

/**
 * <code>CMTComponentRankings</code> is the panel that shows the ranking of affected components.
 * @author  Lozanne Chavez 
 * @version October 2008
 */

public class CMTComponentRankingsPanel extends JPanel { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CMTSystem s;
	CMT window;
	int width = 400;
	int height = 500;
		
	JPanel p1;
	JPanel p2;
	
	CMTComponentRankingsPanel (CMTSystem s, CMT window) { 
	   //super(new BoxLayout(this, BoxLayout.Y_AXIS));
	   this.setLayout(new FlowLayout());
	   this.s = s;
	   this.window = window;
	   
	   p1 = new JPanel(new SpringLayout());	  
	   //	 Lay out the panel.

	   this.add(p1);

	   p2 = new JPanel(new SpringLayout());	  
	   //	 Lay out the panel.

	   this.add(p2);

	   
	   Border etchedBdr = BorderFactory.createEtchedBorder();
	   Border titledBdr = BorderFactory.createTitledBorder(etchedBdr, " Component Rankings By Expected Disutility");
	   Border emptyBdr  = BorderFactory.createEmptyBorder(5,5,5,5);
	   Border compoundBdr=BorderFactory.createCompoundBorder(emptyBdr,titledBdr);
	   Border compoundBdr2=BorderFactory.createCompoundBorder(compoundBdr, emptyBdr);
	   p1.setBorder(compoundBdr2);
	   
	   Border etchedBdr2 = BorderFactory.createEtchedBorder();
	   Border titledBdr2 = BorderFactory.createTitledBorder(etchedBdr2, " Component Rankings By Vulnerability");
	   Border emptyBdr2  = BorderFactory.createEmptyBorder(5,5,5,5);
	   Border compoundBdr3=BorderFactory.createCompoundBorder(emptyBdr2,titledBdr2);
	   Border compoundBdr4=BorderFactory.createCompoundBorder(compoundBdr3, emptyBdr2);
	   p2.setBorder(compoundBdr4);	   
	   
	   update();
	}

	public void update() {
		updateExpectedDisutilityRanking();
		updateVulnerabilityRanking();
	}
	
	private void updateExpectedDisutilityRanking() {
	   p1.removeAll();
	   Vector pis = new Vector();
	   for (int i=0; i < s.getImpactedComponents().size(); i++){
		 SystemComponent sc = (SystemComponent)s.getImpactedComponents().get(i);
		 //s.setSelectedComponent(sc.getName());
		 //window.cp.setComponent(sc);
		 
		 // we want to order the components in reverse order by expected disutility
		 
		 // this is my work-around...
		 // I'm using string because I was having problems getting a double value out
		 // of a hash map, and I can't sort the values alone without tying them to the 
		 // component
		 
		 // I'm multiplying by 10000 because java wants to put things in scientific
		 // notation when values are less than .001, so, for example, the value .0006
		 // is always returned as 6.0E-4, which breaks the string ordering
		 double cpi = Round.round((sc.getWeightedPI() * sc.getRandomFailureRate()) * 10000, 0);
		 String val = Double.toString(cpi);
		 // since in strings 6.0 is > than 110.0, lets pad the beginning of the string with
		 // zeros so we are now comparing 006.0 to 110.0 which works
		 while (val.length() < 8) {
			 val = "0" + val;
		 }
		 
		 pis.add(val + "_" + sc.getName());
	   }
	   Collections.sort(pis, Collections.reverseOrder());

	   p1.add(new JLabel("  Component  ", JLabel.CENTER));
	   p1.add(new JLabel("  Performance Index  ", JLabel.CENTER));
	   p1.add(new JLabel("  Failure Frequency  ", JLabel.CENTER));
	   p1.add(new JLabel("  Expected Disutility  ", JLabel.CENTER));
		   
	   for (int i=0; i < pis.size(); i++ ){
		 String cpi = (String)pis.get(i);
		 //System.out.println("cpi is " + cpi);
		 int inx = cpi.indexOf("_");
		 String name = cpi.substring(inx + 1);
		 //System.out.println("name is " + name);
		 SystemComponent sc = s.getComponent(name);
		 p1.add(new JLabel(name, JLabel.CENTER));
		 p1.add(new JLabel(Double.toString(Round.round(sc.getPI(), 4)), JLabel.CENTER));
		 p1.add(new JLabel(Double.toString(Round.round(sc.getRandomFailureRate(), 4)), JLabel.CENTER));
		 double e = Round.round((sc.getWeightedPI() * sc.getRandomFailureRate()), 4);
		 p1.add(new JLabel(Double.toString(e), JLabel.CENTER));
	   }
	   
	   //	 Lay out the panel.
	   SpringUtilities.makeCompactGrid(p1,
		   s.getImpactedComponents().size()+1, 4, //rows, cols
		                                 6, 6,        //initX, initY
		                                 6, 6);       //xPad, yPad
		
	}	
	
	
	private void updateVulnerabilityRanking() {
	   p2.removeAll();
	   HashMap map = new HashMap();
	   Vector pis = new Vector();
	   for (int i=0; i < s.getImpactedComponents().size(); i++){
		 SystemComponent sc = (SystemComponent)s.getImpactedComponents().get(i);
		 s.setSelectedComponent(sc.getName());
		 window.cp.setComponent(sc);
		 double cpi = sc.getWeightedPI();
		 map.put(sc.getName(), Double.toString(cpi));
		 pis.add(Double.toString(cpi) + sc.getThreatLevel() + "_" + sc.getName());
	   }
	   Collections.sort(pis, Collections.reverseOrder());

	   p2.add(new JLabel("  Component  ", JLabel.CENTER));
	   p2.add(new JLabel("  Performance Index  ", JLabel.CENTER));
	   p2.add(new JLabel("  Threat Level  ", JLabel.CENTER));
	   
	   for (int i=0; i < pis.size(); i++ ){
		 String cpi = (String)pis.get(i);
		 //System.out.println("cpi is " + cpi);
		 int inx = cpi.indexOf("_");
		 String name = cpi.substring(inx + 1);
		 //System.out.println("name is " + name);
		 SystemComponent sc = s.getComponent(name);
		 p2.add(new JLabel(name, JLabel.CENTER));
		 p2.add(new JLabel((String)map.get(name), JLabel.CENTER));
		 p2.add(new JLabel(Double.toString(sc.getThreatLevel()), JLabel.CENTER));
	   }
	   
	   //	 Lay out the panel.
	   SpringUtilities.makeCompactGrid(p2,
		   s.getImpactedComponents().size()+1, 3, //rows, cols
		                                 6, 6,        //initX, initY
		                                 6, 6);       //xPad, yPad
		
	}
	
	public Dimension getpreferredSize(){
	    return new Dimension(width,height);  
	}

	public Dimension getMinimumSize(){
	    return new Dimension(width,height);  
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
		//System.out.println("loaded " + m.getSystemComponents().size() + " components");
		SystemComponent sc = m.getComponent("T4");

		    
		 
		JFrame mainFrame = new JFrame("Component Ranking Test");
		mainFrame.setSize(600, 525);
		mainFrame.setContentPane(new CMTComponentRankingsPanel(m, null));
		mainFrame.setVisible(true);		 
	}
}
