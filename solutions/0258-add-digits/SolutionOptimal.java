/**
 * LeetCode Problem 258: Add Digits
 * 
 * O(1) Mathematical solution using Digital Root formula
 * 
 * Key Insight:
 * The result follows a pattern based on modulo 9 (Digital Root)
 * 
 * Pattern observation:
 * 1→1, 2→2, ..., 9→9
 * 10→1, 11→2, ..., 18→9
 * 19→1, 20→2, ..., 27→9
 * 
 * Formula: 1 + (num - 1) % 9
 * 
 * Why this works:
 * - For num = 0: result is 0 (special case)
 * - For num % 9 = 0 (and num ≠ 0): result is 9
 * - Otherwise: result is num % 9
 * 
 * The formula elegantly handles all cases:
 * - (num - 1) % 9 gives 0-8
 * - Adding 1 gives 1-9
 * - Special handling for num=0
 * 
 * Time Complexity: O(1) - constant time calculation
 * Space Complexity: O(1) - no extra space
 */
class Solution {
    public int addDigits(int num) {
        if (num == 0) return 0;
        return 1 + (num - 1) % 9;
    }
}
