# 102. Binary Tree Level Order Traversal

**Difficulty:** Medium

## Problem Description

Given the `root` of a binary tree, return the **level order traversal** of its nodes' values. (i.e., from left to right, level by level).

## Examples

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
      3
     / \
    9  20
      /  \
     15   7

Output: [[3],[9,20],[15,7]]
```

**Example 2:**
```
Input: root = [1]
Output: [[1]]
```

**Example 3:**
```
Input: root = []
Output: []
```

## Constraints

- The number of nodes in the tree is in the range `[0, 2000]`.
- `-1000 <= Node.val <= 1000`

## Solution Approach

This is the **textbook BFS (Breadth-First Search) problem!** The key insight: **Use a queue and process level by level.**

### BFS with Queue (Provided Solution)

**The Pattern:**
- Use queue for BFS traversal
- Track current level size
- Process all nodes at current level before moving to next
- Add children to queue for next level

**Why This Works:**
- Queue processes nodes in FIFO order
- All level N nodes enqueued before level N+1
- Capture size to know when level ends
- Each level becomes a separate list

**Visual Example:**

```
Tree:
      3
     / \
    9  20
      /  \
     15   7

Level-by-level processing:

Initial:
  Queue: [3]
  ans: []

Level 0:
  size = 1
  Process: 3
    level = [3]
    Add children: 9, 20
  Queue: [9, 20]
  ans: [[3]]

Level 1:
  size = 2
  Process: 9
    level = [9]
    Add children: none
  Process: 20
    level = [9, 20]
    Add children: 15, 7
  Queue: [15, 7]
  ans: [[3], [9,20]]

Level 2:
  size = 2
  Process: 15
    level = [15]
    Add children: none
  Process: 7
    level = [15, 7]
    Add children: none
  Queue: []
  ans: [[3], [9,20], [15,7]]

Queue empty, done!
```

### Algorithm Steps

```java
1. Handle edge case: if root is null, return empty list
2. Create queue and add root
3. While queue is not empty:
   a. Get current level size (queue.size())
   b. Create list for this level
   c. For each node in current level:
      - Poll from queue
      - Add value to level list
      - Add left child to queue (if exists)
      - Add right child to queue (if exists)
   d. Add level list to result
4. Return result
```

### Why Track Size?

**Without size tracking:**
```java
// Wrong: processes all nodes at once
while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    level.add(node.val);
    // Can't tell when level ends!
}
```

**With size tracking:**
```java
// Right: knows exactly how many nodes in current level
int size = queue.size();  // Capture at start!
for (int i = 0; i < size; i++) {
    // Process exactly 'size' nodes
    // These are all at current level
}
```

**Example:**
```
Queue: [9, 20]
size = 2

Process exactly 2 nodes (9 and 20)
Even though we add 15 and 7 during processing,
they don't get processed until next iteration!

Queue after: [15, 7] (next level)
```

### Key Insight: Queue Size

```
At start of while loop:
  Queue contains ALL and ONLY nodes of current level
  queue.size() = number of nodes at this level

During for loop:
  We add children (next level nodes)
  But we only process current level (fixed by size)

After for loop:
  Queue contains ALL and ONLY nodes of next level
```

## Complexity Analysis

### BFS with Queue (Provided):

- **Time Complexity:** O(n)
  - Visit each node exactly once
  - Each node added to queue once
  - Each node polled from queue once
  - O(n) total operations

- **Space Complexity:** O(w)
  - w = maximum width of tree
  - Queue holds at most one level
  - Worst case: complete tree, bottom level = n/2 nodes
  - So O(n) worst case
  - Not counting output space

## Alternative Approaches

**1. Recursive DFS with Level Tracking:**
```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    dfs(root, 0, result);
    return result;
}

private void dfs(TreeNode node, int level, List<List<Integer>> result) {
    if (node == null) return;
    
    // Create new level list if needed
    if (level == result.size()) {
        result.add(new ArrayList<>());
    }
    
    // Add to appropriate level
    result.get(level).add(node.val);
    
    // Process children
    dfs(node.left, level + 1, result);
    dfs(node.right, level + 1, result);
}
```
- Time: O(n)
- Space: O(h) recursion stack
- Less intuitive than BFS
- DFS for level-order feels unnatural

**2. Two Queues:**
```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> current = new LinkedList<>();
    Queue<TreeNode> next = new LinkedList<>();
    current.offer(root);
    
    while (!current.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        
        while (!current.isEmpty()) {
            TreeNode node = current.poll();
            level.add(node.val);
            
            if (node.left != null) next.offer(node.left);
            if (node.right != null) next.offer(node.right);
        }
        
        result.add(level);
        Queue<TreeNode> temp = current;
        current = next;
        next = temp;
    }
    
    return result;
}
```
- Time: O(n)
- Space: O(w)
- Uses two queues instead of size tracking
- More intuitive but more memory

Your single-queue with size tracking is **the standard approach**!

## Edge Cases Handled

✓ **Empty tree:** null → []  
✓ **Single node:** [1] → [[1]]  
✓ **Left skewed:** Only left children  
✓ **Right skewed:** Only right children  
✓ **Complete tree:** All levels full except maybe last  
✓ **Single child nodes:** Mixed left/right  

## Pattern Recognition

This problem teaches **Level-Order BFS**:

**Use this pattern when:**
- Need to process tree level by level
- Group nodes by depth/level
- Need specific order (left to right per level)
- Want shortest path in tree (unweighted)

**Key techniques:**
- Queue for BFS
- Size capture for level separation
- FIFO order maintains left-to-right
- Children added for next level

**Similar problems:**
- Binary Tree Zigzag Level Order Traversal (103)
- Binary Tree Level Order Traversal II (107) - bottom-up
- Binary Tree Right Side View (199)
- Average of Levels in Binary Tree (637)
- Maximum Depth of Binary Tree (BFS approach)
- Minimum Depth of Binary Tree
- Symmetric Tree (level comparison)

## Key Insights

✓ **BFS with queue** - Natural level-order traversal  
✓ **Size capture** - Separates levels cleanly  
✓ **FIFO order** - Maintains left-to-right  
✓ **Level list** - Groups nodes at same depth  
✓ **Early null check** - Handles empty tree  
✓ **Child addition** - Builds next level  

## BFS vs DFS

**BFS (This Problem):**
```
Level by level:
      3
     / \
    9  20
   /  \
  15   7

