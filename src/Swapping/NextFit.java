package Swapping;

import Swapping.Memory.Block;

public class NextFit implements Swap {
	private int lastIndex;
	public NextFit() {
		// consider keeping track of a "last index used" here, as a starting point for looking for room
		lastIndex = 0;
	}
	
	/**
	 * Swaps the process into memory if able, according to its algorithm.
	 * @return true if able to swap process into a free block, false if no blocks have room for the process
	 */
	@Override
	public boolean swap(Process process, Memory memory) {
		
		/* 
		 * This one will likely require two searches due to how I designed the block system (if you can think of an improvement
		 * that would make this work nicely (perhaps a special sort function?) then make a branch and a PR for it.
		 * 
		 * Otherwise just search through all the blocks, ignoring the ones that are at or before your last used index.
		 * If there was no room found, follow up with a search through the blocks doing the opposite, ignoring all
		 * that are at or after your last used index.
		 */
		int index = search(lastIndex, process.getSize(), memory);
		if(index < 0){		
			return false;
		}
		
		memory.swapProcess(process, index);
		lastIndex = index + process.getSize();
		return true;
	}
	
	private int search(int index, int size, Memory memory){
		int sizeAtIndex = memory.checkMem(index);
		if(sizeAtIndex >= size)
			return index;
		
		Block nextBlock = memory.getNextBlock(index);
		
		if(nextBlock.getSize() >= size){
			return nextBlock.getIndex();
		}
		
		if(nextBlock.getIndex() == lastIndex){
			return -1;
		}
		return search(index, size, memory);
	}
}