package adt.cmt;

import gui.cmt.CMT;
import gui.examples.ConsequenceModelingExample;
import gui.examples.SystemLevels;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
//import java.lang.*;
import java.util.*;
import adt.util.*;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import javax.swing.table.TableModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import adt.ahp.*;

/**
 * CMT System class
 * @author  LC 
 * @version Mar 2008
 */
public class CMTSystem implements Cloneable,Serializable{
	Hierarchy h;                              // the ahp model
	Vector constructedScales = new Vector();  // alternative level of the ahp model
	Object[][] performanceMeasures;           // criterial levels of the ahp model
	double pi = 0.0;                          // system performance index
	
	String img;                                // the image of the system 
	//Map coords;                               // the rectangles around the components in the system
	
	private Vector systemHealthLevels;                // the levels of overall system health
	private Vector performanceLevels;				  // the performance levels of the system

	
	public String selectedComponent;
	private Vector impactedComponents = new Vector();
	
	int[][] healthLookup = {
			{0, 0, 0, 0, 0, 0 },
			{0, 1, 1, 1, 1, 1 },
			{0, 1, 1, 2, 2, 2 },
			{0, 1, 2, 2, 3, 3 },
			{0, 2, 2, 3, 3, 4 },
			{1, 2, 3, 4, 4, 4 }};	
	
	private Map components;         // the components in the system
	private Map loads;				// the load components in the system
	
	private Vector customerGroups;   // the customer groups, rates and usage
	
	/**
	 * Creates a new <code>CMTSystem</code> instance.
	 * @param h Hierarchy
	 */
	public CMTSystem(Hierarchy h) {
		this.h = h;
		//this.components = components;
		pullHierarchyData();
	}
	
	public int getSystemLostRevenue() {
		int systemLostRevenue = 0;
		for (int i=0; i < this.impactedComponents.size(); i++) {
			SystemComponent sc = (SystemComponent)impactedComponents.get(i);
			systemLostRevenue = systemLostRevenue + getLostRevenue(sc);
		}
		return systemLostRevenue;
		
	}
	
	public void setImpactedComponents(Vector impactedComponents) {
		this.impactedComponents = impactedComponents;
	}
	public Vector getImpactedComponents() {
		return this.impactedComponents;
	}
	public Map getLoadComponents() {
		return this.loads;
	}
	public void setImpactedComponent(SystemComponent sc) {
		if (!this.impactedComponents.contains(sc)) {
			this.impactedComponents.add(sc);
		}
	}
	public void removeImpactedComponent(SystemComponent sc) {
		if (this.impactedComponents.contains(sc)) {
			this.impactedComponents.remove(sc);
		}
	}
	
	public SystemComponent getImpactedComponentAt(int index) {
		return (SystemComponent)impactedComponents.elementAt(index);
	}
	public void setSystemHealthLevels(Vector systemHealthLevels) {
		this.systemHealthLevels = systemHealthLevels;
	}
	public Vector getSystemHealthLevels() {
		return this.systemHealthLevels;
	}
	public Level getSystemHealthLevelAt(int index) {
		return (Level)this.systemHealthLevels.get(index);
	}
	public void setSystemHealthLevelAt(int index, Level lvl) {
		this.systemHealthLevels.add(index, lvl);
	}
	
	public void setPerformanceLevels(Vector performanceLevels) {
		this.performanceLevels = performanceLevels;
	}
	public Vector getPerformanceLevels() {
		return this.performanceLevels;
	}
	public Level getPerformanceLevelAt(int index) {
		return (Level)this.performanceLevels.get(index);
	}
	public void setPerformanceLevelAt(int index, Level lvl) {
		this.performanceLevels.add(index, lvl);
	}	
	/*
	public int lookupHealth(int x, int y) {
		System.out.println("CMTSystem:  lookupHealth");
		return healthLookup[x][y];
	}
*/
	
	public int[][] getHealthTable() {
		return healthLookup;
	}
	public void setHealthTable(int[][] healthLookup) {
		this.healthLookup = healthLookup;
	}
	public void setSelectedComponent(String comp) {
		//System.out.println("CMTSystem:  setSelectedComponent");
		this.selectedComponent = comp;
	}
	
	public String getSelectedComponent() {
		//System.out.println("CMTSystem:  getSelectedComponent");
		return this.selectedComponent;
	}
	
	/**
	 * <code>getSystemComponents</code>
	 * @return Vector of System Components
	 */
	public Map getSystemComponents() {
		//System.out.println("CMTSystem:  getSystemComponents");
		return this.components;
	}
	
	/**
	 * <code>setSystemComponents</code>
	 * @param components - Vector of system components
	 */
	public void setSystemComponents(Map components) {
		//System.out.println("CMTSystem:  setSystemComponents");
		this.components = components;
	}
	
	public void setComponentSusceptibility(String component, int sus) {
		//System.out.println("CMTSystem:  setComponentSusceptibility");
		//System.out.println("CMTSystem - setComponentSusceptivility:  component " + component + " threat level " + sus);
		SystemComponent sc = getComponent(component);
		//sc.print();
		if (sc != null) {
			sc.setThreatLevel(sus);
			//System.out.println("CMTSystem - setComponentSusceptibility: Threat level is " + sc.getThreatLevel());
			
		}
	}
	
