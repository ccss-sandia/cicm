// Graphical User Interface
package gui.ahp;

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

/**
 * <code>AlternativesModel</code> the custom data model used by AlternativesTable (swing.Table)
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version April 14, 2003
 */
public class AlternativesModel extends AbstractTableModel {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//ATTRIBUTES
  //private Hierarchy h; // the Decision Hierarchy
  private JAHP window; // the main window
  private AlternativesTable at;  // The table to reference
  private Vector<TableModelListener> tableModelListeners = new Vector<TableModelListener>(); // The list of all listeners associated to this model
  private int index; //index of the last deleted alternative

  /**
   * Creates a new  <code>AlternativesModel</code> instance.
   * @param the decision <code>Hierarchy</code>
   * @param the <code>AlternativeTable</code> to model
   * @param the main <code>JAHP</code> window
   */
  public AlternativesModel(Hierarchy h,AlternativesTable at,JAHP window) {
    //this.h=h;
    this.window=window;
    this.at=at;
    this.addTableModelListener(at);
  }



//////////////// Fire events //////////////////////////////////////////////

//   public void tableChanged(TableModelEvent e){
//     System.out.println("tableChanged event fired un AlternativesModels");
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
    else if (col==1) return "Description";
    else return "Weight";
    }

  /**
   * <code>getRowCount</code> overide method here.
   *
   * @return int the number of rows
   */
  public int getRowCount() {
    // we need to know where in our model to add this alternative
	try {
		Criteria parent = window.getSelectedPath();	  
		return parent.getNumAlternatives();
	} catch (NullPointerException e) { return 0; }
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
    // we need to know where in our model to add this alternative
	Criteria parent = window.getSelectedPath();	  
	if (parent != null) {
		Alternative alt=(Alternative)(parent.getAlternatives()).get(row);
		if (col == 0) {
			return alt.getName();
		} else if (col == 1) {
			return alt.getComment();
		} else {
			return alt.getWeight();
		}
	    //if (col==0) return alt.getName();
	    //else return new Double((h.getGoal()).Jstar(row));		
	} else 
	  return null;
  }
  
  /**
   * <code>isCellEditable</code> overide method here.
   * @param int row index
   * @param int column index
   * @return boolean if the cell is editable
   */
  public boolean isCellEditable(int row, int col){
    ListSelectionModel lsm = at.getSelectionModel();
    int selectedRow = lsm.getMinSelectionIndex();
    if (col<=1 && row==selectedRow) return true; 
    else return false;

  }

  /**
   * <code>isCellEditable</code> overide method here.
   * @param int row index
   * @param int column index
   * @param  the Object value to set
   */   
  public void setValueAt(Object value, int row, int col) {
    // we need to know where in our model to add this alternative
	Criteria parent = window.getSelectedPath();	  	  
    Alternative alt=(Alternative)(parent.getAlternatives()).get(row);
    if (col == 0) {
    	alt.setName((String) value);
    } else if (col == 1) {
    	alt.setComment((String) value);
    }
    window.updateaftermodifyALTERNATIVE();
    //fireTableCellUpdated(row, col);
    }

  /**
   * <code>addRow</code>  method to add a row in the Table Model.
   */   
  public void addRow(String cs, String desc) {  
    // we need to know where in our model to add this alternative
	Criteria parent = window.getSelectedPath();  
	Alternative alt=new Alternative(); 
	alt.setName(cs);
	alt.setComment(desc);
    parent.addAlternative(alt);
    at.tableChanged(new TableModelEvent(this, parent.getNumAlternatives()-1,  parent.getNumAlternatives()-1, javax.swing.event.TableModelEvent.ALL_COLUMNS, javax.swing.event.TableModelEvent.INSERT) );
    fireTableRowsInserted(parent.getNumAlternatives()-1,parent.getNumAlternatives()-1);
    window.updateafteraddALTERNATIVE(alt);

  }

  /**
   * <code>removeRow</code>  method to delete a row in the Table Model.
   * @param alt the <code>Alternative</code> should be deleted
   */   
  public void removeRow(Alternative alt) {//last but not least
    // we need to know where in our model to add this alternative
    Criteria parent = window.getSelectedPath();	  	  
    index = parent.getAlternatives().indexOf(alt);
    parent.delAlternative(index);
    at.tableChanged(new TableModelEvent(this, index, index, javax.swing.event.TableModelEvent.ALL_COLUMNS, javax.swing.event.TableModelEvent.DELETE));
    fireTableRowsDeleted(index,index);
    window.updateafterdelALTERNATIVE();
  }


  /**
   * <code>updateALTERNATIVE</code>  method to update the Model
   */   
  public void updateALTERNATIVE(){
    // we need to know where in our model to add this alternative
	Criteria parent = window.getSelectedPath();	  
    //Systemout.println("fireTableDataChanged");
    for(int row=0;row<parent.getNumAlternatives();row++) fireTableCellUpdated(row,2) ;
    //fireTableRowsUpdated(0,h.getNb_alternatives()) ;
      //fireTableDataChanged(); 
  }

}
