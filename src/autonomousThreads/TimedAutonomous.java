package autonomousThreads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;

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
					double[] displacement = {Double.parseDouble(command.substring(command.indexOf("X:") + 2, command.indexOf("Y:"))), 
											 Double.parseDouble(command.substring(command.indexOf("Y:") + 2, command.indexOf("R:")))}; 
					double rotation = Double.parseDouble(command.substring(command.indexOf("R:") + 2));
					
					//Correct E-r
					while((Math.abs(revX.getRotation() - rotation)) >= 2.1) {
						pidi.turnToAngle(rotation);
					}
					
					pidi.turnToAngle(Math.atan(displacement[0] / displacement[1]));
					
					while(Math.sqrt(Math.pow(revX.getDisplacement()[0], 2) + Math.pow(revX.getDisplacement()[1], 2)) - 
						  Math.sqrt(Math.pow(displacement[0], 2) + Math.pow(displacement[1], 2)) >= 0.5) {
						pidi.move(Math.sqrt(Math.pow(displacement[0], 2) + Math.pow(displacement[1], 2)));
					}
					pidi.turnToAngle(-Math.atan(displacement[0] / displacement[1]));
					
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