	/**
	 * <code>printComponents</code>
	 * Prints all components in the system
	 */
	public void printSystemComponents() {
		//System.out.println("CMTSystem:  printSystemComponents");
		//System.out.println("Components:\n");
		Iterator it = components.values().iterator();
		while(it.hasNext()) {
			SystemComponent c = (SystemComponent)it.next();
			//System.out.println(c.getName() + ": " + c.print());
		}
	}
	
	public SystemComponent getComponent(String comp) {
		//System.out.println("CMTSystem:  getComponent");
		try {
			return (SystemComponent)components.get(comp);
		} catch (NullPointerException e) { return null; }
	}
	
	public void setComponent(SystemComponent sc) {
		//System.out.println("CMTSystem:  setComponent");
		this.components.put(sc.getName(), sc);
	}
	
	/**
	 * <code>getConstructedScales</code>
	 * @return Vector of vectors of constructed scales
	 */
	public Vector getConstructedScales() {
		//System.out.println("CMTSystem:  getConstructedScales");
		  return this.constructedScales;
    }
	
	/**
	 * <code>getConstructedScaleAt</code>
	 * @param i - index of constructed scale
	 * @return Vector of constructed scales
	 */
	public Vector getConstructedScaleAt(int i) {
		//System.out.println("CMTSystem:  getConstructedScaleAt");
		return (Vector)constructedScales.get(i);
	}
	
	/**
	 * <code>getPerformanceMeasures</code>
	 * @return Object[][] of performance measures
	 * [i][0] is the performance measure text description
	 * [i][1] is the performance measure selected constructed level
	 * [i][2] is the performance measure double value
	 */
	public Object[][] getPerformanceMeasures() {
		//System.out.println("CMTSystem:  getPerformanceMeasures");
		return this.performanceMeasures;
	}
	
	/**
	 * <code>printPerformanceMeasures</code>
	 * Prints the array of performance measures and values
	 */
	public void printPerformanceMeasures() {
		//System.out.println("CMTSystem:  printPerformanceMeasures");		
		System.out.println("Performance Measures:");
		for (int i=0; i < performanceMeasures.length; i++) {
			System.out.println(i + ": " + performanceMeasures[i][0] + " selected val: " + performanceMeasures[i][1]);
		}
	}
	
	/**
	 * <code>printConstructedScales</code>
	 * Prints the vector of constructed scales
	 */
	private void printConstructedScales() {
		//System.out.println("CMTSystem:  printConstructedScales");		
		if (constructedScales != null) {
			System.out.println("Constructed Scales:");
			for (int i=0; i < constructedScales.size(); i++) {
				System.out.println(i + ": " + constructedScales.get(i));
			}
		} else {
			System.out.println("There are no constructed scales.");
		}
	}
	
	/**
	 * <code>printConstructedScaleAt</code>
	 * @param i - index of constructed scale
	 */
	private void printConstructedScaleAt(int i) {
		//System.out.println("CMTSystem:  printConstructedScaleAt");
		if (constructedScales != null) {
			System.out.println(i + ": " + constructedScales.get(i));
		}
	}
	  
	/**
	 * <code>pullHierarchyData</code>
	 * Pulls out the performance measures and constructed scales from the ahp model
	 */
	private void pullHierarchyData() {
		//System.out.println("CMTSystem:  pullHierarchyData");
		if (h != null) {
			Criteria goal = h.getGoal();
		
			Vector pms = new Vector();
			Vector crit = goal.getSubCriteria();
			// get the number of lowest level criteria
			for (int i=0; i < crit.size(); i++) {
				Criteria c = getLowestLevelCriteria((Criteria)crit.elementAt(i));
				String str = c.toString();
				Vector v = c.getSubCriteria();
				for (int j=0; j < v.size(); j++) {
					Criteria cr = (Criteria)v.elementAt(j);
					String s = str + " - " + cr.toString();
	    			pms.add(s);
	    			constructedScales.add(cr.getAlternatives());
				}
			}
		
			performanceMeasures = new Object[pms.size()][2];
			for (int i=0; i < pms.size(); i++) {
				performanceMeasures[i][0] = pms.elementAt(i);
				
				Vector cs = (Vector)constructedScales.get(i);
				Alternative alt = (Alternative)cs.get(cs.size()-1);
				
				performanceMeasures[i][1] = alt.getComment();
			} 
		}
	  }
	
