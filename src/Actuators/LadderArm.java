package Actuators;

import Sensors.LimitSwitch;

public class LadderArm {
	LinearActuator linAct;
	LimitSwitch Bounds15, BoundsMax, BoundsMin;
	
	public LadderArm(int linearActuatorPort, double potentiometerPort int limitMaxPort, int limitMinPort, int limit15Port) {
		linAct = new LinearActuator(linearActuatorPort, potentiometerPort, 0, 25);
		Bound15 = new LimitSwitch(limit15Port);
		BoundsMin = new LimitSwitch(limitMinPort);
	}
}
