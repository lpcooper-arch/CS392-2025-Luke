//
// HX: 50 points
//
import Library.LnList.*;
// Please see Library/LnList for LnList.java

public class Quiz01_05 {


	// Helper method to get an item in a linked list
	public static <T> T getAt(LnList<T> xs, int index) {
		int i = 0;

		while (!xs.nilq1()) {
			if (i == index) return xs.hd1();
			
			xs = xs.tl1();
			i++;
		}
		return null;
	}

	// Helper method to append one list to another
	public static <T> LnList<T> appendLists(LnList<T> first, LnList<T> second) {
		if (first == null || first.nilq1()) {
			return second;
		}
		if (second == null || second.nilq1()) {
			return first;
		}
		
		LnList<T> current = first;
		while (!current.tl1().nilq1()) {
			current = current.tl1();
		}
		current.link(second);
		return first;
	}


	public static
	<T extends Comparable<T>>
	LnList<T> LnListQuickSort(LnList<T> xs) {
		// HX-2025-10-12:
		// Please implement quicksort on a linked list (LnList).
		// Note that you are not allowed to modify the definition
		// of the LnList class. You can only use the public methods
		// provided by the LnList class
		

		if (xs.nilq1() || xs.tl1().nilq1()) {
			return xs;
		}

		int length = xs.length1();
		int pivotIndex = (int) (Math.random() * length);
		T pivot = getAt(xs, pivotIndex);


		LnList<T> left = null;
		LnList<T> middle = null;
		LnList<T> right = null;


		while (!xs.nilq1()) {
			T current = xs.hd1();
			LnList<T> node = xs;
			xs = node.unlink();

			int comparison = current.compareTo(pivot);
			
			if (comparison < 0) {
				left = (left == null) ? node : appendLists(left, node);
			} else if (comparison > 0) {
				right = (right == null) ? node : appendLists(right, node);
			} else {
				middle = (middle == null) ? node : appendLists(middle, node);
			}
		}

		LnList<T> sortedLeft = (left == null || left.nilq1()) ? left : LnListQuickSort(left);
		LnList<T> sortedRight = (right == null || right.nilq1()) ? right : LnListQuickSort(right);

		LnList<T> result;
		if (sortedLeft == null || sortedLeft.nilq1()) {
			result = middle;
		} else {
			result = appendLists(sortedLeft, middle);
		}
		
		if (sortedRight != null && !sortedRight.nilq1()) {
			result = appendLists(result, sortedRight);
		}

		return result;
	}
	
	public static void main (String[] args) {
		// HX-2025-10-12:
		// Please write minimal testing code for LnListQuickSort

		LnList<Integer> list = new LnList<>();
		
		for (int i = 9; i >= 0; i--) {
			int val = (int) (Math.random() * 10);
			list = new LnList<Integer>(val, list);
		}
		
		System.out.print("Original: ");
		list.System$out$print1();

		LnList<Integer> sorted = LnListQuickSort(list);
		System.out.print("\nSorted: ");
		sorted.System$out$print1();
	
	}
}