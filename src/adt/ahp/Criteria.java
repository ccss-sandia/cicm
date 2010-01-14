// Abstract Data Type
package adt.ahp ;

//imports
//import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;
//import java.awt.*;
//import java.awt.event.*;
import java.util.*;
import java.io.*;
//import java.net.*;


/**
 * Criterium class
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version Fev 13, 2003
 * @version March 24, 2003  final one
 * @version July 21, 2003
 */
public class Criteria extends Activity implements Serializable,Cloneable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//ATTRIBUTES

  //useful to have always a new criteria with a new name 
 private static int newCriteriaSuffix = 1;

  // Is this criteria is in the lowest level?
  private boolean ll = true;

  //link with other Objects
  private Criteria parent;
  private Vector<Activity> children ;
 
  // The associated PairwiseComparisonMatrix
  private PairwiseComparisonMatrix comparisonMatrix;
  

  /**
   * Creates a new <code>Criteria</code> instance.
   *
   */
  public Criteria() {
	  super("My criteria"+newCriteriaSuffix++);
	  this.children = new Vector<Activity>();
	  this.setComparisonMatrix(new PairwiseComparisonMatrix(0, ll)); 	  
  }
  public Criteria (String name) {
	  super(name);
	  this.children = new Vector<Activity>();
	  this.setComparisonMatrix(new PairwiseComparisonMatrix(0, ll));  	  
  }
  public Criteria(String name, String comment){
    super(name, comment);
    this.children = new Vector<Activity>();
    this.setComparisonMatrix(new PairwiseComparisonMatrix(0, ll));  
  }
  
  public Criteria(String name, Criteria parent) {
	  super(name);
	  this.children = new Vector<Activity>();
	  this.parent = parent;
	  this.setComparisonMatrix(new PairwiseComparisonMatrix(0, ll)); 	
  }
  
  public Criteria(String name, String comment, Criteria parent) {
	  super(name, comment);
	  this.children = new Vector<Activity>();
	  this.parent = parent;
	  this.setComparisonMatrix(new PairwiseComparisonMatrix(0, ll)); 	  
	  
  }
  
  
  /**
   * Get the value of ll.
   * @return value of ll.
   */
  // parent will either be a goal or criteria
  public boolean isLl() {
    return ll;
  }
  
  public void setLl(boolean ll) {
	  this.ll = ll;
  }
    
  /**
   * Get the value of parent.
   * @return value of parent.
   */
  // parent will either be a goal or criteria
  public Criteria getParent() {
    return parent;
  }
  
  /**
   * Set the value of parent.
   * @param v  Value to assign to parent.
   */
  // parent will either be a goal or criteria
  public void setParent(Criteria  v) {
    this.parent = v;
  }
  
  /**
   * Gets the number of children
   * @return the number of children
   */
  private int getNumChildren() {
    return children.size();
  }
  

  /**
   * Gets the number of alternatives
   * @return the number of alternatives
   */
  public int getNumAlternatives() {
	  if (ll) return children.size();
	  else return 0;
  }
  

  /**
   * Gets the number of subcriteria
   * @return the number of subcriteris
   */  
  public int getNumSubCriteria() {
	  if (ll) return 0;
	  if (children.size() > 0)
	   return children.size();
	  else return 0;
  }

  /**
   * Sets the subcriteria
   * @param v the vector of subcriteria
   */   
  @SuppressWarnings("unchecked")
public void setSubCriteria(Vector v){
	  this.ll = false;
	  this.children = v; 
	  this.comparisonMatrix = (new PairwiseComparisonMatrix(v.size(), ll));
  }
  
  /**
   * Gets the subcriteria
   * @return the vector of subcriteria
   */  
  public Vector getSubCriteria() {
	  if (this.ll) return null;
	  return this.children;
  }
  
  /**
   * Sets the alternatives
   * @param v the vector of alternatives
   */     
  @SuppressWarnings("unchecked")
