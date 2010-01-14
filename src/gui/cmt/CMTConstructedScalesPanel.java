package gui.cmt;

//import gui.ahp.AlternativePanel;
import gui.examples.ConsequenceModelingExample;
import gui.examples.SystemLevels;

import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.*;

import java.io.*;
import java.awt.event.*;

import adt.ahp.Alternative;
//import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import adt.cmt.*;

/**
 * <code>CMTConstructedScalesPanel</code> is the panel that contains the 
 * constructed scales.
 */

public class CMTConstructedScalesPanel extends JPanel implements MouseListener { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width = 400;
	private int height = 500;
	
	//private Vector constructedScales;
	
	CMTConstructedScalesPanel (Vector constructedScales) {
		super(new GridLayout(constructedScales.size()+1,1));
		
		//this.constructedScales = constructedScales;
	    
		this.add(new JLabel("The constructed scales..."));
		//this.add(new JLabel("Scale"));
		//this.add(new JLabel("Disutiltity"));

	    for (int i=0; i < constructedScales.size(); i++) {
	    	Vector v = (Vector)constructedScales.get(i);
	    	for (int j=0; j < v.size(); j++) {
	    		Alternative alt = (Alternative)v.get(j);
	    		//JTextField l = new JTextField(Integer.toString(j));
	    		//l.setEnabled(false);
	    		//this.add(l);
	    		JTextField min = new JTextField(alt.getName() + " - " + Double.toString(alt.getWeight()));
	    		min.setEnabled(false);
	    		this.add(min);
	    		//JTextField max = new JTextField(Double.toString(alt.getWeight()));
	    		//max.setEnabled(false);
	    	
	    	//this.add(max);
	    	}
	    }
	}
	
		
	public Dimension getpreferredSize(){
	    return new Dimension(width,height);  
	}

	public Dimension getMinimumSize(){
	    return new Dimension(width,height);  
	}	
	
	public void mousePressed(MouseEvent e) { } // do nothing
	public void mouseReleased(MouseEvent e) { } // do nothing
	public void mouseClicked(MouseEvent e) { } // do nothing
	public void mouseEntered(MouseEvent e) { } // do nothing
	public void mouseExited(MouseEvent e) { } // do nothing
	
	
	
	public static void main(String args[]){
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
	    
		 
		JFrame mainFrame = new JFrame("PerformanceLevelsPanel test");
		mainFrame.setSize(600, 525);
		mainFrame.setContentPane(new CMTPerformanceLevelsPanel(m.getPerformanceLevels()));
		mainFrame.setVisible(true);		 
	}
}
