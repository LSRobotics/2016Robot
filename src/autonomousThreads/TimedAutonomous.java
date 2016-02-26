package autonomousThreads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;

import autonomousThreads.PIDFunctions.Controllers;
import sensors.RevX;
import edu.wpi.first.wpilibj.DriverStation;

public class TimedAutonomous extends Thread implements Autonomous {
	private Robot robot;
	PIDFunctions pidi;
	
	private boolean inAuton;
	
	//for actionPlayback only
	ArrayList<String> commands;
	ArrayList<Double> timePeriods;
	double commandStartTime = 0;
	RevX revX;
	
	public TimedAutonomous(Robot r, DriveTrain drive) {
		robot = r;
		
		pidi = new PIDFunctions(r, drive);
		inAuton = false;
	}
	
	public void run() {
		try {
			int iterative = 0;
			for (String command:commands) {
				if(command.contains("SETPOINT")) {
					double rotation = Double.parseDouble(command.substring(command.indexOf("R:") + 2));
					
					//Correct E-r
					while(pidi.onTarget(Controllers.ROTATION)) {
						pidi.turnToAngle(rotation);
					}
					continue;
				}
				
				commandStartTime = System.nanoTime() / 1000000;
				while((System.nanoTime() / 1000000) - commandStartTime < timePeriods.get(iterative)) {
					robot.teleopMaster(true);
					Gamepad.setSyntheticState(command);
					
					//Breaks out of thread is autonomous ends
					if(!inAuton) {
						break;
					}
				}
				//Breaks out of thread is autonomous ends
				if(!inAuton) {
					break;
				}
				iterative++;
			}
			DriverStation.reportError("Finished", false);
		}
		catch(Exception e) {
			DriverStation.reportError(e + "", true);
		}
	}
	
	/**
	 * 
	 */
	public void actionPlayback(String recordingFileName, long period) {
		commands = new ArrayList<String>();
		timePeriods = new ArrayList<Double>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(recordingFileName)));
			String line = "";
			while((line = br.readLine()) != null) {
				if (line.equals("")) {
					continue;
				}
				if(line.contains("t:")) {
					timePeriods.add(Double.parseDouble(line.substring(line.indexOf("t:") + 2, line.lastIndexOf(";"))));
					continue;
				}
				commands.add(line);
			}
			br.close();
			
			do {
				revX.zeroYaw();
			} while(Math.abs(revX.getRotation()) < 0.5);
			
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
