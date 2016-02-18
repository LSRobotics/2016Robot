package autonomousThreads;

public interface Autonomous {
	public void actionPlayback(String fileName, long time);
	public void setAutonState(boolean inAuton);
}
