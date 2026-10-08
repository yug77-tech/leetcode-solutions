# 704. Binary Search

**Difficulty:** Easy

## Problem Description

Given an array of integers `nums` which is sorted in ascending order, and an integer `target`, write a function to search `target` in `nums`. If `target` exists, then return its index. Otherwise, return `-1`.

You must write an algorithm with **O(log n)** runtime complexity.

## Examples

**Example 1:**
```
Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums and its index is 4
```

**Example 2:**
```
Input: nums = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in nums so return -1
```

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 < nums[i], target < 10^4`
- All the integers in `nums` are **unique**.
- `nums` is sorted in ascending order.

## Solution Approach

This is the **classic Binary Search** algorithm - one of the most fundamental algorithms in computer science.

**Core Idea:**
- Since the array is sorted, we can eliminate half of the search space in each iteration
- Compare the middle element with target
- Based on comparison, search either left or right half

**Algorithm:**
1. Initialize two pointers: `left = 0`, `right = nums.length - 1`
2. While `left <= right`:
   - Calculate middle index: `mid = (left + right) / 2`
   - If `nums[mid] == target`: Found! Return `mid`
   - If `nums[mid] < target`: Target is in right half, set `left = mid + 1`
   - If `nums[mid] > target`: Target is in left half, set `right = mid - 1`
3. If loop completes without finding target: Return `-1`

**Example Walkthrough** (nums = [-1,0,3,5,9,12], target = 9):
```
Initial: left=0, right=5

Iteration 1:
  mid = (0+5)/2 = 2
  nums[2] = 3 < 9
  left = 3

Iteration 2:
  mid = (3+5)/2 = 4
  nums[4] = 9 == 9
  Return 4 ✓
```

**Why O(log n)?**
- Each iteration eliminates half the remaining elements
- After k iterations, search space = n / 2^k
- When search space = 1: n / 2^k = 1 → k = log₂(n)

## Complexity Analysis

- **Time Complexity:** O(log n) - Halves search space each iteration
- **Space Complexity:** O(1) - Only using constant extra space

## Important Note

To avoid integer overflow in some languages, you can calculate `mid` as:
```java
int mid = left + (right - left) / 2;
```
Instead of:
```java
int mid = (left + right) / 2;
```

However, in Java with typical array sizes, the simpler version is fine.

## Tags

Array, Binary Search
