package Paging;

import java.util.ArrayList;

public interface Algorithm {
	public Integer pageReferenced(int page, ArrayList<Page> disk, ArrayList<Page> memory);
}
