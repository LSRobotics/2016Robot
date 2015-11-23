package org.firs.frc.team5181.Recoder;

import org.usfirst.frc.team5181.robot.Gamepad;

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
		//TODO
	}
	public void stratRecording() {
		
	}
	public void stopRecording() {
		
	}
}
