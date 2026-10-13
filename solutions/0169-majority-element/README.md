# 169. Majority Element

**Difficulty:** Easy

## Problem Description

Given an array `nums` of size `n`, return the **majority element**.

The majority element is the element that appears more than `⌊n / 2⌋` times. You may assume that the majority element always exists in the array.

## Examples

**Example 1:**
```
Input: nums = [3,2,3]
Output: 3
```

**Example 2:**
```
Input: nums = [2,2,1,1,1,2,2]
Output: 2
```

## Constraints

- `n == nums.length`
- `1 <= n <= 5 * 10^4`
- `-10^9 <= nums[i] <= 10^9`

## Follow-up

Could you solve the problem in linear time and in O(1) space?

## Solution Approach

This problem can be solved elegantly using the **Boyer-Moore Voting Algorithm**!

### Understanding the Problem

**Majority element** appears more than n/2 times:
- In an array of 7 elements, majority appears at least 4 times
- In an array of 8 elements, majority appears at least 5 times
- **Key insight:** Majority element appears more times than all other elements combined!

### Boyer-Moore Voting Algorithm

The algorithm works like a "voting" system with cancellation:

**Intuition:**
- Imagine each occurrence of the majority element "votes" for itself
- Each occurrence of other elements "votes" against it
- Since majority appears > n/2 times, it will always win!

**Algorithm:**
1. Initialize `candidate` and `count = 0`
2. For each element:
   - If `count == 0`, set current element as new candidate
   - If element equals candidate, increment count
   - Otherwise, decrement count
3. Return candidate

**Why this works:**
- When count reaches 0, we've seen equal numbers of candidate and non-candidate elements
- We can "throw away" these equal pairs
- The remaining elements still contain the majority
- After processing all elements, candidate must be the majority!

### Visual Example

**nums = [2, 2, 1, 1, 1, 2, 2]**

```
Element | Candidate | Count | Explanation
--------|-----------|-------|------------
2       | 2         | 1     | count=0, set candidate=2, count++
2       | 2         | 2     | matches candidate, count++
1       | 2         | 1     | doesn't match, count--
1       | 2         | 0     | doesn't match, count--
1       | 1         | 1     | count=0, set candidate=1, count++
2       | 1         | 0     | doesn't match, count--
2       | 2         | 1     | count=0, set candidate=2, count++

Result: candidate = 2 ✓
```

**Key observation:** 
- We "canceled out" 2 vs 1, 2 vs 1, and 1 vs 2
- Three pairs canceled, leaving [2, 2, 2, 2]
- Majority element survives!

### Why It Always Works

**Proof sketch:**
1. Majority element appears > n/2 times
2. All other elements combined appear < n/2 times
3. Even if we pair up and cancel majority with non-majority elements
4. Majority will have leftover occurrences (since it's more than half!)
5. These leftover occurrences ensure the final candidate is the majority

## Complexity Analysis

### Boyer-Moore Voting Algorithm (Provided Solution):

- **Time Complexity:** O(n) - Single pass through array
- **Space Complexity:** O(1) - Only two variables (candidate, count)

### Alternative Approaches

**1. Sorting:**
```java
Arrays.sort(nums);
return nums[n/2];  // Middle element must be majority
```
- Time: O(n log n), Space: O(1) or O(n)

**2. HashMap:**
```java
Map<Integer, Integer> counts = new HashMap<>();
for (int num : nums) {
    counts.put(num, counts.getOrDefault(num, 0) + 1);
    if (counts.get(num) > n/2) return num;
}
```
- Time: O(n), Space: O(n)

**3. Bit Manipulation:**
- Count bits at each position
- Time: O(n), Space: O(1)

The Boyer-Moore algorithm is optimal: **O(n) time, O(1) space**!

## Tags

Array, Hash Table, Divide and Conquer, Sorting, Counting, Boyer-Moore Voting Algorithm
