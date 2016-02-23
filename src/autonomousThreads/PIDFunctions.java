package autonomousThreads;

import org.usfirst.frc.team5181.robot.DriveTrain;
import org.usfirst.frc.team5181.robot.Robot;

import sensors.RevX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class PIDFunctions implements PIDOutput {
	Robot robot;
	DriveTrain drive;
	RevX revX;
	
	PIDController pidiR, pidiD;
	
	public static double kPr = 0.21, kPd = 0.03; 
	public static double kIr = 0.0175, kId = 0.00; 
	public static double kDr = 0.20, kDd = 0.00; 
	public static double kFr = 0.00, kFd = 0.00; 
	
	static final double toleranceRotation = 0.5;
	static final double toleranceDistance = .5;
	
	double rotationRate = 0;
	
	public PIDFunctions(Robot r, DriveTrain drive) {
		robot = r;
		this.drive = drive;
		revX = r.getRevX();
		
		pidiR = new PIDController(kPr, kIr, kDr, kFr, revX, this);
		
		pidiR.setInputRange(-180.0, 180.0);
		pidiR.setOutputRange(-1, 1);
		pidiR.setAbsoluteTolerance(toleranceRotation);
		pidiR.setContinuous(true);
		
		pidiD = new PIDController(kPr, kIr, kDr, kFr, revX, this);
		pidiD.setInputRange(0, 3);
		pidiD.setOutputRange(-1, 1);
		pidiD.setAbsoluteTolerance(toleranceDistance);
		pidiD.setContinuous(true);
		
	}
	public void turnToAngle(double angle) {
		pidiR.setSetpoint(angle);
		pidiR.enable();
		
		drive.arcadeDrive(rotationRate, 0);
	}
	public void move(double distance) {
		pidiD.setSetpoint(distance);
		pidiD.enable();
		
		
	}
	
	public void pidWrite(double output) {
		rotationRate = output;
	}
}
