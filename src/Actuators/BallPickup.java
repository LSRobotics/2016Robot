package actuators;

import org.usfirst.frc.team5181.robot.Statics;

import edu.wpi.first.wpilibj.Talon;

public class BallPickup {
	Talon left, right;
	
	public BallPickup() {
		left = new Talon(Statics.LeftBall);
		right = new Talon(Statics.RightBall);
	}
	
	public void pickupBall(double axisControl) {
		left.set(axisControl);
		right.set(-axisControl);
	}
	
	public void throwBall(double axisControl) {
		left.set(axisControl);
		right.set(-axisControl);
	}
}
