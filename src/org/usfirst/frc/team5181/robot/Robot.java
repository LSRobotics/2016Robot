package org.usfirst.frc.team5181.robot;
import org.firs.frc.team5181.Recoder.ActionBased;

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
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	DriveTrain drive;
	
	public void robotInit() {
		
		
		auton = new Autonomous(this, "actionPlayback", 0);
		recorder = new ActionBased(ds);
		t = new Timer();
		gp = new Gamepad();
		
		drive = new DriveTrain(gp, speedLimit);
		t.start();
	}
	
	public void autonomous() {
		DriverStation.reportError("Started", false);
		auton.actionPlayback(0, "/var/rcrdng/autonRecording.rcrdng");
	}
	
	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			recording();
			teleopMaster(false);
		}
	}
	public void teleopMaster(boolean inAuton) {	
		if(!inAuton) {
			gp.update(false);
		}
		
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
		recorder.recording(t.get());
		
		if(gp.BACK_State)  {
			recorder.stopRecording();
		}
	}
}
