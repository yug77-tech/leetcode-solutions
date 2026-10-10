import java.util.Arrays;

/**
 * LeetCode Problem 561: Array Partition
 * 
 * Explicit version using Math.min() for clarity
 * 
 * This solution is functionally identical to the optimized version,
 * but uses Math.min() to make the pairing logic more explicit.
 * 
 * Key Observation:
 * After sorting, nums[i] is always ≤ nums[i+1], so:
 * Math.min(nums[i], nums[i+1]) will always return nums[i]
 * 
 * However, using Math.min() makes the code more self-documenting:
 * - Clearly shows we're taking the minimum of each pair
 * - More readable for someone unfamiliar with the problem
 * - Slight overhead from Math.min() call (negligible in practice)
 * 
 * Trade-off:
 * ✓ More explicit and readable
 * ✗ Redundant function call (after sorting)
 * 
 * Algorithm:
 * 1. Sort array in ascending order
 * 2. For each pair of adjacent elements, take the minimum
 * 3. Sum all minimums
 * 
 * Example: nums = [1,4,3,2]
 *   Sort: [1,2,3,4]
 *   i=0: min(1,2) = 1
 *   i=2: min(3,4) = 3
 *   Sum: 1 + 3 = 4
 * 
 * Time Complexity: O(n log n) - dominated by sorting
 * Space Complexity: O(1) or O(log n) - depends on sort implementation
 * 
 * Note: Complexity is same as the optimized version.
 * The Math.min() call adds O(n) operations, which doesn't change
 * the overall O(n log n) complexity.
 */
class Solution {
    public int arrayPairSum(int[] nums) {
        int sum = 0;
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length; i += 2) {
            sum += Math.min(nums[i], nums[i + 1]);
        }
        
        return sum;
    }
}
