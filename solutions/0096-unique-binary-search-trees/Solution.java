/**
 * LeetCode Problem 96: Unique Binary Search Trees
 * 
 * Mathematical solution using Catalan Numbers
 * 
 * Problem Insight:
 * The number of unique BSTs with n nodes equals the nth Catalan number.
 * Catalan numbers appear in many combinatorial problems!
 * 
 * Why Catalan Numbers?
 * For n nodes (1 to n):
 * - Choose any node as root
 * - Smaller values form left subtree, larger values form right subtree
 * - Total combinations = sum of (left_ways × right_ways) for each root
 * - This recurrence relation defines Catalan numbers!
 * 
 * Formula:
 * C(n) = (2n)! / ((n+1)! × n!)
 * 
 * Iterative computation:
 * C(n) = C(n-1) × 2×(2n-1) / (n+1)
 * 
 * Starting from C(0) = 1, we compute:
 * result = result × 2×(2i+1) / (i+2) for i from 0 to n-1
 * 
 * Example: n = 3
 *   i=0: result = 1 × 2×1/2 = 1
 *   i=1: result = 1 × 2×3/3 = 2
 *   i=2: result = 2 × 2×5/4 = 5
 *   Result: 5 unique BSTs
 * 
 * Why use long?
 * Intermediate calculations may overflow int, but final result fits in int.
 * 
 * Time Complexity: O(n) - single loop
 * Space Complexity: O(1) - constant extra space
 */
class Solution {
    public int numTrees(int n) {
        long result = 1;
        for (int i = 0; i < n; i++) {
            result = result * 2 * (2 * i + 1) / (i + 2);
        }
        return (int) result;
    }
}
