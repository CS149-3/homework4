package Swapping;

import Swapping.Memory.Block;

public class BestFit implements Swap {
	
	public BestFit() {
		// Doubt you need to use this, nothing needs to be remembered, just find the best fit
	}
	
	/**
	 * Swaps the process into memory if able, according to its algorithm.
	 * @return true if able to swap process into a free block, false if no blocks have room for the process
	 */
	@Override
	public boolean swap(Process process, Memory memory) {
		/*
		 *  If you remember doing a max search, that might be a good idea here, except you are searching for the closest
		 *  block size that can still big enough to contain 
		 */
		return false;
	}

}
