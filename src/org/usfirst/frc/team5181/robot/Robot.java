package org.usfirst.frc.team5181.robot;

import sensors.LimitSwitch;
import sensors.Potentiometer;
import sensors.RevX;
import actuators.BallPickup;
import actuators.LinearActuator;
import autonomousThreads.ActionBased;
import autonomousThreads.Autonomous;
import autonomousThreads.FrequencyAutonomous;
import autonomousThreads.TimedAutonomous;
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
	
	//Special
	Bear koala;
	SimpleClient client;
	
	//Sensors
	RevX revX;
	
	//Actuators
	BallPickup ballPickUp;
	
	//Recorder Vars
	final long timeFrequency = 500; //in actions/second
	final long period = 2; //in MS
	boolean isRecording;
	
	//Sensors
	Potentiometer potent;
	LimitSwitch limitSwitch;

	private boolean ballTracker;

	
	public void robotInit(){ 
		auton = new TimedAutonomous(this);
		recorder = new ActionBased();
		drive = new DriveTrain(speedLimit);
		
		//Sensors
		revX = new RevX(SPI.Port.kMXP);
		
		//Actuators
		ballPickUp = new BallPickup();
		
		koala = new Bear();
		client = new SimpleClient();
		ballTracker = false;
	}
	
	public void autonomous() {
		auton.actionPlayback("/var/rcrdng/autonRecording4.rcrdng", timeFrequency);
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
		
		ballPickUp.setBallIntake(Gamepad.RIGHT_Trigger_State, Gamepad.LEFT_Stick_Y_State);
		
		if (Gamepad.B_Button_State && !ballTracker) {
			ballTracker = true;
		}
		if (Gamepad.X_Button_State && ballTracker) {
			ballTracker = false;
		}
		
		if (ballTracker) {
			double currX = client.centerX;
			if (currX == -1) {
				drive.tankDrive(0, 0);
			}
			else if (currX > 320) {
				drive.tankDrive(.2, 0);
			}
			else {
				drive.tankDrive(0, .2);
			}
		}
		
		if(Gamepad.A_Button_State) {
			double[] temp = revX.getDisplacement();
			for(int i = 0; i < 2; i++) {
				DriverStation.reportError(temp[i] + "\n", false);
			}
		}
		if(revX.hadCollision()) {
			DriverStation.reportError("Collision\n", false);
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
			   
			recorder.startRecording(true, period);
			
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
