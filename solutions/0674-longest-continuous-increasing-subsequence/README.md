# 674. Longest Continuous Increasing Subsequence

**Difficulty:** Easy

## Problem Description

Given an unsorted array of integers `nums`, return the length of the **longest continuous increasing subsequence** (i.e. subarray). The subsequence must be **strictly increasing**.

A continuous increasing subsequence is defined by two indices `l` and `r` (`l < r`) such that it is `[nums[l], nums[l + 1], ..., nums[r - 1], nums[r]]` and for each `l <= i < r`, `nums[i] < nums[i + 1]`.

## Examples

**Example 1:**
```
Input: nums = [1,3,5,4,7]
Output: 3
Explanation: The longest continuous increasing subsequence is [1,3,5] with length 3.
Even though [1,3,5,7] is an increasing subsequence, it is not continuous as elements 5 and 7 are separated by element 4.
```

**Example 2:**
```
Input: nums = [2,2,2,2,2]
Output: 1
Explanation: The longest continuous increasing subsequence is [2] with length 1.
Note: It must be strictly increasing.
```

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^9 <= nums[i] <= 10^9`

## Solution Approach

This is a **simple one-pass problem!** The key insight: **Track current streak length and update max whenever streak breaks.**

### Single Pass with Counter

**The Strategy:**
- Maintain current streak length
- When increasing: increment counter
- When not increasing: reset counter to 1
- Track maximum seen

**Why This Works:**
- Continuous = unbroken sequence
- Any non-increasing element breaks the streak
- Just need to track longest streak

### Algorithm Steps

1. Initialize `max = 0` and `count = 0`
2. For each element:
   - If first element OR greater than previous:
     - Increment count (continue streak)
   - Else:
     - Reset count to 1 (new streak)
   - Update max with count
3. Return max

### Visual Example

**Input: nums = [1,3,5,4,7]**

```
Index: 0  1  2  3  4
Value: 1  3  5  4  7

i=0, nums[0]=1:
  First element → count=1
  max = 1
  [1] ← current streak

i=1, nums[1]=3:
  3 > 1 ✓ → count=2
  max = 2
  [1,3] ← current streak

i=2, nums[2]=5:
  5 > 3 ✓ → count=3
  max = 3
  [1,3,5] ← current streak

i=3, nums[3]=4:
  4 < 5 ✗ → count=1 (reset)
  max = 3 (no change)
  [4] ← new streak

i=4, nums[4]=7:
  7 > 4 ✓ → count=2
  max = 3 (no change)
  [4,7] ← current streak

Final: max = 3
Longest streak was [1,3,5]
```

**Input: nums = [2,2,2,2,2]**

```
Index: 0  1  2  3  4
Value: 2  2  2  2  2

i=0: First → count=1, max=1
i=1: 2 not > 2 → count=1, max=1
i=2: 2 not > 2 → count=1, max=1
i=3: 2 not > 2 → count=1, max=1
i=4: 2 not > 2 → count=1, max=1

Final: max = 1
No increasing streak, each element is individual
```

### Detailed Walkthrough

**Input: nums = [1,3,5,4,7]**

```
Initialization:
  max = 0
  count = 0

i=0, nums[0]=1:
  Condition: i==0 (first element)
  Action: count++ → count=1
  Update: max = max(0, 1) = 1

i=1, nums[1]=3:
  Condition: nums[1]=3 > nums[0]=1 ✓
  Action: count++ → count=2
  Update: max = max(1, 2) = 2

i=2, nums[2]=5:
  Condition: nums[2]=5 > nums[1]=3 ✓
  Action: count++ → count=3
  Update: max = max(2, 3) = 3

i=3, nums[3]=4:
  Condition: nums[3]=4 not > nums[2]=5 ✗
  Action: count = 1 (reset)
  Update: max = max(3, 1) = 3

i=4, nums[4]=7:
  Condition: nums[4]=7 > nums[3]=4 ✓
  Action: count++ → count=2
  Update: max = max(3, 2) = 3

