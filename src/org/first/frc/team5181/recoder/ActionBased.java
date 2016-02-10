package org.first.frc.team5181.recoder;

import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Statics;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ActionBased extends Thread {
	
	List<String> recording;
	int timeStep;
	boolean isRecording;
	int recordingNumber;
	
	public ActionBased(long step) {
		recording = new ArrayList<String>();
		isRecording = false;
		timeStep = (int)step;
		recordingNumber = 0;
	}
	
	private void recordAction(int button, double magnitude) {
		recording.add(button + ":" + magnitude + ";");
	}
	
	private void sendActions() {
		try {
			DriverStation.reportError("\nFinished\n", false);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/var/rcrdng/autonRecording4.rcrdng")));
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
	public void startRecording() {
		isRecording = true;
		
		this.start();
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
				record();
				Thread.sleep((timeStep - 1), (1000000 - 503000));
			}
			
			if(!isRecording) {
				sendActions();
			}
		}
		catch(Exception e) {
			DriverStation.reportError(e.getMessage() + "\n", false);
		}
	}
}
