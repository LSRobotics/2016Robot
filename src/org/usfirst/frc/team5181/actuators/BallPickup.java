package org.usfirst.frc.team5181.actuators;

import org.usfirst.frc.team5181.robot.Filters;
import org.usfirst.frc.team5181.robot.Statics;
import org.usfirst.frc.team5181.sensors.LimitSwitch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;

public class BallPickup {
	Talon left, right;
	LimitSwitch ballInTrap;
	
	public BallPickup() {
		left = new Talon(Statics.LeftBall);
		right = new Talon(Statics.RightBall); 
		
		ballInTrap = new LimitSwitch(9);
	}
	
	public void setBallIntake(double leftMag, double rightMag) {
		if(!ballInTrap.get()) {
			if(rightMag > .1) {
				left.set(-1);
				right.set(1);
				return;
			}
			else if(!(rightMag > .1)) {
				left.set(0);
				right.set(0);
				return;
			}
		}
		if(!(rightMag > .1)) {
			if(leftMag > 0.2) {
				left.set(0.2);
				right.set(-0.2);
			}
			if(Math.abs(leftMag) < 0.1) {
				left.set(0);
				right.set(0);
			}
		}
	}
}

