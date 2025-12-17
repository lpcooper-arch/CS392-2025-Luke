package MyFinalLib.zMiscellaneous;

//
// HX: 50 points
// Here we revisit a question on quiz01 (Quiz01_03).
// Instead of sorting 10 elements without recursion,
// you are asked to sort up to 1000 elements without
// recursion.
// Hint: Think about building a tree of commands for
// swapping array elements.
//
public class Sort1000NoRecursion {
    public static
    <T extends Comparable<T>>
    void sort1000WithNoRecursion(T[] A) {
		// HX-2025-11-20:
		// A is an array of size at most 1000.
		// Please implement a sorting algorithm
		// WITHOUT recursion that can effectively
		// sort A.
        int n = A.length;
        if (n <= 1) return;

        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[n];

        for (int size = 1; size < n; size *= 2) {
            for (int left = 0; left < n - size; left += 2 * size) {

                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);

                int i = left;
                int j = mid + 1;
                int k = left;

                for (; i <= mid && j <= right; k++) {
                    if (A[i].compareTo(A[j]) <= 0) {
                        temp[k] = A[i];
                        i++;
                    } else {
                        temp[k] = A[j];
                        j++;
                    }
                }

                for (; i <= mid; i++, k++) temp[k] = A[i];
                for (; j <= right; j++, k++) temp[k] = A[j];

                for (int t = left; t <= right; t++)
                    A[t] = temp[t];
            }
        }
    }

    public static void main (String[] args) {
		// HX-2025-11-19:
		// Please write minimal testing code for FnA1szLongestMonoSubsequence
        Integer[] xs = new Integer[1000];

		for (int i = 0; i < 1000; i++) {
			xs[i] = (int)(Math.random() * 250);
		}
		System.out.println("Unsorted Array:");

		for (int x : xs) System.out.print(x + " ");

		System.out.println("\n");

		System.out.println("Sorted Array:");
        sort1000WithNoRecursion(xs);
        for (int x : xs) System.out.print(x + " ");
        System.out.println();
		return /*void*/;
    }
}

// contains Quiz02_02