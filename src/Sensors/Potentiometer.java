package sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Potentiometer {
	AnalogPotentiometer potent;
	
	public Potentiometer(int port, double range, double offset) {
		potent = new AnalogPotentiometer(port, range, offset);
	}
	public double getPosition() {
		return potent.get();                                                                 
	}
}