Order: 3 → 9 → 20 → 15 → 7
Natural for level-order!
```

**DFS (Pre-order):**
```
Depth first:
      3
     / \
    9  20
   /  \
  15   7

Order: 3 → 9 → 15 → 20 → 7
Need extra tracking for levels
```

**For level-order, BFS is natural choice!**

## Queue Operations

**offer() vs add():**
- Both add to queue
- offer() returns false on failure
- add() throws exception on failure
- offer() is preferred

**poll() vs remove():**
- Both remove from queue
- poll() returns null if empty
- remove() throws exception if empty
- poll() is safer

**Your code uses offer/poll - correct choice!**

## Why Process All Level at Once?

**Alternative: Process one node at a time**
```java
// How to know when level ends?
while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    // Add to which level?
    // Can't tell without extra tracking!
}
```

**Solution: Capture size**
```java
// Process exactly current level
int size = queue.size();
for (int i = 0; i < size; i++) {
    // All nodes in this loop are at same level
}
```

**This is the KEY technique!**

## Common Mistakes to Avoid

❌ **Not capturing size at start**
```java
// Wrong: size changes during iteration
for (int i = 0; i < queue.size(); i++) {
    TreeNode node = queue.poll();
    queue.offer(node.left);  // Size increased!
    // Loop condition is wrong now
}

// Right: capture size first
int size = queue.size();
for (int i = 0; i < size; i++) {
    // Fixed iteration count
}
```

❌ **Adding null children to queue**
```java
// Wrong: adds nulls
queue.offer(node.left);   // Might be null
queue.offer(node.right);  // Might be null

// Right: check first
if (node.left != null) queue.offer(node.left);
if (node.right != null) queue.offer(node.right);
```

❌ **Not handling empty tree**
```java
// Wrong: NPE if root is null
queue.offer(root);  // If null, adds null to queue

// Right: check first
if (root == null) return ans;
queue.offer(root);
```

## Detailed Example Walkthrough

**Input:**
```
      3
     / \
    9  20
      /  \
     15   7
```

**Execution:**

```
Initial:
  ans = []
  queue = []
  root = 3 (not null)

Add root to queue:
  queue = [3]

─────────────────────
Iteration 1:

queue = [3]
size = 1
level = []

For i = 0:
  node = queue.poll() = 3
  level.add(3) → level = [3]
  node.left = 9 (not null)
    queue.offer(9) → queue = [9]
  node.right = 20 (not null)
    queue.offer(20) → queue = [9, 20]

ans.add([3]) → ans = [[3]]

─────────────────────
Iteration 2:

queue = [9, 20]
size = 2
level = []

For i = 0:
  node = queue.poll() = 9
  level.add(9) → level = [9]
  node.left = null (skip)
  node.right = null (skip)

For i = 1:
  node = queue.poll() = 20
  level.add(20) → level = [9, 20]
  node.left = 15 (not null)
    queue.offer(15) → queue = [15]
  node.right = 7 (not null)
    queue.offer(7) → queue = [15, 7]

ans.add([9, 20]) → ans = [[3], [9,20]]

─────────────────────
Iteration 3:

queue = [15, 7]
size = 2
level = []

For i = 0:
  node = queue.poll() = 15
  level.add(15) → level = [15]
  node.left = null (skip)
  node.right = null (skip)

For i = 1:
  node = queue.poll() = 7
  level.add(7) → level = [15, 7]
  node.left = null (skip)
  node.right = null (skip)

ans.add([15, 7]) → ans = [[3], [9,20], [15,7]]

─────────────────────

queue = []
queue.isEmpty() = true
Exit loop

Return ans = [[3], [9,20], [15,7]]
```

## Why This Pattern Works

**Queue Property:**
- FIFO (First In, First Out)
- Nodes added left-to-right
- Processed in same order

**Level Separation:**
- All level N nodes in queue before any level N+1 node
- Size capture freezes the boundary
- Process exactly current level

**Left-to-Right:**
- Add left child before right child
- Queue maintains this order naturally

**Result:**
- Each level processed as a group
- Left-to-right order maintained
- Clean separation between levels

Perfect for level-order traversal!

## Interview Tips

**If asked "Why BFS not DFS?"**
- BFS natural for level-order
- Queue automatically groups by level
- DFS needs extra level tracking
- BFS is cleaner and more intuitive

**If asked "Why capture size?"**
- Need to know when level ends
- Size at loop start = exact count for level
- Prevents processing next level prematurely
- This is the key technique!

**If asked "Space complexity?"**
- O(w) where w is max width
- Complete tree: bottom level has n/2 nodes
- Worst case: O(n)
- Better than O(h) for balanced tree

## Tags

Tree, Breadth-First Search, Binary Tree
