/*
// HX: 50 points for Final_03
// HX: This one tests your hash map implementation
// In Final_02, pg2701_word$count$listize2() is implemented
// to list words in pg2701.txt according their frequencies.
// In Final_03, you are asked to implement the same functionality
// with a different approach.
*/

import MyFinalLib.FnList.*;
import MyFinalLib.FnTuple.*;
import MyFinalLib.LnStrm.*;
import MyFinalLib.MyMap00.*;
import java.util.function.ToIntBiFunction;

public class Final_03 {

	private static String wordToString(FnList<Character> word) {
		StringBuilder sb = new StringBuilder();
		word.foritm(ch -> sb.append(ch));
		return sb.toString();
	}
	
	private static FnList<Character> stringToWord(String str) {
		FnList<Character> word = FnListSUtil.nil();
		for (int i = str.length() - 1; i >= 0; i--) {
			word = FnListSUtil.cons(str.charAt(i), word);
		}
		return word;
	}

    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize3() {
	// HX-2025-12-15:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Then use the hash map implemented in Assign08_02 (open addressing)
	//    to count the number of occurrences of each word in the stream of words
	// 3. Then figure out a way to turn the hash map into a list WNS (FnList) of
	//    word-count pairs
	// 4. Use the mergesort (mergeSort) in Assign05_01 to sort WNS using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 5. The sorted WNS is the return value of pg2701_word$count$listize3()


	// Step 1.
		LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();


	// Step 2.
		MyHashMapQuadraticProbing<Integer> wordCount = new MyHashMapQuadraticProbing<>(20000);
		
		wordStream.foritm0(word -> {
			String key = wordToString(word);
			wordCount.insert$raw(key, 1);
		});


	// Step 3.
		final FnList[] WNSHolder = new FnList[]{FnListSUtil.nil()};

		LnStrm<FnTupl2<String, FnList<Integer>>> entries = wordCount.strmize();

		entries.foritm0(entry -> {
			String key = entry.s0();
			FnList<Integer> values = entry.s1();
			int count = values.length();

			FnList<Character> word = stringToWord(key);

			WNSHolder[0] = FnListSUtil.cons(new FnTupl2<>(word, count), WNSHolder[0]);
		});

		FnList<FnTupl2<FnList<Character>, Integer>> WNS = WNSHolder[0];


	// Step 4.
		ToIntBiFunction<FnList<Character>, FnList<Character>> cmpWord = (w1, w2) -> {
		FnList<Character> a = w1;
		FnList<Character> b = w2;
		while (!a.nilq() && !b.nilq()) {
			char c1 = a.hd();
			char c2 = b.hd();
			if (c1 != c2) return c1 - c2;
			a = a.tl();
			b = b.tl();
		}
		return a.length() - b.length();
	};
		
		ToIntBiFunction<FnTupl2<FnList<Character>, Integer>, FnTupl2<FnList<Character>, Integer>> cmpPair = 
			(p1, p2) -> {
				int n1 = p1.s1(), n2 = p2.s1();
				if (n1 != n2) return n2 - n1;
				return cmpWord.applyAsInt(p1.s0(), p2.s0());
			};
		
		WNS = FnListSortUtil.mergeSort(WNS, cmpPair);


	// Step 5.
		return WNS;

    }
    public static void main (String[] args) {
	// HX-2025-12-16:
	// Please write minimal testing code for pg2701_word$count$listize3()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.

		FnList<FnTupl2<FnList<Character>, Integer>> wordCounts = pg2701_word$count$listize3();
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
// javac -cp ".;.." Final_03.java
// java -cp ".;.." Final_03