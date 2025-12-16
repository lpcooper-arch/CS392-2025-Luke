package MyLibrary.zMiscellaneous;

import MyLibrary.FnList.*;
import MyLibrary.FnTuple.*;
import MyLibrary.LnStrm.*;
import MyLibrary.FnGtree.*;

public class KnightTour {
    // HX-2025-12-02:
    // Please use Warnsdorf's rule to
    // search for knight's tours on a chess board
    // of dimension (chessBoardSize x chessBoardSize)
    // Your search should be based on the PFirstEnumerate
    // (See Code/FnGtree/FnGtreeSUtil.java)
    public static
	LnStrm<FnList<FnTupl2<Integer, Integer>>>
	genKnightsTours(int chessBoardSize) {
	// I expect you to find some knight's tours for
	// a board of dimension 8x8; there will be bonus
	// points for handling larger boards.
        class Node implements FnGtree<FnList<FnTupl2<Integer,Integer>>> {
            FnList<FnTupl2<Integer,Integer>> path;
            int pr;
            Node(FnList<FnTupl2<Integer,Integer>> p, int pr0) { path = p; pr = pr0; }
            public FnList<FnTupl2<Integer,Integer>> value() { return path; }
            public int priority() { return pr; }

            public FnList<FnGtree<FnList<FnTupl2<Integer,Integer>>>> children() {
                FnTupl2<Integer,Integer> pos = path.hd();
                int[][] moves = {
                    {1,2},{2,1},{-1,2},{-2,1},
                    {1,-2},{2,-1},{-1,-2},{-2,-1}
                };

                FnList<FnGtree<FnList<FnTupl2<Integer,Integer>>>> r = new FnList<>();
                int mindeg = Integer.MAX_VALUE;
                int[] chosen = null;

                for (int[] m : moves) {
                    int nx = pos.s0() + m[0];
                    int ny = pos.s1() + m[1];
                    if (0 <= nx && nx < chessBoardSize && 0 <= ny && ny < chessBoardSize) {
                        final int fx = nx, fy = ny;
                        if (!path.forall(p -> !(p.s0()==fx && p.s1()==fy))) continue;

                        int deg = 0;
                        for (int[] m2 : moves) {
                            int tx = nx + m2[0], ty = ny + m2[1];
                            if (0 <= tx && tx < chessBoardSize && 0 <= ty && ty < chessBoardSize) {
                                final int ftx = tx, fty = ty;
                                if (path.forall(p -> !(p.s0()==ftx && p.s1()==fty))) deg++;
                            }
                        }

                        if (deg < mindeg) {
                            mindeg = deg;
                            chosen = new int[]{nx, ny};
                        }
                    }
                }

                if (chosen != null) {
                    FnList<FnTupl2<Integer,Integer>> np = new FnList<>(new FnTupl2<>(chosen[0], chosen[1]), path);
                    r = new FnList<>(new Node(np, 8 - mindeg), r);
                }

                return r;
            }
        }

        int sx = chessBoardSize / 2;
        int sy = chessBoardSize / 2;
        FnList<FnTupl2<Integer,Integer>> start =
            new FnList<>(new FnTupl2<>(sx,sy), new FnList<>());
        Node root = new Node(start, 0);
        LnStrm<FnList<FnTupl2<Integer,Integer>>> s =
            FnGtreeSUtil.PFirstEnumerate(root);
        return s.filter0(p -> p.length() == chessBoardSize * chessBoardSize);
    }

    // Please write minimal testing code for [genKnightsTours]
    public static void main(String[] args) {
        int[] sizes = {8, 10, 15, 20};

        for (int n : sizes) {
            System.out.println("Trying board size " + n + "x" + n + "...");
            LnStrm<FnList<FnTupl2<Integer,Integer>>> t = genKnightsTours(n);

            final boolean[] found = {false};
            t.foritm0(path -> {
                found[0] = true;
                System.out.println("Tour found for size " + n + ":");
                path.rforitm(pos ->
                    System.out.print("(" + pos.s0() + "," + pos.s1() + ") ")
                );
                System.out.println("\n");
            });

            if (!found[0])
                System.out.println("No tour found for " + n + "x" + n + ".\n");
        }
    }
}

// contains Assign09_02