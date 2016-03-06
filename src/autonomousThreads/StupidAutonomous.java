package autonomousThreads;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class StupidAutonomous extends Thread {

	boolean tStart = false;
	Timer t = new Timer();
	//DriveTrain driveTrain;
    /**
     * This function is called periodically during autonomous
     */
    private void autonomousPeriodic() {
    	if (tStart == false) {
    		t.start();
    		tStart = true;
    	}
    	if (t.get() < 2.0) 
    		{
    		//driveTrain.tankDrive(.1, .1);
    		}
    	else {
    		t.stop();
    		t.reset();
    		//driveTrain.tankDrive(0, 0);
    		tStart = false;
    	}
    }
}
