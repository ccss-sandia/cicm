// Graphical User Interface
package gui.ahp;

//imports
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
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import gui.examples.*;

/**
 * <code>CriteriumPanel</code> the  Pane to modify the comparisons in a criterium of the  Decisionnal Hierarchy
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 9, 2003
 */
public class CriteriumPanel extends JPanel{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//ATTRIBUTS
  private Hierarchy h; // the decision Hierarchy
  //private Criteria c; // the criterium to show
  private JAHP window; // the main JAHP window
  private CriteriumModifyPanel cmp; // subpanel
  private CriteriumShowPanel csp; // subpanel


  /**
   * <code>update</code>  method to update the Panel and subpanel
   * @param Criteria c to show
   */   
  public void update(Criteria c){
    //this.c=c;
    csp.update(c);    
    this.remove(this.cmp);
    this.cmp=new CriteriumModifyPanel(c,h,csp,window);
    this.add("Center",cmp);
  }



  /**
   * Creates a new  <code>CriteriumPanel</code> instance.
   * @param Criteria c
   * @param Hierarchy h
   * @param JAHP main window   
   */
  public CriteriumPanel(Criteria c, Hierarchy h,JAHP window) {
    super(new BorderLayout());
    //this.c=c;
    this.h=h;

    this.window=window;
    this.csp = new CriteriumShowPanel(c,h);
    this.cmp = new CriteriumModifyPanel(c,h,csp,window);
    this.add("North",csp);
    this.add("Center",cmp);
      

    }


  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
    public Dimension getPreferredSize(){
      return new Dimension(800,500);
    }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
    return new Dimension(500,400);
  }



  /**
   * <code>main</code> method to test this class.
   * @param Criteria :  command line
   * 
   */
  public static void main(String s[]) {
    JFrame frame = new JFrame("CriteriumPanel");
    
    frame.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {System.exit(0);}
      });

    AHPExample test=new AHPExample();
    Hierarchy h=test.getHierarchyExample();
    CriteriumPanel panel = new CriteriumPanel((h.getGoal()).getSubCriteriaAt(0),h,null);	
    frame.getContentPane().add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
