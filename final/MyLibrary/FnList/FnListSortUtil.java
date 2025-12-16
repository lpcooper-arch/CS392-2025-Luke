package MyLibrary.FnList;

import java.util.function.ToIntBiFunction;

public class FnListSortUtil {
    
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



    public static <T extends Comparable<T>> FnList<T> insertSort(FnList<T> xs) {
		return insertSort(xs, (x1, x2) -> x1.compareTo(x2));
    }
//
    public static<T> FnList<T> insertSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
		FnList<T> sortedNums = FnListSUtil.nil();

		// Sorts / inserts xs one element at a time into sortedNums
		while (!xs.nilq()) {
			T x0 = xs.hd();
			xs = xs.tl();
			sortedNums = insert(sortedNums, x0, cmp);
		}
		
		return sortedNums;
    }

	// Inserts an element (x0) into a sorted list (sortedNums)
	private static <T> FnList<T> insert(FnList<T> sortedNums, T x0, ToIntBiFunction<T,T> cmp) {
		FnList<T> left = FnListSUtil.nil(); // FnList that will contain all numbers before the insertion of x0 in sorted nums
		while (!sortedNums.nilq()) {
			T head = sortedNums.hd();
			if (cmp.applyAsInt(head, x0) <= 0) {
				left = FnListSUtil.cons(head, left);
				sortedNums = sortedNums.tl();
			} else {
				break;
			}
		}

		FnList<T> result = FnListSUtil.cons(x0, sortedNums);


		// Merges together left and sortedNums with x0 properly inserted
		while (!left.nilq()) {
			result = FnListSUtil.cons(left.hd(), result);
			left = left.tl();
		}

		return result;
	}
}

// contains the FnList merge and insert sorts coded in Assign05