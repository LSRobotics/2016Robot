package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.sensors.SonicRangeSensor;
import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;
public class TestingAutonomous {
/**
 * @author Alex F.
 * @param For testing sensors and autonomous ideas...
 * @param Loaded usually in test() method in Robot.java
 */
	SonicRangeSensor sr;
	DriveTrain driveTrain;
	private Robot robot;
	
	public TestingAutonomous() throws Exception {
		sr = new SonicRangeSensor();
	}
	
	public void testAutonomous() throws Exception {
		while(sr.getRangeCm() > 60.0) {
			robot.drive.arcadeDrive(0.0, -0.3);
		}
	}
}
