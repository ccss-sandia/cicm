package adt.util;

public class Round {
	/**
	 * Rounding.java - 
	 *   Program that uses two overloaded methods to round doubles
	 *   or floats to a specified number of decimal places.
	 *
	 * @author Grant William Braught
	 * @author Dickinson College
	 * @version 2/14/2001
	 */

	    public static void main (String[] args) {
		double x = 1.23456789;
		float y = 9.87654f;
		double z;
		float w;

		z = round(x,2);
		System.out.println(z);
		z = round(x,5);
		System.out.println(z);

		System.out.println();

		w = round(y,3);
		System.out.println(w);
		w = round(y,0);
		System.out.println(w);
	    }

	    /**
	     * Round a double value to a specified number of decimal 
	     * places.
	     *
	     * @param val the value to be rounded.
	     * @param places the number of decimal places to round to.
	     * @return val rounded to places decimal places.
	     */
	    public static double round(double val, int places) {
		long factor = (long)Math.pow(10,places);

		// Shift the decimal the correct number of places
		// to the right.
		val = val * factor;

		// Round to the nearest integer.
		long tmp = Math.round(val);

		// Shift the decimal the correct number of places
		// back to the left.
		return (double)tmp / factor;
	    }

	    /**
	     * Round a float value to a specified number of decimal 
	     * places.
	     *
	     * @param val the value to be rounded.
	     * @param places the number of decimal places to round to.
	     * @return val rounded to places decimal places.
	     */
	    public static float round(float val, int places) {
		return (float)round((double)val, places);
	    }


}
