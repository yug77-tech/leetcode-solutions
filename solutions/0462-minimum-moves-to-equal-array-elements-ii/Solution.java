import java.util.Arrays;

/**
 * LeetCode Problem 462: Minimum Moves to Equal Array Elements II
 * 
 * Median minimizes sum of absolute deviations
 * 
 * Problem:
 * Find minimum moves to make all elements equal.
 * One move = increment OR decrement one element by 1.
 * 
 * Key Insight:
 * The optimal target is the MEDIAN!
 * - Median minimizes sum of absolute differences
 * - This is a fundamental property in statistics
 * 
 * Why Median (Not Mean)?
 * 
 * Mean minimizes: sum of squared distances
 * Median minimizes: sum of absolute distances ← We need this!
 * 
 * Example: [1, 2, 100]
 * Mean = 34, distances: |1-34| + |2-34| + |100-34| = 131
 * Median = 2, distances: |1-2| + |2-2| + |100-2| = 99 ✓
 * 
 * Median is much better!
 * 
 * Intuition: Number Line
 * 
 * Elements on number line:
 * 1    2         9  10
 * ●    ●         ●  ●
 * 
 * Median (between 2 and 9) balances left and right:
 * - Equal number of elements on each side
 * - Moving target left: increases right > decreases left
 * - Moving target right: increases left > decreases right
 * - Median is equilibrium point!
 * 
 * Algorithm:
 * 1. Sort array (to find median)
 * 2. Find median = nums[n/2]
 * 3. Calculate sum of |num - median| for all elements
 * 
 * Example Execution: [1,2,3]
 * 
 * Step 1: Sort
 * sorted = [1, 2, 3]
 * 
 * Step 2: Find median
 * n = 3
 * median = nums[3/2] = nums[1] = 2
 * 
 * Step 3: Calculate moves
 * moves = |1-2| + |2-2| + |3-2|
 *       = 1 + 0 + 1
 *       = 2
 * 
 * Return 2
 * 
 * Example: [1,10,2,9]
 * 
 * Step 1: Sort
 * sorted = [1, 2, 9, 10]
 * 
 * Step 2: Find median
 * n = 4 (even)
 * median = nums[4/2] = nums[2] = 9
 * 
 * Note: For even n, any value between two middle elements works:
 * - Lower median: nums[1] = 2
 * - Upper median: nums[2] = 9
 * - Any value between: 2 ≤ target ≤ 9
 * All give same total distance!
 * 
 * Using 9:
 * moves = |1-9| + |2-9| + |9-9| + |10-9|
 *       = 8 + 7 + 0 + 1
 *       = 16
 * 
 * Using 2:
 * moves = |1-2| + |2-2| + |9-2| + |10-2|
 *       = 1 + 0 + 7 + 8
 *       = 16
 * 
 * Same result!
 * 
 * Why Median Works (Mathematical):
 * 
 * For sorted array [a₁, a₂, ..., aₙ]:
 * 
 * If target < median:
 * - Distance increases for all elements > median (many)
 * - Distance decreases for elements < median (few)
 * - Net: distance increases
 * 
 * If target > median:
 * - Distance increases for all elements < median (many)
 * - Distance decreases for elements > median (few)
 * - Net: distance increases
 * 
 * At median:
 * - Balanced number of elements on each side
 * - Cannot improve by moving target
 * 
 * Comparison with Problem 453:
 * 
 * Problem 453:
 * - Operation: increment (n-1) elements by 1
 * - Optimal target: minimum
 * - Formula: sum(num - min)
 * - Time: O(n)
 * 
 * Problem 462:
 * - Operation: inc/dec any element by 1
 * - Optimal target: median
 * - Formula: sum(|num - median|)
 * - Time: O(n log n) - need sorting
 * 
 * Key difference:
 * - 453: Can only increase → target must be minimum
 * - 462: Can inc/dec → target is median
 * 
 * Edge Cases:
 * 
 * 1. All equal: [5,5,5]
 *    median = 5
 *    moves = |5-5| + |5-5| + |5-5| = 0 ✓
 * 
 * 2. Single element: [42]
 *    median = 42
 *    moves = |42-42| = 0 ✓
 * 
 * 3. Two elements: [1,3]
 *    sorted = [1,3]
 *    median = nums[2/2] = nums[1] = 3
 *    moves = |1-3| + |3-3| = 2
 *    
 *    Note: Using 1 also works:
 *    moves = |1-1| + |3-1| = 2
 * 
 * 4. Large gaps: [1,100]
 *    median = 100 (or 1)
 *    moves = |1-100| + |100-100| = 99
 *    Or: |1-1| + |100-1| = 99
 * 
 * 5. Negative numbers: [-10, 0, 10]
 *    median = 0
 *    moves = |-10-0| + |0-0| + |10-0| = 20 ✓
 * 
 * Alternative: Two Pointers (Without Finding Median)
 * 
 * public int minMoves2(int[] nums) {
 *     Arrays.sort(nums);
 *     int left = 0, right = nums.length - 1;
 *     int moves = 0;
 *     
 *     while (left < right) {
 *         moves += nums[right] - nums[left];
 *         left++;
 *         right--;
 *     }
 *     
 *     return moves;
 * }
 * 
 * How it works:
 * - Pair smallest with largest
 * - They meet somewhere in middle (median)
 * - Distance between pair = moves needed
 * - Sum all pair distances
 * 
 * Example: [1, 2, 9, 10]
 * Pair 1 with 10: distance = 9
 * Pair 2 with 9: distance = 7
 * Total = 16 ✓
 * 
 * Same result, different approach!
 * 
 * Why Not Mean?
 * 
 * Mean is affected by outliers:
 * [1, 2, 100]
 * mean = 34.33
 * 
 * Using mean:
 * |1-34| + |2-34| + |100-34| = 131
 * 
 * Using median (2):
 * |1-2| + |2-2| + |100-2| = 99 ✓
 * 
 * Median is robust to extremes!
 * 
 * Pattern: Median Optimization
 * 
 * Use median when:
 * - Minimize sum of absolute differences
 * - Find optimal meeting point
 * - Balance distances from target
 * - Robust to outliers
 * 
 * Similar problems:
 * - Best Meeting Point
 * - Minimum Total Distance
 * - Optimal Array Transformation
 * 
 * Time Complexity: O(n log n)
 *   - Sorting: O(n log n)
 *   - Finding median: O(1)
 *   - Calculating moves: O(n)
 *   - Dominated by sorting
 * 
 * Space Complexity: O(1) or O(log n)
 *   - Depends on sorting algorithm
 *   - Arrays.sort() uses dual-pivot quicksort: O(log n) recursion
 *   - In-place sorting: O(1) extra space
 */
class Solution {
    public int minMoves2(int[] nums) {
        // Step 1: Sort array to find median
        // Median is middle element in sorted array
        Arrays.sort(nums);

        // Step 2: Find median
        // For odd n: unique middle element
        // For even n: any value between two middle elements works
        // nums[n/2] gives upper median for even n
        int median = nums[nums.length / 2];
        
        // Step 3: Calculate total moves
        // Move each element to median
        // Distance = absolute difference
        int moves = 0;

        for (int num : nums) {
            // Add distance from current element to median
            // Use Math.abs for absolute value (handles both inc and dec)
            moves += Math.abs(num - median);
        }

        // Total moves = sum of all distances to median
        return moves;
    }
}
