/**
 * LeetCode Problem 453: Minimum Moves to Equal Array Elements
 * 
 * Mathematical transformation: increment n-1 = decrement 1
 * 
 * Problem:
 * Find minimum moves to make all array elements equal.
 * One move = increment (n-1) elements by 1.
 * 
 * Key Insight (BRILLIANT!):
 * Incrementing (n-1) elements by 1
 * =
 * Decrementing 1 element by 1 (relatively)
 * 
 * Why? Relative positions matter!
 * 
 * Example: [1, 2, 3]
 * 
 * Traditional thinking (simulate):
 * Move 1: Increment [1,2] → [2,3,3]
 * Move 2: Increment [2,3] → [3,4,3]
 * Move 3: Increment [3,3] → [4,4,4]
 * Total: 3 moves
 * 
 * Mathematical insight:
 * - Incrementing n-1 is like decrementing 1
 * - Goal: make all equal to minimum
 * - Moves = sum of differences from minimum
 * 
 * For [1,2,3]:
 * min = 1
 * moves = (1-1) + (2-1) + (3-1)
 *       = 0 + 1 + 2
 *       = 3 ✓
 * 
 * Same answer, much simpler!
 * 
 * Why This Works:
 * 
 * Each move increments (n-1) elements:
 * - Total sum increases by (n-1)
 * - One element stays relatively behind
 * - This is equivalent to "decrementing" that element
 * 
 * To make all equal:
 * - Can't go below minimum (only increment)
 * - So target = minimum
 * - Each element needs: (element - min) moves
 * - Total: sum(nums[i] - min)
 * 
 * Mathematical Proof:
 * 
 * Let array be [a₁, a₂, ..., aₙ]
 * Let min = minimum element
 * 
 * After k moves, all equal to value V:
 * - Each element incremented some number of times
 * - Element that was min: incremented k times → V = min + k
 * 
 * For element aᵢ:
 * - Needs to reach V = min + k
 * - Started at aᵢ
 * - Incremented (k - (aᵢ - min)) times
 * - This means it was NOT incremented (aᵢ - min) times
 * 
 * Total moves where each element NOT incremented:
 * sum(aᵢ - min) = k
 * 
 * So: moves = sum(nums[i] - min)
 * 
 * Example Execution: [1,2,3]
 * 
 * Step 1: Find minimum
 * min = 1
 * 
 * Step 2: Calculate moves
 * moves = 0
 * 
 * For num=1: moves += (1-1) = 0 → moves = 0
 * For num=2: moves += (2-1) = 1 → moves = 1
 * For num=3: moves += (3-1) = 2 → moves = 3
 * 
 * Return 3
 * 
 * Example: [5,6,8,8,5]
 * 
 * Step 1: min = 5
 * 
 * Step 2:
 * moves = (5-5) + (6-5) + (8-5) + (8-5) + (5-5)
 *       = 0 + 1 + 3 + 3 + 0
 *       = 7
 * 
 * Return 7
 * 
 * Why Not Simulate?
 * 
 * Simulation approach:
 * while (!allEqual) {
 *     find max element
 *     increment all except max
 *     moves++
 * }
 * 
 * Problems:
 * - Time: O(n × moves)
 * - For [1, 1000000]: 999999 moves!
 * - Would timeout
 * 
 * Our approach: O(n) regardless of move count!
 * 
 * Edge Cases:
 * 
 * 1. All equal: [5,5,5]
 *    min = 5
 *    moves = (5-5) + (5-5) + (5-5) = 0 ✓
 * 
 * 2. Single element: [42]
 *    min = 42
 *    moves = (42-42) = 0 ✓
 * 
 * 3. Large differences: [1, 1000000]
 *    min = 1
 *    moves = (1-1) + (1000000-1) = 999999
 *    Still O(n) calculation!
 * 
 * 4. Negative numbers: [-10, -5, 0]
 *    min = -10
 *    moves = (-10-(-10)) + (-5-(-10)) + (0-(-10))
 *          = 0 + 5 + 10
 *          = 15 ✓
 * 
 * Alternative Formula:
 * 
 * moves = sum(nums) - n × min
 * 
 * This is mathematically equivalent:
 * sum(nums[i] - min) = sum(nums) - sum(min)
 *                     = sum(nums) - n × min
 * 
 * Could implement in single loop:
 * int min = Integer.MAX_VALUE;
 * int sum = 0;
 * for (int num : nums) {
 *     min = Math.min(min, num);
 *     sum += num;
 * }
 * return sum - nums.length * min;
 * 
 * Your two-loop version is clearer!
 * 
 * Why This Problem is Beautiful:
 * 
 * 1. Counter-intuitive transformation:
 *    - "Increment n-1" sounds complex
 *    - Actually equivalent to "decrement 1"
 *    - One insight changes everything!
 * 
 * 2. Elegant solution:
 *    - From simulation to simple sum
 *    - From O(n × moves) to O(n)
 *    - From complex to trivial
 * 
 * 3. Mathematical thinking:
 *    - Focus on relative differences
 *    - Not absolute values
 *    - Transform the problem
 * 
 * Pattern: Mathematical Transformation
 * 
 * Key lesson:
 * - Don't always simulate directly
 * - Look for mathematical equivalence
 * - Think about relative changes
 * - Transform to simpler problem
 * 
 * Similar problems:
 * - Minimum Moves to Equal Array Elements II
 * - Gas Station
 * - Best Time to Buy and Sell Stock
 * 
 * Time Complexity: O(n)
 *   - First loop: find minimum - O(n)
 *   - Second loop: sum differences - O(n)
 *   - Total: O(n)
 * 
 * Space Complexity: O(1)
 *   - Only two variables: min and moves
 *   - No extra arrays or data structures
 *   - Constant space
 */
class Solution {
    public int minMoves(int[] nums) {
        // Step 1: Find the minimum element
        // This is our target - all elements will equal this eventually
        // (relatively speaking, by "decrementing" others to this level)
        int min = nums[0];
        int moves = 0;

        for (int num : nums) {
            min = Math.min(min, num);
        }

        // Step 2: Calculate total moves
        // Each element needs (element - min) "virtual decrements"
        // Sum these up to get total moves
        for (int num : nums) {
            moves += num - min;
        }

        // Total moves = sum of all differences from minimum
        return moves;
    }
}
