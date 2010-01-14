package gui.cmt;

import gui.examples.ConsequenceModelingExample;
import gui.examples.SystemLevels;

import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import adt.ahp.Hierarchy;
import adt.cmt.*;

public class CMTSusceptibilityPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Vector<Level> lvls = new Vector<Level>();
	
	private JLabel lblTitle;
	//private JLabel lblDesc;
	private int defaultLevel = 5;
	private int susceptibility = defaultLevel;
	public JRadioButton r;
	ButtonGroup b;
	String component;
	CMTSystem s;
	CMT window;
	
	public CMTSusceptibilityPanel (CMT window, CMTSystem s) { 
		super(new BorderLayout());
		this.window = window;
		this.s = s;
		
		// set the default susceptibility levels
		lvls.add(new Level(0, "Zero", "Completely secure, inaccessible"));
		lvls.add(new Level(1, "Very Low", "Guarded, secure area, locked, alarmed, complex closure"));
		lvls.add(new Level(2, "Low", "Secure area, locked, complex closure"));
		lvls.add(new Level(3, "Moderate", "Complex barrier, security patrols, video surveillence"));
		lvls.add(new Level(4, "High", "Unlocked, non-complex barriers (door or access panel)"));
		lvls.add(new Level(5, "Extreme", "Completely open, no controls, no barriers"));
		
		lblTitle = new JLabel("What is the susceptibility level of " + component + "?");
		this.add(lblTitle, BorderLayout.NORTH);
		
		b = new ButtonGroup();
        JPanel radioPanel = new JPanel(new GridLayout(lvls.size(),1));
        
        String comp = s.getSelectedComponent();
        
        if (comp != null) {
        	SystemComponent sc = (SystemComponent)s.getSystemComponents().get(s.getSelectedComponent());        
        	susceptibility = (int)sc.getThreatLevel();
        }
		
		for (int i=lvls.size()-1; i >= 0; i--) {
			r = new JRadioButton(lvls.elementAt(i).toString());
			int level = ((Level) lvls.elementAt(i)).getLevel();
			r.setActionCommand(Integer.toString(level));
			
			// add to button group
			b.add(r);

			//Register a listener for the radio buttons.
			r.addActionListener(this);
			
			// add the radio button to the panel
			radioPanel.add(r);
			
			// set selection status
			if (susceptibility >= 0) {
				if (susceptibility == i) {
					r.setSelected(true);
				}
			} else {
				if (defaultLevel == i) {
					r.setSelected(true);
				}
			}
		}
			
        add(radioPanel, BorderLayout.CENTER);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
	}
	
	public void paintComponent (Graphics g) { 
		super.paintComponent (g);
		lblTitle.setText("What is the susceptibility level of " + s.getSelectedComponent() + "?");
	}

	public void actionPerformed(ActionEvent e) {
		susceptibility = Integer.parseInt(e.getActionCommand());
		//System.out.println("CMTSusceptibilityPanel: " + e.getActionCommand() + " was selected");
		
		s.setComponentSusceptibility(s.getSelectedComponent(), Integer.parseInt(e.getActionCommand()));
		s.getSystemPI();
		window.updateHealth();
		//window.status.b.removeItem(s.getSelectedComponent());
		//double pi = s.getSystemPI() * 100;
		//int p = (int)pi;
		//window.status.b.addItem(s.getSelectedComponent(), p, window.health.getColor());
		
		//window.status.b.repaint();
	}
	
	public String getComponent() {
		return component;
	}
	
	public void setComponent(String component) {
		this.component = component;
		repaint();
	}
	
	public int getSusceptibility() {
		return susceptibility;
	}
	
	public void setSusceptibility(int sus) {
		this.susceptibility = sus;
		/*
		JRadioButton r = null;
		for (int i=0; i < sus; i++) {
			r = (JRadioButton)b.getElements().nextElement();
			System.out.println("--------------------------------button is " + r.getText());
		}
		if (r != null) {
			r.setSelected(true);
		}
		*/
	}
	
	public Dimension getpreferredSize(){
	    return new Dimension(600,200);  
	}

	public Dimension getMinimumSize(){
	    return new Dimension(600,200);  
	}	
	
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
		
		JFrame mainFrame = new JFrame("Susceptibility test");
	    mainFrame.setSize(600, 200);
	    CMTSusceptibilityPanel s = new CMTSusceptibilityPanel(null, m);
	    s.setComponent("T1");
		mainFrame.setContentPane(s);
		mainFrame.setVisible(true);
	}
}


	
	


