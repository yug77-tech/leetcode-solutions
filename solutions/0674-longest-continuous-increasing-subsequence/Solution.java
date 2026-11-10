/**
 * LeetCode Problem 674: Longest Continuous Increasing Subsequence
 * 
 * Single-pass streak tracking
 * 
 * Problem:
 * Find length of longest continuous strictly increasing subarray.
 * 
 * Key Insight:
 * Simple counter approach!
 * - Track current streak length
 * - When increasing: extend streak
 * - When not increasing: reset streak
 * - Track maximum streak seen
 * 
 * Continuous vs Subsequence:
 * 
 * Continuous (This problem):
 * - Elements must be ADJACENT
 * - Example: [1,3,5] in [1,3,5,4,7]
 * - Simpler problem
 * 
 * Subsequence (Different problem):
 * - Can skip elements
 * - Example: [1,3,5,7] in [1,3,5,4,7]
 * - More complex (requires DP)
 * 
 * Algorithm:
 * 
 * 1. Initialize max=0, count=0
 * 2. For each element:
 *    - If first OR greater than previous:
 *      - Increment count (extend streak)
 *    - Else:
 *      - Reset count to 1 (new streak)
 *    - Update max
 * 3. Return max
 * 
 * Example: [1,3,5,4,7]
 * 
 * i=0, nums[0]=1:
 *   First element → count=1
 *   max=1
 *   Current streak: [1]
 * 
 * i=1, nums[1]=3:
 *   3 > 1 ✓ → count=2
 *   max=2
 *   Current streak: [1,3]
 * 
 * i=2, nums[2]=5:
 *   5 > 3 ✓ → count=3
 *   max=3
 *   Current streak: [1,3,5]
 * 
 * i=3, nums[3]=4:
 *   4 not > 5 ✗ → count=1 (reset)
 *   max=3 (no change)
 *   New streak: [4]
 * 
 * i=4, nums[4]=7:
 *   7 > 4 ✓ → count=2
 *   max=3 (no change)
 *   Current streak: [4,7]
 * 
 * Result: 3 (longest was [1,3,5])
 * 
 * Why Check i == 0?
 * 
 * First element needs special handling:
 * - Can't compare with previous (doesn't exist)
 * - Always starts a new streak
 * 
 * if (i == 0 || nums[i] > nums[i-1])
 *    ↑ Check this first!
 * 
 * Why order matters:
 * - Java short-circuit evaluation
 * - If i==0 is true, nums[i-1] never accessed
 * - Prevents ArrayIndexOutOfBoundsException
 * 
 * Wrong order:
 * if (nums[i] > nums[i-1] || i == 0)
 * - When i=0, tries nums[-1] first
 * - Crashes!
 * 
 * Strictly Increasing Requirement:
 * 
 * Example: [2,2,2,2,2]
 * 
 * i=0: First → count=1, max=1
 * i=1: 2 not > 2 → count=1, max=1
 * i=2: 2 not > 2 → count=1, max=1
 * i=3: 2 not > 2 → count=1, max=1
 * i=4: 2 not > 2 → count=1, max=1
 * 
 * Result: 1
 * 
 * Equal elements break the streak!
 * Must use > not >=
 * 
 * Why Update Max Every Iteration?
 * 
 * Don't miss shorter final streaks:
 * 
 * Example: [1,2,3,4,1,2]
 * 
 * Last streak [1,2] has length 2
 * If only update on reset, miss this final streak
 * 
 * Solution: update max every iteration
 * max = Math.max(max, count);
 * 
 * Edge Cases:
 * 
 * 1. Single element: [5]
 *    i=0: count=1, max=1
 *    Result: 1 ✓
 * 
 * 2. All increasing: [1,2,3,4,5]
 *    Never resets, count grows to 5
 *    Result: 5 ✓
 * 
 * 3. All decreasing: [5,4,3,2,1]
 *    Resets every time, count always 1
 *    Result: 1 ✓
 * 
 * 4. All equal: [3,3,3,3]
 *    Equal breaks streak
 *    Result: 1 ✓
 * 
 * 5. Negative numbers: [-3,-1,0,2]
 *    Works same way (comparison is universal)
 *    Result: 4 ✓
 * 
 * Alternative: Two Pointers
 * 
 * Track start of current streak:
 * 
 * int max = 1;
 * int start = 0;
 * 
 * for (int i = 1; i < nums.length; i++) {
 *     if (nums[i] <= nums[i-1]) {
 *         start = i;  // Reset start
 *     }
 *     max = Math.max(max, i - start + 1);
 * }
 * 
 * Same complexity, different perspective:
 * - Your solution: tracks length directly
 * - Two pointers: tracks start position
 * 
 * Both are O(n) time, O(1) space
 * Your solution is more intuitive!
 * 
 * Common Mistakes:
 * 
 * 1. Wrong condition check order:
 *    if (nums[i] > nums[i-1] || i == 0)
 *    → ArrayIndexOutOfBoundsException
 * 
 * 2. Using >= instead of >:
 *    if (nums[i] >= nums[i-1])
 *    → Allows equal elements (wrong for strictly increasing)
 * 
 * 3. Not updating max continuously:
 *    → Misses final streak
 * 
 * 4. Initializing count to 1 instead of 0:
 *    → Off by one error
 * 
 * Pattern: Streak Tracking
 * 
 * Use when:
 * - Finding longest/shortest continuous property
 * - Property can break and restart
 * - Simple state tracking
 * 
 * Similar problems:
 * - Max Consecutive Ones (485)
 * - Longest Subarray of 1's After Deleting (1493)
 * - Arithmetic Slices (413)
 * 
 * Time Complexity: O(n)
 *   - Single pass through array
 *   - Each element processed once
 *   - Constant work per element
 * 
 * Space Complexity: O(1)
 *   - Only two variables: max and count
 *   - No extra data structures
 *   - Constant space regardless of input size
 */
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        int max = 0;     // Maximum streak length seen
        int count = 0;   // Current streak length

        for (int i = 0; i < nums.length; i++) {
            // Check if we should extend current streak
            // Two conditions:
            // 1. First element (i == 0) - always starts a streak
            // 2. Current > Previous (nums[i] > nums[i-1]) - continue streak
            if (i == 0 || nums[i] > nums[i - 1]) {
                // Extend current streak
                count++;
            } else {
                // Current element not greater than previous
                // Streak is broken, start new streak
                count = 1;
            }

            // Update maximum streak length
            // Check every iteration (don't miss final streak!)
            max = Math.max(max, count);
        }

        return max;
    }
}
