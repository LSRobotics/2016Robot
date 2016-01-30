package org.usfirst.frc.team5181.robot;
import org.firs.frc.team5181.Recoder.ActionBased;

import Sensors.Potentiometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends SampleRobot {
	
	private static double speedLimit = .6; 
	
	Gamepad gp;
	ActionBased recorder;
	Timer t;
	static final double timeStep = .01;
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	DriveTrain drive;
	Victor linAct;
	Potentiometer potent;
	
	public void robotInit() {
		auton = new Autonomous(this, "actionPlayback", timeStep);
		recorder = new ActionBased(ds, timeStep);
		t = new Timer();
		gp = new Gamepad();
		linAct = new Victor(4);
		drive = new DriveTrain(gp, speedLimit);
		t.start();
		potent = new Potentiometer();
	}
	
	public void autonomous() {
		auton.actionPlayback("/var/rcrdng/autonRecording3.rcrdng");
	}
	
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			recording();
			teleopMaster(false);
		}
	}
	public void teleopMaster(boolean inAuton) {	
		gp.update(inAuton);
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
		if(gp.START_State) {
			DriverStation.reportError("Started", false);
			t.reset();            
			recorder.startRecording();
		}
		recorder.recording();
		
		if(gp.BACK_State)  {
			recorder.stopRecording();
		}
	}
}