	  /**
	   * <code>getSystemPI</code>
	   * @return double
	   * The system performance index is calculated as the sum of weighted performance index for each component
	   */
	  public double getSystemPI() {
		  //System.out.println(".....getting System PI...for " + getImpactedComponents().size() + " components");
		  // create a temporary SystemComponent to store the values in and
		  // calculate the SystemComponent PI
		  SystemComponent temp = new SystemComponent("system", "", this.getPerformanceMeasures(), this.getConstructedScales(), null);
		  int lostRevenue = 0;
		  int repairCost = 0;
		  int numCommercial = 0;
	      int numResidential = 0;
		  int numSmIndustrial = 0;
		  int numLgIndustrial = 0;
		  int duration = 0;	  
		  int utilityWorkerLevel = 0;
		  int faunaLevel = 0;
		  for (int z=0; z < getImpactedComponents().size(); z++) {
			  SystemComponent s = getImpactedComponentAt(z);
			  lostRevenue = lostRevenue + getLostRevenue(s);
			  //System.out.println(z + s.getName() + ": lost Revenue is " + s.getLostRevenue() + " system lost rev: " + lostRevenue);
			  repairCost = repairCost + s.getRepairCost();
			  //System.out.println(z + s.getName() + ": repair cost is " + s.getRepairCost() + " system repair cost: " + repairCost);
			  numCommercial = numCommercial + s.getNumCommericalAffected();
			  //System.out.println(z + s.getName() + ": num commercial is " + s.getNumCommericalAffected() + " system numCommercial: " + numCommercial);
			  numResidential = numResidential + s.getNumResidentialAffected();
			  //System.out.println(z + s.getName() + ": num residential is " + s.getNumResidentialAffected() + " system numResidential: " + numResidential);  
			  numSmIndustrial = numSmIndustrial + s.getNumSmallMedIndustrialAffected();
			  //System.out.println(z + s.getName() + ": num smIndustrial is " + s.getNumSmallMedIndustrialAffected() + " system numSmIndustrial: " + numSmIndustrial);
			  numLgIndustrial = numLgIndustrial + s.getNumLargeIndustrialAffected();
			  //System.out.println(z + s.getName() + ": num lgIndustrial is " + s.getNumLargeIndustrialAffected() + " system numLgIndustrial: " + numLgIndustrial);			  
			  // get the max duration
			  if (s.getTime() > duration) {
				  duration = s.getTime();
			  }
			  //System.out.println(z + s.getName() + ": duration is " + s.getTime() + " system duration: " + duration);
			  // get the max utilityWorkerLevel
			  if (s.getUtilityWorkerLevel() > utilityWorkerLevel) {
				  utilityWorkerLevel = s.getUtilityWorkerLevel();
			  }
			  //System.out.println(z + s.getName() + ": utilityworkerlevel is " + s.getUtilityWorkerLevel() + " system utilityworkerlevel: " + utilityWorkerLevel);
			  // get the max fauna level
			  if (s.getFaunaLevel() > faunaLevel) {
				  faunaLevel = s.getFaunaLevel();
			  }
			  //System.out.println(z + s.getName() + ": faunalevel is " + s.getFaunaLevel() + " system fauna level: " + faunaLevel);
		  }
     	  
		  try {
		  	Vector cs = temp.getConstructedScales();
		    		
		  	//System.out.println("CMTPerformanceMeasuresPanel selected component: " + temp.getName());    	
		  	if (temp != null) {
		  		Object[][] m = temp.getPerformanceMeasures();
		  		for (int i=0; i < m.length; i++) {
		  			String val = "";
		  			if (((String)m[i][0]).equals("Economics - Lost Revenue")) {
		  				val = CMTElectricPowerSystem.getEconomicsLostRevenue(lostRevenue);
		  			} else if (((String)m[i][0]).equals("Economics - Repair/Replace")) {
    					val = CMTElectricPowerSystem.getEconomicsRepairReplace(repairCost);
		    		} else if (((String)m[i][0]).equals("Image - Public")) {
		    			val = CMTElectricPowerSystem.getImagePublic((Vector)cs.get(i), duration, numResidential, numCommercial, numSmIndustrial, numLgIndustrial);		    			
		    		} else if (((String)m[i][0]).equals("Image - Political")) {	    				
    					val = CMTElectricPowerSystem.getImagePolitical((Vector)cs.get(i), duration, numResidential, numCommercial, numSmIndustrial, numLgIndustrial);	
		    		} else if (((String)m[i][0]).equals("Image - Customer")) { 
		    			val = CMTElectricPowerSystem.getImageCustomer((Vector)cs.get(i), duration, numResidential, numCommercial, numSmIndustrial, numLgIndustrial);
		    		} else if (((String)m[i][0]).equals("Health & Safety - General Public")) {
		    			val = CMTElectricPowerSystem.getHealthSafetyGeneralPublic((Vector)cs.get(i), duration, numResidential, numCommercial, numSmIndustrial, numLgIndustrial);		    					
		    		} else if (((String)m[i][0]).equals("Health & Safety - Utility Workers")) {
		    			val = CMTElectricPowerSystem.getHealthSafetyUtilityWorkers((Vector)cs.get(i), utilityWorkerLevel);		    					
		    		} else if (((String)m[i][0]).equals("Environment - Fauna")) {
		    			val = CMTElectricPowerSystem.getEnvironmentFauna((Vector)cs.get(i), faunaLevel);
		    		} else {				
		    			val = (String)m[i][1];
		    		}
		  			m[i][1] = val;
		  			//System.out.println("value is...." + m[i][0] + "..............." + m[i][1]);
		  		}
		  		temp.setPerformanceMeasures(m);
		  	} 
		  	
		} catch (NullPointerException e) {}
	 
		//System.out.println("System PI is " + temp.getPI());
	    return Round.round(temp.getPI(),5);
		  
		  
		  
		  /*   ---- old way of calculating -- just takes the max  ------
		  //System.out.println("CMTSystem:  getSystemPI");
		  double pi = 0.0;
		  //for (int i=0; i < components.size(); i++) {
			  //double cpi = ((SystemComponent)components.get(i)).getWeightedPI();
		  for (int i=0; i < this.impactedComponents.size(); i++) {
			  SystemComponent sc = (SystemComponent)impactedComponents.get(i);			  
			  if (sc != null) {
				  //System.out.println("CMTSystem:  component is " + sc.getName());
				  double cpi = sc.getWeightedPI();
				  //System.out.println("CMTSystem:  weighted pi is " + cpi);
				  //pi = pi + cpi;
				  if (cpi > pi)
					  pi = cpi;
			  }
		  }
		  
		  
		  //System.out.println("CMTSystem:  calculating system PI..." + pi);
		  this.pi = pi;
		  return pi;
		  
		  */
	  }
	  
