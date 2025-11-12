
import MyLibrary.FnList.*;
import MyLibrary.LnStrm.*;
import MyLibrary.FnGtree.*;

//import java.util.function.Consumer;

import MyDeque.MyDequeList;

public class Assign07_01 {
//
    public static<T> LnStrm<T>
	BFirstEnumerate(FnGtree<T> root) {
	// Please add your code here
	// You need to use your solution to Assign04_02
	// If you need to change your code for Assign04_02
	// Please detail what changes are made
		return new LnStrm<>(() -> {
			if (root == null) {return new LnStcn<>();}

			MyDequeList<FnGtree<T>> deq = new MyDequeList<>();
			deq.renque$exn(root);

			return bfsStream(deq);
		});
    }

	private static<T> LnStcn<T>
		bfsStream(MyDequeList<FnGtree<T>> deq) {
		if (deq.isEmpty()) {return new LnStcn<>();}

		FnGtree<T> node = deq.deque$exn();

		FnList<FnGtree<T>> children = node.children();
		while (children.consq()) {
			deq.renque$exn(children.hd());
			children = children.tl();
		}

		final MyDequeList<FnGtree<T>> nextDeq = deq;

		return new LnStcn<>(node.value(), new LnStrm<>(() -> 
			bfsStream(nextDeq)
		));
	}
//
    public static<T> LnStrm<T>
	DFirstEnumerate(FnGtree<T> root) {
	// Please add your code here
	// You need to use your solution to Assign04_02
	// If you need to change your code for Assign04_02
	// Please detail what changes are made

		return new LnStrm<>(() -> {

			if (root == null) {return new LnStcn<>();}

			MyDequeList<FnGtree<T>> deq = new MyDequeList<>();
			deq.fenque$exn(root);

			return dfsStream(deq);
		});
    }

	private static<T> LnStcn<T>
		dfsStream(MyDequeList<FnGtree<T>> deq) {
		if (deq.isEmpty()) {return new LnStcn<>();}

		FnGtree<T> node = deq.fdeque$exn();

		FnList<FnGtree<T>> children = node.children().reverse();
		while (children.consq()) {
			deq.fenque$exn(children.hd());
			children = children.tl();
		}

		final MyDequeList<FnGtree<T>> nextDeq = deq;

		return new LnStcn<>(node.value(), new LnStrm<>(() -> 
			dfsStream(nextDeq)
		));
	}

	// Testing code for BFirstSearch and DFirstSearch
	public static void main(String[] args) {
		/*
		 *  Structure of the Tree:
		 * 
		 * 				   A(1)
		 * 				/   |   \
		 *			  B(2) C(3) D(4)
		 * 				   / \
		 * 				E(5)  F(6)
		 * 
		 */

		FnList<FnGtree<Integer>> none = new FnList<>();
		
		FnGtree<Integer> E = new FnGtree<Integer>() {
		public Integer value() { return 5; }
		public FnList<FnGtree<Integer>> children() { return none; }
		};

		FnGtree<Integer> F = new FnGtree<Integer>() {
		public Integer value() { return 6; }
		public FnList<FnGtree<Integer>> children() { return none; }
		};

		FnGtree<Integer> B = new FnGtree<Integer>() {
		public Integer value() { return 2; }
		public FnList<FnGtree<Integer>> children() { return none; }
		};

		FnGtree<Integer> D = new FnGtree<Integer>() {
		public Integer value() { return 4; }
		public FnList<FnGtree<Integer>> children() { return none; }
		};

		
		FnList<FnGtree<Integer>> cKids =
		new FnList<>(E, new FnList<>(F, new FnList<>()));

		FnGtree<Integer> C = new FnGtree<Integer>() {
		public Integer value() { return 3; }
		public FnList<FnGtree<Integer>> children() { return cKids; }
		};

		FnList<FnGtree<Integer>> aKids =
			new FnList<>(B, new FnList<>(C, new FnList<>(D, new FnList<>())));

		FnGtree<Integer> A = new FnGtree<Integer>() {
			public Integer value() { return 1; }
			public FnList<FnGtree<Integer>> children() { return aKids; }
		};

		System.out.println("Breadth-First:");
		BFirstEnumerate(A).foritm0(System.out::println);

		System.out.println("\nDepth-First:");
		DFirstEnumerate(A).foritm0(System.out::println);
	}
//
} // end of [public class Assign07_01{...}]
