# 258. Add Digits

**Difficulty:** Easy

## Problem Description

Given an integer `num`, repeatedly add all its digits until the result has only one digit, and return it.

## Examples

**Example 1:**
```
Input: num = 38
Output: 2
Explanation: The process is
38 --> 3 + 8 --> 11
11 --> 1 + 1 --> 2 
Since 2 has only one digit, return it.
```

**Example 2:**
```
Input: num = 0
Output: 0
```

## Constraints

- `0 <= num <= 2^31 - 1`

## Follow-up

Could you do it without any loop/recursion in O(1) runtime?

## Solution Approach

### Iterative Solution (Provided)

This solution uses a **simulation approach** with nested loops.

**Algorithm:**
1. **Outer loop**: Continue while `num >= 10` (more than one digit)
2. **Inner loop**: Sum all digits of current number
   - Extract last digit: `num % 10`
   - Add to sum
   - Remove last digit: `num / 10`
3. **Update**: Set `num = sum` and repeat
4. **Return**: When `num < 10`, return it

**Example Walkthrough** (num = 38):
```
Iteration 1:
  num = 38
  Inner loop: sum = 3 + 8 = 11
  num = 11

Iteration 2:
  num = 11
  Inner loop: sum = 1 + 1 = 2
  num = 2

num < 10, return 2
```

### Complexity Analysis (Iterative)

- **Time Complexity:** O(log n) - Each iteration reduces number of digits
- **Space Complexity:** O(1) - Only using constant extra space

### O(1) Mathematical Solution (Digital Root)

There's a mathematical pattern called **Digital Root** that solves this in O(1):

```java
public int addDigits(int num) {
    if (num == 0) return 0;
    return 1 + (num - 1) % 9;
}
```

**Why this works:**
- The digital root follows a pattern based on modulo 9
- For num = 0: result is 0
- For num % 9 = 0 (and num ≠ 0): result is 9
- Otherwise: result is num % 9

**Pattern:**
```
1→1, 2→2, 3→3, 4→4, 5→5, 6→6, 7→7, 8→8, 9→9
10→1, 11→2, 12→3, 13→4, 14→5, 15→6, 16→7, 17→8, 18→9
19→1, 20→2, 21→3, ...
```

The formula `1 + (num - 1) % 9` handles all cases elegantly!

## Tags

Math, Simulation, Number Theory
