package org.usfirst.frc.team5181.robot;

import org.firs.frc.team5181.Recoder.ActionBased;
import org.firs.frc.team5181.Recoder.Lock;

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
	Gamepad gp;
	ActionBased recorder;
	Timer t;
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	DriveTrain drive;
	Victor linAct;
	Potentiometer potent;
	Lock lock;
	//Recorder Vars
	final long timeStep = 10; //in Milliseconds
	boolean isRecording;
	
	public void robotInit() {
		lock = new Lock();
		auton = new Autonomous(this, "actionPlayback");
		recorder = new ActionBased(ds, timeStep, gp, lock);
		t = new Timer();
		gp = new Gamepad(3);
		linAct = new Victor(4);
		drive = new DriveTrain(gp, speedLimit);
		t.start();
		potent = new Potentiometer();
	}
	
	public void autonomous() {
		auton.actionPlayback("/var/rcrdng/autonRecording3.rcrdng", timeStep);
	}
	
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			recording();
			teleopMaster(false);
		}
	}
	public void teleopMaster(boolean inAuton) {	
		if (!inAuton) {
			gp.getPhysicalState(lock);
		}
		if (gp.Y_Button_State) {
			DriverStation.reportError("" + potent.getPosition(), false);
		}
		
		linAct.set(gp.LEFT_Stick_Y_State);
		drive.updateSpeedLimit();
		drive.ArcadeDrive(gp.RIGHT_Stick_X_State, gp.RIGHT_Stick_Y_State);
	}
	
	
	/**
	 * Controls the starting and stopping of the recorder
	 */
	public void recording() {	
		if(gp.START_State && !isRecording) {
			isRecording = true;
			
			DriverStation.reportError("Started", false);          
			recorder.startRecording();
		}
		if(gp.BACK_State && isRecording)  {
			isRecording = false;
			recorder.stopRecording();
		}
	}
}
