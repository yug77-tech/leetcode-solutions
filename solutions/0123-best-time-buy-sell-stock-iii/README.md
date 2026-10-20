# 123. Best Time to Buy and Sell Stock III

**Difficulty:** Hard

## Problem Description

You are given an array `prices` where `prices[i]` is the price of a given stock on the `ith` day.

Find the maximum profit you can achieve. You may complete **at most two transactions**.

**Note:** You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

## Examples

**Example 1:**
```
Input: prices = [3,3,5,0,0,3,1,4]
Output: 6
Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
Total profit = 3 + 3 = 6.
```

**Example 2:**
```
Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging
multiple transactions at the same time. You must sell before buying again.
```

**Example 3:**
```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.
```

## Constraints

- `1 <= prices.length <= 10^5`
- `0 <= prices[i] <= 10^5`

## Solution Approach

This is the hardest problem in the stock series! We need to track **states** for two transactions.

### State Machine Dynamic Programming

**Key Idea:**
Track four states at each day:
1. **buy1**: Maximum profit after first buy
2. **sell1**: Maximum profit after first sell
3. **buy2**: Maximum profit after second buy
4. **sell2**: Maximum profit after second sell

**State Transitions:**
```
buy1  = max(buy1,  -price)           // Buy first stock
sell1 = max(sell1, buy1 + price)     // Sell first stock
buy2  = max(buy2,  sell1 - price)    // Buy second stock (using profit from first)
sell2 = max(sell2, buy2 + price)     // Sell second stock
```

### Understanding the States

**buy1 = max(buy1, -price)**
- Either keep previous buy1 state
- Or buy at current price (spending money, so negative)
- Start with MIN_VALUE (impossible state initially)

**sell1 = max(sell1, buy1 + price)**
- Either keep previous sell1 state
- Or sell at current price (getting back money)
- Start with 0 (no profit yet)

**buy2 = max(buy2, sell1 - price)**
- Either keep previous buy2 state
- Or buy second stock using profit from sell1
- Key: We can use profit from first transaction!

**sell2 = max(sell2, buy2 + price)**
- Either keep previous sell2 state
- Or sell second stock
- This is our final answer!

### Visual Example

**Input: prices = [3, 3, 5, 0, 0, 3, 1, 4]**

```
Day | Price | buy1  | sell1 | buy2  | sell2
----|-------|-------|-------|-------|-------
-   | -     | -∞    | 0     | -∞    | 0
0   | 3     | -3    | 0     | -3    | 0
1   | 3     | -3    | 0     | -3    | 0
2   | 5     | -3    | 2     | -3    | 2
3   | 0     | 0     | 2     | 2     | 2
4   | 0     | 0     | 2     | 2     | 2
5   | 3     | 0     | 3     | 2     | 5
6   | 1     | 0     | 3     | 2     | 5
7   | 4     | 0     | 4     | 2     | 6 ✓

Final answer: 6
```

### Step-by-Step Walkthrough

**Example: [1, 2, 4]**

```
Initial state:
  buy1 = -∞, sell1 = 0, buy2 = -∞, sell2 = 0

Day 0 (price = 1):
  buy1  = max(-∞, -1) = -1           // Buy first at 1
  sell1 = max(0, -1+1) = 0           // Sell first at 1 (no profit)
  buy2  = max(-∞, 0-1) = -1          // Buy second at 1
  sell2 = max(0, -1+1) = 0           // Sell second at 1 (no profit)

Day 1 (price = 2):
  buy1  = max(-1, -2) = -1           // Keep first buy at 1
  sell1 = max(0, -1+2) = 1           // Sell first at 2 (profit = 1)
  buy2  = max(-1, 1-2) = -1          // Keep buy2
  sell2 = max(0, -1+2) = 1           // Sell second at 2 (profit = 1)

Day 2 (price = 4):
  buy1  = max(-1, -4) = -1           // Keep first buy at 1
  sell1 = max(1, -1+4) = 3           // Sell first at 4 (profit = 3)
  buy2  = max(-1, 1-4) = -1          // Keep buy2
  sell2 = max(1, -1+4) = 3           // Sell second at 4 (profit = 3)

Result: 3 (one transaction: buy at 1, sell at 4)
```

### Why This Works

✓ **State machine** - Tracks all possible states  
✓ **Optimal substructure** - Each state builds on previous  
✓ **No arrays needed** - Constant space DP  
✓ **One pass** - O(n) time  
✓ **Handles 0, 1, or 2 transactions** - Automatically optimal  

## Complexity Analysis

### State Machine DP (Provided):

- **Time Complexity:** O(n)
  - Single pass through array
  - Constant updates per element (4 operations)

- **Space Complexity:** O(1)
  - Only 4 variables (buy1, sell1, buy2, sell2)
  - No arrays or extra data structures

### Alternative Approach (Less Optimal)

**Split Array DP:**
```java
// For each split point i:
//   maxProfit = maxProfit1[0...i] + maxProfit2[i+1...n-1]
// Time: O(n), Space: O(n)
```
Your solution is more elegant!

## Comparison with Stock Problems

| Problem | Transactions | Strategy | Complexity |
|---------|-------------|----------|------------|
| **121** | 1 | Track min, calc max profit | O(n), O(1) |
| **122** | Unlimited | Sum positive differences | O(n), O(1) |
| **123** | 2 | State machine DP | O(n), O(1) |

Problem 123 is the hardest but has the most elegant solution!

## Edge Cases Handled

✓ **No profit possible:** [7,6,4,3,1] → 0  
✓ **One transaction better:** [1,2,3,4,5] → 4 (uses only 1 transaction)  
✓ **Two transactions:** [3,3,5,0,0,3,1,4] → 6  
✓ **Single day:** [5] → 0  
✓ **All same:** [3,3,3,3] → 0  

## Initialization Explained

**Why buy1 = Integer.MIN_VALUE?**
- Represents "haven't bought yet"
- First max(MIN_VALUE, -price) will always choose -price
- Ensures first day always updates buy1

**Why sell1 = 0?**
- No profit before any transaction
- max(0, buy1+price) starts from 0

**Why buy2 = Integer.MIN_VALUE?**
- Same reason as buy1
- Ensures proper initialization

**Why sell2 = 0?**
- Same reason as sell1
- This is our final answer

## Tags

Array, Dynamic Programming, State Machine
