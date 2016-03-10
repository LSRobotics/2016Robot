package org.usfirst.frc.team5181.recoder;

import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Statics;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import java.io.*;
import java.net.Socket;

public class ActionBased extends Thread {
	
	String recording;
	long timeStep;
	boolean isRecording;
	int recordingNumber;
	
	public ActionBased(long step) {
		recording = "";
		isRecording = false;
		timeStep = step;
		recordingNumber = 0;
	}
	
	private void recordAction(int button, double magnitude) {
		recording += button + ":" + magnitude + ";";
	}
	
	private void sendActions(String autonFileName) {
		try {
			DriverStation.reportError(recording + "\n", false);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(autonFileName)));
			bw.write(recording);
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
	public void startRecording() {
		isRecording = true;
		if(this.getState().equals(Thread.State.TIMED_WAITING)) {
			this.resume();
		}
		else {
			this.start();
		}
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
	
		recording += "\n";
	}
	
	public void stopRecording(String autonName) {
		if (isRecording) {
			sendActions(autonName);
		}
		isRecording = false;
		this.suspend();
	}
	
	private double toDouble(boolean bool) {
		if (bool) {
			return 1.0;
		}
		else return 0.0;
	}
	
	public void run() {
		try {
			while(true) {
				if(isRecording) {
					record();
					Thread.sleep(timeStep);
				}
			}
		}
		catch(Exception e) {
			DriverStation.reportError(e.getStackTrace() + "", false);
		}
	}
}
