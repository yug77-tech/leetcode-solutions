# 70. Climbing Stairs

**Difficulty:** Easy

## Problem Description

You are climbing a staircase. It takes `n` steps to reach the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

## Examples

**Example 1:**
```
Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
```

**Example 2:**
```
Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
```

## Constraints

- `1 <= n <= 45`

## Solution Approach

This is a classic dynamic programming problem that follows the Fibonacci sequence pattern.

**Key Insight:** To reach step `n`, you can either:
- Come from step `n-1` (by taking 1 step), OR
- Come from step `n-2` (by taking 2 steps)

Therefore: `dp[n] = dp[n-1] + dp[n-2]`

**Algorithm:**
1. Base cases: 
   - For n=1: only 1 way (one step)
   - For n=2: 2 ways (1+1 or 2)
2. For each step i from 3 to n:
   - Calculate dp[i] = dp[i-1] + dp[i-2]
3. Return dp[n]

## Complexity Analysis

- **Time Complexity:** O(n) - Single pass through the steps
- **Space Complexity:** O(1) - Only using constant extra space (prev1, prev2, current)

**Space Optimization:** Instead of storing all values in a DP array, we only keep track of the last two values since those are the only ones needed to calculate the next value.

## Tags

Dynamic Programming, Math, Memoization
