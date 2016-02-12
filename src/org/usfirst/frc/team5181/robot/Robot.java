package org.usfirst.frc.team5181.robot;

import org.first.frc.team5181.recoder.ActionBased;

import sensors.LimitSwitch;
import sensors.Potentiometer;
import sensors.RevX;
import actuators.LinearActuator;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalOutput;

public class Robot extends SampleRobot {
	
	//Speed Limit vars
	private static double speedLimit = .6; 
	
	//General vars
	ActionBased recorder;
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	DriveTrain drive;
	Relay light;
	
	//Special
	Bear koala;
	
	//Sensors
	RevX revX;
	
	//Actuators
	//LinearActuator linAct;
	
	//Recorder Vars
	final long timeStep = 1; //in Milliseconds
	boolean isRecording;
	
	//Sensors
	Potentiometer potent;
	LimitSwitch limitSwitch;

	
	public void robotInit(){ 
		light = new Relay(0);
		light.set(Relay.Value.kOn);
		auton = new Autonomous(this);
		recorder = new ActionBased(timeStep);
		drive = new DriveTrain(speedLimit);
		
		//Sensors
		revX = new RevX(SPI.Port.kMXP);
		
		//Actuators
		//linAct = new LinearActuator(4, 0, 0.5, 17.5, 24, -0.1); //24 inch
		
		koala = new Bear();
	}
	
	public void autonomous() {
		auton.actionPlayback("/var/rcrdng/autonRecording4.rcrdng", timeStep);
		while(this.isAutonomous()) {
			auton.setAutonState(this.isAutonomous());
		}
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
		
		if (Gamepad.X_Button_State) {
			
		}
		
		if(Gamepad.A_Button_State) {
			double[] temp = revX.getDisplacement();
			for(int i = 0; i < 2; i++) {
				DriverStation.reportError(temp[i] + "\n", false);
			}
		}
		if(revX.hadCollision()) {
			DriverStation.reportError("Collision\n", false);
			koala.start();
		}
		
		if(Gamepad.Y_Button_State) {
			koala.start();
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
			
			DriverStation.reportError("Started\n", false); 	
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
