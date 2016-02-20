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
	boolean canLaunch;
	
	public BallPickup() {
		left = new Talon(Statics.LeftBall);
		right = new Talon(Statics.RightBall); 
		
		ballInTrap = new LimitSwitch(9);

		canLaunch = false;
	}
	
	public void setBallIntake(double leftMag, double rightMag) {
		//Launch
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
		
		//Take in
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
	

	int state = 0; //0 as stop, 1 as take ball, 2 as shoot ball
	
	public void setBallIntake(double Mag) {
		
		if (Mag <= 0.2)state = 0;//if not triggered
		else if (state == 0){
			if (ballInTrap.get()){
				
				//if ball in trap
				state = 2;
			}
			else state = 1; //if ball not in trap
		}
		
		if (state == 0){//not triggered
			left.set(0);
			right.set(0);
		}
		if (state == 1){//take ball
			if (ballInTrap.get()){
				left.set(0.2);
				right.set(-0.2);
			}
			else {}
		}
	}

	//TODO Fix
	
}
