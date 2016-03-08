package Swapping;

import java.util.ArrayList;

public class Simulation {
	
	private static final int MINUTE = 60;
	
	private static Memory memory = new Memory();

	private static int firstFitSwaps = 0;
	private static int nextFitSwaps = 0;
	private static int bestFitSwaps = 0;
	
	public static void main(String[] args) {
		for (int i = 1; i <= 5; i++) {
			System.out.println("Run " + i);
			System.out.println("-----------------------------------------------");
			ArrayList<Process> processesFirst = new ArrayList<Process>();
			ArrayList<Process> processesNext = new ArrayList<Process>();
			ArrayList<Process> processesBest = new ArrayList<Process>();
			
			// create processes
			for (int j = 0; j < 150; j++) {
				Process process = new Process();
				processesFirst.add(process);
				processesNext.add(process.copy());
				processesBest.add(process.copy());
			}
			
			int swaps;
			
			// run simulations
			System.out.println("\nFirst Fit Run");
			System.out.println("\n-----------------------------------------------\n");
			swaps = simulate(new FirstFit(), processesFirst);
			System.out.println("Swaps: " + swaps);
			firstFitSwaps += swaps;
			System.out.println("\nNext Fit Run");
			System.out.println("\n-----------------------------------------------\n");
			swaps = simulate(new NextFit(), processesNext);
			System.out.println("Swaps: " + swaps);
			nextFitSwaps += swaps;
			System.out.println("\nBest Fit Run");
			System.out.println("\n-----------------------------------------------\n");
			swaps = simulate(new BestFit(), processesBest);
			System.out.println("Swaps: " + swaps);
			bestFitSwaps += swaps;
		}
		
		// final output
		System.out.println("\n");
		System.out.println("First Fit Swap Average: " + (firstFitSwaps / 5.0));
		System.out.println("Next Fit Swap Average: " + (nextFitSwaps / 5.0));
		System.out.println("Best Fit Swap Average: " + (bestFitSwaps / 5.0));
	}
	
	private static int simulate(Swap swap, ArrayList<Process> processes) {
		//Initialize simulation
		ArrayList<Process> runningProcesses = new ArrayList<Process>();
		memory.clearMem();
		
		int swapCount = 0;
		
		// main simulation loop
		for (int seconds = 0; seconds < MINUTE; seconds++) {
			
			// clean memory
			for (int i = 0; i < runningProcesses.size(); i++) {
				Process process = runningProcesses.get(i);
				if (process.getRuntime() >= process.getDuration()) {
					String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
					int pn = process.getProcessNumber();
					while (pn > chars.length()) pn -= chars.length();
					System.out.println("process " + chars.charAt(pn-1) + " removed");
					memory.removeProcess(process);
					runningProcesses.remove(process);
					System.out.println((seconds + 1) + " " + memory);
				}
			}
			
			System.out.println();
			
			// add processes until there is no more room for the next process
			while (true) {
				Process process = processes.get(0);
				if (swap.swap(process, memory)) {
					// if successful, move to running processes
					runningProcesses.add(process);
					processes.remove(process);
					swapCount++;
					
					String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
					int pn = process.getProcessNumber();
					while (pn > chars.length()) pn -= chars.length();
					System.out.println("process " + chars.charAt(pn-1) + " added");
					System.out.println(process);
				}
				else {
					break;
				}
				// debug output. "oooooo prettyyyyy...."
				// will replace with output specified from homework, but this is something to look at
				System.out.println((seconds + 1) + " " + memory);
			}
			
			System.out.println();
			
			// run processes for one "second"
			for (Process process : runningProcesses) {
				process.run();
			}
		}
		
		return swapCount;
	}
}
