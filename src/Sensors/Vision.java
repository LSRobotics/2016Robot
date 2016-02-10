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
	
	
	public double[] getBallCoord() {
		
		double maxArea = 0;
		int areaIndex = 0, maxAreaIndex = 0;		
		for (double area : grip.getNumberArray("ballContourReport/area", new double[0])) {
			if (area > maxArea) { 
				maxArea = area; 
				maxAreaIndex = areaIndex;
			}
			areaIndex++;
		}
		double centerX = grip.getNumberArray("ballContourReport/centerX", new double[0])[maxAreaIndex];
		double centerY = grip.getNumberArray("ballContourReport/centerY", new double[0])[maxAreaIndex];
        
		return new double[] {centerX, centerY};
	} 
}
