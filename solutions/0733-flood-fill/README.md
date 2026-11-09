# 733. Flood Fill

**Difficulty:** Easy

## Problem Description

You are given an image represented by an `m x n` grid of integers `image`, where `image[i][j]` represents the pixel value of the image. You are also given three integers `sr`, `sc`, and `color`. Your task is to perform a **flood fill** on the image starting from the pixel `image[sr][sc]`.

To perform a flood fill:

1. Begin with the starting pixel and change its color to `color`.
2. Perform the same process for each pixel that is **directly adjacent** (pixels that share a side with the original pixel, either horizontally or vertically) and shares the **same color** as the starting pixel.
3. Keep repeating this process by checking neighboring pixels of the updated pixels and modifying their color if it matches the original color of the starting pixel.
4. The process stops when there are no more adjacent pixels of the original color to update.

Return the modified image after performing the flood fill.

## Examples

**Example 1:**
```
Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
Output: [[2,2,2],[2,2,0],[2,0,1]]

Explanation:
From the center (1,1), the flood fill spreads to all connected 1's:

Original:        After flood fill:
[1, 1, 1]        [2, 2, 2]
[1, 1, 0]   →    [2, 2, 0]
[1, 0, 1]        [2, 0, 1]

The 1 in bottom-right corner is not connected, so it stays 1.
```

**Example 2:**
```
Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, color = 0
Output: [[0,0,0],[0,0,0]]

Explanation:
The starting pixel is already the target color, so no change is made.
```

## Constraints

- `m == image.length`
- `n == image[i].length`
- `1 <= m, n <= 50`
- `0 <= image[i][j], color < 2^16`
- `0 <= sr < m`
- `0 <= sc < n`

## Solution Approach

This is a **classic DFS (Depth-First Search) problem!** The key insight: **Recursively spread to all connected pixels with the same original color.**

### DFS (Depth-First Search) Approach

**What is Flood Fill?**
- Like paint bucket tool in image editors
- Fills connected region with new color
- Only spreads to pixels with same original color

**Algorithm:**
1. Save original color at starting position
2. **Edge case:** If original == new color, return (avoid infinite loop!)
3. Use DFS to visit all connected pixels:
   - Change current pixel to new color
   - Recursively visit 4 neighbors (up, down, left, right)
   - Only visit if:
     - Within bounds
     - Has original color (not yet visited)

### Visual Example

**Input: image = [[1,1,1],[1,1,0],[1,0,1]], start = (1,1), color = 2**

```
Original grid:
  0 1 2
0[1,1,1]
1[1,1,0]  ← Start at (1,1)
2[1,0,1]

Step 1: Change (1,1) from 1 to 2
[1,1,1]
[1,2,0]
[1,0,1]

Step 2: DFS explores neighbors
Try (0,1) - up:
[1,2,1]  ← Changed (0,1)
[1,2,0]
[1,0,1]

Step 3: From (0,1), explore neighbors
Try (0,0) - left:
[2,2,1]  ← Changed (0,0)
[1,2,0]
[1,0,1]

Step 4: From (0,0), explore neighbors
All neighbors either out of bounds or wrong color

Step 5: Back to (0,1), try (0,2) - right:
[2,2,2]  ← Changed (0,2)
[1,2,0]
[1,0,1]

Step 6: Continue DFS from (1,1)
Try (1,0) - left:
[2,2,2]
[2,2,0]  ← Changed (1,0)
[1,0,1]

Step 7: From (1,0), try (2,0) - down:
[2,2,2]
[2,2,0]
[2,0,1]  ← Changed (2,0)

All connected 1's are now 2's!

Final:
[2,2,2]
[2,2,0]
[2,0,1]
```

### Detailed Walkthrough

**Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr=1, sc=1, color=2**

```
Call: dfs(1, 1)
  image[1][1] = 1 (original) → change to 2
  
  Try down (2,1):
    image[2][1] = 0 ≠ 1 → return
  
  Try up (0,1):
    image[0][1] = 1 → change to 2
    Call: dfs(0, 1)
      
      Try down (1,1):
        image[1][1] = 2 ≠ 1 → return (already visited)
      
      Try up (-1,1):
        Out of bounds → return
      
      Try right (0,2):
        image[0][2] = 1 → change to 2
        Call: dfs(0, 2)
          Try down (1,2):
            image[1][2] = 0 ≠ 1 → return
          Try up (-1,2):
            Out of bounds → return
          Try right (0,3):
            Out of bounds → return
          Try left (0,1):
            image[0][1] = 2 ≠ 1 → return (visited)
        Return
      
      Try left (0,0):
        image[0][0] = 1 → change to 2
        Call: dfs(0, 0)
          Try down (1,0):
            image[1][0] = 1 → change to 2
            Call: dfs(1, 0)
              Try down (2,0):
                image[2][0] = 1 → change to 2
                Call: dfs(2, 0)
                  Try down (3,0):
                    Out of bounds → return
                  Try up (1,0):
                    image[1][0] = 2 ≠ 1 → return (visited)
                  Try right (2,1):
                    image[2][1] = 0 ≠ 1 → return
                  Try left (2,-1):
                    Out of bounds → return
                Return
              Try up (0,0):
                image[0][0] = 2 ≠ 1 → return (visited)
              Try right (1,1):
                image[1][1] = 2 ≠ 1 → return (visited)
              Try left (1,-1):
                Out of bounds → return
            Return
          Try up (-1,0):
            Out of bounds → return
          Try right (0,1):
            image[0][1] = 2 ≠ 1 → return (visited)
          Try left (0,-1):
            Out of bounds → return
        Return
    Return
  
  Try right (1,2):
    image[1][2] = 0 ≠ 1 → return
  
  Try left (1,0):
    image[1][0] = 2 ≠ 1 → return (already visited)

Done!
```

