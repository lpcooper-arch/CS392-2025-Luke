package MyLibrary.zMiscellaneous;

//
// HX: 50 points
//

import MyLibrary.FnList.*;
import java.util.function.ToIntBiFunction;

abstract public class SomeSortStable {
	public static<T>
	FnList<T> someSort
	(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX-2025-10-15:
	// This one is abstract, that is, not implemented
	return FnListSUtil.quickSort(xs, cmp);
    }
    public static
	<T extends Comparable<T>>
	FnList<T> someStableSort
	(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX-2025-10-15:
	// Please implement a stable sorting method based on
	// someSort, which may not be stable
	
	FnList<Indexed<T>> decorated = decorate(xs);

		FnList<Indexed<T>> sorted = someSort(decorated, (a, b) -> {
			int r = cmp.applyAsInt(a.value, b.value);
			if (r != 0) return r;
			return Integer.compare(a.index, b.index);
		});

		return strip(sorted);
	}

	private static class Indexed<T> {
		T value;
		int index;
		Indexed(T v, int i) { value = v; index = i; }
	}


	private static <T> FnList<Indexed<T>> decorate(FnList<T> xs) {
		FnList<Indexed<T>> decorated = new FnList<>();
		int idx = 0;
		FnList<T> current = xs;
		while (!current.nilq()) {
			decorated = new FnList<>(new Indexed<>(current.hd(), idx), decorated);
			current = current.tl();
			idx++;
		}
	return decorated.reverse(); // Reverse to restore original order
	}

	private static <T> FnList<T> strip(FnList<Indexed<T>> xs) {
    FnList<T> stripped = new FnList<>();
    FnList<Indexed<T>> current = xs;
    while (!current.nilq()) {
        stripped = new FnList<>(current.hd().value, stripped);
        current = current.tl();
    }
    return stripped.reverse();
	}


    private static FnList<Integer> createLargeList(int n) {
        FnList<Integer> list = new FnList<>();
        for (int i = n - 1; i >= 0; i--) {
            list = new FnList<>(i, list);
        }
        return list;
	}


    public static void main(String[] args) {
        int n = 1_000_000;
		System.out.println("Creating list...");
        FnList<Integer> list = createLargeList(n);

        System.out.println("Sorting list with parity comparator...");
        long start = System.currentTimeMillis();

        FnList<Integer> sorted = someStableSort(list, (a, b) -> {
            int parityA = a % 2;
            int parityB = b % 2;
            if (parityA != parityB) return Integer.compare(parityA, parityB);
            return 0;
        });

        long end = System.currentTimeMillis();
        System.out.println("Sorting done in " + (end - start) + " ms");

        System.out.println("Verifying sorted list...");
        int evensCount = 0;
        int oddsCount = 0;

        FnList<Integer> current = sorted;
        while (!current.nilq()) {
            int val = current.hd();
            if (val % 2 == 0) evensCount++;
            else oddsCount++;
            current = current.tl();
        }

        System.out.println("Evens count: " + evensCount);
        System.out.println("Odds count: " + oddsCount);
    }
}

// contains Quiz01_06


////////////////////////////////////////////////////////////////////////.
//
// HX-2025-10-15:
// Please find a way to test someStableSort by
// implementing someSort as quickSort on FnList
// and then use someStableSort to parity-sort the following
// list of 1M integers:
// 0, 1, 2, 3, 4, ..., 999999
// That is, the result of parity-sorting should be:
// 0, 2, ..., 999998, 1, 3, ..., 999999
//
// Note that you should be able to call the quickSort method
// in Library/FnList/FnListSUtil.java; should not do another
// implementation of quickSort in your testing code.
////////////////////////////////////////////////////////////////////////.
