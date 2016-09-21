package org.usfirst.frc.team5181.robot;

/**
 * Filters for number manipulation
 * @author connordevitt2016
 *
 */
public class Filters {
	
	/**
	 * Manipulates the input by the specified power while maintaining sign
	 * @param power power to raise the input to
	 * @param input what is going to be changed
	 * @return input ^ power
	 */
	public static double powerCurving(double input, double power) {
		return ((input < 0)? -1 : 1) * Math.pow(Math.abs(input), power);
	}
	
	// yark
	public static double determineControlY(double y) {
		if(y >= 0.8) {
			return -(1.5 - y);
		} else {
			return (1 - y);
		}
	}
}
