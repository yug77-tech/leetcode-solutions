# 561. Array Partition

**Difficulty:** Easy

## Problem Description

Given an integer array `nums` of `2n` integers, group these integers into `n` pairs `(a1, b1), (a2, b2), ..., (an, bn)` such that the sum of `min(ai, bi)` for all `i` is **maximized**. Return the maximized sum.

## Examples

**Example 1:**
```
Input: nums = [1,4,3,2]
Output: 4
Explanation: All possible pairings (ignoring the ordering of elements) are:
1. (1, 4), (2, 3) → min(1, 4) + min(2, 3) = 1 + 2 = 3
2. (1, 3), (2, 4) → min(1, 3) + min(2, 4) = 1 + 2 = 3
3. (1, 2), (3, 4) → min(1, 2) + min(3, 4) = 1 + 3 = 4 ✓
```

**Example 2:**
```
Input: nums = [6,2,6,5,1,2]
Output: 9
Explanation: The optimal pairing is (2, 1), (2, 5), (6, 6). 
min(2, 1) + min(2, 5) + min(6, 6) = 1 + 2 + 6 = 9.
```

## Constraints

- `1 <= n <= 10^4`
- `nums.length == 2 * n`
- `-10^4 <= nums[i] <= 10^4`

## Solution Approach

**Key Insight:** 
To maximize the sum of minimums, we should **pair adjacent elements after sorting**!

**Why?**
- When we take min(a, b), we "lose" the larger value
- To minimize loss, pair each element with its closest neighbor
- After sorting: [a, b, c, d], pair as (a,b), (c,d)
- This ensures we only "lose" the smallest possible values

**Algorithm:**
1. Sort the array in ascending order
2. Sum all elements at even indices (0, 2, 4, ...)
3. These are the minimum values from each optimal pair

**Example:** nums = [1, 4, 3, 2]
```
Step 1: Sort → [1, 2, 3, 4]
Step 2: Pairs → (1,2), (3,4)
Step 3: Sum minimums → 1 + 3 = 4
```

## Complexity Analysis

### All Optimized Solutions (Solution.java & SolutionExplicit.java):
- **Time Complexity:** O(n log n) - Dominated by sorting
- **Space Complexity:** O(1) or O(log n) - Depending on sort implementation

**Note:** Both optimized versions have identical complexity. The `Math.min()` call in the explicit version adds O(n) operations, which doesn't change the overall O(n log n) time complexity.

### Bubble Sort Solution (SolutionBubbleSort.java):
- **Time Complexity:** O(n²) - Bubble sort worst/average case
- **Space Complexity:** O(1) - In-place sorting
- **Note:** Works but not efficient for large inputs

## Solution Variants

### 1. Optimized with Built-in Sort (Recommended - Solution.java)
Uses `Arrays.sort()` for O(n log n) time complexity.
- Directly sums `nums[i]` (even indices)
- Most efficient: no redundant operations
- Time: O(n log n), Space: O(1)

### 2. Explicit Math.min() Version (SolutionExplicit.java)
Same algorithm but uses `Math.min(nums[i], nums[i+1])`.
- More readable and self-documenting
- Makes pairing logic explicit
- Functionally identical after sorting (since nums[i] ≤ nums[i+1])
- Time: O(n log n), Space: O(1)
- Trade-off: Slightly more readable but redundant function calls

### 3. Bubble Sort Implementation (SolutionBubbleSort.java)
Manual bubble sort implementation for educational purposes.
- Good space complexity (O(1))
- Poor time complexity (O(n²))
- Demonstrates understanding of sorting algorithms
- Includes optimization: early exit when no swaps occur

## Why Bubble Sort is Not Ideal Here

While bubble sort has O(1) space complexity, its O(n²) time complexity makes it impractical for:
- Large arrays (n up to 10^4 means up to 10^8 operations!)
- Time-constrained environments (interviews, competitions)

Built-in sorting (typically QuickSort/MergeSort) is much faster in practice.

## Tags

Array, Greedy, Sorting
