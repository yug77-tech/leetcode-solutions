/**
 * LeetCode Problem 58: Length of Last Word
 * 
 * Optimal two-phase scanning from right to left
 * 
 * Problem:
 * Find the length of the last word in a string.
 * String may have trailing/leading spaces.
 * 
 * Algorithm (Two Phases):
 * 
 * Phase 1: Skip trailing spaces
 * - Start from end of string
 * - Move left while encountering spaces
 * - Stop at first non-space character
 * 
 * Phase 2: Count word characters
 * - Continue from where Phase 1 stopped
 * - Count non-space characters
 * - Stop when hitting space or beginning
 * 
 * Visual Example:
 * Input: "   fly me   to   the moon  "
 *                               ^^^^  (last word)
 * 
 * Start from end (index = length - 1)
 * 
 * Phase 1: Skip spaces
 *   i=25: ' ' → skip
 *   i=24: ' ' → skip  
 *   i=23: 'n' → stop (found end of word)
 * 
 * Phase 2: Count characters
 *   i=23: 'n' → count=1
 *   i=22: 'o' → count=2
 *   i=21: 'o' → count=3
 *   i=20: 'm' → count=4
 *   i=19: ' ' → stop (word ended)
 * 
 * Result: 4
 * 
 * Why scan right-to-left?
 * - We only care about the LAST word
 * - No need to process the entire string
 * - Handles trailing spaces naturally
 * - More efficient than splitting the string
 * 
 * Edge Cases:
 * 1. "hello world  " → Phase 1 skips trailing spaces
 * 2. "a" → Single character word works
 * 3. "   hello" → Phase 2 counts from 'o' to 'h'
 * 
 * Time Complexity: O(n) worst case, O(m) average
 *   - n = string length
 *   - m = length of last word + trailing spaces
 *   - Best case: O(1) if word ends at string end
 * 
 * Space Complexity: O(1)
 *   - Only two variables: i and count
 *   - Most space-efficient solution!
 */
class Solution {
    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        int count = 0;

        // Phase 1: Skip trailing spaces
        // Move left until we find a non-space character
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }

        // Phase 2: Count word characters
        // Continue moving left, counting non-space characters
        while (i >= 0 && s.charAt(i) != ' ') {
            count++;
            i--;
        }

        return count;
    }
}
