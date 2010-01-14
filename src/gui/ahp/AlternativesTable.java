// Graphical User Interface
package gui.ahp;

//imports
import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
import javax.swing.event.*;
//import javax.swing.tree.*;
import java.awt.*;
//import java.awt.event.*;
//import java.util.*;
//import java.io.*;
//import java.net.*;

//import Jama.*;


// Abstract Data Type
//import adt.*;
import adt.ahp.*;
import gui.examples.*;


/**
 * <code>AlternativesTable</code> the custom  swing.Table
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version April 14, 2003
 */
public class AlternativesTable extends JTable implements ListSelectionListener{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//ATTRIBUTS
  private Hierarchy h; // The decision Hierarchy
  private JAHP window; // the main JAHP window
  private AlternativesModel am; // the model

  /**
   * Creates a new  <code>AlternativesTable</code> instance.
   * @param Hierarchy h
   * @param JAHP window the main window
   */
    public AlternativesTable(Hierarchy h,JAHP window) {
        super();
	this.setPreferredScrollableViewportSize(new Dimension(150, 150));
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


	this.h=h;
	this.window=window;
	this.am=new AlternativesModel(h,this,window);
	this.setModel(am);

	//Listen for when the selection changes.
	this.setColumnSelectionAllowed(false); 
	this.setRowSelectionAllowed(false); 
	getSelectionModel().addListSelectionListener(this);


	// Look and Feel
        //DefaultCellRenderer renderer = new DefaultCellRenderer(red,new ColorRenderer(true));
        //setCellRenderer(renderer);
    }


  /**
   * the <code>valueChanged</code> overide method here.
   *
   * @param ListSelectionModel 
   * @see ListSelectionListener
   *
   */

  public void valueChanged(ListSelectionEvent e) {
    //Systemout.println("Alternatives valueChanged");    
    ListSelectionModel lsm = getSelectionModel();
    if (e.getValueIsAdjusting()) return;
    if (lsm.isSelectionEmpty()) {
      //no rows are selected
    } else {
      //int selectedRow = lsm.getMinSelectionIndex();
      //Alternative node = (Alternative) (h.getAlternatives()).get(selectedRow);
      // show the criterium in the Main Panel
      //Systemout.println("New Alternative value :"+node.toString());    
      //TO DO
      //Systemout.println("value changed"+node.getName());
      //window.updateSHOWALTERNATIVE(node);

    }
  }


  /**
   * the <code>delNode</code> method to delete a node in this table..
   *
   */  

  public void delNode() {
    //Systemout.println("Alternatives valueChanged");    
    ListSelectionModel lsm = getSelectionModel();
    if (lsm.isSelectionEmpty()) {
      //no rows are selected
//System.out.println("nothing selected");    	
    } else {
      int selectedRow = lsm.getMinSelectionIndex();
      Criteria parent = window.getSelectedPath();	  	 
      Alternative node = (Alternative) (parent.getAlternatives()).get(selectedRow);
	  int response;

      response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the constructed scale " + node.getName() + "?", "Delete Constructed Scale", JOptionPane.OK_CANCEL_OPTION);  	  
      
      if (response == 0) { // yes
    	  // show the criterium in the Main Panel
    	  //System.out.println("New Alternative value :"+node.toString());    
    	  am.removeRow(node);
    	  
      }
    }
  }

  /**
   * the <code>addNode</code> method to add a node in this table..
   *
   */  
  public void addNode(String cs, String desc) {
    am.addRow(cs, desc);
  }

  /**
   * the <code>updateALTERNATIVE</code> method to update this table.
   *
   */  
  public void updateALTERNATIVE(){
    //Systemout.println("AlternativesTable update alt");
    this.am=new AlternativesModel(h,this,window);
    this.setModel(am);
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
   * <code>main</code> method to test this class.
   * @param Criterium :  command line
   * 
   */
 public static void main(String[] args) {
    // create a frame
    AHPExample test=new AHPExample();
    Hierarchy h =new Hierarchy();
    h=test.getHierarchyExample();
    JFrame mainFrame = new JFrame("AlternativesPanel test");
    mainFrame.setContentPane(new AlternativesTable(h,null));
    mainFrame.pack();
    mainFrame.setVisible(true);
  }

}
