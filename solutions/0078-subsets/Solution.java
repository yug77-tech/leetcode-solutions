import java.util.*;

/**
 * LeetCode Problem 78: Subsets
 * 
 * Backtracking to generate power set
 * 
 * Problem:
 * Generate all possible subsets (power set) of unique elements.
 * 
 * Key Insight:
 * For each element, we have TWO choices: INCLUDE or EXCLUDE
 * This creates a binary decision tree with 2^n possibilities
 * 
 * Power Set:
 * - Set with n elements has 2^n subsets
 * - Includes empty set [] and full set
 * - For [1,2,3]: 2^3 = 8 subsets
 * 
 * Decision Tree for [1,2,3]:
 * 
 *                         []
 *                     /        \
 *               Include 1    Exclude 1
 *                 [1]            []
 *               /    \         /    \
 *            +2      -2      +2      -2
 *          [1,2]   [1]     [2]      []
 *          /  \    /  \    /  \     /  \
 *        +3  -3  +3  -3  +3  -3   +3  -3
 *     [1,2,3][1,2][1,3][1][2,3][2][3][]
 * 
 * Result: 8 subsets
 * 
 * Algorithm:
 * 1. Add current subset to result (EVERY subset is valid!)
 * 2. For each remaining element (from index to end):
 *    - Include element in current subset
 *    - Recursively explore with next index
 *    - Exclude element (backtrack by removing)
 * 
 * Critical Differences from Permutations:
 * 
 * 1. Add at EVERY recursion level (not just base case)
 *    - All subsets valid, even partial ones
 *    - Empty set [] is valid
 * 
 * 2. Start loop at INDEX (not 0)
 *    - Prevents duplicates: [1,2] and [2,1] are same subset
 *    - Maintains increasing order
 *    - Once skip element, never revisit
 * 
 * 3. No base case check needed
 *    - Loop naturally terminates when index >= n
 *    - Empty loop = leaf node
 * 
 * Example Execution: [1,2]
 * 
 * backtrack(index=0, current=[])
 *   Add [] → result = [[]]
 *   
 *   i=0: Include 1
 *     current = [1]
 *     backtrack(index=1, current=[1])
 *       Add [1] → result = [[],[1]]
 *       
 *       i=1: Include 2
 *         current = [1,2]
 *         backtrack(index=2, current=[1,2])
 *           Add [1,2] → result = [[],[1],[1,2]]
 *           Loop: i=2 >= 2, no iterations
 *         Remove 2 → [1]
 *     Remove 1 → []
 *   
 *   i=1: Include 2 (skip 1)
 *     current = [2]
 *     backtrack(index=2, current=[2])
 *       Add [2] → result = [[],[1],[1,2],[2]]
 *       Loop: i=2 >= 2, no iterations
 *     Remove 2 → []
 * 
 * Final: [[],[1],[1,2],[2]]
 * 
 * Why Add at Beginning?
 * 
 * Every subset is valid:
 * - [] is valid (empty set)
 * - [1] is valid (partial)
 * - [1,2] is valid (partial)
 * - [1,2,3] is valid (complete)
 * 
 * Must capture state at EVERY recursion level!
 * 
 * Why Start Loop at Index?
 * 
 * Prevents duplicates:
 * - If started at 0: would get [1,2] and [2,1]
 * - These are same subset (order doesn't matter)
 * - Starting at index ensures increasing order
 * - Once skip element, never revisit it
 * 
 * Example at index=1:
 * - Can add: 2, 3 (elements at index >= 1)
 * - Can't add: 1 (already processed at index=0)
 * 
 * Why Create New List?
 * 
 * current list is mutable and shared!
 * - Modified throughout recursion
 * - Must create copy for result
 * - Each subset needs independent list
 * 
 * Edge Cases:
 * 
 * 1. Single element: [1]
 *    backtrack(0, [])
 *      Add [] → [[]]
 *      i=0: Add 1 → [1]
 *        backtrack(1, [1])
 *          Add [1] → [[],[1]]
 *          Loop done
 *        Remove 1
 *    Result: [[],[1]]
 * 
 * 2. Empty array: [] (if allowed)
 *    backtrack(0, [])
 *      Add [] → [[]]
 *      Loop: i=0 >= 0, no iterations
 *    Result: [[]]
 * 
 * 3. Three elements: [1,2,3]
 *    Generates 2^3 = 8 subsets
 * 
 * Comparison: Subsets vs Permutations
 * 
 * Subsets:
 * - Order doesn't matter: [1,2] = [2,1]
 * - Add at every level
 * - Start loop at index
 * - Count: 2^n
 * 
 * Permutations:
 * - Order matters: [1,2] ≠ [2,1]
 * - Add only at base case
 * - Start loop at 0, use swaps
 * - Count: n!
 * 
 * Alternative Approaches:
 * 
 * 1. Iterative (Build from Previous):
 *    Start with [[]]
 *    For each element:
 *      Copy all existing subsets
 *      Add element to copies
 *      Add copies to result
 *    
 *    Example: [1,2]
 *    Start: [[]]
 *    Add 1: [[],[1]]
 *    Add 2: [[],[1],[2],[1,2]]
 * 
 * 2. Bit Manipulation:
 *    Iterate 0 to 2^n - 1
 *    Each number represents a subset
 *    Bit i = 1 means include nums[i]
 *    
 *    Example: [1,2] (n=2)
 *    0 (00): [] - exclude both
 *    1 (01): [1] - include first
 *    2 (10): [2] - include second
 *    3 (11): [1,2] - include both
 * 
 * Your backtracking is most intuitive!
 * 
 * Number of Subsets: 2^n
 * - Each element: include or exclude (2 choices)
 * - n elements: 2 × 2 × ... × 2 = 2^n
 * 
 * Examples:
 * n=1: 2^1 = 2 → [], [1]
 * n=2: 2^2 = 4 → [], [1], [2], [1,2]
 * n=3: 2^3 = 8 → [], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]
 * n=10: 2^10 = 1024 subsets (constraint max)
 * 
 * Common Mistakes:
 * 
 * 1. Only adding at base case:
 *    if (index == nums.length) result.add(...)
 *    This only gets complete set [1,2,3]
 *    Misses all partial subsets!
 * 
 * 2. Starting loop from 0:
 *    for (i = 0; i < n; i++)
 *    Creates duplicates: [1,2] and [2,1]
 * 
 * 3. Adding current reference:
 *    result.add(current)
 *    All subsets point to same list!
 * 
 * Time Complexity: O(n × 2^n)
 *   - Total subsets: 2^n
 *   - Copying each: O(n)
 *   - Total: O(n × 2^n)
 *   - For n=3: 3 × 8 = 24 operations
 * 
 * Space Complexity: O(n)
 *   - Recursion depth: O(n)
 *   - Current list: O(n)
 *   - Not counting output (2^n lists)
 */
