package Swapping;

import java.util.ArrayList;

public class Simulation {
	
	private static final int MINUTE = 60;
	private static ArrayList<Process> processes = new ArrayList<Process>();
	private static ArrayList<Process> runningProcesses = new ArrayList<Process>();
	private static Memory memory = new Memory();
	
	public static void main(String[] args) {
		
		// create processes
		for (int i = 0; i < 150; i++) {
			processes.add(new Process());
		}
		
		// run simulations
		simulate(new FirstFit());
		simulate(new NextFit());
		simulate(new BestFit());
		
		// final output here probably
		
	}
	
	private static void simulate(Swap swap) {
		// main simulatiom loop
		for (int seconds = 0; seconds < MINUTE; seconds++) {
			
			// clean memory
			for (int i = 0; i < runningProcesses.size(); i++) {
				Process process = runningProcesses.get(i);
				if (process.getRuntime() >= process.getDuration()) {
					memory.removeProcess(process);
					runningProcesses.remove(process);
				}
			}
			
			// add processes until there is no more room for the next process
			while (true) {
				Process process = processes.get(0);
				if (swap.swap(process, memory)) {
					// if successful, move to running processes
					runningProcesses.add(process);
					processes.remove(process);
				}
				else {
					break;
				}
				// debug output. "oooooo prettyyyyy...."
				// will replace with output specified from homework, but this is something to look at
				System.out.println((seconds + 1) + " " + memory);
			}
			
			// run processes for one "second"
			for (Process process : runningProcesses) {
				process.run();
			}
		}
	}
}
