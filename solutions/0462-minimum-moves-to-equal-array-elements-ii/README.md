# 462. Minimum Moves to Equal Array Elements II

**Difficulty:** Medium

## Problem Description

Given an integer array `nums` of size `n`, return the **minimum number of moves** required to make all array elements equal.

In one move, you can **increment or decrement** an element of the array by `1`.

Test cases are designed so that the answer will fit in a **32-bit integer**.

## Examples

**Example 1:**
```
Input: nums = [1,2,3]
Output: 2
Explanation:
Only two moves are needed (remember each move increments or decrements one element):
[1,2,3]  =>  [2,2,3]  =>  [2,2,2]
```

**Example 2:**
```
Input: nums = [1,10,2,9]
Output: 16
Explanation:
Optimal target is 5 or 6 (median).
Using 5: |1-5| + |10-5| + |2-5| + |9-5| = 4+5+3+4 = 16
Using 6: |1-6| + |10-6| + |2-6| + |9-6| = 5+4+4+3 = 16
```

## Constraints

- `n == nums.length`
- `1 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

## Solution Approach

This is a **classic median problem!** The key insight: **The optimal target is the MEDIAN of the array!**

### Mathematical Insight: Why Median?

**The Problem:**
- Can increment OR decrement (unlike problem 453)
- Need to find optimal target value
- Minimize total distance to target

**The Answer: MEDIAN**
- Median minimizes sum of absolute deviations
- This is a well-known property in statistics!

**Why not mean (average)?**
- Mean minimizes sum of squared distances
- Median minimizes sum of absolute distances
- We need absolute distances (moves)!

### Visual Example

**Input: [1,2,3]**

```
Array: [1, 2, 3]
       
After sorting: [1, 2, 3]
Median = nums[3/2] = nums[1] = 2

Distances from median:
|1 - 2| = 1
|2 - 2| = 0
|3 - 2| = 1

Total moves = 1 + 0 + 1 = 2 ✓
```

**Input: [1,10,2,9]**

```
After sorting: [1, 2, 9, 10]
Median = nums[4/2] = nums[2] = 9

Wait, that seems wrong. Let me reconsider...

For even length, any value between the two middle elements works!
Middle elements: nums[1]=2 and nums[2]=9

Let's try median = 2:
|1-2| + |2-2| + |9-2| + |10-2| = 1+0+7+8 = 16 ✓

Let's try median = 9:
|1-9| + |2-9| + |9-9| + |10-9| = 8+7+0+1 = 16 ✓

Let's try median = 5 (middle value):
|1-5| + |2-5| + |9-5| + |10-5| = 4+3+4+5 = 16 ✓

All give same result!
Convention: use nums[n/2]
```

### Why Median Minimizes Sum of Absolute Deviations

**Intuition:**

Imagine elements on a number line:
```
1    2         9  10
●    ●         ●  ●

Target at 2:
←1→  ○         ←7→←8→
     2
Distance: 1 + 7 + 8 = 16

Target at 5:
←4→  ←3→       ←4→←5→
         5
Distance: 4 + 3 + 4 + 5 = 16

Target at 9:
←8→  ←7→       ○  ←1→
               9
Distance: 8 + 7 + 1 = 16
```

**Mathematical Property:**
- For sorted array, median balances left and right
- Moving target left: increase right distances > decrease left distances
- Moving target right: increase left distances > decrease right distances
- Median is the equilibrium point!

### Detailed Walkthrough

**Input: nums = [1,2,3]**

```
Step 1: Sort array
sorted = [1, 2, 3]

Step 2: Find median
median = nums[3/2] = nums[1] = 2

Step 3: Calculate moves
moves = 0

For each element:
  num = 1: moves += |1 - 2| = 1
  num = 2: moves += |2 - 2| = 0
  num = 3: moves += |3 - 2| = 1

Total = 1 + 0 + 1 = 2

Return 2
```

**Input: nums = [1,10,2,9]**

```
Step 1: Sort array
sorted = [1, 2, 9, 10]

Step 2: Find median
median = nums[4/2] = nums[2] = 9

Step 3: Calculate moves
For each element:
  num = 1: moves += |1 - 9| = 8
  num = 2: moves += |2 - 9| = 7
  num = 9: moves += |9 - 9| = 0
  num = 10: moves += |10 - 9| = 1

Total = 8 + 7 + 0 + 1 = 16

