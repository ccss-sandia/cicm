// Graphical User Interface
package gui.ahp;


//imports
//import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
//import java.awt.*;
//import java.awt.event.*;
import java.util.*;
//import java.io.*;
//import java.net.*;

//import Jama.*;



// Abstract Data Type
//import adt.*;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
//import gui.examples.*;

/**
 * <code>HierarchyModel</code> the custom data model used by HierarchyTree (swing.Tree)
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 8, 2003
 * @version March 26, 2003
 */
public class HierarchyModel implements TreeModel{
  

  // ATTRIBUTS
  private JAHP window;
  private Criteria root;
  //private Hierarchy h;
  private HierarchyTree ht;
  private Vector<TreeModelListener> treeModelListeners = new Vector<TreeModelListener>();


  /**
   * Creates a new  <code>HierarchyModel</code> instance.
   * @param the decision <code>Hierarchy</code>
   * @param the <code>HierarchyTree</code> : a JTree
   * @param the main <code>JAHP</code> window
   */
  public HierarchyModel(Hierarchy h,HierarchyTree ht,JAHP window) {
    this.window=window;
    //this.h=h;
    this.ht=ht;
    root= h.getGoal();
    //this.addTreeModelListener(ht);
  }



    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     */
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.addElement(l);
    }

    /**
     * Removes a listener previously added with addTreeModelListener().
     */
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.removeElement(l);
    }

    /**
     * Returns the child of parent at index in the parent's child array.
     */
    public Object getChild(Object parent, int index) {
        Criteria c = (Criteria)parent;
        return c.getSubCriteriaAt(index);
    }

    /**
     * Returns the number of children of parent.
     */
    public int getChildCount(Object parent) {
        Criteria c = (Criteria) parent;
        if (c.isLl()) return c.getAlternatives().size();
        return c.getSubCriteria().size();
    }

    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object parent, Object child) {
        Criteria c = (Criteria)parent;
        return c.getIndexOfSubcriteria((Criteria)child);
    }

    /**
     * Returns the root of the tree.
     */
    public Object getRoot() {
        return root;
    }

    /**
     * Returns true if node is a leaf.
     */
    public boolean isLeaf(Object node) {
        Criteria c = (Criteria)node;
        return c.isLl();
    }


  /**
   * Add a sucriterium to the <code>Criterium</code> parent.
   */
  public Criteria addPath(Criteria parent) {
	  //System.out.println("criteria is " + parent.getName());
    Criteria new_c =new Criteria();
    //parent.addSubcriteria(new_c);
    parent.addSubcriteria(new_c);
    //ht.treeNodesInserted(new TreeModelEvent(this,parent.getParentPath())); 
    
    for(int i=0;i<treeModelListeners.size();i++){
      TreeModelListener tml= (TreeModelListener) treeModelListeners.get(i) ;
      //tme=new TreeModelEvent(Object source, Object[] path, int[] childIndices, Object[] children);
      tml.treeStructureChanged(new TreeModelEvent(this,new TreePath(parent)));
      //treeNodesInserted(TreeModelEvent e)

	  //    Invoked after nodes have been inserted into the tree.
	  //Use e.getPath() to get the parent of the new node(s). e.getChildIndices() returns the index(es) of the new node(s) in ascending order.


    }
    //fireTableRowsInserted(parent.getNumAlternatives()-1,parent.getNumAlternatives()-1);
    //fireTreeNodesInserted(parent,(h.getGoal()).getPath(), int[] childIndices, Object[] children)
      //Notifies all listeners that have registered interest for notification on this event type.
        window.updateafteraddCRITERIUM(new_c);
    return new_c;
  }

  /**
   * Remove the <code>Criterium</code> c.
   */
  public void removeNodeFromParent(Criteria c) {//last but not least
	Criteria parent = c.getParent();
    parent.delCriteria(c);
    for(int i=0;i<treeModelListeners.size();i++){
      TreeModelListener tml= (TreeModelListener) treeModelListeners.get(i) ;
      //tme=new TreeModelEvent(Object source, Object[] path, int[] childIndices, Object[] children);
      //          Used to create an event when nodes have been changed, inserted, or removed, 
      //identifying the path to the parent of the modified items as an array of Objects.
      //public void treeNodesRemoved(TreeModelEvent e)

	//Invoked after nodes have been removed from the tree. Note that if a subtree is removed from the tree, 
	//this method may only be invoked once for the root of the removed subtree, not once for each individual set of siblings removed.
	//Use e.getPath() to get the former parent of the deleted node(s). e.getChildIndices() returns, 
	//in ascending order, the index(es) the node(s) had before being deleted.

      tml.treeStructureChanged(new TreeModelEvent(this,new TreePath(parent)));
      window.updateafterdelCRITERIUM();

    }
  }


  /**
   * Messaged when the user has altered the value for the item
   * identified by path to newValue. 
   */
    public void valueForPathChanged(TreePath path, Object newValue) {
      
        //Systemout.println("*** valueForPathChanged : "
      //           + path + " --> " + newValue);
	Criteria c= (Criteria) ht.getLastSelectedPathComponent();
	c.setName((String)newValue);
	window.updateaftermodifyCRITERIUM();
	  }



}
