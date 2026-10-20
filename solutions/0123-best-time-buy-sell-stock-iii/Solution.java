/**
 * LeetCode Problem 123: Best Time to Buy and Sell Stock III
 * 
 * State machine DP for at most 2 transactions
 * 
 * Problem:
 * Maximize profit with at most 2 transactions.
 * Cannot hold multiple stocks simultaneously.
 * 
 * Key Insight:
 * Track 4 states representing the maximum profit at each stage:
 * 1. buy1:  After buying first stock
 * 2. sell1: After selling first stock
 * 3. buy2:  After buying second stock (using profit from first)
 * 4. sell2: After selling second stock (final answer)
 * 
 * State Transitions:
 * buy1  = max(buy1,  -price)          // Buy first stock
 * sell1 = max(sell1, buy1 + price)    // Sell first stock
 * buy2  = max(buy2,  sell1 - price)   // Buy second stock
 * sell2 = max(sell2, buy2 + price)    // Sell second stock
 * 
 * Why this works:
 * - Each state represents best profit we can achieve at that stage
 * - buy1: Best profit if we've only bought once
 * - sell1: Best profit if we've completed one transaction
 * - buy2: Best profit if we've bought second stock (can use profit from sell1!)
 * - sell2: Best profit after two complete transactions
 * 
 * Visual Example: [1, 2, 4]
 * 
 * Initial: buy1=-∞, sell1=0, buy2=-∞, sell2=0
 * 
 * Price=1:
 *   buy1  = max(-∞, -1) = -1         // Buy at 1
 *   sell1 = max(0, -1+1) = 0         // No profit yet
 *   buy2  = max(-∞, 0-1) = -1        // Buy second at 1
 *   sell2 = max(0, -1+1) = 0         // No profit yet
 * 
 * Price=2:
 *   buy1  = max(-1, -2) = -1         // Keep buy at 1
 *   sell1 = max(0, -1+2) = 1         // Sell at 2, profit=1
 *   buy2  = max(-1, 1-2) = -1        // Keep previous
 *   sell2 = max(0, -1+2) = 1         // Profit=1
 * 
 * Price=4:
 *   buy1  = max(-1, -4) = -1         // Keep buy at 1
 *   sell1 = max(1, -1+4) = 3         // Sell at 4, profit=3
 *   buy2  = max(-1, 1-4) = -1        // Keep previous
 *   sell2 = max(1, -1+4) = 3         // Final profit=3
 * 
 * Result: 3 (one transaction: buy@1, sell@4)
 * 
 * Why Integer.MIN_VALUE for buy states?
 * - Represents "impossible" or "not yet happened"
 * - First max(MIN_VALUE, -price) will always choose -price
 * - Ensures states are properly initialized on first iteration
 * 
 * Why 0 for sell states?
 * - No profit before any transaction
 * - Valid starting point for max comparisons
 * 
 * Order of updates matters!
 * - buy1 must be updated before sell1 (depends on buy1)
 * - sell1 must be updated before buy2 (depends on sell1)
 * - buy2 must be updated before sell2 (depends on buy2)
 * 
 * Connection to Problem 121 & 122:
 * - 121: Only track buy1 and sell1 (one transaction)
 * - 122: No state tracking needed (unlimited transactions)
 * - 123: Track both transactions as states (2 transactions)
 * 
 * Generalization:
 * This pattern extends to k transactions (Problem 188)
 * - Track buy[1..k] and sell[1..k]
 * - Same update pattern for each transaction
 * 
 * Time Complexity: O(n)
 *   - Single pass through prices array
 *   - 4 constant-time operations per element
 * 
 * Space Complexity: O(1)
 *   - Only 4 state variables
 *   - No arrays or recursion needed
 */
class Solution {
    public int maxProfit(int[] prices) {
        // State 1: Maximum profit after first buy
        int buy1 = Integer.MIN_VALUE;
        
        // State 2: Maximum profit after first sell
        int sell1 = 0;
        
        // State 3: Maximum profit after second buy
        int buy2 = Integer.MIN_VALUE;
        
        // State 4: Maximum profit after second sell (final answer)
        int sell2 = 0;

        for (int price : prices) {
            // Update states in order (later states depend on earlier ones)
            
            // First transaction - buy
            buy1 = Math.max(buy1, -price);
            
            // First transaction - sell
            sell1 = Math.max(sell1, buy1 + price);
            
            // Second transaction - buy (can use profit from first)
            buy2 = Math.max(buy2, sell1 - price);
            
            // Second transaction - sell (final profit)
            sell2 = Math.max(sell2, buy2 + price);
        }

        return sell2;
    }
}
