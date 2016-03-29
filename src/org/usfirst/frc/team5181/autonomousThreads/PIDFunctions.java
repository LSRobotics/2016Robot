package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.autonomousThreads.PIDSources.DisplacementSource;
import org.usfirst.frc.team5181.autonomousThreads.PIDSources.GyroSource;
import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;
import org.usfirst.frc.team5181.robot.SimpleClient;
import org.usfirst.frc.team5181.sensors.RevX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import org.usfirst.frc.team5181.sensors.RangeSensors.SonicRangeSensor;;

public class PIDFunctions implements PIDOutput {
	DriveTrain drive;
	
	public PIDController pidiR, pidiD;
	public GyroSource gyroPID;
	public DisplacementSource displacePID;
	
	static double kPr = 0.16, kPd = 0.1; //kPr = 0.12, kPd = 0.009; 
	static double kIr = 0.001,  kId = 0.00; //kIr = 0.0, kId = 0.00; 
	static double kDr = 0.19, kDd = 0.00; //kDr = 0.06, kDd = 0.00; 
	static double kFr = 0.0,  kFd = 0.00; //kFr = 0.00, kFd = 0.00; 
	
	static final double toleranceRotation = 1;
	static final double toleranceDistance = 90;
	
	double pidValue = 0;
	
	public PIDFunctions(Robot r, Controllers pidType, Object source) {
		this.drive = r.drive;
		
		switch(pidType) {
			case ROTATION:
				gyroPID = new GyroSource((RevX) source);
				
				pidiR = new PIDController(kPr, kIr, kDr, kFr, gyroPID, this);
				
				pidiR.setInputRange(-180.0, 180.0);
				pidiR.setOutputRange(-1, 1);
				pidiR.setAbsoluteTolerance(toleranceRotation);
				pidiR.setContinuous(true);
				
				break;
			case DISPLACEMENT:
				displacePID = new DisplacementSource((SonicRangeSensor) source); 
				
				pidiD = new PIDController(kPd, kId, kDd, kFd, displacePID, this);
				
				pidiD.setInputRange(0, 5000); //mm
				pidiD.setOutputRange(-0.5, 0.5);
				pidiD.setAbsoluteTolerance(toleranceDistance);
				pidiD.setContinuous(true);
				
				break;
		}
	}
	
	public void turnToAngle(double angle) throws NullPointerException {
		if(pidiR != null) {
			pidiR.setSetpoint(angle);
			pidiR.enable();
			drive.arcadeDrive(pidValue, 0);
		}
		else {
			throw new NullPointerException();
		}
	}
	
	public void moveTo(double distance) throws NullPointerException{
		if(pidiD != null) {
			pidiD.setSetpoint(distance);
			pidiD.enable();
			drive.arcadeDrive(0, pidValue);
		}
		else {
			throw new NullPointerException();
		}
	}
	
	public void pidWrite(double output) {
		pidValue = output;
	}
	
	public enum Controllers {
		ROTATION, DISPLACEMENT
	}
	
	public void setPIDSource(Object source, Controllers pidType) {
		if(pidType == Controllers.ROTATION) {
			pidiR = new PIDController(kPr, kIr, kDr, new GyroSource((RevX) source), this);
		}
		if(pidType == Controllers.DISPLACEMENT) {
			pidiD = new PIDController(kPd, kId, kDd, new DisplacementSource((SonicRangeSensor) source), this);
		}
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
