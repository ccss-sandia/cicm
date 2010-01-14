// Graphical User Interface
package gui.cmt;

//imports
import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.event.TableModelEvent;
//import java.awt.*;
//import java.awt.event.*;
import java.util.*;
//import java.io.*;
//import java.net.*;

//import Jama.*;


// Abstract Data Type
//import adt.*;
import adt.ahp.*;
import adt.cmt.CustomerGroup;
import adt.cmt.*;
/**
 * <code>CustomerGroupsModel</code> the custom data model used by CustomerGroupsTable (swing.Table)
 */
public class CMTCustomerGroupsModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;

  private CMTSystem s; // the system
  private CMTCustomerGroupsTable cgt;  // The table to reference
  private Vector<TableModelListener> tableModelListeners = new Vector<TableModelListener>(); // The list of all listeners associated to this model
  private int index; //index of the last deleted customer group
  private Vector customerGroups;
  /**
   * Creates a new  <code>CustomerGroupModel</code> instance.
   * @param the <code>customer groups</code>
   * @param the <code>CustomerGroupTable</code> to model
   * @param the main <code>CMT</code> window
   */
  public CMTCustomerGroupsModel(Vector customerGroups, CMTCustomerGroupsTable cgt,CMTSystem s) {
    this.customerGroups=customerGroups;
    this.s=s;
    this.cgt=cgt;
    this.addTableModelListener(cgt);
    

    
  }



//////////////// Fire events //////////////////////////////////////////////

//   public void tableChanged(TableModelEvent e){
//     System.out.println("tableChanged event fired un CustomerGroupsModels");
//     System.out.println("NOT YET Implemented");
//     int row = e.getFirstRow();
//     int column = e.getColumn();
//     String columnName = getColumnName(column);
//     Object data = model.getValueAt(row, column);



//     switch (e.getType()){
//     //          Notifies all listeners that all cell values in the table's rows may have changed.  void       
//     case (TableModelEvent.UPDATE) :      fireTableRowsUpdated(index,index);
//       //Notifies all listeners that rows in the range [firstRow, lastRow], inclusive, have been deleted.  void
//     case (TableModelEvent.DELETE) :     fireTableRowsDeleted(index,index);
//     //Notifies all listeners that rows in the range [firstRow, lastRow], inclusive, have been inserted.  void
//     case (TableModelEvent.INSERT) :      fireTableRowsInserted(h.getNb_alternatives()-1,h.getNb_alternatives()-1);
//     }
//   }

  

//////////////// TableModel interface implementation ///////////////////////


    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     */
    public void addTableModelListener(TableModelListener l) {
        tableModelListeners.addElement(l);
    }

    /**
     * Removes a listener previously added with addTreeModelListener().
     */
    public void removeTableModelListener(TableModelListener l) {
      tableModelListeners.removeElement(l);
    }


  /**
   * <code>getColumnClass</code> overide method here.
   *
   * @param int the index of the column
   * @return String or Double method
   */
    @SuppressWarnings("unchecked")
	public Class getColumnClass(int columnIndex) { 	
    if (columnIndex<=1) return String.class;
    else return Double.class;
  }

  /**
   * <code>getColumnName</code> overide method here.
   *
   * @param int the index of the column
   * @return String the name of the column
   */
  public String getColumnName(int col) { 
    if (col==0) return "Name";
    else if (col==1) return "Rate";
    else return "Usage";
    }

  /**
   * <code>getRowCount</code> overide method here.
   *
   * @return int the number of rows
   */
  public int getRowCount() {
	  return 0;
  }

  /**
   * <code>getColumnCount</code> overide method here.
   *
   * @return int the number of columns
   */
  public int getColumnCount() { return 3; }

  /**
   * <code>getValueAt</code> overide method here.
   * @param int row index
   * @param int column index
   * @return int the number of columns
   */
  public Object getValueAt(int row, int col) { 
	  return null;
  }
  
  /**
   * <code>isCellEditable</code> overide method here.
   * @param int row index
   * @param int column index
   * @return boolean if the cell is editable
   */
  public boolean isCellEditable(int row, int col){
	  return false;
  }

  /**
   * <code>isCellEditable</code> overide method here.
   * @param int row index
   * @param int column index
   * @param  the Object value to set
   */   
  public void setValueAt(Object value, int row, int col) {
  }

  /**
   * <code>addRow</code>  method to add a row in the Table Model.
   */   
  public void addRow(String name, double rate, double usage) {  
	  CustomerGroup cg = new CustomerGroup(name, rate, usage);
	  s.addCustomerGroup(cg);
      cgt.tableChanged(new TableModelEvent(this, s.getNumCustomerGroups()-1,  s.getNumCustomerGroups()-1, javax.swing.event.TableModelEvent.ALL_COLUMNS, javax.swing.event.TableModelEvent.INSERT) );
      fireTableRowsInserted(s.getNumCustomerGroups()-1,s.getNumCustomerGroups()-1);
	  //s.updateafteraddCustomerGroup(cg);
  }

  /**
   * <code>removeRow</code>  method to delete a row in the Table Model.
   * @param cg the <code>CustomerGroup</code> should be deleted
   */   
  public void removeRow(CustomerGroup cg) {//last but not least
	  s.delCustomerGroup(cg);
      cgt.tableChanged(new TableModelEvent(this, s.getNumCustomerGroups()-1,  s.getNumCustomerGroups()-1, javax.swing.event.TableModelEvent.ALL_COLUMNS, javax.swing.event.TableModelEvent.INSERT) );
      fireTableRowsDeleted(s.getNumCustomerGroups()-1,s.getNumCustomerGroups()-1);
  }

}
