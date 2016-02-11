package actuators;

import sensors.Potentiometer;
import edu.wpi.first.wpilibj.Victor;

public class LinearActuator {
	Victor linAct;
	
	/**
	 * 
	 * @param actuatorPort Actuator Port
	 */
	public LinearActuator(int actuatorPort) {
		linAct = new Victor(actuatorPort);
	}
	
	/**
	 * Moves the linear actuator
	 * @param magnitude how quickly to move it
	 * @param direction which direction: use LinearActuator.Extend or LinearActuator.Contract
	 */
	public void move(double magnitude) {
		linAct.set(magnitude); 
	}
}
