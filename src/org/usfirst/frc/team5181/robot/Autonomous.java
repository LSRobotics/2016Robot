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
			//Send the start command
			new BufferedWriter(new OutputStreamWriter((new Socket("LSCHS-ROBOTICS", 5800).getOutputStream()))).write("Run Auton");
			
			//read commands from file on drive computer
			List<String> commands = new ArrayList<String>(); //Will either be the state of the controller or the time
			BufferedReader br = new BufferedReader(new InputStreamReader((new Socket("LSCHS-ROBOTICS", 5800).getInputStream())));
			
			String line = "";
			while((line = br.readLine()) != null) {
				commands.add(line);
			}
			
			//parse commands and execute
			StringTokenizer tokenizer;
			String next = "";
			String button = "";
			String magnitude = "";
			double time = 0;
			
			t.start(); //start timer
			Iterator<String> i = commands.iterator();
			while(i.hasNext()) {
				next = i.next();
				if(next.substring(0, 4).equals("time")) {
					time = Double.parseDouble(next.substring(5));
					if(Math.abs(time - t.get()) < interval) {
						next = i.next();
						tokenizer = new StringTokenizer(next, ";");
						
						String token = "";
						while(tokenizer.hasMoreTokens()) {
							token = tokenizer.nextToken();
							
							//Interpret button and magnitude
							//adjust game controller state
							//Each token should be [button]:[magnitude]
						}
					}
				}
				else {
					DriverStation.reportError("No time attached", true);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
