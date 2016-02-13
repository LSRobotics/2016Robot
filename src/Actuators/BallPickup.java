package Actuators;

import org.usfirst.frc.team5181.robot.Statics;

import edu.wpi.first.wpilibj.Talon;

public class BallPickup {
	Talon left, right;
	
	public BallPickup() {
		left = new Talon(Statics.LeftBall);
		right = new Talon(Statics.RightBall);
	}
	
	public void setBallIntake(double magnitude) {
		left.set(magnitude);
		right.set(-magnitude);
	}
}
