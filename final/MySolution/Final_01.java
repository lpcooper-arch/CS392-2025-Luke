/*
// HX: 20 points for Final_01
// A word consists of a sequence of
// letters ([a-z]+[A-Z]) plus aprostrophe (')
// And words are separated by non-letters-aprostrophe
// (such as blanks, punctuations, etc.) in pg2701.txt.
*/

import MyFinalLib.FnList.*;
import MyFinalLib.LnStrm.*;

public class Final_01 {

	private static boolean wordCharq(char c) {
		return ('a' <= c && c <= 'z')
			|| ('A' <= c && c <= 'Z')
			|| c == '\'';
	}

	private static char lower(char c) {
		if ('A' <= c && c <= 'Z') {
			return (char)(c - 'A' + 'a');
		}
		if (c == '\u2019' || c == '\u2018') {
			return '\'';
		}
		return c;
	}

	private static class WordRest {
		FnList<Character> word;
		LnStrm<Character> rest;
		WordRest(FnList<Character> w, LnStrm<Character> r) {
			word = w; rest = r;
		}
	}

	private static WordRest readWord(LnStrm<Character> cs, FnList<Character> acc) {
		LnStcn<Character> st = cs.eval0();
		if (st.nilq()) {
			return new WordRest(
				acc.reverse(),
				new LnStrm<>(() -> new LnStcn<>())
			);
		}

		char c = lower(st.head);

		if (wordCharq(c)) {
			return readWord(
				st.tail,
				new FnList<>(c, acc)
			);
		} else {
			return new WordRest(acc.reverse(), st.tail);
		}
	}

	private static LnStrm<FnList<Character>> wordsFromChars(LnStrm<Character> cs) {
		return new LnStrm<>(() -> {
			LnStcn<Character> st = cs.eval0();
			if (st.nilq()) {
				return new LnStcn<>();
			}
			char c = lower(st.head);
			if (!wordCharq(c) || c == '\'') {
				return wordsFromChars(st.tail).eval0();
			}
			WordRest wr =
				readWord(
					st.tail,
					new FnList<>(c, new FnList<>())
				);
			return new LnStcn<>(wr.word, wordsFromChars(wr.rest));
		});
	}

	static LnStrm<FnList<Character>> pg2701_word$strmize() {
	// HX-2025-12-16:
	// Please construct a stream of words contained in the
	// file Data/pg2701.txt
	// Note that a word is represented as a list of characters
	// Also every upper case letter in the original text should
	// be turned into its corresponding lower case.
	// This stream should be built on top of pg2701_char$strmize
	// which is already implemented in Final_00.
	// In particular, you should NOT use Java library function
	// for processing files!
		return wordsFromChars(Final_00.pg2701_char$strmize());
	}

	public static void main (String[] args) {
	// HX-2025-12-16:
	// Please write minimal testing code for pg2701_word$strmize()

		LnStrm<FnList<Character>> ws = pg2701_word$strmize();
		ws.foritm0(word -> {
			word.foritm(ch -> System.out.print(ch));
			System.out.println();
		});
		return /*void*/;
	}
}

// Go to final/MySolution directory and then run:
// javac -cp ".;.." Final_01.java
// java -cp ".;.." Final_01