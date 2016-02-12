package org.usfirst.frc.team5181.robot;

import sensors.RevX;

public class AutoTurning {
	RevX revX;
	
	/**
	 * For use with a revX sensor
	 * @param revX The purple thing (you know what we mean from that website...)
	 */
	public AutoTurning(RevX revX) {
		this.revX = revX;
	}
	
	public double[] revXTurn(double target) {
		double error = revX.getRotation();
		
		double leftSpeed = speedF(error);
		double rightSpeed = -speedF(error);
		
		return new double[] {leftSpeed, rightSpeed};
	}
	
	private double speedF(double x) {
		return Math.pow(x, 2);
	}
}