	  /**
	   * <code>getSystemThreatLevel</code>
	   * @return double
	   * The system threat level is the sum of all component threat levels divided by the number of components
	   */
	  public double getSystemThreatLevel() {
		  //System.out.println("CMTSystem:  getSystemThreatLevel");
		  double stl=0;
		  //int i;
		  if (this.impactedComponents.size() > 0) {
			  //System.out.println("here");
			  for (int i=0; i < this.impactedComponents.size(); i++) {
				  SystemComponent sc = (SystemComponent)impactedComponents.get(i);			  
				  if (sc != null) {
				  //	System.out.println("CMTSystem:  component is " + sc.getName());
					  double tl = sc.getThreatLevel();
				  //	System.out.println("CMTSystem:  weighted pi is " + cpi);
				  //	pi = pi + cpi;
					  if (tl > stl)
						  stl = tl;
				  }
			  }
		  } else {
			  SystemComponent sc = (SystemComponent)getComponent(getSelectedComponent()); 	  
			  //int ctl = sc.getThreatLevel();
			  if (sc != null)
				  stl = sc.getThreatLevel();			  
		  }
		  //System.out.println("system threat level is " + stl);
		  /*
		  //if (components.size() > 0) {
			//for (i=0; i < components.size(); i ++) {
			  //SystemComponent sc = (SystemComponent)components.get(i);
		  if (getSelectedComponent() != null) {
		  SystemComponent sc = (SystemComponent)getComponent(getSelectedComponent()); 	  
			  //int ctl = sc.getThreatLevel();
			  tl = sc.getThreatLevel();
			  System.out.println("component " + sc.getName() + " and threat level " + sc.getThreatLevel());
		  }
			  //tl = tl + ctl;
		  	//}
			
		  	//return  tl/i;
		  //}
		  return tl;
		  */
		  return stl;
	  }
	    
  
	  /**
	   * <code>setComponentParametersFile</code>
	   * @param compParams
	   * The XML file containing the default parameters for each component in the system
	   */
	  public void setComponentParametersFile(File compParams) {
		  loadComponentParameters(compParams);
	  }

	  
	  public String getImageFile() {
		  //System.out.println("CMTSystem:  getImageFile");
		  return this.img;
	  }
	  
	  public void setImageFile(String img) {
		  //System.out.println("CMTSystem:  setImageFile");
		  this.img = img;
	  }
	  
	  /**
	   * <code>getRectFor</code>
	   * @param name String - name of the component
	   * returns the rectangle coordinates for the component name
	   */
	  public Rectangle getRectFor(String name) {
		  //System.out.println("CMTSystem:  getRectFor");
		  //return (Rectangle)coords.get(name);
		  return null;
	  }
	  
	  /**
	   * <code>getCoords</code>
	   * returns the hashmap containing all the coordinates of the rectangles for the system image
	   */
	  //public HashMap getCoords() {
	  	 // return (HashMap)this.coords;
	  //}
	  
