package org.usfirst.frc.team5181.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class Gamepad extends Joystick {
	// Gamepad ports
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
	// Axis
	public static int LEFT_Stick_X = 0;
	public static int LEFT_Stick_Y = 1;
	public static int LEFT_Trigger = 2;
	public static int RIGHT_Trigger = 3;
	public static int RIGHT_Stick_X = 4;
	public static int RIGHT_Stick_Y = 5;

	// Gamepad states
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

	public Gamepad() {
		super(3);

		setAxisChannel(Joystick.AxisType.kTwist, LEFT_Stick_X);
		setAxisChannel(Joystick.AxisType.kX, RIGHT_Stick_X);
		setAxisChannel(Joystick.AxisType.kY, RIGHT_Stick_Y);

	}
	
	public void update() {
		A_Button_State = this.getRawButton(A_Button);
		B_Button_State = this.getRawButton(B_Button);
		X_Button_State = this.getRawButton(X_Button);
		Y_Button_State = this.getRawButton(Y_Button);
		LEFT_Bumper_State = this.getRawButton(LEFT_Bumper);
		RIGHT_Bumper_State = this.getRawButton(RIGHT_Bumper);
		BACK_State = this.getRawButton(BACK);
		START_State = this.getRawButton(START);
		LEFT_Stick_DOWN_State = this.getRawButton(LEFT_Stick_DOWN);
		RIGHT_Stick_DOWN_State = this.getRawButton(RIGHT_Stick_DOWN);

		// Axis
		LEFT_Stick_X_State = this.getRawAxis(LEFT_Stick_X);
		LEFT_Stick_Y_State = this.getRawAxis(LEFT_Stick_Y);
		LEFT_Trigger_State = this.getRawAxis(LEFT_Trigger);
		RIGHT_Trigger_State = this.getRawAxis(RIGHT_Trigger);
		RIGHT_Stick_X_State = this.getRawAxis(RIGHT_Stick_X);
		RIGHT_Stick_Y_State = this.getRawAxis(RIGHT_Stick_Y);
	}
}
