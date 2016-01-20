package org.usfirst.frc.team5181.robot;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.io.*;
import java.net.Socket;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
	private static Timer t;
	private String recordingFile; //for actionPlayback
	private Robot robot;
	
	/**
	 * 
	 * @param autonName Auton method to execute
	 * @param interval Default = 0; Window of time in which an action fires
	 * @par
	 */
	public Autonomous(Robot r, String autonName, double interval, String recordingFileName) {
		t = new Timer();
		robot = r;
		
		//choose auton
		if(autonName.equalsIgnoreCase("actionPlayback")) {
			actionPlayback(interval, recordingFileName);
		}
	}
	
	/**
	 * 
	 * @param interval amount of time to round the recording's time so that the action occurs
	 */
	private void actionPlayback(double interval, String recordignFileName) {
		try {
			//read commands from file on drive computer
			List<String> commands = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new InputStreamReader((new Socket("LSCHS-ROBOTICS", 5800).getInputStream())));
			
			String line = "";
			while((line = br.readLine()) != null) {
				commands.add(line);
			}
			
			//parse commands and execute
			StringTokenizer tokenizer;
			String button = "";
			String magnitude = "";
			double time = 0;
			
			boolean startedTimer = false;
			
			Iterator<String> i = commands.iterator();
			while(i.hasNext()) {
				tokenizer = new StringTokenizer(i.next(), ":");
				button = tokenizer.nextToken().trim();
				magnitude = tokenizer.nextToken().trim();
				time = Integer.parseInt(tokenizer.nextToken());
				
				if(!startedTimer) {
					t.start();
					startedTimer = true;
				}
				
				//check time
				if(Math.abs(t.get() - time) <= interval) {
					
					//starting the action
					robot.teleopPeriodic();
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
