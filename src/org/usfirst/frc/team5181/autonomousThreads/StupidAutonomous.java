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

public class StupidAutonomous implements Autonomous {

	
	DriveTrain dt;
	
    /**
     * @param This function is called periodically during autonomous
     */
	
	public StupidAutonomous(Robot r) {
		dt = r.drive;
	}
	
	/**
	 * @param VERY DANGEROUS METHOD
	 */		
	
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
		dt.arcadeDrive(0.0, 0.9);
	}
}
