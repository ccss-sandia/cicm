package gui.cmt;

//import gui.ahp.AlternativePanel;
import gui.examples.ConsequenceModelingExample;
import gui.examples.SystemLevels;
import layout.SpringUtilities;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
 * <code>CMTImageMap</code> is the panel that contains the image map diagram of the system.
 * @author  Lozanne Chavez 
 * @version February 2008
 */

public class CMTComponentPanel extends JPanel implements DocumentListener, ActionListener, MouseListener { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CMTSystem s;
	CMT window;
	SystemComponent sc;
	CMTSystemHealthPanel comphealth;
	JLabel namelbl, loadShed;
	
	JPanel p1, p2, p3;
	JTextField name,type,description, outageTime, randomFailureRate, costToRepair, utilityWorkerLevel,
		faunaLevel, numberCommercialCustomers, numberCommercialCustomersAffected,
		numberResidentialCustomers, numberResidentialCustomersAffected,
		numberSmIndustrialCustomers, numberSmIndustrialCustomersAffected,
		numberLgIndustrialCustomers, numberLgIndustrialCustomersAffected;
	JComboBox threatLevels;
	
	int width = 400;
	int height = 700;
	
	//CMTPerformanceMeasuresPanel pmp;
	
	
	//ScrollPaneLayout spl;
	
	CMTComponentPanel (CMTSystem s, CMT window, SystemComponent sc) { 
	   //super(new BoxLayout(this, BoxLayout.Y_AXIS));
	   this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	   this.s = s;
	   this.window = window;
	   
	   //this.spl=new ScrollPaneLayout();
	   //spl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	   //spl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	   //this.setLayout(spl);
	   
	   namelbl = new JLabel(" ");
	   this.add(namelbl);

	   comphealth = new CMTSystemHealthPanel(s.getHealthTable(),s.getSystemHealthLevels(), s.getPerformanceLevels(), s, "Component");
	   //health.setSize(500,50);
	   this.add(comphealth);
	   
	   p1 = new JPanel(new SpringLayout());	  
	   //	 Lay out the panel.

	   p2 = new JPanel();
	   p3 = new JPanel();
	   this.add(p1);
	   this.add(p2);
	   this.add(p3);
	   
	   //p1.add(new JLabel("No component is selected"));
	   
	   p1.add(new JLabel("Name"));
	   name = new JTextField();
	   name.getDocument().addDocumentListener(this);
	   name.getDocument().putProperty("name", "name");
	   p1.add(name);
   
	   p1.add(new JLabel("Type"));
	   type = new JTextField();
	   type.getDocument().addDocumentListener(this);
	   type.getDocument().putProperty("name", "type");	   
	   p1.add(type);
   
	   p1.add(new JLabel("Description"));
	   description = new JTextField();
	   description.getDocument().addDocumentListener(this);
	   description.getDocument().putProperty("name", "description");	   
	   p1.add(description);
   
	   
	   p1.add(new JLabel("Threat Level"));
	   threatLevels = new JComboBox();
	   threatLevels.addActionListener(this);
	   p1.add(threatLevels);
	     
	   p1.add(new JLabel("Outage Time"));
	   outageTime = new JTextField();
	   outageTime.getDocument().addDocumentListener(this);
	   outageTime.getDocument().putProperty("name", "outageTime");	   
	   p1.add(outageTime);

	   p1.add(new JLabel("Random Failure Rate"));
	   randomFailureRate = new JTextField();
	   randomFailureRate.getDocument().addDocumentListener(this);
	   randomFailureRate.getDocument().putProperty("name", "randomFailureRate");	   
	   p1.add(randomFailureRate);
	   
	   p1.add(new JLabel("Cost to Repair"));
	   costToRepair = new JTextField();
	   costToRepair.getDocument().addDocumentListener(this);
	   costToRepair.getDocument().putProperty("name", "costToRepair");	   
	   p1.add(costToRepair);
	   
	   p1.add(new JLabel("Default Utility Worker Level"));
	   utilityWorkerLevel = new JTextField();
	   utilityWorkerLevel.getDocument().addDocumentListener(this);
	   utilityWorkerLevel.getDocument().putProperty("name", "utilityWorkerLevel");	   
	   p1.add(utilityWorkerLevel);

	   p1.add(new JLabel("Default Fauna Level"));
	   faunaLevel = new JTextField();
	   faunaLevel.getDocument().addDocumentListener(this);
	   faunaLevel.getDocument().putProperty("name", "faunaLevel");	   
	   p1.add(faunaLevel);
	   
	   p1.add(new JLabel("Number of Residential Customers"));
	   numberResidentialCustomers = new JTextField();
	   numberResidentialCustomers.getDocument().addDocumentListener(this);
	   numberResidentialCustomers.getDocument().putProperty("name", "numberResidentialCustomers");	   
	   p1.add(numberResidentialCustomers);
   
	   p1.add(new JLabel("Number of Commerical Customers"));
	   numberCommercialCustomers = new JTextField();
	   numberCommercialCustomers.getDocument().addDocumentListener(this);
	   numberCommercialCustomers.getDocument().putProperty("name", "numberCommercialCustomers");	   
	   p1.add(numberCommercialCustomers);
   
	   p1.add(new JLabel("Number of Small/Medium Industrial Customers"));
	   numberSmIndustrialCustomers = new JTextField();
	   numberSmIndustrialCustomers.getDocument().addDocumentListener(this);
	   numberSmIndustrialCustomers.getDocument().putProperty("name", "numberSmIndustrialCustomers");	   
	   p1.add(numberSmIndustrialCustomers);
   

	   p1.add(new JLabel("Number of Large Industrial Customers"));
	   numberLgIndustrialCustomers = new JTextField();
	   numberLgIndustrialCustomers.getDocument().addDocumentListener(this);
	   numberLgIndustrialCustomers.getDocument().putProperty("name", "numberLgIndustrialCustomers");	   
	   p1.add(numberLgIndustrialCustomers);

	   //p1.add(new JLabel("Load Shed"));
	   //loadShed = new JLabel();
	   //p1.add(loadShed);

	   //	 Lay out the panel.
	   SpringUtilities.makeCompactGrid(p1,
	                                   13, 2, //rows, cols
	                                   6, 6,        //initX, initY
	                                   6, 6);       //xPad, yPad

	   
	   setComponent(sc);
	}
	
