package Paging;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {
	
	private static final int PAGES = 10;
	
	// not needed, just felt like having each algorithm go through the same pattern for each run if anyone wanted to compare
	private static long seed;
	
	private static ArrayList<Page> disk;
	private static ArrayList<Page> memory;
	private static int totalHitsFIFO = 0;
	private static int totalHitsLRU = 0;
	private static int totalHitsLFU = 0;
	private static int totalHitsMFU = 0;
	private static int totalHitsRand = 0;
	
	public static void main(String[] args) {
		
		// set up arrays
		disk = new ArrayList<Page>();
		memory = new ArrayList<Page>();
		
		// populate disk with pages
		for (int page = 0; page < PAGES; page++) disk.add(new Page());
		
		// multiple runs loop
		for (int run = 1; run <= 5; run++) {
			System.out.println("Run " + run);
			System.out.println("-----------------------------------------------");
			
			// seed stuff, see comment on variable. Unique for each run, but not for each algorithm within a run
			Random random = new Random();
			Simulation.seed = random.nextLong();
			
			// simulate
			System.out.println("\nFIFO Run");
			System.out.println("\n-----------------------------------------------\n");
			totalHitsFIFO += simulate(new FIFO(), copyPageArray(disk), copyPageArray(memory));
			System.out.println("\nLRU Run");
			System.out.println("\n-----------------------------------------------\n");
			totalHitsLRU += simulate(new LRU(), copyPageArray(disk), copyPageArray(memory));
			System.out.println("\nLFU Run");
			System.out.println("\n-----------------------------------------------\n");
			totalHitsLFU += simulate(new LFU(), copyPageArray(disk), copyPageArray(memory));
			System.out.println("\nMFU Run");
			System.out.println("\n-----------------------------------------------\n");
			totalHitsMFU += simulate(new MFU(), copyPageArray(disk), copyPageArray(memory));
			System.out.println("\nRandom Run");
			System.out.println("\n-----------------------------------------------\n");
			totalHitsRand += simulate(new Rand(), copyPageArray(disk), copyPageArray(memory));
		}
		
		// average output
		System.out.println("\n");
		System.out.println("FIFO hit ratio " + Simulation.totalHitsFIFO + "/500");
		System.out.println("LRU  hit ratio " + Simulation.totalHitsLRU + "/500");
		System.out.println("LFU  hit ratio " + Simulation.totalHitsLFU + "/500");
		System.out.println("MFU  hit ratio " + Simulation.totalHitsMFU + "/500");
		System.out.println("Rand hit ratio " + Simulation.totalHitsRand + "/500");
	}
	
	private static int simulate(Algorithm algorithm, ArrayList<Page> disk, ArrayList<Page> memory) {
		
		int successfulHits = 0;
		Random random = new Random(Simulation.seed);
		
		// randomly set first page reference
		int page = random.nextInt(10);
		
		// for 100 references
		for (int i = 0; i < 100; i++) {
			
			// Randomly choose a page delta, accounting for locality chance.
			int deltaPages = random.nextInt(10) < 7 ? random.nextInt(3) - 1 : random.nextInt(7) + 2;
			
			// set new page with wrapping
			page = page + deltaPages < PAGES ? page + deltaPages : page + deltaPages - PAGES;
			if (page == -1) page = PAGES - 1;
			
			// update page reference
			ArrayList<Page> combined = new ArrayList<Page>();
			combined.addAll(memory);
			combined.addAll(disk);
			for (Page p : combined) {
				if (p.getPageId() == page) {
					p.referenced(i, memory);
					break;
				}
			}
			
			// apply memory algorithm
			Integer evictedPage = algorithm.pageReferenced(page, disk, memory);
			
			// print memory state after possible changes
			System.out.println(memory);
			
			// if page needed to be swapped into memory, print changes
			if (evictedPage == null) {
				successfulHits++;
			}
			else {
				System.out.println("Page moved in: " + page);
				System.out.println("Page evicted: " + evictedPage);
			}
		}
		return successfulHits;
	}
	
	private static ArrayList<Page> copyPageArray(ArrayList<Page> pages) {
		ArrayList<Page> pagesCopy = new ArrayList<Page>();
		
		for (Page page : pages) pagesCopy.add(new Page(page));
		
		return pagesCopy;
	}
	
}