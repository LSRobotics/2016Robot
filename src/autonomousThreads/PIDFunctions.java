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
	
	static final double kPr = 0.07, kPd = 0.03; 
	static final double kIr = 0.00, kId = 0.00; 
	static final double kDr = 0.00, kDd = 0.00; 
	static final double kFr = 0.00, kFd = 0.00; 
	
	static final double toleranceRotation = 2;
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
		pidiR.enable();
		
	}
	public void turnToAngle(double angle) {
		pidiR.setSetpoint(angle);
		pidiR.enable();
		
		while(!pidiR.onTarget()) {
			DriverStation.reportError(revX.getAngle() + "\n", false);
			drive.arcadeDrive(rotationRate, 0);
		}
	}
	public void move(double setpoint) {
		//TODO
	}
	
	public void pidWrite(double output) {
		rotationRate = output;
	}
}
