package Swapping;

import java.util.ArrayList;

public class Memory {
	
	private int[] memory;
	
	public Memory() {
		this.memory = new int[100];
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
	 * Swaps a process into memory
	 * @param process process to swap into memory
	 * @param block free block to swap the process into
	 */
	public void swapProcess(Process process, Block block) {
		// check for mistake
		if (process.getSize() > block.size) throw new RuntimeException("Tried to assign a process of size "
				+ process.getSize() +" to a free block of size " + block.size);
		
		// add to memory
		for (int i = block.index; i < block.index + process.getSize(); i++) {
			memory[i] = process.getProcessNumber();
		}
	}
	
	/**
	 * Removes a process from memory
	 * @param process process to remove from memory
	 */
	public void removeProcess(Process process) {
		for (int i = 0; i < memory.length; i++) {
			if (memory[i] == process.getProcessNumber()) {
				memory[i] = 0;
			}
		}
	}
	
	public String toString() {
		String str = "[";
		for (int mb : memory) {
			str += mb + ", ";
		}
		return str.substring(0, str.length() - 2) + "]";
	}
	
	/**
	 * Inner class Block, represents a free block of space in memory.
	 * @author jludeman
	 */
	protected class Block implements Comparable<Block> {
		
		private int index;
		private int size;
		
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
