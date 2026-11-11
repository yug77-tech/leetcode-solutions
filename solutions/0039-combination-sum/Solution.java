import java.util.*;

/**
 * LeetCode Problem 39: Combination Sum
 * 
 * Backtracking with unlimited reuse
 * 
 * Problem:
 * Find all unique combinations where numbers sum to target.
 * Can use same number unlimited times.
 * 
 * Key Insight:
 * Classic backtracking with TWO key decisions:
 * 1. Include current number (can use again)
 * 2. Exclude current number (move to next)
 * 
 * Unique vs Permutation:
 * - [2,2,3] is one combination
 * - [2,3,2] and [3,2,2] are same (we want unique combinations)
 * - Solution: only move forward in candidates array
 * 
 * Algorithm:
 * 
 * Base cases:
 * 1. target == 0: found valid combination, add to result
 * 2. target < 0: exceeded target, invalid
 * 3. index == length: no more candidates
 * 
 * Recursive cases:
 * For current candidate:
 * 1. Include it:
 *    - Add to current list
 *    - Recursively search with SAME index (allow reuse)
 *    - Subtract from target
 * 2. Exclude it:
 *    - Remove from current list
 *    - Move to NEXT index
 *    - Same target
 * 
 * Example: candidates=[2,3,6,7], target=7
 * 
 * Decision tree:
 *                     []
 *                     ↓
 *             ┌───────┴───────┐
 *          Use 2            Skip 2
 *            [2]              []
 *       ┌─────┴─────┐         ↓
 *    Use 2      Skip 2    Use 3...
 *     [2,2]      [2]
 *   ┌───┴───┐   ┌─┴─┐
 * Use 2  Skip [2,3]
 * [2,2,2]     ↓
 *   ↓       Add 3
 * target=1  [2,2,3]
 * invalid     ↓
 *          target=0
 *          Found! ✓
 * 
 * Also finds [7] when directly using candidates[3]
 * 
 * Why Stay at Same Index?
 * 
 * Allow unlimited reuse:
 * backtrack(candidates, target - candidates[index], index, ...)
 *                                                   ↑ Same index!
 * 
 * Example: target=8, candidates=[2]
 * - Use 2: target=6, index=0
 * - Use 2: target=4, index=0
 * - Use 2: target=2, index=0
 * - Use 2: target=0, index=0 → Found [2,2,2,2]
 * 
 * Why Move to Next Index (Not Previous)?
 * 
 * Avoid duplicates:
 * 
 * candidates=[2,3], target=5
 * 
 * With forward-only:
 * - [2,3] found (use 2, then 3)
 * - [3,2] NOT generated (when at 3, can't go back to 2)
 * 
 * If could go backward:
 * - Both [2,3] and [3,2] generated
 * - These are same combination (duplicates)!
 * 
 * Backtracking Pattern:
 * 
 * 1. Make choice: current.add(value)
 * 2. Explore: recursive call
 * 3. Undo choice: current.remove(last)
 * 4. Try next choice
 * 
 * This is the CORE backtracking pattern!
 * 
 * Why Create New List When Adding to Result?
 * 
 * current list is MUTABLE and SHARED:
 * 
 * Wrong:
 * if (target == 0) ans.add(current);
 * - Adds reference to current
 * - current keeps changing
 * - All results point to same list
 * - Final result: all lists are same!
 * 
 * Right:
 * if (target == 0) ans.add(new ArrayList<>(current));
 * - Creates independent copy
 * - Copy captures current state
 * - Each result is independent
 * 
 * Example Execution: [2,3], target=5
 * 
 * backtrack(target=5, index=0, current=[])
 *   
 *   Include candidates[0]=2:
 *     current = [2]
 *     backtrack(target=3, index=0, current=[2])
 *     
 *       Include candidates[0]=2:
 *         current = [2,2]
 *         backtrack(target=1, index=0, current=[2,2])
 *         
 *           Include candidates[0]=2:
 *             current = [2,2,2]
 *             backtrack(target=-1, index=0, current=[2,2,2])
 *             target < 0 → return
 *           Remove 2: current = [2,2]
 *           
 *           Exclude candidates[0]:
 *             backtrack(target=1, index=1, current=[2,2])
 *             
 *               Include candidates[1]=3:
 *                 3 > 1, invalid
 *               
 *               Exclude candidates[1]:
 *                 index=2, out of bounds
 *             return
 *         
 *       Remove 2: current = [2]
 *       
 *       Exclude candidates[0]:
 *         backtrack(target=3, index=1, current=[2])
 *         
 *           Include candidates[1]=3:
 *             current = [2,3]
 *             backtrack(target=0, index=1, current=[2,3])
 *             target == 0 → Add [2,3] ✓
 *           Remove 3: current = [2]
 *           
 *           Exclude candidates[1]:
 *             index=2, out of bounds
 *         
 *   Remove 2: current = []
 *   
 *   Exclude candidates[0]:
 *     backtrack(target=5, index=1, current=[])
 *     (Continues exploring without 2...)
 * 
 * Result: [[2,2,2,2], [2,3,3], ...] (depends on candidates)
 * 
 * Edge Cases:
 * 
 * 1. No solution: target=1, candidates=[2]
 *    All candidates > target
 *    No valid combinations
 *    Result: []
 * 
 * 2. Single number: target=7, candidates=[7]
 *    Direct match
 *    Result: [[7]]
 * 
 * 3. Multiple uses: target=8, candidates=[2]
 *    Use 2 four times
 *    Result: [[2,2,2,2]]
 * 
 * 4. Multiple combinations:
 *    Various paths lead to target
 *    All captured by backtracking
 * 
 * Optimization: Sorting
 * 
 * Can sort candidates first:
 * - Enables early termination
 * - If candidate > target, skip rest
 * 
 * Arrays.sort(candidates);
 * if (candidates[i] > target) break;
 * 
 * Not necessary but can help!
 * 
 * Common Mistakes:
 * 
 * 1. Not creating new list:
 *    ans.add(current) instead of ans.add(new ArrayList<>(current))
 * 
 * 2. Using index+1 for reuse:
 *    Prevents using same number multiple times
 * 
 * 3. Forgetting to remove:
 *    Missing current.remove(...) breaks backtracking
 * 
 * 4. Checking target < 0 in wrong place:
 *    Better to check after subtraction
 * 
 * Pattern: Backtracking with Reuse
 * 
 * Use when:
 * - Need all combinations (not just count)
 * - Elements can be reused
 * - Avoid duplicates by ordering
 * 
 * Similar problems:
 * - Combination Sum II (40)
 * - Combination Sum III (216)
 * - Letter Combinations (17)
 * - Subsets (78)
 * 
 * Time Complexity: O(N^(T/M))
 *   - N = candidates.length
 *   - T = target
 *   - M = minimum candidate value
 *   - Each level: N choices
 *   - Max depth: T/M (using smallest repeatedly)
 * 
 * Space Complexity: O(T/M)
 *   - Recursion depth: O(T/M)
 *   - Current list: O(T/M) elements max
 *   - Not counting output space
 */
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), ans);
        return ans;
    }

    /**
     * Backtracking helper to find all combinations
     * 
     * @param candidates  Array of candidate numbers
     * @param target      Remaining target sum
     * @param index       Current position in candidates
     * @param current     Current combination being built
     * @param ans         Result list of all valid combinations
     */
    private void backtrack(int[] candidates, int target, int index,
                           List<Integer> current, List<List<Integer>> ans) {
        // Base case 1: Found valid combination
        if (target == 0) {
            // IMPORTANT: Create new list (don't add reference)
            // current is mutable and shared across recursion
            ans.add(new ArrayList<>(current));
            return;
        }

        // Base case 2 & 3: Invalid states
        // - Out of candidates (index == length)
        // - Exceeded target (target < 0)
        if (index == candidates.length || target < 0) {
            return;
        }

        // Recursive case 1: Include current candidate
        // Can use same number multiple times, so stay at same index
        current.add(candidates[index]);
        backtrack(candidates, target - candidates[index], index, current, ans);

        // Backtrack: undo the choice
        current.remove(current.size() - 1);
        
        // Recursive case 2: Exclude current candidate
        // Move to next candidate to avoid revisiting
        // This ensures combinations are unique (no [2,3] and [3,2])
        backtrack(candidates, target, index + 1, current, ans);
    }
}
