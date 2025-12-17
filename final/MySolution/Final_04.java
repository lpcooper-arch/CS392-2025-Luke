/*
// HX: 50 points for Final_04
// HX: This one tests your RBST implementation done in Quiz02_06.
// In Final_02, pg2701_word$count$listize1() is implemented
// to list words in pg2701.txt according their frequencies.
// In Final_04, you are asked to implement the same functionality
// with a different approach.
*/

import MyFinalLib.FnList.*;
import MyFinalLib.FnTuple.*;
import MyFinalLib.LnStrm.*;
import MyFinalLib.MyBST.*;
import java.util.function.ToIntBiFunction;


public class Final_04 {

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


    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize4() {
	// HX-2025-12-15:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Then use the RBST implemented in Quiz02_06 to count the number of
	//    occurrences of each word in the stream of words.
	//    Note that you need to modify your Quiz02_06 implementation to turn
	//    it into an generic associative map for this part.
	// 3. Then figure out a way to turn the RBST-based map into a list WNS
	//    (FnList) of word-count pairs
	// 4. Use the mergesort (mergeSort) in Assign05_01 to sort WNS using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 5. The sorted WNS is the return value of pg2701_word$count$listize4()

	// Step 1.
		LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();


	// Step 2.
		MyBST<String, Integer> wordCount = new MyBST<>();
		
		wordStream.foritm0(word -> {
			String key = wordToString(word);
			Integer currentCount = wordCount.search(key);
			if (currentCount == null) {
				wordCount.insert(key, 1);
			} else {
				wordCount.insert(key, currentCount + 1);
			}
		});


	// Step 3.
		final FnList<FnTupl2<FnList<Character>, Integer>>[] WNSArray = new FnList[1];
		WNSArray[0] = FnListSUtil.nil();
		
		wordCount.inorderTraverse((key, count) -> {
			FnList<Character> word = stringToWord(key);
			WNSArray[0] = FnListSUtil.cons(new FnTupl2<>(word, count), WNSArray[0]);
		});
		
		FnList<FnTupl2<FnList<Character>, Integer>> WNS = WNSArray[0];


	// Step 4.
		ToIntBiFunction<FnList<Character>, FnList<Character>> cmpWord = (w1, w2) -> {
			int n1 = w1.length(), n2 = w2.length();
			int i = 0;
			while (i < n1 && i < n2) {
				char c1 = w1.hd(); w1 = w1.tl();
				char c2 = w2.hd(); w2 = w2.tl();
				if (c1 != c2) return c1 - c2;
				i++;
			}
			return n1 - n2;
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
	// Please write minimal testing code for pg2701_word$count$listize4()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.

		FnList<FnTupl2<FnList<Character>, Integer>> wordCounts = pg2701_word$count$listize4();
		final int[] count = new int[]{0};
		
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
// javac -cp ".;.." Final_04.java
// java -cp ".;.." Final_04