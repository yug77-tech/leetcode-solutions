/**
 * LeetCode Problem 733: Flood Fill
 * 
 * DFS (Depth-First Search) to fill connected region
 * 
 * Problem:
 * Change color of starting pixel and all connected pixels
 * with same original color to new color.
 * 
 * Connected = share a side (4-directional: up, down, left, right)
 * 
 * Key Insight:
 * Classic DFS/BFS problem!
 * - Start from given pixel
 * - Recursively visit all neighbors with same color
 * - Change color as we visit (marks as visited)
 * 
 * Real-World Analogy:
 * Paint bucket tool in image editors:
 * - Click on pixel
 * - Fills entire connected region
 * - Only spreads to same-colored pixels
 * 
 * Algorithm:
 * 1. Save original color
 * 2. Check edge case: original == new color (avoid infinite loop)
 * 3. Start DFS from starting pixel
 * 4. DFS process:
 *    - Check bounds and if pixel has original color
 *    - Change to new color
 *    - Recursively visit 4 neighbors
 * 
 * Example: [[1,1,1],[1,1,0],[1,0,1]], start=(1,1), color=2
 * 
 * Original:
 * [1, 1, 1]
 * [1, 1, 0]  ← Start at (1,1)
 * [1, 0, 1]
 * 
 * DFS Traversal:
 * 
 * Start at (1,1):
 *   Change 1→2: [1,1,1][1,2,0][1,0,1]
 *   Visit neighbors:
 * 
 * Try down (2,1):
 *   image[2][1]=0 ≠ 1 → stop
 * 
 * Try up (0,1):
 *   image[0][1]=1 → change to 2
 *   [1,2,1][1,2,0][1,0,1]
 *   Visit neighbors of (0,1):
 *   
 *   Try up (-1,1): out of bounds
 *   Try down (1,1): already 2 (visited)
 *   
 *   Try left (0,0):
 *     image[0][0]=1 → change to 2
 *     [2,2,1][1,2,0][1,0,1]
 *     Visit neighbors of (0,0):
 *     
 *     Try down (1,0):
 *       image[1][0]=1 → change to 2
 *       [2,2,1][2,2,0][1,0,1]
 *       Visit neighbors of (1,0):
 *       
 *       Try down (2,0):
 *         image[2][0]=1 → change to 2
 *         [2,2,1][2,2,0][2,0,1]
 *         All neighbors visited/blocked
 *       
 *       All neighbors of (1,0) done
 *     
 *     All neighbors of (0,0) done
 *   
 *   Try right (0,2):
 *     image[0][2]=1 → change to 2
 *     [2,2,2][2,2,0][2,0,1]
 *     All neighbors visited/blocked
 * 
 * Final result:
 * [2, 2, 2]
 * [2, 2, 0]
 * [2, 0, 1]
 * 
 * Note: The 1 at (2,2) is NOT connected, stays 1
 * 
 * Why Check original == color?
 * 
 * CRITICAL edge case prevention!
 * 
 * Example: image=[[2,2,2]], start=(0,0), color=2
 * 
 * Without check:
 * - Change (0,0) to 2 (already 2)
 * - Visit (0,1): color is 2 → matches "original"
 * - Change (0,1) to 2, visit (0,0) again
 * - Infinite loop!
 * 
 * With check:
 * - Original == new color
 * - Nothing to do, return immediately
 * - No infinite loop!
 * 
 * DFS Termination Conditions:
 * 
 * 1. Out of bounds:
 *    r < 0 || r >= m || c < 0 || c >= n
 * 
 * 2. Wrong color:
 *    image[r][c] != original
 *    (Either different color or already visited)
 * 
 * Why image[r][c] != original works for visited check:
 * - When we visit, we change to new color
 * - If we revisit, it's now new color (not original)
 * - So check fails and we don't revisit
 * - No need for separate visited array!
 * 
 * Four Directions:
 * 
 * Up:    (r-1, c)
 * Down:  (r+1, c)
 * Left:  (r, c-1)
 * Right: (r, c+1)
 * 
 * Note: Only 4 directions (orthogonal)
 * Not 8 directions (no diagonals)
 * 
 * Edge Cases:
 * 
 * 1. Original == new color:
 *    Return immediately (avoid infinite loop)
 * 
 * 2. Single pixel:
 *    Just change that one pixel
 * 
 * 3. All same color:
 *    Fill entire image
 * 
 * 4. No connected pixels:
 *    Only starting pixel changes
 * 
 * 5. Multiple disconnected regions:
 *    Only fill connected component containing start
 * 
 * Alternative: BFS (Breadth-First Search)
 * 
 * Uses queue instead of recursion:
 * - Add starting pixel to queue
 * - While queue not empty:
 *   - Poll pixel, change color
 *   - Add valid neighbors to queue
 * 
 * BFS pros:
 * - Iterative (no stack overflow)
 * - Level-by-level traversal
 * 
 * DFS pros:
 * - Simpler code
 * - More natural recursive solution
 * 
 * For this problem: both work equally well!
 * 
 * Common Mistakes:
 * 
 * 1. Not checking original == color:
 *    → Infinite loop
 * 
 * 2. Not saving original color before changing:
 *    → Comparison fails after first change
 * 
 * 3. Wrong bounds check order:
 *    → Array index out of bounds exception
 * 
 * 4. Using 8 directions instead of 4:
 *    → Wrong answer (diagonal connections)
 * 
 * Pattern: Connected Component / Flood Fill
 * 
 * Use when:
 * - Need to visit all connected cells
 * - Spread/propagate through grid
 * - 4-directional or 8-directional connectivity
 * 
 * Similar problems:
 * - Number of Islands (200)
 * - Surrounded Regions (130)
 * - Pacific Atlantic Water Flow (417)
 * - Minesweeper (529)
 * 
 * Time Complexity: O(m × n)
 *   - Visit each pixel at most once
 *   - Each pixel: constant work (change color, check neighbors)
 *   - Total: O(m × n) where m×n is grid size
 * 
 * Space Complexity: O(m × n)
 *   - Recursion stack depth
 *   - Worst case: all pixels connected in snake pattern
 *   - Stack depth = O(m × n)
 *   - Best case: O(1) if single pixel
 */
class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        // Save original color at starting position
        int original = image[sr][sc];

        // Edge case: if original color == new color
        // Nothing to do (and avoids infinite loop)
        if (original == color) {
            return image;
        }

        // Start DFS from starting pixel
        dfs(image, sr, sc, original, color);
        return image;
    }

    /**
     * DFS helper to recursively fill connected pixels
     * 
     * @param image  The image grid
     * @param r      Current row
     * @param c      Current column
     * @param original Original color to match
     * @param color  New color to fill
     */
    private void dfs(int[][] image, int r, int c, int original, int color) {
        int m = image.length;
        int n = image[0].length;

        // Base cases (termination conditions):
        // 1. Out of bounds
        // 2. Not original color (either different or already visited)
        if (r < 0 || c < 0 || r >= m || c >= n || image[r][c] != original) {
            return;
        }

        // Change current pixel to new color
        // This also marks it as visited (no longer matches original)
        image[r][c] = color;

        // Recursively fill 4 neighbors (up, down, left, right)
        // Order doesn't matter for correctness
        dfs(image, r + 1, c, original, color);  // Down
        dfs(image, r - 1, c, original, color);  // Up
        dfs(image, r, c + 1, original, color);  // Right
        dfs(image, r, c - 1, original, color);  // Left
    }
}
