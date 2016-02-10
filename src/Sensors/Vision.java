package sensors;

import java.io.IOException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {
	private final static String GRIP_CMD =
		        "/usr/local/frc/JRE/bin/java -jar /home/lvuser/grip.jar /home/lvuser/project.grip";
	private final NetworkTable grip = NetworkTable.getTable("grip");
	
	
	public Vision() {
		try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	public double getBallCenterX() {
		double maxCenterX = 0;
		for (double centerX : grip.getNumberArray("ballContourReport/centerX", new double[0])) {
            if (centerX > maxCenterX) {
            	maxCenterX = centerX;
            }
        }
		
        return maxCenterX;
	} 
	
	public double getBallCenterY() {
		double maxCenterY = 0;
		for (double centerY : grip.getNumberArray("ballContourReport/centerY", new double[0])) {
            if (centerY > maxCenterY) {
            	maxCenterY = centerY;
            }
		}
		return maxCenterY;
	}
}
