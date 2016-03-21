package org.usfirst.frc.team5181.robot;

import org.usfirst.frc.team5181.actuators.BallPickup;
import org.usfirst.frc.team5181.actuators.Boris;
import org.usfirst.frc.team5181.actuators.LadderArm;
import org.usfirst.frc.team5181.actuators.LinearActuator;
import org.usfirst.frc.team5181.autonomousThreads.ActionBased;
import org.usfirst.frc.team5181.autonomousThreads.Autonomous;
import org.usfirst.frc.team5181.autonomousThreads.FrequencyAutonomous;
import org.usfirst.frc.team5181.autonomousThreads.PIDFunctions;
import org.usfirst.frc.team5181.autonomousThreads.StupidAutonomous;
import org.usfirst.frc.team5181.autonomousThreads.TimedAutonomous;
import org.usfirst.frc.team5181.autonomousThreads.PIDFunctions.Controllers;
import org.usfirst.frc.team5181.autonomousThreads.TestingAutonomous;
import org.usfirst.frc.team5181.sensors.LimitSwitch;
import org.usfirst.frc.team5181.sensors.Potentiometer;
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
	public PIDFunctions pidi;
	StupidAutonomous stupidAuton;

	// Special
	Bear koala;
	SimpleClient client;
	CameraServer server;

	// Autonomous Chooser
	Command autonCommand;
	SendableChooser autonChooser;

	// Testing Autonomous
	TestingAutonomous ta;

	// Sensors
	public RevX revX;
	public RangeSensors rangeSensors;
	
	// Actuators
	BallPickup ballPickUp;
	LadderArm arm;
	Boris boris;
	boolean rotateMAXPOWER;

	// Recorder Vars
	final long timeFrequency = 500; // in actions/second
	final long period = 25; // in MS
	boolean isRecording;

	// Sensors
	Potentiometer potent;
	public LimitSwitch limitSwitch;

	private boolean ballTracker;
	private boolean clientStarted;

	public void robotInit() {
		drive = new DriveTrain(speedLimit);

		// Sensors
		revX = new RevX(SPI.Port.kMXP);
		limitSwitch = new LimitSwitch(7);
		rangeSensors = new RangeSensors();
		// Actuators
		ballPickUp = new BallPickup();
		arm = new LadderArm(6, 7, 0); // TODO change constructor
		rotateMAXPOWER = false;

		// Auton
		auton = new TimedAutonomous(this, drive);
		recorder = new ActionBased(revX);
		pidi = new PIDFunctions(this, drive);
		stupidAuton = new StupidAutonomous(this);
		autonChooser = new SendableChooser();
		autonChooser.addDefault("Rock-Wall", "/var/rcrdng/rockWall.rcrdng");
		autonChooser.addObject("Rough-Terrain",
				"/var/rcrdng/roughTerrain.rcrdng");
		SmartDashboard.putData("Auto Selector", autonChooser);

		koala = new Bear();
		ballTracker = false;
		clientStarted = false;

		server = CameraServer.getInstance();
		server.setQuality(100);
		server.startAutomaticCapture("cam0");
		
		try {
			ta = new TestingAutonomous(rangeSensors);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void autonomous() {
		String selectedAuton = (String) autonChooser.getSelected();
		while (this.isAutonomous()) {
			auton.setAutonState(this.isAutonomous());
		}
	}

	public void scoreLowGoalAuton() {
		String selectedAuton = (String) autonChooser.getSelected();
		while (this.isAutonomous()) {
			auton.setAutonState(this.isAutonomous());
		}
		stupidAuton.autonomousPeriodic();

		/**
		 * Call to the recorded files
		 */
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
		ballPickUp.setBallIntake(Gamepad.LEFT_Trigger_State,
				Gamepad.RIGHT_Trigger_State);
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
		if (Gamepad.A_Button_State) {
			arm.extend(LadderArm.extensionDirections.CONTRACT, 1);
		}

		if (Gamepad.Y_Button_State) {
			arm.extend(LadderArm.extensionDirections.EXTEND, 1);
		}

		if (!Gamepad.A_Button_State && !Gamepad.Y_Button_State) {
			arm.extendFree(0);
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
		//DriverStation.reportError(rotateMAXPOWER + "\n", false);
		// End ladder

		//Boris
		boris.Set(Gamepad.LEFT_Stick_Y_State);
		//End Boris
		
		// Drive
		drive.updateSpeedLimit(Gamepad.RIGHT_Bumper_State,
				Gamepad.LEFT_Bumper_State, Gamepad.B_Button_State);
		drive.arcadeDrive(Gamepad.RIGHT_Stick_X_State,
				Gamepad.RIGHT_Stick_Y_State);
		// End Drive
	}

	/**
	 * Controls the starting and stopping of the recorder
	 */
	public void recording() {
		if (Gamepad.START_State && !isRecording) {
			isRecording = true;

			recorder.startRecording(true, period);
			// recorder.setFile((String) autonChooser.getSelected());
			recorder.setFile("/var/rcrdng/autonRecordingComp.rcrdng");
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
		try {
			ta.testAutonomous();
			
			/**
			while (this.isEnabled()) {
				sr.restartSensor();
				DriverStation.reportError(
						"[SRX]Current distence is:" + sr.getRangeInches()
								+ "inches" + "\n", false);
			
			}**/
			
		} catch (Exception e) {
			DriverStation.reportError("Catastrophe", false);
		}
	}

	/**
	 * A button signifies convergence B signifies oscillation X signifies
	 * divergence Y signifies reduce currDelta by a factor of 10 and start gain
	 * at last stable value
	 * 
	 * @param controller
	 *            either rotation or displacement
	 */
	private boolean xPressed = false, aPressed = false, bPressed = false, yPressed = false;
	private int xGain = 0; // 0 == kP, 1 == kD, 2 == kI
	private double stableP = 0.00, stableD = 0.00, stableI = 0;
	private double currP = 0.00, currD = 0.000, currI = 0;
	private double currDelta = 0.05;
	private boolean turnTo90 = true;

	public void autoTunePID(Controllers controller) {
		pidi.upadtePID(controller, currP, currI, currD, 0);
		pidi.setPID(controller);

		DriverStation.reportError("P: " + currP + "  ,  D: " + currD
				+ "  ,  I: " + currI + "  ,  Delta: " + currDelta + "  ,  R:"
				+ revX.getRotation() + "\n", false);

		if (turnTo90) {
			pidi.turnToAngle(90);
		} else {
			pidi.turnToAngle(0);
		}

		// Converges and gain needs to be increased if P, if D then P needs to
		// be increased again
		if (Gamepad.A_Button_State && !aPressed) {
			aPressed = true;
			turnTo90 = !turnTo90;
			switch (xGain) {
			case 0: // P
				stableP = currP;
				currP += currDelta;
				break;
			case 1: // D
				stableD = currD;
				xGain = 0; // D is stable increase P
				currDelta = 0.05;
				break;
			case 2:// I
				stableI = currI;
				DriverStation.reportError("P: " + stableP + "  ,  D: "
						+ stableD + "  ,  I: " + stableI + "  ,  " + "Rot:"
						+ revX.getRotation() + "\n", false);
				break;
			}
		} else if (!Gamepad.A_Button_State) {
			aPressed = false;
		}

		// If P-gain causes oscillation OR D-gain causes spasms
		if (Gamepad.B_Button_State && !bPressed) {
			bPressed = true;
			turnTo90 = !turnTo90;
			switch (xGain) {
			case 0: // P

				xGain = 1;
				break;
			case 1: // D
				currD += currDelta;
				break;
			case 2:// I
				stableI = currI;
				currI += currDelta;
				break;

			}
		} else if (!Gamepad.B_Button_State) {
			bPressed = false;
		}

		// If currDelta is too high
		if (Gamepad.Y_Button_State && !yPressed) {
			yPressed = true;
			turnTo90 = !turnTo90;

			switch (xGain) {
			case 0: // P
				currP = stableP;
				break;
			case 1: // D
				currD = stableD;
				break;
			case 2:// I
				currI = stableI;
				break;
			}
			currDelta /= 5;
		} else if (!Gamepad.Y_Button_State) {
			yPressed = false;
		}
		// If for when DGAin dosent stablize
		if (Gamepad.X_Button_State && !xPressed) {
			xPressed = true;
			turnTo90 = !turnTo90;

			switch (xGain) {
			case 0: // P
				//
				break;
			case 1: // D
				currD = stableD;
				xGain = 2;
				break;
			case 2:// I
					//
				break;
			}
		} else if (!Gamepad.X_Button_State) {
			xPressed = false;
		}
	}
}
