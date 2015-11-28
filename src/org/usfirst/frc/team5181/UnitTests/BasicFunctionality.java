package org.usfirst.frc.team5181.UnitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.usfirst.frc.team5181.robot.Robot;

public class BasicFunctionality {
	
	@Test
	public void method() {
		Robot rb = new Robot();
		rb.robotInit();
		assertEquals(1+1, 2);
	}
}
