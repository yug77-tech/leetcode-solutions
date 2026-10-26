# 75. Sort Colors

**Difficulty:** Medium

## Problem Description

You are given an array `nums` with `n` objects colored red, white, or blue, sort them **in-place** so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

We will use the integers `0`, `1`, and `2` to represent the color red, white, and blue, respectively.

You must solve this problem **without using the library's sort function**.

## Examples

**Example 1:**
```
Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```

**Example 2:**
```
Input: nums = [2,0,1]
Output: [0,1,2]
```

## Constraints

- `n == nums.length`
- `1 <= n <= 300`
- `nums[i]` is either `0`, `1`, or `2`.

## Follow-up

Could you come up with a one-pass algorithm using only constant space?

## Solution Approach

This is the famous **Dutch National Flag Problem** by Edsger Dijkstra! 🇳🇱

The key insight: **Use three pointers to partition the array into three sections in one pass!**

### Dutch National Flag Algorithm (Three-Way Partitioning)

**Visual Partitioning:**
```
[0,0,0...0 | 1,1,1...1 | ?,?,?...? | 2,2,2...2]
 ^          ^           ^           ^
 0          low         mid         high    length-1

Left section  [0, low):    All 0's (red)
Middle section [low, mid): All 1's (white)
Unknown section [mid, high]: Not yet processed
Right section  (high, n):   All 2's (blue)
```

**Three Pointers:**
- `low`: boundary of 0's region (next position for 0)
- `mid`: current element being examined
- `high`: boundary of 2's region (next position for 2)

**Algorithm:**
1. Initialize: `low = 0`, `mid = 0`, `high = n-1`
2. While `mid <= high`:
   - **Case 1: nums[mid] == 0** (red)
     - Swap with nums[low]
     - Move both low++ and mid++
     - (We know nums[low] was 1, so safe to move mid)
   
   - **Case 2: nums[mid] == 1** (white)
     - Already in correct section
     - Just move mid++
   
   - **Case 3: nums[mid] == 2** (blue)
     - Swap with nums[high]
     - Move high--
     - DON'T move mid (need to check swapped element)

### Visual Example

**Input: [2, 0, 2, 1, 1, 0]**

```
Initial:
[2, 0, 2, 1, 1, 0]
 ^           ^
low         high
mid

nums[mid] = 2 → Swap with high, high--
[0, 0, 2, 1, 1, 2]
 ^        ^
low      high
mid

nums[mid] = 0 → Swap with low, low++, mid++
[0, 0, 2, 1, 1, 2]
    ^     ^
   low   high
   mid

nums[mid] = 0 → Swap with low, low++, mid++
[0, 0, 2, 1, 1, 2]
       ^  ^
      low high
      mid

nums[mid] = 2 → Swap with high, high--
[0, 0, 1, 1, 2, 2]
       ^  ^
      low high
      mid

nums[mid] = 1 → mid++
[0, 0, 1, 1, 2, 2]
       ^  ^
      low high
         mid

nums[mid] = 1 → mid++
[0, 0, 1, 1, 2, 2]
       ^     ^
      low   high
            mid

mid > high → Stop

Result: [0, 0, 1, 1, 2, 2] ✓
```

### Step-by-Step Detailed Walkthrough

**Input: [1, 2, 0]**

```
Step 1:
Array: [1, 2, 0]
        l,m     h
        
nums[mid] = 1 → mid++
        
Step 2:
Array: [1, 2, 0]
        l  m    h
        
nums[mid] = 2 → Swap with high, high--
Array: [1, 0, 2]
        l  m h
        
Step 3:
Array: [1, 0, 2]
        l  m h
        
nums[mid] = 0 → Swap with low, low++, mid++
Array: [0, 1, 2]
           l  m
              h

mid > high → Stop

Result: [0, 1, 2] ✓
```

### Why This Works

**Invariants maintained throughout:**
1. All elements before `low` are 0's
2. All elements from `low` to `mid-1` are 1's
3. All elements after `high` are 2's
4. Elements from `mid` to `high` are unknown

**Why don't we increment mid after swapping with high?**
- When we swap with `low`, we know `nums[low]` was a 1 (it's in the processed region)
- When we swap with `high`, we DON'T know what `nums[high]` was (it's unprocessed)
- So we need to examine the swapped element again

### Why Mid++ After Swap with Low?

```
When nums[mid] == 0:
  Swap with nums[low]
  
Before swap:
[0,0,1,1,?] where low points to first 1
           ^   ^
          low mid

After swap:
[0,0,0,1,1] where ? was swapped to mid
         ^ ^
        low mid

The element at mid is now 1 (from low position)
So it's safe to increment mid!
```

## Complexity Analysis

### Dutch National Flag (Provided):

- **Time Complexity:** O(n)
  - Single pass through array
  - Each element examined at most once
  - mid pointer only moves forward

- **Space Complexity:** O(1)
  - Only three pointer variables
  - In-place sorting
  - No extra arrays or data structures

### Alternative Approaches

**1. Counting Sort (Two Pass):**
```java
public void sortColors(int[] nums) {
    int count0 = 0, count1 = 0, count2 = 0;
    
    // First pass: count
    for (int num : nums) {
        if (num == 0) count0++;
        else if (num == 1) count1++;
        else count2++;
    }
    
    // Second pass: fill
    int i = 0;
    while (count0-- > 0) nums[i++] = 0;
    while (count1-- > 0) nums[i++] = 1;
    while (count2-- > 0) nums[i++] = 2;
}
```
- Time: O(n), Space: O(1)
- Two passes instead of one
- Simpler logic but not optimal

**2. Library Sort:**
```java
public void sortColors(int[] nums) {
    Arrays.sort(nums);
}
```
- Time: O(n log n)
- Problem explicitly forbids this!

**3. Bubble/Selection Sort:**
- Time: O(n²)
- Way too slow

Your Dutch National Flag is **optimal**: O(n) time, O(1) space, one pass! 🎯

## Edge Cases Handled

✓ **All same color:** [0,0,0] → [0,0,0]  
✓ **Already sorted:** [0,1,2] → [0,1,2]  
✓ **Reverse sorted:** [2,1,0] → [0,1,2]  
✓ **Two colors:** [0,0,2,2] → [0,0,2,2]  
✓ **Single element:** [1] → [1]  
✓ **Two elements:** [2,0] → [0,2]  

## Why Called "Dutch National Flag"?

The Dutch flag has three horizontal stripes (red, white, blue):
```
🟥 Red
⬜ White
🟦 Blue
```

This problem partitions array into three sections, just like the flag's three colors!

Named by **Edsger Dijkstra**, a Dutch computer scientist who created this algorithm.

## Pattern Recognition

This problem teaches **Three-Way Partitioning**:

**Use when:**
- Need to partition into 3+ categories
- Want to sort with limited distinct values
- Need one-pass, in-place algorithm

**Similar problems:**
- Partition array around pivot (QuickSort)
- Move all zeros to end
- Segregate even/odd numbers
- Sort binary array

## Key Insights

✓ **Don't increment mid after swapping with high** - Unknown element  
✓ **Do increment mid after swapping with low** - Known to be 1  
✓ **Condition is `mid <= high`** - Process all elements  
✓ **Three pointers** - One for each color boundary  
✓ **Single pass** - More elegant than counting sort  

## Tags

Array, Two Pointers, Sorting
