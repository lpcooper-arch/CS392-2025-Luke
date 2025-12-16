package MyLibrary.LnList;

//
// HX: 40 points
//
// Please see Library/LnList for LnList.java
public class LnListInsertSortUtil {


	public static <T extends Comparable<T>> LnList<T> orderedInsert(LnList<T> sortedList, LnList<T> node) {
		if (sortedList.nilq1()) {
			return node;
		}

		T nodeVal = node.hd1();
		T sortedListHd = sortedList.hd1();

		if (nodeVal.compareTo(sortedListHd) <= 0) {
			node.link1(sortedList);
			return node;
		} else {
			LnList<T> current = sortedList;
			LnList<T> next = current.tl1();

			while (next.consq1() && next.hd1().compareTo(nodeVal) < 0) {
				current = next;
				next = current.tl1();
			}

			current.unlink1();
			node.link1(next);
			current.link1(node);

			return sortedList;
		}
	}


	public static
	<T extends Comparable<T>>
	LnList<T> LnListInsertSort(LnList<T> xs) {
		// HX-2025-10-12:
		// Please implement (stable) insert sort on a
		// linked list (LnList).
		// Note that you are not allowed to modify the definition
		// of the LnList class. You can only use the public methods
		// provided by the LnList class
		
		if (xs.nilq1()) {
			return xs;
		}

		
		LnList<T> rest = xs.tl1();
		xs.unlink1();
		LnList<T> sorted = xs;
		xs = rest;

		while (xs.consq1()) {
			rest = xs.tl1();
			xs.unlink1();
			LnList<T> node = xs;
			sorted = orderedInsert(sorted, node);
			xs = rest;
		}

		return sorted;
	}
	
	public static void main (String[] args) {
		// HX-2025-10-12:
		// Please write minimal testing code for LnListInsertSort

		LnList<Integer> list = new LnList<>();
		
		
		for (int i = 9; i >= 0; i--) {
			int val = (int) (Math.random() * 10);
			list = new LnList<Integer>(val, list);
		}
		
		System.out.print("Original: ");
		list.System$out$print1();

		LnList<Integer> sorted = LnListInsertSort(list);
		System.out.print("\nSorted: ");
		sorted.System$out$print1();
	}
}

// contains Quiz01_04