Return 16
```

### Even Length Arrays

**For even n, which median?**

Array: [1, 2, 9, 10]
- Lower median: nums[1] = 2
- Upper median: nums[2] = 9

**Both work equally well!**
```
Using 2: |1-2| + |2-2| + |9-2| + |10-2| = 1+0+7+8 = 16
Using 9: |1-9| + |2-9| + |9-9| + |10-9| = 8+7+0+1 = 16
```

Java convention: `nums[n/2]` gives upper median for even n

### Comparison with Problem 453

| Aspect | Problem 453 | Problem 462 |
|--------|-------------|-------------|
| Operation | Increment n-1 by 1 | Inc/Dec any by 1 |
| Target | Minimum | Median |
| Formula | sum(num - min) | sum(\|num - median\|) |
| Sorting | Not needed | Required |
| Complexity | O(n) | O(n log n) |

**Key difference:**
- 453: Can only increase → target = minimum
- 462: Can inc/dec → target = median

## Complexity Analysis

### Median Approach (Provided):

- **Time Complexity:** O(n log n)
  - Sorting: O(n log n)
  - Finding median: O(1)
  - Calculating moves: O(n)
  - Total: O(n log n) dominated by sorting

- **Space Complexity:** O(1) or O(log n)
  - Depends on sorting algorithm
  - QuickSort: O(log n) recursion
  - In-place sorting: O(1) extra space
  - Not counting input array

### Alternative: QuickSelect (O(n) average)

```java
public int minMoves2(int[] nums) {
    int median = quickSelect(nums, nums.length / 2);
    int moves = 0;
    for (int num : nums) {
        moves += Math.abs(num - median);
    }
    return moves;
}
```
- Time: O(n) average, O(n²) worst
- Space: O(log n) recursion
- More complex, sorting is simpler!

## Edge Cases Handled

✓ **All equal:** [5,5,5] → 0 moves  
✓ **Two elements:** [1,3] → 2 moves (both to 2)  
✓ **Large gaps:** [1,100] → 99 moves  
✓ **Negative numbers:** [-5,0,5] → Works (median=0)  
✓ **Single element:** [42] → 0 moves  
✓ **Sorted already:** [1,2,3,4] → Already sorted  

## Pattern Recognition

This problem teaches **Median Optimization**:

**Use median when:**
- Minimize sum of absolute differences
- Find optimal meeting point
- Balance distances from target

**Use mean when:**
- Minimize sum of squared differences
- Least squares optimization

**Similar problems:**
- Meeting point problem
- Minimum total distance
- Optimal array transformation

## Mathematical Proof: Why Median?

**Claim:** Median minimizes sum of absolute deviations

**Proof (Intuitive):**

For sorted array [a₁, a₂, ..., aₙ]:

If target < median:
- All right elements farther
- Left elements slightly closer
- Net increase in distance

If target > median:
- All left elements farther
- Right elements slightly closer
- Net increase in distance

Median balances left and right optimally!

**Formal Proof:**

For odd n, median is unique.
For even n, any value between two middle elements works.

Moving target by δ:
- Increases distance for k elements on one side
- Decreases distance for k elements on other side
- Net change = 0 only at median

## Key Insights

✓ **Median is optimal** - Minimizes absolute deviations  
✓ **Must sort first** - To find median  
✓ **Even length: any middle works** - Both medians give same result  
✓ **O(n log n) solution** - Sorting dominates  
✓ **Different from problem 453** - Can inc/dec vs only inc  

## Why Not Mean (Average)?

**Example: [1, 2, 100]**

```
Mean = (1 + 2 + 100) / 3 = 34.33

Using mean (34):
|1-34| + |2-34| + |100-34| = 33 + 32 + 66 = 131

Median = 2

Using median (2):
|1-2| + |2-2| + |100-2| = 1 + 0 + 98 = 99 ✓

Median is much better!
```

**Why?**
- Mean affected by outliers
- Median is robust to extremes
- Absolute distance cares about order, not magnitude

## Common Mistakes to Avoid

❌ **Using mean instead of median**
```java
// Wrong!
int target = sum / n;
// Mean doesn't minimize absolute distances
```

❌ **Forgetting to sort**
```java
// Wrong!
int median = nums[nums.length / 2];
// Need sorted array first!
```

❌ **Not using Math.abs**
```java
// Wrong!
moves += num - median;
// Need absolute value!
```

## Alternative: Two Pointers (Without Finding Median)

```java
public int minMoves2(int[] nums) {
    Arrays.sort(nums);
    int left = 0, right = nums.length - 1;
    int moves = 0;
    
    while (left < right) {
        moves += nums[right] - nums[left];
        left++;
        right--;
    }
    
    return moves;
}
```

**How it works:**
- Pair smallest with largest
- Distance = difference between pairs
- Sum all distances
- Same result, different approach!

## Tags

Array, Math, Sorting
