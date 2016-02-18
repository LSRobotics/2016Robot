package autonomousThreads;

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
	long timeFrequency;
	boolean isRecording;
	int recordingNumber;
	boolean includeTimes = false;
	
	double currentRunTime = 0; //Time to run code in MS
	
	public ActionBased() {
		recording = new ArrayList<String>();
		isRecording = false;
		recordingNumber = 0;
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
	
	/**
	 * 
	 * @param includeTimeInvervals
	 * @param timeFrequency is includeTimeIntervals is true this parameter becomes a period not a frequency
	 */
	public void startRecording(boolean includeTimeInvervals, long timeFrequency) {
		isRecording = true;
		includeTimes = includeTimeInvervals;
		this.timeFrequency = timeFrequency;
		
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
				currentRunTime = System.currentTimeMillis();
				
				record();
				
				currentRunTime -= System.currentTimeMillis();
				
				long delayMS ;
				int delayNS;
				if(!includeTimes) {
					delayMS = ((1/timeFrequency) * (1000)) - (int) (currentRunTime + 1);
					delayNS = (int) (((((long) currentRunTime) - currentRunTime) * 1000) + 0.5); //Rounds in the case that the result is a decimal
					Thread.sleep(delayMS, delayNS);
				}
				else {
					delayMS = timeFrequency - (int)currentRunTime;
					delayNS = (int) (((((long) currentRunTime) - currentRunTime) * 1000) + 0.5); //Rounds in the case that the result is a decimal
					Thread.sleep(delayMS, delayNS);
				}
				
				//Records total time between actions
				//Timing error, but error is minimal
				if(includeTimes) {
					recordPeriod(delayMS + (delayNS / 1000000)); //in MS //More accurate than simply adding timeFrequency (ie period)
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
}
