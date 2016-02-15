package actuators;

import org.usfirst.frc.team5181.robot.Filters;
import org.usfirst.frc.team5181.robot.Statics;

import sensors.LimitSwitch;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;

public class BallPickup {
	Talon left, right;
	static double speedLimit = .3;
	LimitSwitch ballInTrap;
	
	public BallPickup() {
		left = new Talon(Statics.LeftBall);
		right = new Talon(Statics.RightBall); 
		
		ballInTrap = new LimitSwitch(9);
	}
	
	public void setBallIntake(double magnitude) {
		if(!ballInTrap.get()) {
			if(magnitude < -.1) {
				left.set(-1);
				right.set(1);
				return;
			}
			else {
				left.set(0.2);
				right.set(-.2);
				return;
			}
		}
		
		if(magnitude > 0.2) {
			left.set(0.2);
			right.set(-0.2);
		}
		if(Math.abs(magnitude) < 0.1) {
			left.set(0);
			right.set(0);
		}
	}
}
