/*
  HX-2025-09-15: 10 points
*/
public class Assign03_01 {
    //
    // HX-2025-09-15:
    // This implementation of f91
    // is not tail-recursive. Please
    // translate it into a version that
    // is tail-recursive
    //
    /*
    static int f91(int n) {
	if (n > 100)
	    return n-10;
	else
	    return f91(f91(n+11);
    }
    */

    public static int f91(int n) {
        return f91Helper(n, 1);
    }

    private static int f91Helper(int n, int c) {
        if (c == 0) return n;
        else if (n > 100) return f91Helper(n - 10, c - 1);
        
        return f91Helper(n + 11, c + 1); 
    }

    public static void main(String[] args) {
        System.out.println(f91(102)); // Output: 92
        System.out.println(f91(99)); // Output: 91
    }
}
