//
// HX-2025-11-19: 50 points
//
// This question tests your understanding
// of recursion and time analysis involving
// recursion.
// Given a sequence xs, a subsequence of xs
// can be represented as a list of integers
// (representing indices). For instance, given
// xs = "Hello", (0, 2, 4) refers to the subeqence
// "Hlo" (since xs[0] = 'H', xs[2] = 'l', and
// xs[4] = 'o'); (0, 3, 4) also refers to "Hlo".
// The subsequece (0, 2, 4) is to the left of
// the subsequece (0, 3, 4) as (0, 2, 4) is less
// than (0, 3, 4) according to the lexicographic
// ordering.
//
// Here you are asked to implement a function that
// finds the longest leftmost ascending subsequence
// of a given sequence.
// For instance, suppose xs = [1,2,1,2,3,1,2,3,4],
// the longest leftmost ascending subsequence of xs
// is represented by (0, 1, 3, 4, 7, 8) (which refers
// to [1,2,2,3,3,4] in xs).
//
// In order to receive 50 points, your implementation
// should be quadratic time, that is, O(n^2) time and
// you MUST give a brief explanation as to why it is so.
// Otherwise, a working solution receives at most 60%, that
// is, 30 points out of 50 points.
//
import MyLibrary.FnList.*;
import MyLibrary.FnA1sz.*;


public class Quiz02_01 {
    public static
	<T extends Comparable<T>>
	FnList<Integer> FnA1szLongestMonoSubsequence(FnA1sz<T> xs) {
	// HX-2025-11-19:
	// This method finds the leftmost longest ascending subsequence
	// of xs. Note that the returned list consists of the indices of
	// the elements of the subsequence.
	
	int n = xs.length();
	if (n == 0) return new FnList<>();
	
	int[] dp = new int[n];
	int[] prev = new int[n];
	
	for (int i = 0; i < n; i++) {
	    dp[i] = 1;
	    prev[i] = -1;
	}

	for (int i = 1; i < n; i++) {
	    for (int j = 0; j < i; j++) {
		if (xs.getAt(j).compareTo(xs.getAt(i)) < 0) {
		    if (dp[j] + 1 > dp[i]) {
			dp[i] = dp[j] + 1;
			prev[i] = j;
		    }
		}
	    }
	}

	int maxLength = 0;
	int lastIndex = -1;
	for (int i = 0; i < n; i++) {
	    if (dp[i] > maxLength) {
		maxLength = dp[i];
		lastIndex = i;
	    }
	}

	int[] resultArray = new int[maxLength];
	int idx = maxLength - 1;
	int current = lastIndex;
	while (current != -1) {
	    resultArray[idx--] = current;
	    current = prev[current];
	}

	FnList<Integer> result = new FnList<>();
	for (int i = maxLength - 1; i >= 0; i--) {
	    result = new FnList<>(resultArray[i], result);
	}

	return result;
    }

    public static void main(String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for FnA1szLongestMonoSubsequence

	FnList<Integer> list = new FnList<>(1, new FnList<>(2, new FnList<>(1, new FnList<>(2, new FnList<>(3, new FnList<>(1, new FnList<>(2, new FnList<>(3, new FnList<>(4, new FnList<>())))))))));
	FnA1sz<Integer> xs = new FnA1sz<>(list);

	FnList<Integer> result = FnA1szLongestMonoSubsequence(xs);
	
	System.out.print("Indices: ");
	result.foritm((index) -> System.out.print(index + " "));
	System.out.println();
	
	System.out.print("Values: ");
	result.foritm((index) -> System.out.print(xs.getAt(index) + " "));
	System.out.println();

	return /*void */;
    }
}