public class Assign02_03 {
    public static boolean solve_3sum(Integer[] A) {
    	for (int i = 0; i < A.length; i++) {
            for (int j = i + 1; j < A.length; j++) {
                if (Assign02_02.indexOf(A, A[i] + A[j]) != -1) { // Soft O(n^2) because it performs a binary search using i and j instead of a linear search
                    return true;
                }
            }
        }
        return false;
    }
    public static void main(String[] args) {
	    final Integer[] arr = {1, 3, 5, 7, 9};
        final Integer[] arr2 = {1, 3, 5, 4, 10};
        System.out.println(solve_3sum(arr));    // Output: false
        System.out.println(solve_3sum(arr2));   // Output: true
    }
}