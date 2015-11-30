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
	public void stratRecording(double time) { 
		if(gamepad.getRawButton(Gamepad.A_Button)) {
			recordAction("A_Button", 1, time);
		}
	}
	public void stopRecording() {
		//TODO sendAtions?
		sendActions();
	}
}
