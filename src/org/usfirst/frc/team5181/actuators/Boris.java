package org.usfirst.frc.team5181.actuators;

import edu.wpi.first.wpilibj.Talon;

public class Boris {
	
	private Talon boris;
	
	public Boris(int portNum) {
		boris = new Talon(portNum);
	}
	
	public void set(double speed) {
	/**	if(speed > .1) {
			boris.set(1);
		}
		else if(speed < -.1) {
			boris.set(-1);
		}
		else {
			boris.set(0);
		}**/
		boris.set(speed);
	}
}
