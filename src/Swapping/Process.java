package Swapping;

import java.util.Random;

public class Process {
	
	private static final Random random = new Random();
	private static int processCount = 0;
	
	private static final int[] MEMORY_SIZES = {5, 11, 17 ,31};
	private static final int[] DURATIONS = {1, 2, 3, 4, 5};
	
	private int processNumber;
	private int size;
	private int duration;
	private int runtime = 0;
	private int startingIndex;
	
	public Process() {
		
		// set process number
		this.processNumber = (Process.processCount++) + 1;
		
		// set size
		this.size = MEMORY_SIZES[random.nextInt(MEMORY_SIZES.length)];
		
		// set duration
		this.duration = DURATIONS[random.nextInt(DURATIONS.length)];
	}
	
	public void run() {
		runtime++;
	}
	
	public int getStartingIndex(){
		return startingIndex;
	}
	
	public int getProcessNumber() {
		return processNumber;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public void setStartingIndex(int start){
		startingIndex = start;
	}
	
	public String toString() {
		return "PN: " + this.processNumber + " " + this.startingIndex + " " + this.size + " " + this.runtime + "/" + this.duration;
	}
}
