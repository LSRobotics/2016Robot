package org.usfirst.frc.team5181.actuators;

import edu.wpi.first.wpilibj.Talon;

public class Boris {
	
	private Talon boris;
	
	public Boris(int portNum) {
		boris = new Talon(portNum);
	}
	
	public void Set(double speed) {
		boris.set(speed);
	}
}
