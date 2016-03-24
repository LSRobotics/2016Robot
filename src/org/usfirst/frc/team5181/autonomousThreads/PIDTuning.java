package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.autonomousThreads.PIDFunctions.Controllers;
import org.usfirst.frc.team5181.robot.Gamepad;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.sensors.RevX;

import edu.wpi.first.wpilibj.DriverStation;

public class PIDTuning {

	/**
	 * A button signifies convergence B signifies oscillation X signifies
	 * divergence Y signifies reduce currDelta by a factor of 10 and start gain
	 * at last stable value
	 * 
	 * @param controller
	 *            either rotation or displacement
	 */
	private boolean xPressed = false, aPressed = false, bPressed = false, yPressed = false;
	private int xGain = 0; // 0 == kP, 1 == kD, 2 == kI
	private double stableP = 0.00, stableD = 0.00, stableI = 0;
	private double currP = 0.00, currD = 0.000, currI = 0;
	private double currDelta = 0.05;
	private boolean turnTo90 = true;

	private PIDFunctions pidi;
	private RevX revX;
	public PIDTuning(Robot robot, Object source, Controllers pidType) {
		pidi = new PIDFunctions(robot, pidType, source);
		revX = robot.getRevX();
	}
	
	public void autoTunePID(Controllers controller) {
		pidi.upadtePID(controller, currP, currI, currD, 0);
		pidi.setPID(controller);

		DriverStation.reportError("P: " + currP + "  ,  D: " + currD
				+ "  ,  I: " + currI + "  ,  Delta: " + currDelta + "  ,  R:"
				+ revX.getRotation() + "\n", false);

		if (turnTo90) {
			pidi.turnToAngle(90);
		} else {
			pidi.turnToAngle(0);
		}

		// Converges and gain needs to be increased if P, if D then P needs to
		// be increased again
		if (Gamepad.A_Button_State && !aPressed) {
			aPressed = true;
			turnTo90 = !turnTo90;
			switch (xGain) {
			case 0: // P
				stableP = currP;
				currP += currDelta;
				break;
			case 1: // D
				stableD = currD;
				xGain = 0; // D is stable increase P
				currDelta = 0.05;
				break;
			case 2:// I
				stableI = currI;
				DriverStation.reportError("P: " + stableP + "  ,  D: "
						+ stableD + "  ,  I: " + stableI + "  ,  " + "Rot:"
						+ revX.getRotation() + "\n", false);
				break;
			}
		} else if (!Gamepad.A_Button_State) {
			aPressed = false;
		}

		// If P-gain causes oscillation OR D-gain causes spasms
		if (Gamepad.B_Button_State && !bPressed) {
			bPressed = true;
			turnTo90 = !turnTo90;
			switch (xGain) {
			case 0: // P

				xGain = 1;
				break;
			case 1: // D
				currD += currDelta;
				break;
			case 2:// I
				stableI = currI;
				currI += currDelta;
				break;

			}
		} else if (!Gamepad.B_Button_State) {
			bPressed = false;
		}

		// If currDelta is too high
		if (Gamepad.Y_Button_State && !yPressed) {
			yPressed = true;
			turnTo90 = !turnTo90;

			switch (xGain) {
			case 0: // P
				currP = stableP;
				break;
			case 1: // D
				currD = stableD;
				break;
			case 2:// I
				currI = stableI;
				break;
			}
			currDelta /= 5;
		} else if (!Gamepad.Y_Button_State) {
			yPressed = false;
		}
		
		// If for when DGAin dosent stablize
		if (Gamepad.X_Button_State && !xPressed) {
			xPressed = true;
			turnTo90 = !turnTo90;

			switch (xGain) {
			case 0: // P
				//
				break;
			case 1: // D
				currD = stableD;
				xGain = 2;
				break;
			case 2:// I
					//
				break;
			}
		} else if (!Gamepad.X_Button_State) {
			xPressed = false;
		}
	}
}
