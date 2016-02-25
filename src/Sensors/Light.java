package Actuators;

import java.awt.Color;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Relay;

public class Light{
	Relay redRelay;
	Relay greenRelay;
	Relay blueRelay;
	final Color GOLD = new Color(255, 215, 0);
	final Color RED = new Color(255, 0, 0);
	final Color BLUE = new Color(0, 0, 255);
	
	public Light(){
		redRelay = new Relay(1, Relay.Direction.kForward);
		greenRelay = new Relay(3,Relay.Direction.kForward);
		blueRelay = new Relay(2, Relay.Direction.kForward);
	}
	
	public void setColor(Color c) {
		redRelay.set( 1 - (c.getRed()/255));
		greenRelay.set(1 - (c.getGreen()/255));
		blueRelay.set(1 - (c.getBlue()/255));
	}
	
	public void periodic(){
		while (true){
			if (DriverStation.getInstance().getAlliance().equals(Alliance.Blue)) {
				this.setColor(BLUE);
			}
			else if (DriverStation.getInstance().getAlliance().equals(Alliance.Red)) {
				this.setColor(RED);
			}
			else {
				this.setColor(GOLD);
			}
		}
	}
}
