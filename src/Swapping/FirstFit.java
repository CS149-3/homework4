package Swapping;

import Swapping.Memory.Block;

public class FirstFit implements Swap {
	
	public FirstFit() {
		// unused, FirstFit does not need to remember anything
	}
	
	/**
	 * Swaps the process into memory if able, according to its algorithm.
	 * @return true if able to swap process into a free block, false if no blocks have room for the process
	 */
	@Override
	public boolean swap(Process process, Memory memory) {
		
		// for each free block of memory (already in lowest index order for first fit)
		for (Block block : memory.getFreeBlocks()) {
			
			// if block size can fit the process
			if (block.getSize() >= process.getSize()) {
				
				// swap the process into that block
				memory.swapProcess(process, block);
				
				// return true, signaling that there was room for the process and to try to fit another
				return true;
			}
		}
		
		// if there is no room for the process, indicate that there is no more room and to continue running processes
		return false;
	}

}
