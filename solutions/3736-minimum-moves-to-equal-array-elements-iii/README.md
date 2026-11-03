# 3736. Minimum Moves to Equal Array Elements III

**Difficulty:** Easy

## Problem Description

You are given an integer array `nums`.

In one move, you may **increase** the value of any single element `nums[i]` by `1`.

Return the **minimum total number of moves** required so that all elements in `nums` become equal.

## Examples

**Example 1:**
```
Input: nums = [1,2,3]
Output: 3
Explanation: 
Increase 1 to 3 (2 moves)
Increase 2 to 3 (1 move)
Total: 3 moves
All elements equal 3 (the maximum)
```

**Example 2:**
```
Input: nums = [5,5,5]
Output: 0
Explanation: All elements are already equal.
```

**Example 3:**
```
Input: nums = [1,1,1,5]
Output: 12
Explanation:
Increase three 1's to 5 (4 moves each)
Total: 3 × 4 = 12 moves
```

## Constraints

- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^9`

## Solution Approach

This is the **simplest** variant of the minimum moves problems! The key insight: **Can only increase, so target must be the MAXIMUM!**

### Mathematical Insight

**The Problem:**
- Can only increase elements (no decrease)
- Need to make all elements equal
- Minimize total moves

**The Answer: Target = Maximum**
- Can't make elements smaller
- Must raise everything to maximum
- Each element needs: (max - element) moves

**Formula:**
```
moves = sum(max - nums[i]) for all i
      = n × max - sum(nums)
```

### Visual Example

**Input: [1,2,3]**

```
Array: [1, 2, 3]

Find maximum: max = 3

Calculate moves needed:
  1 → 3: needs 2 moves
  2 → 3: needs 1 move
  3 → 3: needs 0 moves

Total = (3-1) + (3-2) + (3-3)
      = 2 + 1 + 0
      = 3

Alternative formula:
moves = 3 × 3 - (1+2+3)
      = 9 - 6
      = 3 ✓
```

**Input: [1,1,1,5]**

```
Find maximum: max = 5

Calculate moves:
  1 → 5: needs 4 moves (3 times)
  5 → 5: needs 0 moves (1 time)

Total = (5-1) + (5-1) + (5-1) + (5-5)
      = 4 + 4 + 4 + 0
      = 12

Using formula:
moves = 4 × 5 - (1+1+1+5)
      = 20 - 8
      = 12 ✓
```

### Why Maximum (Not Minimum)?

**Can only increase:**
```
If target < max:
  Can't decrease max element!
  Operation doesn't allow it.

If target = max:
  Only need to increase smaller elements
  This is achievable!

Conclusion: Target must be maximum
```

**Example showing minimum doesn't work:**
```
[1, 2, 5]

If we try target = 1 (minimum):
  Need to decrease 2 and 5
  But we can only increase!
  Impossible!

Must use target = 5 (maximum):
  Increase 1 → 5: 4 moves
  Increase 2 → 5: 3 moves
  Total: 7 moves ✓
```

### Detailed Walkthrough

**Input: nums = [1,2,3]**

```
Step 1: Find maximum and sum
max = 1
sum = 0

Loop through array:
  num = 1:
    max = max(1, 1) = 1
    sum = 0 + 1 = 1
  
  num = 2:
    max = max(1, 2) = 2
    sum = 1 + 2 = 3
  
  num = 3:
    max = max(2, 3) = 3
    sum = 3 + 3 = 6

Final: max = 3, sum = 6

Step 2: Calculate moves
moves = max × n - sum
      = 3 × 3 - 6
      = 9 - 6
      = 3

Return 3
```

**Input: nums = [5,5,5]**

```
Step 1: Find max and sum
After loop:
  max = 5
  sum = 15

Step 2: Calculate moves
moves = 5 × 3 - 15
      = 15 - 15
      = 0

Return 0 (already equal)
```

### Why Use Long for Sum?

**Overflow prevention:**
```java
sum += num;  // sum could overflow int

max * nums.length  // product could overflow int
```

**Example causing overflow:**
```
nums = [10^9, 10^9, ..., 10^9]  (10^5 elements)
sum = 10^9 × 10^5 = 10^14 > Integer.MAX_VALUE (2×10^9)

