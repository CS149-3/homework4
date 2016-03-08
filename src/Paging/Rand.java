package Paging;

import java.util.ArrayList;
import java.util.Random;

public class Rand implements Algorithm {
	
	private Random random;
	
	public Rand() {
		random = new Random();
	}
	
	@Override
	public Integer pageReferenced(int page, ArrayList<Page> disk, ArrayList<Page> memory) {
			for(int i = 0; i < memory.size(); ++i)
			{
				if(memory.get(i).getPageId() == page)
				{
					return null;
				}
			}
			if(memory.size() < 4)
			{
				for (int i = 0; i < disk.size(); i++) {
					if (disk.get(i).getPageId() == page) {
						memory.add(disk.remove(i));
						return null;
					}
				}
			}
			else
			{
				for(int i = 0; i < disk.size(); ++i)
				{
					if(disk.get(i).getPageId() == page)
					{
						Page ep = memory.remove(random.nextInt(4));
						Page rp = disk.remove(i);
						disk.add(ep);
						memory.add(rp);
						
						return ep.getPageId();
					}
				}
			}
			return null;
	}

}
