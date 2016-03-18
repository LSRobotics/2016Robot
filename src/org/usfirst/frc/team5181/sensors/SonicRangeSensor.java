package org.usfirst.frc.team5181.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class SonicRangeSensor {

	static int ULTRASONIC_ECHO_PULSE_OUTPUT = 4;
	static int ULTRASONIC_TRIGGER_PULSE_INPUT = 3;
	Ultrasonic ultra = new Ultrasonic(ULTRASONIC_ECHO_PULSE_OUTPUT,
			ULTRASONIC_TRIGGER_PULSE_INPUT);

	public SonicRangeSensor() throws Exception {
		//restartSensor();
		ultra.setAutomaticMode(true);
	}

	/**
	 * Get range in Inches
	 * @return double
	 * @return 0.0314159 -> error
	 */
	public double getRangeInches() {
		if (ultra.isRangeValid()) {
			return ultra.getRangeInches();
		} else {
			return 0.0314159;
		}
	}
	
	/**
	 * Get range in Foot
	 * @return double
	 * @return 0.0314159 -> error
	 */
	public double getRangeFt() {
		if (ultra.isRangeValid()) {
			return getRangeInches() * 0.0833333;
		} else {
			return 0.0314159;
		}
	}

	/**
	 * Get range in Yards
	 * @return double
	 * @return 0.0314159 -> error
	 */
	public double getRangeYd() {
		if (ultra.isRangeValid()) {
			return getRangeInches() * 0.0277778;
		} else {
			return 0.0314159;
		}
	}


	/**
	 * Get range in Milimeter
	 * @return double
	 * @return 0.0314159 -> error
	 */
	public double getRangeMm() {
		if (ultra.isRangeValid()) {
			return ultra.getRangeMM();
		} else {
			return 0.0314159;
		}
	}
	

	/**
	 * Get range in Centimeter
	 * @return double
	 * @return 0.0314159 -> error
	 */
	public double getRangeCm() {
		if (ultra.isRangeValid()) {
			return getRangeMm() * 0.1;
		} else {
			return 0.0314159;
		}
	}
	
	/**
	 * Get range in Dm
	 * @return double
	 * @return 0.0314159 -> error
	 */
	public double getRangeDm() {
		if (ultra.isRangeValid()) {
			return getRangeMm() * 0.01;
		} else {
			return 0.0314159;
		}
	}
	
	/**
	 * Get range in Meters
	 * @return double
	 * @return 0.0314159 -> error
	 */
	public double getRangeMeters() {
		if (ultra.isRangeValid()) {
			return getRangeMm() * 0.001;
		} else {
			return 0.0314159;
		}
	}

	public Ultrasonic.Unit feederPID() {
		return ultra.getDistanceUnits();
	}

	public void updateValues() {
		ultra.updateTable();
	}

	public void restartSensor() {
		if (ultra.isEnabled()) {
			ultra.setEnabled(false);
			ultra.setEnabled(true);
		} else {
			ultra.setEnabled(true);
		}
	}

}
