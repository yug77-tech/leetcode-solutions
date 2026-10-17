/**
 * LeetCode Problem 771: Jewels and Stones
 * 
 * Array-based hash set solution for optimal performance
 * 
 * Problem:
 * Count how many stones are jewels.
 * Letters are case-sensitive (a != A).
 * 
 * Algorithm:
 * 1. Use boolean array as hash set for jewels
 * 2. Mark jewel characters as true
 * 3. Count stones that are marked as jewels
 * 
 * Why array instead of HashSet?
 * - ASCII characters have limited range (0-127)
 * - Direct array indexing is faster than hashing
 * - No hash collision handling needed
 * - Better cache locality
 * - Still O(1) lookup time
 * 
 * Array size = 128:
 * - Covers all ASCII characters (0-127)
 * - 'A' = 65, 'Z' = 90
 * - 'a' = 97, 'z' = 122
 * - Size 128 ensures all letters fit
 * 
 * Example: jewels = "aA", stones = "aAAbbbb"
 * 
 * Step 1: Build jewel set
 *   set[97] = true  (for 'a')
 *   set[65] = true  (for 'A')
 * 
 * Step 2: Count jewels in stones
 *   'a' → set[97] = true  → count++  (now 1)
 *   'A' → set[65] = true  → count++  (now 2)
 *   'A' → set[65] = true  → count++  (now 3)
 *   'b' → set[98] = false → no change
 *   'b' → set[98] = false → no change
 *   'b' → set[98] = false → no change
 *   'b' → set[98] = false → no change
 * 
 * Result: 3
 * 
 * Space Optimization Note:
 * - Array of 128 booleans = 128 bytes
 * - This is constant space O(1)
 * - Could use smaller array (58 for A-z range)
 * - But 128 is simpler (no offset calculation)
 * 
 * Performance Comparison:
 * Array[128]:
 *   - Access: O(1) direct index
 *   - Memory: 128 bytes (constant)
 *   - Speed: Very fast (no hashing)
 * 
 * HashSet:
 *   - Access: O(1) with hash computation
 *   - Memory: Overhead + n entries
 *   - Speed: Slower (hash + collision handling)
 * 
 * For ASCII characters, array is superior!
 * 
 * Time Complexity: O(n + m)
 *   - n = jewels.length() (build set)
 *   - m = stones.length() (count jewels)
 * 
 * Space Complexity: O(1)
 *   - Array of size 128 is constant
 *   - Not dependent on input size
 */
class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        // Create boolean array as hash set
        // Size 128 covers all ASCII characters
        boolean[] set = new boolean[128];

        // Mark all jewel characters as true
        for (char c : jewels.toCharArray()) {
            set[c] = true;
        }

        // Count stones that are jewels
        int count = 0;
        for (char c : stones.toCharArray()) {
            if (set[c]) {
                count++;
            }
        }

        return count;
    }
}
