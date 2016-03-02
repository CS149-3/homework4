package Swapping;

import java.util.Comparator;
import Swapping.Memory.Block;
import java.util.ArrayList;

public class BestFit implements Swap {
	
	public BestFit() {
		// Unused
	}
	
	/**
	 * Swaps the process into memory if able, according to its algorithm.
	 * @return true if able to swap process into a free block, false if no blocks have room for the process
	 */
	@Override
	public boolean swap(Process process, Memory memory) {
		
		// Sort the list of free blocks by size
		ArrayList<Block> freeBlocks = memory.getFreeBlocks();
		freeBlocks.sort(new SizeComparator());
		
		/* Since the list is sorted by size this will now find the smallest 
		 * possible space that is at least the size of the process
		 */
		for (Block b : freeBlocks) {
			if (b.getSize() >= process.getSize()) {
				memory.swapProcess(process, b);
				return true;
			}
		}
		return false;
	}
	
	private static class SizeComparator implements Comparator<Block> {
		@Override
		public int compare(Block b1, Block b2) {
			if (b1.getSize() < b2.getSize()) 
				return -1;
			else if (b1.getSize() == b2.getSize()) 
				return 0;
			else 
				return 1;
		}
	}
}
