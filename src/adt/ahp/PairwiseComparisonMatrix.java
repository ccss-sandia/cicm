// Abstract Data Type
package adt.ahp ;


//imports
//import javax.swing.*;          //This is the final package name.
//import adt.util.*;
//import com.sun.java.swing.*; //Used by JDK 1.2 Beta 4 and all
//Swing releases before Swing 1.1 Beta 3.
//import javax.swing.border.*;

//import adt.util.Round;



//import java.awt.*;
//import java.awt.event.*;
//import java.util.*;
import java.io.*;

import Jama.*;


/**
 * PairwiseComparisonMatrix class
 * <br>Built off of the JAHP code by Maxime MORGE <A HREF="mailto:morge@emse.fr">morge@emse.fr</a>, Fev 13, 2003
 * @updatedBy  Lozanne Chavez 
 * @lastUpdate February 2008
 */
public class PairwiseComparisonMatrix implements Serializable,Cloneable{
  

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// ATTRIBUTES
  public static double EXTREMELY=9.0;
  public static double BETWEEN_EXTREMELY_AND_VERY_STRONGLY=8.0;
  public static double VERY_STRONGLY=7.0;
  public static double BETWEEN_VERY_VERY_STRONGLY_AND_STRONGLY=6.0;
  public static double STRONGLY=5.0;
  public static double BETWEEN_STRONGLY_AND_SLIGHTLY=4.0;
  public static double SLIGHTLY=3.0;
  public static double BETWEEN_SLIGHTLY_EQUALLY=2.0;
  public static double EQUALLY=1.0;

  private Matrix matrix ;
  private Matrix weights;
  private int size;
  private boolean ll;

  /**
   * Set the value of the pairwise comparison aij between w_i and w_j
   *
   * @param i index of the dominant activity w_i
   * @param j index of the dominated activity w_j
   * @param value of w_i/w_j
   * @exception IllegalArgumentException
   */
  public void set(int i,int j, double value) {
    if (i>=getSize() && j>=getSize())
      throw new IllegalArgumentException("index of a single element should be like 0<=i,j<size");
    if (i==j && value!=EQUALLY)
      throw new IllegalArgumentException("Elements in the diagonal of the Pairwise Comparison Matrix should be EQUAL.");
    matrix.set(i,j,value);
    matrix.set(j,i,1.0/value);
  }

  /**
   * Get the value of the pairwise comparison between aij=w_i 
   *
   * @param i index of the dominant activity W_i
   * @param j index of the dominated activity W_j
   * @return value of W_i/W_j
   * @exception IllegalArgumentException "Out of bounded..."
   */
  public double get(int i,int j) {
    if (i>=getSize() && j>=getSize())
      throw new IllegalArgumentException("index of a single element should be like 0<=i,j<size");
    return matrix.get(i,j);
  }

  /**
   * Gets the value of matrix
   *
   * @return the value of matrix
   */
  public Matrix getMatrix() {
    return this.matrix;
  }

  /**
   * Sets the value of matrix
   *
   * @param argMatrix Value to assign to this.matrix
   */
  public void setA(Matrix argMatrix){
    this.matrix = argMatrix;
  }

  /**
   * Gets the value of size
   *
   * @return the value of size
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Sets the value of size
   *
   * @param argSize Value to assign to this.size
   */
  public void setSize(int argSize){
    this.size = argSize;
  }


  /**
   * Creates a new  <code>PairwiseComparisonMatrix</code> instance.
   *
   */
  public PairwiseComparisonMatrix(int size, boolean isll ){
    this.size=size;
    this.ll = isll;
    InitMatrix();
  }

  /**
   * Init a new  <code>Matrix</code> instance.
   *
   */
  public void InitMatrix(){
      Matrix matrix2 =new Matrix(size,size, 1.0);
      if (matrix != null) {
    	  // copy the matrix
    	  for (int i=0; i < matrix.getRowDimension(); i++) {
    		  for (int j=0; j < matrix.getColumnDimension(); j++) {
    			  matrix2.set(i, j, matrix.get(i,j));
    		  }
    	  }
      }
      matrix = matrix2;
  }

