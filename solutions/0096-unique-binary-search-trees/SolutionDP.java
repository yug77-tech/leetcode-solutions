/**
 * LeetCode Problem 96: Unique Binary Search Trees
 * 
 * Dynamic Programming solution (alternative approach)
 * 
 * This approach is more intuitive - it directly models the problem:
 * "How many BSTs can we make with i nodes?"
 * 
 * Recurrence Relation:
 * For i nodes, try each node j as root:
 * - Left subtree has (j-1) nodes → dp[j-1] ways
 * - Right subtree has (i-j) nodes → dp[i-j] ways
 * - Total for root j: dp[j-1] × dp[i-j]
 * 
 * dp[i] = sum of dp[j-1] × dp[i-j] for all j from 1 to i
 * 
 * Base cases:
 * - dp[0] = 1 (empty tree - one way)
 * - dp[1] = 1 (single node - one way)
 * 
 * Example: n = 3
 * 
 * dp[0] = 1, dp[1] = 1
 * 
 * dp[2]: root can be 1 or 2
 *   root=1: left=0 nodes, right=1 node → dp[0]×dp[1] = 1
 *   root=2: left=1 node, right=0 nodes → dp[1]×dp[0] = 1
 *   dp[2] = 2
 * 
 * dp[3]: root can be 1, 2, or 3
 *   root=1: left=0, right=2 → dp[0]×dp[2] = 2
 *   root=2: left=1, right=1 → dp[1]×dp[1] = 1
 *   root=3: left=2, right=0 → dp[2]×dp[0] = 2
 *   dp[3] = 5
 * 
 * Time Complexity: O(n²) - nested loops
 * Space Complexity: O(n) - dp array
 * 
 * Note: Less efficient than the Catalan formula approach,
 * but easier to understand and derive!
 */
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        
        return dp[n];
    }
}
