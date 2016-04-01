package org.usfirst.frc.team5181.actuators;

import org.usfirst.frc.team5181.robot.Filters;
import org.usfirst.frc.team5181.robot.Statics;
import org.usfirst.frc.team5181.sensors.LimitSwitch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Talon;

public class BallPickup {
	Talon left, right;
	LimitSwitch ballInTrap;
	
	public BallPickup() {
		left = new Talon(Statics.LeftBall);
		right = new Talon(Statics.RightBall); 
	}
	
	public void setBallIntake(double leftMag, double rightMag) {
		if(rightMag > .1) {
			left.set(-1);
			right.set(1);
		}
		if(leftMag > 0.1) {
			left.set(-.2);
			right.set(.2);
		}
	}
	
	public void shootFree(double leftMag, double rightMag) {
		left.set(-leftMag);
		right.set(rightMag);
	}
}