  /**
   * Print the  <code>Matrix</code> 
   * 
   */
  public void print(){
    matrix.print(matrix.getRowDimension(),4);
  }


  /**
   * Get the value of the InconsistencyRatio   
   * @return double
   */
  public double getInconsistencyIndex()  {
    //n!=1
    if (getSize()<=1) return 0.0;
    return (getMaxEigenValue()- getSize())/ (getSize()-1.0); 
  }
  
  public void printWeights() {
	  
	  for (int i=0; i <this.weights.getRowDimension(); i++) {
		  System.out.println(i + " " + adt.util.Round.round(this.weights.get(i, 0),4));
	  }
  }


  /**
   * Get the value of the InconsistencyRatio   
   * @return double
   */
    public double getRandomInconsistency(){
    switch (size)
    {
      case 0 : return 0.00;
      case 1 : return 0.00;
      case 2 : return 0.00;
      case 3 : return 0.58;
      case 4 : return 0.90;
      case 5 : return 1.12;
      case 6 : return 1.24;
      case 7 : return 1.32;
      case 8 : return 1.41;
      case 9 : return 1.45;
      case 10: return 1.49;
      default : return 1.5; //take care
    }
    
  }

  /**
   * Get the value of the InconsistencyRatio   
   * @return double
   */
  public double getInconsistencyRatio(){
    if (getSize()<=2) return 0.0;
    return getInconsistencyIndex()/getRandomInconsistency();
  }


  /**
   * Check the consistency of the <code>PairwiseComparisonMatrix</code>  
   * @return boolean value
   */
  public boolean isConsistency(){
    if (getInconsistencyRatio() <= 0.1) return true;
    else return false;
  }
  

  /**
   * Get the value of the <code>max_eigen_value</code> of the matrix A.
   * @return the max_eigen_value of the matrix A
   */

  public double getMaxEigenValue(){
    EigenvalueDecomposition Eig = new EigenvalueDecomposition(matrix);
    double[] values = Eig.getRealEigenvalues() ;
    double max=0.0;

    for(int i=0;i<this.size;i++){
      if (values[i]>=max) max=values[i];
    }

    //System.out.println("The max eigenvalue : " + max);
    return max;

  }


