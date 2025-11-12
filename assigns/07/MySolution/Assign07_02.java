import MyLibrary.LnStrm.*;
import MyLibrary.FnList.*;

import MyLibrary.FnGtree.*;

class UnsupportedOpr
    extends RuntimeException {
    String opr;
    public UnsupportedOpr(String opr) {
	this.opr = opr;
    }
}

abstract class Term {
    public String tag = "Term";
    public abstract double eval();
    // eval() returns the value of the term
}

class TermInt extends Term {
    public int val;
    public TermInt(int val) {
	this.tag = "TermInt"; this.val = val;
    }
    public double eval() { return val; }

	public String toString() {
		return String.valueOf(val);
	}
}

class TermOpr extends Term {
    public String opr;
    public Term arg1, arg2;
    public TermOpr(String opr0, Term arg1, Term arg2) {
	this.tag = "TermOpr";
	this.opr = opr0; this.arg1 = arg1; this.arg2 = arg2;
    }
    public double eval() {
	switch (opr) {
	  case "+":
	      return arg1.eval() + arg2.eval();
	  case "-":
	      return arg1.eval() - arg2.eval();
	  case "*":
	      return arg1.eval() * arg2.eval();
	  case "/":
	      return arg1.eval() / arg2.eval();
	}
	throw new UnsupportedOpr(     opr     );
    }

	public String toString() {
		return "(" + arg1.toString() + " " + opr + " " + arg2.toString() + ")";
	}
}

public class Assign07_02 {
//

	private static final String[] OPS = {"+", "-", "*", "/"};

	// Helper Methods:

	// This method creates (and returns) all combinations of trees from a list of terms
	private FnList<Term> makeTree(FnList<Term> terms) {
		if (!terms.tl().consq()) {
			return terms;
		}

		FnList<Term> results = new FnList<>();

		for (int i = 0; i < terms.length(); i++) {
			Term a = nth(terms, i);
			for (int j = 0; j < terms.length(); j++) {
				if (i != j) {
					Term b = nth(terms, j);

					FnList<Term> rest = remove2(terms, i, j);

					for (String op : OPS) {
						TermOpr combined = new TermOpr(op, a, b);

						try {
							double val = combined.eval();
							if (Double.isFinite(val)) {
								FnList<Term> newList = new FnList<>(combined, rest);
								results = append(results, makeTree(newList));
							}

						} catch (Exception e) {}
					}
				}
			}
		}
		return results;
	}

	private static <T> T nth(FnList<T> xs, int index) {
		int i = 0;
		while (xs.consq()) {
			if (i == index) {return xs.hd();}

			xs = xs.tl();
			i++;
		}
		throw new IndexOutOfBoundsException();
	}

	private static <T> FnList<T> remove2(FnList<T> xs, int i, int j) {
		FnList<T> result = new FnList<>();
		int k = 0;
		while (xs.consq()) {
			if (k != i && k != j) {
				result = new FnList<>(xs.hd(), result);
			}
			xs = xs.tl();
			k++;
		}
		return result.reverse();
	}

	private static <T> FnList<T> append(FnList<T> a, FnList<T> b) {
		if (a.nilq()) {return b;}
		return new FnList<>(a.hd(), append(a.tl(), b));
	}

	// Converts an FnList of terms into an FnGtree so that they can be put through BFirst & DFirst
	private FnGtree<Term> wrapAsTree(FnList<Term> terms) {
		FnList<FnGtree<Term>> children = new FnList<>();

		while (terms.consq()) {
			final Term t = terms.hd();
			FnGtree<Term> child = new FnGtree<Term>() {
				public Term value() { return t; }
				public FnList<FnGtree<Term>> children() { return new FnList<>(); }
			};
			children = new FnList<>(child, children);
			terms = terms.tl();
		}

		final FnList<FnGtree<Term>> finalChildren = children;
		return new FnGtree<Term>() {
			public Term value() { return null; }
			public FnList<FnGtree<Term>> children() { return finalChildren; }
		};
	}
	//
	// End of Helper Methods


