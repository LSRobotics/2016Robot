package org.usfirst.frc.team5181.robot;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.io.*;
import java.net.Socket;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous extends Thread {
	private String recordingFile; //for actionPlayback
	private Robot robot;
	
	//for actionPlayback only
	ArrayList<String> commands;
	long timeStep;
	
	/**
	 * 
	 * @param r robot object
	 * @param autonName name of auton method to run
	 */
	public Autonomous(Robot r, String autonName) {
		robot = r;
	}
	
	public void run() {
		try {			
			for (String command:commands) {
				Gamepad.setSyntheticState(command);
				robot.teleopMaster(true);
				Thread.sleep(timeStep);
			}
		}
		catch(Exception e) {
			DriverStation.reportError(e + "", true);
		}
	}

	/**
	 * 
	 * @param interval amount of time to round the recording's time so that the action occurs
	 */
	public void actionPlayback(String recordingFileName,  long step) {
		timeStep = step;
		commands = new ArrayList<String>();
		try {
			DriverStation.reportError("HERE", false);
			
			BufferedReader br = new BufferedReader(new FileReader(new File(recordingFileName)));
			String line = "";
			while((line = br.readLine()) != null) {
				if (line.equals("")) {
					continue;
				}
				commands.add(line);
				DriverStation.reportError("Line: " + line, false);
			}
			this.start();
		}
		catch(Exception e) {
			DriverStation.reportError(e + "", true);
		}
	}
}
