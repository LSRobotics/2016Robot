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
	private static boolean speedBool = false;
	
	
	Gamepad gp;
	ActionBased recorder;
	Timer t;
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	DriveTrain drive;
	
	public void robotInit() {
		
		
		auton = new Autonomous(this, "actionPlayback", 0, "autonRecording1.txt");
		recorder = new ActionBased(ds);
		t = new Timer();
		gp = new Gamepad();
		
		drive = new DriveTrain(gp, speedLimit);
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
		if(gp.RIGHT_Bumper_State && !speedBool) {
			drive.setSpeedLimit(speedLimit += 0.1);
			speedBool = true;
		}
		else if(gp.B_Button_State) {
			drive.setSpeedLimit(speedLimit = 0);
		}
		else if(gp.LEFT_Bumper_State && !speedBool) {
			drive.setSpeedLimit(speedLimit -= 0.1);
			speedBool = true;
		}
		else if(!gp.LEFT_Bumper_State && !gp.RIGHT_Bumper_State && speedBool){
			speedBool = false;
		}
		
		drive.ArcadeDrive(gp.RIGHT_Stick_X_State, gp.RIGHT_Stick_Y_State);
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
