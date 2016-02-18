package autonomousThreads;

import java.io.*;
import java.util.ArrayList;

import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

public class FrequencyAutonomous extends Thread implements Autonomous {
	private Robot robot;
	
	private boolean inAuton;
	
	//for actionPlayback only
	ArrayList<String> commands;
	long timeFrequency;
	double currentRunTime = 0; //Time to run code in MS
	/**
	 * 
	 * @param r robot object
	 * @param autonName name of auton method to run
	 */
	public FrequencyAutonomous(Robot r) {
		robot = r;
		
		inAuton = false;
	}
	
	public void run() {
		try {
			for (String command:commands) {
				currentRunTime = System.currentTimeMillis();

				robot.teleopMaster(true);
				Gamepad.setSyntheticState(command);
				
				currentRunTime -= System.currentTimeMillis();
				
				long delayMS = ((1/timeFrequency) * (1000)) - (int)currentRunTime;
				int delayNS = (int) (((currentRunTime % 1) * 1000000) + 0.5); //Rounds in the case that the result is a decimal
				
				Thread.sleep(delayMS, delayNS);	//Note: if frequency is extremely low, robot will stop between actions
				
				//Breaks out of thread is autonomous ends
				if(!inAuton) {
					break;
				}
			}
			DriverStation.reportError("Finished", false);
		}
		catch(Exception e) {
			DriverStation.reportError(e + "", true);
		}
	}

	/**
	 * @param interval amount of time to round the recording's time so that the action occurs
	 **/
	public void actionPlayback(String recordingFileName, long frequency) {
		timeFrequency = frequency;
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
			this.start();
		}
		catch(Exception e) {
			DriverStation.reportError(e + "Autonomous.java, actionPlayback: " + this.getState(), false);
		}
	}
	public void setAutonState(boolean inAuton) {
		this.inAuton = inAuton;
	}
}
