# 1267. Count Servers that Communicate

**Difficulty:** Medium

## Problem Description

You are given a map of a server center, represented as a `m * n` integer matrix `grid`, where `1` means that on that cell there is a server and `0` means that it is no server.

Two servers are said to communicate if they are on the **same row** or on the **same column**.

Return the number of servers that **communicate with any other server**.

## Examples

**Example 1:**
```
Input: grid = [[1,0],[0,1]]
Output: 0
Explanation: No servers can communicate with others.

Grid visualization:
[1, 0]
[0, 1]

Server at (0,0): row 0 has 1 server, col 0 has 1 server → isolated
Server at (1,1): row 1 has 1 server, col 1 has 1 server → isolated
```

**Example 2:**
```
Input: grid = [[1,0],[1,1]]
Output: 3
Explanation: All three servers can communicate with at least one other server.

Grid visualization:
[1, 0]
[1, 1]

Server at (0,0): row 0 has 1 server, col 0 has 2 servers → communicates ✓
Server at (1,0): row 1 has 2 servers → communicates ✓
Server at (1,1): row 1 has 2 servers → communicates ✓
```

**Example 3:**
```
Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
Output: 4
Explanation: 
Servers at (0,0) and (0,1) communicate (same row 0)
Servers at (1,2) and (2,2) communicate (same col 2)
Server at (3,3) is isolated

Grid visualization:
[1, 1, 0, 0]
[0, 0, 1, 0]
[0, 0, 1, 0]
[0, 0, 0, 1]

Count = 4 servers communicate
```

## Constraints

- `m == grid.length`
- `n == grid[i].length`
- `1 <= m <= 250`
- `1 <= n <= 250`
- `grid[i][j] == 0 or 1`

## Solution Approach

This is a **counting problem with preprocessing!** The key insight: **Pre-count servers in each row and column, then check if any server has neighbors.**

### Efficient Two-Pass Solution

**Naive Approach:**
- For each server, scan its entire row and column
- Time: O(m × n × (m + n)) - Too slow!

**Optimized Approach:**
1. **Pass 1:** Count servers in each row and column
2. **Pass 2:** Count servers with row_count > 1 OR col_count > 1

**Why This Works:**
- A server communicates if it's NOT alone in its row OR column
- Pre-counting avoids repeated scans
- O(m × n) time with two simple passes

### Algorithm Steps

**Preprocessing:**
1. Create `rows[m]` array: count servers per row
2. Create `cols[n]` array: count servers per column
3. Scan grid once to populate both arrays

**Counting:**
1. For each server at (i, j):
   - If `rows[i] > 1` OR `cols[j] > 1`:
     - This server can communicate
     - Increment count

### Visual Example

**Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]**

```
Grid:
     col: 0  1  2  3
row 0:   [1, 1, 0, 0]
row 1:   [0, 0, 1, 0]
row 2:   [0, 0, 1, 0]
row 3:   [0, 0, 0, 1]

Step 1: Count servers per row and column

rows = [2, 1, 1, 1]
       ↑  ↑  ↑  ↑
       r0 r1 r2 r3

cols = [1, 1, 2, 1]
       ↑  ↑  ↑  ↑
       c0 c1 c2 c3

Step 2: Check each server

Server at (0,0):
  rows[0] = 2 > 1 ✓ → can communicate
  count = 1

Server at (0,1):
  rows[0] = 2 > 1 ✓ → can communicate
  count = 2

Server at (1,2):
  rows[1] = 1, but cols[2] = 2 > 1 ✓ → can communicate
  count = 3

Server at (2,2):
  rows[2] = 1, but cols[2] = 2 > 1 ✓ → can communicate
  count = 4

Server at (3,3):
  rows[3] = 1 and cols[3] = 1 → isolated ✗
  count = 4

Final count = 4
```

### Detailed Walkthrough

**Input: grid = [[1,0],[1,1]]**

```
Grid:
[1, 0]
[0, 1]

Wait, this doesn't match Example 2. Let me use Example 2:

Grid:
[1, 0]
[1, 1]

Step 1: Count servers

First pass:
  (0,0): grid[0][0]=1 → rows[0]++, cols[0]++
  (0,1): grid[0][1]=0 → skip
  (1,0): grid[1][0]=1 → rows[1]++, cols[0]++
  (1,1): grid[1][1]=1 → rows[1]++, cols[1]++

rows = [1, 2]
       r0 has 1 server
       r1 has 2 servers

cols = [2, 1]
       c0 has 2 servers
       c1 has 1 server

Step 2: Count communicating servers

count = 0

Check (0,0): grid[0][0]=1
  rows[0]=1, cols[0]=2
  cols[0] > 1 ✓ → communicates
  count = 1

Check (0,1): grid[0][1]=0
  Not a server, skip

Check (1,0): grid[1][0]=1
  rows[1]=2, cols[0]=2
  rows[1] > 1 ✓ → communicates
  count = 2

Check (1,1): grid[1][1]=1
  rows[1]=2, cols[1]=1
  rows[1] > 1 ✓ → communicates
  count = 3

Final: count = 3
```

