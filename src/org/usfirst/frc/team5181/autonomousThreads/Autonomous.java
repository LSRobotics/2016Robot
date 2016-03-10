package org.usfirst.frc.team5181.autonomousThreads;

public interface Autonomous {
	public void actionPlayback(String recordingName, long time);
	public void setAutonState(boolean inAuton);
}
