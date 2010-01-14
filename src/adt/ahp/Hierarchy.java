// Abstract Data Type
package adt.ahp ;

//imports
import gui.examples.ConsequenceModelingExample;

//import javax.swing.*;          //This is the final package name.
//import javax.swing.border.*;
//import java.awt.*;
//import java.awt.event.*;
import java.util.*;
import java.io.*;
//import java.net.*;

//import Jama.*;

/**
 * Decision <code>Hierarchy</code> class
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version Fev 13, 2003
 * @version March 24, 2003  final one
 */
public class Hierarchy implements Cloneable,Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// ATTRBUTES
  private Criteria goal;
  //private Vector a;

  /**
   * Gets the value of goal
   *
   * @return the value of goal
   */
  public Criteria getGoal() {
    return this.goal;
  }

  /**
   * Sets the value of goal
   *
   * @param argGoal Value to assign to this.goal
   */
  public void setGoal(Criteria argGoal){
    this.goal = argGoal;
  }

  /**
   * Gets the value of criteria
   *
   * @return the value of criteria
   */
  public Vector getCriteria() {
    return this.goal.getSubCriteria();
  }

   
  /**
   * Sets the value of criteria
   *
   * @param argCriteria Value to assign to this.criteria
   */
  public void setCriteria(Vector argCriteria){
    //this.criteria = argCriteria;
    goal.setSubCriteria(argCriteria);
  }

  /**
   * Gets the number of criteria
   *
   * @return the number of criteria
   */
  public int getNumCriteria() {
    return this.goal.getNumSubCriteria();
  }
  
  
  /**
   * <code>calculateWeights</code>
   *
   * @param Criteria c
   * added by lc
   */
  public static void calculateWeights(Criteria c) {
	  System.out.println("critera is " + c.getName());
	  PairwiseComparisonMatrix p = c.getComparisonMatrix();

	  if (c.getParent() == null) {  // goal of hierarchy
		  c.setWeight(1.0);		  
		  //System.out.println("....weight of goal is 1.0....");
	  }
	  if (c.isLl()) {  // lowest level of hierarchy
		  //System.out.println("....lowest level of hierarchy...");
		  Vector alts = c.getAlternatives();
		  for (int i=0; i < alts.size(); i++) {
			  Alternative alt = (Alternative)alts.get(i);
			  p.calcWeights();
			  //System.out.println("====== " + c.getName() + "  disutility is " + adt.util.Round.round(p.getWeight(i),4) + " parent weight is " + c.getWeight() + " weighted val is " + p.getWeight(i) * c.getWeight());
			  alt.setWeight(p.getWeight(i) * c.getWeight());
			  //System.out.println("===> weight is " + p.getWeight(i));
		  }
	  }
	  else {
		  //System.out.println("....setting weight of subcriteria....");
		  for (int i=0; i < c.getNumSubCriteria(); i++) {
			  // set the weight for each subcriteria
			  Criteria crit = (Criteria)c.getSubCriteriaAt(i);
			  p.calcWeights();
			  double w = p.getWeight(i) * c.getWeight();
			  crit.setWeight(w);
			  
			  // calculate weight of all subcriteria
			  calculateWeights(c.getSubCriteriaAt(i));
		  }	
	  }
  }	  
  
  
  /**
   * Creates a new  <code>Hierarchy</code> instance.
   * a minimal hierarchy is composed by 1 goal : "My goal" and 2 criteria : "Criteria 1" and "Criteria 2"
   */
  public Hierarchy(){
    //Specification of the goal
    goal = new Criteria("My Goal","This is my goal",null);

    //Specification of the criteria
	Vector<Criteria> criteria = new Vector<Criteria>();    
	Criteria c1 =new Criteria("Criteria 1", goal);
    Criteria c2 =new Criteria("Criteria 2", goal);
    criteria.add(c1);
    criteria.add(c2);
    

    setCriteria(criteria);
  }
  
  public Hierarchy(String goal) {
	  this.goal = new Criteria(goal);
  }
  
  public Hierarchy(String goalName, String goalComment) {
	  this.goal = new Criteria(goalName, goalComment);
  }



  /**
   * <code>print</code> Returns a string representation of this Hierarchy, containing the String representation of each elements to debug.
   *
   * @return a <code>String</code> value
   */
  public String print(){
    String s=new String();
    s=s+"Hierarchy       : \n"+getGoal().print();
    return s;
  }


  /**
   * <code>toString</code> Returns a short string representation of this Hierarchy
   *
   * @return a <code>String</code> value
   */
  public String toString(){
    String s=new String();
    s="Goal of the hierarchy       : \n"+getGoal().toString();
    s=s+"Num of criteria          : "+getNumCriteria()+"\n";
    return s;
  }


  /**
   * <code>addCriteria</code> method here.
   *
   * @param alt <code>Criteria</code> value
   * @exception IllegalArgumentException "Out of bounded..."
   * @return int index of the criteria 
   */
  //public int indexC(Criteria c){
  //  int index=0;
  //  if (isNew(c)) throw new IllegalArgumentException("out of bounded criteria");
  //  for(int i=0;i<num_criteria;i++){
  //    Criteria current_c=(Criteria) criteria.get(i);
  //    if (current_c.equals(c)){
