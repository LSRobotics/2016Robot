package org.usfirst.frc.team5181.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class IRSensor {
	/**
	 * @notice Might need tuning, see http://wpilib.screenstepslive.com/s/4485/m/13809/l/241876-analog-inputs
	 */
	AnalogInput ai = new AnalogInput(0);
	
	public double getDistance () {
		/**
		 * @params Returns the distence from Avg. voltage in double
		 * Will be inaccurate in short distances
		 * Some magic happens...
		 * http://home.roboticlab.eu/en/examples/sensor/ir_distance
		 */
		return (5461 / (ai.getAverageVoltage() - 17) - 2);
	}
	
	
}
