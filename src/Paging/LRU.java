package Paging;

import java.util.ArrayList;
import java.util.function.Predicate;

public class LRU implements Algorithm {

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
		
		
		ArrayList<Page> memoryCopy = memory;
		memoryCopy.sort(Page.compareByLastUsed());
		int evictPage = memoryCopy.get(0).getPageId();
		
		memory.removeIf(new Predicate<Page>() {
			@Override
			public boolean test(Page p1) {
				return p1.getPageId() == evictPage;
			}
			
		});
		
		memory.add(newPage);
		return evictPage;
	}

}
