package org.usfirst.frc.team5181.robot;
import org.firs.frc.team5181.Recoder.ActionBased;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends IterativeRobot {
	
	private static double speedLimit = .6; 
	
	Victor leftFront;
	Victor leftBack;
	Victor rightFront;
	Victor rightBack;
	RobotDrive drive;
	Gamepad gp;
	ActionBased recorder;
	Timer t;
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	
	public void robotInit() {
		leftFront = new Victor(Statics.LEFTPortFront);
		leftBack = new Victor(Statics.LEFTPortBack);
		rightFront = new Victor(Statics.RIGHTPortFront);
		rightBack = new Victor(Statics.RIGHTPortBack);
		
		drive = new RobotDrive(leftFront, leftBack, rightFront, rightBack);
		
		auton = new Autonomous(this, "actionPlayback", 0, "autonRecording1.txt");
		recorder = new ActionBased(ds);
		t = new Timer();
		gp = new Gamepad();
		
		t.start();
	}
	
	public void autonomousPeriodic() {
		
	}
	
	public void teleopPeriodic() {
		teleopPeriodicMaster(false);
	}
	public void teleopPeriodicMaster(boolean inAuton) {	
		if(!inAuton) {
			gp.update(false);
		}
		
		//Speed Limit Control
		if(gp.RIGHT_Bumper_State) {
			speedLimit += .1;
		}
		else if(gp.B_Button_State) {
			speedLimit = 0;
		}
		else if(gp.LEFT_Bumper_State) {
			speedLimit -= 0.1;
		}
		
		//Tank drive normal
		double left = gp.LEFT_Stick_Y_State;
		double right = gp.RIGHT_Stick_Y_State;
		drive.tankDrive(-left * speedLimit, -right * speedLimit);
	}
	
	public void testPeriodic() {
		recording(); 
		teleopPeriodic();
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
