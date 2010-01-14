// Graphical User Interface
package gui.ahp;

import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.*;
//import java.io.*;
//import java.net.*;

//import Jama.*;


// Abstract Data Type
//import adt.*;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import gui.examples.*;


/**
 * <code>HierarchyTree</code> the custom  swing.Tree
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 8, 2003
 * @version March 26, 2003
 */
public class HierarchyTree extends JTree implements TreeSelectionListener, ActionListener {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//ATTRIBUTS
  private Hierarchy h;
  private JAHP window;
  private HierarchyModel hm;


  /**
   * Creates a new  <code>HierarchyTree</code> instance.
   * @param the decision <code>Hierarchy</code> 
   * @param the main <code>JAHP</code> window
   */
    public HierarchyTree(Hierarchy h,JAHP window) {
        super();
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setEditable(true);

        this.h=h;
        this.window=window;
        this.hm=new HierarchyModel(h,this,window);
        this.setModel(hm);

        //	Listen for when the selection changes.
        this.addTreeSelectionListener(this);

        // Look and Feel
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        Icon personIcon = null;
        renderer.setLeafIcon(personIcon);//Icon
        renderer.setClosedIcon(personIcon);//Icon
        renderer.setOpenIcon(personIcon);//Icon
        setCellRenderer(renderer);
        
        final JPopupMenu menu = new JPopupMenu();
        
        // Create and add a menu item
        JMenuItem item = new JMenuItem("Edit");
        item.addActionListener(this);
        menu.add(item);
        
        // Set the component to show the popup menu
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    menu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    menu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });
        
        
      
    }


  ////////////////////////Events
  
  /**
   * <code>valueChanged</code> method invoked when url a new element in JTree is selected
   *
   * @param e a <code>ActionEvent</code> value
   */
  public void valueChanged(TreeSelectionEvent e) {
    //Systemout.println("HierarchyTree valueChanged"); 
	try {
		Criteria node = (Criteria) this.getSelectionPath().getLastPathComponent();
//	    Criteria node = (Criteria) getLastSelectedPathComponent();
	    if (node == null) return;	      	      
	    // show the criterium in the Main Panel
	    //System.out.println("New Hierarchy value :"+node.toString());    
	    window.updateSHOWCRITERIUM(node);
	    //System.out.println("value changed"+node.getName());

	} catch (NullPointerException ex) {}
  }
  
  public void actionPerformed(ActionEvent e) {
	  //System.out.println("clicked edit");
  }
  

  /*
   * Delete a node in the JTree
   */
  public void delNode() {	  
    Criteria currentNode = null;
    //Criteria parentNode = null;
    TreePath currentSelection = this.getSelectionPath();
    if (currentSelection == null) {
      //System.out.println("parentPath null");
    } else {      
      currentNode= (Criteria) currentSelection.getLastPathComponent();
	  int response;

      response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the performance measure " + currentNode.getName() + " and everything below it?", "Delete Performance Measure", JOptionPane.OK_CANCEL_OPTION);  	  
   //   System.out.println("response is " + response);
      if (response == 0) {  // yes
    	  System.out.println("current node is " + currentNode);      
      //parentNode= (Criterium) (currentNode.getFather());
//System.out.println("parent is " + currentNode.getParent().getName());
    	  if (currentNode.getParent() != null){   	  
    		  hm.removeNodeFromParent(currentNode);
    		  // recalculate the weights
    		  h.calculateWeights(h.getGoal());
    		  
    	  }
      }
    }
  }

  /*
   * Add a node in the JTree
   */
  public void addNode(String pm, String desc) {
    Criteria parentNode = null;
    Criteria childNode = null;
    TreePath parentPath = this.getSelectionPath();
    if (parentPath == null) {
      parentNode = h.getGoal();
      childNode=hm.addPath(parentNode); 
      childNode.setParent(parentNode);
      parentPath = this.getPathForRow(0);
      
    } 
    else {
      parentNode = (Criteria) parentPath.getLastPathComponent();
      childNode=hm.addPath(parentNode); 
      childNode.setParent(parentNode);
    }
    childNode.setName(pm);
    childNode.setComment(desc);
    
    // recalculate the weights
    h.calculateWeights(h.getGoal());
    
    TreePath newPath = parentPath.pathByAddingChild(childNode);
    this.scrollPathToVisible(newPath);      
    this.setSelectionPath(newPath);
    
  }
  
  public Criteria getParentPath() {
	 return (Criteria) this.getSelectionPath().getLastPathComponent();
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
   * @param Criteria :  command line
   * 
   */
 public static void main(String[] args) {
    // create a frame
    AHPExample test=new AHPExample();
    Hierarchy h =new Hierarchy();
    h=test.getHierarchyExample();
    JFrame mainFrame = new JFrame("CriteriaPanel test");
    mainFrame.setContentPane(new HierarchyTree(h,null));
    mainFrame.pack();
    mainFrame.setVisible(true);
  }

}
