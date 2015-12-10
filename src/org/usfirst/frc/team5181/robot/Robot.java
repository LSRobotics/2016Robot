package org.usfirst.frc.team5181.robot;
import org.firs.frc.team5181.Recoder.ActionBased;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends IterativeRobot {
	
	private static double speedLimit = .7; //20% speed
	
	Victor leftFront;
	Victor leftBack;
	Victor rightFront;
	Victor rightBack;
	RobotDrive drive;
	Gamepad gp;
	ActionBased recorder;
	Timer t;
	
	public void robotInit() {
		leftFront = new Victor(Statics.LEFTPortFront);
		leftBack = new Victor(Statics.LEFTPortBack);
		rightFront = new Victor(Statics.RIGHTPortFront);
		rightBack = new Victor(Statics.RIGHTPortBack);
		
		drive = new RobotDrive(leftFront, leftBack, rightFront, rightBack);
		
		
		recorder = new ActionBased();
		t = new Timer();
		gp = new Gamepad();
		
		t.start();
	}
	
	public void autonomousPeriodic() {
		
	}
	
	public void teleopPeriodic() {
		//Speed Limit Control
		if(gp.getRawButton(Gamepad.RIGHT_Bumper)) {
			speedLimit += .1;
		}
		else if(gp.getRawButton(Gamepad.B_Button)) {
			speedLimit = 0;
		}
		else if(gp.getRawButton(Gamepad.LEFT_Bumper)){
			speedLimit -= 0.1;
		}
		
		//Tank Drive
		double leftDrive = gp.getRawAxis(gp.LEFT_Trigger);
		double rightDrive = gp.getRawAxis(gp.RIGHT_Trigger);
		double dir = Math.abs(gp.getRawAxis(gp.RIGHT_Stick_X)) / gp.getRawAxis(Gamepad.RIGHT_Stick_X); //1 or -1
		
		if(Math.abs(leftDrive) >= speedLimit) {
			leftDrive = speedLimit;
		}
		if(Math.abs(rightDrive) >= speedLimit) {
			rightDrive = speedLimit;
		}
		drive.tankDrive(dir * leftDrive, dir *rightDrive);
	}
	
	public void testPeriodic() {
		
	}
	/**
	 * Controls the starting and stopping of the recorder
	 */
	public void recording() {
		if(gp.getRawButton(Gamepad.START) == true) {
			recorder.startRecording(t.get());
		}
	}
}
