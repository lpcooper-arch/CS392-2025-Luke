//
// HX-2025-11-20: 50 points
// Some "hard" Sudoku puzzles can be
// found here: https://sudoku.com/hard/.
// This question is similar to Assign07_02.
// You are asked to use DFirstEnumerate and BFirstEnumerate
// in FnGtree to solve Sudoku puzzles. Your solution
// should be able to solve "hard" Sudoku puzzles effectively.
//
import MyLibrary.FnList.*;
import MyLibrary.LnStrm.*;
import MyLibrary.FnGtree.*;
//
class Sudoku implements FnGtree<Sudoku> {
    FnList<FnList<Integer>> grid;

    public Sudoku() {
        FnList<FnList<Integer>> rows = FnListSUtil.nil();
        for (int i = 0; i < 9; i++) {
            FnList<Integer> row = FnListSUtil.nil();
            for (int j = 0; j < 9; j++) {
                row = FnListSUtil.cons(0, row);
            }
            rows = FnListSUtil.cons(row, rows);
        }
        this.grid = rows;
    }
    
    public Sudoku(FnList<FnList<Integer>> grid) {
        this.grid = grid;
    }

    public void System$out$print() {
        grid.iforitm((rowIndex, row) -> {
            row.iforitm((colIndex, cell) -> {
                System.out.print(cell + " ");
                if ((colIndex + 1) % 3 == 0 && colIndex != 8) {
                    System.out.print("| ");
                }
            });
            System.out.println();
            if ((rowIndex + 1) % 3 == 0 && rowIndex != 8) {
                System.out.println("---------------------");
            }
        });
    }

    public Sudoku set(int row, int col, int value) {
        FnList<FnList<Integer>> newGrid = FnListSUtil.imap_list(grid, (i, rowList) -> {
            if (i == row) {
                return FnListSUtil.imap_list(rowList, (j, cell) -> {
                    return (j == col) ? value : cell;
                });
            }
            return rowList;
        });
        return new Sudoku(newGrid);
    }

    public int get(int row, int col) {
        FnList<FnList<Integer>> rows = grid;
        for (int i = 0; i < row; i++) {
            rows = rows.tl();
        }
        FnList<Integer> rowList = rows.hd();
        for (int j = 0; j < col; j++) {
            rowList = rowList.tl();
        }
        return rowList.hd();
    }

    public boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (get(row, i) == num) return false;
        }
        for (int i = 0; i < 9; i++) {
            if (get(i, col) == num) return false;
        }
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (get(boxRow + i, boxCol + j) == num) return false;
            }
        }
        return true;
    }

    public int[] findEmptyWithMRV() {
        int minOptions = 10;
        int bestRow = -1, bestCol = -1;
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (get(i, j) == 0) {
                    int options = countOptions(i, j);
                    if (options == 0) return new int[]{-1, -1};
                    if (options < minOptions) {
                        minOptions = options;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }
        
        if (bestRow == -1) return null;
        return new int[]{bestRow, bestCol};
    }
    
    private int countOptions(int row, int col) {
        int count = 0;
        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) count++;
        }
        return count;
    }

    public boolean isSolved() {
        return findEmptyWithMRV() == null;
    }

    public Sudoku value() {
        return this;
    }

    public FnList<FnGtree<Sudoku>> children() {
        int[] empty = findEmptyWithMRV();
        if (empty == null) {
            return FnListSUtil.nil();
        }
        
        if (empty[0] == -1) {
            return FnListSUtil.nil();
        }

        int row = empty[0];
        int col = empty[1];
        FnList<FnGtree<Sudoku>> children = FnListSUtil.nil();

        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                Sudoku newPuzzle = set(row, col, num);
                children = FnListSUtil.cons(newPuzzle, children);
            }
        }

        return FnListSUtil.reverse(children);
    }
}

class FnGtreeEnumerate {
    public static<T> LnStrm<T> DFirstEnumerate(FnGtree<T> root) {
        return new LnStrm<T>(() -> {
            return new LnStcn<T>(root.value(), DFirstChildren(root.children()));
        });
    }

