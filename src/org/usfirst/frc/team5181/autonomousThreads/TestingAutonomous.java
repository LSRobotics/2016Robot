package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.sensors.SonicRangeSensor;
import org.usfirst.frc.team5181.sensors.SonicRangeSensor.UseRangeSensors;
import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;
public class TestingAutonomous {
/**
 * @author Alex F.
 * @param For testing sensors and autonomous ideas...
 * @param Loaded usually in test() method in Robot.java
 */
	UseRangeSensors rangeSensors;
	DriveTrain driveTrain;
	private Robot robot;
	
	public TestingAutonomous(UseRangeSensors sensors) throws Exception {
		/**
		 * Example usage for SonicRangeSensor.UseSonicRangeSensors
		 * srs.srFront.getRangeCm()
		 * srs.srBack.getRangeCm()
		 * srs.srRight.getRangeCm()
		 * srs.srLeft.getRangeCm()
		 */
		 rangeSensors = sensors;
	}
	
	public void testAutonomous() throws Exception {
		while(rangeSensors.srBack.getRangeCm() > 60.0) {
			robot.drive.arcadeDrive(0.0, -0.3);
		}
	}
}
