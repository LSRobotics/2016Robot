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
	Timer t;
	double prevTime;
	double timeStep;
	boolean isRecording;
	int state;
	
	public ActionBased(DriverStation ds, double step) {
		recording = "";
		gamepad = new Gamepad();
		this.ds = ds;
		state = 0;
		isRecording = false;
		timeStep = step;
		prevTime = 0 - timeStep;
		t = new Timer();
	}
	
	private void recordAction(int button, double magnitude) {
		recording += button + ":" + magnitude + ";";
	}
	
	public void newState() {
		recording += "\nstate:" + (state++) + "\n";
	}
	private void sendActions() {
		DriverStation.reportError(recording, false);
		
		try {
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter((new Socket("LSCHS-ROBOTICS", 5800).getOutputStream())));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/var/rcrdng/autonRecording3.rcrdng")));
			bw.write(recording);
		
		}
		catch(Exception e) {
			DriverStation.reportError(e.getMessage(), false);
		}
	}
	
	public void startRecording() {
		isRecording = true;
		t.start();
	}
	
	public void recording() {
		
		//for buttons
		if (isRecording && t.get() > prevTime + timeStep) {
			prevTime = t.get();
			
			newState();
			
			recordAction(gamepad.A_Button, toDouble(gamepad.getRawButton(gamepad.A_Button)));
			recordAction(gamepad.B_Button, toDouble(gamepad.getRawButton(gamepad.B_Button)));
			recordAction(gamepad.X_Button, toDouble(gamepad.getRawButton(gamepad.X_Button)));
			recordAction(gamepad.Y_Button, toDouble(gamepad.getRawButton(gamepad.Y_Button)));
			recordAction(gamepad.RIGHT_Bumper, toDouble(gamepad.getRawButton(gamepad.RIGHT_Bumper)));
			recordAction(gamepad.LEFT_Bumper, toDouble(gamepad.getRawButton(gamepad.LEFT_Bumper)));
			
			//for triggers/analog sticks
			recordAction(12, gamepad.getRawAxis(gamepad.LEFT_Stick_Y));
			recordAction(11, gamepad.getRawAxis(gamepad.LEFT_Stick_X));
			recordAction(16, gamepad.getRawAxis(gamepad.RIGHT_Stick_Y));
			recordAction(15, gamepad.getRawAxis(gamepad.RIGHT_Stick_X));
			recordAction(14, gamepad.getRawAxis(gamepad.RIGHT_Trigger));
			recordAction(13, gamepad.getRawAxis(gamepad.LEFT_Trigger));
					
			//DPad
			//recordAction("DPAD", gamepad.getPOV());
		}
	}
	
	public void stopRecording() {
		if (isRecording) {
			sendActions();
		}
		t.stop();
		isRecording = false;
	}
	
	private double toDouble(boolean bool) {
		if (bool) {
			return 1.0;
		}
		else return 0.0;
	}
}
