package org.usfirst.frc.team5181.robot;

import java.util.StringTokenizer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class Gamepad {
	
	private static int GPportNumber = 3;
	private static int JSportNumber = 4;
	
	//States of Buttons
	public static boolean A_Button_State = false; //FROM JOYSTICK
	public static boolean B_Button_State = false; //FROM GAMEPAD
	public static boolean X_Button_State = false; //FROM GAMEPAD
	public static boolean Y_Button_State = false; //FROM JOYSTICK
	public static boolean LEFT_Bumper_State = false; //FROM GAMEPAD
	public static boolean RIGHT_Bumper_State = false; //FROM GAMEPAD
	public static boolean BACK_State = false; //FROM GAMEPAD
	public static boolean START_State = false; //FROM GAMEPAD
	public static boolean LEFT_Stick_DOWN_State = false; //FROM JOYSTICK
	public static boolean RIGHT_Stick_DOWN_State = false; //FROM GAMEPAD
	public static boolean TRIGGER_State = false; //FROM JOYSTICK
	
	// Axis
	public static double LEFT_Stick_X_State = 0; //FROM JOYSTICK
	public static double LEFT_Stick_Y_State = 0; //FROM JOYSTCIK
	public static double LEFT_Trigger_State = 0; //FROM GAMEPAD
	public static double RIGHT_Trigger_State = 0; //FROM GAMEPAD
	public static double RIGHT_Stick_X_State = 0; //FROM GAMEPAD
	public static double RIGHT_Stick_Y_State = 0; //FROM GAMEPAD

	//DPAD
	public static double D_PAD_State = -1; //FROM GAMEPAD
	
	public static void setNaturalState(int GPport, int JSport) {
		try {
			Joystick jGP = new Joystick(GPport);
			Joystick jJS = new Joystick(JSport);
			
			jJS.setAxisChannel(Joystick.AxisType.kTwist, Statics.LEFT_Stick_X);
			jGP.setAxisChannel(Joystick.AxisType.kX, Statics.RIGHT_Stick_X);
			jGP.setAxisChannel(Joystick.AxisType.kY, Statics.RIGHT_Stick_Y);
			
			//A_Button_State = jGP.getRawButton(Statics.A_Button);
			//Y_Button_State = jGP.getRawButton(Statics.Y_Button);
			
			if(jJS.getPOV() == 180) {
				A_Button_State = true;
			}
			else if(jJS.getPOV() == 0) {
				Y_Button_State = true;
			}
			else {
				Y_Button_State = false;
				A_Button_State = false;
			}
			
			B_Button_State = jGP.getRawButton(Statics.B_Button);
			X_Button_State = jGP.getRawButton(Statics.X_Button);
			LEFT_Bumper_State = jGP.getRawButton(Statics.LEFT_Bumper);
			RIGHT_Bumper_State = jGP.getRawButton(Statics.RIGHT_Bumper);
			BACK_State = jGP.getRawButton(Statics.BACK);
			START_State = jGP.getRawButton(Statics.START);
			LEFT_Stick_DOWN_State = jJS.getRawButton(Statics.LEFT_Stick_DOWN);
			RIGHT_Stick_DOWN_State =jGP.getRawButton(Statics.RIGHT_Stick_DOWN);
			TRIGGER_State = jJS.getRawButton(Statics.JoyStickTrigger);
			
			// Axis
			LEFT_Stick_X_State = jJS.getRawAxis(Statics.LEFT_Stick_X);
			LEFT_Stick_Y_State = jJS.getRawAxis(Statics.LEFT_Stick_Y);
			LEFT_Trigger_State = jGP.getRawAxis(Statics.LEFT_Trigger);
			RIGHT_Trigger_State = jGP.getRawAxis(Statics.RIGHT_Trigger);
			RIGHT_Stick_X_State = jGP.getRawAxis(Statics.RIGHT_Stick_X);
			RIGHT_Stick_Y_State = jGP.getRawAxis(Statics.RIGHT_Stick_Y);
			 
			//DPAD
			//D_PAD_State = jGP.getPOV();
			if(jJS.getRawButton(Statics.MAXPOWER)) {
				D_PAD_State = 0;
			}
			else {
				D_PAD_State = -1;
			}
		}
		catch (Exception e) {
			DriverStation.reportError(e.getMessage(), true);
		}
	}
	public static void setNaturalState() {
		try {
			Joystick jGP = new Joystick(GPportNumber);
			Joystick jJS = new Joystick(JSportNumber);
			
			jJS.setAxisChannel(Joystick.AxisType.kTwist, Statics.LEFT_Stick_X);
			jGP.setAxisChannel(Joystick.AxisType.kX, Statics.RIGHT_Stick_X);
			jGP.setAxisChannel(Joystick.AxisType.kY, Statics.RIGHT_Stick_Y);
			
			//A_Button_State = jGP.getRawButton(Statics.A_Button);
			//Y_Button_State = jGP.getRawButton(Statics.Y_Button);
			
			if(jJS.getPOV() == 180) {
				A_Button_State = true;
			}
			else if(jJS.getPOV() == 0) {
				Y_Button_State = true;
			}
			else {
				Y_Button_State = false;
				A_Button_State = false;
			}
			
			B_Button_State = jGP.getRawButton(Statics.B_Button);
			X_Button_State = jGP.getRawButton(Statics.X_Button);
			
			LEFT_Bumper_State = jGP.getRawButton(Statics.LEFT_Bumper);
			RIGHT_Bumper_State = jGP.getRawButton(Statics.RIGHT_Bumper);
			BACK_State = jGP.getRawButton(Statics.BACK);
			START_State = jGP.getRawButton(Statics.START);
			LEFT_Stick_DOWN_State = jJS.getRawButton(Statics.LEFT_Stick_DOWN);
			RIGHT_Stick_DOWN_State =jGP.getRawButton(Statics.RIGHT_Stick_DOWN);
			TRIGGER_State = jJS.getRawButton(Statics.JoyStickTrigger);
			
			// Axis
			LEFT_Stick_X_State = jJS.getRawAxis(Statics.LEFT_Stick_X);
			LEFT_Stick_Y_State = jJS.getRawAxis(Statics.LEFT_Stick_Y);
			LEFT_Trigger_State = jGP.getRawAxis(Statics.LEFT_Trigger);
			RIGHT_Trigger_State = jGP.getRawAxis(Statics.RIGHT_Trigger);
			RIGHT_Stick_X_State = jGP.getRawAxis(Statics.RIGHT_Stick_X);
			RIGHT_Stick_Y_State = jGP.getRawAxis(Statics.RIGHT_Stick_Y);
			
			//DPAD
			//D_PAD_State = jGP.getPOV();
			if(jJS.getRawButton(Statics.MAXPOWER)) {
				D_PAD_State = 0;
			}
			else {
				D_PAD_State = -1;
			}
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
		    	case 17:
		    		LEFT_Stick_DOWN_State = magnitude.equals("1.0");
		    		break;
		    	case 18:
		    		RIGHT_Stick_DOWN_State = magnitude.equals("1.0");
		    		break;
		    	case 19:
		    		TRIGGER_State = magnitude.equals("1.0");
		    		break;
			}
		}
	}
}
