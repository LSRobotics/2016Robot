package sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch {
	private int port;
	
	DigitalInput limitSwitch;
	public LimitSwitch(int port) {
		this.port = port;
		
		limitSwitch = new DigitalInput(port);
	}
	
	public boolean get() {
		return limitSwitch.get();
	}
}

