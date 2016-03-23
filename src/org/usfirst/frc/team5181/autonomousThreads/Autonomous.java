package org.usfirst.frc.team5181.autonomousThreads;

public interface Autonomous {
	public void initializeAuton(String recordingName);
	public void setAutonState(boolean inAuton);
	public void doAuton();
}
