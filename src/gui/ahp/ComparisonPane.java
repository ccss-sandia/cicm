// Graphical User Interface
package gui.ahp;
//imports
import gui.examples.*;

import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
import javax.swing.event.*;
//import javax.swing.tree.*;



import java.awt.*;
import java.awt.event.*;
import java.util.*;
//import java.io.*;
//import java.net.*;

//import Jama.*;

// Data Type
//import adt.*;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import adt.ahp.PairwiseComparisonMatrix;
import adt.util.Round;

/**
 * <code>ComparisonPane</code> the  Pane to show  and modify a comparision   
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 18, 2003
 * @version March 26, 2003
 */
public class ComparisonPane extends JPanel implements ChangeListener{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Criteria c;
  private int i;
  private int j;
  private JSlider slide;
  private boolean last;

  private CriteriumShowPanel csp;

  private JAHP window;

  public ComparisonPane(Criteria c, int i, int j,boolean last,CriteriumShowPanel csp,JAHP window) {
      super(new BorderLayout());
      this.c=c;
      this.i=i;
      this.j=j;
      this.last=last;
      this.csp=csp;
      this.window=window;
      
      //Create the label table with title
      Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
      labelTable.put( new Integer( 8 ), new JLabel("9 - EXTREMELY") );
      labelTable.put( new Integer( 7 ), new JLabel("8 ") );
      //labelTable.put( new Integer( 7 ), new JLabel("8 Intermediate between VERY STRONGLY and EXTREMELY") );
      labelTable.put( new Integer( 6 ), new JLabel("7 - VERY STRONGLY") );
      labelTable.put( new Integer( 5 ), new JLabel("6 ") );
      //labelTable.put( new Integer( 5 ), new JLabel("6 Intermediate between STRONGLY and VERY STRONGLY") );
      labelTable.put( new Integer( 4 ), new JLabel("5 - STRONGLY") );
      labelTable.put( new Integer( 3 ), new JLabel("4 ") );
      //labelTable.put( new Integer( 3 ), new JLabel("4 Intermediate between SLIGHTLY and STRONGLY") );
      labelTable.put( new Integer( 2 ), new JLabel("3 - SLIGHTLY"));
      labelTable.put( new Integer( 1 ), new JLabel("2 "));//up
      //labelTable.put( new Integer( 1 ), new JLabel("2 Intermediate between EQUALLY and SLIGHTLY"));//up
      labelTable.put( new Integer( 0 ), new JLabel("1 - EQUALLY") );
      labelTable.put( new Integer( -1 ), new JLabel("2 "));//down
      //labelTable.put( new Integer( -1 ), new JLabel("2 Intermediate between EQUALLY and SLIGHTLY"));//down
      labelTable.put( new Integer( -2 ), new JLabel("3 - SLIGHTLY"));
      labelTable.put( new Integer( -3 ), new JLabel("4 ") );      
      //labelTable.put( new Integer( -3 ), new JLabel("4 Intermediate between SLIGHTLY and STRONGLY") );
      labelTable.put( new Integer( -4 ), new JLabel("5 - STRONGLY") );
      labelTable.put( new Integer( -5 ), new JLabel("6 ") );      
      //labelTable.put( new Integer( -5 ), new JLabel("6 Intermediate between STRONGLY and VERY STRONGLY") );
      labelTable.put( new Integer( -6 ), new JLabel("7 - VERY STRONGLY") );
      labelTable.put( new Integer( -7 ), new JLabel("8 ") );
      //labelTable.put( new Integer( -7 ), new JLabel("8 Intermediate between VERY STRONGLY and EXTREMELY") );
      labelTable.put( new Integer( -8 ), new JLabel("9 - EXTREMELY") );

      int n = 0;
      if (c != null) {
    	  if (c.isLl()) {
    		  n = c.getAlternatives().size();
    	  } else {
    		  n = c.getSubCriteria().size();
    	  }
      
    	  //System.out.println("NB SONS"+n);
    	  //System.out.println(c.getName() + " -- " + c.getComparisonMatrix().get(i,j));
    	  int num = (int) Round.round(c.getComparisonMatrix().get(i,j), 0) - 1;
    	  int flipNum = (int) -(Round.round(c.getComparisonMatrix().get(j,i), 0)) + 1;
    	  //System.out.println("i : "+i+"   j : "+j+"      n : "+n + "    num: " + num + "   flip: " + flipNum);
    	  if (c.getComparisonMatrix().get(i, j) < 1) {
    		  slide= new JSlider (JSlider.VERTICAL, -8,8, flipNum);
    	  } else {
    		  slide= new JSlider (JSlider.VERTICAL, -8,8, num);
    	  }
      } else {
		  slide= new JSlider (JSlider.VERTICAL, -8,8, 0);   
		  slide.setPaintTrack(false);
		  slide.setEnabled(false);

      }
      slide.setLabelTable( labelTable );
      slide.addChangeListener(this);
      slide.setMajorTickSpacing(2);
      slide.setMinorTickSpacing(1);
      slide.setPaintTicks(true);
      slide.setSnapToTicks(true);
      
      if (last) {slide.setPaintLabels(true); }//Paint label only for the last JSlider
      else slide.setPaintLabels(false);
      slide.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
      
      String top_title; 
      String down_title;
      if (c != null) {
    	  if (c!= null && c.isLl()) {
    		  top_title= new String((c.getAlternativeAt(i)).getName());
    		  down_title = new String((c.getAlternativeAt(j)).getName());
    	  }
    	  else{
    		  top_title= new String((c.getSubCriteriaAt(i)).getName());
    		  down_title = new String((c.getSubCriteriaAt(j)).getName());
    	  }
      } else {
		  top_title= new String("Choice 1");
		  down_title = new String("Choice 2");    	  
      }
      

      
      this.add("North",new JLabel("<html><font color=red>"+top_title+"</font></html>"));
      this.add("Center",slide);
      this.add("South",new JLabel("<html><font color=red>"+down_title+"</font></html>"));
  }

  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
    public Dimension getPreferredSize(){
      if (last) return new Dimension(400,350);
      return new Dimension(100,350);
    }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
      if (last) return new Dimension(400,350);
      return new Dimension(100,350);
  }



  /**
   * <code>stateChanged</code> method invoked when slider value is changed
   *
   * @param e a <code>ChangeEvent</code> value
   */
  public synchronized void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider)e.getSource();
    if (c!= null) {
    	if (!source.getValueIsAdjusting()) {
    		int val = (int)source.getValue();
    		//System.out.println("comparison value "+val);
    		PairwiseComparisonMatrix p=c.getComparisonMatrix();
    		if (val==0){
    			p.set(this.i,this.j,1);
    			//System.out.println("fraction is 1/1");
    			//System.out.println(p.toString());
    		}else if (val<0){	
    			p.set(this.i,this.j,1.0/(-val+1.0));	
    			//System.out.println("fraction is 1/" + (-val+1.0));
    			//System.out.println(p.toString());
    		}else if (val>0){	
    			p.set(this.i,this.j,(val+1));
    			//System.out.println("fraction is " + (val + 1));
    			//System.out.println(p.toString());
    		}

      
    		// calculate the weights
    		if (c.getParent() != null) {
    			Hierarchy.calculateWeights(c.getParent());
    		} else {
    			Hierarchy.calculateWeights(c);
    		}
    		// repaint
      
    		//System.out.println("updateTABLE");     
    		csp.updateWEIGHT();
    		//System.out.println("*********************************************state changed*********************");
    		window.updateALTERNATIVE();
    	}
    }
  }



  public static void main(String s[]) {
    JFrame frame = new JFrame("ComparisonPane");
    
    frame.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {System.exit(0);}
      });

    AHPExample test=new AHPExample();
    Hierarchy h=test.getHierarchyExample();
    ComparisonPane panel = new ComparisonPane((h.getGoal()),0,1,false,new CriteriumShowPanel(h.getGoal(),h),null);	
    frame.getContentPane().add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
