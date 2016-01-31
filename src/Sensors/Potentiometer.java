package Sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Potentiometer {
	AnalogPotentiometer potent;

	public Potentiometer() {
		potent = new AnalogPotentiometer(0, 1, 0);
	}
	public double getPosition() {
		return potent.get();                                                                 
	}
}
