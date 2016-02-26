package autonomousThreads;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.robot.SimpleClient;

import autonomousThreads.PIDSources.DisplacementSource;
import autonomousThreads.PIDSources.GyroSource;
import sensors.RevX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDFunctions implements PIDOutput {
	Robot robot;
	DriveTrain drive;
	
	public PIDController pidiR, pidiD;
	public GyroSource gyroPID;
	public DisplacementSource displacePID;
	
	static double kPr = 0.15, kPd = 0.06; 
	static double kIr = 0.00004, kId = 0.00; 
	static double kDr = 0.1, kDd = 0.00; 
	static double kFr = 0.00, kFd = 0.00; 
	
	static final double toleranceRotation = 0.5;
	static final double toleranceDistance = .5;
	
	double pidValue = 0;
	
	public PIDFunctions(Robot r, DriveTrain drive) {
		robot = r;
		this.drive = drive;
		
		gyroPID = new GyroSource(r.getRevX());
		displacePID = new DisplacementSource(r.getRevX());
		
		pidiR = new PIDController(kPr, kIr, kDr, kFr, gyroPID, this);
		
		pidiR.setInputRange(-180.0, 180.0);
		pidiR.setOutputRange(-1, 1);
		pidiR.setAbsoluteTolerance(toleranceRotation);
		pidiR.setContinuous(true);
		
		pidiD = new PIDController(kPr, kIr, kDr, kFr, displacePID, this);
		pidiD.setInputRange(-3, 3);
		pidiD.setOutputRange(-1, 1);
		pidiD.setAbsoluteTolerance(toleranceDistance);
		pidiD.setContinuous(true);
		
	}
	public void turnToAngle(double angle) {
		pidiR.setSetpoint(angle);
		pidiR.enable();
		drive.arcadeDrive(pidValue, 0);
	}
	public void moveTo(double distance) {
		
		pidiD.setSetpoint(distance);
		pidiD.enable();
		
		drive.arcadeDrive(pidValue, pidValue);
	}
	
	public void pidWrite(double output) {
		pidValue = output;
	}
	public enum Controllers {
		ROTATION, DISPLACEMENT
	}
	public static void upadtePID(Controllers controller, double kP, double kI, double kD, double kF) {
		if(controller == Controllers.ROTATION) {
			kPr = kP;
			kIr = kI;
			kDr = kD;
			kFr = kF;
		}
		else if(controller == Controllers.DISPLACEMENT) {
			kPd = kP;
			kId = kI;
			kDd = kD;
			kFd = kF;
		}
	}
	public void setPID(Controllers controller) {
		if(controller == Controllers.ROTATION) {
			if(pidiR != null) {
				pidiR.setPID(kPr, kIr, kDr, kFr);
			}
		}
		else if(controller == Controllers.DISPLACEMENT) {
			if(pidiD != null) {
				pidiD.setPID(kPd, kId, kDd, kFd);
			}
		}
	}
	public static double[] getPIDTunings(Controllers controller) {
		if(controller == Controllers.ROTATION) {
			return new double[]{kPr, kIr, kDr, kFr};
		}
		else if(controller == Controllers.DISPLACEMENT) {
			return new double[]{kPd, kId, kDd, kFd};
		}
		else {
			return null;
		}
	}
	
	public boolean onTarget(Controllers c) {
		boolean onTar = false;
		switch (c) {
			case ROTATION:
				onTar = pidiR.onTarget();
				break;
			case DISPLACEMENT:
				onTar = pidiD.onTarget();
				break;
		}
		return onTar;
	}
}
