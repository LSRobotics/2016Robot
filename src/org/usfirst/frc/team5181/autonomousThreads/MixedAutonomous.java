package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.RangeSensors;
import org.usfirst.frc.team5181.sensors.RevX;
import org.usfirst.frc.team5181.actuators.BallPickup;
import org.usfirst.frc.team5181.autonomousThreads.PIDFunctions;
import org.usfirst.frc.team5181.autonomousThreads.PIDFunctions.Controllers;
import org.usfirst.frc.team5181.robot.UnitConversion;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class MixedAutonomous implements Autonomous {
	Robot r; 
	
	RangeSensors rangeSensors;
	BallPickup ballPickup;
	DriveTrain drive;
	PIDFunctions pidi;
	UnitConversion uc;
	RevX revX;
	private boolean inAuton;
	private String[] autonPosition; //[0] == Side [1] == Defense
	String state;
	boolean gotDistance;
	double sensedDistance;
	Autonomous recordingAuton;

	public static double d1 = 159.86 /* LEFT */, d2 = 116 /* RIGHT */, a1 = -120 /* LEFT */, a2 = 120 /* RIGHT */;
	
	public MixedAutonomous(Robot robot) {       
		r = robot;
		state = "recording";
		gotDistance = false;
		sensedDistance = 0;
		rangeSensors = robot.rangeSensors;
		ballPickup = robot.ballPickUp;
		drive = robot.drive;
		revX = robot.getRevX();
		pidi = new PIDFunctions(robot, Controllers.ROTATION, revX);
		uc = new UnitConversion();
		
		recordingAuton = new TimedAutonomous(robot);
	}

	/**
	 * @param recordingName
	 *            = "left" or "right"
	 */
	public void initializeAuton(String recordingName, String[] others) {
		autonPosition = others;
		
		recordingAuton.initializeAuton(recordingName, others);
	}

	@Override
	public void setAutonState(boolean inAuton) {
		this.inAuton = inAuton;

	}

	@Override
	public void doAuton() {
		//DO recording
		if (state.equalsIgnoreCase("recording")) {
			Thread temp = new Thread() {
				public void run() {
					recordingAuton.doAuton();
				}
			};
			temp.start();
			
			recordingAuton.setAutonState(inAuton);
			if(!temp.isAlive()) {
				state = "initialized";
			}
		}
		if (state.equalsIgnoreCase("initialized") && inAuton) {
			if (autonPosition[1].equalsIgnoreCase("Ramparts") || autonPosition[1].equalsIgnoreCase("ChivalDeFrise") || autonPosition[1].equalsIgnoreCase("Portecullis")) {
				state = "turning";
			}
			else {
				state = "backwards";
			}
		}
		
		//Turn if need be
		if(state.equalsIgnoreCase("turning") && inAuton) {
			try {
				pidi.setPIDSource(r.rangeSensors.srBack, Controllers.ROTATION);
				DriverStation.reportError("MixedAuton @ ln 70, turning 180 deg", false);
				if (!pidi.onTarget(Controllers.ROTATION)) {
					pidi.turnToAngle(180);
				}
				else {
					do {
						revX.reset();
					} while(revX.getAngle() != 0);
					state = "backwards";
				}
			}
			catch(NullPointerException e) {
				pidi = new PIDFunctions(r, Controllers.ROTATION, revX);
			}
			
		}
		
		boolean autonPositionIsLeft = autonPosition[0].equalsIgnoreCase("left");
		if (state.equalsIgnoreCase("backwards") && inAuton) {
			//Backwards after defense
			if (!gotDistance) {
				//Get measurements immediately
				sensedDistance = (autonPositionIsLeft) ? rangeSensors.srLeft.getRangeInches() : rangeSensors.srRight.getRangeInches();
				gotDistance = true;
			}
			pidi.setPIDSource(r.rangeSensors.srBack, Controllers.DISPLACEMENT);
			
			if(!pidi.onTarget(Controllers.DISPLACEMENT)) {
				double distance = (autonPositionIsLeft) ? d1 : d2;
				DriverStation.reportError("MixedAuton @ ln 90; Needed: " + (161.5 - ((distance - sensedDistance) * Math.tan(Math.PI/6) - 39)) + " current: " + rangeSensors.srBack.getRangeInches() + "\n", false);
				pidi.moveTo(-(uc.unitConversion("inches", "milimeters", (161.5 - ((distance - sensedDistance) * Math.tan(Math.PI/6) - 39))))); //I don't want to do it like that; Tim forced me to
			}
			else {
				state = "turnToFaceGoal";
			}
		}
		if (state.equalsIgnoreCase("turnToFaceGoal") && inAuton) {
			//Turn to face goal
			pidi.setPIDSource(revX, Controllers.ROTATION);
			if(!pidi.onTarget(Controllers.ROTATION)) {
				double angle = (autonPositionIsLeft) ? a1 : a2;
				DriverStation.reportError("MixedAuton @ ln 98, turning to face goal\n", false);
				pidi.turnToAngle(angle);
			}
			else {
				state = "DriveToGoal";
			}
		}
		if (state.equalsIgnoreCase("DriveToGoal") && inAuton) {
			//Drive to goal
			pidi.setPIDSource(r.rangeSensors.srFront, Controllers.DISPLACEMENT);
			//temporary comment as of 3-29-16
	//		while(!(Math.abs(revX.getWorldLinearAccelZ() + 1) <= 0.05) && inAuton) {
	//			pidi.moveTo(uc.unitConversion("feet", "centimeters", 4.0));
	//		}
			if(!(Math.abs(revX.getWorldLinearAccelZ() + 1) <= 0.05)) {
				DriverStation.reportError("MixedAuton @ ln 90, moving a distance\n", false);
				drive.arcadeDrive(0, 0.3);
			}
			else {
				state = "shoot";
			}
		}
		
		if (state.equalsIgnoreCase("shoot")) {
		//Shoot
			ballPickup.shootFree(1, 1);
		}
	}
}
