package org.firs.frc.team5181.Recoder;

import org.usfirst.frc.team5181.robot.Gamepad;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

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
	private void recordAction(String button, double magnitude, double time) {
		recording += button + ":" + magnitude + ":" + time + "\n";
	}
	private void sendActions() {
		ds.reportError(recording, false);
	}
	public void startRecording() {
		isRecording = true;
	}
	public void recording(double time) {
		//for buttons
		if (isRecording) {
//			recordAction("A_Button", toDouble(gamepad.getRawButton(gamepad.A_Button)), time);
//			recordAction("B_Button", toDouble(gamepad.getRawButton(gamepad.B_Button)), time);
//			recordAction("X_Button", toDouble(gamepad.getRawButton(gamepad.X_Button)), time);
//			recordAction("Y_Button", toDouble(gamepad.getRawButton(gamepad.Y_Button)), time);
//			recordAction("Right_Bumper", toDouble(gamepad.getRawButton(gamepad.RIGHT_Bumper)), time);
//			recordAction("Left_Bumper", toDouble(gamepad.getRawButton(gamepad.LEFT_Bumper)), time);
//			
			//for triggers/analog sticks
			recordAction("Left_Y", gamepad.getRawAxis(gamepad.LEFT_Stick_Y), time);
//			recordAction("Left_X", gamepad.getRawAxis(gamepad.LEFT_Stick_X), time);
			recordAction("Right_Y", gamepad.getRawAxis(gamepad.RIGHT_Stick_Y), time);
//			recordAction("Right_X", gamepad.getRawAxis(gamepad.RIGHT_Stick_X), time);
//			recordAction("Right_Trigger", gamepad.getRawAxis(gamepad.RIGHT_Trigger), time);
//			recordAction("Left_Trigger", gamepad.getRawAxis(gamepad.LEFT_Trigger), time);
					
			//DPad
			//recordAction("DPAD", gamepad.getRawAxis(gamepad.getPOV()), time);
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
