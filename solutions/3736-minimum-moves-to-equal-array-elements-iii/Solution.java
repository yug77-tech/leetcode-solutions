/**
 * LeetCode Problem 3736: Minimum Moves to Equal Array Elements III
 * 
 * Simple target = maximum (can only increase)
 * 
 * Problem:
 * Find minimum moves to make all elements equal.
 * One move = increase any single element by 1.
 * 
 * Key Insight:
 * Can ONLY increase (not decrease)
 * → Target must be MAXIMUM!
 * 
 * Why Maximum?
 * 
 * Can't go below maximum (only increase operation):
 * - If target < max: impossible to decrease max
 * - If target = max: just increase smaller elements
 * - If target > max: need more moves (suboptimal)
 * 
 * Optimal: target = maximum
 * 
 * Example: [1, 2, 3]
 * 
 * max = 3
 * 
 * Moves needed:
 * - 1 → 3: needs 2 moves
 * - 2 → 3: needs 1 move
 * - 3 → 3: needs 0 moves
 * 
 * Total = (3-1) + (3-2) + (3-3)
 *       = 2 + 1 + 0
 *       = 3
 * 
 * Formula:
 * moves = sum(max - nums[i]) for all i
 *       = n × max - sum(nums)
 * 
 * Why This Formula?
 * 
 * Final state: all elements = max
 * Final sum = n × max
 * 
 * Initial sum = sum(nums)
 * 
 * Moves = Final sum - Initial sum
 *       = n × max - sum(nums)
 * 
 * Each move increases sum by 1, so difference = moves!
 * 
 * Example Execution: [1,2,3]
 * 
 * Step 1: Find max and sum
 * max = 1, sum = 0
 * 
 * Loop:
 *   num=1: max=1, sum=1
 *   num=2: max=2, sum=3
 *   num=3: max=3, sum=6
 * 
 * Final: max=3, sum=6
 * 
 * Step 2: Calculate moves
 * moves = 3 × 3 - 6
 *       = 9 - 6
 *       = 3
 * 
 * Return 3
 * 
 * Example: [1,1,1,5]
 * 
 * max = 5
 * sum = 1 + 1 + 1 + 5 = 8
 * 
 * moves = 5 × 4 - 8
 *       = 20 - 8
 *       = 12
 * 
 * Verification:
 * - Three 1's need 4 moves each: 3 × 4 = 12 ✓
 * 
 * Why Use Long?
 * 
 * Overflow prevention:
 * - nums.length can be 10^5
 * - nums[i] can be 10^9
 * - sum could be 10^5 × 10^9 = 10^14
 * - Integer.MAX_VALUE ≈ 2 × 10^9
 * 
 * Must use long to handle large values!
 * 
 * Example causing overflow:
 * nums = [10^9, 10^9, ..., 10^9] (10^5 elements)
 * sum = 10^14 > Integer.MAX_VALUE
 * 
 * Comparison with Problems 453 and 462:
 * 
 * Problem 453:
 * - Operation: increment (n-1) elements by 1
 * - Insight: equivalent to decrementing 1
 * - Target: minimum
 * - Formula: sum(num - min)
 * - Time: O(n)
 * - Complexity: requires counter-intuitive insight
 * 
 * Problem 462:
 * - Operation: inc OR dec any element by 1
 * - Insight: median minimizes absolute distances
 * - Target: median
 * - Formula: sum(|num - median|)
 * - Time: O(n log n) - needs sorting
 * - Complexity: requires statistical knowledge
 * 
 * Problem 3736:
 * - Operation: increment any element by 1
 * - Insight: can only increase
 * - Target: maximum
 * - Formula: sum(max - num)
 * - Time: O(n)
 * - Complexity: most intuitive!
 * 
 * This is the SIMPLEST variant:
 * - Direct operation (increase)
 * - Obvious target (maximum)
 * - Clear reasoning
 * 
 * Edge Cases:
 * 
 * 1. All equal: [5,5,5]
 *    max = 5, sum = 15
 *    moves = 5×3 - 15 = 0 ✓
 * 
 * 2. Single element: [42]
 *    max = 42, sum = 42
 *    moves = 42×1 - 42 = 0 ✓
 * 
 * 3. Two elements: [1,3]
 *    max = 3, sum = 4
 *    moves = 3×2 - 4 = 2
 *    (1 needs 2 moves to reach 3) ✓
 * 
 * 4. Large differences: [1, 1000000]
 *    max = 1000000, sum = 1000001
 *    moves = 1000000×2 - 1000001 = 999999 ✓
 * 
 * 5. Large values (overflow check):
 *    [10^9, 10^9] (simplified)
 *    max = 10^9, sum = 2×10^9
 *    moves = 10^9×2 - 2×10^9 = 0
 *    Using long prevents overflow in calculation ✓
 * 
 * Pattern: Constraint-Based Target
 * 
 * Key lesson:
 * - Operation constraints determine target
 * - Can only increase → must reach max
 * - Can only decrease → must reach min
 * - Can inc/dec → optimal is median
 * 
 * Alternative Implementation (Two Loops):
 * 
 * public int minMoves(int[] nums) {
 *     // Find maximum
 *     int max = nums[0];
 *     for (int num : nums) {
 *         max = Math.max(max, num);
 *     }
 *     
 *     // Calculate moves
 *     int moves = 0;
 *     for (int num : nums) {
 *         moves += max - num;
 *     }
 *     
 *     return moves;
 * }
 * 
 * Two loops instead of one
 * Same O(n) time
 * Your single-loop is more efficient!
 * 
 * Why Cast to Long Before Multiply?
 * 
 * // Wrong:
 * max * nums.length  // Could overflow BEFORE cast
 * 
 * // Right:
 * max * (long) nums.length  // Cast first, then multiply
 * 
 * Ensures calculation happens in long arithmetic
 * 
 * Time Complexity: O(n)
 *   - Single pass through array
 *   - Find max: O(n)
 *   - Calculate sum: O(n)
 *   - Both in same loop: O(n)
 *   - Final calculation: O(1)
 * 
 * Space Complexity: O(1)
 *   - Only two variables: max and sum
 *   - No extra arrays or structures
 *   - Constant space
 */
class Solution {
    public int minMoves(int[] nums) {
        // Track maximum element and sum simultaneously
        int max = nums[0];
        long sum = 0;  // Use long to prevent overflow
        
        // Single pass: find max and calculate sum
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }
        
        // Formula: moves = n × max - sum
        // Each element needs (max - element) moves
        // Total = sum of (max - element) for all elements
        //       = n × max - sum of elements
        
        // Cast max to long before multiplication to prevent overflow
        // Then subtract sum
        // Cast result back to int (guaranteed to fit per problem)
        return (int) (max * (long) nums.length - sum);
    }
}
