package autonomousThreads;

import org.usfirst.frc.team5181.robot.SimpleClient;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import sensors.RevX;

public class PIDSources {
	
	public static class GyroSource implements PIDSource {
		
		private RevX revX;
		private PIDSourceType sourceType;
		
		public GyroSource(RevX r) {
			revX = r;
			sourceType = PIDSourceType.kDisplacement;
		}
		
		public void setPIDSourceType(PIDSourceType pidSource) {
			sourceType = pidSource;
		}

		public PIDSourceType getPIDSourceType() {
			return sourceType;
		}

		public double pidGet() {
			return revX.getYaw();
		}
	}
	
	public static class DisplacementSource implements PIDSource {

		private RevX revX;
		private PIDSourceType sourceType;
		
		public DisplacementSource(RevX r) {
			revX = r;
			sourceType = PIDSourceType.kDisplacement;
		}
		
		public void setPIDSourceType(PIDSourceType pidSource) {
			sourceType = pidSource;
		}

		public PIDSourceType getPIDSourceType() {
			return sourceType;
		}

		public double pidGet() {
			//return Math.sqrt(Math.pow(revX.getDisplacementX(), 2) + Math.pow(revX.getDisplacementY(), 2));
			return revX.getDisplacementX();
		}
		
	}
	

}
