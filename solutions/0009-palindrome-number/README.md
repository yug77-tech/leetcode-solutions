# 9. Palindrome Number

**Difficulty:** Easy

## Problem Description

Given an integer `x`, return `true` if `x` is a palindrome, and `false` otherwise.

## Examples

**Example 1:**
```
Input: x = 121
Output: true
Explanation: 121 reads as 121 from left to right and from right to left.
```

**Example 2:**
```
Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
```

**Example 3:**
```
Input: x = 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
```

## Constraints

- `-2^31 <= x <= 2^31 - 1`

## Follow-up

Could you solve it without converting the integer to a string?

## Solution Approach

This solution uses a **mathematical approach** without converting to string.

**Key Insight:**
- Negative numbers are never palindromes (because of the '-' sign)
- Reverse the entire number and compare with the original
- If they're equal, it's a palindrome

**Algorithm:**
1. **Quick reject**: If `x < 0`, return false immediately
2. **Reverse the number**:
   - Extract last digit using `% 10`
   - Build reversed number: `rev = rev * 10 + digit`
   - Remove last digit: `temp = temp / 10`
   - Repeat until temp becomes 0
3. **Compare**: Return `x == rev`

**Example Walkthrough** (x = 121):
```
Initial: temp = 121, rev = 0

Iteration 1: 
  digit = 121 % 10 = 1
  rev = 0 * 10 + 1 = 1
  temp = 121 / 10 = 12

Iteration 2:
  digit = 12 % 10 = 2
  rev = 1 * 10 + 2 = 12
  temp = 12 / 10 = 1

Iteration 3:
  digit = 1 % 10 = 1
  rev = 12 * 10 + 1 = 121
  temp = 1 / 10 = 0

Result: x (121) == rev (121) → true
```

## Complexity Analysis

- **Time Complexity:** O(log₁₀(n)) - Number of digits in x (each iteration removes one digit)
- **Space Complexity:** O(1) - Only using constant extra space

## Alternative Approach

An even more optimized approach reverses only **half** of the number and compares the two halves. This saves iterations but is slightly more complex to implement.

## Tags

Math, Two Pointers
