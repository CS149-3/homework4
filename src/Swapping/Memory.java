package Swapping;


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
	 * Return index of next block. 
	 * @param index - Starting point of check
	 * @return Integer value of the index location of next block or 0 if checked block is last block in memory. 
	 */
	public int getNextBlock(int index){
		if(memory[index] != 0){
			try{
				while(memory[index] != 0){
					index++;
				}
			}catch(IndexOutOfBoundsException e){
				return 0;
			}
			return index;
		}
		
		try{
			while(memory[index] == 0){
				index++;
			}
		}catch(IndexOutOfBoundsException e){
				return 0;
		}
		return index;
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
		for (int i = process.getStartingIndex(); i < process.getSize(); i++) {
			memory[i] = 0;
		}
	}
	
	public String toString() {
		String str = "[";
		for (int mb : memory) {
			str += mb + ", ";
		}
		return str.substring(0, str.length() - 2) + "]";
	}
}
