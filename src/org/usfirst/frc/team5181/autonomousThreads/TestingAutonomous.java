package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.sensors.RangeSensors;
import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;
public class TestingAutonomous {
/**
 * @author Alex F.
 * @param For testing sensors and autonomous ideas...
 * @param Loaded usually in test() method in Robot.java
 */
	RangeSensors rangeSensors;
	DriveTrain driveTrain;
	private Robot robot;
	
	public TestingAutonomous(RangeSensors sensors) {
		/**
		 * Example usage for SonicRangeSensor.UseSonicRangeSensors
		 * srs.srFront.getRangeCm()
		 * srs.srBack.getRangeCm()
		 * srs.srRight.getRangeCm()
		 * srs.srLeft.getRangeCm()
		 */
		 rangeSensors = sensors;
	}
	
	public void testAutonomous() {
		while(rangeSensors.srFront.getRangeCm() > 60.0) {
			robot.drive.arcadeDrive(0.0, 0.3);
		}
	}
}
