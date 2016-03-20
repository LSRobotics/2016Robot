package org.usfirst.frc.team5181.autonomousThreads;

import org.usfirst.frc.team5181.robot.SimpleClient;
import org.usfirst.frc.team5181.sensors.RevX;
import org.usfirst.frc.team5181.sensors.SonicRangeSensor;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;

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

		private SonicRangeSensor ultraSonic;
		private PIDSourceType sourceType;
		
		public DisplacementSource(SonicRangeSensor us) {
			ultraSonic = us;
			sourceType = PIDSourceType.kDisplacement;
		}
		
		public void setPIDSourceType(PIDSourceType pidSource) {
			sourceType = pidSource;
		}

		public PIDSourceType getPIDSourceType() {
			return sourceType;
		}
		
		@Override
		public double pidGet() {
			switch (ultraSonic.feederPID()) {
				case kInches:
					return ultraSonic.getRangeInches();
				case kMillimeters:
					return ultraSonic.getRangeMm();
				default:
					return ultraSonic.getRangeMm();
			}
		}	
	}
}
