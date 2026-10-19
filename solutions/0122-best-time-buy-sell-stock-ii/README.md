# 122. Best Time to Buy and Sell Stock II

**Difficulty:** Medium

## Problem Description

You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `ith` day.

On each day, you may decide to buy and/or sell the stock. You can only hold **at most one share** of the stock at any time. However, you can buy it then immediately sell it on the **same day**.

Find and return the *maximum profit* you can achieve.

## Examples

**Example 1:**
```
Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
Total profit is 4 + 3 = 7.
```

**Example 2:**
```
Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Total profit is 4.
Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging
multiple transactions at the same time. You must sell before buying again.
```

**Example 3:**
```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: There is no way to make a positive profit, so we never buy the stock to achieve
the maximum profit of 0.
```

## Constraints

- `1 <= prices.length <= 3 * 10^4`
- `0 <= prices[i] <= 10^4`

## Solution Approach

The key insight: **Capture every upward price movement!**

### Brilliant Greedy Strategy

**Core Idea:**
- Since we can transact unlimited times, we want to profit from every price increase
- If tomorrow's price is higher than today's, buy today and sell tomorrow
- This is equivalent to summing all positive daily differences!

**Why this works:**
- Any upward trend can be broken into daily increases
- Example: Buy at 1, sell at 5 = (2-1) + (3-2) + (4-3) + (5-4) = 4
- We capture the same total profit by taking every upward step!

### Visual Proof

**Example: prices = [1, 3, 2, 5]**

```
Prices:  1 → 3 → 2 → 5
         ↑+2  ↓-1  ↑+3

Strategy 1: Buy at 1, sell at 3, buy at 2, sell at 5
  Profit = (3-1) + (5-2) = 2 + 3 = 5

Strategy 2: Capture every upward movement
  Day 1→2: +2 (3-1)
  Day 2→3: 0  (2>3? no)
  Day 3→4: +3 (5-2)
  Total = 2 + 3 = 5 ✓

Both give same result!
```

**The Magic:**
```
prices[i] - prices[j] (where i > j)
= (prices[i] - prices[i-1]) + (prices[i-1] - prices[i-2]) + ... + (prices[j+1] - prices[j])

Any multi-day profit = Sum of all daily profits!
```

### Step-by-Step Example

**Input: [7, 1, 5, 3, 6, 4]**

```
Day | Price | Daily Change | Add to Profit? | Running Profit
----|-------|--------------|----------------|---------------
0   | 7     | -            | -              | 0
1   | 1     | 1-7 = -6     | No (negative)  | 0
2   | 5     | 5-1 = +4     | Yes            | 4
3   | 3     | 3-5 = -2     | No (negative)  | 4
4   | 6     | 6-3 = +3     | Yes            | 7
5   | 4     | 4-6 = -2     | No (negative)  | 7

Result: 7

Transactions:
- Buy at 1, sell at 5: profit = 4
- Buy at 3, sell at 6: profit = 3
- Total: 7
```

### Algorithm Logic

```
For each day i (starting from day 1):
  If prices[i] > prices[i-1]:
    profit += prices[i] - prices[i-1]
```

**Simple as that!**

### Why This is Optimal

✓ **Greedy works perfectly** - Every local upward move contributes to global maximum  
✓ **One pass** - O(n) time  
✓ **Constant space** - O(1) space  
✓ **Mathematically equivalent** - Captures all possible profit  
✓ **Elegant** - Just 4 lines of actual code!  

## Complexity Analysis

### Greedy Solution (Provided):

- **Time Complexity:** O(n)
  - Single pass through the array
  - Constant work per element

- **Space Complexity:** O(1)
  - Only one variable: `profit`
  - No extra data structures

## Comparison with Problem 121

| Aspect | Problem 121 (Single Transaction) | Problem 122 (Multiple Transactions) |
|--------|----------------------------------|-------------------------------------|
| **Transactions** | 1 only | Unlimited |
| **Strategy** | Find global min/max pair | Capture every upward move |
| **Algorithm** | Track min, calc max profit | Sum all positive differences |
| **Complexity** | O(n) time, O(1) space | O(n) time, O(1) space |
| **Code** | ~10 lines | ~5 lines |

Problem 122 is actually **simpler** despite being "Medium"!

## Mathematical Proof

**Claim:** Sum of positive daily differences = Maximum possible profit

**Proof:**
- Let optimal transactions be: buy at `b₁, b₂, ...` and sell at `s₁, s₂, ...`
- Total profit = Σ(sᵢ - bᵢ)
- Each (sᵢ - bᵢ) = sum of daily increases from bᵢ to sᵢ
- Ignoring all daily decreases (negative differences) is optimal
- Therefore: Total profit = Σ(all positive daily differences)

**Example:**
```
prices = [1, 3, 5, 2, 8]

Optimal: Buy at 1, sell at 5, buy at 2, sell at 8
Profit = (5-1) + (8-2) = 4 + 6 = 10

Daily differences:
  Day 1→2: +2 (3-1)
  Day 2→3: +2 (5-3)
  Day 3→4: -3 (2-5) → skip
  Day 4→5: +6 (8-2)
  Sum of positives = 2 + 2 + 6 = 10 ✓
```

## Edge Cases Handled

✓ **Decreasing prices:** [7,6,4,3,1] → profit = 0 (no positive differences)  
✓ **Increasing prices:** [1,2,3,4,5] → profit = 4 (sum all differences)  
✓ **Single day:** [5] → profit = 0 (no comparisons)  
✓ **Flat prices:** [3,3,3,3] → profit = 0 (no increases)  
✓ **Zigzag:** [1,3,2,4] → profit = (3-1) + (4-2) = 5  

## Tags

Array, Dynamic Programming, Greedy
