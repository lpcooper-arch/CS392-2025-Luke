/*
// HX: 50 points for Final_02
// HX: This one tests your quicksort and mergesort
// In Final_01, pg2701_word$strmize() is implemented
// that lists all the words in pg2701.txt. Here, you
// are asked to generate FnList of pairs; each pair consists
// of a word (FnList<Character>) and a count (Integer) such that
// the count is the number of occurrences of the word in pg2701.txt.
// Note that a lower case letter is considered the same as its
// corresponding upper case. For instance, "Whale" and "whale"
// are considered the same word.
*/

import MyFinalLib.FnList.*;
import MyFinalLib.FnTuple.*;
import MyFinalLib.FnArray.*;
import java.util.function.ToIntBiFunction;
import MyFinalLib.LnStrm.*;

public class Final_02 {

	private static String wordToString(FnList<Character> word) {
		StringBuilder sb = new StringBuilder();
		word.foritm(ch -> sb.append(ch));
		return sb.toString();
	}

    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize2() {
	// HX-2025-12-15:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Turn this stream into an array A1 of words (FnList<Character>[])
	// 3. Call the quicksort (arrayQuickSort) done in Assign06_03 to sort A1
	// 4. Use sorted A1 to generate a list L2 of word-count pairs
	// 5. Use the mergesort (mergeSort) in Assign05_01 to sort L2 using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 6. The sorted L2 is the return value of pg2701_word$count$listize2()

	// Step 1.
		LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();


	// Step 2.
		java.util.List<FnList<Character>> tmpList = new java.util.ArrayList<>();
		LnStcn<FnList<Character>> stcn = wordStream.eval0();
		while (stcn.consq()) {
			tmpList.add(stcn.head);
			stcn = stcn.tail.eval0();
		}
		@SuppressWarnings("unchecked")
		FnList<Character>[] arr = (FnList<Character>[]) new FnList[tmpList.size()];
		for (int i = 0; i < tmpList.size(); i++) {
			arr[i] = tmpList.get(i);
		}
		FnArray<FnList<Character>> A1 = new FnArray<>(arr);


	// Step 3.
		ToIntBiFunction<FnList<Character>, FnList<Character>> cmpWord = (w1, w2) -> {
			int n1 = w1.length(), n2 = w2.length();
			int i = 0;
			while (i < n1 && i < n2) {
				char c1 = Character.toLowerCase(w1.hd()); w1 = w1.tl();
				char c2 = Character.toLowerCase(w2.hd()); w2 = w2.tl();
				if (c1 != c2) return c1 - c2;
				i++;
			}
			return n1 - n2;
		};
		FnArrayQuickSortUtil.arrayQuickSort(A1, cmpWord);


	// Step 4.
		FnList<FnTupl2<FnList<Character>, Integer>> L2 = FnListSUtil.nil();
		int n = A1.length();
		int i = 0;
		while (i < n) {
			FnList<Character> w = A1.sub(i);
			int count = 1;
			while (i + 1 < n && cmpWord.applyAsInt(A1.sub(i + 1), w) == 0) {
				count++; i++;
			}
			L2 = FnListSUtil.cons(new FnTupl2<>(w, count), L2);
			i++;
		}
		L2 = L2.reverse();


	// Step 5.
		ToIntBiFunction<FnTupl2<FnList<Character>, Integer>, FnTupl2<FnList<Character>, Integer>> cmpPair =
		(p1, p2) -> {
			int n1 = p1.s1(), n2 = p2.s1();
			if (n1 != n2) return n2 - n1;
			return cmpWord.applyAsInt(p1.s0(), p2.s0());
		};
		L2 = FnListSortUtil.mergeSort(L2, cmpPair);


	// Step 6.
		return L2;

    }
    
    public static void main (String[] args) {
	// HX-2025-12-16:
	// Please write minimal testing code for pg2701_word$count$listize2()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.

		FnList<FnTupl2<FnList<Character>, Integer>> wordCounts = pg2701_word$count$listize2();
		final int[] count = new int[]{0}; // Using array to allow modification inside lambda
		
		wordCounts.foritm(p -> {
			if (count[0] < 100) {
				System.out.println(wordToString(p.s0()) + " : " + p.s1());
				count[0]++;
			}
		});

	return /*void*/;
    }
}

// Go to final/MySolution directory and then run:
// javac -cp ".;.." Final_02.java
// java -cp ".;.." Final_02