	public void setComponent(SystemComponent sc) {
	   this.sc = sc;

	   //p1.removeAll();
	   p2.removeAll();
	   p3.removeAll();
	   p3.setLayout(new BorderLayout());
	   //repaint();		   
      
	   if (sc != null) {
		   namelbl.setText(sc.getName());
		   name.setText(sc.getName());
		   type.setText(sc.getType());
		   description.setText(sc.getComment());
		   int index = (int)sc.getThreatLevel();
		   if (threatLevels.getItemCount() == 0) {
			   for (int i=0; i < sc.getThreatLevels().size(); i++) {
				   Level lvl = (Level)sc.getThreatLevels().get(i);
				   //System.out.println("level is " + lvl);
				   threatLevels.addItem(lvl);
			   }
		   }
		   threatLevels.setSelectedIndex(index);		   
		   outageTime.setText(String.valueOf(sc.getTime()));
		   randomFailureRate.setText(String.valueOf(sc.getRandomFailureRate()));
		   costToRepair.setText(String.valueOf(sc.getRepairCost()));		   
		   utilityWorkerLevel.setText(String.valueOf(sc.getUtilityWorkerLevel()));
		   faunaLevel.setText(String.valueOf(sc.getFaunaLevel()));		   
		   numberResidentialCustomers.setText(String.valueOf(sc.getNumResidential()));
		   numberCommercialCustomers.setText(String.valueOf(sc.getNumCommercial()));
		   numberSmIndustrialCustomers.setText(String.valueOf(sc.getNumSmallMedIndustrial()));
		   numberLgIndustrialCustomers.setText(String.valueOf(sc.getNumLargeIndustrial()));

		   //loadShed.setText(String.valueOf(sc.getLoadShed()));
		   
		   //pmp = new CMTPerformanceMeasuresPanel(window, s);
		   //p2.add(pmp);
		   p2.add(new CMTPerformanceMeasuresPanel(window, s));
		   this.add(p1);
		   this.add(p2);
		   repaint();
		   
		   if (sc.getLoadShed().size() > 0) {
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
				   //window.map.setSelectedComponent(s.getComponent(key));
			   }
			   
			   p3.add(new JLabel("Number of Residential Customers Affected"));
			   numberResidentialCustomersAffected = new JTextField();
			   numberResidentialCustomersAffected.setEnabled(false);
			   numberResidentialCustomersAffected.setText(String.valueOf(sc.getNumResidentialAffected()));
			   p3.add(numberResidentialCustomersAffected);

			   p3.add(new JLabel("Number of Commerical Customers Affected"));
			   numberCommercialCustomersAffected = new JTextField();
			   numberCommercialCustomersAffected.setEnabled(false);
			   numberCommercialCustomersAffected.setText(String.valueOf(sc.getNumCommericalAffected()));
			   p3.add(numberCommercialCustomersAffected);

			   p3.add(new JLabel("Number of Small/Medium Industrial Customers Affected"));
			   numberSmIndustrialCustomersAffected = new JTextField();
			   numberSmIndustrialCustomersAffected.setEnabled(false);
			   numberSmIndustrialCustomersAffected.setText(String.valueOf(sc.getNumSmallMedIndustrialAffected()));
			   p3.add(numberSmIndustrialCustomersAffected);			   
			   
			   p3.add(new JLabel("Number of Large Industrial Customers Affected"));
			   numberLgIndustrialCustomersAffected  = new JTextField();
			   numberLgIndustrialCustomersAffected.setEnabled(false);
			   numberLgIndustrialCustomersAffected.setText(String.valueOf(sc.getNumLargeIndustrialAffected()));
			   p3.add(numberLgIndustrialCustomersAffected);
			   
			   SpringUtilities.makeCompactGrid(p3,
                       numAffectedComponents, 2, //rows, cols
                       6, 6,        //initX, initY
                       6, 6);       //xPad, yPad			   
		   
		   }
		   
		   comphealth.updateHealth(sc.getPI(), sc.getThreatLevel(), s.getLostRevenue(sc));
	   }
	} 
		
	public void clear() {
		setComponent(null);
		namelbl.setText("");
		name.setText("");
		type.setText("");
		description.setText("");
		threatLevels.setSelectedIndex(-1);
		outageTime.setText("");
		randomFailureRate.setText("");
		costToRepair.setText("");		   
		utilityWorkerLevel.setText("");
		faunaLevel.setText("");		   
		numberResidentialCustomers.setText("");
		numberCommercialCustomers.setText("");
		numberSmIndustrialCustomers.setText("");
		numberLgIndustrialCustomers.setText("");
		comphealth.clear();
	}
	public Dimension getpreferredSize(){
	    return new Dimension(width,height);  
	}

	public Dimension getMinimumSize(){
	    return new Dimension(width,height);  
	}	
	
	public void mousePressed(MouseEvent e) {
//System.out.println("CMTComponentPanel mouse pressed");		
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
			s.setComponentSusceptibility(s.getSelectedComponent(), (int)s.getSystemThreatLevel());         
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
		int index = threatLevels.getSelectedIndex();
		if (index >= 0) {
			sc.setThreatLevel(index);
			updateDisplay();
		}
	}
	public void removeUpdate(DocumentEvent event) {}
	
	public void changedUpdate(DocumentEvent event){ }
	public void insertUpdate(DocumentEvent event) {
	
//System.out.println("###########################################################");
		String source = (String)event.getDocument().getProperty("name");
		boolean requiresUpdate = false;
	    if (source == "name") {
	      sc.setName(name.getText());
	    } else if (source == "description") {
	      sc.setComment(description.getText());
	    } else if (source == "type") {
	      sc.setType(type.getText());
	    //} else if (source == "threatLevel") {
		//  sc.setThreatLevel(Double.parseDouble(threatLevel.getText()));
		//  requiresUpdate = true;
	    } else if (source == "outageTime") {
		  sc.setTime(Integer.parseInt(outageTime.getText()));
		  requiresUpdate = true;
	    } else if (source == "randomFailureRate") {
			  sc.setRandomFailureRate(Double.parseDouble(randomFailureRate.getText()));
			  requiresUpdate = true;
	    } else if (source == "costToRepair") {
		  sc.setRepairCost(Integer.parseInt(costToRepair.getText()));
		  requiresUpdate = true;
	    } else if (source == "utilityWorkerLevel") {
		  sc.setUtilityWorkerLevel(Integer.parseInt(utilityWorkerLevel.getText()));
		  requiresUpdate = true;
	    } else if (source == "faunaLevel") {
		  sc.setFaunaLevel(Integer.parseInt(faunaLevel.getText()));
		  requiresUpdate = true;
	    } else if (source == "numberCommercialCustomers") {
		  sc.setNumCommerical(Integer.parseInt(numberCommercialCustomers.getText()));
		  requiresUpdate = true;
	    } else if (source == "numberCommercialCustomersAffected") {
	      requiresUpdate = true;
		  sc.setNumCommercialAffected(Integer.parseInt(numberCommercialCustomersAffected.getText()));
	    } else if (source == "numberResidentialCustomers") {
		  sc.setNumResidential(Integer.parseInt(numberResidentialCustomers.getText()));
		  requiresUpdate = true;
	    } else if (source == "numberResidentialCustomersAffected") {
		  sc.setNumResidentialAffected(Integer.parseInt(numberResidentialCustomersAffected.getText()));
		  requiresUpdate = true;
	    } else if (source == "numberSmIndustrialCustomers") {
		  sc.setNumSmallMedIndustrial(Integer.parseInt(numberSmIndustrialCustomers.getText()));
		  requiresUpdate = true;
	    } else if (source == "numberSmIndustrialCustomersAffected") {
		  sc.setNumSmallMedIndustrialAffected(Integer.parseInt(numberSmIndustrialCustomersAffected.getText()));
		  requiresUpdate = true;
	    } else if (source == "numberLgIndustrialCustomers") {
		  sc.setNumLargeIndustrial(Integer.parseInt(numberLgIndustrialCustomers.getText()));
		  requiresUpdate = true;
	    } else if (source == "numberLgIndustrialCustomersAffected") {
		  sc.setNumLargeIndustrialAffected(Integer.parseInt(numberLgIndustrialCustomersAffected.getText()));
		  requiresUpdate = true;
	    }
	    if (requiresUpdate) {
	    	updateDisplay();
	    }
	}	
	public void updateDisplay() {
    	double pi = s.getSystemPI() * 100;
    	int p = (int)pi;
    	try {
    		window.cp.comphealth.updateHealth(sc.getPI(), sc.getThreatLevel(), s.getLostRevenue(sc));
    	   	window.updateHealth();	
    	   	window.status.getBarChart().update(s.selectedComponent, p, window.cp.comphealth.getColor());
    	} catch (NullPointerException e) {}
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
		System.out.println("loaded " + m.getSystemComponents().size() + " components");
		SystemComponent sc = m.getComponent("T4");

		    
		 
		JFrame mainFrame = new JFrame("Component test");
		mainFrame.setSize(600, 525);
		mainFrame.setContentPane(new CMTComponentPanel(m, null, sc));
		mainFrame.setVisible(true);		 
	}
}
