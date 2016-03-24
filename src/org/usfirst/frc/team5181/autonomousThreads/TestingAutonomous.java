package org.usfirst.frc.team5181.autonomousThreads;


import org.usfirst.frc.team5181.autonomousThreads.PIDFunctions.Controllers;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.RangeSensors;

import edu.wpi.first.wpilibj.DriverStation;

public class TestingAutonomous implements Autonomous {
	/**
	 * @author Alex F.
	 * @param For
	 *            testing sensors and autonomous ideas...
	 * @param Loaded
	 *            usually in test() method in Robot.java
	 */
	RangeSensors rangeSensors;
	Robot robot;
	PIDFunctions pidiD;

	public TestingAutonomous(Robot robot) {
		/**
		 * Example usage for SonicRangeSensor.UseSonicRangeSensors
		 * srs.srFront.getRangeCm() srs.srBack.getRangeCm()
		 * srs.srRight.getRangeCm() srs.srLeft.getRangeCm()
		 */
		rangeSensors = robot.rangeSensors;
		this.robot = robot;

		pidiD = new PIDFunctions(robot, Controllers.DISPLACEMENT, robot.rangeSensors.srFront);
	}

	@Override
	public void initializeAuton(String recordingName, String[] others) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutonState(boolean inAuton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doAuton() {
		
		
	}
}
