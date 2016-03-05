package Paging;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {
	
	private static final int PAGES = 10;
	
	// not needed, just felt like having each algorithm go through the same pattern for each run if anyone wanted to compare
	private static long seed;
	
	private static ArrayList<Page> disk;
	private static ArrayList<Page> memory;
	
	public static void main(String[] args) {
		
		// set up arrays
		disk = new ArrayList<Page>();
		memory = new ArrayList<Page>();
		
		// populate disk with pages
		for (int page = 0; page < PAGES; page++) disk.add(new Page());
		
		// multiple runs loop
		for (int run = 0; run < 5; run++) {
			
			// seed stuff, see comment on variable. Unique for each run, but not for each algorithm within a run
			Random random = new Random();
			Simulation.seed = random.nextLong();
			
			// simulate
			simulate(new FIFO(), copyPageArray(disk), copyPageArray(memory));
			simulate(new LRU(), copyPageArray(disk), copyPageArray(memory));
			simulate(new LFU(), copyPageArray(disk), copyPageArray(memory));
			simulate(new MFU(), copyPageArray(disk), copyPageArray(memory));
			simulate(new Rand(), copyPageArray(disk), copyPageArray(memory));
		}
		
	}
	
	private static void simulate(Algorithm algorithm, ArrayList<Page> disk, ArrayList<Page> memory) {
		
		int hitRatio = 0;
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
				hitRatio++;
			}
			else {
				System.out.println("Page moved in: " + page);
				System.out.println("Page evicted: " + evictedPage);
			}
		}
		
	}
	
	private static ArrayList<Page> copyPageArray(ArrayList<Page> pages) {
		ArrayList<Page> pagesCopy = new ArrayList<Page>();
		
		for (Page page : pages) pagesCopy.add(new Page(page));
		
		return pagesCopy;
	}
	
}