class Solution {

    public List<List<Integer>> subsets(int[] nums) {
        // Result list to store all subsets
        List<List<Integer>> result = new ArrayList<>();
        
        // Start backtracking from index 0 with empty subset
        backtrack(nums, 0, new ArrayList<>(), result);
        
        return result;
    }

    /**
     * Backtracking helper to generate all subsets
     * 
     * @param nums    The input array
     * @param index   Current position (elements before are decided)
     * @param current Current subset being built
     * @param result  List storing all subsets
     */
    private void backtrack(int[] nums, int index, List<Integer> current,
                           List<List<Integer>> result) {
        
        // CRITICAL: Add current subset to result
        // Every subset is valid (including empty set)
        // This is why we add at EVERY recursion level
        // Different from permutations (only complete ones)
        result.add(new ArrayList<>(current));
        
        // Try including each remaining element
        // Start from index to avoid duplicates
        // (prevents generating [1,2] and [2,1] as separate subsets)
        for (int i = index; i < nums.length; i++) {
            // Include nums[i] in current subset
            current.add(nums[i]);
            
            // Recursively explore with next index
            // i+1 ensures we only consider elements after current
            // This maintains increasing order and prevents duplicates
            backtrack(nums, i + 1, current, result);
            
            // Exclude nums[i] from current subset (backtrack)
            // Remove last element to try other possibilities
            // This is the "undo" step in backtracking
            current.remove(current.size() - 1);
        }
        
        // When loop ends:
        // - All subsets starting with current prefix explored
        // - Return to previous recursion level
    }
}
