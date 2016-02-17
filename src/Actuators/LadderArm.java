package actuators;

import edu.wpi.first.wpilibj.Talon;
import sensors.LimitSwitch;

public class LadderArm {
	Talon armController;
	LimitSwitch Bounds15, BoundsMax, BoundsMin;
	
	public LadderArm(int armPort, int limitMaxPort, int limitMinPort, int limit15Port) {
		Bounds15 = new LimitSwitch(limit15Port);
		BoundsMin = new LimitSwitch(limitMinPort);
		BoundsMax = new LimitSwitch(limitMaxPort);
		
		armController = new Talon(armPort); 
	}
	
	public void moveFree(double value) {
		armController.set(value);
	}
	
	
	public enum Positions {
		MIN, BOUNDS15, MAX;
	}
	
	/**
	 * Moves the arm to specified position
	 * @param toWhere reference enum Positions
	 */
	public void moveTo(Positions toWhere, double magnitude) {
		if(toWhere == Positions.MIN) {
			armController.set(- Math.abs(magnitude));
		}
		else if(toWhere == Positions.BOUNDS15) {
			armController.set(magnitude);
		}
		else if(toWhere == Positions.MAX) {
			armController.set(Math.abs(magnitude));
		}
		else {
			armController.set(0);
		}
	}
}
