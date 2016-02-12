package org.usfirst.frc.team5181.robot;

import java.io.*;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;

public class Autonomous extends Thread {
	private Robot robot;
	
	private boolean inAuton;
	
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
}