    public LnStrm<Term> GameOf24_bfs_solve
	(int n1, int n2, int n3, int n4) {
	// Please find ALL the solutions of GameOf24
	// for the input n1, n2, n3, and n4
	// Each solution is represented as a Term
	// that evaluates to 24
	// Note that your solution should be based on
	// BFirstEnumerate implemented in Assign07_01
		FnList<Term> nums = new FnList<>(
			new TermInt(n1),
			new FnList<>(new TermInt(n2),
			new FnList<>(new TermInt(n3),
			new FnList<>(new TermInt(n4),
			new FnList<>()))));
		FnList<Term> all = makeTree(nums);
		FnGtree<Term> root = wrapAsTree(all);
		return Assign07_01.BFirstEnumerate(root).filter0(t -> t != null && Math.abs(t.eval() - 24.0) < 1e-6);
	}

    public LnStrm<Term> GameOf24_dfs_solve
	(int n1, int n2, int n3, int n4) {
	// Please find ALL the solutions of GameOf24
	// for the input n1, n2, n3, and n4
	// Note that your solution should be based on
	// DFirstEnumerate implemented in Assign07_01
		FnList<Term> nums = new FnList<>(
			new TermInt(n1),
			new FnList<>(new TermInt(n2),
			new FnList<>(new TermInt(n3),
			new FnList<>(new TermInt(n4),
			new FnList<>()))));
		FnList<Term> all = makeTree(nums);
		FnGtree<Term> root = wrapAsTree(all);
		return Assign07_01.DFirstEnumerate(root).filter0(t -> t != null && Math.abs(t.eval() - 24.0) < 1e-6);

	}
//
	// Please add minimal testing code for GameOf24_bfs_solve
	// Please add minimal testing code for GameOf24_dfs_solve
	
	//GameOf24 testing code
	public static void main(String[] args) {

		Assign07_02 solver = new Assign07_02();
		
		// Test with known solvable case (for now): 8, 3, 8, 3
		int[] parameters = {8, 3, 8, 3};
		
		System.out.print("Game Parameters: ");
		for (int i = 0; i < parameters.length - 1; i++) {
			System.out.print(parameters[i] + ", ");
		}
		System.out.println(parameters[parameters.length - 1] + "\n");

		
		FnList<Term> nums = new FnList<>(
			new TermInt(parameters[0]),
			new FnList<>(new TermInt(parameters[1]),
			new FnList<>(new TermInt(parameters[2]),
			new FnList<>(new TermInt(parameters[3]),
			new FnList<>()))));
		FnList<Term> all = solver.makeTree(nums);
		System.out.println("Total expression trees generated: " + all.length());
		
		
		int count = 0;
		FnList<Term> temp = all;
		while (temp.consq() && count < 10) {
			Term t = temp.hd();
			System.out.println(t.toString() + " = " + t.eval());
			temp = temp.tl();
			count++;
		}
		System.out.println();

		System.out.println("BFS Solutions:");
		LnStrm<Term> bfsSolutions = solver.GameOf24_bfs_solve(
			parameters[0], parameters[1], parameters[2], parameters[3]);
		final int[] bfsCount = {0};
		bfsSolutions.foritm0(t -> {
			bfsCount[0]++;
			if (bfsCount[0] <= 5) {
				System.out.println(t.toString() + " = " + t.eval());
			}
		});
		System.out.println("Total BFS solutions: " + bfsCount[0] + "\n");

		System.out.println("DFS Solutions:");
		LnStrm<Term> dfsSolutions = solver.GameOf24_dfs_solve(
			parameters[0], parameters[1], parameters[2], parameters[3]);
		final int[] dfsCount = {0};
		dfsSolutions.foritm0(t -> {
			dfsCount[0]++;
			if (dfsCount[0] <= 5) {
				System.out.println(t.toString() + " = " + t.eval());
			}
		});
		System.out.println("Total DFS solutions: " + dfsCount[0]);
	}
//
} // end of [public class Assign07_02{...}]
