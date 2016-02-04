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
	private long avgDelay = 0;
	private int countSample = 0;
	private final int delay = 0;
	
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
	}
	
	public void run() {
		try {
			long time = System.currentTimeMillis();
			for (String command:commands) {
				int count = 0;
				while(count <= timeStep) {
					Gamepad.setSyntheticState(command);
					robot.teleopMaster(true);
					Thread.sleep(delay - delay);
					count += delay;
				}
			}
			//this.suspend();
			time = System.currentTimeMillis()-time;
			this.avgDelay = (this.avgDelay * countSample + time)/(avgDelay+1);
			System.out.println(this.avgDelay);
			countSample++;
			this.wait();
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
			BufferedReader br = new BufferedReader(new FileReader(new File(recordingFileName)));
			String line = "";
			while((line = br.readLine()) != null) {
				if (line.equals("")) {
					continue;
				}
				commands.add(line);
			}
			if (this.getState().equals(Thread.State.TIMED_WAITING)) {
				//this.resume();
				this.notify();
			}
			else {
				this.start();
			}
			br.close();
		}
		catch(Exception e) {
			DriverStation.reportError(e + "Autonomous.java, actionPlayback: " + this.getState(), false);
		}
	}
}
