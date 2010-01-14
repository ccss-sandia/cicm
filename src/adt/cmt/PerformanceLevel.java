// Abstract Data Type
package adt.cmt ;


//imports
import java.util.*;
import java.io.*;

/**
 * PerformanceLevel class
 */
public class PerformanceLevel implements Serializable,Cloneable{
  
	// ATTRIBUTES
	private int level;
	private double min;
	private double max;
 

  /**
   * Creates a new  <code>PerformanceLevel</code> instance.
   * 
   */
  public PerformanceLevel(int level, double min, double max){
	  this.level = level;
	  this.min = min;
	  this.max = max;
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
   * Get the value of min.
   * @return value of min.
   */
  public double getMin() {
    return min;
  }
  
  /**
   * Set the value of min.
   * @param min  Value to assign to min.
   */
  public void setMin(double min) {
    this.min = min;
  }  
  
  /**
   * Get the value of max.
   * @return value of max.
   */
  public double getMax() {
    return max;
  }
  
  /**
   * Set the value of max.
   * @param max  Value to assign to max.
   */
  public void setMax(double max) {
    this.max = max;
  }  

  /**
   * <code>print</code> Returns a string representation of this PerformanceLevel, 
   * containing the String representation of each element.
   *
   * @return a <code>String</code> value
   */
  public String print(){
    String s = new String();
    s = s + "        Level: " + getLevel() + "\n";
    s = s + "        Min: " + getMin() + "\n";
    s = s + "        Max: " + getMax() + "\n";
    return s;
  }

  /**
   * <code>toString</code> Returns a string representation of this PerformanceLevel, 
   * containing the String representation of each element.
   *
   * @return a <code>String</code> value
   */
  public String toString(){
    return "Level " + getLevel() + ": " + getMin() + " - " + getMax();
  }
  
  
  /**
   * <code>equals</code> method here.
   * @return boolean
   * @param PerformanceLevel l which should be compared 
   *
   */
    public boolean equals(PerformanceLevel l){
    	return l.equals(l.getLevel());
    }

  /**
   * <code>main</code> method to test this class.
   *
   * @param args[] a <code>String</code> value : command line
   */
  public static void main(String args[]){
	Vector levels = new Vector();
    PerformanceLevel l0 = new PerformanceLevel(0, 0.0000, 0.0049);
    PerformanceLevel l1 = new PerformanceLevel(1, 0.0050, 0.0299);
    PerformanceLevel l2 = new PerformanceLevel(2, 0.0300, 0.0499);
    PerformanceLevel l3 = new PerformanceLevel(3, 0.0500, 0.0999);
    PerformanceLevel l4 = new PerformanceLevel(4, 0.1000, 0.2499);
    PerformanceLevel l5 = new PerformanceLevel(5, 0.2500, 1.0000);

    levels.add(l0);
    levels.add(l1);
    levels.add(l2);
    levels.add(l3);
    levels.add(l4);
    levels.add(l5);
    
    System.out.println("Performance Levels:\n");
    for (int i=0; i < levels.size(); i ++) {
    	System.out.println(((PerformanceLevel)levels.elementAt(i)).toString());
    }    
  }
}


  

