/**
 * LeetCode Problem 70: Climbing Stairs
 * 
 * Space-optimized Dynamic Programming solution using Fibonacci pattern
 * 
 * Key Insight:
 * To reach step n, you can come from either step (n-1) or step (n-2)
 * Therefore: ways[n] = ways[n-1] + ways[n-2]
 * 
 * Optimization:
 * Instead of storing all values in an array, we only keep track of the
 * last two values (prev1 and prev2) since we only need them to calculate
 * the current value.
 * 
 * Time Complexity: O(n) - iterate through n steps once
 * Space Complexity: O(1) - only using constant extra space (prev1, prev2, current)
 */
class Solution {
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int prev2 = 1;
        int prev1 = 2;
        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        return prev1;
    }
}
