package org.usfirst.frc.team5181.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

public class DriveTrain {
	
	private double speedLimit;
	
	Victor leftFront;
	Victor leftBack;
	Victor rightFront;
	Victor rightBack;
	RobotDrive drive;
	
	Gamepad gp;
	public DriveTrain(Gamepad gp, double speedLimit) {
		leftFront = new Victor(Statics.LEFTPortFront);
		leftBack = new Victor(Statics.LEFTPortBack);
		rightFront = new Victor(Statics.RIGHTPortFront);
		rightBack = new Victor(Statics.RIGHTPortBack);
		
		drive = new RobotDrive(leftFront, leftBack, rightFront, rightBack);
		
		this.gp = gp;
		this.speedLimit = speedLimit;
	}
	
	/**
	 * Single joystick to control robot drive
	 * @param controlStickX magnitude of turning axis
	 * @param controlStickY magnitude of driving axis
	 */
	public void ArcadeDrive(double controlStickX, double controlStickY) {
		double xRotation = controlStickX;
		double yDrive = controlStickY;
		
		xRotation = Filters.powerCurving(controlStickX, 0.75); //filter rotational values
		
		if(yDrive >= speedLimit) {
			yDrive = speedLimit;
		}
		if(xRotation >= speedLimit) {
			xRotation = speedLimit;
		}
		
		drive.arcadeDrive(-gp.getRawAxis(gp.RIGHT_Stick_Y), -(xRotation));
	}
	
	public void tankDrive(double controlStickLeft, double controlStickRight) {
		drive.tankDrive(controlStickLeft, controlStickRight);
	}
	
	public void setSpeedLimit(double newMaxSpeed) {
		speedLimit = newMaxSpeed;
	}
}
