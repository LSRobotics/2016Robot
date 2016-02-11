package actuators;

import sensors.LimitSwitch;

public class LadderArm {
	LinearActuator linAct;
	LimitSwitch Bounds15, BoundsMax, BoundsMin;
	
	public LadderArm(int linearActuatorPort, int potentiometerPort, int limitMaxPort, int limitMinPort, int limit15Port) {
		Bounds15 = new LimitSwitch(limit15Port);
		BoundsMin = new LimitSwitch(limitMinPort);
		BoundsMax = new LimitSwitch(limitMaxPort);
	}
	
	public void moveFree(double value) {
		linAct.move(value);
	}
	
	/**
	 * 
	 * @param toWhere 1 = min, 2 = 15 bound, 3 = max
	 */
	public void moveTo(int toWhere, double magnitude) {
		if(toWhere == 1 && magnitude < 0 && !BoundsMin.get()) {
			linAct.move(magnitude);
		}
		if(toWhere == 3 && magnitude > 0 && !BoundsMax.get()) {
			linAct.move(toWhere);
		}
		if(toWhere == 2 && !Bounds15.get()) {
			
		}
	}
}