	  /**
	   * <code>loadComponentParameters</code>
	   * @param f File
	   * loads the parameters for each component in the system
	   */
 	   public void loadComponentParameters(File f) {
 		   components = new HashMap();
 		   loads = new HashMap();

   		   int utilityWorkerLevel = 0;
 		   int faunaLevel = 0;
 		   int threatLevel = 0;
 		   
 		   
 		   try {
 	          DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
 	          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
 	          Document doc = docBuilder.parse(f);

 	          // normalize text representation            doc.getDocumentElement ().normalize ();
 	          //System.out.println ("Root element of the doc is " + 
 	           //    doc.getDocumentElement().getNodeName());


 	          NodeList listOfHotspots = doc.getElementsByTagName("Hotspot");
 	          int totalHotspots = listOfHotspots.getLength();
 	          //System.out.println("Total no of hotspots : " + totalHotspots);

 	          for(int s=0; s<listOfHotspots.getLength() ; s++){
 	              Node hotspotNode = listOfHotspots.item(s);
 	              if(hotspotNode.getNodeType() == Node.ELEMENT_NODE){

 	                  Element hotspotElement = (Element)hotspotNode;
 	                  int x1=0, x2=0, y1=0, y2=0, outageHours=0, repairCost=0, numResidential=0,
 	                  	numCommercial=0, numSMIndustrial=0, numLIndustrial=0;
 	                  double randomFailureRate = 0;
 	                  String type=null, name=null, description=null;
 	                  //NodeList propertyNode = hotspotElement.getElementsByTagName("Type");
 	                  NodeList propertyNodeList = hotspotElement.getChildNodes();
 	                  for (int j=0; j < propertyNodeList.getLength(); j++) {
 	                	  Node propertyNode = propertyNodeList.item(j);
 	                	  
 	                	  if (propertyNode.getNodeType() == Node.ELEMENT_NODE) { 	
 	                		  Element propertyElement = (Element)propertyNode;
 	                  
 	                		  String tag = propertyElement.getTagName();
 	                		  String value = propertyElement.getTextContent();
 	             //   		  System.out.println("Property " + tag + ": " + value);
 	                		  
 	                		  if (tag == "Name") {
 	                			  name = value;
 	                		  }else if (tag == "Type") {
 	                			  type = value;
 	                		  }else if (tag == "Description") {
 	                			  description = value;
 	                		  }else if (tag == "X1") {
 	                			  x1 = Integer.parseInt(value);
 	                		  }else if (tag == "X2") {
 	                			  y1 = Integer.parseInt(value); 	                			  
 	                		  }else if (tag == "Y1") {
 	                			  x2 = Integer.parseInt(value); 	                			  
 	                		  }else if (tag == "Y2") {
 	                			  y2 = Integer.parseInt(value); 	                			  
 	                		  }else if (tag == "OutageHours") {
 	                			  outageHours = Integer.parseInt(value);
 	                		  }else if (tag == "RandomFailureRate") {
 	                			  randomFailureRate = Double.parseDouble(value); 	                			  
 	                		  }else if (tag == "RepairCost") {
 	                			  repairCost = Integer.parseInt(value);
 	                		  }else if (tag == "Residential") {
 	                			  numResidential = Integer.parseInt(value);
 	                		  }else if (tag == "Commercial") {
 	                			  numCommercial = Integer.parseInt(value);
 	                		  }else if (tag == "SMIndustrial") {
 	                			  numSMIndustrial = Integer.parseInt(value);
 	                		  }else if (tag == "LIndustrial") {
 	                			  numLIndustrial = Integer.parseInt(value);
 	                		  }else if (tag == "UtilityWorkerLevel") {
 	 	                		  utilityWorkerLevel = Integer.parseInt(value);
 	                		  }else if (tag == "FaunaLevel") {
 	                			  faunaLevel = Integer.parseInt(value);
 	                		  }else if (tag == "Susceptibility"){
 	                			  threatLevel = Integer.parseInt(value);
 	                		  }
 	                		  
 	                		  
 	                		  Rectangle rect = new Rectangle(x1,y1,x2-x1,y2-y1);

 	                		  SystemComponent sc = new SystemComponent(name, description, performanceMeasures, constructedScales, rect);
 	                		  sc.setType(type);
 	                		  sc.setTime(outageHours);
 	                		  sc.setRandomFailureRate(randomFailureRate);
 	                		  sc.setNumResidential(numResidential);
 	                		  sc.setNumCommerical(numCommercial);
 	                		  sc.setNumSmallMedIndustrial(numSMIndustrial);
 	                		  sc.setNumLargeIndustrial(numLIndustrial);
 	                		  sc.setRepairCost(repairCost); 							
 	                		  sc.setUtilityWorkerLevel(utilityWorkerLevel);
 	                		  sc.setFaunaLevel(faunaLevel);
 	                		  sc.setThreatLevel(threatLevel);
 	                		  components.put(name, sc);
 	                		  if (type == "Load") {
 								loads.put(name, sc);
 	                		  }
 	                		  //coords.put(name, rect);

 	                	  }
 	                  }
 	              }
 	          }

 			   
	       }catch (SAXParseException err) {
 		      System.out.println ("** Parsing error" + ", line " 
 		         + err.getLineNumber () + ", uri " + err.getSystemId ());
 		      System.out.println(" " + err.getMessage ());

 		   }catch (SAXException e) {
 		      Exception x = e.getException ();
 		      ((x == null) ? e : x).printStackTrace ();

 		   }catch (Throwable t) {
 		      t.printStackTrace ();
 		   }

			
			/*
			
			try {
				BufferedReader in = new BufferedReader(new FileReader(f));
				// ignore the first line of input
				String str = in.readLine();

				while ((str = in.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(str);
					String name = st.nextToken(" =");
					int x1 = Integer.parseInt(st.nextToken("= "));
					int y1 = Integer.parseInt(st.nextToken(" "));
					int x2 = Integer.parseInt(st.nextToken(" "));
					int y2 = Integer.parseInt(st.nextToken(" "));
					int time = Integer.parseInt(st.nextToken(" "));
					int numR = Integer.parseInt(st.nextToken(" "));
					int numC = Integer.parseInt(st.nextToken(" "));
					int numSMI = Integer.parseInt(st.nextToken(" "));
					int numLI = Integer.parseInt(st.nextToken(" "));
					int repairCost = Integer.parseInt(st.nextToken(" "));
					int utilityWorkerLevel = Integer.parseInt(st.nextToken(" "));
					int faunaLevel = Integer.parseInt(st.nextToken(" "));
					double threatLevel = Double.parseDouble(st.nextToken(" "));
					//System.out.println("Component: " + name + "  time:" + time + "  repairCost:" + repairCost+ "  tl:" + threatLevel);
					
					//System.out.println("next token is " + st.nextToken("= "));
					Rectangle rect = new Rectangle(x1, y1, x2-x1, y2-y1);
					String type = "";
					String desc = "";
					char c = name.charAt(0);
					if (c == 'T') {
						type = "Transmission line";
					} else if (c == 'B') {
						type = "Bus";
					} else if (c == 'L') {
						type = "Load";
					} else if (c == 'G') {
						type = "Generator";
					}
					desc = type + ' ' + name.substring(1);
					SystemComponent sc = new SystemComponent(name, desc, performanceMeasures, constructedScales, rect);
					sc.setType(type);
					sc.setTime(time);
					sc.setNumResidential(numR);
					sc.setNumCommerical(numC);
					sc.setNumSmallMedIndustrial(numSMI);
					sc.setNumLargeIndustrial(numLI);
					sc.setRepairCost(repairCost);
					sc.setUtilityWorkerLevel(utilityWorkerLevel);
					sc.setFaunaLevel(faunaLevel);
					sc.setThreatLevel(threatLevel);
					components.put(name, sc);
					if (type == "Load") {
						loads.put(name, sc);
					}
					//coords.put(name, rect);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			*/
		}	  

 	   
 	   public void loadSystemParameters(File f) {
/* 		   systemParameters = new HashMap();		   
 		   
 		   try {
 	          DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
 	          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
 	          Document doc = docBuilder.parse(f);

 	          // normalize text representation            doc.getDocumentElement ().normalize ();
 	          System.out.println ("Root element of the doc is " + 
 	               doc.getDocumentElement().getNodeName());


 	          NodeList listOfParameters = doc.getElementsByTagName("Parameters");
 	          int totalParameters = listOfParameters.getLength();
 	          System.out.println("Total no of parameters : " + totalParameters);

 	          for(int s=0; s<listOfParameters.getLength() ; s++){
 	              Node parameterNode = listOfParameters.item(s);
 	              if(parameterNode.getNodeType() == Node.ELEMENT_NODE){

 	                  Element parameterElement = (Element)parameterNode;
 	                  int x1=0, x2=0, y1=0, y2=0, outageHours=0, repairCost=0, numResidential=0,
 	                  	numCommercial=0, numSMIndustrial=0, numLIndustrial=0;
 	                  String type=null, name=null, description=null;
 	                  //NodeList propertyNode = hotspotElement.getElementsByTagName("Type");
 	                  NodeList propertyNodeList = hotspotElement.getChildNodes();
 	                  for (int j=0; j < propertyNodeList.getLength(); j++) {
 	                	  Node propertyNode = propertyNodeList.item(j);
 	                	  
 	                	  if (propertyNode.getNodeType() == Node.ELEMENT_NODE) { 	
 	                		  Element propertyElement = (Element)propertyNode;
 	                  
 	                		  String tag = propertyElement.getTagName();
 	                		  String value = propertyElement.getTextContent();
 	                		  System.out.println("Property " + tag + ": " + value);
 	                		  
 	                		  if (tag == "Name") {
 	                			  name = value;
 	                		  }else if (tag == "Type") {
 	                			  type = value;
 	                		  }else if (tag == "Description") {
 	                			  description = value;
 	                		  }else if (tag == "X1") {
 	                			  x1 = Integer.parseInt(value);
 	                		  }else if (tag == "X2") {
 	                			  y1 = Integer.parseInt(value); 	                			  
 	                		  }else if (tag == "Y1") {
 	                			  x2 = Integer.parseInt(value); 	                			  
 	                		  }else if (tag == "Y2") {
 	                			  y2 = Integer.parseInt(value); 	                			  
 	                		  }else if (tag == "OutageHours") {
 	                			  outageHours = Integer.parseInt(value);
 	                		  }else if (tag == "RepairCost") {
 	                			  repairCost = Integer.parseInt(value);
 	                		  }else if (tag == "Residential") {
 	                			  numResidential = Integer.parseInt(value);
 	                		  }else if (tag == "Commercial") {
 	                			  numCommercial = Integer.parseInt(value);
 	                		  }else if (tag == "SMIndustrial") {
 	                			  numSMIndustrial = Integer.parseInt(value);
 	                		  }else if (tag == "LIndustrial") {
 	                			  numLIndustrial = Integer.parseInt(value);
 	                		  }else if (tag == "UtilityWorkerLevel") {
 	 	                		  utilityWorkerLevel = Integer.parseInt(value);
 	                		  }else if (tag == "FaunaLevel") {
 	                			  faunaLevel = Integer.parseInt(value);
 	                		  }else if (tag == "Susceptibility"){
 	                			  threatLevel = Integer.parseInt(value);
 	                		  }
 	                		  
 	                		  
 	                		  Rectangle rect = new Rectangle(x1,y1,x2-x1,y2-y1);

 	                		  SystemComponent sc = new SystemComponent(name, description, performanceMeasures, constructedScales, rect);
 	                		  sc.setType(type);
 	                		  sc.setTime(outageHours);
 	                		  sc.setNumResidential(numResidential);
 	                		  sc.setNumCommerical(numCommercial);
 	                		  sc.setNumSmallMedIndustrial(numSMIndustrial);
 	                		  sc.setNumLargeIndustrial(numLIndustrial);
 	                		  sc.setRepairCost(repairCost); 							
 	                		  sc.setUtilityWorkerLevel(utilityWorkerLevel);
 	                		  sc.setFaunaLevel(faunaLevel);
 	                		  sc.setThreatLevel(threatLevel);
 	                		  components.put(name, sc);
 	                		  if (type == "Load") {
 								loads.put(name, sc);
 	                		  }
 	                		  //coords.put(name, rect);

 	                	  }
 	                  }
 	              }
 	          }

 			   
	       }catch (SAXParseException err) {
 		      System.out.println ("** Parsing error" + ", line " 
 		         + err.getLineNumber () + ", uri " + err.getSystemId ());
 		      System.out.println(" " + err.getMessage ());

 		   }catch (SAXException e) {
 		      Exception x = e.getException ();
 		      ((x == null) ? e : x).printStackTrace ();

 		   }catch (Throwable t) {
 		      t.printStackTrace ();
 		   }

			
			/*
			
			try {
				BufferedReader in = new BufferedReader(new FileReader(f));
				// ignore the first line of input
				String str = in.readLine();

				while ((str = in.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(str);
					String name = st.nextToken(" =");
					int x1 = Integer.parseInt(st.nextToken("= "));
					int y1 = Integer.parseInt(st.nextToken(" "));
					int x2 = Integer.parseInt(st.nextToken(" "));
					int y2 = Integer.parseInt(st.nextToken(" "));
					int time = Integer.parseInt(st.nextToken(" "));
					int numR = Integer.parseInt(st.nextToken(" "));
					int numC = Integer.parseInt(st.nextToken(" "));
					int numSMI = Integer.parseInt(st.nextToken(" "));
					int numLI = Integer.parseInt(st.nextToken(" "));
					int repairCost = Integer.parseInt(st.nextToken(" "));
					int utilityWorkerLevel = Integer.parseInt(st.nextToken(" "));
					int faunaLevel = Integer.parseInt(st.nextToken(" "));
					double threatLevel = Double.parseDouble(st.nextToken(" "));
					//System.out.println("Component: " + name + "  time:" + time + "  repairCost:" + repairCost+ "  tl:" + threatLevel);
					
					//System.out.println("next token is " + st.nextToken("= "));
					Rectangle rect = new Rectangle(x1, y1, x2-x1, y2-y1);
					String type = "";
					String desc = "";
					char c = name.charAt(0);
					if (c == 'T') {
						type = "Transmission line";
					} else if (c == 'B') {
						type = "Bus";
					} else if (c == 'L') {
						type = "Load";
					} else if (c == 'G') {
						type = "Generator";
					}
					desc = type + ' ' + name.substring(1);
					SystemComponent sc = new SystemComponent(name, desc, performanceMeasures, constructedScales, rect);
					sc.setType(type);
					sc.setTime(time);
					sc.setNumResidential(numR);
					sc.setNumCommerical(numC);
					sc.setNumSmallMedIndustrial(numSMI);
					sc.setNumLargeIndustrial(numLI);
					sc.setRepairCost(repairCost);
					sc.setUtilityWorkerLevel(utilityWorkerLevel);
					sc.setFaunaLevel(faunaLevel);
					sc.setThreatLevel(threatLevel);
					components.put(name, sc);
					if (type == "Load") {
						loads.put(name, sc);
					}
					//coords.put(name, rect);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			*/
		}	  
 	   
 	   
 	   
 	   
	  /**
	   * <code>getLowestLevelCriteria</code>
	   * @param c Criteria
	   * @return Criteria
	   * gets the lowest level criteria in the hierarchy
	   */
	  private Criteria getLowestLevelCriteria(Criteria c) {
		//  System.out.println("CMTSystem:  getLowestLevelCriteria");		  
	  	Vector crit = c.getSubCriteria();
	  	if (crit != null) {
	  		for (int i=0; i < crit.size(); i++) {
	  			return getLowestLevelCriteria((Criteria)crit.elementAt(i));
	  		}
	  	}
	  	return c.getParent();
	  }
	
