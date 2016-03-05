package Paging;

import java.util.ArrayList;

public class MFU implements Algorithm {

	public Integer pageReferenced(int page, ArrayList<Page> disk, ArrayList<Page> memory) {
		if(memory.size()<1){
			for(int i=0;i<disk.size();i++){
				if(disk.get(i).getPageId()==page){
					memory.add(disk.get(i));
					disk.remove(i);
					return page;
				}
			}
		}
		for(int i=0;i<memory.size();i++){
			if(memory.get(i).getPageId()==page){
				return null;
			}
		}
		if(memory.size()<4){
			for(int i=0;i<disk.size();i++){
				if(disk.get(i).getPageId()==page){
					memory.add(disk.get(i));
					disk.remove(i);
					return page;
				}
			}

		}
		else{
			Page mostFrequentlyUsedPage=memory.get(0);
			int indexOfMemory=0;
			Page temp=null;
			for(int i=1;i<memory.size();i++){
				if(Page.compareByMostFrequent().compare(mostFrequentlyUsedPage, memory.get(i))==1){
					mostFrequentlyUsedPage=memory.get(i);
					indexOfMemory=i;
				}
			}
			for(int i=0;i<disk.size();i++){
				if(disk.get(i).getPageId()==page){
					temp=disk.get(i);
					disk.remove(i);
				}
			}
			memory.remove(indexOfMemory);
			disk.add(mostFrequentlyUsedPage);
			memory.add(temp);
			return mostFrequentlyUsedPage.getPageId();
		}
		return null;
	}
}
