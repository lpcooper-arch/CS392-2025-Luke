/*
// HX: 50 points for Final_05
// HX: This one tests your priority queue implementation
*/

import MyFinalLib.LnList.*;
import MyFinalLib.FnList.*;
import MyFinalLib.MyPQueue.*;
import java.util.function.ToIntBiFunction;
import java.util.Comparator;

public class Final_05 {

	public static<T> LnList<T>
	LnList_n$way$merge(LnList<T> xss[], ToIntBiFunction<T,T> cmp) {
		// HX: Given an array of (linear) lists (LnList), each of which is
		// ordered according to cmp, please implement a function to merge them
		// into one ordered (linear) list. Please note that you cannot create
		// new list nodes; you can only use exist nodes to form the returned
		// linear list. You are asked to use MyPQueueArray.java implemented in
		// Assigment#9 for finding the minimum of a collection of arguments.

		class Entry {
			LnList<T> xs;
			int idx;
			Entry(LnList<T> xs, int idx) {
				this.xs = xs;
				this.idx = idx;
			}
		}

		Comparator<Entry> pqCmp =
			(e1, e2) -> {
				int c = cmp.applyAsInt(e1.xs.hd1(), e2.xs.hd1());
				if (c != 0) return -c;
				return Integer.compare(e2.idx, e1.idx);
			};

		MyPQueueArray<Entry> pq =
			new MyPQueueArray<Entry>(xss.length, pqCmp);

		for (int i = 0; i < xss.length; i++) {
			if (xss[i] != null && xss[i].consq1()) {
				pq.enque$raw(new Entry(xss[i], i));
			}
		}

		LnList<T> res = null;
		LnList<T> tail = null;

		while (!pq.isEmpty()) {
			Entry e = pq.deque$raw();
			LnList<T> xs = e.xs;
			LnList<T> rest = xs.unlink1();

			if (res == null) {
				res = xs;
				tail = xs;
			} else {
				tail.link1(xs);
				tail = xs;
			}

			if (rest != null && rest.consq1()) {
				pq.enque$raw(new Entry(rest, e.idx));
			}
		}
		return res;
	}

	public static<T>
	FnList<T>
	LnList_mergeSort$5way(LnList<T> xs, ToIntBiFunction<T,T> cmp) {
		// HX: Please use LnList_n$way$merge to implement 5-way mergesort
		// on a linear list. That is, split each list evenly into 5 sublists;
		// recursely sort the 5 sublist and then use LnList_n$way$merge to merge
		// them into one sorted list.
		// Please make sure that your implementation of LnList_mergeSort$5way
		// does stable sorting!

		int n = xs.length1();
		if (n <= 1) {
			final FnList<T>[] result = new FnList[]{FnListSUtil.nil()};
			xs.foritm1(x -> result[0] = FnListSUtil.cons(x, result[0]));
			return result[0].reverse();
		}

		LnList<T>[] parts = splitIntoFive(xs);

		for (int i = 0; i < 5; i++) {
			if (parts[i] != null && parts[i].consq1()) {
				parts[i] = LnList_mergeSortLn(parts[i], cmp);
			}
		}

		LnList<T> merged = LnList_n$way$merge(parts, cmp);

		final FnList<T>[] result = new FnList[]{FnListSUtil.nil()};
		merged.foritm1(x -> result[0] = FnListSUtil.cons(x, result[0]));
		return result[0].reverse();
	}

	// private helper for LnList_mergeSort$5way
	private static<T>
	LnList<T>
	LnList_mergeSortLn(LnList<T> xs, ToIntBiFunction<T,T> cmp) {
		int n = xs.length1();
		if (n <= 1) return xs;

		LnList<T>[] parts = splitIntoFive(xs);

		for (int i = 0; i < 5; i++) {
			if (parts[i] != null && parts[i].consq1()) {
				parts[i] = LnList_mergeSortLn(parts[i], cmp);
			}
		}

		return LnList_n$way$merge(parts, cmp);
	}

	@SuppressWarnings("unchecked")
	private static<T> LnList<T>[] splitIntoFive(LnList<T> xs) {
		LnList<T>[] parts = (LnList<T>[]) new LnList[5];
		int n = xs.length1();
		int base = n / 5;
		int extra = n % 5;
		LnList<T> current = xs;

		for (int i = 0; i < 5; i++) {
			int chunk = base + (i < extra ? 1 : 0);
			if (chunk == 0 || current == null || current.nilq1()) {
				parts[i] = null;  // no new node created
				continue;
			}
			parts[i] = current;
			LnList<T> tail = current;
			for (int j = 1; j < chunk; j++) {
				tail = tail.tl1();
			}
			current = tail.unlink1();
		}

		return parts;
	}

	public static void main(String[] args) {
		// Please write some testing code that applies
		// mergeSort to parity-sort the list [0,1,2,...,999998,999999]
		// of 1000000 elements.

		int N = 1_000_000;
		LnList<Integer> xs = new LnList<>();

		for (int i = N - 1; i >= 0; i--) {
			xs = new LnList<>(i, xs);
		}

		ToIntBiFunction<Integer,Integer> parityCmp =
			(x, y) -> {
				int px = x % 2;
				int py = y % 2;
				if (px != py) return px - py;
				return x - y;
			};

		long t0 = System.currentTimeMillis();
		FnList<Integer> ys =
			LnList_mergeSort$5way(xs, parityCmp);
		long t1 = System.currentTimeMillis();

		// Print first 100 even elements
		System.out.println("First 100 elements (even):");
		final int[] count = new int[]{0};
		ys.foritm(x -> {
			if (count[0] < 100) {
				if (count[0] > 0) System.out.print(", ");
				System.out.print(x);
				count[0]++;
			}
		});
		System.out.println();

		System.out.println();

		// Print first 100 odd numbers
		System.out.println("First 100 elements (odd):");
		final int[] countOdd = new int[]{0};
		ys.foritm(x -> {
			if (x % 2 != 0 && countOdd[0] < 100) {
				if (countOdd[0] > 0) System.out.print(", ");
				System.out.print(x);
				countOdd[0]++;
			}
		});
		System.out.println();


		System.out.println("Length = " + ys.length());
		System.out.println("Time = " + (t1 - t0));
	}
}

// Go to final/MySolution directory and then run:
// javac -cp ".;.." Final_05.java
// java -cp ".;.." Final_05