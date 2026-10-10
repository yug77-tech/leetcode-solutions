import java.util.Arrays;

/**
 * LeetCode Problem 561: Array Partition
 * 
 * Optimized solution using built-in sorting
 * 
 * Key Insight:
 * To maximize sum of min(ai, bi), pair adjacent elements after sorting!
 * 
 * Why this works:
 * - Taking min(a, b) means we "lose" max(a, b)
 * - To minimize loss, pair elements close in value
 * - After sorting, consecutive elements are closest
 * - Example: [1,2,3,4] → pairs (1,2), (3,4) → sum = 1+3 = 4
 * 
 * Algorithm:
 * 1. Sort array in ascending order
 * 2. Sum elements at even indices (0, 2, 4, ...)
 * 3. These are the minimums from optimal pairs
 * 
 * Example: nums = [1,4,3,2]
 *   Sort: [1,2,3,4]
 *   Pairs: (1,2), (3,4)
 *   Sum of mins: 1 + 3 = 4
 * 
 * Example: nums = [6,2,6,5,1,2]
 *   Sort: [1,2,2,5,6,6]
 *   Pairs: (1,2), (2,5), (6,6)
 *   Sum of mins: 1 + 2 + 6 = 9
 * 
 * Time Complexity: O(n log n) - dominated by sorting
 * Space Complexity: O(1) or O(log n) - depends on sort implementation
 */
class Solution {
    public int arrayPairSum(int[] nums) {
        // Sort array using built-in method (typically O(n log n))
        Arrays.sort(nums);
        
        int sum = 0;
        // Sum elements at even indices (0, 2, 4, ...)
        for (int i = 0; i < nums.length; i += 2) {
            sum += nums[i];
        }
        
        return sum;
    }
}
