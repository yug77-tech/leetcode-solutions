import java.util.*;

/**
 * LeetCode Problem 448: Find All Numbers Disappeared in an Array
 * 
 * In-place marking using negation
 * 
 * Problem:
 * Given array nums where nums[i] in [1, n],
 * find all numbers in [1, n] that don't appear in nums.
 * 
 * Follow-up: O(n) time, O(1) space (excluding output)
 * 
 * Key Insight:
 * Use array ITSELF as hash map!
 * - Mark visited numbers by negating values at their indices
 * - Positive values indicate missing numbers
 * 
 * Brilliant Technique: Index as Hash Map
 * 
 * Given nums[i] in range [1, n]:
 * - Array has n positions (indices 0 to n-1)
 * - Number k maps to index (k-1)
 * - Use sign to mark presence
 * 
 * Algorithm:
 * 
 * Phase 1: Mark present numbers
 * For each number num:
 *   index = abs(num) - 1
 *   Mark nums[index] negative
 * 
 * Phase 2: Find missing numbers
 * For each index i:
 *   If nums[i] > 0:
 *     Number (i+1) is missing
 * 
 * Example: [4,3,2,7,8,2,3,1]
 * 
 * Phase 1: Mark present
 * 
 * Process 4: index=3, mark nums[3]
 *   [4, 3, 2, -7, 8, 2, 3, 1]
 * 
 * Process 3: index=2, mark nums[2]
 *   [4, 3, -2, -7, 8, 2, 3, 1]
 * 
 * Process -2: index=1, mark nums[1]
 *   [4, -3, -2, -7, 8, 2, 3, 1]
 * 
 * Process -7: index=6, mark nums[6]
 *   [4, -3, -2, -7, 8, 2, -3, 1]
 * 
 * Process 8: index=7, mark nums[7]
 *   [4, -3, -2, -7, 8, 2, -3, -1]
 * 
 * Process 2: index=1, already negative
 *   [4, -3, -2, -7, 8, 2, -3, -1]
 * 
 * Process -3: index=2, already negative
 *   [4, -3, -2, -7, 8, 2, -3, -1]
 * 
 * Process -1: index=0, mark nums[0]
 *   [-4, -3, -2, -7, 8, 2, -3, -1]
 * 
 * Phase 2: Find missing
 * 
 * i=0: nums[0]=-4 (negative) → 1 present
 * i=1: nums[1]=-3 (negative) → 2 present
 * i=2: nums[2]=-2 (negative) → 3 present
 * i=3: nums[3]=-7 (negative) → 4 present
 * i=4: nums[4]=8 (positive) → 5 MISSING ✓
 * i=5: nums[5]=2 (positive) → 6 MISSING ✓
 * i=6: nums[6]=-3 (negative) → 7 present
 * i=7: nums[7]=-1 (negative) → 8 present
 * 
 * Result: [5, 6]
 * 
 * Why Math.abs?
 * 
 * During marking, may encounter already-negative values:
 * nums = [4, -3, 2, ...]
 * 
 * When processing nums[1] = -3:
 * - Need to find index for number 3
 * - Can't use -3 directly (negative index invalid!)
 * - Use abs(-3) = 3, then index = 3-1 = 2
 * 
 * This handles both positive and negative values correctly.
 * 
 * Why Check nums[index] > 0?
 * 
 * Avoid double negation:
 * 
 * First time seeing number 2:
 * nums[1] = 3 > 0 → negate to -3
 * 
 * Second time seeing number 2 (duplicate):
 * nums[1] = -3 < 0 → already marked, don't negate again!
 * 
 * Without check:
 * -3 → -(-3) = 3 → would lose marking!
 * 
 * Mapping Explained:
 * 
 * Number to Index:
 * Number 1 → Index 0
 * Number 2 → Index 1
 * Number k → Index k-1
 * 
 * Index to Number:
 * Index 0 → Number 1
 * Index 1 → Number 2
 * Index i → Number i+1
 * 
 * This bijection allows using array as hash map!
 * 
 * Edge Cases:
 * 
 * 1. All present: [1,2,3]
 *    All marked negative
 *    No positive values
 *    Result: [] (empty)
 * 
 * 2. All missing except one: [1,1,1]
 *    Only index 0 marked
 *    Indices 1,2 remain positive
 *    Result: [2,3]
 * 
 * 3. Duplicates: [2,2]
 *    Both mark index 1
 *    Index 0 remains positive
 *    Result: [1]
 * 
 * 4. Single element: [1]
 *    Mark index 0
 *    All indices marked
 *    Result: []
 * 
 * Why This is Brilliant:
 * 
 * 1. O(1) Space:
 *    - No HashSet needed
 *    - Uses input array for marking
 *    - Only output list (doesn't count)
 * 
 * 2. O(n) Time:
 *    - Two passes through array
 *    - Each operation O(1)
 * 
 * 3. Non-destructive:
 *    - Can restore original if needed
 *    - Just make all values positive again
 * 
 * 4. Simple Logic:
 *    - Mark phase: negate
 *    - Find phase: check sign
 * 
 * Alternative: HashSet (Extra Space)
 * 
 * Set<Integer> set = new HashSet<>();
 * for (int num : nums) set.add(num);
 * 
 * for (int i = 1; i <= n; i++) {
 *     if (!set.contains(i)) result.add(i);
 * }
 * 
 * Pros: Simpler logic
 * Cons: O(n) extra space
 * 
 * Alternative: Cyclic Sort (More Complex)
 * 
 * Place each number at correct index:
 * nums[0] should be 1
 * nums[1] should be 2
 * nums[i] should be i+1
 * 
 * After sorting, check each position.
 * 
 * Pros: O(1) space
 * Cons: More complex swapping logic
 * 
 * Your negation approach is OPTIMAL and ELEGANT!
 * 
 * Pattern: In-Place Index Marking
 * 
 * Use when:
 * - Array values in range [1, n]
 * - Array length is n
 * - Need O(1) space
 * - Can modify input
 * 
 * Similar problems:
 * - Find the Duplicate Number (287)
 * - First Missing Positive (41)
 * - Set Mismatch (645)
 * - Find All Duplicates (442)
 * 
 * Time Complexity: O(n)
 *   - First loop: O(n) to mark
 *   - Second loop: O(n) to find
 *   - Total: O(n)
 * 
 * Space Complexity: O(1)
 *   - No extra space (except output)
 *   - Modifies input in-place
 *   - Output list doesn't count per problem
 */
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ans = new ArrayList<>();

        // Phase 1: Mark present numbers
        // For each number, mark its index as visited by negating
        for (int i = 0; i < nums.length; i++) {
            // Get the number (use abs in case already negative)
            // Map number to index: number k → index (k-1)
            int index = Math.abs(nums[i]) - 1;

            // If not already marked (positive), mark it negative
            // This indicates the number (index+1) exists in array
            if (nums[index] > 0) {
                nums[index] = -nums[index];
            }
            // If already negative, it means we saw this number before (duplicate)
            // Don't change it (avoid double negation)
        }

        // Phase 2: Find missing numbers
        // Positive values indicate missing numbers
        for (int i = 0; i < nums.length; i++) {
            // If value at index i is positive,
            // it means number (i+1) was never marked
            // Therefore, number (i+1) is missing
            if (nums[i] > 0) {
                ans.add(i + 1);
            }
        }

        return ans;
    }
}
