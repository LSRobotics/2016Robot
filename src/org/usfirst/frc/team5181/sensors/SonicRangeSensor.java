package org.usfirst.frc.team5181.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class SonicRangeSensor {

	static int ULTRASONIC_ECHO_PULSE_OUTPUT = 0;
	static int ULTRASONIC_TRIGGER_PULSE_INPUT = 0;
	Ultrasonic ultra = new Ultrasonic(ULTRASONIC_ECHO_PULSE_OUTPUT , ULTRASONIC_TRIGGER_PULSE_INPUT);

	public SonicRangeSensor () {
		ultra.setAutomaticMode(true);
	}

	public double getRange (int i) {
		if(i == 1)
			return ultra.getRangeInches();
		else
			return ultra.getRangeMM();
	}
	
}
