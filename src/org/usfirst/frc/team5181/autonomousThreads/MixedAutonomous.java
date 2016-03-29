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

	Autonomous recordingAuton;

	public static double d1 = 159.86 /* LEFT */, d2 = 116 /* RIGHT */, a1 = -120 /* LEFT */, a2 = 120 /* RIGHT */;
	
	public MixedAutonomous(Robot robot) {       
		r = robot;
		
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
		recordingAuton.doAuton();
		
		//Turn if need be
		if(autonPosition[1].equalsIgnoreCase("Ramparts") || autonPosition[1].equalsIgnoreCase("ChivalDeFrise") || autonPosition[1].equalsIgnoreCase("Portecullis")) {
			while(true && inAuton) {
				try {
					pidi.turnToAngle(180);
					do {
						revX.reset();
					} while(revX.getAngle() != 0);
					break;
				}
				catch(NullPointerException e) {
					pidi = new PIDFunctions(r, Controllers.ROTATION, revX);
				}
			}
		}
		
		//Backwards after defense
		pidi.setPIDSource(r.rangeSensors.srBack, Controllers.DISPLACEMENT);
		while(pidi.onTarget(Controllers.DISPLACEMENT) && inAuton) {
			double distance = (autonPosition[0].equalsIgnoreCase("left")) ? d1 : d2;
			pidi.moveTo(uc.unitConversion("inches", "milimeters", (161.5 - ((distance - rangeSensors.srRight.getRangeInches()) * Math.tan(Math.PI/6) - 39)))); //I don't want to do it like that; Tim forced me to
		}
		
		//Turn to face goal
		pidi.setPIDSource(revX, Controllers.ROTATION);
		while(pidi.onTarget(Controllers.ROTATION) && inAuton) {
			double angle = (autonPosition[0].equalsIgnoreCase("left")) ? a1 : a2;
			pidi.turnToAngle(angle);
		}
	
		//Drive to goal
		pidi.setPIDSource(r.rangeSensors.srFront, Controllers.DISPLACEMENT);
		while(!(Math.abs(revX.getWorldLinearAccelZ() + 1) <= 0.05) && inAuton) {
			pidi.moveTo(uc.unitConversion("feet", "centimeters", 4.0));
		}
		while(!(Math.abs(revX.getWorldLinearAccelZ() + 1) <= 0.05) && inAuton) {
				drive.arcadeDrive(0, 0.3);
		}
			
		//Shoot
		ballPickup.shootFree(1, 1);
		
				
	}
}
