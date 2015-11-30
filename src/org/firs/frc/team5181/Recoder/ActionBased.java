package org.firs.frc.team5181.Recoder;

import org.usfirst.frc.team5181.robot.Gamepad;

import edu.wpi.first.wpilibj.Timer;

public class ActionBased {
	String recording;
	Gamepad gamepad;
	
	public ActionBased() {
		recording = "";
		gamepad = new Gamepad();
	}
	private void recordAction(String button, double magnitude, double time) {
		recording += button + ":" + magnitude + ":" + time + "\n";
	}
	private void sendActions() {
		//TODO Send recording to the laptop
	}
	public void startRecording(double time) {
		//for buttons
		recordAction("A_Button", toDouble(gamepad.getRawButton(gamepad.A_Button)), time);
	}
	public void stopRecording() {
		//TODO sendAtions?
		sendActions();
	}
	private double toDouble(boolean bool) {
		if (bool) {
			return 1.0;
		}
		else return 0.0;
	}
}