Need long to handle this!
```

## Complexity Analysis

### Direct Formula (Provided):

- **Time Complexity:** O(n)
  - Single pass to find max and sum
  - Constant time calculation
  - Total: O(n)

- **Space Complexity:** O(1)
  - Only two variables: max and sum
  - No extra data structures
  - Constant space

## Comparison: Problems 453, 462, and 3736

| Aspect | Problem 453 | Problem 462 | Problem 3736 |
|--------|-------------|-------------|--------------|
| **Operation** | Inc n-1 by 1 | Inc/Dec any by 1 | Inc any by 1 |
| **Target** | Minimum | Median | Maximum |
| **Formula** | sum(num - min) | sum(\|num - median\|) | sum(max - num) |
| **Sorting** | Not needed | Required | Not needed |
| **Time** | O(n) | O(n log n) | O(n) |
| **Insight** | Inc n-1 = Dec 1 | Median optimal | Only increase |

**Summary:**
- **453:** Can only relatively increase → target = min
- **462:** Can inc/dec → target = median (optimal balance)
- **3736:** Can only increase → target = max (simplest!)

## Edge Cases Handled

✓ **All equal:** [5,5,5] → 0 moves  
✓ **Single element:** [42] → 0 moves  
✓ **Large differences:** [1,1000000] → 999999 moves  
✓ **Large sum:** Handles overflow with long  
✓ **Two elements:** [1,3] → 2 moves  

## Pattern Recognition

This problem teaches **Constraint-Based Target Selection**:

**Key lesson:**
- Constraints determine target
- Can only increase → must reach maximum
- Can't go higher than highest value
- Simple and intuitive!

**Decision tree:**
```
Can only increase? → Target = maximum
Can only decrease? → Target = minimum
Can inc/dec? → Target = median
```

## Key Insights

✓ **Target is maximum** - Can only increase  
✓ **Simple formula** - max × n - sum  
✓ **O(n) solution** - Single pass  
✓ **Need long for sum** - Prevent overflow  
✓ **Most intuitive variant** - Straightforward logic  

## Why This Is Simpler Than 453 and 462

**Problem 3736:**
- Direct operation: increase any element
- Obvious target: maximum (can't exceed it)
- Simple formula: sum of differences from max
- No clever insights needed!

**Problem 453:**
- Indirect operation: increase n-1 elements
- Counter-intuitive: equivalent to decreasing 1
- Requires mathematical transformation

**Problem 462:**
- Can inc/dec: need optimization
- Non-obvious: median minimizes distances
- Requires sorting

**Problem 3736 is the introduction, 453 and 462 are advanced!**

## Common Mistakes to Avoid

❌ **Using minimum as target**
```java
// Wrong!
int target = min;
// Can't decrease to minimum, can only increase!
```

❌ **Forgetting overflow**
```java
// Wrong!
int sum = 0;
return max * nums.length - sum;
// Product and sum could overflow int!
```

❌ **Not using long cast**
```java
// Wrong!
return (int) (max * nums.length - sum);
// max * nums.length might overflow before cast!

// Right!
return (int) (max * (long) nums.length - sum);
// Cast first, then multiply
```

## Alternative Implementation

**Two-loop version:**
```java
public int minMoves(int[] nums) {
    // Find maximum
    int max = nums[0];
    for (int num : nums) {
        max = Math.max(max, num);
    }
    
    // Calculate moves
    int moves = 0;
    for (int num : nums) {
        moves += max - num;
    }
    
    return moves;
}
```
- Two loops instead of one
- Same O(n) time
- Your single-loop version is more efficient!

## Mathematical Proof

**Claim:** Minimum moves = max × n - sum

**Proof:**
```
Let max = maximum element
Target: make all elements equal to max

For each element nums[i]:
  Moves needed = max - nums[i]

Total moves = sum(max - nums[i])
            = sum(max) - sum(nums[i])
            = n × max - sum(nums)

QED
```

## Why Formula Works

**Intuition:**
```
Final state: [max, max, max, ..., max]
Final sum = n × max

Initial sum = sum(nums)

Difference = moves needed
          = final sum - initial sum
          = n × max - sum(nums)
```

Each move increases total sum by 1, so difference = moves!

## Tags

Array, Math, Greedy