	  public void setNumberCommercialCustomersAffected() {
		  int numCustomers = 0;
		  // for each affected component, get the number of affected customers
		  for (int i=0; i < impactedComponents.size(); i++) {
			  SystemComponent sc = (SystemComponent)impactedComponents.get(i);
			  HashMap map = sc.getLoadShed();
			  
			  // each affected component will have a list of buses and a percent 
			  // of load to shed
			  Iterator it = map.keySet().iterator();
			  //int numCommercial = 0;
			  while(it.hasNext()) {
				  String key = (String)it.next();
				  SystemComponent comp = this.getComponent(key);
				  String val = map.get(key).toString();
				  double percent = Double.parseDouble(val)/100;
				  double num = comp.getNumCommercial() * percent;
				  comp.setNumCommercialAffected((int)num);
				  numCustomers = numCustomers + (int)num;
			  }
			  sc.setNumCommercialAffected(numCustomers);				  
		  }
		  //System.out.println("======> Number Commercial:" + numCustomers);
	  }
	  
	  public void setNumberResidentialCustomersAffected() {
		  int numCustomers = 0;
		  // for each affected component, get the number of affected customers
		  for (int i=0; i < impactedComponents.size(); i++) {
			  SystemComponent sc = (SystemComponent)impactedComponents.get(i);
			  HashMap map = sc.getLoadShed();
			  
			  // each affected component will have a list of buses and a percent 
			  // of load to shed
			  Iterator it = map.keySet().iterator();
			  //int numResidential = 0;
			  while(it.hasNext()) {
				  String key = (String)it.next();
				  SystemComponent comp = this.getComponent(key);
				  String val = map.get(key).toString();
				  double percent = Double.parseDouble(val)/100;
				  double num = comp.getNumResidential() * percent;
				  comp.setNumResidentialAffected((int)num);
				  numCustomers = numCustomers + (int)num;

			  }
			  sc.setNumResidentialAffected(numCustomers);				  
		  }
	  }
	  