//	index=i;
//	return index;
 //     }
  //  }
  //  return -1;
 // }



  /**
   * <code>addCriteria</code> method here.
   *
   * @param c <code>Criteria</code> value
   * @exception IllegalArgumentException "Out of bounded"
   */
  @SuppressWarnings("unchecked")
public void addCriteria(Criteria c){
    if (!isNew(c)) throw new IllegalArgumentException("A criteria with the same name is already in this hierarchy");
    Vector<Criteria> cr = goal.getSubCriteria();
    cr.add(c);
    goal.setSubCriteria(cr);
  }

  /**
   * <code>addSubcriteria</code> method here.
   *
   * @param Criteria c which should be the father
   * @param Criteria subc which should be added
   * @param Vector alternatives
   * @param int nb_alternatives
   * @exception IllegalArgumentException
   */
  //***?????????????????????????????????????????????
  public void addSubcriteria(Criteria c,Criteria subc,Vector alternatives,int nb_alternatives){

    if (!goal.isNew(subc)) throw new IllegalArgumentException("A criteria with the same name is always in this hierarchy");
    subc.setParent(c);
    subc.setLl(true);
    subc.setAlternatives((Vector) alternatives.clone());
    subc.setComparisonMatrix(new PairwiseComparisonMatrix(nb_alternatives, true));
    //goal.addSubcriteria(c,subc);
  }


  /**
   * <code>isNew</code> method here.
   *
   * @param Criteria c
   * @return boolean
   */
  public boolean isNew(Criteria c){
    for(int i=0;i<getNumCriteria();i++){
      Criteria current_c=(Criteria) goal.getSubCriteria().get(i);
      if (current_c.equals(c)) return false;
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
   * <code>best_alternative</code> method here.
   *
   * @return an int : the index of the best alternative 
   */
 // public int bestCriteria(){
 //   int index=0;
 //   double value=0.0;
 //   for(int i=0;i<num_criteria;i++){
 //     if (Pi(i)>value){
//	index=i;
//	value=Pi(i);
 //     }
 // }
 //   return index;
 // }


  /**
   * <code>Pi</code> method here.
   * @param i :  the index of the alternative
   * @return double  : the value of the alternative according to the hierarchy
   */
//  public double Pi(int i){
//    return goal.Jstar(i);
//  }

  /**
   * <code>V</code> method here.
   * @param Criteria c according to the hierarchy is evaluated
   * @return double  : the value of the criteria according to the hierarchy
   * @return IllegalArgumentException
   */
  //public double V(Criteria c){
  //  if (goal.equals(c)) throw new IllegalArgumentException("the value of the criteria according to the hierarchy can not be calculated");
  //  return(goal.I(c));
  //}


  //************************************
  //
  //Method to modify hierarchy
  // 
  // 
  //************************************* 

  /**
   * <code>delCriteria</code> method here.
   * @param Criteria :  the criteria which should be deleted
   * @exception IllegalArgumentException
   */
/*
  public void delCriteria(Criteria c){
    Vector old_c=(Vector)this.getCriteria().clone();
    int old_num_c=this.getCriteria().size();
    Vector new_c=new Vector();
    int num_criteria = goal.getNumSubCriteria();
    int index=0;
    if (num_criteria==1) throw new IllegalArgumentException("criteria can not be deleted : a hierarchy must have an even number of criteria");
    for(int i=0;i<num_criteria;i++){
      Criteria current_c=(Criteria) old_c.get(i);
      if (current_c.equals(c)){
	index=i;
      }
      else new_c.add(current_c);
    }
    setCriteria(new_c);
    num_criteria--;
    //goal.delCriteria(index);
  }

  /**
   * <code>delCriteria</code> method here.
   * @param Criteria :  the criteria which should be deleted
   * @exception IllegalArgumentException
   */
/*
  public void delCriteria(Criteria c){
    goal.delCriteria(c,this);
  }
*/

  /////////////////////
  //
  // Methods to load and save a hierarchy
  // NOT Yet Implemented
  /////////////////////
  

  /**
   * <code>main</code> method to test this class.
   * 
   */
  public static void main(String[] argv) {
	  Hierarchy h = new Hierarchy();
	  ConsequenceModelingExample ex = new ConsequenceModelingExample();
	  h=ex.getHierarchyExample();
	    
	  System.out.println(h.print());

  }  

}


  

