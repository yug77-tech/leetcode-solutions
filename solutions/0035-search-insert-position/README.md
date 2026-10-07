# 35. Search Insert Position

**Difficulty:** Easy

## Problem Description

Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with **O(log n)** runtime complexity.

## Examples

**Example 1:**
```
Input: nums = [1,3,5,6], target = 5
Output: 2
```

**Example 2:**
```
Input: nums = [1,3,5,6], target = 2
Output: 1
```

**Example 3:**
```
Input: nums = [1,3,5,6], target = 7
Output: 4
```

**Example 4:**
```
Input: nums = [1,3,5,6], target = 0
Output: 0
```

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` contains **distinct** values sorted in **ascending** order.
- `-10^4 <= target <= 10^4`

## Solution Approach

This problem requires **Binary Search** to achieve O(log n) time complexity.

**Key Insight:**
- Use binary search to find the target
- If target is found, return its index
- If target is not found, the `left` pointer will be at the correct insertion position

**Algorithm:**
1. Initialize `left = 0` and `right = nums.length - 1`
2. While `left <= right`:
   - Calculate `mid = (left + right) / 2`
   - If `nums[mid] == target`: return `mid` (found!)
   - If `nums[mid] < target`: search right half (`left = mid + 1`)
   - If `nums[mid] > target`: search left half (`right = mid - 1`)
3. If loop ends without finding target, return `left`

**Why return `left`?**
When the loop exits:
- `left > right` (they've crossed)
- `left` points to the first element greater than target
- This is exactly where target should be inserted!

**Example Walkthrough** (nums = [1,3,5,6], target = 2):
```
Initial: left=0, right=3

Iteration 1:
  mid = (0+3)/2 = 1
  nums[1] = 3 > 2
  right = 0

Iteration 2:
  mid = (0+0)/2 = 0
  nums[0] = 1 < 2
  left = 1

Loop exits: left=1, right=0
Return left=1 (insert position between 1 and 3)
```

## Complexity Analysis

- **Time Complexity:** O(log n) - Binary search halves search space each iteration
- **Space Complexity:** O(1) - Only using constant extra space

## Tags

Array, Binary Search
