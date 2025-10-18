//
// HX: 40 points
//
import Library.LnList.*;
// Please see Library/LnList for LnList.java
public class Quiz01_04 {


	public static <T extends Comparable<T>> LnList<T> orderedInsert(LnList<T> sortedList, LnList<T> node) {
		if (sortedList.nilq1()) {
			return node;
		}

		T nodeVal = node.hd1();
		T sortedListHd = sortedList.hd1();

		if (nodeVal.compareTo(sortedListHd) <= 0) {
			node.link(sortedList);
			return node;
		} else {
			LnList<T> current = sortedList;
			LnList<T> next = current.tl1();


			while (!next.nilq1() && next.hd1().compareTo(nodeVal) < 0) {
			current = next;
			next = current.tl1();
			}

			node.link(next);
			current.unlink();
			current.link(node);

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
		

	LnList<T> sorted = xs.unlink();
	xs = xs.tl1();

	while (xs.consq1()) {
		LnList<T> next = xs.tl1();
		LnList<T> node = xs.unlink();
		sorted = orderedInsert(sorted, node);
		xs = next;
	}

	return sorted;

    }
    public static void main (String[] args) {
	// HX-2025-10-12:
	// Please write minimal testing code for LnListInsertSort

		LnList<Integer> list = new LnList<>();
		
		list = new LnList<>(4, list);
		list = new LnList<>(5, list);
		list = new LnList<>(3, list);
		list = new LnList<>(7, list);
		list = new LnList<>(7, list);
		list = new LnList<>(2, list);
		
		System.out.print("Original: ");
		list.System$out$print1();

		System.out.print("\nSorted: ");
		LnListInsertSort(list).System$out$print1();
    }
}
