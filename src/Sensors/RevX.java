package sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI.Port;

public class RevX extends AHRS {
	//Variables for Collision
	double AccX, AccY, PrevAccX, PrevAccY;
	final static double Collision_DealtaG = 1f;
	
	public RevX(Port port) {
		super(port);
		AccX = 0;
		AccY = 0;
		PrevAccX = 0;
		PrevAccY = 0;
	}
	
	public boolean hadCollision() {
		AccX = this.getWorldLinearAccelX();
		AccY = this.getWorldLinearAccelY();
		
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
		return this.getYaw(); 
	}
	

	public double[] getDisplacement() {
		return new double[] {this.getDisplacementX(), this.getDisplacementY(), this.getDisplacementZ()};
	}
	
	public void reset() {
		this.reset();
	}

}
