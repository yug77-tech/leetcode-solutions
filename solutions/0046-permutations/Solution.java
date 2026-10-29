import java.util.*;

/**
 * LeetCode Problem 46: Permutations
 * 
 * Backtracking with in-place swapping
 * 
 * Problem:
 * Generate all possible permutations of distinct integers.
 * 
 * Key Insight:
 * Use BACKTRACKING to explore all possibilities!
 * - Fix each element at each position
 * - Recursively permute remaining elements
 * - Swap back to try different choices (backtrack)
 * 
 * Core Algorithm:
 * 1. Base case: index reaches end → permutation complete
 * 2. Loop through positions from index to end
 * 3. For each position i:
 *    - Swap nums[index] with nums[i] (make choice)
 *    - Recursively permute rest (explore)
 *    - Swap back (undo choice, backtrack)
 * 
 * Visual Tree for [1,2,3]:
 * 
 *                     [1,2,3]
 *                     index=0
 *          ┌────────────┼────────────┐
 *          │            │            │
 *     Fix 1 at 0   Fix 2 at 0   Fix 3 at 0
 *     [1,2,3]      [2,1,3]      [3,2,1]
 *     index=1      index=1      index=1
 *     ┌──┴──┐      ┌──┴──┐      ┌──┴──┐
 *     │     │      │     │      │     │
 *   Fix 2 Fix 3  Fix 1 Fix 3  Fix 2 Fix 1
 *   [1,2,3][1,3,2][2,1,3][2,3,1][3,2,1][3,1,2]
 * 
 * Result: 6 permutations (3! = 6)
 * 
 * Example Execution: [1,2]
 * 
 * backtrack([1,2], index=0)
 *   i=0: Swap nums[0]↔nums[0] → [1,2] (no change)
 *     backtrack([1,2], index=1)
 *       i=1: Swap nums[1]↔nums[1] → [1,2]
 *         backtrack([1,2], index=2)
 *           index==2 → Add [1,2] ✓
 *         Swap back → [1,2]
 *     Return
 *   Swap back → [1,2]
 *   
 *   i=1: Swap nums[0]↔nums[1] → [2,1]
 *     backtrack([2,1], index=1)
 *       i=1: Swap nums[1]↔nums[1] → [2,1]
 *         backtrack([2,1], index=2)
 *           index==2 → Add [2,1] ✓
 *         Swap back → [2,1]
 *     Return
 *   Swap back → [1,2]
 * 
 * Result: [[1,2], [2,1]]
 * 
 * Why Swap Back? (Backtracking)
 * 
 * Without swap back:
 *   After trying [2,1,3], array stays [2,1,3]
 *   Can't try [3,2,1] next!
 * 
 * With swap back:
 *   Try [2,1,3], swap back to [1,2,3]
 *   Now can try [3,2,1]
 * 
 * Backtracking = "undo choice to try other possibilities"
 * 
 * Why Loop from index to end?
 * 
 * index splits array into two parts:
 * [fixed... | ...to permute]
 *  0 to      index to
 *  index-1   n-1
 * 
 * We fix elements at positions [0...index-1]
 * We permute elements at positions [index...n-1]
 * 
 * Example at index=1 for [1,2,3]:
 * [1 | 2,3]  → Fixed 1, permute 2,3
 * 
 * Try 2 at index=1: [1,2,3]
 * Try 3 at index=1: [1,3,2]
 * 
 * Why Create New List in Base Case?
 * 
 * Arrays are mutable and shared across recursion!
 * If we add nums directly:
 *   - All permutations would point to same array
 *   - By end, all would show final state
 * 
 * Solution: Create new list (copy current state)
 * Each permutation gets its own independent list
 * 
 * Edge Cases:
 * 1. Single element: [1]
 *    index=0, i=0, swap (no change)
 *    index=1, add [1]
 *    Result: [[1]]
 * 
 * 2. Two elements: [1,2]
 *    Generates [1,2] and [2,1]
 *    Result: [[1,2],[2,1]]
 * 
 * 3. Empty array: nums=[]
 *    index=0==length, add []
 *    Result: [[]]
 * 
 * Number of Permutations:
 * - n elements have n! permutations
 * - n=1: 1! = 1
 * - n=2: 2! = 2
 * - n=3: 3! = 6
 * - n=4: 4! = 24
 * - n=6: 6! = 720 (constraint maximum)
 * 
 * Why This Approach is Elegant:
 * 
 * 1. Space Efficient:
 *    - No visited array needed
 *    - In-place swapping
 *    - Only recursion stack O(n)
 * 
 * 2. Clean Logic:
 *    - Simple swap operation
 *    - Clear base case
 *    - Natural recursion
 * 
 * 3. Complete:
 *    - Generates all n! permutations
 *    - No duplicates (elements distinct)
 * 
 * Alternative: Visited Array Approach
 * - Maintain boolean[] used
 * - Build permutation in separate list
 * - More intuitive but uses O(n) extra space
 * 
 * Your swap approach is optimal!
 * 
 * Common Mistakes:
 * 
 * 1. Forgetting to swap back:
 *    swap(index, i);
 *    backtrack(index+1);
 *    // Missing: swap back!
 * 
 * 2. Adding nums reference instead of copy:
 *    result.add(nums); // Wrong!
 *    Need to create new list
 * 
 * 3. Starting loop from 0:
 *    for (i=0; ...) // Wrong!
 *    Should start from index
 * 
 * Pattern: Backtracking Template
 * 
 * def backtrack(state):
 *     if complete(state):
 *         result.add(copy(state))
 *         return
 *     
 *     for choice in choices:
 *         make_choice(state, choice)
 *         backtrack(state)
 *         undo_choice(state, choice)  # Backtrack!
 * 
 * Time Complexity: O(n! × n)
 *   - Total permutations: n!
 *   - Copying each to list: O(n)
 *   - Total: O(n! × n)
 *   - For n=3: 3! × 3 = 18 operations
 * 
 * Space Complexity: O(n)
 *   - Recursion depth: O(n)
 *   - Not counting output list
 *   - No extra arrays for visited/current
 */
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        // Result list to store all permutations
        List<List<Integer>> result = new ArrayList<>();
        
        // Start backtracking from index 0
        backtrack(nums, 0, result);
        
        return result;
    }

    /**
     * Backtracking helper function
     * 
     * @param nums   The array being permuted (modified in-place)
     * @param index  Current position to fix element
     * @param result List to store all permutations
     */
    private void backtrack(int[] nums, int index, List<List<Integer>> result) {
        // Base case: reached end of array
        // Current arrangement is a complete permutation
        if (index == nums.length) {
            // IMPORTANT: Create NEW list (copy current state)
            // Can't add nums directly (it's mutable and shared)
            List<Integer> list = new ArrayList<>();
            for (int num : nums) {
                list.add(num);
            }
            result.add(list);
            return;
        }

        // Recursive case: try each element at current position
        // Loop from index to end (permute remaining elements)
        for (int i = index; i < nums.length; i++) {
            // Make choice: swap current index with position i
            // This fixes nums[i] at position index
            int temp = nums[index];
            nums[index] = nums[i];
            nums[i] = temp;

            // Explore: recursively permute remaining elements
            // Move to next position (index + 1)
            backtrack(nums, index + 1, result);

            // Undo choice: swap back (BACKTRACK!)
            // This restores original state to try other choices
            // Critical for exploring all possibilities
            temp = nums[index];
            nums[index] = nums[i];
            nums[i] = temp;
        }
        
        // After loop, all possibilities at this index explored
        // Return to previous level
    }
}
