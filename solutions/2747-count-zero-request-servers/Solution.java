import java.util.Arrays;

/**
 * LeetCode Problem 2747: Count Zero Request Servers
 * 
 * Sliding window with offline query processing
 * 
 * Problem:
 * Given n servers, logs of requests, and queries,
 * for each query find servers with NO requests in time window [query-x, query].
 * 
 * Key Insight:
 * Sort logs and queries, then use TWO POINTERS sliding window!
 * - Process queries in sorted order
 * - Expand right: include logs in window
 * - Shrink left: remove logs before window
 * - Track active servers with frequency array
 * 
 * Why This Works:
 * 
 * Naive approach (TLE):
 * - For each query: scan all logs
 * - Time: O(queries × logs) = O(10^10)
 * - Too slow!
 * 
 * Optimized approach:
 * - Sort logs once: O(L log L)
 * - Sort queries once: O(Q log Q)
 * - Sliding window: O(L + Q)
 * - Each log processed exactly twice (enter, exit)
 * - Much faster!
 * 
 * Algorithm:
 * 
 * 1. Sort logs by time
 * 2. Create query array with original indices
 * 3. Sort queries by value
 * 4. For each query (in sorted order):
 *    a. Expand right: add logs ≤ query time
 *    b. Shrink left: remove logs < (query - x)
 *    c. Count active servers (freq > 0)
 *    d. Answer = n - active
 * 5. Return answers in original query order
 * 
 * Example: n=3, logs=[[1,3],[2,6],[1,5]], x=5, queries=[10,11]
 * 
 * Step 1: Sort logs
 * logs = [[1,3], [1,5], [2,6]]
 * 
 * Step 2: Create query array
 * q = [[10,0], [11,1]]
 *      (val,idx)
 * 
 * Step 3: Process query 10 (index 0)
 * Window: [5, 10]
 * 
 * Expand right to include all logs ≤ 10:
 *   [1,3]: time 3 ≤ 10, add server 1
 *   [1,5]: time 5 ≤ 10, add server 1 again (freq++)
 *   [2,6]: time 6 ≤ 10, add server 2
 * 
 * freq = [0, 2, 1, 0]
 * active = 2 (servers 1 and 2)
 * 
 * Shrink left to remove logs < 5:
 *   [1,3]: time 3 < 5, remove one occurrence of server 1
 * 
 * freq = [0, 1, 1, 0]
 * active = 2 (servers 1 and 2 still active)
 * 
 * ans[0] = 3 - 2 = 1 ✓
 * 
 * Step 4: Process query 11 (index 1)
 * Window: [6, 11]
 * 
 * Expand right (no new logs):
 *   Already at end
 * 
 * Shrink left to remove logs < 6:
 *   [1,5]: time 5 < 6, remove server 1
 * 
 * freq = [0, 0, 1, 0]
 * active = 1 (only server 2)
 * 
 * ans[1] = 3 - 1 = 2 ✓
 * 
 * Result: [1, 2]
 * 
 * Why Frequency Array?
 * 
 * Server can have multiple requests in window:
 * logs = [[1,3], [1,5]]
 * 
 * When processing:
 * - Add [1,3]: freq[1] = 1, active++
 * - Add [1,5]: freq[1] = 2, active unchanged (already active)
 * - Remove [1,3]: freq[1] = 1, active unchanged (still active)
 * - Remove [1,5]: freq[1] = 0, active-- (now inactive)
 * 
 * Without frequency:
 * - Would double-count server 1
 * - Or incorrectly mark as inactive early
 * 
 * Why Keep Query Indices?
 * 
 * Queries processed in sorted order for efficiency,
 * but answer must be in original order!
 * 
 * queries = [11, 10]
 * Process: 10 first, then 11
 * But return: [ans for 11, ans for 10]
 * 
 * Solution: store (value, originalIndex) pairs
 * 
 * Two Pointers Explanation:
 * 
 * left and right define current window:
 * - right expands: add logs as query time increases
 * - left shrinks: remove old logs as window start increases
 * 
 * Key property: monotonic
 * - Queries processed in increasing order
 * - Once log enters, stays until left catches up
 * - Each log visited exactly twice:
 *   1. When right pointer reaches it (enter)
 *   2. When left pointer passes it (exit)
 * 
 * Time: O(L + Q) for sliding window part!
 * 
 * Edge Cases:
 * 
 * 1. No logs in window:
 *    active = 0
 *    ans = n - 0 = n (all servers inactive)
 * 
 * 2. All servers have requests:
 *    active = n
 *    ans = n - n = 0
 * 
 * 3. Server never in any log:
 *    freq[server] always 0
 *    Never counted as active
 *    Correctly counted as inactive
 * 
 * 4. Single query:
 *    Still works, processes one query
 * 
 * 5. x = 0:
 *    Window is single point [query, query]
 *    Still works correctly
 * 
 * Comparison with Alternatives:
 * 
 * 1. Brute force per query:
 *    Time: O(Q × L)
 *    For each query, scan all logs
 *    Too slow for large inputs
 * 
 * 2. Binary search per query:
 *    Time: O(Q × log L + Q × L)
 *    Binary search for range, then scan
 *    Better but still O(Q × L) for scanning
 * 
 * 3. This sliding window:
 *    Time: O(L log L + Q log Q + L + Q)
 *    Optimal! Each log processed once
 * 
 * Time Complexity: O(L log L + Q log Q)
 *   - Sort logs: O(L log L)
 *   - Sort queries: O(Q log Q)
 *   - Sliding window: O(L + Q)
 *     - Each log enters once, exits once: O(L)
 *     - Each query processed once: O(Q)
 *   - Dominated by sorting
 * 
 * Space Complexity: O(n + Q)
 *   - Query array: O(Q)
 *   - Frequency array: O(n+1)
 *   - Answer array: O(Q)
 *   - Total: O(n + Q)
 *   - Not counting input/output
 */
class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        // Step 1: Sort logs by time for efficient sliding window
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Step 2: Create query array with original indices
        // Need to restore original order after sorting
        int[][] q = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            q[i][0] = queries[i];  // query value
            q[i][1] = i;           // original index
        }

        // Step 3: Sort queries by value (not by index)
        // Process queries in increasing time order
        Arrays.sort(q, (a, b) -> Integer.compare(a[0], b[0]));

        // Answer array (will fill in original query order)
        int[] ans = new int[queries.length];
        
        // Frequency array: count requests per server in current window
        int[] freq = new int[n + 1];  // server IDs from 1 to n

        // Sliding window pointers and active server count
        int left = 0, right = 0, active = 0;

        // Step 4: Process queries in sorted order
        for (int[] query : q) {
            int end = query[0];           // query time
            int start = end - x;          // window start
            // Window: [start, end] inclusive

            // Expand right: include all logs with time ≤ end
            while (right < logs.length && logs[right][1] <= end) {
                int id = logs[right++][0];  // server ID
                
                // Increment frequency
                // If this is first request for this server, increment active count
                if (freq[id]++ == 0) {
                    active++;
                }
            }

            // Shrink left: remove all logs with time < start
            // Note: < not ≤ because interval is inclusive
            while (left < right && logs[left][1] < start) {
                int id = logs[left++][0];  // server ID
                
                // Decrement frequency
                // If frequency becomes 0, server no longer active
                if (--freq[id] == 0) {
                    active--;
                }
            }

            // Calculate answer for this query
            // Inactive servers = total - active
            ans[query[1]] = n - active;
        }

        return ans;
    }
}
