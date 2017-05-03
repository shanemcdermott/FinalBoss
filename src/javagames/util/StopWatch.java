package javagames.util;

public class StopWatch {

	private long startTime;
	private long stopTime;
	private long elapsedTime;
	
	public StopWatch () {
		startTime = stopTime = 0;
	}
	
	public void start () {
		startTime = System.nanoTime();
	}
	
	public void stop () {
		stopTime = System.nanoTime();
		elapsedTime = stopTime - startTime;
	}
	
	public long hours () {
		return elapsedTime / 3_600_000_000_000_000L;
	}
	
	public long minutes () {
		return elapsedTime % 3_600_000_000_000L / 60_000_000_000L;
	}
	
	public long seconds () {
		return elapsedTime % 60_000_000_000L / 1_000_000_000L;
	}
	
	public long milliseconds () {
		return elapsedTime % 1_000_000_000L / 1_000_000L;
	}
	
	public long microseconds () {
		return elapsedTime % 1_000_000L / 1_000L;
	}
	
	public long nanoseconds () {
		return elapsedTime % 1_000L;
	}
	
	public long totalHours () {
		return elapsedTime / 3_600_000_000_000_000L;
	}
	
	public long totalMinutes () {
		return elapsedTime / 60_000_000_000L;
	}
	
	public long totalSeconds () {
		return elapsedTime / 1_000_000_000L;
	}
	
	public long totalMilliseconds () {
		return elapsedTime / 1_000_000L;
	}
	
	public long totalMicroseconds () {
		return elapsedTime / 1_000L;
	}
	
	public long totalNanoseconds () {
		return elapsedTime;
	}
	
}
