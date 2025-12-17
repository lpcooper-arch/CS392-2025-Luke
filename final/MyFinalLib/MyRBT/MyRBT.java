package MyFinalLib.MyRBT;

//
// HX-2025-11-20: 30 points
// (plus up to 20 bonus points)
// This is more of a theory problem
// than a programming one.
//
public class MyRBT {
    public static class RBTnode {
        int key;
        int color; // Red = 0; Black = 1
        RBTnode lchild;
        RBTnode rchild;
        
        public RBTnode(int key, int color) {
            this.key = key;
            this.color = color;
            this.lchild = null;
            this.rchild = null;
        }
    }
    
    private static class RBTInfo {
        boolean isValid;
        int blackHeight;
        
        public RBTInfo(boolean isValid, int blackHeight) {
            this.isValid = isValid;
            this.blackHeight = blackHeight;
        }
    }
    //
    // HX: 10 points for this one
    // HX: If your implementation only
    // visit each node in [rbt] at most once,
    // then it will be rewarded with some bonus
    // points (up to 20 bonus points).
    // For instance, if you compute the size of
    // height of a tree, then you already visit
    // each node once.
    //
    public static boolean isRBT (RBTnode rbt) {
        // HX: Please implement a function that
        // tests whether a given RBTnode is a valid
        // red-black tree. If it is unclear what a
        // red-black tree, you can readily find it on-line
        // Note that you are not asked to check if rbt is
        // a binary search tree in this case.
        if (rbt != null && rbt.color == 0) return false;
        return checkRBT(rbt).isValid;
    }
    
    private static RBTInfo checkRBT(RBTnode node) {
        if (node == null) {
            return new RBTInfo(true, 1);
        }
        
        if (node.color == 0) {
            if ((node.lchild != null && node.lchild.color == 0) ||
                (node.rchild != null && node.rchild.color == 0)) {
                return new RBTInfo(false, 0);
            }
        }
        
        RBTInfo leftInfo = checkRBT(node.lchild);
        if (!leftInfo.isValid) {
            return new RBTInfo(false, 0);
        }
        
        RBTInfo rightInfo = checkRBT(node.rchild);
        if (!rightInfo.isValid) {
            return new RBTInfo(false, 0);
        }
        
        if (leftInfo.blackHeight != rightInfo.blackHeight) {
            return new RBTInfo(false, 0);
        }
        
        int currentBlackHeight = leftInfo.blackHeight;
        if (node.color == 1) {
            currentBlackHeight++;
        }
        
        return new RBTInfo(true, currentBlackHeight);
    }
    //
    // HX: 20 points
    // This is largely about understanding red-black trees.
    // Please explain BRIEFLY as to why the generated RBT is
    // of minimal black height (not height).
    //
    public static RBTnode genRedBlackBST() {
        // Please genenerate a binary search RBT that
        // contains exactly 1 million keys: 0, 1, 2, ..., 999999
        // such that the black height (not height) of this tree is
        // minimal (that is, as small as possible). What is this black
        // height? Please give a brief explanation on your implementation
        // strategy.
        return buildOptimalRBT(0, 999999, 0);
    }
    
    private static RBTnode buildOptimalRBT(int start, int end, int level) {
        if (start > end) {
            return null;
        }
        
        int mid = start + (end - start) / 2;
        
        int color = (level % 2 == 0) ? 1 : 0;
        RBTnode node = new RBTnode(mid, color);
        
        node.lchild = buildOptimalRBT(start, mid - 1, level + 1);
        node.rchild = buildOptimalRBT(mid + 1, end, level + 1);
        
        return node;
    }
    
    private static int computeBlackHeight(RBTnode node) {
        if (node == null) {
            return 1;
        }
        int bh = computeBlackHeight(node.lchild);
        if (node.color == 1) {
            bh++;
        }
        return bh;
    }
    
    public static void main (String[] args) {
        // Please add minimal testing code for isRBT()
        RBTnode valid = new RBTnode(10, 1);
        valid.lchild = new RBTnode(5, 0);
        valid.rchild = new RBTnode(15, 0);
        valid.lchild.lchild = new RBTnode(3, 1);
        valid.lchild.rchild = new RBTnode(7, 1);
        valid.rchild.lchild = new RBTnode(12, 1);
        valid.rchild.rchild = new RBTnode(17, 1);
        
        System.out.println("Valid RBT test: " + isRBT(valid));
        
        RBTnode invalid = new RBTnode(10, 1);
        invalid.lchild = new RBTnode(5, 0);
        invalid.rchild = new RBTnode(15, 0);
        invalid.lchild.lchild = new RBTnode(3, 0);
        
        System.out.println("Invalid RBT test (red-red violation): " + isRBT(invalid));
        
        RBTnode invalid2 = new RBTnode(10, 1);
        invalid2.lchild = new RBTnode(5, 1);
        invalid2.rchild = new RBTnode(15, 1);
        invalid2.lchild.lchild = new RBTnode(3, 1);
        
        System.out.println("Invalid RBT test (black height mismatch): " + isRBT(invalid2));
        
        RBTnode invalidRoot = new RBTnode(10, 0);
        System.out.println("Invalid RBT test (red root): " + isRBT(invalidRoot));
        
        // Please add minimal testing code for genRedBlackBST()
        RBTnode generatedTree = genRedBlackBST();
        System.out.println("Generated tree is valid RBT: " + isRBT(generatedTree));
        System.out.println("Black height of generated tree: " + (computeBlackHeight(generatedTree) - 1));
        
        return /*void*/;
    }
}

// contains Quiz02_05