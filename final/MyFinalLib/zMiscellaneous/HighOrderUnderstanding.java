package MyFinalLib.zMiscellaneous;

import MyFinalLib.FnList.*;
import java.util.function.Consumer;
//
// HX: 30 points
// This one tests your understanding of higher-order
// methods. Trying to construct a consumer of consumers
// (of the type Consumer<Consumer<Character>>) can help
// you understand the meaning of this one.
//
public class HighOrderUnderstanding {


	// Class so that I can reassign cs in charConsumer
	public static class Holder<T> {
		public FnList<T> cs;

		public Holder(FnList<T> list) {
			this.cs = list;
		} 
	}
    public static
	FnList<Character>
	thirdOrderFun
	(Consumer<Consumer<Character>> ffcs) {
	// HX: Given a consumer of consumers of characters,
	// thirdOrderFun returns a string cs.
	// Given fcs = (ch) -> System.out.print(ch),
	// which is of the type Consumer<Character>,
	// ffcs.accept(fcs) and cs.foritm(fcs) should behave
	// the same.

	Holder<Character> holder = new Holder<>(new FnList<>());
	
	Consumer<Character> charConsumer = (Character ch) -> {
		holder.cs = new FnList<Character> (ch, holder.cs);
	};

	ffcs.accept(charConsumer);

	return holder.cs.reverse();
    }
    public static void main (String[] args) {
		Consumer<Consumer<Character>> ffcs = f -> {
			f.accept('H');
			f.accept('E');
			f.accept('L');
			f.accept('L');
			f.accept('O');
			f.accept('!');
		};

		FnList<Character> cs = thirdOrderFun(ffcs);
		cs.System$out$print();
    }
}


// contians Quiz01_02