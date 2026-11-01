# 453. Minimum Moves to Equal Array Elements

**Difficulty:** Medium

## Problem Description

Given an integer array `nums` of size `n`, return the **minimum number of moves** required to make all array elements equal.

In one move, you can increment `n - 1` elements of the array by `1`.

## Examples

**Example 1:**
```
Input: nums = [1,2,3]
Output: 3
Explanation: Only three moves are needed (remember each move increments two elements):
[1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
```

**Example 2:**
```
Input: nums = [1,1,1]
Output: 0
Explanation: All elements are already equal.
```

## Constraints

- `n == nums.length`
- `1 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- The answer is guaranteed to fit in a **32-bit** integer.

## Solution Approach

This is a **brilliant math problem!** The key insight: **Incrementing n-1 elements is equivalent to decrementing 1 element!**

### Mathematical Insight

**The Trick:**
```
Incrementing (n-1) elements by 1 
= 
Decrementing 1 element by 1

Why? Look at relative differences!
```

**Proof:**
```
Original: [1, 2, 3]
After incrementing first two: [2, 3, 3]

Relative to average:
- Elements that increased moved closer to max
- Element that stayed moved relatively down

It's the SAME as decrementing the max!
```

**New Problem:**
- Instead of: "increment n-1 elements"
- Think: "decrement 1 element to minimum"
- Goal: make all elements equal to minimum

**Solution:**
- Find minimum element
- Calculate: sum of (each element - minimum)
- This is the number of moves!

### Visual Example

**Input: [1,2,3]**

```
Traditional thinking (complex):
Move 1: Increment [1,2] → [2,3,3]
Move 2: Increment [2,3] → [3,4,3]
Move 3: Increment [3,3] → [4,4,4]
Result: 3 moves

Mathematical insight (simple):
min = 1
Difference from min:
  2 - 1 = 1
  3 - 1 = 2
Total: 1 + 2 = 3 moves ✓

Same answer, much easier!
```

### Why This Works

**Intuition:**

Each move increments n-1 elements by 1.
- Total sum increases by (n-1) per move
- To make all equal, need all = some target value

**Key realization:**
- Can't decrease max directly
- But incrementing others is equivalent!
- Each "decrement" of max = 1 move
- Reduce everything to min

**Formula:**
```
moves = sum(nums[i] - min) for all i
      = sum(nums) - n × min
```

### Detailed Walkthrough

**Input: nums = [1,2,3]**

```
Step 1: Find minimum
min = 1

Step 2: Calculate moves
moves = 0

For each element:
  num = 1: moves += (1 - 1) = 0
  num = 2: moves += (2 - 1) = 1
  num = 3: moves += (3 - 1) = 2

Total moves = 0 + 1 + 2 = 3

Result: 3 moves needed
```

**Input: nums = [1,1,1]**

```
Step 1: Find minimum
min = 1

Step 2: Calculate moves
For each element:
  num = 1: moves += (1 - 1) = 0
  num = 1: moves += (1 - 1) = 0
  num = 1: moves += (1 - 1) = 0

Total moves = 0

Result: 0 moves (already equal)
```

**Input: nums = [5,6,8,8,5]**

```
Step 1: Find minimum
min = 5

Step 2: Calculate moves
moves = (5-5) + (6-5) + (8-5) + (8-5) + (5-5)
      = 0 + 1 + 3 + 3 + 0
      = 7

Result: 7 moves
```

### Why Not Simulate Each Move?

**Simulation approach (Wrong!):**
```java
// This would be O(n × moves) - TOO SLOW!
while (!allEqual(nums)) {
    // Increment all except max
    int maxIdx = findMaxIndex(nums);
    for (int i = 0; i < n; i++) {
        if (i != maxIdx) nums[i]++;
    }
    moves++;
}
```

**Problems:**
- Time: O(n × moves) where moves could be huge!
- For [1, 1000000]: 999999 moves!
- Would timeout

**Our math solution:**
- Time: O(n) - single pass to find min and sum
- Works for any input size
- Elegant and efficient!

## Complexity Analysis

### Mathematical Approach (Provided):

- **Time Complexity:** O(n)
  - First loop: find minimum - O(n)
  - Second loop: sum differences - O(n)
  - Total: O(n)

- **Space Complexity:** O(1)
  - Only two variables: min and moves
  - No extra data structures
  - Constant space

### Alternative Formula

**Single loop version:**
```java
public int minMoves(int[] nums) {
    int min = Arrays.stream(nums).min().getAsInt();
    return Arrays.stream(nums).map(num -> num - min).sum();
}
```
- Same O(n) time
- More concise with streams
- Your two-loop version is clearer!

## Edge Cases Handled

✓ **All equal:** [5,5,5] → 0 moves  
✓ **Already at min:** [0,0,0] → 0 moves  
✓ **Large differences:** [1,1000000] → 999999 moves  
✓ **Negative numbers:** [-10,-5,0] → Works fine (min=-10)  
✓ **Single element:** [42] → 0 moves  
✓ **Two elements:** [1,2] → 1 move  

## Pattern Recognition

This problem teaches **Mathematical Transformation**:

**Key lesson:**
- Don't always simulate directly
- Look for mathematical equivalence
- "Increment n-1" = "Decrement 1"
- Relative thinking

**Similar problems:**
- Minimum Moves to Equal Array Elements II
- Array partition problems
- Relative difference problems

## Mathematical Proof

**Claim:** Incrementing n-1 elements = Decrementing 1 element

**Proof:**
```
Let array be [a₁, a₂, ..., aₙ]

After incrementing all except aᵢ:
[a₁+1, a₂+1, ..., aᵢ, ..., aₙ+1]

Relative to new average:
- All except aᵢ moved up by 1
- aᵢ stayed same (relatively moved down)

This is equivalent to:
[a₁, a₂, ..., aᵢ-1, ..., aₙ]

Where we decremented aᵢ by 1!

The relative positions are identical.
```

**Why reduce to minimum?**
```
To make all equal, need target value.

Can't go below minimum (only have increment operation).

So target = minimum!

Moves = sum of "virtual decrements" to reach minimum
      = sum(nums[i] - min)
```

## Key Insights

✓ **Increment n-1 = Decrement 1** - Core insight  
✓ **Target is minimum** - Can't go below it  
✓ **Sum of differences** - Simple formula  
✓ **O(n) solution** - No simulation needed  
✓ **Relative thinking** - Focus on differences  

## Why This Problem is Beautiful

1. **Counter-intuitive:**
   - Looks like simulation needed
   - Actually pure math!

2. **Elegant transformation:**
   - Complex operation → simple formula
   - One insight changes everything

3. **Efficient:**
   - O(n) instead of O(n × moves)
   - Could be millions of moves!

## Common Mistakes to Avoid

❌ **Trying to simulate moves**
```java
// Wrong: Too slow!
while (!allEqual) {
    increment all except max
    moves++
}
```

❌ **Using sorting**
```java
// Unnecessary!
Arrays.sort(nums);
// Don't need sorted order
```

❌ **Complicated formulas**
```java
// Overthinking!
int target = average(nums);
// Target is simply the minimum!
```

## Alternative: Single Pass

```java
public int minMoves(int[] nums) {
    int min = Integer.MAX_VALUE;
    int sum = 0;
    
    for (int num : nums) {
        min = Math.min(min, num);
        sum += num;
    }
    
    return sum - nums.length * min;
}
```

Uses formula: `sum(nums) - n × min`
- Single pass
- Same O(n) time
- Your two-pass version is clearer!

## Tags

Array, Math
