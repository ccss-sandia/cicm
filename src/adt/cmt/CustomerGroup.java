// Abstract Data Type
package adt.cmt ;


//imports
import java.util.*;
import java.io.*;

/**
 * Level class
 */
public class CustomerGroup implements Serializable,Cloneable{
  
	// ATTRIBUTES
	private String name;
	private double rate;
	private double usage;
 

  /**
   * Creates a new  <code>customer group</code> instance.
   * 
   */
  public CustomerGroup(String name, double rate, double usage){
	  this.name = name;
	  this.rate = rate;
	  this.usage = usage;
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
   * @param name  Value to assign to name.
   */
  public void setName(String name) {
    this.name = name;
  }  

  /**
   * Get the value of rate.
   * @return value of rate.
   */
  public double getRate() {
    return rate;
  }
  
  /**
   * Set the value of rate.
   * @param rate  Value to assign to rate.
   */
  public void setRate(double rate) {
    this.rate = rate;
  }  
  
  /**
   * Get the value of usage.
   * @return value of usage.
   */
  public double getUsage() {
    return usage;
  }
  
  /**
   * Set the value of usage.
   * @param usage  Value to assign to usage.
   */
  public void setUsage(double usage) {
    this.usage = usage;
  }  

  /**
   * <code>print</code> Returns a string representation of this customer group, 
   * containing the String representation of each element.
   *
   * @return a <code>String</code> value
   */
  public String print(){
    String s = new String();
    s = s + "        Name: " + getName() + "\n";
    s = s + "        Rate: " + getRate() + "\n";
    s = s + "        Usage: " + getUsage() + "\n";
    return s;
  }

  /**
   * <code>toString</code> Returns a string representation of this customer group, 
   * containing the String representation of each element.
   *
   * @return a <code>String</code> value
   */
  public String toString(){
    return "Name " + getName() + " - " + getRate() + " - " + getUsage();
  }
  
  
  /**
   * <code>equals</code> method here.
   * @return boolean
   * @param CustomerGroup c which should be compared 
   *
   */
    public boolean equals(CustomerGroup c){
    	return c.equals(c.getName());
    }

  /**
   * <code>main</code> method to test this class.
   *
   * @param args[] a <code>String</code> value : command line
   */
  public static void main(String args[]){
	Vector customerGroups = new Vector();
    CustomerGroup c1 = new CustomerGroup("Residential", .0868, 1.25);
    CustomerGroup c2 = new CustomerGroup("Commerical", .0794, 8.25);
    CustomerGroup c3 = new CustomerGroup("Small/Medium Industrial", .0506, 20);
    CustomerGroup c4 = new CustomerGroup("Large Industrial", .0506, 1000);
  
    
    customerGroups.add(c1);
    customerGroups.add(c2);
    customerGroups.add(c3);
    customerGroups.add(c4);
    
    System.out.println("Customer Groups:\n");
    for (int i=0; i < customerGroups.size(); i ++) {
    	System.out.println(((CustomerGroup)customerGroups.elementAt(i)).toString());
    }    
  }
}


  

