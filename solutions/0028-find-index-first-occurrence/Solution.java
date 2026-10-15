/**
 * LeetCode Problem 28: Find the Index of the First Occurrence in a String
 * 
 * Clean solution using built-in String.startsWith() method
 * 
 * Problem:
 * Find the first index where needle appears in haystack, or -1 if not found.
 * 
 * Algorithm:
 * 1. Loop through valid starting positions (0 to n-m)
 * 2. At each position, check if haystack starts with needle
 * 3. Return first matching position, or -1 if none found
 * 
 * Key Insight:
 * Loop condition is i <= n - m, not i < n
 * - This ensures we don't check positions where needle can't fit
 * - Example: haystack="hello" (n=5), needle="lo" (m=2)
 *   Valid positions: 0,1,2,3 (at position 3: "lo" fits)
 *   Position 4: would need chars at 4,5 but we only have 0-4
 * 
 * Example walkthrough:
 * haystack = "sadbutsad", needle = "sad"
 * n = 9, m = 3
 * 
 * i=0: haystack.startsWith("sad", 0)
 *      Compare "sad" with "sad" → match! return 0
 * 
 * Example with no match:
 * haystack = "leetcode", needle = "leeto"
 * n = 8, m = 5
 * 
 * i=0: "leetc" != "leeto"
 * i=1: "eetco" != "leeto"
 * i=2: "etcod" != "leeto"
 * i=3: "tcode" != "leeto"
 * Loop ends → return -1
 * 
 * Why startsWith() with offset?
 * - startsWith(prefix, offset) checks if substring starting at offset
 *   begins with the given prefix
 * - More readable than manual character comparison
 * - Java's implementation is optimized
 * 
 * Alternative one-liner:
 * return haystack.indexOf(needle);
 * 
 * But the loop approach is more explicit and educational!
 * 
 * Time Complexity: O(n × m) worst case
 *   - n = haystack length, m = needle length
 *   - Each startsWith() compares up to m characters
 *   - We check up to (n-m+1) positions
 * 
 * Space Complexity: O(1)
 *   - No extra space used, only loop variables
 */
class Solution {
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        // Check each valid starting position
        for (int i = 0; i <= n - m; i++) {
            // Check if haystack starts with needle at position i
            if (haystack.startsWith(needle, i)) {
                return i;
            }
        }

        // No match found
        return -1;
    }
}
