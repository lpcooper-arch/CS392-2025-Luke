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
		i ++;
	}
	return null;
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


	LnList<T> left = new LnList<>();
	LnList<T> middle = new LnList<>();
	LnList<T> right = new LnList<>();


	while (!xs.nilq1()) {
		T current = xs.hd1();
		xs = xs.tl1();

		int comparison = current.compareTo(pivot);
		
		if (comparison > 0) {
			right = new LnList<T>(current, right);
		} else if (comparison < 0) {
			left = new LnList<T>(current, left);
		} else {
			middle = new LnList<T>(current, middle);
		}
	}

	LnList<T> sortedLeft = LnListQuickSort(left);
	LnList<T> sortedRight = LnListQuickSort(right);

	LnList<T> result = sortedLeft;

	result.append0(middle);
	result.append0(sortedRight);

	return result;
    }
    public static void main (String[] args) {
	// HX-2025-10-12:
	// Please write minimal testing code for LnListQuickSort

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
	LnListQuickSort(list).System$out$print1();
    }
}
