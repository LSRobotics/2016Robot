package org.usfirst.frc.team5181.robot;

import org.usfirst.frc.team5181.actuators.BallPickup;
import org.usfirst.frc.team5181.actuators.Boris;
import org.usfirst.frc.team5181.actuators.LadderArm;
import org.usfirst.frc.team5181.actuators.LinearActuator;
import org.usfirst.frc.team5181.autonomousThreads.*;
import org.usfirst.frc.team5181.autonomousThreads.PIDFunctions.Controllers;
import org.usfirst.frc.team5181.sensors.LimitSwitch;
import org.usfirst.frc.team5181.sensors.Potentiometer;
import org.usfirst.frc.team5181.sensors.RangeSensors.SonicRangeSensor;
import org.usfirst.frc.team5181.sensors.RevX;
import org.usfirst.frc.team5181.sensors.RangeSensors;

import edu.wpi.first.wpilibj.CameraServer;
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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {

	// Speed Limit vars

	private static double speedLimit = .8;

	// General vars
	ActionBased recorder;
	DriverStation ds = DriverStation.getInstance();
	Autonomous auton;
	public DriveTrain drive;

	// Special
	Bear koala;
	SimpleClient client;
	CameraServer server;

	// Autonomous Chooser
	SendableChooser autonChooser;
	SendableChooser positionChooser;
	SendableChooser scoreLowGoal;
	
	// Sensors
	public RevX revX;
	public RangeSensors rangeSensors;
	// Actuators
	public BallPickup ballPickUp;
	LadderArm arm;
	Boris boris;
	boolean rotateMAXPOWER;

	// Recorder Vars
	final long period = 25; // in MS
	boolean isRecording;

	// Sensors
	Potentiometer potent;
	//public LimitSwitch limitSwitch;

	private boolean ballTracker;
	private boolean clientStarted;

	public void robotInit() {
		drive = new DriveTrain(speedLimit);

		// Sensors
		revX = new RevX(SPI.Port.kMXP);
		//limitSwitch = new LimitSwitch(7);
		rangeSensors = new RangeSensors();
		
		// Actuators
		ballPickUp = new BallPickup();
		//arm = new LadderArm(6, 7, 0); // TODO change constructor
		rotateMAXPOWER = false;
		boris = new Boris(Statics.BORIS_PORT);
		
		// Auton		
		recorder = new ActionBased(revX);
		autonChooser = new SendableChooser();
		autonChooser.addDefault("Rock-Wall", "/var/rcrdng/rockWall.rcrdng");
		autonChooser.addObject("Rough-Terrain", "/var/rcrdng/roughTerrain.rcrdng");
		SmartDashboard.putData("Auto Selector", autonChooser);

		positionChooser = new SendableChooser();
		positionChooser.addDefault("Right", "right");
		positionChooser.addDefault("Left", "left");
		SmartDashboard.putData("Position Selector", positionChooser);
		
		scoreLowGoal = new SendableChooser();
		scoreLowGoal.addDefault("Don't Score Low Goal", false);
		scoreLowGoal.addObject("Score Low Goal", true);
		SmartDashboard.putData("Score Low Goal Chooser", scoreLowGoal);
		
		koala = new Bear();
		ballTracker = false;
		clientStarted = false;

		//server = CameraServer.getInstance();
		//server.setQuality(100);
		//server.startAutomaticCapture("cam0");
	}

	public void autonomous() {
		boolean lowGoal = (boolean) scoreLowGoal.getSelected();
		String selectedAuton = (String) autonChooser.getSelected();
		String selectedPosition = (String) positionChooser.getSelected();
		
		if (lowGoal) {
			auton = new MixedAutonomous(this);
			auton.initializeAuton(selectedAuton, new String[] {selectedPosition, selectedAuton.substring(selectedAuton.lastIndexOf('/'), selectedAuton.indexOf('.'))}); //need to find a better way to get the straight defense name
			while (this.isAutonomous()) {
				auton.setAutonState(this.isAutonomous());
				auton.doAuton();
			}
		}
		else {
			auton = new TimedAutonomous(this);
			auton.initializeAuton(selectedAuton, new String[] {"", ""}); //TimedAutonomous doesn't use the second argument, don't want to waste time doing substring
			new Thread() { 
				public void run() {
						auton.doAuton();
					}
			}.start();
			while (this.isAutonomous()) {
				auton.setAutonState(this.isAutonomous());
			}
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
		// Ball pickup
			ballPickUp.setBallIntake(Gamepad.LEFT_Trigger_State, Gamepad.RIGHT_Trigger_State);
		// End ball pickup
		
		// Start Raspberry Pi client
			// if (!clientStarted) {
			// client = new SimpleClient();
			// clientStarted = true;
			// }
		// End Start Raspberry Pi Client

		// Ball Tracking
			/*
			 * if (Gamepad.B_Button_State && !ballTracker) { ballTracker = true; }
			 * if (Gamepad.X_Button_State && ballTracker) { ballTracker = false; }
			 * 
			 * if (ballTracker) { int currX = client.centerX; int currY =
			 * client.centerY; DriverStation.reportError("\n(" + currX + ", " +
			 * currY + ")", false); if (currX == -1) { drive.tankDrive(.5, 0); }
			 * else if (currX > 320) { drive.tankDrive(0, .5); } else {
			 * drive.tankDrive(0, 0); } }
			 */
		// End Ball Tracking

		// Colliison
			if (revX.hadCollision()) {
				DriverStation.reportError("Collision\n", false);
			}
		// End Collision

		// Ladder
			if(Gamepad.TRIGGER_State) {
				if (Gamepad.A_Button_State) {
					arm.extend(LadderArm.extensionDirections.CONTRACT, 1);
				}
		
				if (Gamepad.Y_Button_State) {
					arm.extend(LadderArm.extensionDirections.EXTEND, 1);
				}
		
				if (!Gamepad.A_Button_State && !Gamepad.Y_Button_State) {
					//arm.extendFree(0);
				}
		
				if (Gamepad.LEFT_Stick_DOWN_State) {
					arm.stayRotated();
				} else if (!Gamepad.LEFT_Stick_DOWN_State) {
					arm.rotate(Gamepad.LEFT_Stick_Y_State, 0.35);
				}
		
				rotateMAXPOWER = (Gamepad.D_PAD_State == 0);
				
				if (rotateMAXPOWER) {
					arm.rotateFree(Gamepad.LEFT_Stick_Y_State);
				}
			}
		// End ladder

		//Boris
			else {
				boris.Set(Gamepad.LEFT_Stick_Y_State);
			}
		//End Boris
		
		// Drive
			drive.updateSpeedLimit(Gamepad.RIGHT_Bumper_State, Gamepad.LEFT_Bumper_State, Gamepad.B_Button_State);
			drive.arcadeDrive(Gamepad.RIGHT_Stick_X_State, Gamepad.RIGHT_Stick_Y_State);
		// End Drive
	}

	/**
	 * Controls the starting and stopping of the recorder
	 */
	public void recording() {
		if (Gamepad.START_State && !isRecording) {
			isRecording = true;

			recorder.startRecording(true, period);
			recorder.setFile((String) autonChooser.getSelected());
			DriverStation.reportError("Started\n", false);
		}

		// Setpoint
		if (Gamepad.START_State && isRecording) {
			recorder.addSetpoint();
		}
		if (!Gamepad.START_State) {
			recorder.resetSetpoint();
		}

		if (Gamepad.BACK_State && isRecording) {
			isRecording = false;
			recorder.stopRecording();
		}

		if (Gamepad.LEFT_Stick_DOWN_State) {
			recorder.incrementRecording();
		}
	}

	public RevX getRevX() {
		return revX;
	}

	public void test() {
		//	while (this.isEnabled()) {
		//		auton.doAuton();
		//	}
		
		//SonicRangeSensor sonic = new SonicRangeSensor(Statics.FRONT_Ultra_Echo, Statics.FRONT_Ultra_Trigger);
//		sonic.ultra.setEnabled(true);
//		sonic.ultra.setAutomaticMode(true);
		
		PIDFunctions pid = new PIDFunctions(this, Controllers.DISPLACEMENT, rangeSensors.srBack);
		while(this.isEnabled()) {
			pid.moveTo(250);
			DriverStation.reportError("\n FRONT: " + rangeSensors.srBack.getRangeMm(), false);
		}
		
	}
}
