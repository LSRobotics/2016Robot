package Actuators;

import java.awt.Color;

public class Light{
	Relay redRelay;
	Relay greenRelay;
	Relay blueRelay;
	final Color GOLD = new Color(255, 215, 0);
	
	public Light(){
		redRelay = new Relay(1,kForwardOnly);
		greenRelay = new Relay(3,kForwardOnly);
		blueRelay = new Relay(2,kForwardOnly);
		
	}
	
	public void setColor(Color c){
		
		redRelay.set( 1 - (c.getRed()/255));
		greenRelay.set(1 - (c.getGreen()/255));
		blueRelay.set(1 - (c.getBlue()/255));
	}
	
	public void periodic(){
		while (true){
			//TODO if blue alliance, show blue
			//TODO if red alliance, show red
			this.setColor(GOLD);
		}
	}
}
