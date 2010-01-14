package gui.examples;

import java.util.*;
import java.io.*;
import java.net.*;

import adt.ahp.Alternative;
import adt.ahp.Criteria;
import adt.ahp.Hierarchy;
import adt.ahp.PairwiseComparisonMatrix;
import Jama.*;

public class AHPExample {

	  /**
	   *  Constructs a simple hierarchy  used to test.
	   */
	  public Hierarchy getHierarchyExample() {
	    // build a sample heirarchy
	    Hierarchy h = new Hierarchy("New Car", "Select a new vehicle.");

	    //System.out.println("Setup hierarchy:\n" + h.print());	    
	    //System.out.println("\n------------------------------------------------------\n");	    
	    
	    //build and add criteria Style, Reliability, Fuel Economy
	    Vector criteria = new Vector();
	    Criteria c1=new Criteria("Style","Style of vehicle", h.getGoal());
	    criteria.add(c1) ;
	    
	    // specify the alternatives
	    Vector alternatives1 = new Vector();
	    Alternative c1alt1 = new Alternative("Civic","style",c1);
	    alternatives1.add(c1alt1);
   
	    Alternative c1alt2 = new Alternative("Saturn","style",c1);
	    alternatives1.add(c1alt2);
	    
	    Alternative c1alt3 = new Alternative("Escort", "style", c1);
	    alternatives1.add(c1alt3);
    
	    Alternative c1alt4 = new Alternative("Clio", "style", c1);
	    alternatives1.add(c1alt4);
	    
	    c1.setAlternatives(alternatives1);	    
	    //System.out.println("Added Criteria Style\n" + c1.print());

	    //System.out.println("\n------------------------------------------------------\n");
	    //System.out.println("Now hierarchy is:\n" + h.print());
	    //System.out.println("\n------------------------------------------------------\n");	    
	    
	    
	    Criteria c2=new Criteria("Reliability","Reliability of vehicle", h.getGoal());
	    criteria.add(c2) ;

	    // specify the alternatives
	    Vector alternatives2 = new Vector();
	    Alternative c2alt1 = new Alternative("Civic","reliability",c2);
	    alternatives2.add(c2alt1);
   
	    Alternative c2alt2 = new Alternative("Saturn","reliability",c2);
	    alternatives2.add(c2alt2);
	    
	    Alternative c2alt3 = new Alternative("Escort","reliability",c2);
	    alternatives2.add(c2alt3);
    
	    Alternative c2alt4 = new Alternative("Clio","reliability",c2);
	    alternatives2.add(c2alt4);
	    
	    c2.setAlternatives(alternatives2);		    
	    //System.out.println("Added Criteria Reliability\n" + c2.print());

	    //System.out.println("\n------------------------------------------------------\n");
	    //System.out.println("Now hierarchy is:\n" + h.print());
	    //System.out.println("\n------------------------------------------------------\n");	    
	    
	    
	    Criteria c3=new Criteria("Fuel Economy","Fuel economy of vehicle", h.getGoal());
	    criteria.add(c3);
	    
	    // specify the alternatives
	    Vector alternatives3 = new Vector();
	    Alternative c3alt1 = new Alternative("Civic","fuel economy", c3);
	    alternatives3.add(c3alt1);
   
	    Alternative c3alt2 = new Alternative("Saturn","fuel economy", c3);
	    alternatives3.add(c3alt2);
	    
	    Alternative c3alt3 = new Alternative("Escort", "fuel economy", c3);
	    alternatives3.add(c3alt3);
    
	    Alternative c3alt4 = new Alternative("Clio", "fuel economy", c3);
	    alternatives3.add(c3alt4);
	    
	    c3.setAlternatives(alternatives3);		    
	    
	    //System.out.println("Added Criteria Fuel Economy\n" + c3.print());

	    //System.out.println("\n------------------------------------------------------\n");
	    //System.out.println("Now hierarchy is:\n" + h.print());
	    //System.out.println("\n------------------------------------------------------\n");	    
	    
	    
	    h.setCriteria(criteria);
	    
	    //System.out.println("Set Criteria\n");

	    //System.out.println("\n------------------------------------------------------\n");
	    //System.out.println("Now hierarchy is:\n" + h.print());
	    //System.out.println("\n------------------------------------------------------\n");	    
	    
	    
	    // setup the pairwise comparison matrix for criteria
	    //-------------------------------------------------------
	    
	    // reliability is 2 times as important as style
	    // style is 3 times as important as fuel economy
	    // reliability is 4 times as important as fuel economy
	    // matrix looks like
	    //
	    //					style	reliability		fuel economy
	    //	style			 1/1	    1/2				3/1
	    //  reliability		 2/1		1/1				4/1
	    //  fuel economy	 1/3		1/4				1/1
	    //
	    // converting fractions to decimals gives:
	    //
	    //					style	reliability		fuel economy
	    //	style			1.0000	   0.5000			3.0000
	    //  reliability		2.0000	   1.0000			4.0000
	    //  fuel economy	0.3333	   0.2500			1.0000
	    //
	    PairwiseComparisonMatrix m = h.getGoal().getComparisonMatrix();
	    m.set(0,0,1.0000);
	    m.set(0,1,0.5000);
	    m.set(0,2,3.0000);
	    //m.set(1,0,2.0000);
	    m.set(1,1,1.0000);
	    m.set(1,2,4.0000);
	    //m.set(2,0,0.3333);
	    //m.set(2,1,0.2500);
	    m.set(2,2,1.0000);
	    
	    // set the weights
	    //c1.setWeight(m.getWeight(0));
	    //c2.setWeight(m.getWeight(1));
	    //c3.setWeight(m.getWeight(2));
	    
	    PairwiseComparisonMatrix mC1 = c1.getComparisonMatrix();
	    mC1.set(0,0,1);
	    mC1.set(0,1,.25);
	    mC1.set(0,2,4);
	    mC1.set(0,3,.1667);
	    //mC1.set(1,0,4);
	    mC1.set(1,1,1);
	    mC1.set(1,2,4);
	    mC1.set(1,3,.25);
	    //mC1.set(2,0,.25);
	    //mC1.set(2,1,.25);
	    mC1.set(2,2,1);
	    mC1.set(2,3,.2);
	    //mC1.set(3,0,6);
	    //mC1.set(3,1,4);
	    //mC1.set(3,2,5);
	    mC1.set(3,3,1);
	    
	    // set the weights
	    //c1.getAlternativeAt(0).setWeight(mC1.getWeight(0));
	    //c1.getAlternativeAt(1).setWeight(mC1.getWeight(1));
	    //c1.getAlternativeAt(2).setWeight(mC1.getWeight(2));	   
	    //c1.getAlternativeAt(3).setWeight(mC1.getWeight(3));
	    
	    PairwiseComparisonMatrix mC2 = c2.getComparisonMatrix();
	    mC2.set(0,0,1);
	    mC2.set(0,1,2);
	    mC2.set(0,2,5);
	    mC2.set(0,3,1);
	    //mC2.set(1,0,.5);
	    mC2.set(1,1,1);
	    mC2.set(1,2,3);
	    mC2.set(1,3,2);
	    //mC2.set(2,0,.2);
	    //mC2.set(2,1,.3333);
	    mC2.set(2,2,1);
	    mC2.set(2,3,.25);
	    //mC2.set(3,0,1);
	    //mC2.set(3,1,.5);
	    //mC2.set(3,2,4);
	    mC2.set(3,3,1);
	    
	    // set the weights
	    //c2.getAlternativeAt(0).setWeight(mC2.getWeight(0));
	    //c2.getAlternativeAt(1).setWeight(mC2.getWeight(1));
	    //c2.getAlternativeAt(2).setWeight(mC2.getWeight(2));	   
	    //c2.getAlternativeAt(3).setWeight(mC2.getWeight(3));	    
	        
	    /*
	    // get the solution
	    int numCriteria = h.getNumCriteria();
	    Criteria crit = (Criteria)h.getCriteria().get(0);
	    int numAlternatives = crit.getNumAlternatives();
	    Matrix altWeights = new Matrix(numAlternatives,numCriteria);
	    altWeights.set(0,0,.1160); //c1.getAlternativeAt(0).getWeight());
	    altWeights.set(0,1,.3790); //c2.getAlternativeAt(0).getWeight());
	    altWeights.set(0,2,.3010); //c3.getAlternativeAt(0).getWeight());
	    altWeights.set(1,0,.2470); //c1.getAlternativeAt(1).getWeight());
	    altWeights.set(1,1,.2900); //c2.getAlternativeAt(1).getWeight());
	    altWeights.set(1,2,.2390); //c3.getAlternativeAt(1).getWeight());
	    altWeights.set(2,0,.0600); //c1.getAlternativeAt(2).getWeight());
	    altWeights.set(2,1,.0740); //c2.getAlternativeAt(2).getWeight());
	    altWeights.set(2,2,.2120); //c3.getAlternativeAt(2).getWeight());
	    altWeights.set(3,0,.5770); //c1.getAlternativeAt(3).getWeight());
	    altWeights.set(3,1,.2570); //c2.getAlternativeAt(3).getWeight());
	    altWeights.set(3,2,.2480); //c3.getAlternativeAt(3).getWeight());
	    
	    Matrix criteriaWeights = new Matrix(3,1); //= m.getWeight();
	    criteriaWeights.set(0,0,.3196);
	    criteriaWeights.set(1,0,.5584);
	    criteriaWeights.set(2,0,.1220);
	    */
	    // calculate the weights
	    h.calculateWeights(h.getGoal());

	    // Fuel Economy is determined by normalizing the 
	    // miles/gallon for each vehicle
	    // Civic	34		34/113 = .3010
	    // Saturn	27		27/113 = .2390
	    // Escort	24		24/113 = .2120
	    // Clio		28		28/113 = .2480
	    //	               ----      -----
	    //                  113      1.0000
	    // set the weights
	    c3.getAlternativeAt(0).setWeight(.3010);
	    c3.getAlternativeAt(1).setWeight(.2390);
	    c3.getAlternativeAt(2).setWeight(.2120);	   
	    c3.getAlternativeAt(3).setWeight(.2480);	    
	    
	    
	    
	    //System.out.println("altWeights:");
	    //altWeights.print(altWeights.getRowDimension(),4);
	    
	    //System.out.println("criteriaWeights:");
	    //criteriaWeights.print(criteriaWeights.getRowDimension(), 4);
	    
	    // multiply the matricies
	   // Matrix finalSolution = altWeights.times(criteriaWeights);
	    //for (int i=0; i < altWeights.getRowDimension(); i ++){
	    //	double sum = 0;
	   // 	for (int j=0; j < altWeights.getColumnDimension(); j++){
	   // 		System.out.println(altWeights.get(i,j) + " times " + criteriaWeights.get(j,0));
	   // 		sum = sum + (altWeights.get(i,j) * criteriaWeights.get(j,0));
	   // 	}
	   // 	finalSolution.set(i,0,sum);
	    //}
	    
	  //  System.out.println("Final Solution:\n");
	   // finalSolution.print(finalSolution.getRowDimension(), 4);
	    
	    
	    return h;

	  }

 
	  public static void main(String[] argv) {
	        
	    // build a tested hierarchy 
	    Hierarchy h = new Hierarchy();
	    AHPExample ex = new AHPExample();
	    h=ex.getHierarchyExample();
	    System.out.println("\t \t FINAL Hierarchy h: "+h.print());

	  }
		




}
