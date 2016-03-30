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
	Relay relay;
	
	public BallPickup() {
		left = new Talon(Statics.LeftBall);
		right = new Talon(Statics.RightBall); 
		relay = new Relay(Statics.BallIntakePort);
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
	
	public void shootFree(double leftMag, double rightMag) {
		left.set(-leftMag);
		right.set(rightMag);
	}
	
	public void setRelay(double mag) {
		Value val;
		if (mag > 0) {
			val = Value.kForward;
		}
		else if (mag < 0) {
			val = Value.kReverse;
		}
		else {
			val = Value.kOff;
		}
		relay.set(val);
	}
}

