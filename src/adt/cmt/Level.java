// Abstract Data Type
package adt.cmt ;


//imports
import java.util.*;
import java.io.*;

/**
 * Level class
 */
public class Level implements Serializable,Cloneable{
  
	// ATTRIBUTES
	private int level;
	private String category;
	private String description;
 

  /**
   * Creates a new  <code>Level</code> instance.
   * 
   */
  public Level(int level, String category, String description){
	  this.level = level;
	  this.category = category;
	  this.description = description;
  }
  
  /**
   * Get the value of level.
   * @return value of level.
   */
  public int getLevel() {
    return level;
  }
  
  /**
   * Set the value of level.
   * @param level  Value to assign to level.
   */
  public void setLevel(int  level) {
    this.level = level;
  }  

  /**
   * Get the value of category.
   * @return value of category.
   */
  public String getCategory() {
    return category;
  }
  
  /**
   * Set the value of category.
   * @param category  Value to assign to category.
   */
  public void setCategory(String category) {
    this.category = category;
  }  
  
  /**
   * Get the value of description.
   * @return value of description.
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * Set the value of description.
   * @param description  Value to assign to description.
   */
  public void setDescription(String description) {
    this.description = description;
  }  

  /**
   * <code>print</code> Returns a string representation of this Level, 
   * containing the String representation of each element.
   *
   * @return a <code>String</code> value
   */
  public String print(){
    String s = new String();
    s = s + "        Level: " + getLevel() + "\n";
    s = s + "        Category: " + getCategory() + "\n";
    s = s + "        Description: " + getDescription() + "\n";
    return s;
  }

  /**
   * <code>toString</code> Returns a string representation of this Level, 
   * containing the String representation of each element.
   *
   * @return a <code>String</code> value
   */
  public String toString(){
    return "Level " + getLevel() + " - " + getCategory() + " - " + getDescription();
  }
  
  
  /**
   * <code>equals</code> method here.
   * @return boolean
   * @param Level lt which should be compared 
   *
   */
    public boolean equals(Level l){
    	return l.equals(l.getLevel());
    }

  /**
   * <code>main</code> method to test this class.
   *
   * @param args[] a <code>String</code> value : command line
   */
  public static void main(String args[]){
	Vector levels = new Vector();
    Level l0 = new Level(0, "Zero", "Completely secure, inaccessible");
    Level l1 = new Level(1, "Very Low", "Guarded, secure area, locked, alarmed, complex closure");
    Level l2 = new Level(2, "Low", "Secure area, locked, complex closure");
    Level l3 = new Level(3, "Moderate", "Complex barrier, security patrols, video surveillence");
    Level l4 = new Level(4, "High", "Unlocked, non-complex barriers (door or access panel)");
    Level l5 = new Level(5, "Extreme", "Completely open, no controls, no barriers");

    levels.add(l0);
    levels.add(l1);
    levels.add(l2);
    levels.add(l3);
    levels.add(l4);
    levels.add(l5);
    
    System.out.println("Susceptibility Levels:\n");
    for (int i=0; i < levels.size(); i ++) {
    	System.out.println(((Level)levels.elementAt(i)).toString());
    }    
  }
}


  

