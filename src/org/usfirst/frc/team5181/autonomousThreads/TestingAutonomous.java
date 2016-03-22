package org.usfirst.frc.team5181.autonomousThreads;


import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.RangeSensors;

import edu.wpi.first.wpilibj.DriverStation;

public class TestingAutonomous {
	/**
	 * @author Alex F.
	 * @param For
	 *            testing sensors and autonomous ideas...
	 * @param Loaded
	 *            usually in test() method in Robot.java
	 */
	RangeSensors rangeSensors;
	Robot robot;

	public TestingAutonomous(RangeSensors sensors) {
		/**
		 * Example usage for SonicRangeSensor.UseSonicRangeSensors
		 * srs.srFront.getRangeCm() srs.srBack.getRangeCm()
		 * srs.srRight.getRangeCm() srs.srLeft.getRangeCm()
		 */
		rangeSensors = sensors;

	}

	public void testAutonomous() {
		while (rangeSensors.srBack.getRangeCm() > 30.0) {
			DriverStation.reportError(
					"[TestingAutonomous] Outside of stop range"
							+ rangeSensors.srBack.getRangeCm() + "cm \n",
					false);
			try {
				robot.drive.arcadeDrive(0.0, 0.5);
				DriverStation.reportError("[TestingAutonomous]Here", false);
			} catch (NullPointerException ne) {
				DriverStation.reportError(
						"[TestingAutonomous] Catastrophe right in arcadeDrive"
								+ ne, true);
			}
		}
		DriverStation.reportError("[TestingAutonomous] Now stopping"
				+ rangeSensors.srBack.getRangeCm() + "cm \n", false);
	}

}
