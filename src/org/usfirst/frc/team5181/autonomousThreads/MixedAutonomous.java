package org.usfirst.frc.team5181.autonomousThreads;
import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.RangeSensors;
import org.usfirst.frc.team5181.actuators.BallPickup;

import edu.wpi.first.wpilibj.Timer;

public class MixedAutonomous implements Autonomous {

	RangeSensors rangeSensors;
	boolean tStart = false;
	Timer t = new Timer();
	DriveTrain driveTrain;
	private Robot robot;
	BallPickup ballPickup = new BallPickup();
	
	public MixedAutonomous(RangeSensors sensors) {
		rangeSensors = sensors;
	}

	public void rangeSensingAuton() {
		if (rangeSensors.srBack.getRangeInches() <= 11.811) {
			if (rangeSensors.srFront.getRangeInches() <= 1.811) {
			//TODO done
				shoot();
			} else {
				cutThread();
			}
		} else {
			robot.drive.arcadeDrive(0.0, -0.3);
		}

	}

	public void cutThread() {
	}

	public void shoot() {
		ballPickup.setBallIntake(.2, .2);

	}

	@Override
	public void actionPlayback(String recordingName, long time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutonState(boolean inAuton) {
		// TODO Auto-generated method stub
		
	}
}
