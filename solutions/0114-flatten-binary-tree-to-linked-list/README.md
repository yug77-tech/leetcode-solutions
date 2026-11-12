# 114. Flatten Binary Tree to Linked List

**Difficulty:** Medium

## Problem Description

Given the `root` of a binary tree, flatten the tree into a "linked list":

- The "linked list" should use the same `TreeNode` class where the **right** child pointer points to the next node in the list and the **left** child pointer is always `null`.
- The "linked list" should be in the same order as a **pre-order traversal** of the binary tree.

## Examples

**Example 1:**
```
Input: root = [1,2,5,3,4,null,6]
     1
    / \
   2   5
  / \   \
 3   4   6

Output: [1,null,2,null,3,null,4,null,5,null,6]
     1
      \
       2
        \
         3
          \
           4
            \
             5
              \
               6
```

**Example 2:**
```
Input: root = []
Output: []
```

**Example 3:**
```
Input: root = [0]
Output: [0]
```

## Constraints

- The number of nodes in the tree is in the range `[0, 2000]`.
- `-100 <= Node.val <= 100`

**Follow-up:** Can you flatten the tree in-place (with `O(1)` extra space)?

## Solution Approach

This problem has **multiple solutions**, but the provided one is the **Morris Traversal approach** - achieving O(1) space with an elegant iterative algorithm!

### Morris Traversal (O(1) Space) - Provided Solution

**The Key Insight:**
For each node with a left subtree:
1. Find the rightmost node of left subtree
2. Connect it to current node's right subtree
3. Move left subtree to right
4. Set left to null
5. Continue with right

**Why This Works:**
- Pre-order: Root → Left → Right
- We're threading the tree so left subtree comes before right
- Rightmost of left connects to original right
- This maintains pre-order!

**Visual Example:**

```
Original tree:
     1
    / \
   2   5
  / \   \
 3   4   6

Pre-order: 1 → 2 → 3 → 4 → 5 → 6

Step 1: At node 1
  Left subtree exists
  Find rightmost of left (node 4)
  
     1
    / \
   2   5
  / \   \
 3   4   6
      ↓ (rightmost of left)

Step 2: Connect rightmost to original right
     1
    / 
   2   
  / \   
 3   4 → 5
          \
           6

Step 3: Move left to right, null left
     1
      \
       2
      / \
     3   4 → 5
              \
               6
       
Step 4: Move to node 2 (curr.right)
  Left subtree exists
  Find rightmost (node 4)
  Connect to original right (already done!)
  
     1
      \
       2
        \
         3
          \
           4 → 5
                \
                 6

Step 5: Continue...
  Node 3: no left, move right
  Node 4: no left, move right
  Node 5: no left, move right
  Node 6: no left, done!

Final:
     1
      \
       2
        \
         3
          \
           4
            \
             5
              \
               6
```

### Algorithm Steps

```java
1. Start with current = root
2. While current != null:
   a. If current has left child:
      - Find rightmost node of left subtree
      - Connect rightmost.right to current.right
      - Move current.left to current.right
      - Set current.left to null
   b. Move to current = current.right
```

### Why Find Rightmost?

**Pre-order traversal:** Root → Left subtree → Right subtree

When we process a node:
- Left subtree should come next
- But after left subtree finishes, need right subtree
- Rightmost of left is the LAST node visited in left subtree
- So connect it to right subtree!

**Example:**
```
     1
    / \
   2   5
  / \
 3   4

Pre-order: 1 → 2 → 3 → 4 → 5

Left subtree of 1: 2,3,4
Rightmost: 4
After processing left, should go to 5
So: 4.right = 5
```

## Complexity Analysis

### Morris Traversal (Provided):

- **Time Complexity:** O(n)
  - Each node visited at most twice
  - Once as current
  - Once when finding rightmost
  - Still O(n) overall

- **Space Complexity:** O(1)
  - No recursion stack
  - No extra data structures
  - Only a few pointers
  - **Achieves the follow-up requirement!**

## Alternative Approaches

**1. Recursive Pre-order:**
```java
private TreeNode prev = null;

public void flatten(TreeNode root) {
    if (root == null) return;
    
    TreeNode right = root.right;
    TreeNode left = root.left;
    
    if (prev != null) {
        prev.right = root;
        prev.left = null;
    }
    
    prev = root;
    
    flatten(left);
    flatten(right);
}
```
- Time: O(n)
- Space: O(h) - recursion stack
- Simpler but uses stack space

**2. Stack-based Iterative:**
```java
public void flatten(TreeNode root) {
    if (root == null) return;
    
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    
    while (!stack.isEmpty()) {
        TreeNode curr = stack.pop();
        
        if (curr.right != null) stack.push(curr.right);
        if (curr.left != null) stack.push(curr.left);
        
        if (!stack.isEmpty()) {
            curr.right = stack.peek();
        }
        curr.left = null;
    }
}
```
- Time: O(n)
- Space: O(h) - stack space
- More intuitive pre-order

