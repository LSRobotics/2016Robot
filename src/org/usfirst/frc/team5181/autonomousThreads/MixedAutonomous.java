package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.SonicRangeSensor;
import org.usfirst.frc.team5181.actuators.BallPickup;

import edu.wpi.first.wpilibj.Timer;

public class MixedAutonomous extends Thread {

	SonicRangeSensor s = new SonicRangeSensor(); // TODO Map to port
	boolean tStart = false;
	Timer t = new Timer();
	DriveTrain driveTrain;
	private Robot robot;
	BallPickup ballPickup = new BallPickup();

	public void rangeSensingAuton() throws Exception {
		if (s.getRangeInches() <= 11.811) {
			if (s.getRangeInches() <= 1.811) {
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

	public void run() {
		try {
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}