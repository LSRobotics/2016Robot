package actuators;

import org.usfirst.frc.team5181.robot.Filters;
import org.usfirst.frc.team5181.robot.Statics;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;

public class BallPickup {
	Talon left, right;
	static double speedLimit = .3;
	
	public BallPickup() {
		left = new Talon(Statics.LeftBall);
		right = new Talon(Statics.RightBall);
	}
	
	public void setBallIntake(double magnitude) {
		
		if (Math.abs(magnitude) < .05) {
			magnitude = 0;
		}
		else if (Math.abs(magnitude) > speedLimit) {
			magnitude = (Math.abs(magnitude)/magnitude) * speedLimit;
		}
		
		left.set(Filters.powerCurving(magnitude, (magnitude < 0) ? 0.85 : 2)); //Slower for intake, faster for output
		right.set(-Filters.powerCurving(magnitude, (magnitude < 0) ? 0.85 : 2));
	}
}
