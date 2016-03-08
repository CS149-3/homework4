package Paging;

import java.util.ArrayList;
import java.util.Comparator;

public class Page {
	
	private static int pageCount = 0;
	
	private int pageId;
	private int cycleAdded;
	private int lastUsed;
	private int timesUsed;
	
	/**
	 * Holds data related to a page, including page id, most recent cycle added to memory, last cycle used, and times used.
	 */
	public Page() {
		pageId = Page.pageCount++;
		cycleAdded = 0;
		lastUsed = 0;
		timesUsed = 0;
	}
	
	/**
	 * Copy constructor
	 * @param parent parent page to make a copy of
	 */
	public Page(Page parent) {
		this.pageId = parent.pageId;
		this.cycleAdded = parent.cycleAdded;
		this.lastUsed = parent.lastUsed;
		this.timesUsed = parent.timesUsed;
	}
	
	/**
	 * Notify the page that it was referenced. Updated lastUsed and increments timesUsed. If the page was not already
	 * in memory, it also updates cycleAdded.
	 * @param cycleReferenced the cycle on which this was referenced (the loop variable)
	 * @param memory the memory array, used to check if the page is already in memory
	 */
	public void referenced(int cycleReferenced, ArrayList<Page> memory) {
		this.lastUsed = cycleReferenced;
		this.timesUsed++;
		if (!memory.contains(this))
			this.cycleAdded = cycleReferenced;
	}
	
	/**
	 * Get the id number of the page
	 * @return pageId
	 */
	public int getPageId() {
		return pageId;
	}
	
	/**
	 * Get the cycle on which this process was added to memory.
	 * @return cycleAdded
	 */
	public int getCycleAdded() {
		return cycleAdded;
	}
	
	/**
	 * Get the last time the process was used (the lower the time, the earlier it was used).
	 * @return lastUsed
	 */
	public int getLastUsed() {
		return lastUsed;
	}
	
	/**
	 * Get the number of times the page has been used.
	 * @return timesUsed
	 */
	public int getTimesUsed() {
		return timesUsed;
	}
	
	public String toString() {
		return "" + this.pageId;
	}
	
	/**
	 * Returns a comparator to compare by when a page was added. Sorts by first added page to last added page.
	 * @return comparator a comparator to compare by cycle.
	 */
	public static Comparator<Page> compareByCycle() {
		return new Comparator<Page>() {
			
			@Override
			public int compare(Page p1, Page p2) {
				if (p1.getCycleAdded() > p2.getCycleAdded()) return 1;
				else if (p1.getCycleAdded() == p2.getCycleAdded()) return 0;
				else return -1;
			}
		};
	}
	
	/**
	 * Returns a comparator to compare by when a page was last used. Sorts least recently used first.
	 * @return comparator a comparator to compare by cycle.
	 */
	public static Comparator<Page> compareByLastUsed() {
		return new Comparator<Page>() {
			
			@Override
			public int compare(Page p1, Page p2) {
				if (p1.getLastUsed() > p2.getLastUsed()) return 1;
				else if (p1.getLastUsed() == p2.getLastUsed()) return 0;
				else return -1;
			}
		};
	}
	
	/**
	 * Returns a comparator to compare by how often a page was used. Sorts by least used page first.
	 * @return comparator a comparator to compare by cycle.
	 */
	public static Comparator<Page> compareByLeastFrequent() {
		return new Comparator<Page>() {
			
			@Override
			public int compare(Page p1, Page p2) {
				if (p1.getTimesUsed() > p2.getTimesUsed()) return 1;
				else if (p1.getTimesUsed() == p2.getTimesUsed()) return 0;
				else return -1;
			}
		};
	}
	
	/**
	 * Returns a comparator to compare by how often a page was used. Sorts by most used page first.
	 * @return comparator a comparator to compare by cycle.
	 */
	public static Comparator<Page> compareByMostFrequent() {
		return new Comparator<Page>() {
			
			@Override
			public int compare(Page p1, Page p2) {
				if (p1.getTimesUsed() > p2.getTimesUsed()) return -1;
				else if (p1.getTimesUsed() == p2.getTimesUsed()) return 0;
				else return 1;
			}
		};
	}
}
