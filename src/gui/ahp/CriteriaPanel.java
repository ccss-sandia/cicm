// Graphical User Interface
package gui.ahp;

//imports
import gui.examples.*;

import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
//import javax.swing.event.*;
//import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.*;
//import java.io.*;
//import java.net.*;

//import Jama.*;


// Abstract Data Type
//import adt.*;
import adt.ahp.*;

/**
 * <code>CriteriaPanel</code> is the panel with HierarchyTree + add/del criterium
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 26, 2003 initial version
 */
public class CriteriaPanel extends JPanel implements ActionListener {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//ATTRIBUTES
  //private Hierarchy h; // The Decision hierarchy
  private HierarchyTree ht; // the Jtree 
  private JButton add; // a button to add a criterium
  private JButton del; // a button to delete a criterium
  private JAHP window;// the main JAHP window

  /**
   * Creates a new  <code>CriteriaPanel</code> instance.
   * @param the decision <code>Hierarchy</code>
   * @param the main <code>JAHP</code> window
   */
  public CriteriaPanel(Hierarchy h,JAHP window) {
    super(new BorderLayout());
    this.window=window;
    //HierarchyTree
    ht =new HierarchyTree(h,window);
    this.add("Center",ht);

    // JButton to add and delete criteria
    JPanel hierarchymodifypanel =new JPanel(new GridLayout(0,1));
    add=new JButton("Add Performance Measure");
    del=new JButton("Delete Performance Measure");
    hierarchymodifypanel.add(add);
    hierarchymodifypanel.add(del);
    add.addActionListener(this);
    del.addActionListener(this);

    this.add("South",hierarchymodifypanel);
        
  }
  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getpreferredSize(){
    return new Dimension(150,400);  
  }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
    return new Dimension(150,300);  
  }

  /**
   * <code>actionPerformed</code> method invoked when an Alternative is add/dell
   *
   * @param e a <code>ActionEvent</code> value
   */
  public void actionPerformed(ActionEvent e){
    if (e.getSource()==add){
    	Criteria selectedPerformanceMeasure;
    	try {
    		selectedPerformanceMeasure = (Criteria) ht.getSelectionPath().getLastPathComponent();
    	} catch (NullPointerException ex) {
    		// no path was selected...get the top of the tree
    		selectedPerformanceMeasure = (Criteria)ht.getPathForRow(0).getLastPathComponent();
    	}
      AddPerformanceMeasureDialog d = new AddPerformanceMeasureDialog(selectedPerformanceMeasure);
      d.pack();
      d.setVisible(true);
      
      
      //System.out.println("Add Performance Measure");      
      ht.addNode(d.getPerformanceMeasure(), d.getDescription());
      window.updateALTERNATIVE();
    }
    else{
      //System.out.println("Del Performance Measure");
      ht.delNode();
      window.updateALTERNATIVE();
    }
  }
  /**
   * <code>getSelectedPath</code> returns the Criteria object selected in the hierarchy tree
   */  
  public Criteria getSelectedPath() {
	  try {
		  Criteria sel = (Criteria)ht.getSelectionPath().getLastPathComponent();
		  return sel;
	  }
	  catch (NullPointerException e) {
		  return null;
	  }
  }

  /**
   * <code>main</code> method to test this class.
   * @param Criterium :  command line
   * 
   */
  public static void main(String[] args) {
    // create a frame
	AHPExample test = new AHPExample();
    Hierarchy h =new Hierarchy();
    h=test.getHierarchyExample();
    JFrame mainFrame = new JFrame("Performance Measure Panel test");
    mainFrame.setContentPane(new CriteriaPanel(h,null));
    mainFrame.pack();
    mainFrame.setVisible(true);
  }
}
