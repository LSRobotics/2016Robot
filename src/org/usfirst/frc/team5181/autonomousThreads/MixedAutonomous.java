package org.usfirst.frc.team5181.autonomousThreads;
import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.RangeSensors;
import org.usfirst.frc.team5181.actuators.BallPickup;

import edu.wpi.first.wpilibj.Timer;

public class MixedAutonomous implements Autonomous {

	RangeSensors rangeSensors;
	BallPickup ballPickup;
	DriveTrain drive;
	
	private boolean inAuton;
	
	TimedAutonomous recordingAuton;
	
	public MixedAutonomous(Robot robot) {
		rangeSensors = robot.rangeSensors;
		ballPickup = robot.ballPickUp;
		drive = robot.drive;
		recordingAuton = new TimedAutonomous(robot);
	}	
	public void initializeAuton(String recordingName) {
		recordingAuton.initializeAuton(recordingName);                 
		
	}

	@Override
	public void setAutonState(boolean inAuton) {
		this.inAuton = inAuton;
		
	}

	@Override
	public void doAuton() {
		if (rangeSensors.srBack.getRangeInches() <= 11.811) {
			if (rangeSensors.srFront.getRangeInches() <= 1.811) {
				//shoot
			} else {
				//What here
			}
		} else {
			drive.arcadeDrive(0.0, -0.3);
		}
	}
}
