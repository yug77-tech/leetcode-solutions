import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

/**
 * LeetCode Problem 102: Binary Tree Level Order Traversal
 * 
 * Classic BFS (Breadth-First Search) with Queue
 * 
 * Problem:
 * Return level-order traversal (left to right, level by level).
 * 
 * Key Insight:
 * BFS is NATURAL for level-order traversal!
 * - Use queue (FIFO structure)
 * - Process nodes level by level
 * - Capture size to separate levels
 * 
 * Why Queue?
 * 
 * Queue = First In, First Out (FIFO)
 * 
 * Tree:
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 
 * Add root: queue = [3]
 * 
 * Process 3:
 *   Add children left-to-right: 9, then 20
 *   queue = [9, 20]
 * 
 * Process 9:
 *   No children
 *   queue = [20]
 * 
 * Process 20:
 *   Add children: 15, then 7
 *   queue = [15, 7]
 * 
 * FIFO order = level-by-level order!
 * 
 * Why Capture Size?
 * 
 * KEY TECHNIQUE: Know when level ends!
 * 
 * Without size:
 *   queue = [9, 20]
 *   Process 9, add nothing
 *   Process 20, add 15 and 7
 *   Now queue = [15, 7]
 *   How to know level 1 ended?
 *   Can't tell without extra tracking!
 * 
 * With size:
 *   queue = [9, 20]
 *   size = 2  ← Captured at start!
 *   Process exactly 2 nodes (9 and 20)
 *   Even though 15 and 7 added during,
 *   they don't get processed yet
 *   Level separation maintained!
 * 
 * Algorithm Flow:
 * 
 * 1. Check if tree is empty
 * 2. Add root to queue
 * 3. While queue not empty:
 *    a. Capture current level size
 *    b. Create list for this level
 *    c. Process exactly 'size' nodes:
 *       - Poll node from queue
 *       - Add value to level list
 *       - Add children to queue (for next level)
 *    d. Add level list to result
 * 4. Return result
 * 
 * Example Execution:
 * 
 * Tree:
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 
 * Initial:
 *   ans = []
 *   queue = [3]
 * 
 * Level 0:
 *   size = 1
 *   level = []
 *   
 *   i=0: node=3
 *     level.add(3) → [3]
 *     Add 9, 20 to queue
 *   
 *   ans.add([3]) → [[3]]
 *   queue = [9, 20]
 * 
 * Level 1:
 *   size = 2 ← Important! Fixed count
 *   level = []
 *   
 *   i=0: node=9
 *     level.add(9) → [9]
 *     No children
 *   
 *   i=1: node=20
 *     level.add(20) → [9, 20]
 *     Add 15, 7 to queue
 *   
 *   ans.add([9,20]) → [[3], [9,20]]
 *   queue = [15, 7]
 * 
 * Level 2:
 *   size = 2
 *   level = []
 *   
 *   i=0: node=15
 *     level.add(15) → [15]
 *     No children
 *   
 *   i=1: node=7
 *     level.add(7) → [15, 7]
 *     No children
 *   
 *   ans.add([15,7]) → [[3], [9,20], [15,7]]
 *   queue = []
 * 
 * Queue empty, done!
 * Result: [[3], [9,20], [15,7]]
 * 
 * Why Size Capture Works:
 * 
 * At start of while loop:
 *   Queue contains ALL and ONLY current level nodes
 * 
 * During for loop:
 *   We add children (next level)
 *   But size is fixed, so we don't process them yet
 * 
 * After for loop:
 *   Queue contains ALL and ONLY next level nodes
 * 
 * This separation is KEY to level-order!
 * 
 * Queue Operations:
 * 
 * offer(node):
 *   - Adds to end of queue
 *   - Returns false if fails (better than exception)
 *   - Preferred over add()
 * 
 * poll():
 *   - Removes from front of queue
 *   - Returns null if empty (better than exception)
 *   - Preferred over remove()
 * 
 * size():
 *   - Returns number of elements
 *   - IMPORTANT: capture before loop, not in loop condition!
 * 
 * isEmpty():
 *   - Checks if queue has elements
 *   - Used for main while loop
 * 
 * Why Not Use DFS?
 * 
 * DFS (Depth-First Search):
 *   Goes deep first
 *   Would need extra level tracking
 *   Less natural for level-order
 * 
 * Example DFS:
 *   public void dfs(TreeNode node, int level, List<List<Integer>> result) {
 *       if (node == null) return;
 *       if (level == result.size()) result.add(new ArrayList<>());
 *       result.get(level).add(node.val);
 *       dfs(node.left, level + 1, result);
 *       dfs(node.right, level + 1, result);
 *   }
 * 
 * Works, but:
 *   - Needs level parameter
 *   - Needs to create lists as needed
 *   - Less intuitive
 *   - BFS is cleaner!
 * 
 * Edge Cases:
 * 
 * 1. Empty tree:
 *    root = null
 *    Return [] immediately
 *    No queue operations
 * 
 * 2. Single node:
 *    root = 1
 *    queue = [1]
 *    size = 1
 *    Process 1, no children
 *    Result: [[1]]
 * 
 * 3. Left skewed:
 *         1
 *        /
 *       2
 *      /
 *     3
 *    Each level has 1 node
 *    Result: [[1], [2], [3]]
 * 
 * 4. Complete tree:
 *         1
 *        / \
 *       2   3
 *      / \
 *     4   5
 *    All levels processed correctly
 *    Result: [[1], [2,3], [4,5]]
 * 
 * Common Mistakes:
 * 
 * 1. Not capturing size:
 *    for (int i = 0; i < queue.size(); i++)
 *    Problem: queue.size() changes during loop!
 *    Solution: int size = queue.size() first
 * 
 * 2. Adding null children:
 *    queue.offer(node.left);  // Might be null
 *    Problem: Nulls in queue cause errors
 *    Solution: Check if not null first
 * 
 * 3. Not handling empty tree:
 *    queue.offer(root);  // If root is null
 *    Problem: Adds null to queue
 *    Solution: Check root first
 * 
 * 4. Creating level list outside loop:
 *    List<Integer> level = new ArrayList<>();
 *    while (!queue.isEmpty()) { ... }
 *    Problem: Same list reused for all levels!
 *    Solution: Create new list each iteration
 * 
 * BFS vs DFS Comparison:
 * 
 * BFS (This solution):
 *   - Uses Queue
 *   - Space: O(w) where w = max width
 *   - Natural for level-order
 *   - Iterative
 * 
 * DFS:
 *   - Uses Stack (implicit via recursion)
 *   - Space: O(h) where h = height
 *   - Needs level tracking
 *   - Recursive or explicit stack
 * 
 * For level-order, BFS is the way!
 * 
 * Space Complexity Analysis:
 * 
 * Queue holds at most one level at a time
 * 
 * Complete tree:
 *         1
 *        / \
 *       2   3
 *      / \ / \
 *     4  5 6  7
 * 
 * Bottom level: 4 nodes (n/2 for n=7)
 * Queue at that level: [4,5,6,7]
 * 
 * Worst case: O(n)
 * Best case (skewed): O(1)
 * Average: O(w) where w is width
 * 
 * Pattern: Level-Order BFS
 * 
 * Use when:
 *   - Process tree level by level
 *   - Group nodes by depth
 *   - Need left-to-right order per level
 *   - Find shortest path (unweighted)
 * 
 * Similar problems:
 *   - Binary Tree Zigzag Level Order (103)
 *   - Binary Tree Level Order II (107) - bottom-up
 *   - Binary Tree Right Side View (199)
 *   - Average of Levels (637)
 *   - Maximum/Minimum Depth
 * 
 * Time Complexity: O(n)
 *   - Visit each node exactly once
 *   - Each node added to queue once
 *   - Each node polled from queue once
 *   - O(n) total operations
 * 
 * Space Complexity: O(w)
 *   - w = maximum width of tree
 *   - Queue holds at most one level
 *   - Worst case: complete tree, w = n/2
 *   - So O(n) worst case
 *   - Not counting output space
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        // Result list to store all levels
        List<List<Integer>> ans = new ArrayList<>();

        // Handle empty tree case
        if (root == null) {
            return ans;
        }

        // Queue for BFS traversal
        // LinkedList implements Queue interface
        Queue<TreeNode> queue = new LinkedList<>();
        
        // Start with root node
        queue.offer(root);

        // Process tree level by level
        while (!queue.isEmpty()) {
            // CRITICAL: Capture size at start of level
            // This tells us how many nodes are in current level
            // Size will change as we add children, but this is fixed
            int size = queue.size();
            
            // List to store values of current level
            List<Integer> level = new ArrayList<>();

            // Process exactly 'size' nodes (entire current level)
            for (int i = 0; i < size; i++) {
                // Remove and get front node from queue
                TreeNode node = queue.poll();
                
                // Add node's value to current level
                level.add(node.val);

                // Add children to queue for next level
                // Check for null to avoid adding null to queue
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            // Add completed level to result
            ans.add(level);
        }

        return ans;
    }
}
