package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Statics;
import org.usfirst.frc.team5181.sensors.RevX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ActionBased extends Thread {
	
	List<String> recording;
	long timeFrequency;
	boolean isRecording;
	int recordingNumber;
	boolean includeTimes = false;
	boolean addSetpoint = false;
	RevX revX;
	String fileName = ""; 
	
	long currentRunTime = 0; //Time to run code in NS
	
	public ActionBased(RevX revX) {
		recording = new ArrayList<String>();
		isRecording = false;
		recordingNumber = 0;
		
		this.revX = revX;
	}
	
	private void recordAction(int button, double magnitude) {
		recording.add(button + ":" + magnitude + ";");
	}
	private void recordPeriod(double magnitude) {
		recording.add("t:" + magnitude + ";");
	}
	private void sendActions() {
		try {
			DriverStation.reportError("\nFinished\n", false);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
			for(String command:recording) {
				bw.write(command);
			}
			bw.flush();
			bw.close();
		}
		catch(Exception e) {
			DriverStation.reportError(e.getMessage(), false);
		}
	} 
	
	public void incrementRecording() {
		recordingNumber++;
	}
	
	/**
	 * 
	 * @param includeTimeInvervals
	 * @param timeFrequency is includeTimeIntervals is true this parameter becomes a period not a frequency
	 */
	public void startRecording(boolean includeTimeInvervals, long timeFrequency) {
		isRecording = true;
		includeTimes = includeTimeInvervals;
		this.timeFrequency = timeFrequency;
		
		do {
			revX.zeroYaw();
		} while(Math.abs(revX.getRotation()) < 0.5);
		
		this.start();
	}
	
	public void addSetpoint() {
		addSetpoint = true;
	}
	public void resetSetpoint() {
		addSetpoint = false;
	}
	public void record() {
		//for buttons
		recordAction(Statics.A_Button, toDouble(Gamepad.A_Button_State));
		recordAction(Statics.B_Button, toDouble(Gamepad.B_Button_State));
		recordAction(Statics.X_Button, toDouble(Gamepad.X_Button_State));
		recordAction(Statics.Y_Button, toDouble(Gamepad.Y_Button_State));
		recordAction(Statics.RIGHT_Bumper, toDouble(Gamepad.RIGHT_Bumper_State));
		recordAction(Statics.LEFT_Bumper, toDouble(Gamepad.LEFT_Bumper_State));
		
		//for triggers/analog sticks
		recordAction(12, Gamepad.LEFT_Stick_Y_State);
		recordAction(11, Gamepad.LEFT_Stick_X_State);
		recordAction(16, Gamepad.RIGHT_Stick_Y_State);
		recordAction(15, Gamepad.RIGHT_Stick_X_State);
		recordAction(14, Gamepad.RIGHT_Trigger_State);
		recordAction(13, Gamepad.LEFT_Trigger_State);
		
		//Analog downs
		recordAction(17, toDouble(Gamepad.LEFT_Stick_DOWN_State));
		recordAction(18, toDouble(Gamepad.RIGHT_Stick_DOWN_State));
		
		recording.add("\n");
	}
	
	public void stopRecording() {
		isRecording = false;
	}
	
	
	private double toDouble(boolean bool) {
		if (bool) {
			return 1.0;
		}
		else return 0.0;
	}
	
	public void run() {
		try {
			while(isRecording) {
				currentRunTime = System.nanoTime();
				
				record();
				
				currentRunTime = System.nanoTime() - currentRunTime;
				
				long delayMS;
				int delayNS;
				if(!includeTimes) {
					delayMS = ((1/timeFrequency) * (1000)) - (int) (currentRunTime / 1000000);
					delayNS = (int) (currentRunTime % 1000000); //Rounds in the case that the result is a decimal
					Thread.sleep(delayMS, delayNS);
				}
				else {
					delayMS = timeFrequency - (int) (currentRunTime / 1000000);
					delayNS = (int) (currentRunTime % 1000000); //Rounds in the case that the result is a decimal
					Thread.sleep(delayMS, delayNS);
				}
				
				//Records total time between actions
				//Timing error, but error is minimal
				if(includeTimes) {
					recordPeriod(delayMS + (delayNS / 1000000)); //in MS //More accurate than simply adding timeFrequency (ie period)
					recording.add("\n");
				}
				if(addSetpoint) {
					recording.add("SETPOINT;R:" + revX.getRotation() + "\n");
				}
			}
			
			if(!isRecording) {
				sendActions();
			}
		}
		catch(Exception e) {
			DriverStation.reportError(e.getMessage() + "\n", false);
		}
	}
	public void setFile(String file) {
		fileName = file;
	}
}
