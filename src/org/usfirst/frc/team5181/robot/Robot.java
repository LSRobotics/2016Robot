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
	
	private boolean left_bumper = false;
	private boolean right_bumper = false;
	private double kPr = 0.15;
	private double kDr = 0.1;
	private double kIr = 0.00003;
	
	public void test() {
		if (Gamepad.LEFT_Bumper_State && !left_bumper) {
			kPr += 0.05;
			DriverStation.reportError("kPr: " + kPr + " kDr: " + kDr + "\n", false);
			left_bumper = true;
		}
		if (!Gamepad.LEFT_Bumper_State) {left_bumper = false;}
		if (Gamepad.RIGHT_Bumper_State && !right_bumper) {
			kIr += 0.0001;
			DriverStation.reportError("kIr " + kIr + "\n", false);
			right_bumper = true;
		}
		if (!Gamepad.RIGHT_Bumper_State) {right_bumper = false;}
		if(Gamepad.A_Button_State) {
			revX.zeroYaw();
		}
		if(Gamepad.X_Button_State) {
			pidi.turnToAngle(90);
		}
		if(Gamepad.B_Button_State) {
			pidi.turnToAngle(0);
		}
		if (Gamepad.Y_Button_State) {
			DriverStation.reportError("" + revX.getYaw() + "\n", false);
		}
		//DriverStation.reportError("\tYAW " + revX.getYaw() + "\n", false);
		pidi.pidiR.setPID(0.15, kIr, 0.1);
		DriverStation.reportError("" + revX.getPIDSourceType() + "\n", false);
	}
}
