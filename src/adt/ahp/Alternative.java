// Abstract Data Type
package adt.ahp ;

import java.io.*;

/**
 * Alternative class
 * @author  Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</A> 
 * @version July 21, 2003
 * 
 * Modified by LC Jan 2008 - removed methods to calculate solutions, 
 *                           changed Criterium to Criteria,
 *                           updated print function,
 *                           changed constructor,
 *                           added a parent
 */
public class Alternative extends Activity implements Serializable,Cloneable{
  
  //ATTRIBUTES

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//useful to have always a new criterium with a new name 
 private static int newAlternativeSuffix = 1;
 private Criteria parent;
 

  /**
   * Creates a new  <code>Alternative</code> instance.
   * 
   */
  public Alternative(){
    super("My alternative " + newAlternativeSuffix++);
  }
  
  public Alternative(String name, String comment, Criteria parent) {
	  super(name, comment);
	  this.parent = parent;
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
   * <code>print</code> Returns a string representation of this Alternative, containing the String representation of each element.
   *
   * @return a <code>String</code> value
   */
  public String print(){
    String s = new String();
    s = s + "        Name: " + getName() + "\n";
    s = s + "        Comment: " + getComment() + "\n";
    s = s + "        Weight: " + getWeight() + "\n";
    return s;
  }

  /**
   * <code>equals</code> method here.
   * @return boolean
   * @param Alternative alt which should be compared 
   *
   */
    public boolean equals(Alternative alt){
    	return name.equals(alt.getName());
    }

  /**
   * <code>main</code> method to test this class.
   *
   * @param args[] a <code>String</code> value : command line
   */
  public static void main(String args[]){
    Alternative a1=new Alternative();
    Alternative a2=new Alternative();
    System.out.println(a1.print());
    System.out.println(a2.print());
    
  }
}


  

