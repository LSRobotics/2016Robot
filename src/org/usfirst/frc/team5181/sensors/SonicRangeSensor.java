package org.usfirst.frc.team5181.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class SonicRangeSensor {

	static int ULTRASONIC_ECHO_PULSE_OUTPUT = 5;
	static int ULTRASONIC_TRIGGER_PULSE_INPUT = 4;
	Ultrasonic ultra = new Ultrasonic(ULTRASONIC_ECHO_PULSE_OUTPUT,
			ULTRASONIC_TRIGGER_PULSE_INPUT);

	public SonicRangeSensor() {
		restartSensor();
		ultra.setAutomaticMode(true);
	}

	public double getRange(int i) throws Exception {
		// TODO add modes
		if (i == 0) {
			return ultra.getRangeInches();
		} else {
			return ultra.getRangeMM();
			// Connor is my big hero
		}
	}
	
	public double getRangeInches() throws Exception {
		return ultra.getRangeInches();
	}
	
	public double getRangeMm() throws Exception {
		return ultra.getRangeMM();
	}
	
	public Ultrasonic.Unit feederPID() throws Exception {
		return ultra.getDistanceUnits();
	}
	
	public void updateValues() {
		ultra.updateTable();
	}
	
	public void restartSensor() {
		if(ultra.isEnabled()) {
			ultra.setEnabled(false);
			ultra.setEnabled(true);
		} else {
			ultra.setEnabled(true);
		}
	}
	
}
