/**
 * LeetCode Problem 121: Best Time to Buy and Sell Stock
 * 
 * One-pass greedy/DP solution for maximum profit
 * 
 * Problem:
 * Find maximum profit from buying and selling stock once.
 * Must buy before selling.
 * 
 * Key Insight:
 * Track the minimum price seen so far.
 * At each day, calculate profit if we sell today.
 * Keep track of maximum profit.
 * 
 * Why this works:
 * - To maximize profit on day i, we need to buy at the lowest price before day i
 * - By tracking min as we scan left-to-right, we always have the best buy price
 * - We calculate potential profit at each sell opportunity
 * 
 * Algorithm:
 * 1. Initialize min with first price
 * 2. For each subsequent price:
 *    - If price < min: update min (found better buy day)
 *    - Else: calculate profit if selling today, update max profit
 * 
 * Visual Example: [7, 1, 5, 3, 6, 4]
 * 
 * i=0: min=7, profit=0
 * i=1: 1<7 → min=1, profit=0
 * i=2: 5≥1 → profit=max(0, 5-1)=4
 * i=3: 3≥1 → profit=max(4, 3-1)=4
 * i=4: 6≥1 → profit=max(4, 6-1)=5 ✓
 * i=5: 4≥1 → profit=max(5, 4-1)=5
 * 
 * Result: 5 (buy at 1, sell at 6)
 * 
 * Why if-else structure?
 * - If price < min: This is a new best buy day, can't sell today
 * - Else: Price ≥ min, so selling today is valid, check profit
 * - This prevents buying and selling on same day
 * 
 * Edge Cases:
 * 1. Decreasing prices [7,6,5,4]: min keeps updating, profit stays 0
 * 2. Increasing prices [1,2,3,4]: min=1, profit keeps increasing
 * 3. Single element [5]: loop doesn't run, profit=0
 * 
 * Relation to other algorithms:
 * - Similar to Kadane's algorithm (max subarray sum)
 * - This is essentially finding max of (price[j] - price[i]) where j > i
 * - Greedy approach: always use best buy price available
 * 
 * Time Complexity: O(n)
 *   - Single pass through array
 *   - Constant work per element
 * 
 * Space Complexity: O(1)
 *   - Only two variables: min and profit
 *   - No extra data structures needed
 */
class Solution {
    public int maxProfit(int[] prices) {
        // Track minimum price seen so far (best buy price)
        int min = prices[0];
        
        // Track maximum profit achieved
        int profit = 0;

        // Scan through prices starting from day 1
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < min) {
                // Found a better buy price
                min = prices[i];
            } else {
                // Calculate profit if selling today
                // Update maximum profit if this is better
                profit = Math.max(profit, prices[i] - min);
            }
        }

        return profit;
    }
}
