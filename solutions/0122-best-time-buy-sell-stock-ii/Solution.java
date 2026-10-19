/**
 * LeetCode Problem 122: Best Time to Buy and Sell Stock II
 * 
 * Elegant greedy solution - sum all positive daily differences
 * 
 * Problem:
 * Maximize profit with unlimited transactions (but at most 1 stock held at a time).
 * 
 * Key Insight:
 * Since we can transact unlimited times, capture EVERY upward price movement!
 * This is mathematically equivalent to optimal transaction strategy.
 * 
 * Why this works:
 * Any profit from buy at day i and sell at day j (j > i) equals:
 *   prices[j] - prices[i]
 *   = (prices[i+1] - prices[i]) + (prices[i+2] - prices[i+1]) + ... + (prices[j] - prices[j-1])
 *   = sum of all daily differences from i to j
 * 
 * By taking all positive daily differences, we capture all possible profit!
 * 
 * Visual Example: [1, 3, 2, 5]
 * 
 * Method 1 (Optimal transactions):
 *   Buy at 1, sell at 3: profit = 2
 *   Buy at 2, sell at 5: profit = 3
 *   Total = 5
 * 
 * Method 2 (Sum positive differences):
 *   Day 0→1: 3-1 = +2 ✓
 *   Day 1→2: 2-3 = -1 (skip negative)
 *   Day 2→3: 5-2 = +3 ✓
 *   Total = 2 + 3 = 5 ✓
 * 
 * Same result!
 * 
 * Algorithm:
 * For each day starting from day 1:
 *   If today's price > yesterday's price:
 *     profit += today's price - yesterday's price
 * 
 * That's it! Incredibly simple yet optimal.
 * 
 * Comparison with Problem 121 (Single Transaction):
 * - Problem 121: Find one best buy-sell pair (track min, calc max profit)
 * - Problem 122: Capture every upward move (sum positive differences)
 * - Paradox: Problem 122 (Medium) is actually SIMPLER to code!
 * 
 * Example walkthrough: [7, 1, 5, 3, 6, 4]
 * 
 * i=1: 1 < 7  → no profit
 * i=2: 5 > 1  → profit += 5-1 = 4
 * i=3: 3 < 5  → no profit
 * i=4: 6 > 3  → profit += 6-3 = 3
 * i=5: 4 < 6  → no profit
 * 
 * Result: 7
 * Equivalent to: buy@1 sell@5 (profit=4) + buy@3 sell@6 (profit=3)
 * 
 * Edge Cases:
 * 1. Decreasing: [7,6,5,4] → all differences negative → profit = 0
 * 2. Increasing: [1,2,3,4] → all differences positive → profit = (4-1) = 3
 * 3. Single day: [5] → no comparison → profit = 0
 * 
 * Mathematical Proof:
 * - Any multi-day profit = sum of intermediate daily profits
 * - Taking all positive daily differences captures all possible gains
 * - Skipping negative differences is optimal (don't hold during decreases)
 * - Therefore: greedy approach = optimal solution
 * 
 * Time Complexity: O(n)
 *   - Single pass through array
 *   - Constant work per element
 * 
 * Space Complexity: O(1)
 *   - Only one variable: profit
 *   - No extra data structures
 */
class Solution {
    public int maxProfit(int[] prices) {
        int profit = 0;

        // Compare each day with previous day
        for (int i = 1; i < prices.length; i++) {
            // If price increased, capture the profit
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
            // If price decreased or stayed same, do nothing
        }

        return profit;
    }
}
