package org.usfirst.frc.team5181.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class SonicRangeSensor {

	static int ULTRASONIC_ECHO_PULSE_OUTPUT;
	static int ULTRASONIC_TRIGGER_PULSE_INPUT;
	Ultrasonic ultra = new Ultrasonic(ULTRASONIC_ECHO_PULSE_OUTPUT , ULTRASONIC_TRIGGER_PULSE_INPUT);

	public SonicRangeSensor (int pulseOutput, int pulseInput) {
		ULTRASONIC_ECHO_PULSE_OUTPUT = pulseOutput;
		ULTRASONIC_TRIGGER_PULSE_INPUT = pulseInput;
		ultra.setAutomaticMode(true);
	}

	public double getRange (int i) {
		//TODO add modes
		if(i == 0)
			return ultra.getRangeInches();
		else
			return ultra.getRangeMM();
	}
	
}