	  public void setNumberSmIndustrialCustomersAffected() {
		  int numCustomers = 0;
		  // for each affected component, get the number of affected customers
		  for (int i=0; i < impactedComponents.size(); i++) {
			  SystemComponent sc = (SystemComponent)impactedComponents.get(i);
			  HashMap map = sc.getLoadShed();
			  
			  // each affected component will have a list of buses and a percent 
			  // of load to shed
			  Iterator it = map.keySet().iterator();
			  //int numSmallMediumIndustrial = 0;
			  while(it.hasNext()) {
				  String key = (String)it.next();
				  SystemComponent comp = this.getComponent(key);
				  String val = map.get(key).toString();
				  double percent = Double.parseDouble(val)/100;
				  double num = comp.getNumSmallMedIndustrial() * percent;
				  comp.setNumSmallMedIndustrialAffected((int)num);
				  numCustomers = numCustomers + (int)num;
			  }
			  sc.setNumSmallMedIndustrialAffected(numCustomers);
		  }
	  }
	  
	  public void setNumberLgIndustrialCustomersAffected() {
		  int numCustomers = 0;
		  // for each affected component, get the number of affected customers
		  for (int i=0; i < impactedComponents.size(); i++) {
			  SystemComponent sc = (SystemComponent)impactedComponents.get(i);
			  HashMap map = sc.getLoadShed();
			  
			  // each affected component will have a list of buses and a percent 
			  // of load to shed
			  Iterator it = map.keySet().iterator();
			  //int numLargeIndustrial = 0;
			  while(it.hasNext()) {
				  String key = (String)it.next();
				  SystemComponent comp = this.getComponent(key);
				  String val = map.get(key).toString();
				  double percent = Double.parseDouble(val)/100;
				  double num = comp.getNumLargeIndustrial() * percent;
				  comp.setNumLargeIndustrialAffected((int)num);
				  numCustomers = numCustomers + (int)num;
			  }
			  sc.setNumLargeIndustrialAffected(numCustomers);				  
		  }
	  }
	  
