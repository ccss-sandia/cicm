package adt.cmt;

import adt.ahp.*;
import gui.examples.ConsequenceModelingExample;

import java.io.*;
import java.util.*;
import java.awt.*;
import adt.ahp.Activity;
import adt.ahp.Hierarchy;
import adt.util.Round;

	/**
	 * Component class
	 * @author  LC 
	 * @version Jan 2008
	 */
	public class SystemComponent extends Activity implements Serializable,Cloneable{
	  
	  //ATTRIBUTES

	  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	//useful to have always a new component with a new name 
	 //private static int newComponentSuffix = 1;
	 private Object[][] performanceMeasures;
	 private Vector constructedScales;
	 private Rectangle rect;
	 private double threatLevel;
	 private Vector<Level> threatLevels;
	 private int time;
	 private double randomFailureRate;
	 private int numResidential;
	 private int numResidentialAffected = 0;
	 private int numCommercial;
	 private int numCommercialAffected = 0;
	 private int numSmallMedIndustrial;
	 private int numSmallMedIndustrialAffected = 0;
	 private int numLargeIndustrial;
	 private int numLargeIndustrialAffected = 0;
	 private String type;
	 private int repairCost;
	 private int utilityWorkerLevel;
	 private int faunaLevel;
	 private Map<String, Integer> loadShed = new HashMap<String, Integer>(); // keys are bus numbers, values are percent of load shed
	  /**
	   * Creates a new  <code>Component</code> instance.
	   * @param String name
	   * @param String comment
	   * @param Object[][] preformanceMeasures 
	   *        [i][0] contains the performance measure text description
	   *        [i][1] contains the performance measure selected constructed scale value
	   * @param Rectangle rect
	   * 		the coordinates for the box around component in the image map
	   */
	  public SystemComponent(String name, String comment, Object[][] perfMeasures, Vector constructedScales, Rectangle rect) {
		  super(name,comment);
		  
		  this.rect = rect;
		  this.constructedScales = constructedScales;
		  
		  // deep copy
		  if (perfMeasures != null){
			  performanceMeasures = new Object[perfMeasures.length][2];
			  for (int i=0; i < perfMeasures.length; i++) {
				  this.performanceMeasures[i][0] = perfMeasures[i][0];
				  this.performanceMeasures[i][1] = perfMeasures[i][1];
			  }
		  }
		  
		  threatLevels = new Vector<Level>();
		  // set the default susceptibility levels
		  threatLevels.add(new Level(0, "Zero", "Completely secure, inaccessible"));
		  threatLevels.add(new Level(1, "Very Low", "Guarded, secure area, locked, alarmed, complex closure"));
		  threatLevels.add(new Level(2, "Low", "Secure area, locked, complex closure"));
		  threatLevels.add(new Level(3, "Moderate", "Complex barrier, security patrols, video surveillence"));
		  threatLevels.add(new Level(4, "High", "Unlocked, non-complex barriers (door or access panel)"));
		  threatLevels.add(new Level(5, "Extreme", "Completely open, no controls, no barriers"));
	  }
	  
	  public void setThreatLevels(Vector threatLevels) {
		  this.threatLevels = threatLevels;
	  }
	  public Vector getThreatLevels() {
		  return this.threatLevels;
	  }
	  public void setLoadShed(HashMap loadShed) {
		  this.loadShed = loadShed;
	  }
	  public HashMap getLoadShed() {
		  return (HashMap)this.loadShed;
	  }
	  public void setLoadShed(String key, int value) {
		  loadShed.put(key, value);
	  }
	  public int getLoadShed(String key) {
		  try {
			  Object obj = this.loadShed.get(key);
			  return Integer.parseInt(obj.toString());
		  } catch (Exception e) { return 0; }
	  }

	  public void setTime(int time) {
		  this.time = time;
	  }
	  public int getTime() {
		  return this.time;
	  }
	  public void setRandomFailureRate(double randomFailureRate) {
		  this.randomFailureRate = randomFailureRate;
	  }
	  public double getRandomFailureRate() {
		  return this.randomFailureRate;
	  }

	  public void setType(String type) {
		  this.type = type;
	  }
	  public String getType() {
		  return this.type;
	  }
	  
	  public void setRepairCost(int cost) {
		  this.repairCost = cost;
	  }
	  
	  public int getRepairCost() {
		  return this.repairCost;
	  }
	  
	  public void setUtilityWorkerLevel(int lvl) {
		  this.utilityWorkerLevel = lvl;
	  }
	  
	  public int getUtilityWorkerLevel() {
		  return this.utilityWorkerLevel;
	  }
	  
	  public void setFaunaLevel(int lvl) {
		  this.faunaLevel = lvl;
	  }
	  
	  public int getFaunaLevel() {
		  return this.faunaLevel;
	  }
	  
	  /**
	   * <code>setRect</code>
	   * sets the coordinates for the box around the component in the image map
	   * @param Rectangle rect
	   */
	  public void setRect(Rectangle rect) {
		  this.rect = rect;
	  }
	  
	  /**
	   * <code>getRect</code>
	   * returns the Rectangle for the box drawn around the component in the image map
	   */
	  public Rectangle getRect() {
		  return this.rect;
	  }
	  
	  public Vector getConstructedScales() {
		  return this.constructedScales;
	  }
	  
	  /**
	   * <code>setThreatLevel</code>
	   * sets the threat level for the component
	   * @param double threatLevel
	   */
	  public void setThreatLevel(double tl) {
		  this.threatLevel = tl;
	  }
	  
	  /**
	   * <code>getThreatLevel</code>
	   * returns the double threat level value for the component
	   */
	  public double getThreatLevel() {
		  return this.threatLevel;
	  }
	  
	  /**
	   * <code>getPerformanceMeasures</code>
	   * returns the two-dimensional array of performance measures for the component
	   * [i][0] contains the performance measure text description
	   * [i][1] contains the performance measure double value
	   */
	  public Object[][] getPerformanceMeasures() {
		  return this.performanceMeasures;
	  }
	  
	  /**
	   * <code>setPerformanceMeasures</code>
	   * sets the two-dimensional array of performance measures for the component
	   * @param Object[][] performanceMeasures
	   * [i][0] contains the performance measure text description
	   * [i][1] contains the performance measure double value
	   */
	  public void setPerformanceMeasures(Object[][] perfMeasures) {
		  this.performanceMeasures = perfMeasures;
	  }
	  
	  /**
	   * <code>setPerformanceMeasureAt</code> 
	   * sets the double value of the performance measure at index i
	   */
	  public void setPerformanceMeasureAt(int i, String val) {
		  this.performanceMeasures[i][1] = val;
	  }
	  
	  public String getPerformanceMeasureAt(int i) {
		  return (String)performanceMeasures[i][1];
	  }
	  public int getNumResidentialAffected() {
		  return this.numResidentialAffected;
	  }
	  public void setNumResidentialAffected(int numResAffected) {
		  this.numResidentialAffected = numResAffected;
	  }
	  
	  public int getNumResidential() {
		  return this.numResidential;
	  }
	  public void setNumResidential(int numResidential) {
		  this.numResidential = numResidential;
	  }
	  public int getNumCommericalAffected() {
		  return this.numCommercialAffected;
	  }
	  public void setNumCommercialAffected(int numCommAffected) {
		  this.numCommercialAffected = numCommAffected;
	  }
	  public int getNumCommercial() {
		  return this.numCommercial;
	  }
	  public void setNumCommerical(int numCommercial) {
		  this.numCommercial = numCommercial;
	  }
	  public int getNumSmallMedIndustrialAffected() {
		  return this.numSmallMedIndustrialAffected;
	  }
	  public void setNumSmallMedIndustrialAffected(int numSmallMedIndustrialAffected) {
		  this.numSmallMedIndustrialAffected = numSmallMedIndustrialAffected;
	  }
	  
	  public int getNumSmallMedIndustrial() {
		  return this.numSmallMedIndustrial;
	  }
	  public void setNumSmallMedIndustrial(int numSmallMedIndustrial) {
		  this.numSmallMedIndustrial = numSmallMedIndustrial;
	  }
	  public int getNumLargeIndustrialAffected() {
		  return this.numLargeIndustrialAffected;
	  }
	  public void setNumLargeIndustrialAffected(int numLargeIndustrialAffected) {
		  this.numLargeIndustrialAffected = numLargeIndustrialAffected;
	  }
	  
	  public int getNumLargeIndustrial() {
		  return this.numLargeIndustrial;
	  }
	  public void setNumLargeIndustrial(int numLargeIndustrial) {
		  this.numLargeIndustrial = numLargeIndustrial;
	  }

	  /**
	   * <code>print</code> Returns a string representation of this Component, containing the String representation of each element.
	   *
	   * @return a <code>String</code> value
	   */
	  public String print(){
	    String s = new String();
	    s = s + "\n";
	    s = s + "        Name: " + getName() + "\n";
	    s = s + "        Comment: " + getComment() + "\n";
	    s = s + "        Type: " + getType() + "\n";
	    s = s + "        ThreatLevel: " + (int)getWeight() + "\n";
	    s = s + "        Time: " + getTime() + "\n";
	    s = s + "        # Residential Customers: " + getNumResidential() + "\n";
	    s = s + "        # Commercial Customers: " + getNumCommercial() + "\n";
	    s = s + "        # Small/Medium Industrial Customers: " + getNumSmallMedIndustrial() + "\n";
	    s = s + "        # Large Industrial Customers: " + getNumLargeIndustrial() + "\n";
	    s = s + "        PerformanceMeasures: \n";
	    for (int i=0; i < performanceMeasures.length; i++) {
	    	s = s + "          " + i + ": " + performanceMeasures[i][0] + " val: " + performanceMeasures[i][1] + "\n";
	    }
	    return s;
	  }
	  
	  /**
	   * <code>getPI</code>
	   * returns the performance index for the component
	   * the performance index is calculated as the sum of all performance measure values
	   */
	  public double getPI() {
	   	int rows = performanceMeasures.length;
	   	double c_pi = 0.0;    	
	   	for (int i=0; i < rows; i++) {
	   		//String m = (String)performanceMeasures[i][0];
	   		String csv = (String)performanceMeasures[i][1];
	    	double v = 0.0;
	    	Vector cs = (Vector)constructedScales.get(i);
	   		for (int j=0; j < cs.size(); j ++) {
	   			Alternative alt = (Alternative)cs.get(j);
	   			if (alt.getComment() == csv) {
	   				v = alt.getWeight();
	   			}
	   		}
	   		//System.out.println("SystemComponent " + this.name + " measure: " + m + " selected val is " + csv + " val is " + v);
	   		
	   		c_pi = c_pi + v;    			
	   	}
	   	//System.out.println("++++++++++++++++++SystemComponent:  pi is " + c_pi);
	   	return Round.round(c_pi,5);
	  }	

	  /**
	   * <code>getWeightedPI</code>
	   * returns the weighed performance index for the component
	   * the weighted performance index is calculated as the performance index of the component multiplied by the threat level of the component
	   */
	  public double getWeightedPI() {
		  //System.out.println("SystemComponent:  threat level is " + this.getThreatLevel());
		  //System.out.println("SystemComponent: weighted component pi is " + getPI() * this.getThreatLevel());
		  return getPI();// * this.getThreatLevel();
	  }
	  

	  /**
	   * <code>equals</code> method here.
	   * @return boolean
	   * @param Component which should be compared 
	   *
	   */
	    public boolean equals(Component c){
	    	return name.equals(c.getName());
	    }
	    public int getLostRevenue(){
			  double residentialRate = .0868;
			  double commercialRate = .0794;
			  double smIndustrialRate = .0506;
			  double lgIndustrialRate = .0506;
			  double residentialUsage = 1.25;
			  double commercialUsage = 8.25;
			  double smIndustrialUsage = 20;
			  double lgIndustrialUsage = 1000;	  
			  
			  int numCommercial = getNumCommericalAffected();
			  int numResidential = getNumResidentialAffected();
			  int numLargeIndustrial = getNumLargeIndustrialAffected();
			  int numSmallMedIndustrial = getNumSmallMedIndustrialAffected();
			  

			  int outageTime = getTime();
			  
			  double val = ((numResidential * residentialRate * residentialUsage) + (numCommercial * commercialRate * commercialUsage) +
			  (numSmallMedIndustrial * smIndustrialRate * smIndustrialUsage) + (numLargeIndustrial * lgIndustrialRate * lgIndustrialUsage)) * outageTime;
			  //System.out.println("lost revenue is :  " + (int)adt.util.Round.round(val,0));
			  
			  // NOTE:  added cost to repair
			  val = val + getRepairCost(); 
			  
			  return (int)adt.util.Round.round(val, 0);
			  
		  }

	  /**
	   * <code>main</code> method to test this class.
	   *
	   * @param args[] a <code>String</code> value : command line
	   */
	  public static void main(String args[]){
		  
	    ConsequenceModelingExample test = new ConsequenceModelingExample();
	    Hierarchy h = test.getHierarchyExample();
	    CMTSystem m = new CMTSystem(h);
	    
	    SystemComponent c1=new SystemComponent("T1", "Transmission line 1", m.getPerformanceMeasures(), m.getConstructedScales(), new Rectangle());
	    SystemComponent c2=new SystemComponent("T2", "Transmission line 2", m.getPerformanceMeasures(),  m.getConstructedScales(), null);
	    
	    c1.setPerformanceMeasureAt(0, ".12");
	    c1.setPerformanceMeasureAt(1, ".13");
	    c1.setThreatLevel(0.34);
	    
	    c2.setPerformanceMeasureAt(0, ".23");
	    c2.setPerformanceMeasureAt(1, ".50");
	    c2.setThreatLevel(0.56);
	    
	    System.out.println(c1.print());
	    System.out.println(c2.print());
	    
	    System.out.println("Performance Index is of c1 is " + c1.getPI() + " and weighted pi is " + c1.getWeightedPI());	    
	    System.out.println("Performance Index is of c2 is " + c2.getPI() + " and weighted pi is " + c2.getWeightedPI());
	    
	  }
	}


	  


	
	

