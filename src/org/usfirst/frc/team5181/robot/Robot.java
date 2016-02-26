package org.usfirst.frc.team5181.robot;

import sensors.LimitSwitch;
import sensors.Potentiometer;
import sensors.RevX;
import actuators.BallPickup;
import actuators.LadderArm;
import actuators.LinearActuator;
import autonomousThreads.ActionBased;
import autonomousThreads.Autonomous;
import autonomousThreads.FrequencyAutonomous;
import autonomousThreads.PIDFunctions;
import autonomousThreads.PIDFunctions.Controllers;
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
	private static double speedLimit = .7; 
	
	//General vars
	ActionBased recorder;
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	DriveTrain drive;
	PIDFunctions pidi;
	
	//Special
	Bear koala;
	SimpleClient client;
	
	//Sensors
	RevX revX;
	
	//Actuators
	BallPickup ballPickUp;
	LadderArm arm;
	boolean rotateMAXPOWER;
	
	//Recorder Vars
	final long timeFrequency = 500; //in actions/second
	final long period = 25; //in MS
	boolean isRecording;
	
	//Sensors
	Potentiometer potent;
	LimitSwitch limitSwitch;

	private boolean ballTracker;
	private boolean clientStarted;

	public void robotInit(){ 
		drive = new DriveTrain(speedLimit);

		//Sensors
		revX = new RevX(SPI.Port.kMXP);
		
		//Actuators
		ballPickUp = new BallPickup();
		arm = new LadderArm(6, 7); //TODO change constructor
		rotateMAXPOWER = false;
		
		//Auton
		auton = new TimedAutonomous(this, drive);
		recorder = new ActionBased(revX);
		pidi = new PIDFunctions(this, drive);
	
		koala = new Bear();
		ballTracker = false;
		clientStarted = false;
		
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
		//Ball pickup
			ballPickUp.setBallIntake(Gamepad.LEFT_Trigger_State, Gamepad.RIGHT_Trigger_State);
		//End ball pickup
		
		// Start Raspberry Pi client
			if (!clientStarted) {
				client = new SimpleClient();
				clientStarted = true;
			}
		//End Start Raspberry Pi Client 
			
		//Ball Tracking
		/*
			if (Gamepad.B_Button_State && !ballTracker) {
				ballTracker = true;
			}
			if (Gamepad.X_Button_State && ballTracker) {
				ballTracker = false;
			}
			
			if (ballTracker) {
				int currX = client.centerX;
				int currY = client.centerY;
				DriverStation.reportError("\n(" + currX + ", " + currY + ")", false);
				if (currX == -1) {
					drive.tankDrive(.5, 0);
				}
				else if (currX > 320) {
					drive.tankDrive(0, .5);
				}
				else {
					drive.tankDrive(0, 0);
				}
			}
		*/
		//End Ball Tracking
		
		//Colliison
			if(revX.hadCollision()) {
				DriverStation.reportError("Collision\n", false);
			}
		//End Collision
		
		//Ladder
			if(Gamepad.A_Button_State) {
				arm.extend(LadderArm.extensionDirections.CONTRACT, 1);
			}
			
			if(Gamepad.Y_Button_State) {
				arm.extend(LadderArm.extensionDirections.EXTEND, 1); 
			}
			
			if(!Gamepad.A_Button_State && !Gamepad.Y_Button_State) {
				arm.extendFree(0);
			}
			
			if(Gamepad.LEFT_Stick_DOWN_State) {
				arm.stayRotated();
			}
			else if(!Gamepad.LEFT_Stick_DOWN_State) {
				arm.rotate(-Gamepad.LEFT_Stick_Y_State, 0.25);
			}
			
			if(Gamepad.D_PAD_State == 90) {
				rotateMAXPOWER = true;
			}
			if(rotateMAXPOWER) {
				arm.rotateFree(-Gamepad.LEFT_Stick_Y_State);
			}
		//End ladder
			
		//Drive
			drive.updateSpeedLimit(Gamepad.RIGHT_Bumper_State, Gamepad.LEFT_Bumper_State, Gamepad.B_Button_State);
			drive.arcadeDrive(Gamepad.RIGHT_Stick_X_State, Gamepad.RIGHT_Stick_Y_State);
		//End Drive
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
		
		//Setpoint
		if(Gamepad.START_State && isRecording) {
			recorder.addSetpoint();
		}
		if(Gamepad.START_State && !isRecording) {
			recorder.resetSetpoint();
		}
		
		if(Gamepad.BACK_State && isRecording)  {
			isRecording = false;
			recorder.stopRecording();
		}

		if(Gamepad.LEFT_Stick_DOWN_State) {
			recorder.incrementRecording();
		}
	}
	public RevX getRevX() {
		return revX;
	}

	
	public void test() {
		Gamepad.setNaturalState();
		autoTunePID(Controllers.ROTATION);
	}
	
	/**
	 * A button signifies convergence 
	 * B signifies oscillation
	 * X signifies divergence
	 * Y signifies reduce currDelta by a factor of 10 and start gain at last stable value
	 * @param controller either rotation or displacement
	 */
	private boolean xPressed = false, aPressed = false, bPressed = false, yPressed = false;
	private int xGain = 0; //0 == kP, 1 == kD, 2 == kI
	private double stableP = 0,stableD = 0, stableI = 0;
	private double currP = 0.01, currD = 0, currI = 0;
	private double currDelta = 0.05;
	private boolean turnTo90 = true;
	public void autoTunePID(Controllers controller) {
		pidi.upadtePID(controller, currP, currD, currI, 0);
		pidi.setPID(controller);
		
		if(turnTo90) {
			pidi.turnToAngle(90);
		}
		else {
			pidi.turnToAngle(0);
		}
		
		//Converges and gain needs to be increased if P, if D then P needs to be increased again
		if(Gamepad.A_Button_State) {
			aPressed = true;
			turnTo90 = !turnTo90;
			switch(xGain) {
				case 0: //P
					stableP = currP;
					currP += currDelta;
				case 1: //D
					stableD = currD;
					xGain = 0; //D is stable increase P
				case 2://I
					stableI = currI;
					DriverStation.reportError("P: " + stableP + "  ,  D: " + stableD + "  ,  I: " + stableI + "\n", false);
			}
		}
		else {
			aPressed = false;
		}
		
		//If P-gain causes oscillation OR D-gain causes spasms
		if(Gamepad.B_Button_State) {
			bPressed = true;
			turnTo90 = !turnTo90;
			switch(xGain) {
				case 0: //P
					xGain++;
				case 1: //D
					xGain = 2; //revert D and tune I
				case 2://I
					stableI = currI;
			}	
		}
		else {
			bPressed = false;
		}
		
		//If currDelta is too high
		if(Gamepad.Y_Button_State) {
			yPressed = true;
			turnTo90 = !turnTo90;
			
			switch(xGain) {
				case 0: //P
					currP = stableP;
					currDelta /= 10;
				case 1: //D
					currD = stableD;
					currDelta /= 10;
				case 2://I
					currI = stableI;
					currDelta /= 10;
			}	
		}
		else {
			yPressed = false;
		}
	}
}
