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

//import adt.ahp.Alternative;
//import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import adt.cmt.*;

/**
 * <code>CMTSystemHealthLevelsPanel</code> is the panel that contains the 
 * system health levels.
 */

public class CMTSystemHealthLevelsPanel extends JPanel implements MouseListener { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width = 400;
	private int height = 500;
	
	//private Vector systemHealthLevels;
	
	CMTSystemHealthLevelsPanel (Vector systemHealthLevels) {
		super(new GridLayout(systemHealthLevels.size()+1,2));
		//this.systemHealthLevels = systemHealthLevels;
		
		this.add(new JLabel("Level"));
		this.add(new JLabel("Category"));
		this.add(new JLabel("Description"));
	    for (int i=0; i < systemHealthLevels.size(); i++) {
	    	Level lvl = (Level)systemHealthLevels.get(i);
	    	JTextField l = new JTextField(Integer.toString(lvl.getLevel()));
	    	l.setEnabled(false);
	    	this.add(l);
	    	JTextField c = new JTextField(lvl.getCategory());
	    	c.setEnabled(false);
	    	this.add(c);
	    	JTextPane p = new JTextPane();
	    	p.setText(lvl.getDescription());
	    	p.setPreferredSize(new Dimension(250, 145));
	    	p.setMinimumSize(new Dimension(10, 10));
	    	p.setEnabled(false);
	    	JScrollPane sp = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    	this.add(sp);
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
	    
		 
		JFrame mainFrame = new JFrame("SystemHealthLevelsPanel test");
		mainFrame.setSize(600, 525);
		mainFrame.setContentPane(new CMTSystemHealthLevelsPanel(m.getSystemHealthLevels()));
		mainFrame.setVisible(true);		 
	}
}