**3. Reverse Post-order (Elegant Recursive):**
```java
private TreeNode prev = null;

public void flatten(TreeNode root) {
    if (root == null) return;
    
    flatten(root.right);  // Process right first
    flatten(root.left);   // Then left
    
    root.right = prev;
    root.left = null;
    prev = root;
}
```
- Time: O(n)
- Space: O(h)
- Clever: processes in reverse pre-order
- Builds from back to front

Your Morris Traversal is **the most space-efficient** - O(1) space!

## Edge Cases Handled

✓ **Empty tree:** null → null  
✓ **Single node:** [1] → [1]  
✓ **Only left children:** Converts correctly  
✓ **Only right children:** Already flat  
✓ **Full tree:** Flattens properly  
✓ **Skewed tree:** Handles both left and right skewed  

## Pattern Recognition

This problem teaches **Morris Traversal**:

**Use this pattern when:**
- Need tree traversal in O(1) space
- In-place tree modification
- Can temporarily modify tree structure

**Key techniques:**
- Threading: temporary links between nodes
- Rightmost predecessor finding
- In-place rewiring

**Similar problems:**
- Morris Pre-order Traversal
- Morris In-order Traversal
- Threaded Binary Trees
- Binary Tree to Doubly Linked List

## Key Insights

✓ **O(1) space** - No recursion or stack  
✓ **Pre-order property** - Maintained by threading  
✓ **In-place** - Modifies original tree  
✓ **Rightmost connection** - Links subtrees correctly  
✓ **Left to right movement** - Simulates pre-order  
✓ **Each node processed twice** - Still O(n) time  

## Pre-order vs In-order vs Post-order

**Pre-order (this problem):**
```
     1
    / \
   2   3
Pre-order: 1 → 2 → 3
Flatten: 1 → 2 → 3 (right chain)
```

**In-order:**
```
     1
    / \
   2   3
In-order: 2 → 1 → 3
```

**Post-order:**
```
     1
    / \
   2   3
Post-order: 2 → 3 → 1
```

This problem specifically requires **pre-order** (root first, then left, then right).

## Why Morris Traversal?

**Traditional approaches:**
- Recursion: O(h) space for call stack
- Stack: O(h) space for explicit stack
- Both use extra memory

**Morris Traversal:**
- Uses tree structure itself
- Temporary threading
- No extra space needed
- Achieves O(1) space!

**Trade-off:**
- More complex to understand
- Temporarily modifies tree
- But optimal space complexity

## Common Mistakes to Avoid

❌ **Losing right subtree reference**
```java
// Wrong: loses original right
curr.right = curr.left;
curr.left = null;
// Original right subtree lost!

// Right: connect first
prev.right = curr.right;  // Save connection
curr.right = curr.left;
curr.left = null;
```

❌ **Not finding rightmost**
```java
// Wrong: connects to wrong node
curr.left.right = curr.right;

// Right: find rightmost first
TreeNode prev = curr.left;
while (prev.right != null) {
    prev = prev.right;
}
prev.right = curr.right;
```

❌ **Forgetting to null left pointer**
```java
// Wrong: left not nulled
curr.right = curr.left;

// Right: null it
curr.right = curr.left;
curr.left = null;
```

## Step-by-Step Detailed Example

**Input:**
```
     1
    / \
   2   5
  / \   \
 3   4   6
```

**Execution:**

```
curr = 1
Left exists (node 2)
Find rightmost of left:
  prev = 2
  prev.right = 4 (not null)
  prev = 4
  prev.right = null (stop)
Connect: prev.right = curr.right
  4.right = 5

Current state:
     1
    / 
   2   
  / \   
 3   4 → 5
          \
           6

Move left to right:
  curr.right = curr.left = 2
  curr.left = null

Current state:
     1
      \
       2
      / \
     3   4 → 5
              \
               6

curr = curr.right = 2
──────────────────────

curr = 2
Left exists (node 3)
Find rightmost of left:
  prev = 3
  prev.right = null (stop)
Connect: prev.right = curr.right
  3.right = 4

Current state:
     1
      \
       2
      / 
     3 → 4 → 5
              \
               6

Move left to right:
  curr.right = curr.left = 3
  curr.left = null

Current state:
     1
      \
       2
        \
         3 → 4 → 5
                  \
                   6

curr = curr.right = 3
──────────────────────

curr = 3
Left is null, skip
curr = curr.right = 4
──────────────────────

curr = 4
Left is null, skip
curr = curr.right = 5
──────────────────────

curr = 5
Left is null, skip
curr = curr.right = 6
──────────────────────

curr = 6
Left is null, skip
curr = curr.right = null
──────────────────────

Done!

Final:
     1
      \
       2
        \
         3
          \
           4
            \
             5
              \
               6
```

## Why This Is Hard

**Challenges:**
1. In-place modification (can't create new nodes)
2. Don't lose references (right subtree!)
3. Maintain pre-order (threading strategy)
4. O(1) space constraint (no recursion/stack)

**Morris Traversal solves all:**
- Rewires in-place
- Saves references via threading
- Pre-order via left-to-right processing
- No extra space

## Tags

Tree, Linked List, Stack, Depth-First Search, Binary Tree
