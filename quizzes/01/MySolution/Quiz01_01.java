//
// HX: 20 points
//
import Library.FnA1sz.*;
// Please see Library/FnA1sz for FnA1sz.java
public class Quiz01_01 {
    public static
	<T extends Comparable<T>>
	int FnA1szBinarySearch(FnA1sz<T> A, T key) {
	// HX-2025-10-12:
	// Please implement binary search on a sorted functional array (FnA1sz)
	// that returns the largest index i such that key >= A[i] if such i exists,
	// or the method returns -1. The comparison function should be the compareTo
	// method implemented by the class T.

	int low = 0;
	int high = A.length() - 1;

	int result = -1;
	while (low <= high) {
		int middle = (high + low) / 2;
		if (key.compareTo(A.getAt(middle)) >= 0) {
			result = middle;
			low = middle + 1;
		}
		else {
			high = middle - 1;
		}
	}
	return result;
    }
    public static void main (String[] args) {
		
		Integer[] arr1 = {1, 2, 3, 4, 5, 6, 7};
		FnA1sz<Integer> list = new FnA1sz<Integer>(arr1);


		Integer x = 5;
		System.out.print("Highest Index of " + x + " in "); // At Index 4
		list.System$out$print();
		System.out.println(":");
		System.out.print(FnA1szBinarySearch(list, x));

		System.out.println("\n");

		Integer[] arr2 = {2, 4, 5, 5, 8, 9, 10};
		list = new FnA1sz<Integer>(arr2);


		x = 5;
		System.out.print("Highest Index of " + x + " in "); // At Index 3
		list.System$out$print();
		System.out.println(":");
		System.out.print(FnA1szBinarySearch(list, x));
    }
}
