/**
 * LeetCode Problem 190: Reverse Bits
 * 
 * Bit-by-bit reversal using shift operations
 * 
 * Problem:
 * Reverse the bits of a 32-bit unsigned integer.
 * 
 * Algorithm:
 * Process each bit from right to left (input),
 * building result from left to right (output).
 * 
 * Three key operations:
 * 1. Extract rightmost bit: n & 1
 * 2. Add to result (shifted): result = (result << 1) | bit
 * 3. Move to next bit: n >>>= 1
 * 
 * Visual Example:
 * Input:  00000000000000000000000000001011 (11)
 * 
 * Step 0: Extract bit 1, result = 0000...0001
 * Step 1: Extract bit 1, result = 0000...0011
 * Step 2: Extract bit 0, result = 0000...0110
 * Step 3: Extract bit 1, result = 0000...1101
 * Steps 4-31: Extract bit 0, shift left each time
 * 
 * Output: 11010000000000000000000000000000
 * 
 * Why >>> instead of >>?
 * - >>  : Arithmetic shift (sign extends)
 * - >>> : Logical shift (zero fills)
 * 
 * For bit manipulation, we want logical shift!
 * Example: -8 >> 1 = -4 (sign extended)
 *          -8 >>> 1 = 2147483644 (zero filled)
 * 
 * Bit Operations Explained:
 * 
 * n & 1:
 *   Extracts rightmost bit
 *   1011 & 0001 = 0001 (extracts 1)
 *   1010 & 0001 = 0000 (extracts 0)
 * 
 * result << 1:
 *   Shifts all bits left, makes room on right
 *   0011 << 1 = 0110
 * 
 * (result << 1) | bit:
 *   Adds bit to rightmost position
 *   0110 | 1 = 0111
 * 
 * n >>>= 1:
 *   Logical right shift by 1
 *   1011 >>> 1 = 0101
 * 
 * Time Complexity: O(1) - exactly 32 iterations (constant)
 * Space Complexity: O(1) - only two variables
 */
class Solution {
    public int reverseBits(int n) {
        int result = 0;
        
        // Process all 32 bits
        for (int i = 0; i < 32; i++) {
            // Shift result left by 1 to make room for new bit
            // Extract rightmost bit of n using & 1
            // Add extracted bit to result using | (OR)
            result = (result << 1) | (n & 1);
            
            // Shift n right by 1 to process next bit
            // Use >>> (logical shift) not >> (arithmetic shift)
            n >>>= 1;
        }
        
        return result;
    }
}
