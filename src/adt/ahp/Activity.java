// Abstract Data Type
package adt.ahp ;


//imports
//import javax.swing.*;          //This is the final package name.
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;

//import adt.util.Round;



//import java.awt.*;
//import java.awt.event.*;
//import java.util.*;
import java.io.*;
//import java.net.*;

//import Jama.*;


/**
 * Activity class
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version July 21, 2003
 * 
 * Modified by LC Jan 2008 - added weight
 */
public abstract class Activity implements Serializable,Cloneable{

  //ATTRIBUTES

  // information about the activity
  protected String comment;
  protected String name;
  protected double weight;
  
  /**
   * Get the value of weight.
   * @return value of weight.
   */
  public double getWeight() {
	  return adt.util.Round.round(weight,4);
  }
  
  /**
   * Set the value of weight.
   * @param w Value to assign to weight.
   */
  public void setWeight(double w) {
	  this.weight = w;
  }
  
  /**
   * Get the value of name.
   * @return value of name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Set the value of name.
   * @param v  Value to assign to name.
   */
  public void setName(String  v) {
    this.name = v;
  }
   
  
  /**
   * Gets the value of comment
   *
   * @return the value of comment
   */
  public String getComment() {
    return this.comment;
  }

  /**
   * Sets the value of comment
   *
   * @param argComment Value to assign to this.comment
   */
  public void setComment(String argComment){
    this.comment = argComment;
  }

  /**
   * Creates a new  <code>Activity</code> 
   */
  public Activity(){
	this.name = new String("Default name");
    this.comment = new String("Default comment...");
  }
  
  public Activity(String name) {
	  this.name = name;
	  this.comment = new String("Default comment...");
  }
  
  public Activity(String name, String comment) {
	  this.name = name;
	  this.comment = comment;
  }


  /**
   * <code>toString</code> Returns a short string representation of this Alternative
   *
   * @return a <code>String</code> value
   */
  public String toString(){
    return this.name;
  }

 
}