  /**
   * Get the vector of weights of the <code>PairwiseComparisonMatrix</code>.
   * @return Matrix
   */
  public void calcWeights(){
	// copy the matrix
	Matrix squared = matrix.copy();  
	
	//System.out.println("matrix:\n");
	//squared.print(matrix.getRowDimension(), 4);
	
	Matrix eigenvector = new Matrix(squared.getRowDimension(),1);
	
	//System.out.println("eigenvector:\n");
	//eigenvector.print(eigenvector.getRowDimension(), 4);
	
	Matrix prevEigenvector = new Matrix(eigenvector.getRowDimension(), eigenvector.getColumnDimension(), 1.0);
	
	//System.out.println("prev eigenvector:\n");
	//eigenvector.print(prevEigenvector.getRowDimension(), 4);	
	double min=0, max=0;
	while (!equal(eigenvector, prevEigenvector)) {
		double totalSum = 0;
		// first, save a copy of the eigenvector for comparison
		prevEigenvector = eigenvector.copy();
		
		//System.out.println("prevEigenvector:");
		//prevEigenvector.print(prevEigenvector.getRowDimension(), 4);
		
		// step 1:  square the matrix
		squared = squared.times(squared); 
		
		//System.out.println("squared:");
		//squared.print(squared.getRowDimension(), 4);
		
		// step 2:  compute eigenvector
		// first sum the rows
		for (int i=0; i < squared.getRowDimension(); i++) {
			double sum = 0;
			for (int j=0; j < squared.getColumnDimension(); j++) {
				sum = sum + squared.get(i,j);
			}
			eigenvector.set(i, 0, sum);
			totalSum = totalSum + sum;
		}
		
		//System.out.println("sum rows:");
		//eigenvector.print(eigenvector.getRowDimension(), 4);
		//System.out.println("\nTotal sum: " + totalSum + "\n");
		
		// normalize
		min = 1000000000;
		max = 0;
		for (int i=0; i < eigenvector.getRowDimension(); i++) {
			double norm = eigenvector.get(i,0)/totalSum;
			if (norm < min) {
				min = norm;
			}
			if (norm > max) {
				max = norm;
			}
			eigenvector.set(i,0,norm);
		}
		//System.out.println("normalized:");
		//eigenvector.print(eigenvector.getRowDimension(), 4);	

		//System.out.println("prev eigenvector:\n");
		//eigenvector.print(prevEigenvector.getRowDimension(), 4);
		//System.out.println("eigenvector normalized:\n");
		//eigenvector.print(eigenvector.getRowDimension(), 4);
		//System.out.println("total sum is " + totalSum);
		
	}
	
	// for alternatives only scale from 0-1
	//System.out.println("min is " + min + " and max is " + max);
	if (ll) {
		for (int i=0; i < eigenvector.getRowDimension(); i++) {
			double val = eigenvector.get(i,0);
			//double scale = (val - min)/(max-min);
		//System.out.println("scale is: " + scale);
			eigenvector.set(i, 0, (val - min) / (max - min));
		}
	}

	this.weights = eigenvector;
	
	//return eigenvector;

	
    //Matrix Ab = new Matrix(getSize(), getSize());
    
    //Matrix W = new Matrix (getSize(), 1, 1.0);
    /*
    double sum=0.00;
    // normalization
    for(int j=0;j < getSize();j++){
      sum=0.00;
      for (int i=0;i<getSize();i++){
    	  sum+=matrix.get(i,j);
      }
      for (int i=0;i<getSize();i++){
    	  try{Ab.set(i,j,matrix.get(i,j)/sum);}
    	  catch (ArrayIndexOutOfBoundsException e) { System.err.println("Error in setting Ab : ArrayIndexOutOfBoundsException"+e);}
      }
    }
    //System.out.println("Matrix is:\n");
    //Ab.print(getSize(), getSize());
    
    //sum on each line
    for(int i=0;i < getSize();i++){
      sum=0.00;
      for (int j=0;j<getSize();j++){
    	  sum+=matrix.get(i,j);
      }
      try{W.set(i,0,sum);}
      catch (ArrayIndexOutOfBoundsException e) { System.err.println("Error in setting W : ArrayIndexOutOfBoundsException"+e);}
     
    }
    //normalization vector
    sum=0.00;
    for (int i=0;i<getSize();i++){
      try{sum+=W.get(i,0);}
      catch (ArrayIndexOutOfBoundsException e) { System.err.println("Error in setting W : ArrayIndexOutOfBoundsException"+e);}
    }
    for (int i=0;i<getSize();i++){
      
      try{W.set(i,0,W.get(i,0)/sum);} 
      catch (ArrayIndexOutOfBoundsException e) { System.err.println("Error in setting W : ArrayIndexOutOfBoundsException"+e);}
    }
    //System.out.println("normalization vector:\n");
    //W.print(getSize(), 4);
	*/

    //return W;
  }

  private boolean equal(Matrix a, Matrix b) {
	  if (a.getRowDimension() != b.getRowDimension()) return false;
	  if (a.getColumnDimension() != b.getColumnDimension()) return false;
	  for (int i=0; i < a.getRowDimension(); i++){
		  for (int j=0; j < a.getColumnDimension(); j++) {
			  if (adt.util.Round.round(a.get(i, j), 4) != adt.util.Round.round(b.get(i,j), 4)) return false;
		  }
	  }
	  return true;
  }
  
  /**
   * Get the i element of the vector of weights of the <code>PairwiseComparisonMatrix</code>.
   * @param int index
   * @return double value 
   */
  public double getWeight(int i){
    //Matrix W=new Matrix(getSize(),1);
    //W=getWeight();
    //return W.get(i,0);
	double w = 0;
	try {
		w = weights.get(i,0);
	} catch (NullPointerException e) {}
	return w;
  }
  
  public void setWeight(int i, double val) {
	  if (weights != null){
		  weights.set(i, 0, val);
	  }
  }


