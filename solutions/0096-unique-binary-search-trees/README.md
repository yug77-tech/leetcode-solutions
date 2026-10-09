# 96. Unique Binary Search Trees

**Difficulty:** Medium

## Problem Description

Given an integer `n`, return the number of structurally unique **BST's** (binary search trees) which has exactly `n` nodes of unique values from `1` to `n`.

## Examples

**Example 1:**

![BST Example](https://assets.leetcode.com/uploads/2021/01/18/uniquebstn3.jpg)

```
Input: n = 3
Output: 5
Explanation: There are 5 unique BSTs with 3 nodes:
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
```

**Example 2:**
```
Input: n = 1
Output: 1
```

## Constraints

- `1 <= n <= 19`

## Solution Approach

This problem is solved using **Catalan Numbers** - a sequence that appears in many combinatorial problems!

### Understanding the Problem

For n nodes with values 1 to n:
- Any value can be the root
- Values less than root go in left subtree
- Values greater than root go in right subtree
- Number of unique BSTs = sum of (left subtree combinations × right subtree combinations)

### The Mathematical Insight

The number of unique BSTs follows the **nth Catalan Number**:

**Formula:** C(n) = (2n)! / ((n+1)! × n!)

Which can be computed iteratively as:
```
C(n) = C(0) × [2(2×0+1) / (0+2)] × [2(2×1+1) / (1+2)] × ... × [2(2×(n-1)+1) / (n-1+2)]
```

Starting with C(0) = 1, we multiply by these factors.

### Algorithm

1. Initialize `result = 1`
2. For i from 0 to n-1:
   - Multiply result by `2 × (2×i + 1)`
   - Divide result by `(i + 2)`
3. Return result

**Why this works:**
- We're computing the Catalan number iteratively
- Using long to avoid overflow during computation
- The division is always exact (no remainder)

### Example Walkthrough (n = 3):

```
Initial: result = 1

i = 0:
  result = 1 × 2×1 / 2 = 1

i = 1:
  result = 1 × 2×3 / 3 = 2

i = 2:
  result = 2 × 2×5 / 4 = 5

Result: 5 ✓
```

### Catalan Number Sequence

```
n = 0: 1
n = 1: 1
n = 2: 2
n = 3: 5
n = 4: 14
n = 5: 42
n = 6: 132
...
```

## Complexity Analysis

- **Time Complexity:** O(n) - Single loop from 0 to n-1
- **Space Complexity:** O(1) - Only using constant extra space

## Alternative Approaches

### Dynamic Programming Approach:
```java
public int numTrees(int n) {
    int[] dp = new int[n + 1];
    dp[0] = dp[1] = 1;
    
    for (int i = 2; i <= n; i++) {
        for (int j = 1; j <= i; j++) {
            dp[i] += dp[j - 1] * dp[i - j];
        }
    }
    return dp[n];
}
```
- Time: O(n²), Space: O(n)
- More intuitive but less efficient

## Tags

Math, Dynamic Programming, Binary Search Tree, Catalan Numbers, Combinatorics