### Why Check Original == Color?

**Critical edge case:**

```java
if (original == color) return image;
```

**Without this check:**
```
image = [[2,2,2]], start = (0,0), color = 2

Without check:
1. Change (0,0) to 2 (already 2)
2. DFS to (0,1): color is 2 → change to 2
3. DFS to (0,2): color is 2 → change to 2
4. Infinite loop! Keep revisiting same cells

With check:
- Original color == new color
- Nothing to do, return immediately
```

## Complexity Analysis

### DFS Approach (Provided):

- **Time Complexity:** O(m × n)
  - Visit each pixel at most once
  - Each pixel checked and possibly colored
  - Total: O(m × n) pixels

- **Space Complexity:** O(m × n)
  - Recursion stack depth
  - Worst case: all pixels connected (snake pattern)
  - Stack depth = O(m × n)

## Alternative Approaches

**1. BFS (Breadth-First Search):**
```java
public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    int original = image[sr][sc];
    if (original == color) return image;
    
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{sr, sc});
    image[sr][sc] = color;
    
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int r = curr[0], c = curr[1];
        
        for (int[] dir : dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            
            if (nr >= 0 && nr < image.length && 
                nc >= 0 && nc < image[0].length && 
                image[nr][nc] == original) {
                image[nr][nc] = color;
                queue.offer(new int[]{nr, nc});
            }
        }
    }
    
    return image;
}
```
- Time: O(m × n)
- Space: O(min(m, n)) for queue
- Iterative, no stack overflow risk

**2. DFS with Explicit Stack:**
```java
public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    int original = image[sr][sc];
    if (original == color) return image;
    
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{sr, sc});
    
    while (!stack.isEmpty()) {
        int[] curr = stack.pop();
        int r = curr[0], c = curr[1];
        
        if (r < 0 || r >= image.length || 
            c < 0 || c >= image[0].length || 
            image[r][c] != original) continue;
        
        image[r][c] = color;
        
        stack.push(new int[]{r+1, c});
        stack.push(new int[]{r-1, c});
        stack.push(new int[]{r, c+1});
        stack.push(new int[]{r, c-1});
    }
    
    return image;
}
```
- Time: O(m × n)
- Space: O(m × n) for stack
- Iterative DFS

Your recursive DFS is **most elegant and intuitive**!

## Edge Cases Handled

✓ **Already target color:** Return immediately  
✓ **Single pixel:** Works correctly  
✓ **All same color:** Fills entire image  
✓ **No connected pixels:** Only changes starting pixel  
✓ **Disconnected regions:** Only fills connected component  

## Pattern Recognition

This problem teaches **Flood Fill / Connected Component**:

**Use this pattern when:**
- Need to visit all connected cells
- Spread/propagate through grid
- 4-directional or 8-directional connectivity
- Connected component problems

**Key techniques:**
- DFS or BFS traversal
- Track visited with color change
- Check bounds and conditions
- Handle edge case where original == target

**Similar problems:**
- Number of Islands
- Surrounded Regions
- Pacific Atlantic Water Flow
- Coloring Borders
- Minesweeper

## Key Insights

✓ **DFS recursion** - Elegant for graph traversal  
✓ **In-place marking** - Use color change to track visited  
✓ **4-directional** - Up, down, left, right neighbors  
✓ **Edge case check** - Original == color → infinite loop  
✓ **Bounds checking** - Stay within grid  
✓ **Original color saved** - Needed for comparison  

## DFS vs BFS

**DFS (Your solution):**
- Simpler code
- Goes deep first
- Uses recursion stack
- More natural for tree-like problems

**BFS:**
- Level-by-level traversal
- Uses explicit queue
- Better for shortest path
- More control over iteration

**For flood fill: both work equally well!**

## Common Mistakes to Avoid

❌ **Forgetting edge case check**
```java
// Wrong: infinite loop if original == color
public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    dfs(image, sr, sc, image[sr][sc], color);
    return image;
}
```

❌ **Not saving original color**
```java
// Wrong: loses original color after first change
if (image[r][c] == image[sr][sc])  // sr,sc already changed!
```

❌ **Wrong bounds check**
```java
// Wrong: should check before accessing
if (image[r][c] != original) return;
if (r < 0 || r >= m || c < 0 || c >= n) return;

// Right: check bounds first
if (r < 0 || r >= m || c < 0 || c >= n || image[r][c] != original) return;
```

## Tags

Array, Depth-First Search, Breadth-First Search, Matrix
