// Graphical User Interface
package gui.ahp;

//imports
import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;
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
 * <code>CriteriumShowPanel</code> the  Pane to show  a criterium 
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version March 26, 2003
 * @version July 21, 2003
 */
public class CriteriumShowPanel extends JPanel implements DocumentListener{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//ATRIBUTS
  private Hierarchy h; // The decision hierarchy
  private Criteria c; // the Criterium to show
  //private JLabel l_ir; // inconsistency ration view
  private CriteriumSonTablePane table;// table to show Criteria

  private JTextArea tf_comment;// comment area
  private PlainDocument whatsup;// document area



  /**
   * <code>update</code>  method to update the Panel 
   * @param Criteria c to show
   */   
  public void update(Criteria c){
    this.c=c;
    //System.out.println("new name of criterium :"+c.getName());    
    //tf_comment.setText((c.getComment()).toString());
    //whatsup=new PlainDocument();
    //try{ whatsup.insertString(0,c.getComment(), null); }
    //catch (BadLocationException e) {System.err.println("Bad Location Exception "+e);}
    //tf_comment=new JTextArea(whatsup);
    this.remove(table);
    table=new CriteriumSonTablePane(c,h);
    this.add(table);//updateWEIGHT();
  }

  /**
   * <code>update</code>  method to update the Panel 
   */   
  public void updateWEIGHT(){
    //System.out.println("repaint");    
    table.update();
    //double value= c.getWeight();
//    (((c.getP()).getInconsistencyRatio())*100);
    //double v = java.lang.Double.valueOf(value);
    //double val = adt.util.Round.round(v, 4);
    //String value_s = Double.toString(val);    
    
    //String value_s =new String(value.toString());
    //if(value_s.length()>6){
    //  value_s = value_s.substring(0,5);}	        
    //if (!(c.getP()).isConsistency()) l_ir.setText(("<html><font color=red>"+value_s+"%</font></html>"));
    //else l_ir.setText(("<html><font color=green>"+value_s+"%</font></html>"));
    //Systemout.println("size PairwiseComparisonMatrix"+(c.getP()).getSize());
    
    //System.out.println("the new criterium");    
    //System.out.println(c.print());
    
  }

  /**
   * Creates a new  <code>CriteriumShowPanel</code> instance.
   * @param Criteria c
   * @param Hierarchy h
   */
  public CriteriumShowPanel(Criteria c, Hierarchy h) {
    super(new GridLayout(0,1));
    this.c=c;
    this.h=h;
    
    //
    if (c!=null){
    JPanel top=new JPanel(new GridLayout(0,1));
    table=new CriteriumSonTablePane(c,h);
    //JPanel down=new JPanel(new FlowLayout());
   
    //top
    top.add(new JLabel(c.getName() + " Description: "));
    whatsup=new PlainDocument();
    try{ whatsup.insertString(0,c.getComment(), null); }
    catch (BadLocationException e) {System.err.println("Bad Location Exception "+e);}
    tf_comment=new JTextArea(whatsup,null,20,5);
    tf_comment.setCaretPosition(tf_comment.getText().length());
    tf_comment.setToolTipText("Click here to edit " + c.getName() + " description.");
    JScrollPane scrollPane = new JScrollPane(tf_comment);
    whatsup.addDocumentListener(this);
    top.add(scrollPane);
    
    
    //down
    //down.add(new JLabel("Inconsisteny Ratio"));

    //Double value= new Double((c.getP()).getInconsistencyRatio()*100);
    //String value_s =new String(value.toString());
    //if(value_s.length()>6){
    //  value_s = value_s.substring(0,5);}	        
    //if (!(c.getP()).isConsistency()) l_ir=new JLabel(("<html><font color=red>"+value_s+"%</font></html>"));
    //else l_ir= new JLabel(("<html><font color=green>"+value_s+"%</font></html>"));
    //down.add(l_ir);

    //center
    //     int n=c.getNb_sons();
    //     System.out.println("NB SONS"+n);
    //     for(int i=0;i<c.getNb_sons();i++){    
    //     }
    this.add(top);    
    this.add(table);
    //this.add(down);
    }
  }

  /**
   * Describe <code>getPreferredSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
    public Dimension getPreferredSize(){
      return new Dimension(350,150);
    }

  /**
   * Describe <code>getMinimumSize</code> method here.
   *
   * @return a <code>Dimension</code> value
   * @see  <code>Container</code>
   */
  public Dimension getMinimumSize(){
    return new Dimension(350,150);
}


  /**
   * <code>updateComment</code> method invoked when comment is changed
   *
   * @param e a <code>DocumentEvent</code> value
   */
  public void updateComment(DocumentEvent e) {
    //JTextField source = (JTextField)e.getSource();
    String text=new String() ;
    try {text=whatsup.getText(0,whatsup.getLength());}
    catch (BadLocationException f) {System.out.println("Bad Location Exception "+f);}  
    c.setComment(text);
    
  }

  /**Gives notification that there was an insert into the document.*/
  public void insertUpdate(DocumentEvent e){
    updateComment(e);
  }

  /**Gives notification that a portion of the document has been removed.*/
  public void removeUpdate(DocumentEvent e){
    updateComment(e);
  }

  /** Gives notification that an attribute or set of attributes changed. */
  public void changedUpdate(DocumentEvent e){
    updateComment(e);
  }

  /**
   * <code>main</code> method to test this class.
   * @param Criteria :  command line
   * 
   */
  public static void main(String s[]) {
    JFrame frame = new JFrame("CriteriumShowPanel");
    
    frame.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent e) {System.exit(0);}
      });

    AHPExample test=new AHPExample();
    Hierarchy h=test.getHierarchyExample();
    CriteriumShowPanel cp = new CriteriumShowPanel((h.getGoal()).getSubCriteriaAt(0),h);	
    frame.getContentPane().add(cp);
    frame.pack();
    frame.setVisible(true);
  }
}
