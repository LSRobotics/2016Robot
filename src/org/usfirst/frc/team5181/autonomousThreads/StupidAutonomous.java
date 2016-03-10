package org.usfirst.frc.team5181.autonomousThreads;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class StupidAutonomous extends Thread {

	boolean tStart = false;
	Timer t = new Timer();
	DriveTrain driveTrain;
	private Robot robot;
    /**
     * This function is called periodically during autonomous
     */
	
	public StupidAutonomous(Robot r) {
		robot = r;
	}
	
    public void autonomousPeriodic() {
		/**
		 * Back up a bit, turn right 90 degrees (so back is facing ball zone);
		 */
		for (int i = 0; i < 500; i++) {
			robot.drive.arcadeDrive(0.0, -0.3);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		robot.pidi.turnToAngle(90.0); //TODO
		for (int i = 0; i < 1000; i++) {
			robot.drive.arcadeDrive(0.0, -0.5);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
}
