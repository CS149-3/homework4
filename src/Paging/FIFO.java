package Paging;

import java.util.ArrayList;

public class FIFO implements Algorithm {
	
	@Override
	public Integer pageReferenced(int page, ArrayList<Page> disk, ArrayList<Page> memory) {
		Page newPage;
		if(memory.contains(newPage = disk.get(page))){
			return null;
		}
		
		if(memory.size() < 4){
			memory.add(newPage);
			return null;
		}
		
		int evictPage = memory.remove(0).getPageId();
		
		memory.add(newPage);
		return evictPage;
	}
	

}
