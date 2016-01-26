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
			String nextCommand = "";
			String button = "";
			String magnitude = "";
			double time = 0;
			
			t.start(); //start timer
			Iterator<String> i = commands.iterator();
			while(i.hasNext()) {
				nextCommand = i.next();
				if(nextCommand.startsWith("time")) {
					time = Double.parseDouble(nextCommand.substring(5));
					if(Math.abs(time - t.get()) < interval) {
						nextCommand = i.next();
						tokenizer = new StringTokenizer(nextCommand, ";");
						
						String token;
						while(tokenizer.hasMoreTokens()) {
							token = tokenizer.nextToken();
							
							int colonIndex = token.indexOf(':');    ///Interpret button and magnitude
							button = token.substring(0, colonIndex);
							magnitude = token.substring(colonIndex + 1, token.indexOf(';'));
							//adjust game controller state
						    switch (button) {
						    	case "A_Button":
						    		robot.gp.A_Button_State = (magnitude.equals("1.0")) ? true : false;
						    		break;
						    	case "B_Button":
						    		robot.gp.B_Button_State = (magnitude.equals("1.0")) ? true : false;
						    		break;
						    	case "X_Button":
						    		robot.gp.X_Button_State = (magnitude.equals("1.0")) ? true : false;
						    		break;
						    	case "Y_Button":
						    		robot.gp.Y_Button_State = (magnitude.equals("1.0")) ? true : false;
						    		break;
						    	case "Left_Bumper":
						    		robot.gp.LEFT_Bumper_State = (magnitude.equals("1.0")) ? true : false;
						    		break;
						    	case "Right_Bumper":
						    		robot.gp.RIGHT_Bumper_State = (magnitude.equals("1.0")) ? true : false;
						    		break;
						    	case "Left_Y":
						    		robot.gp.LEFT_Stick_Y_State = Double.parseDouble(magnitude);
						    		break;
						    	case "Left_X":
						    		robot.gp.LEFT_Stick_X_State = Double.parseDouble(magnitude);
						    		break;
						    	case "Right_Y":
						    		robot.gp.RIGHT_Stick_Y_State = Double.parseDouble(magnitude);
						    		break;
						    	case "Right_X":
						    		robot.gp.RIGHT_Stick_X_State = Double.parseDouble(magnitude);
						    		break;
						    	case "Right_Trigger":
						    		robot.gp.LEFT_Stick_Y_State = Double.parseDouble(magnitude);
						    		break;
						    	case "Left_Trigger":
						    		robot.gp.LEFT_Stick_Y_State = Double.parseDouble(magnitude);
						    		break;
						    	case "DPAD":
						    		//Could not find DPAD variable in Gamepad, no references anywhere else (I think); TODO
						    		break;
						    }
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
