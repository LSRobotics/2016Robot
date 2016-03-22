package org.usfirst.frc.team5181.autonomousThreads;


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

	public TestingAutonomous(RangeSensors sensors, Robot robot) {
		/**
		 * Example usage for SonicRangeSensor.UseSonicRangeSensors
		 * srs.srFront.getRangeCm() srs.srBack.getRangeCm()
		 * srs.srRight.getRangeCm() srs.srLeft.getRangeCm()
		 */
		rangeSensors = sensors;
		this.robot = robot;

	}

	public void testAutonomous() {
		//robot.pidi.moveTo(900); //millimeters
//		if (robot.rangeSensors.srBack.getRangeMm() > 900) {
//			robot.drive.arcadeDrive(0.0, -0.5);
//			return false;
//		}
//		return true;
	}

	@Override
	public void actionPlayback(String recordingName, long time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAutonState(boolean inAuton) {
		// TODO Auto-generated method stub

	}

}
