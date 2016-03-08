package Swapping;

import java.util.ArrayList;

public class Memory {
	
	private int[] memory;
	
	public Memory() {
		this.memory = new int[100];
	}
	
	/**
	 * Checks specified location in memory for available blocks.
	 * @param index - Memory starting location
	 * @return Integer with the size of the available block. 
	 */
	public int checkMem(int index){
		int size = 0;
		try{
			while(memory[size+index] == 0){
				size++;
			}
		}catch(IndexOutOfBoundsException e){
			return size;
		}
		return size;
	}
	
	/**
	 * Returns the free blocks available in memory
	 * @return ArrayList<Block> List of free blocks
	 */
	public ArrayList<Block> getFreeBlocks() {
		ArrayList<Block> freeBlocks = new ArrayList<Block>();
		
		for (int i = 0; i < memory.length; i++) {
			if (memory[i] == 0) {
				int index = i;
				while (i < memory.length && memory[i] == 0) {
					i++;
				}
				int size = i - index;
				freeBlocks.add(new Block(index, size));
			}
		}
		
		return freeBlocks;
	}
	
	/**
	 * Convenience version of getNextBlock(int index)
	 * @param block use this block's index as starting point
	 * @return Block return index and size of next block in memory
	 */
	public Block getNextBlock(Block block) {
		return getNextBlock(block.getIndex());
	}
	
	/**
	 * Return index of next block. 
	 * @param index - Starting point of check
	 * @return block return index and size of next block in memory
	 */
	public Block getNextBlock(int index){
		if(memory[index] != 0){
			int process = memory[index];
			try{
				while(memory[index] == process){
					index++;
				}
			}catch(IndexOutOfBoundsException e){
				return new Block(0, checkMem(0));
			}
			return new Block(index, checkMem(index));
		}
		
		try{
			while(memory[index] == 0){
				index++;
			}
		}catch(IndexOutOfBoundsException e){
			return new Block(0, checkMem(0));
		}
		return new Block(index, checkMem(index));
	}
	
	/**
	 * Convenience function for swapProcess(Process process, int index) below, see for details
	 * @param process Process to be added
	 * @param block Block to add process to (passes index)
	 */
	public void swapProcess(Process process, Block block) {
		swapProcess(process, block.getIndex());
	}
	
	/**
	 * Enters process into Memory. Checks if dump is possible and throws RunTime exception if not.
	 * Replaces memory indices index to index + Process object size with Process object ID and set starting index in process. 
	 * @param process - Process to be added
	 * @param index - location to add process
	 */
	public void swapProcess(Process process, int index){
		if(process.getSize() > checkMem(index)){
			throw new RuntimeException("Index " + index + " in memory does not have block size of " + process.getSize() + " available");
		}
		
		process.setStartingIndex(index);
		
		for(int i = index; i < index + process.getSize(); i++){
			memory[i] = process.getProcessNumber();
		}
	}
	
	/**
	 * Removes a process from memory.  
	 * Replaces process ID with 0. 
	 * @param process process to remove from memory
	 */
	public void removeProcess(Process process) {
		for (int i = process.getStartingIndex(); i < (process.getStartingIndex() + process.getSize()); i++) {
			memory[i] = 0;
		}
	}
	
	public String toString() {
		String str = "";
		for (int mb : memory) {
			if (mb == 0) {
				str += ".";
			}
			else {
				String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
				int i = mb;
				while (i > chars.length()) i -= chars.length();
				str += chars.charAt(i-1);
			}
		}
		return str.substring(0, str.length() - 2);
	}
	
	public void clearMem(){
		for(int i = 0; i < memory.length;i++){
			memory[i] = 0;
		}
	}
	/**
	 * Inner class Block, represents a free block of space in memory.
	 * @author jludeman
	 */
	protected class Block implements Comparable<Block> {
		
		private final int index;
		private final int size;
		
		/**
		 * Constructor for Block object. Only constructable by Memory.
		 * @param index
		 * @param size
		 */
		private Block(int index, int size) {
			this.index = index;
			this.size = size;
		}
		
		/**
		 * Returns the first index of the free block in memory
		 * @return first free index of block
		 */
		public int getIndex() {
			return index;
		}
		
		/**
		 * Returns the size of the free block in memory
		 * @return size of free block
		 */
		public int getSize() {
			return size;
		}
		
		/**
		 * Makes Blocks comparable by index, sorting lowest index first.
		 * Note that Memory.getFreeBlocks() returns an array that is already sorted by index.
		 */
		@Override
		public int compareTo(Block other) {
			if (this.index > other.index) return 1;
			else if (this.index == other.index) return 0;
			else return -1;
		}
	}
}
