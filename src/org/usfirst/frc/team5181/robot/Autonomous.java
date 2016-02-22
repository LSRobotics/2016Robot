package org.usfirst.frc.team5181.robot;

import java.io.*;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class Autonomous extends Thread implements PIDOutput {
	private Robot robot;
	
	private boolean inAuton;
	private double angleRate;
	
	//for actionPlayback only
	ArrayList<String> commands;
	long timeStep;
	
	/**
	 * 
	 * @param r robot object
	 * @param autonName name of auton method to run
	 */
	public Autonomous(Robot r) {
		robot = r;
		angleRate = 0;
		inAuton = false;
	}
	
	public void run() {
		try {
			for (String command:commands) {
				double count = 0;
				while(count < timeStep) {	
					robot.teleopMaster(true);
					Gamepad.setSyntheticState(command);
					Thread.sleep(0, 722000);
					count++;					
					if(!inAuton) {
						break;
					}
				}
				if(!inAuton) {
					break;
				}
			}
			DriverStation.reportError("Finished", false);
			this.stop();
		}
		catch(Exception e) {
			DriverStation.reportError(e + "", true);
		}
	}

	/**
	 * @param interval amount of time to round the recording's time so that the action occurs
	 **/
	public void actionPlayback(String recordingFileName,  long step) {
		timeStep = step;
		commands = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(recordingFileName)));
			String line = "";
			while((line = br.readLine()) != null) {
				if (line.equals("")) {
					continue;
				}
				commands.add(line);
			}
			
			br.close();
			
			if (this.getState().equals(Thread.State.TIMED_WAITING)) {
				//this.notify();
				this.start();
			}
			else {
				DriverStation.reportError("Started", false);
				this.start();
			}
		}
		catch(Exception e) {
			DriverStation.reportError(e + "Autonomous.java, actionPlayback: " + this.getState(), false);
		}
	}
	public void setAutonState(boolean inAuton) {
		this.inAuton = inAuton;
	}
	
	/**
	 * Moves the robot across the x/y plane
	 * @param distance The distances to travel (in meters) 
	 */
	public void revXMoveDistance(double x, double y) { 	
		robot.revX.resetDisplacement();
		double angle = Math.atan2(y, x);
		
	}
	
	public void turnToAnglePID(double angle) {
		double kP = 0.03;
		double kI = 0.00;
		double kD = 0.00;
		double kF = 0.00;
		double degreeTolerance = 2.0;
		
		PIDController pid = new PIDController(kP, kI, kD, kF, robot.revX.revX, robot);
		pid.setInputRange(-180.0, 180.0);
		pid.setOutputRange(-1, 1);
		pid.setAbsoluteTolerance(2);
		pid.setContinuous(true);
		pid.enable();
		
	}
	
	public void pidWrite(double output) {
		angleRate = output;
	}
}
