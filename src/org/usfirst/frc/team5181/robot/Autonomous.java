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
	double timeStep;
	double prevTime;
	
	/**
	 * 
	 * @param autonName Auton method to execute
	 * @param interval Default = 0; Window of time in which an action fires
	 * @par
	 */
	public Autonomous(Robot r, String autonName, double step) {
		t = new Timer();
		robot = r;
		timeStep = step;
		prevTime = 0 - timeStep;
	}
	
	/**
	 * 
	 * @param interval amount of time to round the recording's time so that the action occurs
	 */
	public void actionPlayback(String recordingFileName) {
		try {
			//Send the start command
			//new BufferedWriter(new OutputStreamWriter((new Socket("LSCHS-ROBOTICS", 5800).getOutputStream()))).write("Run Auton");
			
			//read commands from file on drive computer
			List<String> commands = new ArrayList<String>(); //Will either be the state of the controller or the time
			//BufferedReader br = new BufferedReader(new InputStreamReader((new Socket("LSCHS-ROBOTICS", 5800).getInputStream())));
			BufferedReader br = new BufferedReader(new FileReader(new File(recordingFileName)));
			
			String line = "";
			while((line = br.readLine()) != null) {
				commands.add(line);
			}
			
			//parse commands and execute
			StringTokenizer tokenizer;
			String nextCommand = "";
			int button = -1;
			int state = 0;
			String magnitude = "";
			boolean printTrace= false; //so I don't need to change the default driverstation.reporterror param
			Iterator<String> i = commands.iterator();
			t.start();
			while(i.hasNext()) {
				nextCommand = i.next();
				if(nextCommand.equals("")) {
					continue;
				}
				else if(nextCommand.startsWith("state")) {
					int readState = Integer.parseInt(nextCommand.substring(6));
					while(t.get() < prevTime + timeStep){} //wait
					prevTime = t.get();;
					if(state == readState) {
						nextCommand = i.next();
						tokenizer = new StringTokenizer(nextCommand, ";");
						
						String token;
						while(tokenizer.hasMoreTokens()) {
							token = tokenizer.nextToken();
							int colonIndex = token.indexOf(':');    ///Interpret button and magnitude
							
							button = Integer.parseInt(token.substring(0, colonIndex));
							magnitude = token.substring(colonIndex + 1);
							//adjust game controller state
							switch (button) {
						    	case 1:
						    		robot.gp.A_Button_State = magnitude.equals("1.0") ;
						    		break;
						    	case 2:
						    		robot.gp.B_Button_State = magnitude.equals("1.0") ;
						    		break;
						    	case 3:
						    		robot.gp.X_Button_State = magnitude.equals("1.0") ;
						    		break;
						    	case 4:
						    		robot.gp.Y_Button_State = magnitude.equals("1.0") ;
						    		break;
						    	case 5:
						    		robot.gp.LEFT_Bumper_State = magnitude.equals("1.0") ;
						    		break;
						    	case 6:
						    		robot.gp.RIGHT_Bumper_State = magnitude.equals("1.0") ;
						    		break;
						    	case 12:
						    		robot.gp.LEFT_Stick_Y_State = Double.parseDouble(magnitude);
						    		break;
						    	case 11:
						    		robot.gp.LEFT_Stick_X_State = Double.parseDouble(magnitude);
						    		break;
						    	case 16:
						    		robot.gp.RIGHT_Stick_Y_State = Double.parseDouble(magnitude);
						    		break;
						    	case 15:
						    		robot.gp.RIGHT_Stick_X_State = Double.parseDouble(magnitude);
						    		break;
						    	case 14:
						    		robot.gp.RIGHT_Trigger_State = Double.parseDouble(magnitude);
						    		break;
						    	case 13:
						    		robot.gp.LEFT_Trigger_State = Double.parseDouble(magnitude);
						    		break;
						    	//case "DPAD":
						    		//Could not find DPAD variable in Gamepad, no references anywhere else (I think); TODO
						    		//break;
						    	default:
						    			DriverStation.reportError("Button not found: " + button, false);
						    			break;
						    }
						 
						}
						robot.teleopMaster(true);
						state++;
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
