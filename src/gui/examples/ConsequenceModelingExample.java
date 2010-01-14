package gui.examples;

import java.util.*;
import java.io.*;
import java.net.*;

import adt.ahp.Alternative;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import adt.ahp.PairwiseComparisonMatrix;

import Jama.*;

public class ConsequenceModelingExample {

	  /**
	   *  Constructs the hierarchy used in the paper Bulk Power Grid Risk Analysis:
	   *  Ranking Infrastructure Elements According to their Risk Significance
	   *  -- A.M. Konce, G.E. Apostolakis, B.K. Cook
	   */
	  public Hierarchy getHierarchyExample() {
	    // build a tested hierarchy H={G,A1,A2}
	    Hierarchy h = new Hierarchy("Value", "Overall value to Stakeholder");
	      
	    //build and add impact categories
	    Vector criteria = new Vector();
	    Criteria c1=new Criteria("Economics","Economics", h.getGoal());
	    criteria.add(c1);
	    
	    //Specify the performance measures
	    Vector ecoPM = new Vector();
	    Criteria eco1 = new Criteria("Lost Revenue","Lost Revenue", c1);
	    Criteria eco2 = new Criteria("Repair/Replace","Repair/Replace", c1);
	    ecoPM.add(eco1);
	    ecoPM.add(eco2);
	    c1.setSubCriteria(ecoPM);
	    
	    // specify the constructed scales
	    Vector eco1CS = new Vector();
	    Alternative eco1cs1 = new Alternative("Level 6", "Hundreds of Millions of Dollars", eco1);
	    Alternative eco1cs2 = new Alternative("Level 5", "Tens of Millions of Dollars", eco1);
	    Alternative eco1cs3 = new Alternative("Level 4", "Millions of Dollars", eco1);
	    Alternative eco1cs4 = new Alternative("Level 3", "Hundreds of Thousands of Dollars", eco1);
	    Alternative eco1cs5 = new Alternative("Level 2", "Tens of Thousands of Dollars", eco1);
	    Alternative eco1cs6 = new Alternative("Level 1", "Thousands of Dollars", eco1);
	    Alternative eco1cs7 = new Alternative("Level 0", "No Impact", eco1);
	    eco1CS.add(eco1cs1);
	    eco1CS.add(eco1cs2);
	    eco1CS.add(eco1cs3);
	    eco1CS.add(eco1cs4);
	    eco1CS.add(eco1cs5);
	    eco1CS.add(eco1cs6);
	    eco1CS.add(eco1cs7);
	    eco1.setAlternatives(eco1CS);
    
	    Vector eco2CS = new Vector();
	    Alternative eco2cs1 = new Alternative("Level 6","Hundreds of Millions of Dollars", eco2);
	    Alternative eco2cs2 = new Alternative("Level 5","Tens of Millions of Dollars", eco2);
	    Alternative eco2cs3 = new Alternative("Level 4","Millions of Dollars", eco2);
	    Alternative eco2cs4 = new Alternative("Level 3","Hundreds of Thousands of Dollars", eco2);
	    Alternative eco2cs5 = new Alternative("Level 2","Tens of Thousands of Dollars", eco2);
	    Alternative eco2cs6 = new Alternative("Level 1","Thousands of Dollars", eco2);
	    Alternative eco2cs7 = new Alternative("Level 0","No Impact", eco2);
	    eco2CS.add(eco2cs1);
	    eco2CS.add(eco2cs2);
	    eco2CS.add(eco2cs3);
	    eco2CS.add(eco2cs4);
	    eco2CS.add(eco2cs5);
	    eco2CS.add(eco2cs6);
	    eco2CS.add(eco2cs7);
	    
	    eco2.setAlternatives(eco2CS);
	       
	    Criteria c2=new Criteria("Image", h.getGoal());
	    criteria.add(c2);
	    
	    //Specify the performance measures
	    Vector imagePM = new Vector();
	    Criteria image1 = new Criteria("Public","Public", c2);
	    Criteria image2 = new Criteria("Customer","Customer", c2);
	    Criteria image3 = new Criteria("Political","Political", c2);
	    imagePM.add(image1);
	    imagePM.add(image2);
	    imagePM.add(image3);
	    c2.setSubCriteria(imagePM);
	    
	    // specify the constructed scales
	    Vector image1CS = new Vector();
	    Alternative image1cs1 = new Alternative("Level 4", "International media interest", image1);
	    Alternative image1cs2 = new Alternative("Level 3", "Repeated publications in national media", image1);
	    Alternative image1cs3 = new Alternative("Level 2", "Repeated publications in local media, appearance in national media", image1);
	    Alternative image1cs4 = new Alternative("Level 1", "Single appearance in local media", image1);
	    Alternative image1cs5 = new Alternative("Level 0", "No impact", image1);
	    image1CS.add(image1cs1);
	    image1CS.add(image1cs2);
	    image1CS.add(image1cs3);
	    image1CS.add(image1cs4);
	    image1CS.add(image1cs5);
	    image1.setAlternatives(image1CS);
	        
	    Vector image2CS = new Vector();
	    Alternative image2cs1 = new Alternative("Level 5", "Billions of Dollars", image2);
	    Alternative image2cs2 = new Alternative("Level 4", "Hundreds of Millions of Dollars", image2);
	    Alternative image2cs3 = new Alternative("Level 3", "Tens of Millions of Dollars", image2);
	    Alternative image2cs4 = new Alternative("Level 2", "Millions of Dollars", image2);
	    Alternative image2cs5 = new Alternative("Level 1", "Hundreds of Thousands of Dollars", image2);
	    Alternative image2cs6 = new Alternative("Level 0", "No impact", image2);
	    image2CS.add(image2cs1);
	    image2CS.add(image2cs2);
	    image2CS.add(image2cs3);
	    image2CS.add(image2cs4);
	    image2CS.add(image2cs5);
	    image2CS.add(image2cs6);
	    image2.setAlternatives(image2CS);
	    
	    Vector image3CS = new Vector();
	    Alternative image3cs1 = new Alternative("Level 3", "Political push for major regulation reform", image3);
	    Alternative image3cs2 = new Alternative("Level 2", "Moderate political push for additional regulations", image3);
	    Alternative image3cs3 = new Alternative("Level 1", "Low political influence on industry regulations", image3);
	    Alternative image3cs4 = new Alternative("Level 0", "No impact", image3);
	    image3CS.add(image3cs1);
	    image3CS.add(image3cs2);
	    image3CS.add(image3cs3);
	    image3CS.add(image3cs4);	    
	    image3.setAlternatives(image3CS);    
	        
	    Criteria c3=new Criteria("Health & Safety","Health & Safety", h.getGoal());
	    criteria.add(c3);
	    
	    //Specify the performance measures
	    Vector hsPM = new Vector();
	    Criteria hs1 = new Criteria("General Public", "General Public", c3);
	    Criteria hs2 = new Criteria("Utility Workers", "Utility Workers", c3);
	    hsPM.add(hs1);
	    hsPM.add(hs2);
	    c3.setSubCriteria(hsPM);
	    
	    // specify constructed scales
	    Vector hs1CS = new Vector();
	    Alternative hs1cs1 = new Alternative("Level 5", "Numerous deaths attributed to power outage", hs1);
	    Alternative hs1cs2 = new Alternative("Level 4", "Few deaths attribued to power outage", hs1);
	    Alternative hs1cs3 = new Alternative("Level 3", "Numerous long-term injuries related to power outage", hs1);
	    Alternative hs1cs4 = new Alternative("Level 2", "Few long-term injuries/numerous short-term injuries related to power outage", hs1);
	    Alternative hs1cs5 = new Alternative("Level 1", "Few short-term injuries related to power outage", hs1);
	    Alternative hs1cs6 = new Alternative("Level 0", "No impact", hs1);
	    hs1CS.add(hs1cs1);
	    hs1CS.add(hs1cs2);
	    hs1CS.add(hs1cs3);
	    hs1CS.add(hs1cs4);
	    hs1CS.add(hs1cs5);	   
	    hs1CS.add(hs1cs6);
	    hs1.setAlternatives(hs1CS);
	    	    
	    Vector hs2CS = new Vector();
	    Alternative hs2cs1 = new Alternative("Level 3", "High safety impact on worker associated with repairs", hs2);
	    Alternative hs2cs2 = new Alternative("Level 2", "Moderate safety impact on worker associated with repairs", hs2);
	    Alternative hs2cs3 = new Alternative("Level 1", "Low safety impact on worker associated with repairs", hs2);
	    Alternative hs2cs4 = new Alternative("Level 0", "No impact", hs2);
	    hs2CS.add(hs2cs1);
	    hs2CS.add(hs2cs2);
	    hs2CS.add(hs2cs3);
	    hs2CS.add(hs2cs4);
	    hs2.setAlternatives(hs2CS);	    
    
	    Criteria c4=new Criteria("Environment", "Environment", h.getGoal());
	    criteria.add(c4);
	    
	    //Specify the performance measures
	    Vector envPM = new Vector();
	    Criteria env1 = new Criteria("Fauna","Fauna", c4);
	    envPM.add(env1);
	    c4.setSubCriteria(envPM);
	    
	    h.setCriteria(criteria);
	    
	    // specify the constructed scale
	    Vector env1CS = new Vector();
	    Alternative env1cs1 = new Alternative("Level 3", "Extensive impact on wildlife, decades required for full recovery", env1);
	    Alternative env1cs2 = new Alternative("Level 2", "Moderate impact on wildlife, few years required for full recovery", env1);
	    Alternative env1cs3 = new Alternative("Level 1", "Minor impact on wildlife, recovers quickly with no lingering impacts", env1);
	    Alternative env1cs4 = new Alternative("Level 0", "No impact", env1);
	    env1CS.add(env1cs1);
	    env1CS.add(env1cs2);
	    env1CS.add(env1cs3);
	    env1CS.add(env1cs4);	    
	    env1.setAlternatives(env1CS);
	    
	    PairwiseComparisonMatrix P=h.getGoal().getComparisonMatrix();
	    // starting with our Criteria
	    // Economic, Image, Health & Safety, and Environment
	    P.set(0,0,1);
	    P.set(0,1,.25);
	    P.set(0,2,2);
	    P.set(0,3,4);
	    P.set(1,0,4);
	    P.set(1,1,1);
	    P.set(1,2,2);
	    P.set(1,3,4);
	    P.set(2,0,.5);
	    P.set(2,1,.5);
	    P.set(2,2,1);
	    P.set(2,3,2);
	    P.set(3,0,.25);
	    P.set(3,1,.25);
	    P.set(3,2,.5);
	    P.set(3,3,1);
	    
	    
	    PairwiseComparisonMatrix eco = c1.getComparisonMatrix();
	    eco.set(0,0,1);
	    eco.set(0,1,6);
	    eco.set(1,0,.1667);
	    eco.set(1,1,1);

	    PairwiseComparisonMatrix e1 = eco1.getComparisonMatrix();
	    e1.set(0,0,1);
	    e1.set(0,1,3);
	    e1.set(0,2,4);
	    e1.set(0,3,6);
	    e1.set(0,4,7);
	    e1.set(0,5,8);
	    e1.set(0,6,9);
	    e1.set(1,1,1);
	    e1.set(1,2,2);
	    e1.set(1,3,4);
	    e1.set(1,4,6);
	    e1.set(1,5,7);
	    e1.set(1,6,8);
	    e1.set(2,2,1);
	    e1.set(2,3,3);
	    e1.set(2,4,5);
	    e1.set(2,5,6);
	    e1.set(2,6,7);
	    e1.set(3,3,1);
	    e1.set(3,4,3);
	    e1.set(3,5,4);
	    e1.set(3,6,6);
	    e1.set(4,4,1);
	    e1.set(4,5,2);
	    e1.set(4,6,4);
	    e1.set(5,5,1);
	    e1.set(5,6,3);
	    e1.set(6,6,1);
	      
	    
	    
	    PairwiseComparisonMatrix e2 = eco2.getComparisonMatrix();
	    e2.set(0,0,1);
	    e2.set(0,1,3);
	    e2.set(0,2,4);
	    e2.set(0,3,6);
	    e2.set(0,4,7);
	    e2.set(0,5,8);
	    e2.set(0,6,9);
	    e2.set(1,1,1);
	    e2.set(1,2,2);
	    e2.set(1,3,4);
	    e2.set(1,4,6);
	    e2.set(1,5,7);
	    e2.set(1,6,8);
	    e2.set(2,2,1);
	    e2.set(2,3,3);
	    e2.set(2,4,5);
	    e2.set(2,5,6);
	    e2.set(2,6,7);
	    e2.set(3,3,1);
	    e2.set(3,4,3);
	    e2.set(3,5,4);
	    e2.set(3,6,6);
	    e2.set(4,4,1);
	    e2.set(4,5,2);
	    e2.set(4,6,4);
	    e2.set(5,5,1);
	    e2.set(5,6,3);
	    e2.set(6,6,1);
	    
	    PairwiseComparisonMatrix image = c2.getComparisonMatrix();
	    image.set(0,0,1);
	    image.set(0,1,.25);
	    image.set(0,2,.1667);
	    //image.set(1,0,4);
	    image.set(1,1,1);
	    image.set(1,2,.1667);
	    //image.set(2,0,6);
	    //image.set(2,1,6);
	    image.set(2,2,1); 
	    
	    PairwiseComparisonMatrix i1 = image1.getComparisonMatrix();
	    i1.set(0,0,1);
	    i1.set(0,1,4);
	    i1.set(0,2,6);
	    i1.set(0,3,8);
	    i1.set(0,4,9);
	    
	    i1.set(1,1,1);
	    i1.set(1,2,5);
	    i1.set(1,3,6);
	    i1.set(1,4,8);
	    
	    i1.set(2,2,1);
	    i1.set(2,3,5);
	    i1.set(2,4,6);
	    
	    i1.set(3,3,1);
	    i1.set(3,4,4);
	    
	    i1.set(4,4,1);

	    PairwiseComparisonMatrix i2 = image2.getComparisonMatrix();
	    i2.set(0,0,1);
	    i2.set(0,1,3);
	    i2.set(0,2,4);
	    i2.set(0,3,6);
	    i2.set(0,4,7);
	    i2.set(0,5,9);
	    
	    i2.set(1,1,1);
	    i2.set(1,2,2);
	    i2.set(1,3,4);
	    i2.set(1,4,6);
	    i2.set(1,5,8);
	    
	    i2.set(2,2,1);
	    i2.set(2,3,3);
	    i2.set(2,4,5);
	    i2.set(2,5,7);
	    
	    i2.set(3,3,1);
	    i2.set(3,4,3);
	    i2.set(3,5,6);
	    
	    i2.set(4,4,1);
	    i2.set(4,5,4);
	    
	    i2.set(5,5,1);
	    
	    PairwiseComparisonMatrix i3 = image3.getComparisonMatrix();
	    i3.set(0,0,1);
	    i3.set(0,1,4);
	    i3.set(0,2,6);
	    i3.set(0,3,9);
	    
	    i3.set(1,1,1);
	    i3.set(1,2,3);
	    i3.set(1,3,8);
	    
	    i3.set(2,2,1);
	    i3.set(2,3,7);
	    
	    i3.set(3,3,1);
	    
	    PairwiseComparisonMatrix hs = c3.getComparisonMatrix();
	    hs.set(0,0,1);
	    hs.set(0,1,.25);
	    hs.set(1,0,4);
	    hs.set(1,1,1);
	    
	    PairwiseComparisonMatrix h1 = hs1.getComparisonMatrix();
	    h1.set(0,0,1);
	    h1.set(0,1,5);
	    h1.set(0,2,6);
	    h1.set(0,3,7);
	    h1.set(0,4,8);
	    h1.set(0,5,9);
	    
	    h1.set(1,1,1);
	    h1.set(1,2,5);
	    h1.set(1,3,6);
	    h1.set(1,4,7);
	    h1.set(1,5,8);
	    
	    h1.set(2,2,1);
	    h1.set(2,3,5);
	    h1.set(2,4,6);
	    h1.set(2,5,7);
	    
	    h1.set(3,3,1);
	    h1.set(3,4,5);
	    h1.set(3,5,6);
	    
	    h1.set(4,4,1);
	    h1.set(4,5,5);
	    
	    h1.set(5,5,1);
	    
	    PairwiseComparisonMatrix h2 = hs2.getComparisonMatrix();
	    h2.set(0,0,1);
	    h2.set(0,1,4);
	    h2.set(0,2,8);
	    h2.set(0,3,9);
	    
	    h2.set(1,1,1);
	    h2.set(1,2,7);
	    h2.set(1,3,8);
	    
	    h2.set(2,2,1);
	    h2.set(2,3,4);
	    
	    h2.set(3,3,1);
	    
	    PairwiseComparisonMatrix env = c4.getComparisonMatrix();
	    env.set(0,0,1);
	       
	    PairwiseComparisonMatrix en1 = env1.getComparisonMatrix();
	    en1.set(0,0,1);
	    en1.set(0,1,6);
	    en1.set(0,2,8);
	    en1.set(0,3,9);
	    
	    en1.set(1,1,1);
	    en1.set(1,2,5);
	    en1.set(1,3,6);
	    
	    en1.set(2,2,1);
	    en1.set(2,3,4);
	    
	    en1.set(3,3,1);
	    
	    // calculate the weights
	    h.calculateWeights(h.getGoal());

	    
	    
	    return h;

	  }
	  /*
	  public static void calculateWeights(Criteria c) {
		  
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
	  */

	  public static void main(String[] argv) {
	        
	    // build a tested hierarchy 
	    Hierarchy h = new Hierarchy();
	    ConsequenceModelingExample ex = new ConsequenceModelingExample();
	    h=ex.getHierarchyExample();
	    System.out.println("\t \t FINAL Hierarchy h: "+h.print());

	    
	   }
}