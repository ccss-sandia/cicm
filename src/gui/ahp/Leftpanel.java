// Graphical User Interface
package gui.ahp;

//imports
import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
import java.awt.*;



// Abstract Data Type
import adt.ahp.Hierarchy;
import gui.examples.*;

/**
 * <code>Leftpanel</code> is the panel with HierarchyTree, AlternativeList and button to add/del Alternatives/Criterium
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 26, 2003 initial version
 */
public class Leftpanel extends JPanel {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//ATTRIBUTS
  //private Hierarchy h;
  //private JAHP window;
  //private CriteriaPanel csp;
  private AlternativesPanel asp;
  


  public Leftpanel(Hierarchy h,CriteriaPanel csp, AlternativesPanel asp,JAHP window) {
    super(new BorderLayout());
    //this.csp=csp;
    this.asp=asp;
    //this.window=window;
    this.add("Center",csp);
    //this.add("South",asp);
    //this.h = h;
    
  }


  public Dimension getpreferredSize(){
    return new Dimension(150,600);  
  }

  public Dimension getMinimumSize(){
    return new Dimension(150,500);  
  }


  public void updateALTERNATIVE(){
    //Systemout.println("Leftpanel update alt");
    //this.remove(asp);
    //asp=new AlternativesPanel(h,window);
    //this.add("South",asp);
    //return asp;
    asp.updateALTERNATIVE();
  }



  public static void main(String[] args) {
    // create a frame
    AHPExample test=new AHPExample();
    Hierarchy h =new Hierarchy();
    h=test.getHierarchyExample();
    JFrame mainFrame = new JFrame("Leftpanel test");
    mainFrame.setContentPane(new Leftpanel(h,new CriteriaPanel(h,null),null,null));
    mainFrame.pack();
    mainFrame.setVisible(true);
  }
}
