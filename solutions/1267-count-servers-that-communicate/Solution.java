/**
 * LeetCode Problem 1267: Count Servers that Communicate
 * 
 * Two-pass counting with preprocessing
 * 
 * Problem:
 * Count servers that can communicate with at least one other server.
 * Servers communicate if they're in same row OR same column.
 * 
 * Key Insight:
 * Pre-count servers per row and column!
 * - Server communicates if NOT alone in row OR column
 * - Alone = row_count=1 AND col_count=1
 * - Communicates = row_count>1 OR col_count>1
 * 
 * Algorithm:
 * 
 * Pass 1: Count servers
 * - For each cell with server:
 *   - Increment rows[i]
 *   - Increment cols[j]
 * 
 * Pass 2: Count communicating servers
 * - For each server at (i,j):
 *   - If rows[i] > 1 OR cols[j] > 1:
 *     - This server can communicate
 *     - Increment count
 * 
 * Why This Works:
 * 
 * Communication condition:
 * - Same row has another server, OR
 * - Same column has another server
 * 
 * Equivalently:
 * - rows[i] > 1: at least 2 servers in row i
 * - cols[j] > 1: at least 2 servers in col j
 * 
 * If EITHER is true, server can communicate!
 * 
 * Example: [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
 * 
 * Grid:
 * [1, 1, 0, 0]
 * [0, 0, 1, 0]
 * [0, 0, 1, 0]
 * [0, 0, 0, 1]
 * 
 * Pass 1: Count servers
 * 
 * Scan all cells:
 * (0,0): server → rows[0]++, cols[0]++
 * (0,1): server → rows[0]++, cols[1]++
 * (1,2): server → rows[1]++, cols[2]++
 * (2,2): server → rows[2]++, cols[2]++
 * (3,3): server → rows[3]++, cols[3]++
 * 
 * rows = [2, 1, 1, 1]
 *         ↑  ↑  ↑  ↑
 *         r0 r1 r2 r3
 * 
 * cols = [1, 1, 2, 1]
 *         ↑  ↑  ↑  ↑
 *         c0 c1 c2 c3
 * 
 * Pass 2: Count communicating servers
 * 
 * (0,0): server, rows[0]=2 > 1 ✓ → count++
 * (0,1): server, rows[0]=2 > 1 ✓ → count++
 * (1,2): server, rows[1]=1, cols[2]=2 > 1 ✓ → count++
 * (2,2): server, rows[2]=1, cols[2]=2 > 1 ✓ → count++
 * (3,3): server, rows[3]=1, cols[3]=1 → isolated ✗
 * 
 * count = 4
 * 
 * Why Pre-counting is Crucial:
 * 
 * Without pre-counting:
 * - For each server: scan row (O(n)) + scan column (O(m))
 * - Total: O(m × n × (m + n))
 * - For 250×250: ~31 million operations
 * 
 * With pre-counting:
 * - Count once: O(m × n)
 * - Check all: O(m × n)
 * - Total: O(m × n)
 * - For 250×250: ~125 thousand operations
 * 
 * 250x speedup!
 * 
 * Why Two Separate Passes?
 * 
 * Can't combine because:
 * - Need COMPLETE row/column counts
 * - Can't determine if server communicates
 *   until ALL servers counted
 * - Must finish counting before checking
 * 
 * Example showing why:
 * [1, 0, 0]
 * [0, 0, 1]
 * 
 * When processing (0,0):
 * - Don't know yet if col 0 has more servers
 * - Must scan entire grid first
 * 
 * Understanding the OR Condition:
 * 
 * Server communicates if:
 * - NOT alone in row, OR
 * - NOT alone in column
 * 
 * Examples:
 * 
 * 1. Server with row_count=3, col_count=1:
 *    - Has neighbors in row ✓
 *    - Communicates!
 * 
 * 2. Server with row_count=1, col_count=2:
 *    - Has neighbors in column ✓
 *    - Communicates!
 * 
 * 3. Server with row_count=2, col_count=2:
 *    - Has neighbors in BOTH ✓
 *    - Communicates!
 * 
 * 4. Server with row_count=1, col_count=1:
 *    - Alone in both ✗
 *    - Isolated, doesn't communicate
 * 
 * Edge Cases:
 * 
 * 1. No servers: [[0,0],[0,0]]
 *    rows = [0,0], cols = [0,0]
 *    No servers to check
 *    count = 0
 * 
 * 2. All isolated: [[1,0],[0,1]]
 *    rows = [1,1], cols = [1,1]
 *    Both servers: row=1 and col=1 → isolated
 *    count = 0
 * 
 * 3. All communicate: [[1,1],[1,1]]
 *    rows = [2,2], cols = [2,2]
 *    All servers: row>1 and col>1 → communicate
 *    count = 4
 * 
 * 4. Single server: [[1]]
 *    rows = [1], cols = [1]
 *    Server: row=1 and col=1 → isolated
 *    count = 0
 * 
 * 5. Row of servers: [[1,1,1]]
 *    rows = [3], cols = [1,1,1]
 *    All servers: row=3 > 1 → communicate
 *    count = 3
 * 
 * 6. Column of servers: [[1],[1],[1]]
 *    rows = [1,1,1], cols = [3]
 *    All servers: col=3 > 1 → communicate
 *    count = 3
 * 
 * Common Mistakes:
 * 
 * 1. Wrong condition:
 *    if (rows[i] > 1 && cols[j] > 1)
 *    Should be OR, not AND!
 * 
 * 2. Forgetting server check:
 *    if (rows[i] > 1 || cols[j] > 1) count++;
 *    Must check grid[i][j] == 1 first!
 * 
 * 3. Wrong threshold:
 *    if (rows[i] >= 1)
 *    Should be > 1, not >= 1!
 * 
 * Pattern: Preprocessing for Efficiency
 * 
 * Use when:
 * - Multiple queries on same data
 * - Aggregate information needed
 * - Can pre-compute counts/sums
 * - Trade O(m+n) space for O(mn) time saving
 * 
 * Similar problems:
 * - Count battleships
 * - Lonely pixel
 * - Valid sudoku
 * 
 * Time Complexity: O(m × n)
 *   - First pass: O(m × n) to count
 *   - Second pass: O(m × n) to check
 *   - Total: O(m × n)
 * 
 * Space Complexity: O(m + n)
 *   - Row count array: O(m)
 *   - Column count array: O(n)
 *   - Total: O(m + n)
 */
class Solution {
    public int countServers(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Arrays to count servers per row and column
        int[] rows = new int[m];
        int[] cols = new int[n];

        // Pass 1: Count servers in each row and column
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    rows[i]++;  // Increment count for row i
                    cols[j]++;  // Increment count for column j
                }
            }
        }

        // Pass 2: Count servers that can communicate
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // Check if this cell has a server
                // AND if it can communicate (not isolated)
                if (grid[i][j] == 1 && (rows[i] > 1 || cols[j] > 1)) {
                    // Server at (i,j) can communicate if:
                    // - Its row has more than 1 server, OR
                    // - Its column has more than 1 server
                    count++;
                }
            }
        }

        return count;
    }
}
