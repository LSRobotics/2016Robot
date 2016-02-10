package sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;

public class RevX {
	AHRS revX;
	
	//Variables for Collision
	double AccX, AccY, PrevAccX, PrevAccY;
	final static double Collision_DealtaG = 0.5f;
	
	public RevX(Port port) {
		revX = new AHRS(port);
		
		AccX = 0;
		AccY = 0;
		PrevAccX = 0;
		PrevAccY = 0;
	}
	
	public boolean hadCollision() {
		AccX = revX.getWorldLinearAccelX();
		AccY = revX.getWorldLinearAccelY();
		
		double JerkX = AccX - PrevAccX;
		double JerkY = AccY - PrevAccY;
		
		PrevAccX = AccX;
		PrevAccY = AccY;
		
		if(Math.abs(JerkX) < Collision_DealtaG || Math.abs(JerkY) < Collision_DealtaG) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public double getRotation() {
		return revX.getYaw(); 
	}
	

	public double[] getDisplacement() {
		return new double[] {revX.getDisplacementX(), revX.getDisplacementY(), revX.getDisplacementZ()};
	}
}
