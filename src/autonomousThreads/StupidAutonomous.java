package autonomousThreads;


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
	
    public void autonomousPeriodic(int recordingName) {
    	if (tStart == false) {
    		t.start();
    		tStart = true;
    	}
    	if (t.get() < 2.0) 
    		{
    		//driveTrain.tankDrive(.1, .1);
    		}
    	else {
    		t.stop();
    		t.reset();
    		//driveTrain.tankDrive(0, 0);
    		tStart = false;
    	}
		switch(recordingName) {
			case "afterDefense":
			/**
			 * do turn left 90 degrees and go forward; Detect collision
			 */
				break;
			case "afterBangLeft":
			/**
			 * Back up a bit, then turn right 90 degrees, detect collision, then back up a bit
			 */
				break;
			case "finalAuton"
			/**
			 * Call to the recorded files
			 */
				break;
			default:
			
				break;
		}
    }
}
