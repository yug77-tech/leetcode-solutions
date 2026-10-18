# 121. Best Time to Buy and Sell Stock

**Difficulty:** Easy

## Problem Description

You are given an array `prices` where `prices[i]` is the price of a given stock on the `ith` day.

You want to maximize your profit by choosing a **single day** to buy one stock and choosing a **different day in the future** to sell that stock.

Return the *maximum profit* you can achieve from this transaction. If you cannot achieve any profit, return `0`.

## Examples

**Example 1:**
```
Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
```

**Example 2:**
```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transactions are done and the max profit = 0.
```

## Constraints

- `1 <= prices.length <= 10^5`
- `0 <= prices[i] <= 10^4`

## Solution Approach

The key insight: **Track the minimum price seen so far, and calculate profit at each step!**

### One-Pass Greedy/DP Algorithm

**Core Idea:**
- As we scan left to right, we want to buy at the lowest price we've seen
- At each price, calculate the profit if we sell today
- Keep track of the maximum profit

**Why this works:**
- If we sell on day `i`, we must have bought on some day `j < i`
- To maximize profit on day `i`, we should have bought at the minimum price before day `i`
- By tracking `min` as we go, we always know the best buy price for any sell day

### Visual Example

**Input: prices = [7, 1, 5, 3, 6, 4]**

```
Day | Price | Min so far | Sell today (price - min) | Max profit
----|-------|------------|--------------------------|------------
0   | 7     | 7          | 7 - 7 = 0                | 0
1   | 1     | 1          | 1 - 1 = 0                | 0
2   | 5     | 1          | 5 - 1 = 4                | 4
3   | 3     | 1          | 3 - 1 = 2                | 4
4   | 6     | 1          | 6 - 1 = 5                | 5 ✓
5   | 4     | 1          | 4 - 1 = 3                | 5

Result: Maximum profit = 5
Buy at price 1 (day 1), sell at price 6 (day 4)
```

### Step-by-Step Walkthrough

**Example: [7, 1, 5, 3, 6, 4]**

```
Initial: min = 7, profit = 0

i=1: prices[1] = 1
  1 < 7 → update min = 1
  profit = max(0, 1-1) = 0

i=2: prices[2] = 5
  5 ≥ 1 → min stays 1
  profit = max(0, 5-1) = 4

i=3: prices[3] = 3
  3 ≥ 1 → min stays 1
  profit = max(4, 3-1) = 4

i=4: prices[4] = 6
  6 ≥ 1 → min stays 1
  profit = max(4, 6-1) = 5

i=5: prices[5] = 4
  4 ≥ 1 → min stays 1
  profit = max(5, 4-1) = 5

Return: 5
```

### Algorithm Logic

For each price:
1. **If price < min:** Update minimum (found better buy day)
2. **Else:** Calculate profit if selling today, update max profit

The `else` ensures we don't buy and sell on the same day (since we update min first).

### Why This is Optimal

✓ **Single pass** - O(n) time  
✓ **Constant space** - O(1) space  
✓ **Greedy approach** - Always uses best buy price available  
✓ **No need to track buy/sell days** - Only profit matters  
✓ **Handles no profit case** - Returns 0 automatically  

## Complexity Analysis

### Optimal Solution (Provided):

- **Time Complexity:** O(n)
  - Single pass through the array
  - Constant work per element

- **Space Complexity:** O(1)
  - Only two variables: `min` and `profit`
  - No extra data structures

### Alternative Approaches

**1. Brute Force (Nested Loop):**
```java
public int maxProfit(int[] prices) {
    int maxProfit = 0;
    for (int i = 0; i < prices.length; i++) {
        for (int j = i + 1; j < prices.length; j++) {
            maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
        }
    }
    return maxProfit;
}
```
- Time: O(n²), Space: O(1)
- Too slow for large inputs

**2. Track Min and Max Separately:**
```java
public int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;
    for (int price : prices) {
        minPrice = Math.min(minPrice, price);
        maxProfit = Math.max(maxProfit, price - minPrice);
    }
    return maxProfit;
}
```
- Time: O(n), Space: O(1)
- Slightly different style, same logic

**3. Kadane's Algorithm Variant:**
```java
public int maxProfit(int[] prices) {
    int maxProfit = 0;
    int maxCurrent = 0;
    for (int i = 1; i < prices.length; i++) {
        maxCurrent = Math.max(0, maxCurrent + prices[i] - prices[i-1]);
        maxProfit = Math.max(maxProfit, maxCurrent);
    }
    return maxProfit;
}
```
- Time: O(n), Space: O(1)
- Views problem as maximum subarray sum of daily changes

Your solution is the **most intuitive and readable**!

## Edge Cases Handled

✓ **Decreasing prices:** [7,6,4,3,1] → profit = 0  
✓ **Single day:** [5] → profit = 0 (can't sell)  
✓ **All same price:** [3,3,3,3] → profit = 0  
✓ **Increasing prices:** [1,2,3,4,5] → profit = 4 (buy day 0, sell day 4)  
✓ **Valley then peak:** [5,1,5] → profit = 4 (buy at valley)  

## Problem Pattern

This problem is a variant of:
- **Maximum subarray problem** (Kadane's algorithm)
- **Dynamic programming** (optimal substructure)
- **Greedy algorithm** (local optimal leads to global optimal)

## Tags

Array, Dynamic Programming, Greedy
