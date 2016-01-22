package org.firs.frc.team5181.Recoder;

import org.usfirst.frc.team5181.robot.Gamepad;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import java.io.*;
import java.net.Socket;

public class ActionBased {
	String recording;
	Gamepad gamepad;
	DriverStation ds;
	boolean isRecording;
	
	public ActionBased(DriverStation ds) {
		recording = "";
		gamepad = new Gamepad();
		this.ds = ds;
		
		isRecording = false;
	}
	
	private void recordAction(String button, double magnitude) {
		recording += button + ":" + magnitude + ";";
	}
	
	public void recordAction(double time) {
		recording += "time:" + time + "\n";
	}
	
	private void sendActions() {
		DriverStation.reportError(recording, false);
		
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter((new Socket("LSCHS-ROBOTICS", 5800).getOutputStream())));
			bw.write(recording);
		
		}
		catch(Exception e) {
			DriverStation.reportError(e.getMessage(), false);
		}
	}
	
	public void startRecording() {
		isRecording = true;
	}
	
	public void recording(double time) {
		
		//for buttons
		if (isRecording) {
			recordAction(time);
			
			recordAction("A_Button", toDouble(gamepad.getRawButton(gamepad.A_Button)));
			recordAction("B_Button", toDouble(gamepad.getRawButton(gamepad.B_Button)));
			recordAction("X_Button", toDouble(gamepad.getRawButton(gamepad.X_Button)));
			recordAction("Y_Button", toDouble(gamepad.getRawButton(gamepad.Y_Button)));
			recordAction("Right_Bumper", toDouble(gamepad.getRawButton(gamepad.RIGHT_Bumper)));
			recordAction("Left_Bumper", toDouble(gamepad.getRawButton(gamepad.LEFT_Bumper)));
			
			//for triggers/analog sticks
			recordAction("Left_Y", gamepad.getRawAxis(gamepad.LEFT_Stick_Y));
			recordAction("Left_X", gamepad.getRawAxis(gamepad.LEFT_Stick_X));
			recordAction("Right_Y", gamepad.getRawAxis(gamepad.RIGHT_Stick_Y));
			recordAction("Right_X", gamepad.getRawAxis(gamepad.RIGHT_Stick_X));
			recordAction("Right_Trigger", gamepad.getRawAxis(gamepad.RIGHT_Trigger));
			recordAction("Left_Trigger", gamepad.getRawAxis(gamepad.LEFT_Trigger));
					
			//DPad
			recordAction("DPAD", gamepad.getRawAxis(gamepad.getPOV()));
		}
	}
	
	public void stopRecording() {
		if (isRecording) {
			sendActions();
		}
		isRecording = false;
	}
	
	private double toDouble(boolean bool) {
		if (bool) {
			return 1.0;
		}
		else return 0.0;
	}
}
