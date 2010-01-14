package gui.examples;

import adt.cmt.Level;
import adt.cmt.PerformanceLevel;

import java.util.*;

/**
 *  Constructs the system levels used in the paper Bulk Power Grid Risk Analysis:
 *  Ranking Infrastructure Elements According to their Risk Significance
 *  -- A.M. Konce, G.E. Apostolakis, B.K. Cook
 */
public class SystemLevels {
	Vector systemHealthLevels;
	Vector performanceLevels;
	
	public SystemLevels() {
		
		// setup default system health levels
		this.systemHealthLevels = new Vector();
		this.systemHealthLevels.add(new Level(0,"Green","This is the final category for action.  It gathers all locations " +
				"not included in the more severe cases, typically those that are low (and below) on the " +
				"susceptibility scale and low (and below) on the value scale.  It is recognized that constrained " +
				"fiscal resources is likely to limit efforts in this category, but it should not be ignored."));
		this.systemHealthLevels.add(new Level(1,"Blue","This category represents the fourth priority for counter terrorism efforts."));
		this.systemHealthLevels.add(new Level(2,"Yellow","This category represents the third priority for counter terrorism " +
				"efforts.  These locations are normally less vulnerable because they are either less susceptible " +
				"or less valuable than the terrorist desire."));
		this.systemHealthLevels.add(new Level(3,"Orange","This category represents the second priority for counter terrorism" +
				"efforts.  These locations are generally moderate to extreme valuable and moderately to extreme " +
				"susceptible."));
		this.systemHealthLevels.add(new Level(4,"Red","This category represents a severe vulnerability in the infrastructure. " +
				"It is reserved for the most critical locations that are highly susceptible to attack.  Red " +
				"vulnerabilities are those requiring the most immediate attention."));

		// setup default performance levels
	    this.performanceLevels = new Vector();
		this.performanceLevels.add(new PerformanceLevel(0, 0.0000, 0.0049));
	    this.performanceLevels.add(new PerformanceLevel(1, 0.0050, 0.0299));
	    this.performanceLevels.add(new PerformanceLevel(2, 0.0300, 0.0499));
	    this.performanceLevels.add(new PerformanceLevel(3, 0.0500, 0.0999));
	    this.performanceLevels.add(new PerformanceLevel(4, 0.1000, 0.2499));
	    this.performanceLevels.add(new PerformanceLevel(5, 0.2500, 1.0000));
		
		
	}
	
	  /**
	   * <code>getSystemHealthLevels</code>
	   *  returns a vector containing the overall system health levels
	   */
	  public Vector getSystemHealthLevels() {
		return this.systemHealthLevels;
	  }
	  
	  /**
	   * <code>setSystemHealthLevels</code>
	   * @param systemHealthLevels Vector
	   * sets the overall system health levels
	   */
	  public void setSystemHealthLevels(Vector systemHealthLevels) {
		  this.systemHealthLevels = systemHealthLevels;
	  }
	  
	  /**
	   * <code>setSystemHealthLevelAt</code>
	   * @param i - index of health level
	   * @param lvl - system health level
	   * sets the system health level of index i
	   */
	  public void setSystemHealthLevelAt(int i, Level lvl) {
		  this.systemHealthLevels.add(i, lvl);
	  }
	  
	  /**
	   * <code>getPerforamnceLevels</code>
	   *  returns a vector containing the overall performance levels
	   */
	  public Vector getPerformanceLevels() {
		return this.performanceLevels;
	  }
	  
	  /**
	   * <code>setperforamnceLevels</code>
	   * @param perforamnceLevels Vector
	   * sets the overall performance levels
	   */
	  public void setPerformanceLevels(Vector performanceLevels) {
		  this.performanceLevels = performanceLevels;
	  }
	  
	  /**
	   * <code>setPerformanceLevelAt</code>
	   * @param i - index of performance level
	   * @param lvl - performance level
	   * sets the performance level of index i
	   */
	  public void setPerformanceLevelAt(int i, Level lvl) {
		  this.performanceLevels.add(i, lvl);
	  }	  

	  public static void main(String[] argv) {
		  SystemLevels s = new SystemLevels();
		  
		  Vector lvls = s.getSystemHealthLevels();
		  System.out.println("System Health Levels: " + lvls.toString());
		  
		  Vector plvls = s.getPerformanceLevels();
		  System.out.println("System Performance Levels: " + plvls.toString());
		  
	   }
	
}