public void setAlternatives(Vector v){ 
	  this.ll = true;
	  this.children = v;
	  //System.out.println("size of comparison matrix is " + this.comparisonMatrix.getSize());
	  if (this.comparisonMatrix.getSize() == 0) {
//System.out.println("size is 0");		  
		  this.comparisonMatrix = (new PairwiseComparisonMatrix(v.size(), ll));
	  } else {
		  this.comparisonMatrix.addElement();
//System.out.println("size should be one bigger now " + this.comparisonMatrix.getSize());
	  }
  }
  
  /**
   * Gets thealternatives
   * @return the vector of alternatives
   */  
  public Vector<Activity> getAlternatives() {
	  if (this.ll) return this.children;
	  return null;
  }
  
  /**
   * Gets the value of comparisonMatrix
   * @return the value of comparisonMatrix
   */
  public PairwiseComparisonMatrix getComparisonMatrix() {
    return this.comparisonMatrix;
  }
  
  /**
   * Sets the value of comparisonMatrix
   * @param comparisonMatrix Value to assign to this.comparisonMatirx
   */
  public void setComparisonMatrix(PairwiseComparisonMatrix comparisonMatrix){
    this.comparisonMatrix = comparisonMatrix;
  }

  /**
   * <code>print</code> Returns a string representation of this Criteria, containing the String representation of each element.
   * Useful to debug
   * @return a <code>String</code> value
   */
  public String print(){
    String s=new String();

    if (parent == null) {
        s=s+"Name of this goal           : "+getName()+"\n";  	
    }
    else {
        s=s+"Name of this criteria       : "+getName()+"\n";
        s=s+"The name of parent          : "+getParent().getName()+"\n";    	
    }
    s=s+"Number of children          : "+getNumChildren()+"\n";
    s=s+"Weight                      : "+getWeight() + "\n";
    s=s+"Pairwise Comparison Matrix  : "+"\n"+getComparisonMatrix().toString();

    Vector v=new Vector();
    
    if (ll) {
    	v = getAlternatives();

    	for(int i=0;i<getNumChildren();i++){
    	    s=s+"Child " + (i+1) + ": \n";
    		Alternative a = (Alternative) v.get(i);
    		s=s+a.print(); 
    	}
    } else {
    	v = getSubCriteria();
    	
    	for(int i=0; i < getNumChildren(); i++){
    	    s=s+"Child " + (i+1) + ": \n";    		
    		Criteria c = (Criteria) v.get(i);
    		s = s+c.print();
    	}
    }
    return s;
  }

  /**
   * <code>getSubCriteriaAt</code> Returns the ith Subcriteria
   * @param a <code>int</code> value : the index
   * @return a <code>Criteria</code> value 
   * @exception IllegalArgumentException e "not found..."
   */
  public Criteria getSubCriteriaAt(int i) {
    if (this.ll) throw new IllegalArgumentException("The ith subcriteria of a criteria in the least level can not be found");
    return (Criteria)children.elementAt(i);
    }

  /**
   * <code>getIndexOfSubcriteria</code> returns the index of Subcriteria
   * @param a <code>Criteria</code> value 
   * @return a <code>int</code> value : the index
   * @exception IllegalArgumentException e  "not found..."
   */
  public int  getIndexOfSubcriteria(Criteria c) {
    if (this.ll) throw new IllegalArgumentException("The ith subcriteria of a criteria in the least level can not be found");
    return children.indexOf(c);
  }

  /**
   * <code>getAlternativeAt</code> Returns the ith Alternative
   * @param a <code>int</code> value : the index
   * @return a <code>Alternative</code> value
   * @exception IllegalArgumentException e  "nt found..."
   */
  public Alternative getAlternativeAt(int i) {
    if (!this.ll) throw new IllegalArgumentException("The ith alternative of a criteria not in the least level can not be found");
    return (Alternative) children.elementAt(i);
    }

  /**
   * <code>getIndexOfAlternative</code> Returns the index of Alternative
   * @param a <code>Alternative</code> value 
   * @return a <code>int</code> value : the index
   * @exception IllegalArgumentException e "not found..."
   */
  public int  getIndexOfAlternative(Alternative a) {
    if (this.ll) throw new IllegalArgumentException("The ith alternative of a criteria not in the least level can not be found");
    return children.indexOf(a);
  }



  
  
  
  //************************************
  //
  //Method to modify hierarchy
  // 
  // 
  //************************************* 

  /**
   * <code>addAlternative</code> method here.
   *
   * @param alt <code>Alternative</code> value
   */
  public void addAlternative(Alternative alt) {
    Vector<Activity> alts;
	if (this.getNumAlternatives() == 0) {
    	// this criteria does not have any alternatives
    	alts = new Vector<Activity>();
    }
    else {  
    	// this criteria already has alternatives
    	
    	// get the vector of alternatives
    	alts = this.getAlternatives();
    }
    alts.add(alt);
	this.setAlternatives(alts);
    
  }


  /**
   * <code>addSubcriteria</code> method here.
   *
   * @param Criteria c which should be added
   */
  public void addSubcriteria(Criteria c){  
      if (this.getNumChildren() == 0){
System.out.println("adding new one");    	  
    	  // criteria does not have any subcriteria
    	  setLl(false);
    	  children=new Vector<Activity>();
    	  children.add(c);
    	  this.comparisonMatrix=new PairwiseComparisonMatrix(1, false);
      }
      else{
System.out.println("adding to existing");    	  
    	  // criteria already has subcriteria
    	  children.add(c);
    	  this.comparisonMatrix = new PairwiseComparisonMatrix(children.size(), false);
      }
  }


  /**
   * <code>equals</code> method here.
   * @return boolean
   * @param Criteria c
   *
   */
    public boolean equals(Criteria c){
    	return name.equals(c.getName());
    }

  /**
   * <code>isNew</code> method here.
   *
   * @param Criteria c
   */
  public boolean isNew(Criteria c){
    if (name.equals(c.getName())) return false;
    if (ll) return true;
    for (int i=0;i<getNumChildren();i++) {
      Criteria child= (Criteria)children.get(i);
      if (!child.isNew(c)) return false;
    }
    return true;

  }


  //************************************
  //
  //Method to calculate solutions
  // 
  // 
  //************************************* 

  /**
   * <code>Jstar</code> method here.
   *
   * @param index the index of the alternative 
   * @return double the global importance of the alternative according to the criteria
   */
