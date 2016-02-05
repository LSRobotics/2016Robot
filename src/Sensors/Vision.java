package Sensors;

import java.io.IOException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {
	private final static String GRIP_CMD =
		        "/usr/local/frc/JRE/bin/java -jar /home/lvuser/grip.jar /home/lvuser/project.grip";
	private final NetworkTable grip = NetworkTable.getTable("grip");
	
	
	public Vision() {
		try {
            new ProcessBuilder(GRIP_CMD).inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public double[] getCountorAreas() {
		//grip.retrieveValue(key, externalValue);
		//grip.getNumberArray("targets/area", new double[0]);
		return new double[0];
	}
}
