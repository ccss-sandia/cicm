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
 * <code>CriteriumModifyPanel</code> the Pane to modify the comparisons in a criterium of the  Decisionnal Hierarchy
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 26, 2003
 */
public class CriteriumModifyPanel extends JSplitPane{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Hierarchy h;
  //private Criteria c;
  private int size;

  //private CriteriumShowPanel csp;

  ScrollPaneLayout spl;
  JPanel labels;
  JScrollPane sliders;
  //private JAHP window ;

  /**
   * Creates a new  <code>CriteriumModifyPanel</code> instance.
   * @param Criteria c
   * @param Hierarchy h
   * @param CriteriumShowPanel csp
   */
  public CriteriumModifyPanel(Criteria c, Hierarchy h,CriteriumShowPanel csp,JAHP window) {
    super(JSplitPane.HORIZONTAL_SPLIT);
    //this.c=c;
    //this.h=h;
    //this.csp=csp;
    //this.window=window;
    
    
    this.sliders = new JScrollPane();
    this.spl=new ScrollPaneLayout();
    spl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    spl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    sliders.setLayout(spl);
    //this.add(sliders, BorderLayout.CENTER);
    
    JPanel content=new JPanel(new FlowLayout());
    (sliders.getViewport()).add(content);

    this.labels = new JPanel();
    
    
    if (c!=null){
    	int n = 0;
    	if (c.isLl()) {
    		n = c.getAlternatives().size();
    	} else {
    		n = c.getSubCriteria().size();
    	}
    	this.size=n;
    	//System.out.println("NB SONS"+n);
    	n=n*(n-1)/2;
    	int k=0;
    	//System.out.println("NB PairwiseComparison"+n);
    	ComparisonPane current_pane;
    	for(int i=0;i<this.size;i++){
    		for(int j=i+1; j<this.size;j++){
    			//System.out.println("i : "+i+"   j : "+j + "  n: "+ n + "  k: "+ k);	  
    			
    			if (k<n) {
    				current_pane= new ComparisonPane(c,i,j,false,csp,window);
    				content.add(current_pane);
    			}//Paint label only for the last JSlider
	  
    			k++;
    		}
    	}
		//(Criteria c, int i, int j,boolean last,CriteriumShowPanel csp,JAHP window)
		current_pane= new ComparisonPane(null,0,0,true,csp,window);
		labels.add(current_pane);

    }
    //labels.setSize(100,100);
    //this.add(labels, BorderLayout.EAST);
    
    this.setLeftComponent(sliders);
    this.setRightComponent(labels);

    //this.setDividerLocation(450);
    this.setResizeWeight(0.9);
    

//    Provide minimum sizes for the two components in the split pane
    Dimension minimumSize = new Dimension(100, 50);
    sliders.setMinimumSize(minimumSize);
    labels.setMinimumSize(minimumSize);
    
  }

  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
    public Dimension getPreferredSize(){
      return new Dimension(100*(size-1)+500,400);
    }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
    return new Dimension(100*(size-1)+500,350);
}

  /**
   * <code>main</code> method to test this class.
   * @param Criteria :  command line
   * 
   */
  public static void main(String s[]) {
    JFrame frame = new JFrame("CriteriumPane");
    
    frame.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {System.exit(0);}
      });

    AHPExample test=new AHPExample();
    Hierarchy h=test.getHierarchyExample();
    CriteriumModifyPanel panel = new CriteriumModifyPanel((h.getGoal()).getSubCriteriaAt(0),h,null,null);	
    frame.getContentPane().add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
