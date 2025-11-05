/*

import Library.LnStrm.*;
import Library.FnTuple.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

*/
import java.util.function.ToIntBiFunction;

public class Assign06_03 {
    public static <T> void arrayQuickSort(T[] A, ToIntBiFunction<T,T> cmp) {
	// Please implement standard array-based quickSort and make sure
	// that equal elements are properly handled. In particular, your
	// testing code should test your implementation on an array of 1M zeros!
		quickSort(A, 0, A.length - 1, cmp);
    }

	private static <T> void quickSort(T[] A, int low, int high, ToIntBiFunction<T, T> cmp) {
		if (low < high) {
			int[] partition = partition(A, low, high, cmp);
		


			quickSort(A, low, partition[0] - 1, cmp);
			quickSort(A, partition[1] + 1, high, cmp);
	
		}
	}


	private static <T> int[] partition(T[] A, int low, int high, ToIntBiFunction<T, T> cmp) {
		int pivotIndex = (int) (Math.random() * (high - low + 1)) + low;
		T pivot = A[pivotIndex];

		swap(A, pivotIndex, high);

		int i = low - 1;
		int j = high;
		int k = low;

		while (k < j) {
			int comparason = cmp.applyAsInt(A[k], pivot);
			if (comparason < 0) {
				i++;
				swap(A, i, k);
				k++;

			} else if (comparason > 0) {
				j--;
				swap(A, k, j);

			} else {
				k++;

			}
		}

		swap(A, j, high);


		return new int[] {i + 1, j};
	}


	private static <T> void swap(T[] A, int i, int j) {
		T temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}


	public static void main(String[] args) {
		// Testing Code

		Integer[] zeros = new Integer[1_000_000];
		for (int i = 0; i < zeros.length; i++) {
			zeros[i] = 0;
		}

		ToIntBiFunction<Integer, Integer> cmp = (a, b) -> Integer.compare(a, b);

		// Sorts an array of all zeros to test this case
		arrayQuickSort(zeros, cmp);


		Integer[] randNums = new Integer[100];

		for (int i = 0; i < randNums.length; i++) {
			randNums[i] = ((int) (Math.random() * 100));
		}

		arrayQuickSort(randNums, cmp);
		System.out.println("Sorted Array:");
		printArray(randNums);
	}


	public static void printArray(Integer[] arr) {
		System.out.print("Integer Array: {");
		for (int i = 0; i < arr.length - 1; i++) {
			System.out.print(arr[i] + ",");
		}
		System.out.println(arr[arr.length - 1] + "}\n");
	}
} // end of [public class Assign06_03{...}]

