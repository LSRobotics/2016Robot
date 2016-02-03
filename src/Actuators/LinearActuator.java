package Actuators;

import Sensors.Potentiometer;
import edu.wpi.first.wpilibj.Victor;

public class LinearActuator {
	public static final int Extend = -1;
	public static final int Contract = 1;
	
	Victor linAct;
	Potentiometer potent;
	
	private double potentMax;
	private double potentMin;
	
	/**
	 * 
	 * @param actuatorPort Actuator Port
	 * @param potentiometerPort potentiometer Port
	 * @param potentiometerMax potentiometer Max
	 * @param potentiometerMin potentiometer Min
	 */
	public LinearActuator(int actuatorPort, int potentiometerPort, double potentiometerMax, double potentiometerMin) {
		linAct = new Victor(actuatorPort);
		potent = new Potentiometer(potentiometerPort, 24, 0); 
		
		this.potentMax = potentiometerMax;
		this.potentMin = potentiometerMin;
	}
	
	/**
	 * Moves the linear actuator
	 * @param magnitude how quickly to move it
	 * @param direction which direction: use LinearActuator.Extend or LinearActuator.Contract
	 */
	public void move(double magnitude, int direction) {
		if(direction == Contract && potent.getPosition() >= potentMin) {
			linAct.set(direction * magnitude); 
		}
		else if(direction == Extend && potent.getPosition() <= potentMax) {
			linAct.set(direction * magnitude);
		}
	}
}
