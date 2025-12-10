import Library.FnList.*;
    
import java.util.function.ToIntBiFunction;

public class Assign05_02 {

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
	public static <T> FnList<T> insert(FnList<T> sortedNums, T x0, ToIntBiFunction<T,T> cmp) {
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
	
    public static void main(String[] args) {
		// Create the specific test list: 1, 0, 3, 2, 5, 4, 7, 6, 9, 8, 11, 10, ..., 999999, 999998

		FnList<Integer> unsortedNums = FnListSUtil.nil();
		for (int i = 999998; i >= 0; i -= 2) {
			unsortedNums = FnListSUtil.cons(i, unsortedNums);
			unsortedNums = FnListSUtil.cons(i + 1, unsortedNums);
		}
		
    	System.out.print("Sorting 1,000,000 numbers... ");

        FnList<Integer> sortedNums = insertSort(unsortedNums);
        System.out.print("Sorted Numbers: ");
        sortedNums.System$out$print();
    }
}
 // end of [public class Assign05_02{...}]