	  // HARD CODED
	  public int getLostRevenue(SystemComponent sc){  
		  return sc.getLostRevenue();
		  
	  }
	  
	  public Vector getCustomerGroups() {
		  return this.customerGroups;
	  }
	  public void setCustomerGroups(Vector customerGroups) {
		  this.customerGroups = customerGroups;
	  }
	  public void addCustomerGroup(CustomerGroup cg) {
		  this.customerGroups.add(cg);
	  }
	  public void delCustomerGroup(CustomerGroup cg) {
		  this.customerGroups.remove(cg);
	  }
	  public int getNumCustomerGroups() {
		  return this.customerGroups.size();
	  }
	  
	  
	  /**
	   * <code>main</code>
	   * @param args
	   * Used for testing.
	   */
	  public static void main(String[] args) {
		      		  
		    // create a frame
			ConsequenceModelingExample test = new ConsequenceModelingExample();
		    Hierarchy h =new Hierarchy();
		    h=test.getHierarchyExample();
		    CMTSystem m = new CMTSystem(h);

		    m.setImageFile("C:/Documents and Settings/lmchave/Desktop/SCADA Tool/images/IEEE RTS-96 Grid.jpg");
			m.setComponentParametersFile(new File("C:/Documents and Settings/lmchave/Desktop/SCADA Tool/ImageMapper/v2/IEEE RTS-96 Grid.xml"));

		    SystemLevels lvls = new SystemLevels();
		    m.setPerformanceLevels(lvls.getPerformanceLevels());
		    m.setSystemHealthLevels(lvls.getSystemHealthLevels());
		    m.setSelectedComponent("T4");
		    
		    m.getSystemPI();
			
			
		    //Vector comps = new Vector();
		    
		    //SystemComponent c1=new SystemComponent("T1", "Transmission line 1", m.getPerformanceMeasures(), m.getConstructedScales(), new Rectangle());
		    //SystemComponent c2=new SystemComponent("T2", "Transmission line 2", m.getPerformanceMeasures(), m.getConstructedScales(), null);
		    
		    //comps.add(c1);
		    //comps.add(c2);
		    
		    //m.setSystemComponents(comps);
		    
		    
		    //System.out.println(c1.print());
		    //System.out.println(c2.print());
		    
		    //System.out.println("Performance Index is of c1 is " + c1.getPI() + " and weighted pi is " + c1.getWeightedPI() + " and threat level is " + c1.getThreatLevel());	    
		    //System.out.println("Performance Index is of c2 is " + c2.getPI() + " and weighted pi is " + c2.getWeightedPI() + " and threat level is " + c2.getThreatLevel());
		    
		    //System.out.println("System performance index is " + m.getSystemPI());
		    //System.out.println("System threat level is " + m.getSystemThreatLevel());
		    
		    //m.getLostRevenue(sc);
	  }
		    
	
}