Return: 3
```

### Why Check i == 0?

**First element handling:**
```java
if (i == 0 || nums[i] > nums[i - 1])
```

**Reason:**
- When i=0, can't access nums[i-1]
- First element always starts a streak
- Check i==0 first (short-circuit evaluation)

**Without check:**
```java
// Wrong: index out of bounds when i=0
if (nums[i] > nums[i - 1])
```

## Complexity Analysis

### One-Pass Solution (Provided):

- **Time Complexity:** O(n)
  - Single pass through array
  - Each element processed once
  - Constant work per element

- **Space Complexity:** O(1)
  - Only two variables: max and count
  - No extra data structures
  - Constant space

## Alternative Approaches

**1. Two Pointers:**
```java
public int findLengthOfLCIS(int[] nums) {
    int max = 1;
    int start = 0;
    
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] <= nums[i-1]) {
            start = i;  // Reset start
        }
        max = Math.max(max, i - start + 1);
    }
    
    return max;
}
```
- Time: O(n)
- Space: O(1)
- Tracks start of current streak
- Same complexity, different style

**2. DP Approach (Overkill):**
```java
public int findLengthOfLCIS(int[] nums) {
    int[] dp = new int[nums.length];
    dp[0] = 1;
    int max = 1;
    
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] > nums[i-1]) {
            dp[i] = dp[i-1] + 1;
        } else {
            dp[i] = 1;
        }
        max = Math.max(max, dp[i]);
    }
    
    return max;
}
```
- Time: O(n)
- Space: O(n) - unnecessary!
- Can be optimized to O(1) like your solution

Your one-pass counter is **optimal and elegant**!

## Edge Cases Handled

✓ **Single element:** [1] → 1  
✓ **All increasing:** [1,2,3,4,5] → 5  
✓ **All decreasing:** [5,4,3,2,1] → 1  
✓ **All equal:** [2,2,2] → 1  
✓ **Multiple streaks:** [1,3,5,4,7] → 3  
✓ **Negative numbers:** [-3,-1,0,2] → 4  

## Pattern Recognition

This problem teaches **Streak Tracking**:

**Use this pattern when:**
- Need to find longest/shortest continuous property
- Property can break and restart
- Simple counter tracks current state

**Key techniques:**
- Current count variable
- Reset on break condition
- Track maximum seen

**Similar problems:**
- Longest Consecutive Sequence
- Max Consecutive Ones
- Longest Subarray of 1's After Deleting One Element
- Arithmetic Slices

## Key Insights

✓ **Single pass** - O(n) time  
✓ **Constant space** - Only two variables  
✓ **Simple logic** - Increment or reset  
✓ **Track max continuously** - Don't miss shorter streaks  
✓ **Handle first element** - Check i==0 first  

## Why Continuous vs Subsequence?

**Continuous (Subarray):**
- Elements must be adjacent
- Example: [1,3,5] in [1,3,5,4,7]
- This problem

**Subsequence (Not necessarily adjacent):**
- Can skip elements
- Example: [1,3,5,7] in [1,3,5,4,7]
- Different problem (Longest Increasing Subsequence)

This problem is simpler: continuous = adjacent!

## Common Mistakes to Avoid

❌ **Not checking i==0 first**
```java
// Wrong: array index out of bounds
if (nums[i] > nums[i-1] || i == 0)

// Right: short-circuit prevents access
if (i == 0 || nums[i] > nums[i-1])
```

❌ **Using >= instead of >**
```java
// Wrong: allows equal (not strictly increasing)
if (nums[i] >= nums[i-1])

// Right: strictly increasing
if (nums[i] > nums[i-1])
```

❌ **Not updating max continuously**
```java
// Wrong: only checks at reset
if (nums[i] <= nums[i-1]) {
    max = Math.max(max, count);
    count = 1;
}

// Right: check every iteration
max = Math.max(max, count);
```

## Strictly Increasing Definition

**Strictly Increasing:** nums[i] < nums[i+1]
- Each element must be strictly greater
- Equal elements break the streak
- Example: [1,2,2,3] has max length 2, not 4

**Non-Strictly (Weakly) Increasing:** nums[i] <= nums[i+1]
- Equal elements allowed
- Different problem

This problem requires **strictly increasing**!

## Tags

Array, Greedy, Sliding Window
