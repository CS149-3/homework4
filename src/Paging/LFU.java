package Paging;

import java.util.ArrayList;

public class LFU implements Algorithm {

	@Override
	public Integer pageReferenced(int page, ArrayList<Page> disk, ArrayList<Page> memory) {
		//check if page is in memory
		for(int i=0; i<memory.size(); i++){
			if(memory.get(i).getPageId() == page){
				return null;
			}
		}
		
		//if memory is < 4, put pages
		if(memory.size() < 4){
			for(int i=0; i<disk.size(); i++){
				if(disk.get(i).getPageId() == page){
					memory.add(disk.get(i));
					disk.remove(i);
				}
			}
		}

		//move least frequently used page out of memory array and back into disk
		else{
			Page leastFrequentlyUsedPage = memory.get(0);
			int indexOfMemory = 0;
			Page temp = null;
			
			for(int i=1; i<memory.size(); i++){
				if(Page.compareByLeastFrequent().compare(leastFrequentlyUsedPage, memory.get(i)) == 1){
					leastFrequentlyUsedPage=memory.get(i);
					indexOfMemory = i;
				}
			}
			
			for(int i=0; i<disk.size(); i++){
				if(disk.get(i).getPageId() == page){
					temp = disk.get(i);
					disk.remove(i);
				}
			}
			
			memory.remove(indexOfMemory);
			disk.add(leastFrequentlyUsedPage);
			memory.add(temp);
			return leastFrequentlyUsedPage.getPageId();
		}
		return null;
	}

}
