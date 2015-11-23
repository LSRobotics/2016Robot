package org.usfirst.frc.team5181.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Gamepad extends Joystick{
	//Gamepad
	public static int A_Button = 1;
	public static int B_Button = 2;
	public static int X_Button = 3;
	public static int Y_Button = 4;
	public static int LEFT_Bumper = 5;
	public static int RIGHT_Bumper = 6;
	public static int BACK = 7;
	public static int START = 8;
	public static int LEFT_Stick_DOWN = 9;
	public static int RIGHT_Stick_DOWN = 10;	
	//Axis
	public static int LEFT_Stick_X = 0;
	public static int LEFT_Stick_Y = 1;
	public static int LEFT_Trigger = 2;
	public static int RIGHT_Trigger = 3;
	public static int RIGHT_Stick_X = 4;
	public static int RIGHT_Stick_Y = 5;
	
	public Gamepad() {
		super(2);
		
		setAxisChannel(Joystick.AxisType.kTwist, LEFT_Stick_X);
		setAxisChannel(Joystick.AxisType.kX, RIGHT_Stick_X);
		setAxisChannel(Joystick.AxisType.kY, RIGHT_Stick_Y);
		
	}
}
