import MyLibrary.FnList.*;

import java.util.function.ToIntBiFunction;

public class Assign05_01 {


	// Helper class used to avoid using an array in split method (used for type safety)
    private static class FnListPair<T> {
        public final FnList<T> left;
		public final FnList<T> right;

		public FnListPair(FnList<T> left, FnList<T> right) {
			this.left = left;
			this.right = right;
		}
	}

    public static <T extends Comparable<T>> FnList<T> mergeSort(FnList<T> xs) {
        return mergeSort(xs, (x1, x2) -> x1.compareTo(x2));
    }

    public static <T> FnList<T> mergeSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
        int n = xs.length();
        if (n <= 1) {
            return xs;
        }
        // Splits into two FnLists (divide)
        FnListPair<T> halves = split(xs, n / 2);
        FnList<T> leftSorted = mergeSort(halves.left, cmp);
        FnList<T> rightSorted = mergeSort(halves.right, cmp);

		// Merges the two FnLists back together (conquer)
        return merge(leftSorted, rightSorted, cmp);
    }

    // Splits the FnList xs into two lists: first n elements and rest (returns them as a FnListPair)
    private static <T> FnListPair<T> split(FnList<T> xs, int n) {
        FnList<T> left = FnListSUtil.nil();
        FnList<T> right = xs;
        for (int i = 0; i < n; i++) {
            left = FnListSUtil.cons(right.hd(), left);
            right = right.tl();
        }
        left = FnListSUtil.reverse(left);
        return new FnListPair<>(left, right);
    }

    // Combines two sorted lists into one sorted list
    private static <T> FnList<T> merge(FnList<T> xs, FnList<T> ys, ToIntBiFunction<T, T> cmp) {
        FnList<T> result = FnListSUtil.nil();
        while (!xs.nilq() && !ys.nilq()) {

			// xh and yh are the heads of xs and ys
            T xh = xs.hd();
            T yh = ys.hd();

			// Compares xh with yh then adds the sorted element to result (in reverse order)
            if (cmp.applyAsInt(xh, yh) <= 0) {
                result = FnListSUtil.cons(xh, result);
                xs = xs.tl();
            } else {
                result = FnListSUtil.cons(yh, result);
                ys = ys.tl();
            }
        }

        while (!xs.nilq()) {
            result = FnListSUtil.cons(xs.hd(), result);
            xs = xs.tl();
        }
        while (!ys.nilq()) {
            result = FnListSUtil.cons(ys.hd(), result);
            ys = ys.tl();
        }
        // Result is built in reverse order and then reversed here
        return result.reverse();
    }

    public static void main(String[] args) {
        FnList<Integer> unsortedNums = FnListSUtil.rand$int$make(1000000);
		System.out.print("Unsorted Numbers: ");
        unsortedNums.System$out$print();
		System.out.println();

        FnList<Integer> sortedNums = mergeSort(unsortedNums);
		System.out.print("Sorted Numbers: ");
        sortedNums.System$out$print();
    }

} // end of [public class Assign05_01{...}]
