package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.RangeSensors;
import org.usfirst.frc.team5181.sensors.RevX;

import edu.wpi.first.wpilibj.DriverStation;

public class PseudoPID {
	Object sensor;
	Source source;
	DriveTrain drive;
	public enum Source {
		Rotation, Displacement;
	}
	public PseudoPID(Source source, Object sensor, Robot robot) {
		this.source = source;
		this.sensor = sensor;
		
		drive = robot.drive;
	}
	
	public void goToSetpoint(double setpoint, double threshold) {
		if(source == Source.Rotation) {
			try {
				double currValue;
				int deductionFactor = 0;
				double reductionPerFactor = 0.02;
				while(Math.abs((currValue = ((RevX)sensor).getAngle()) - setpoint) > threshold) {
					if(currValue - setpoint < 0) {
						do {
							DriverStation.reportError("here\n", false);
							//go counterclockwise
							double reduction = deductionFactor * reductionPerFactor;
							if(reduction < 0) {
								return;
							}
							drive.arcadeDrive(-0.3 + reduction, 0.0);
						} while((currValue = ((RevX)sensor).getAngle()) < setpoint);
					}
					else {
						do {
							//go counterclockwise
							double reduction = deductionFactor * reductionPerFactor;
							if(reduction < 0) {
								return;
							}
							
							drive.arcadeDrive(.3 - reduction, 0.0);
						} while((currValue = ((RevX)sensor).getAngle()) > setpoint);
					}
					deductionFactor++;
				}
			}
			catch(Exception e) {
				DriverStation.reportError("Caught exception\n", true);
			}
		}
//		else if(source == Source.Displacement) {
//			try {
//				double currValue;
//				int deductionFactor = 0;
//				double reductionPerFactor = 0.02;
//				while(Math.abs((currValue = ((RangeSensors)sensor). setpoint) > threshold) {
//					do {
//						//go counterclockwise
//						double reduction = deductionFactor * reductionPerFactor;
//						if(reduction < 0) {
//							return;
//						}
//						if(currValue - setpoint < 0) {
//							drive.tankDrive(-0.3 + reduction, 0.3 - reduction); 
//						}
//						else {
//							drive.tankDrive(0.3 - reduction, -0.3 + reduction);
//						}
//					} while((currValue = ((RevX)sensor).getAngle()) < setpoint);
//					
//					deductionFactor++;
//				}
//			}
//			catch(Exception e) {
//				//
//			}
//		}
	}
}
