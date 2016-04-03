package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.RevX;

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
				while(Math.abs(((RevX)sensor).getAngle() - setpoint) > threshold) {
						if(currV)
						do {
							
						} while(((RevX)sensor).getAngle())
				}
			}
			catch(Exception e) {
				//
			}

		}
	}
}
