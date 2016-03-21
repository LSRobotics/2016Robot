package org.usfirst.frc.team5181.autonomousThreads;
import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.UseRangeSensors;
import org.usfirst.frc.team5181.actuators.BallPickup;

import edu.wpi.first.wpilibj.Timer;

public class MixedAutonomous {

	UseRangeSensors rangeSensors;
	boolean tStart = false;
	Timer t = new Timer();
	DriveTrain driveTrain;
	private Robot robot;
	BallPickup ballPickup = new BallPickup();
	
	public MixedAutonomous(UseRangeSensors sensors) {
		rangeSensors = new UseRangeSensors();
	}

	public void rangeSensingAuton() throws Exception {
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
}
