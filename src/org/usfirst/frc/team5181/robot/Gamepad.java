package org.usfirst.frc.team5181.robot;

import java.util.StringTokenizer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class Gamepad {
	
	private static int portNumber = 3;
	
	//States of Buttons
	public static boolean A_Button_State = false;
	public static boolean B_Button_State = false;
	public static boolean X_Button_State = false;
	public static boolean Y_Button_State = false;
	public static boolean LEFT_Bumper_State = false;
	public static boolean RIGHT_Bumper_State = false;
	public static boolean BACK_State = false;
	public static boolean START_State = false;
	public static boolean LEFT_Stick_DOWN_State = false;
	public static boolean RIGHT_Stick_DOWN_State = false;
	
	// Axis
	public static double LEFT_Stick_X_State = 0;
	public static double LEFT_Stick_Y_State = 0;
	public static double LEFT_Trigger_State = 0;
	public static double RIGHT_Trigger_State = 0;
	public static double RIGHT_Stick_X_State = 0;
	public static double RIGHT_Stick_Y_State = 0;
	
	public static void setNaturalState(int port) {
		portNumber = port;
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
	public static void setNaturalState() {
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
	public static void setSyntheticState(String state) {
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
		    		A_Button_State = magnitude.equals("1.0") ;
		    		break;
		    	case 2:
		    		B_Button_State = magnitude.equals("1.0") ;
		    		break;
		    	case 3:
		    		X_Button_State = magnitude.equals("1.0") ;
		    		break;
		    	case 4:
		    		Y_Button_State = magnitude.equals("1.0") ;
		    		break;
		    	case 5:
		    		LEFT_Bumper_State = magnitude.equals("1.0") ;
		    		break;
		    	case 6:
		    		RIGHT_Bumper_State = magnitude.equals("1.0") ;
		    		break;
		    	case 12:
		    		LEFT_Stick_Y_State = Double.parseDouble(magnitude);
		    		break;
		    	case 11:
		    		LEFT_Stick_X_State = Double.parseDouble(magnitude);
		    		break;
		    	case 16:
		    		RIGHT_Stick_Y_State = Double.parseDouble(magnitude);
		    		break;
		    	case 15:
		    		RIGHT_Stick_X_State = Double.parseDouble(magnitude);
		    		break;
		    	case 14:
		    		RIGHT_Trigger_State = Double.parseDouble(magnitude);
		    		break;
		    	case 13:
		    		LEFT_Trigger_State = Double.parseDouble(magnitude);
		    		break;
			}
		}
	}
}
