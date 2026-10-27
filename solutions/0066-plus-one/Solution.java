/**
 * LeetCode Problem 66: Plus One
 * 
 * Optimized carry propagation
 * 
 * Problem:
 * Given array representing large integer, add 1 and return result.
 * digits[0] is most significant digit (leftmost)
 * 
 * Key Insight:
 * Most of the time, just increment last digit!
 * Only need special handling when digit is 9 (carry propagates)
 * 
 * Two Main Cases:
 * 
 * Case 1: Last digit < 9 (Common case - ~90%)
 *   [1,2,3] → Simply increment: [1,2,4]
 *   No carry, return immediately!
 * 
 * Case 2: Last digit = 9 (Need carry)
 *   [1,2,9] → Set to 0, carry to next: [1,3,0]
 *   Continue until find digit < 9
 * 
 * Special Case: All 9's (Rare)
 *   [9,9,9] → Need bigger array: [1,0,0,0]
 *   All digits become 0, new digit 1 at front
 * 
 * Algorithm:
 * 1. Iterate from right to left (least to most significant)
 * 2. For each digit:
 *    - If < 9: increment and return (no more carries!)
 *    - If = 9: set to 0 and continue (carry propagates)
 * 3. If loop completes: all digits were 9
 *    - Create new array with length + 1
 *    - Set first digit to 1 (rest are 0 by default)
 * 
 * Example 1: [1,2,3] (No Carry)
 * 
 * i=2: digits[2]=3 < 9
 *      Increment: 3 → 4
 *      [1,2,4]
 *      Return immediately ✓
 * 
 * Example 2: [1,2,9] (Single Carry)
 * 
 * i=2: digits[2]=9
 *      Set to 0: [1,2,0]
 *      Continue...
 * 
 * i=1: digits[1]=2 < 9
 *      Increment: 2 → 3
 *      [1,3,0]
 *      Return ✓
 * 
 * Example 3: [9,9,9] (All Carry)
 * 
 * i=2: digits[2]=9 → Set to 0: [9,9,0]
 * i=1: digits[1]=9 → Set to 0: [9,0,0]
 * i=0: digits[0]=9 → Set to 0: [0,0,0]
 * 
 * Loop complete - all were 9!
 * Create new array: [1,0,0,0]
 * (Java initializes to 0, so only set first to 1)
 * 
 * Example 4: [1,9,9] (Multiple Carries)
 * 
 * i=2: digits[2]=9 → Set to 0: [1,9,0]
 * i=1: digits[1]=9 → Set to 0: [1,0,0]
 * i=0: digits[0]=1 < 9 → Increment: [2,0,0]
 * Return ✓
 * 
 * Why This Solution is Elegant:
 * 
 * 1. Early Termination:
 *    Most numbers don't end in 9, so return immediately
 *    Only about 10% of numbers end in 9
 * 
 * 2. No Explicit Carry Variable:
 *    Setting digit to 0 and continuing loop IS the carry
 *    Cleaner than traditional carry flag
 * 
 * 3. Minimal Space Usage:
 *    Only creates new array when absolutely needed (all 9's)
 *    Modifies in-place otherwise
 * 
 * 4. Java Array Initialization:
 *    new int[n] automatically fills with 0
 *    Just need to set first element to 1
 * 
 * Why Process Right to Left?
 * Just like manual addition:
 *     123
 *   +   1
 *   -----
 *     124
 * Start from ones place (rightmost)
 * Carry propagates left if needed
 * 
 * Edge Cases:
 * 1. Single digit < 9: [5] → [6]
 * 2. Single digit = 9: [9] → [1,0]
 * 3. No carry: [1,2,3] → [1,2,4]
 * 4. Single carry: [1,9] → [2,0]
 * 5. Multiple carries: [1,9,9] → [2,0,0]
 * 6. All nines: [9,9,9] → [1,0,0,0]
 * 7. Large number: works for any length!
 * 
 * Why Not Convert to Integer?
 * Problem states "large integer"
 * - Arrays can have 100+ digits
 * - long max is ~19 digits
 * - Would overflow!
 * 
 * Common Mistake:
 * Forgetting to create new array when all digits are 9:
 *   [9,9,9] → Should be [1,0,0,0], not [0,0,0]
 * 
 * Time Complexity: O(n)
 *   - Best case: O(1) when last digit < 9
 *   - Worst case: O(n) when all digits = 9
 *   - Average case: O(1) - most numbers don't end in 9
 * 
 * Space Complexity: O(1) or O(n)
 *   - Best case: O(1) - modify in place
 *   - Worst case: O(n) - create new array for overflow
 *   - Only creates extra space when result has more digits
 */
class Solution {
    public int[] plusOne(int[] digits) {
        // Process digits from right to left (least to most significant)
        for (int i = digits.length - 1; i >= 0; i--) {
            // If current digit is less than 9
            if (digits[i] < 9) {
                // Simply increment and we're done!
                // No carry needed, return immediately
                digits[i]++;
                return digits;
            }
            
            // Current digit is 9
            // Set to 0 (this represents the carry)
            // Continue to next digit (carry propagates left)
            digits[i] = 0;
        }

        // If we reach here, ALL digits were 9
        // Example: [9,9,9] → all became [0,0,0]
        // We need a new array with one more digit
        // Result should be [1,0,0,0]
        
        // Create new array with length + 1
        int[] result = new int[digits.length + 1];
        
        // Java initializes array to 0: [0,0,0,0]
        // Just set first digit to 1
        result[0] = 1;
        
        // Rest are already 0, so we're done!
        return result;
    }
}
