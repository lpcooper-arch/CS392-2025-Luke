//
// HX: 30 points
// This one may seem easy but can be time-consuming
// if you use a brute-force approach.
//
public class Quiz01_03 {


	// Helper Class that contains a pair of objects, first and second, so that operations can easily be performed on them
	public static class Pair<T extends Comparable<T>> {
		public T first;
		public T second;

		public Pair(T first, T second) {
			this.first = first;
			this.second = second;
		}

		public void sortPair() {
			if (first.compareTo(second) > 0) {
				T temp = first;
				first = second;
				second = temp;
			}
		}
	}


	public static class Box<T extends Comparable<T>> {
		public T value;
		public Box(T value) {
			this.value = value;
		}
	}

	public static <T extends Comparable<T>> void sortTwoBoxes(Pair<T> pair, Box<T> firstBox, Box<T> secondBox) {
		pair.first = firstBox.value;
		pair.second = secondBox.value;

		pair.sortPair();

		firstBox.value = pair.first;
		secondBox.value = pair.second;
	}

    public static
	<T extends Comparable<T>>
	T[] sort10WithNoRecursion
	(T x0, T x1, T x2, T x3, T x4, T x5, T x6, T x7, T x8, T x9) {
	// HX-2025-10-12:
	// Given 10 arguments,
	// please return an array of size 10 containing the
	// 10 arguments sorted according to the order implemented by
	// compareTo on T.
	// HX: No arrays, lists, etc.
	// HX: No recursion is allowed for this one
	// HX: No loops (either while-loop or for-loop) is allowed.
	// HX: Yes, you can use functions (but not recursive functions)
	// HX: Please do not try to write a HUGH if-then-else mumble jumble!

	Box<T> b0 = new Box<T>(x0);
	Box<T> b1 = new Box<T>(x1);
	Box<T> b2 = new Box<T>(x2);
	Box<T> b3 = new Box<T>(x3);
	Box<T> b4 = new Box<T>(x4);
	Box<T> b5 = new Box<T>(x5);
	Box<T> b6 = new Box<T>(x6);
	Box<T> b7 = new Box<T>(x7);
	Box<T> b8 = new Box<T>(x8);
	Box<T> b9 = new Box<T>(x9);

	Pair<T> pair = new Pair<T>(null, null);

		sortTwoBoxes(pair, b0, b1);
		sortTwoBoxes(pair, b2, b3);
		sortTwoBoxes(pair, b4, b5);
		sortTwoBoxes(pair, b6, b7);
		sortTwoBoxes(pair, b8, b9);
		sortTwoBoxes(pair, b0, b2);
		sortTwoBoxes(pair, b1, b3);
		sortTwoBoxes(pair, b4, b6);
		sortTwoBoxes(pair, b5, b7);
		sortTwoBoxes(pair, b0, b4);
		sortTwoBoxes(pair, b1, b5);
		sortTwoBoxes(pair, b2, b6);
		sortTwoBoxes(pair, b3, b7);
		sortTwoBoxes(pair, b0, b8);
		sortTwoBoxes(pair, b1, b9);
		sortTwoBoxes(pair, b2, b4);
		sortTwoBoxes(pair, b3, b5);
		sortTwoBoxes(pair, b6, b8);
		sortTwoBoxes(pair, b7, b9);
		sortTwoBoxes(pair, b1, b2);
		sortTwoBoxes(pair, b3, b4);
		sortTwoBoxes(pair, b5, b6);
		sortTwoBoxes(pair, b7, b8);
		sortTwoBoxes(pair, b1, b3);
		sortTwoBoxes(pair, b4, b6);
		sortTwoBoxes(pair, b2, b5);
		sortTwoBoxes(pair, b3, b4);
		sortTwoBoxes(pair, b6, b7);


	T[] result = (T[]) new Comparable[] {
		b0.value, b1.value, b2.value, b3.value, b4.value, b5.value, b6.value, b7.value, b8.value, b9.value
	};

	return result;
	}
}
 