  /**
   * <code>toString</code> Returns a string representation of this PairwiseComparisonMatrix, containing the String representation of each weight.
   *
   * @return a <code>String</code> value
   */
  public String toString(){
    String s=new String();
    s="Matrix : \n";
    //matrix.print(matrix.getRowDimension(), 4);
    for (int i=0;i<getSize();i++){
      for (int j=0;j<getSize();j++){
    	  s+=" "+adt.util.Round.round(get(i,j),4)+" ";
      }
      s+="\n";
    }
    s+="Weights : \n";
    //weight.print(weight.getRowDimension(), 4);
    for(int i=0;i<getSize();i++) 
    	s+=" "+adt.util.Round.round(getWeight(i),4)+" ";
    s=s+"\n";
    //s=s+"Inconsistency Ratio      : " +getInconsistencyRatio()+"\n";
    return s;
  }

  /**
   * <code>addAlternative</code> method here.
   *
   * 
   */
  public void addElement(){
    setSize(getSize()+1);
    InitMatrix();
    
  }

  /**
   * <code>delElement</code> method here.
   * @param int :  the index of the alternative which should be deleted
   */
  public void delElement(int index){
    Matrix B=new Matrix((size-1),(size-1),1.0);
    
    for(int i=0;i<size;i++){
      for(int j=0;j<size;j++){
	if (i<index && j<index){
	  B.set(i,j,matrix.get(i,j));
	}
	if (i<index && index<j){
	  B.set(i,j-1,matrix.get(i,j));
	}
	if (index <i && j<index){
	  B.set(i-1,j,matrix.get(i,j));
	}
	if (index<i && index<j){
	    //Systemerr.println("size : "+size+"\n");
	  //Systemerr.println("i : "+i+"   j : "+j+"\n");
	  
	  B.set(i-1,j-1,matrix.get(i,j));
	}
      }
    }      

     B=matrix;;
     size--;
  }

  /**
   * <code>main</code> method here to test.
   *
   * @param args[] a <code>String</code> value
   */
  public static void main(String args[]){
   
	  
    PairwiseComparisonMatrix P=new PairwiseComparisonMatrix(7, true);
    // testing out a constructed scale
    P.set(0,0,1);
    P.set(0,1,3);
    P.set(0,2,4);
    P.set(0,3,6);
    P.set(0,4,7);
    P.set(0,5,8);
    P.set(0,6,9);
    P.set(1,1,1);
    P.set(1,2,2);
    P.set(1,3,4);
    P.set(1,4,6);
    P.set(1,5,7);
    P.set(1,6,8);
    P.set(2,2,1);
    P.set(2,3,3);
    P.set(2,4,5);
    P.set(2,5,6);
    P.set(2,6,7);
    P.set(3,3,1);
    P.set(3,4,3);
    P.set(3,5,4);
    P.set(3,6,6);
    P.set(4,4,1);
    P.set(4,5,2);
    P.set(4,6,4);
    P.set(5,5,1);
    P.set(5,6,3);
    P.set(6,6,1);
    
	/*  
    PairwiseComparisonMatrix P=new PairwiseComparisonMatrix(4, true);
    P.set(0,0,1);
    P.set(0,1,7);
    P.set(0,2,8);
    P.set(0,3,9);
	    
    P.set(1,1,1);
    P.set(1,2,7);
    P.set(1,3,8);
	    
    P.set(2,2,1);
    P.set(2,3,7);
	    
    P.set(3,3,1);
	  */
    System.out.println("Print the matrix: ");
   
    /*
	  PairwiseComparisonMatrix P=new PairwiseComparisonMatrix(3);
	    // starting with our Criteria
	    // Economic, Image, Health & Safety, and Environment
	    P.set(0,0,1.0000);
	    P.set(0,1,.5000);
	    P.set(0,2,3.0000);
	    P.set(1,0,2.0000);
	    P.set(1,1,1.0000);
	    P.set(1,2,4.0000);
	    P.set(2,0,.3333);
	    P.set(2,1,.2500);
	    P.set(2,2,1.0000);
	    System.out.println("Print the matrix:  Economics, Image, Health & Safety, Environment:");
	  */  
	  
	  
	P.print();
	P.calcWeights();
	P.printWeights();

    
  }


}


  

