package gui.cmt;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.*;

import java.io.*;
import java.awt.event.*;

import adt.cmt.*;

/**
 * <code>CMTCustomerGroupsPanel</code> is the panel that contains the 
 * customer groups, cost rates, and average usage rates.
 */

public class CMTCustomerGroupsPanel extends JPanel implements ActionListener{

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private CMTCustomerGroupsTable cgt; // the table to show Customer Groups
  private JButton add; // a button to add a Customer Group
  private JButton del;// a button to del a Customer Group

  /**
   * Creates a new  <code>CMTCustomerGroupPanel</code> instance.
   * @param the main <code>CMT</code> window
   */
  public CMTCustomerGroupsPanel(Vector customerGroups, CMTSystem s) {
    super(new BorderLayout());

    cgt =new CMTCustomerGroupsTable(customerGroups, s);
    
    JScrollPane toppane = new JScrollPane(cgt);
    this.add("Center",toppane);

    // JButton to add and delete customer groups
    JPanel modifypanel =new JPanel(new GridLayout(0,1));
    add=new JButton("Add Customer Group");
    del=new JButton("Delete Customer Group");
    modifypanel.add(add);
    modifypanel.add(del);
    this.add("South",modifypanel);

    //add ActionListener to the buttons
    add.addActionListener(this);
    del.addActionListener(this);
  }


  /**
   * a <code>actionPerformed</code> method here.
   * @param the <code>ActionEvent</code>
   */
  public void actionPerformed(ActionEvent e){
    if (e.getSource()==add){
    	
      CMTAddCustomerGroupDialog d = new CMTAddCustomerGroupDialog();
      d.pack();
      d.setVisible(true);
      
      //System.out.println("Add Customer Group");      
      cgt.addRow(d.getName(), d.getRate(), d.getUsage());
    }
    else{
    	
      //System.out.println("Del Customer Group");
      cgt.delRow();
    }
  }

  /**
   * a <code>updateCustomerGroup</code> method to update this panel.
   * @param the <code>ActionEvent</code>
   */
  public void updateCustomerGroup(){
    cgt.updateCustomerGroup();
  }

  

  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
    public Dimension getPreferredSize(){
      return new Dimension(150,200);
    }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
    return new Dimension(150,200);
}


  /**
   * <code>main</code> method to test this class.
   * @param command line
   * 
   */
  public static void main(String s[]) {
    JFrame frame = new JFrame("Customer Group Panel");
    
    frame.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {System.exit(0);}
      });
    
    Vector customerGroups = new Vector();
    CustomerGroup cg1 = new CustomerGroup("Residential", .0868, 1.25);
    CustomerGroup cg2 = new CustomerGroup("Commercial", .0794, 8.25);
    CustomerGroup cg3 = new CustomerGroup("Sm/Med Industrial", .0506, 20);
    CustomerGroup cg4 = new CustomerGroup("Lg Industrial", .0506, 1000);
    customerGroups.add(cg1);
    customerGroups.add(cg2);
    customerGroups.add(cg3);
    customerGroups.add(cg4);
    
    CMTCustomerGroupsPanel cgp = new CMTCustomerGroupsPanel(customerGroups,null);	
    frame.getContentPane().add(cgp);
    frame.pack();
    frame.setVisible(true);
  }
}