    private static<T> LnStrm<T> DFirstChildren(FnList<FnGtree<T>> children) {
        return new LnStrm<T>(() -> {
            if (children.nilq()) {
                return new LnStcn<T>();
            }
            FnGtree<T> child = children.hd();
            LnStrm<T> childStream = DFirstEnumerate(child);
            LnStrm<T> siblingStream = DFirstChildren(children.tl());
            return appendStreams(childStream, siblingStream).eval0();
        });
    }

    private static<T> LnStrm<T> appendStreams(LnStrm<T> s1, LnStrm<T> s2) {
        return new LnStrm<T>(() -> {
            LnStcn<T> c1 = s1.eval0();
            if (c1.nilq()) {
                return s2.eval0();
            }
            return new LnStcn<T>(c1.head, appendStreams(c1.tail, s2));
        });
    }

    public static<T> LnStrm<T> BFirstEnumerate(FnGtree<T> root) {
        FnList<FnGtree<T>> queue = FnListSUtil.cons(root, FnListSUtil.nil());
        return BFirstHelper(queue);
    }

    private static<T> LnStrm<T> BFirstHelper(FnList<FnGtree<T>> queue) {
        return new LnStrm<T>(() -> {
            if (queue.nilq()) {
                return new LnStcn<T>();
            }
            FnGtree<T> node = queue.hd();
            FnList<FnGtree<T>> restQueue = queue.tl();
            FnList<FnGtree<T>> newQueue = FnListSUtil.append(restQueue, node.children());
            return new LnStcn<T>(node.value(), BFirstHelper(newQueue));
        });
    }
}

public class Quiz02_03 {
    public LnStrm<Sudoku> Soduku_dfs_solve(Sudoku puzzle) {
        LnStrm<Sudoku> stream = FnGtreeEnumerate.DFirstEnumerate(puzzle);
        return stream.filter0((p) -> p.isSolved());
    }

    public LnStrm<Sudoku> Soduku_bfs_solve(Sudoku puzzle) {
        LnStrm<Sudoku> stream = FnGtreeEnumerate.BFirstEnumerate(puzzle);
        return stream.filter0((p) -> p.isSolved());
    }
//
    public static void main (String[] args) {
	// Please add minimal testing code for Sudoku_dfs_solve
	// Please add minimal testing code for Sudoku_bfs_solve

        Quiz02_03 solver = new Quiz02_03();

        Sudoku puzzle = new Sudoku();

        // Actual example of a "hard" sudoku puzzle
        puzzle = puzzle.set(0, 0, 5).set(0, 1, 3).set(0, 4, 7);
        puzzle = puzzle.set(1, 0, 6).set(1, 3, 1).set(1, 4, 9).set(1, 5, 5);
        puzzle = puzzle.set(2, 1, 9).set(2, 2, 8).set(2, 7, 6);
        puzzle = puzzle.set(3, 0, 8).set(3, 4, 6).set(3, 8, 3);
        puzzle = puzzle.set(4, 0, 4).set(4, 3, 8).set(4, 5, 3).set(4, 8, 1);
        puzzle = puzzle.set(5, 0, 7).set(5, 4, 2).set(5, 8, 6);
        puzzle = puzzle.set(6, 1, 6).set(6, 6, 2).set(6, 7, 8);
        puzzle = puzzle.set(7, 3, 4).set(7, 4, 1).set(7, 5, 9).set(7, 8, 5);
        puzzle = puzzle.set(8, 4, 8).set(8, 7, 7).set(8, 8, 9);

        System.out.println("Unsolved Puzzle:");
        puzzle.System$out$print();

        System.out.println("\nDFS Solution:");
        LnStrm<Sudoku> dfs = solver.Soduku_dfs_solve(puzzle);
        LnStcn<Sudoku> dfsSol = dfs.eval0();
        if (dfsSol.consq()) {
            dfsSol.head.System$out$print();
        } else {
            System.out.println("No solution found");
        }

        System.out.println("\nBFS Solution:");
        LnStrm<Sudoku> bfs = solver.Soduku_bfs_solve(puzzle);
        LnStcn<Sudoku> bfsSol = bfs.eval0();
        if (bfsSol.consq()) {
            bfsSol.head.System$out$print();
        } else {
            System.out.println("No solution found");
        }

        return /*void*/;
    }
//
}