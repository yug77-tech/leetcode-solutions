/**
 * LeetCode Problem 14: Longest Common Prefix
 * 
 * Horizontal scanning with prefix reduction
 * 
 * Problem:
 * Find the longest common prefix string among an array of strings.
 * 
 * Key Insight:
 * Start with first string as prefix, then shrink it until it matches all strings.
 * The common prefix must be a prefix of EVERY string.
 * 
 * Algorithm:
 * 1. Take first string as initial prefix
 * 2. For each subsequent string:
 *    - While string doesn't start with prefix:
 *      - Remove last character from prefix
 *      - If prefix becomes empty, return ""
 * 3. Return remaining prefix
 * 
 * Why this works:
 * - If prefix doesn't match current string, it's too long
 * - By progressively shrinking, we find longest that works for all
 * - If no common prefix exists, we'll shrink to empty string
 * 
 * Example: ["flower", "flow", "flight"]
 * 
 * Start: prefix = "flower"
 * 
 * Check "flow":
 *   "flow" starts with "flower"? No
 *   Shrink: "flowe" → No
 *   Shrink: "flow" → Yes ✓
 *   prefix = "flow"
 * 
 * Check "flight":
 *   "flight" starts with "flow"? No
 *   Shrink: "flo" → No
 *   Shrink: "fl" → Yes ✓
 *   prefix = "fl"
 * 
 * Result: "fl"
 * 
 * Edge Cases:
 * 1. No common prefix: ["dog", "car"]
 *    - Shrink to "" and return
 * 
 * 2. All identical: ["abc", "abc", "abc"]
 *    - Never shrinks, returns full string
 * 
 * 3. One is prefix of others: ["fl", "flower"]
 *    - Returns shorter string
 * 
 * 4. Empty string exists: ["", "abc"]
 *    - First string is "", returns ""
 * 
 * Why use startsWith()?
 * - Clean and readable
 * - Optimized built-in method
 * - Handles edge cases (empty strings, length differences)
 * - Clear intent
 * 
 * Why substring(0, length-1)?
 * - Removes last character
 * - Example: "flower" → "flowe" → "flow" → "flo" → "fl"
 * 
 * Early termination:
 * - If prefix becomes empty, no common prefix exists
 * - Return immediately, no need to check remaining strings
 * 
 * Time Complexity: O(S) where S = sum of all characters in all strings
 *   - In worst case, might compare all characters
 *   - Example: ["abc", "abc", "abc"] → 9 comparisons
 *   - Best case: Early return with no common prefix
 * 
 * Space Complexity: O(1)
 *   - Only using prefix string (which gets smaller)
 *   - No extra data structures
 *   - String operations in Java may create intermediate strings,
 *     but we don't count that as we're returning a string anyway
 */
class Solution {
    public String longestCommonPrefix(String[] strs) {
        // Start with first string as the prefix candidate
        String prefix = strs[0];

        // Check against each subsequent string
        for (int i = 1; i < strs.length; i++) {
            // While current string doesn't start with prefix
            while (!strs[i].startsWith(prefix)) {
                // Shrink prefix by removing last character
                prefix = prefix.substring(0, prefix.length() - 1);

                // If prefix becomes empty, no common prefix exists
                if (prefix.isEmpty()) {
                    return "";
                }
            }
            // Once startsWith is true, move to next string
        }

        // Prefix has been validated against all strings
        return prefix;
    }
}
