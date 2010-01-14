// Graphical User Interface
package gui.cmt;

//imports
import javax.swing.*;          //This is the final package name.
import javax.swing.event.*;
import java.awt.*;
import java.util.*;

import gui.examples.*;
import adt.cmt.*;


/**
 * <code>CMTCustomerGroupsTable</code> the custom swing.Table
 */
public class CMTCustomerGroupsTable extends JTable implements ListSelectionListener{

  private static final long serialVersionUID = 1L;

  private CMTSystem s; // the system parameters
  private CMTCustomerGroupsModel cgm; // the model
  private Vector customerGroups;

  /**
   * Creates a new  <code>CMTCustomerGroupsTable</code> instance.
   * @param JAHP window the main window
   */
    public CMTCustomerGroupsTable(Vector customerGroups, CMTSystem s) {
        super();
        
	this.setPreferredScrollableViewportSize(new Dimension(150, 150));
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	this.s=s;
	this.cgm=new CMTCustomerGroupsModel(customerGroups,this,s);
	this.setModel(cgm);

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
    ListSelectionModel lsm = getSelectionModel();
    if (e.getValueIsAdjusting()) return;
    if (lsm.isSelectionEmpty()) {
      //no rows are selected
    } else {


    }
  }


  /**
   * the <code>delRow</code> method to delete a row in this table..
   *
   */  

  public void delRow() {
    ListSelectionModel lsm = getSelectionModel();
    if (lsm.isSelectionEmpty()) {
      //no rows are selected 	
    } else {
      int selectedRow = lsm.getMinSelectionIndex();
      CustomerGroup cg = (CustomerGroup)customerGroups.get(selectedRow);	  	 
	  int response;

      response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the customer group " + cg.getName() + "?", "Delete Customer Group", JOptionPane.OK_CANCEL_OPTION);  	  
      
      if (response == 0) { // yes  
    	  cgm.removeRow(cg);
    	  
      }
    }
  }

  /**
   * the <code>addRow</code> method to add a row in this table..
   *
   */  
  public void addRow(String name, double rate, double usage) {
    cgm.addRow(name, rate, usage);
  }

  /**
   * the <code>updateALTERNATIVE</code> method to update this table.
   *
   */  
  public void updateCustomerGroup(){
    this.cgm=new CMTCustomerGroupsModel(s.getCustomerGroups(),this,s);
    this.setModel(cgm);
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
    JFrame mainFrame = new JFrame("Customer Groups Panel test");
    mainFrame.setContentPane(new CMTCustomerGroupsTable(null,null));
    mainFrame.pack();
    mainFrame.setVisible(true);
  }

}
