package org.usfirst.frc.team5181.robot;

import java.util.StringTokenizer;

import org.firs.frc.team5181.Recoder.Lock;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class Gamepad {
	
	private int portNumber;
	
	//States of Buttons
	public boolean A_Button_State = false;
	public boolean B_Button_State = false;
	public boolean X_Button_State = false;
	public boolean Y_Button_State = false;
	public boolean LEFT_Bumper_State = false;
	public boolean RIGHT_Bumper_State = false;
	public boolean BACK_State = false;
	public boolean START_State = false;
	public boolean LEFT_Stick_DOWN_State = false;
	public boolean RIGHT_Stick_DOWN_State = false;
	
	// Axis
	public double LEFT_Stick_X_State = 0;
	public double LEFT_Stick_Y_State = 0;
	public double LEFT_Trigger_State = 0;
	public double RIGHT_Trigger_State = 0;
	public double RIGHT_Stick_X_State = 0;
	public double RIGHT_Stick_Y_State = 0;
	
	public Gamepad(int port) {
		portNumber = port;
	}
	public Gamepad(String state) {
		int button;
		String magnitude;
		StringTokenizer tokenizer = new StringTokenizer(state, ";");
		
		String token;
		while(tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			int colonIndex = token.indexOf(':');    ///Interpret button and magnitude
			
			button = Integer.parseInt(token.substring(0, colonIndex));
			magnitude = token.substring(colonIndex + 1);
			//adjust game controller state
			switch (button) {
		    	case 1:
		    		this.A_Button_State = magnitude.equals("1.0") ;
		    		break;
		    	case 2:
		    		this.B_Button_State = magnitude.equals("1.0") ;
		    		break;
		    	case 3:
		    		this.X_Button_State = magnitude.equals("1.0") ;
		    		break;
		    	case 4:
		    		this.Y_Button_State = magnitude.equals("1.0") ;
		    		break;
		    	case 5:
		    		this.LEFT_Bumper_State = magnitude.equals("1.0") ;
		    		break;
		    	case 6:
		    		this.RIGHT_Bumper_State = magnitude.equals("1.0") ;
		    		break;
		    	case 12:
		    		this.LEFT_Stick_Y_State = Double.parseDouble(magnitude);
		    		break;
		    	case 11:
		    		this.LEFT_Stick_X_State = Double.parseDouble(magnitude);
		    		break;
		    	case 16:
		    		this.RIGHT_Stick_Y_State = Double.parseDouble(magnitude);
		    		break;
		    	case 15:
		    		this.RIGHT_Stick_X_State = Double.parseDouble(magnitude);
		    		break;
		    	case 14:
		    		this.RIGHT_Trigger_State = Double.parseDouble(magnitude);
		    		break;
		    	case 13:
		    		this.LEFT_Trigger_State = Double.parseDouble(magnitude);
		    		break;
			}
		}
	}

	public void getPhysicalState() {
		try {
			Joystick j = new Joystick(portNumber);
			j.setAxisChannel(Joystick.AxisType.kTwist, Statics.LEFT_Stick_X);
			j.setAxisChannel(Joystick.AxisType.kX, Statics.RIGHT_Stick_X);
			j.setAxisChannel(Joystick.AxisType.kY, Statics.RIGHT_Stick_Y);
			
			 A_Button_State = j.getRawButton(Statics.A_Button);
			 B_Button_State = j.getRawButton(Statics.B_Button);
			 X_Button_State = j.getRawButton(Statics.X_Button);
			 Y_Button_State = j.getRawButton(Statics.Y_Button);
			 LEFT_Bumper_State = j.getRawButton(Statics.LEFT_Bumper);
			 RIGHT_Bumper_State = j.getRawButton(Statics.RIGHT_Bumper);
			 BACK_State = j.getRawButton(Statics.BACK);
			 START_State = j.getRawButton(Statics.START);
			 LEFT_Stick_DOWN_State = j.getRawButton(Statics.LEFT_Stick_DOWN);
			 RIGHT_Stick_DOWN_State =j.getRawButton(Statics.RIGHT_Stick_DOWN);
			
			// Axis
			 LEFT_Stick_X_State = j.getRawAxis(Statics.LEFT_Stick_X);
			 LEFT_Stick_Y_State = j.getRawAxis(Statics.LEFT_Stick_Y);
			 LEFT_Trigger_State = j.getRawAxis(Statics.LEFT_Trigger);
			 RIGHT_Trigger_State = j.getRawAxis(Statics.RIGHT_Trigger);
			 RIGHT_Stick_X_State = j.getRawAxis(Statics.RIGHT_Stick_X);
			 RIGHT_Stick_Y_State = j.getRawAxis(Statics.RIGHT_Stick_Y);
			 
		}
		catch (Exception e) {
			DriverStation.reportError(e.getMessage(), true);
		}
	}
		
}
