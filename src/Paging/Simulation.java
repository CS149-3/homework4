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
			String divider = "";
			for(int i = 0; i < 80; i++){
				divider += "=";
			}
			divider += "\n";
			
			//ADDED: Formatted and divided prints
			System.out.println(divider + "[FIFO]\n" + divider);
			simulate(new FIFO(), copyPageArray(disk), copyPageArray(memory));
			System.out.println(divider + "[LRU]\n" + divider);
			simulate(new LRU(), copyPageArray(disk), copyPageArray(memory));
			System.out.println(divider + "[LFU]\n" + divider);
			simulate(new LFU(), copyPageArray(disk), copyPageArray(memory));
			System.out.println(divider + "[MFU]\n" + divider);
			simulate(new MFU(), copyPageArray(disk), copyPageArray(memory));
			System.out.println(divider + "[Random]\n" + divider);
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
			page = Math.abs(page); //ADDED: Eliminate negative values
			
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
			ArrayList<Page> memoryCopy = copyPageArray(memory); //ADDED: make copy of memory (to later compare to the sequential cycle) after modifying
			
			Integer evictedPage = algorithm.pageReferenced(page, disk, memory);
			
			// print memory state after possible changes
			
			
			// if page needed to be swapped into memory, print changes
			if (evictedPage == null) {
				hitRatio++;
			}
			else {
				System.out.println("Page moved in: " + page);
				System.out.println("Page evicted: " + evictedPage);
				if(algorithm.getClass() == LRU.class){ //ADDED: Print out Last References for LRU Algorithm and if-clause for LFU and MFU
					System.out.println("[Last References]"); 
					for(Page p : memoryCopy){
						System.out.println(p.getPageId() + ": " + p.getLastUsed());
					}
				}else if(algorithm.getClass() == LFU.class || algorithm.getClass() == MFU.class){
					System.out.println("[Reference Frequency]"); 
					for(Page p : memoryCopy){
						System.out.println(p.getPageId() + ": " + p.getTimesUsed());
					}
				}
			}
			System.out.println(memory);
			
		}
		
	}
	
	private static ArrayList<Page> copyPageArray(ArrayList<Page> pages) {
		ArrayList<Page> pagesCopy = new ArrayList<Page>();
		for (Page page : pages) pagesCopy.add(new Page(page));
		
		return pagesCopy;
	}
	
}