### Why This Approach Works

**Communication Definition:**
- Server can communicate if:
  - Same row has another server, OR
  - Same column has another server

**Equivalently:**
- Server is NOT isolated
- Isolated = alone in both row AND column
- Not isolated = `(row_count > 1) OR (col_count > 1)`

**Preprocessing Benefits:**
- Count once, use many times
- Avoid O(m + n) scan per server
- Trade space for time

## Complexity Analysis

### Two-Pass with Arrays (Provided):

- **Time Complexity:** O(m × n)
  - First pass: O(m × n) to count servers
  - Second pass: O(m × n) to check each cell
  - Total: O(m × n)

- **Space Complexity:** O(m + n)
  - Row count array: O(m)
  - Column count array: O(n)
  - Total: O(m + n)

## Alternative Approaches

**1. Naive Approach (TLE):**
```java
public int countServers(int[][] grid) {
    int count = 0;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                // Check if has neighbor in row or column
                boolean hasNeighbor = false;
                
                // Check row
                for (int k = 0; k < n; k++) {
                    if (k != j && grid[i][k] == 1) {
                        hasNeighbor = true;
                        break;
                    }
                }
                
                // Check column
                if (!hasNeighbor) {
                    for (int k = 0; k < m; k++) {
                        if (k != i && grid[k][j] == 1) {
                            hasNeighbor = true;
                            break;
                        }
                    }
                }
                
                if (hasNeighbor) count++;
            }
        }
    }
    return count;
}
```
- Time: O(m × n × (m + n))
- Too slow for large grids

**2. Union-Find (Overkill):**
- Connect servers in same row/column
- Count servers in components > 1
- Time: O(m × n × α(n))
- Space: O(m × n)
- Unnecessarily complex

Your two-pass counting is **optimal**!

## Edge Cases Handled

✓ **No servers:** grid = [[0,0],[0,0]] → 0  
✓ **All isolated:** grid = [[1,0],[0,1]] → 0  
✓ **All communicate:** grid = [[1,1],[1,1]] → 4  
✓ **Single server:** grid = [[1]] → 0  
✓ **Single row:** grid = [[1,1,1]] → 3  
✓ **Single column:** grid = [[1],[1],[1]] → 3  

## Pattern Recognition

This problem teaches **Preprocessing for Repeated Queries**:

**Use this pattern when:**
- Need to check same property multiple times
- Property depends on aggregate information
- Can pre-compute counts/sums
- Trade space for time

**Key techniques:**
- Count per row/column
- Single preprocessing pass
- Fast lookup in main pass

**Similar problems:**
- Count battleships
- Number of islands
- Lonely pixel
- Valid sudoku

## Key Insights

✓ **Pre-count servers** - Avoid repeated scans  
✓ **Two-pass algorithm** - Count, then check  
✓ **OR condition** - Row OR column has neighbors  
✓ **O(m × n) time** - Linear in grid size  
✓ **O(m + n) space** - Only count arrays needed  

## Why Preprocessing is Better

**Without preprocessing:**
```
For each server:
  Scan entire row: O(n)
  Scan entire column: O(m)
Total: O(m × n × (m + n))

For 250×250 grid: ~31 million operations
```

**With preprocessing:**
```
Count all servers: O(m × n)
Check all cells: O(m × n)
Total: O(m × n)

For 250×250 grid: ~125 thousand operations
```

**250x speedup!**

## Common Mistakes to Avoid

❌ **Forgetting the OR condition**
```java
// Wrong: need both conditions
if (rows[i] > 1 && cols[j] > 1)

// Right: either is sufficient
if (rows[i] > 1 || cols[j] > 1)
```

❌ **Checking wrong condition**
```java
// Wrong: need > 1, not >= 1
if (rows[i] >= 1)  // always true for servers!

// Right: need at least 2 servers
if (rows[i] > 1)
```

❌ **Counting servers without grid check**
```java
// Wrong: might count empty cells
if (rows[i] > 1 || cols[j] > 1) count++;

// Right: must be a server
if (grid[i][j] == 1 && (rows[i] > 1 || cols[j] > 1)) count++;
```

## Optimization Note

**Can combine passes?**
```java
// No! Need complete counts before checking
// First pass must finish before second pass
```

**Why?**
- Need total count per row/column
- Can't determine if server communicates
  until all servers counted
- Two distinct phases required

## Tags

Array, Counting
