// Graphical User Interface
package gui.ahp;

//imports
import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
//import javax.swing.event.*;
//import javax.swing.tree.*;
import javax.swing.JTable;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
//import java.io.*;
//import java.net.*;
import java.lang.Double;

//import Jama.*;


// Abstract Data Type
//import adt.*;
import adt.ahp.Alternative;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import gui.examples.*;

/**
 * <code>CriteriumSonTablePane</code> the  Pane to show  sons of a criterium  and their weight in a JTable
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 19, 2003 initial version
 * @version March 26, 2003 final version
 */
public class CriteriumSonTablePane extends JPanel {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// attributes
  //private Hierarchy h;
  private Criteria c;

  // Container
  private JTable table;
  private JTableHeader tableheader;

  /**
   * Method to <code>update</code> this Panel if the criterium is changed  
   * 
   */
  public void update(){
    table.setModel(new CriteriumTableModel(c));
  }


  /**
   * Creates a new  <code>CriteriumSonTablePane</code> instance.
   * @param Criteria c
   * @param Hierarchy h
   */
  public CriteriumSonTablePane(Criteria c, Hierarchy h) {
    super(new GridLayout(2,1));
    this.c=c;
    //this.h=h;
    CriteriumTableModel myModel = new CriteriumTableModel(c);
    table=new JTable(myModel);
    tableheader= table.getTableHeader();
    this.add(tableheader);
    this.add(table);

  }

  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
    public Dimension getPreferredSize(){
      return new Dimension(400,200);
    }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
    return new Dimension(300,100);
}



  /**
   * <code>main</code> method to test this class.
   * @param Criteria :  command line
   * 
   */
  public static void main(String s[]) {
    JFrame frame = new JFrame("CriteriumSonTablePane");
    
    frame.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {System.exit(0);}
      });

    AHPExample test=new AHPExample();
    Hierarchy h=test.getHierarchyExample();
    CriteriumSonTablePane cp = new CriteriumSonTablePane((h.getGoal()).getSubCriteriaAt(0),h);	
    frame.getContentPane().add(cp);
    frame.pack();
    frame.setVisible(true);
  }
}

  // class to implement the   Table Model
  class CriteriumTableModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ATTRIBUTES
    private Vector<String> columnNames ; // mames of columns
    private Vector<Double> data; // Data
    
  /**
   * <code>getColumnCount</code> overide method here.
   *
   * @return int the number of columns
   */
    public int getColumnCount() {
      return columnNames.size();
    }
    
  /**
   * <code>getRowCount</code> overide method here.
   *
   * @return int the number of rows
   */
    public int getRowCount() {
      return 1;
    }
    
  /**
   * <code>getColumnName</code> overide method here.
   *
   * @param int the index of the column
   * @return String the name of the column
   */
    public String getColumnName(int col) {
      return (String) columnNames.get(col);
    }

  /**
   * <code>getValueAt</code> overide method here.
   * @param int row index
   * @param int column index
   * @return int the number of columns
   */
    public Object getValueAt(int row, int col) {
      String value_s =new String();
      value_s= ((Double) data.get(col)).toString();
      double v = java.lang.Double.parseDouble(value_s);
      double val = adt.util.Round.round(v, 4);
      value_s = Double.toString(val);
      return (Object) value_s;

    }
    
  /**
   * Creates a new  <code>CriteriumTableModel</code> instance.
   * @param the <code>Criterium</code>
   */
    public CriteriumTableModel(Criteria c){
      columnNames= new Vector<String>();
      data =new Vector<Double>();
      Vector children;
      
      if (c.isLl()) {
    	  children = c.getAlternatives();
    	  for (int i=0; i<children.size(); i++) {
    		  Alternative current_alt = (Alternative)children.get(i);
    		  columnNames.add(current_alt.getName());
    		  data.add(current_alt.getWeight());
    	  }
      } else {
    	  children = c.getSubCriteria();
    	  for(int i=0; i<children.size();i++){
    		  Criteria current_subcriterium=(Criteria)children.get(i);
    		  columnNames.add(current_subcriterium.getName());
    		  data.add(current_subcriterium.getWeight());
    	  }
      }
	//System.out.println("ColumnName i :"+i+"     name ="+getColumnName(i));
    }
  }
