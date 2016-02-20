package actuators;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;
import sensors.LimitSwitch;

public class LadderArm {
	Talon armRotationController, armExtensionController;
	LimitSwitch Bounds15, BoundsMax, BoundsMin;
	
	/**
	 * Controlled movement 
	 * @param rotationPort
	 * @param extensionPort
	 * @param limitMaxPort
	 * @param limitMinPort
	 * @param limit15Port
	 */
	public LadderArm(int rotationPort, int extensionPort, int limitMaxPort, int limitMinPort, int limit15Port) {
		Bounds15 = new LimitSwitch(limit15Port);
		BoundsMin = new LimitSwitch(limitMinPort);
		BoundsMax = new LimitSwitch(limitMaxPort);
		
		armRotationController = new Talon(rotationPort); 
		armExtensionController = new Talon(extensionPort);
	}
	
	/**
	 * Free movement
	 * @param rotationPoer
	 * @param extensionPort
	 */
	public LadderArm(int rotationPort, int extensionPort) {
		armRotationController = new Talon(rotationPort); 
		armExtensionController = new Talon(extensionPort);
	}
	
	public void rotateFree(double value, double speedLimit) {
		if(value <= speedLimit) {
			armRotationController.set(speedLimit);
		}
	}
	
	
	public enum rotationalPositions {
		MIN, BOUNDS15, MAX;
	}
	public enum extensionPositions {
		EXTEND, CONTRACT;
	}
	
	/**
	 * Moves the arm to specified position
	 * @param toWhere reference enum Positions
	 */
	public void rotateTo(rotationalPositions toWhere, double magnitude) {
		if(toWhere == rotationalPositions.MIN && Math.abs(magnitude) > 0.1) {
			armRotationController.set(-Math.abs(magnitude));
		}
		else if(toWhere == rotationalPositions.BOUNDS15 && Math.abs(magnitude) > 0.1) {
			armRotationController.set(magnitude);
		}
		else if(toWhere == rotationalPositions.MAX && Math.abs(magnitude) > 0.1) {
			armRotationController.set(Math.abs(magnitude));
		}
		else {
			armRotationController.set(0);
		}
	}
	
	public void extend(extensionPositions direction, double magnitude) {
		if(direction == extensionPositions.EXTEND && Math.abs(magnitude) > 0.1) {
			armExtensionController.set(Math.abs(magnitude));
		}
		if(direction == extensionPositions.CONTRACT && Math.abs(magnitude) > 0.1) {
			armExtensionController.set(-Math.abs(magnitude));
		}
	}
	
	public void extendFree(double mag) {
		armExtensionController.set(mag);
	}
}
