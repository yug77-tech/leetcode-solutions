/**
 * LeetCode Problem 9: Palindrome Number
 * 
 * Mathematical solution without string conversion
 * 
 * Algorithm:
 * 1. Reject negative numbers immediately (they can't be palindromes)
 * 2. Reverse the entire number digit by digit
 * 3. Compare original with reversed number
 * 
 * How reversal works:
 * - Extract last digit: digit = temp % 10
 * - Build reversed number: rev = rev * 10 + digit
 * - Remove last digit: temp = temp / 10
 * - Repeat until temp becomes 0
 * 
 * Example: x = 121
 *   temp=121, rev=0 → digit=1, rev=1, temp=12
 *   temp=12,  rev=1 → digit=2, rev=12, temp=1
 *   temp=1,   rev=12 → digit=1, rev=121, temp=0
 *   Result: 121 == 121 → true
 * 
 * Time Complexity: O(log₁₀(n)) - number of digits in x
 * Space Complexity: O(1) - constant extra space
 */
class Solution {
    public boolean isPalindrome(int x) {
        
        if (x < 0) {
            return false;
        }
        
        int temp = x;
        int rev = 0;
        
        while (temp != 0) {
            rev = rev * 10 + temp % 10;
            temp = temp / 10;
        }
        
        return x == rev;
    }
}
