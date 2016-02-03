package org.usfirst.frc.team5181.robot;

import org.firs.frc.team5181.Recoder.ActionBased;

import Sensors.LimitSwitch;
import Sensors.Potentiometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends SampleRobot {
	
	//Speed Limit vars
	private static double speedLimit = .6; 
	
	//General vars
	ActionBased recorder;
	Timer t;
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	DriveTrain drive;
	Victor linAct;
	
	//Recorder Vars
	final long timeStep = 100; //in Milliseconds
	boolean isRecording;
	
	//Sensors
	Potentiometer potent;
	LimitSwitch limitSwitch;
	
	public void robotInit(){ 
		auton = new Autonomous(this);
		recorder = new ActionBased(timeStep);
		t = new Timer();
		drive = new DriveTrain(speedLimit);
		t.start();
		
	}
	
	public void autonomous() {
		auton.actionPlayback("/var/rcrdng/autonRecording4.rcrdng", timeStep);
	}
	
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			recording();
			teleopMaster(false);
		}
	}
	public void teleopMaster(boolean inAuton) {	
		if (!inAuton) {
			Gamepad.setNaturalState();
		}
		
		drive.updateSpeedLimit(Gamepad.RIGHT_Bumper_State, Gamepad.LEFT_Bumper_State, Gamepad.B_Button_State);
		drive.ArcadeDrive(Gamepad.RIGHT_Stick_X_State, Gamepad.RIGHT_Stick_Y_State);
	}
	
	
	/**
	 * Controls the starting and stopping of the recorder
	 */
	public void recording() {	
		if(Gamepad.START_State && !isRecording) {
			isRecording = true;
			   
			recorder.startRecording();
			
			DriverStation.reportError("Started", false); 	
		}
		
		if(Gamepad.BACK_State && isRecording)  {
			isRecording = false;
			recorder.stopRecording();
		}

		if(Gamepad.LEFT_Stick_DOWN_State) {
			recorder.incrementRecording();
		}
	}
}