//  public double Jstar(int index){
//    if (isLl()) return J(index);
//    double sum=0.0;
//    for(int i=0;i<nb_sons;i++){
//      Criteria son=(Criteria) sons.get(i);
//      sum+=son.Jstar(index)*p.getWeight(i);
//    }
//    return sum;
//  }

  /**
   * <code>J</code> method here.
   *
   * @param index the index of the alternative 
   * @return double the global importance of the alternative according to the criteria in the lowest level
   * @exception IllegalArgumentException
   */
//  public double J(int index){
 //   if (!isLl()) throw new IllegalArgumentException("J can not be calculated for a criteria which is not in the lowest level");
 //   return p.getWeight(index);
//  }

  /**
   * <code>I</code> method here.
   * @param Criteria c :  the index of the alternative
   * @return double  : the value of the alternative according to the criteria
   */
//  public double I(Criteria c){
//    if (ll) return(0.0);
//    for(int i=0;i <nb_sons;i++){
//      Criteria son=(Criteria) sons.get(i);
//      if (son.equals(c)) return p.getWeight(i);
//    }
//    double sum=0.0;
//    for(int i=0;i <nb_sons;i++){
//      Criteria son=(Criteria) sons.get(i);
//      sum+=p.getWeight(i)*son.I(c);
//    }
//    return(sum);
//  }


  //************************************
  //
  //Method to modify hierarchy
  // 
  // 
  //************************************* 


  /**
   * <code>delAlternative</code> method here.
   * @param int :  the index of the alternative which should be deleted
   */
  public void delAlternative(int index){
    if (this.getNumAlternatives() > 0){
      Vector alts = this.getAlternatives();
      alts.remove(index);
      this.setAlternatives(alts);
    }
  }


  /**
   * <code>delCriteria</code> method here.
   * @param Criteria c :  the criteria which should be deleted
   * @exception IllegalArgumentException
   */
  public void delCriteria(Criteria c){    
    //if (c.getNumSubCriteria() > 0) throw new IllegalArgumentException("The criteria cannot be deleted until all subcriteria are deleted.");
    //if (c.getNumAlternatives() > 0) throw new IllegalArgumentException("The criteria cannot be deleted until all alternatives are deleted.");
    
    // criteria has no children or alternatives, so delete it
    this.getSubCriteria().removeElement(c);

    //*************************************** FINISH
    
  } 
   /**
   * <code>isSubcriteria</code> method here.
   * @param Criteria :  the criteria which should be a subcriteria
   * 
   */
  public boolean isSubcriteria(Criteria c){
    if (ll) return false;
    if (isChild(c)) return true;

    for(int i=0;i<getNumChildren();i++){
      Criteria current_c=(Criteria) children.get(i);
      if (current_c.isSubcriteria(c)) return true;
    }//c is not a subcriteria
    return false;
  }

  /**
   * <code>isChild</code> method here.
   * @param Criteria :  the criteria which should be a child
   * 
   */
  private boolean isChild(Criteria c){
    if (ll) return false;
    for(int i=0;i<getNumChildren();i++){
      Criteria current_c=(Criteria) children.get(i);
      if (c.equals(current_c)) return true;
    }//c is not a child
    return false;
  }


  /**
   * <code>main</code> method to test this class.
   * @param Criteria :  command line
   * 
   */
  public static void main(String[] argv) {
    Criteria c1=new Criteria();
    Criteria c2=new Criteria();
    System.out.println(c1.print());
    System.out.println(c2.print());
    

  }
  

}
