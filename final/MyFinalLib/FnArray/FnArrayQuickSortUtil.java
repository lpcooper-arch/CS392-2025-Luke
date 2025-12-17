package MyFinalLib.FnArray;


import java.util.function.ToIntBiFunction;

public class FnArrayQuickSortUtil {
    public static <T> void arrayQuickSort(FnArray<T> A, ToIntBiFunction<T,T> cmp) {
	// Please implement standard array-based quickSort and make sure
	// that equal elements are properly handled. In particular, your
	// testing code should test your implementation on an array of 1M zeros!
		quickSort(A, 0, A.length() - 1, cmp);
    }

	private static <T> void quickSort(FnArray<T> A, int low, int high, ToIntBiFunction<T, T> cmp) {
		if (low < high) {
			int[] partition = partition(A, low, high, cmp);
		


			quickSort(A, low, partition[0] - 1, cmp);
			quickSort(A, partition[1] + 1, high, cmp);
	
		}
	}


	private static <T> int[] partition(FnArray<T> A, int low, int high, ToIntBiFunction<T, T> cmp) {
		int pivotIndex = (int) (Math.random() * (high - low + 1)) + low;
		T pivot = A.sub(pivotIndex);

		swap(A, pivotIndex, high);

		int i = low - 1;
		int j = high;
		int k = low;

		while (k < j) {
			int comparason = cmp.applyAsInt(A.sub(k), pivot);
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


	private static <T> void swap(FnArray<T> A, int i, int j) {
		T temp = A.root[i];
		A.root[i] = A.root[j];
		A.root[j] = temp;
	}

}

// based on array quicksort made in Assign06_03 (but modified to work for FnArray)
