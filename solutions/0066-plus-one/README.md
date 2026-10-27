# 66. Plus One

**Difficulty:** Easy

## Problem Description

You are given a **large integer** represented as an integer array `digits`, where each `digits[i]` is the `ith` digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading `0`'s.

Increment the large integer by one and return the resulting array of digits.

## Examples

**Example 1:**
```
Input: digits = [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Incrementing by one gives 123 + 1 = 124.
Thus, the result should be [1,2,4].
```

**Example 2:**
```
Input: digits = [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
Incrementing by one gives 4321 + 1 = 4322.
```

**Example 3:**
```
Input: digits = [9]
Output: [1,0]
Explanation: The array represents the integer 9.
Incrementing by one gives 9 + 1 = 10.
Thus, the result should be [1,0].
```

**Example 4:**
```
Input: digits = [9,9,9]
Output: [1,0,0,0]
Explanation: The array represents the integer 999.
Incrementing by one gives 999 + 1 = 1000.
```

## Constraints

- `1 <= digits.length <= 100`
- `0 <= digits[i] <= 9`
- `digits` does not contain any leading `0`'s.

## Solution Approach

The key insight: **Most of the time, just increment the last digit! Only handle carries when needed.**

### Optimized Carry Propagation

**Two Cases:**

**Case 1: Last digit < 9 (Most common - ~90% of cases)**
```
[1,2,3] → Last digit is 3 → Just increment to 4
Result: [1,2,4]
```
No carry needed, return immediately!

**Case 2: Last digit = 9 (Need carry propagation)**
```
[1,2,9] → Last digit is 9 → Becomes 0, carry to next
[1,2,0] → Next digit is 2 → Increment to 3
Result: [1,3,0]
```

**Special Case: All 9's (Rare - need bigger array)**
```
[9,9,9] → All become 0, need extra digit
Result: [1,0,0,0]
```

### Algorithm Flow

1. **Iterate from right to left** (least significant to most significant)
2. **For each digit:**
   - If digit < 9: increment and return (no more carries!)
   - If digit = 9: set to 0 and continue (carry propagates)
3. **If loop completes:** All digits were 9
   - Create new array with length + 1
   - Set first digit to 1 (rest are 0 by default)

### Visual Walkthrough

**Example 1: [1,2,3] (No Carry)**

```
Start from rightmost:
[1, 2, 3]
       ↑
       
digits[2] = 3 < 9
Increment: 3 → 4
[1, 2, 4]

Return immediately!
```

**Example 2: [1,2,9] (Single Carry)**

```
Start from rightmost:
[1, 2, 9]
       ↑
       
digits[2] = 9
Set to 0: [1, 2, 0]

Move left:
[1, 2, 0]
    ↑
    
digits[1] = 2 < 9
Increment: 2 → 3
[1, 3, 0]

Return!
```

**Example 3: [9,9,9] (All Carry)**

```
Start from rightmost:
[9, 9, 9]
       ↑
       
digits[2] = 9
Set to 0: [9, 9, 0]

Move left:
[9, 9, 0]
    ↑
    
digits[1] = 9
Set to 0: [9, 0, 0]

Move left:
[9, 0, 0]
 ↑
 
digits[0] = 9
Set to 0: [0, 0, 0]

Loop complete - all were 9!
Create new array: [1, 0, 0, 0]
(Java initializes to 0, so only set first to 1)
```

**Example 4: [1,9,9] (Multiple Carries)**

```
[1, 9, 9]
       ↑
digits[2] = 9 → Set to 0

[1, 9, 0]
    ↑
digits[1] = 9 → Set to 0

[1, 0, 0]
 ↑
digits[0] = 1 < 9 → Increment to 2

Result: [2, 0, 0]
```

### Why This Solution is Brilliant

✓ **Early termination** - Most cases return immediately  
✓ **No explicit carry variable** - Elegantly handled by loop  
✓ **Clean logic** - Only two simple conditions  
✓ **Optimal space** - Only creates new array when absolutely needed  
✓ **Java array default** - New arrays initialize to 0, so just set first digit  

### Edge Cases Handled

✓ **No carry needed:** [1,2,3] → [1,2,4]  
✓ **Single carry:** [1,9] → [2,0]  
✓ **Multiple carries:** [1,9,9] → [2,0,0]  
✓ **All nines:** [9,9,9] → [1,0,0,0]  
✓ **Single digit 9:** [9] → [1,0]  
✓ **Single digit < 9:** [5] → [6]  
✓ **Large number:** [9,8,7,6,5,4,3,2,1,0] → [9,8,7,6,5,4,3,2,1,1]  

## Complexity Analysis

### Optimized Approach (Provided):

- **Time Complexity:** O(n)
  - Best case: O(1) when last digit < 9
  - Worst case: O(n) when all digits are 9
  - Average case: O(1) - most numbers don't end in 9!

- **Space Complexity:** O(1) or O(n)
  - Best case: O(1) - modify in place
  - Worst case: O(n) - create new array for all 9's
  - Note: If we count the result array, it's always O(n)
  - But we only create **extra** space for overflow case

### Why New Array for All 9's?

When all digits are 9, result has one more digit:
```
999 + 1 = 1000
[9,9,9] + 1 = [1,0,0,0]
```

**Java trick:**
- `new int[n]` initializes all elements to 0
- So `new int[4]` = [0,0,0,0]
- Just set first element: result[0] = 1
- Result: [1,0,0,0] ✓

## Alternative Approaches

**1. Explicit Carry Variable:**
```java
public int[] plusOne(int[] digits) {
    int carry = 1; // Initial carry (adding 1)
    
    for (int i = digits.length - 1; i >= 0; i--) {
        int sum = digits[i] + carry;
        digits[i] = sum % 10;
        carry = sum / 10;
        
        if (carry == 0) break; // No more carry
    }
    
    if (carry == 1) {
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
    
    return digits;
}
```
- More verbose but clearer logic
- Handles general addition (could add any number)

**2. Convert to Number (Wrong!):**
```java
// DON'T DO THIS!
int num = 0;
for (int digit : digits) {
    num = num * 10 + digit;
}
num++;
// Convert back to array...
```
- ❌ Fails for large numbers (overflow)
- ❌ Problem states "large integer"
- ❌ Arrays can have 100+ digits

Your solution is **optimal and elegant**!

## Common Mistakes to Avoid

❌ **Not handling all 9's case**
```java
// Wrong - crashes or incorrect for [9,9,9]
for (int i = digits.length - 1; i >= 0; i--) {
    digits[i]++;
    if (digits[i] < 10) return digits;
    digits[i] = 0;
}
return digits; // Wrong! Should create new array
```

❌ **Converting to integer**
```java
// Wrong - overflow for large numbers
int num = Integer.parseInt(arrayToString(digits));
```

❌ **Not returning early**
```java
// Wrong - unnecessary work
for (int i = digits.length - 1; i >= 0; i--) {
    if (digits[i] < 9) {
        digits[i]++;
        // Don't return - keeps looping!
    }
}
```

## Pattern Recognition

This problem teaches **Digit Manipulation** pattern:

**Key techniques:**
- Process digits right to left (least to most significant)
- Handle carry propagation
- Early termination optimization
- Overflow handling (array expansion)

**Similar problems:**
- Add two numbers (arrays or linked lists)
- Multiply strings
- Plus minus one queries
- Decimal to binary conversion

## Why Right to Left?

Just like manual addition:
```
    1 2 3
  +     1
  -------
    1 2 4
    
Start from rightmost (ones place)
Carry moves left if needed
```

## Tags

Array, Math
