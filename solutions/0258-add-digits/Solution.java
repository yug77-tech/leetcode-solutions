/**
 * LeetCode Problem 258: Add Digits
 * 
 * Iterative simulation solution
 * 
 * Algorithm:
 * 1. While number has more than one digit (num >= 10):
 *    a. Sum all digits of current number
 *    b. Replace num with the sum
 * 2. Return the final single digit
 * 
 * How digit extraction works:
 * - Get last digit: num % 10
 * - Remove last digit: num / 10
 * - Repeat until num becomes 0
 * 
 * Example: num = 38
 *   Iteration 1: 38 → 3 + 8 = 11
 *   Iteration 2: 11 → 1 + 1 = 2
 *   Result: 2 (single digit)
 * 
 * Time Complexity: O(log n) - each iteration reduces digits
 * Space Complexity: O(1) - constant extra space
 */
class Solution {
    public int addDigits(int num) {
        while (num >= 10) {
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num = num / 10;
            }
            num = sum;
        }
        return num;
    }
}
