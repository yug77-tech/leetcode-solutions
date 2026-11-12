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
 * LeetCode Problem 114: Flatten Binary Tree to Linked List
 * 
 * Morris Traversal - O(1) Space Solution
 * 
 * Problem:
 * Convert binary tree to "linked list" using right pointers.
 * Order must be pre-order traversal.
 * Must be done in-place.
 * 
 * Key Insight:
 * Morris Traversal technique!
 * - For each node with left subtree:
 *   1. Find rightmost node of left subtree
 *   2. Connect it to current's right subtree
 *   3. Move left subtree to right
 *   4. Null the left pointer
 * - Move to next node (current.right)
 * 
 * Why This Works:
 * 
 * Pre-order traversal: Root → Left subtree → Right subtree
 * 
 * When we process a node:
 * - Left subtree should come immediately after
 * - Right subtree comes after entire left subtree
 * - Rightmost of left = last node in left subtree
 * - So connect rightmost to original right!
 * 
 * Visual Example:
 * 
 * Original:
 *      1
 *     / \
 *    2   5
 *   / \   \
 *  3   4   6
 * 
 * Pre-order: 1 → 2 → 3 → 4 → 5 → 6
 * 
 * Step 1: At node 1
 *   Left exists (node 2)
 *   Find rightmost of left subtree:
 *     Start at 2, go right until null
 *     2.right = 4, so move to 4
 *     4.right = null, stop
 *     Rightmost = 4
 * 
 *   Connect rightmost to curr.right:
 *     4.right = 5
 * 
 *   Now tree looks like:
 *      1
 *     / 
 *    2   
 *   / \   
 *  3   4 → 5
 *           \
 *            6
 * 
 *   Move left to right:
 *     1.right = 2
 *     1.left = null
 * 
 *   Now tree looks like:
 *      1
 *       \
 *        2
 *       / \
 *      3   4 → 5
 *               \
 *                6
 * 
 * Step 2: Move to curr.right (node 2)
 *   Left exists (node 3)
 *   Find rightmost: node 3 (no right child)
 *   Connect: 3.right = 4
 *   
 *   Now:
 *      1
 *       \
 *        2
 *       /
 *      3 → 4 → 5
 *               \
 *                6
 * 
 *   Move left to right:
 *     2.right = 3
 *     2.left = null
 *   
 *   Now:
 *      1
 *       \
 *        2
 *         \
 *          3 → 4 → 5
 *                   \
 *                    6
 * 
 * Step 3-6: Continue for nodes 3,4,5,6
 *   All have no left child
 *   Just move curr = curr.right
 * 
 * Final:
 *      1
 *       \
 *        2
 *         \
 *          3
 *           \
 *            4
 *             \
 *              5
 *               \
 *                6
 * 
 * Why Find Rightmost?
 * 
 * Pre-order processes: Root → Left subtree → Right subtree
 * 
 * Example:
 *      1
 *     / \
 *    2   5
 *   / \
 *  3   4
 * 
 * Pre-order: 1 → 2 → 3 → 4 → 5
 * 
 * After processing node 1:
 * - Next is left subtree: 2,3,4
 * - After left subtree, need right subtree: 5
 * - Last node of left subtree = rightmost = 4
 * - So 4.right should point to 5
 * 
 * This maintains pre-order!
 * 
 * Why Not Lose Right Subtree?
 * 
 * Critical: connect BEFORE moving!
 * 
 * Order matters:
 * 1. Find rightmost of left
 * 2. Connect rightmost.right to curr.right (save it!)
 * 3. Move curr.left to curr.right
 * 4. Null curr.left
 * 
 * If we moved first:
 *   curr.right = curr.left  // Lost original right!
 *   Can't connect to it anymore
 * 
 * By connecting first:
 *   prev.right = curr.right  // Saved!
 *   curr.right = curr.left   // Now safe to overwrite
 * 
 * Morris Traversal vs Other Approaches:
 * 
 * 1. Recursive Pre-order:
 *    - Space: O(h) for recursion stack
 *    - Simple to understand
 * 
 * 2. Stack-based Iterative:
 *    - Space: O(h) for stack
 *    - Intuitive pre-order
 * 
 * 3. Morris Traversal (This solution):
 *    - Space: O(1) - no stack!
 *    - More complex logic
 *    - Optimal for space
 * 
 * Time Complexity Analysis:
 * 
 * Might seem like O(n²) because:
 * - For each node, find rightmost
 * - Finding rightmost seems like O(h) each time
 * 
 * But actually O(n):
 * - Each edge traversed at most twice
 * - Once when finding rightmost
 * - Once when curr moves along it
 * - Total edges in tree: n-1
 * - So total work: 2(n-1) = O(n)
 * 
 * Edge Cases:
 * 
 * 1. Empty tree:
 *    curr = null
 *    Loop doesn't execute
 *    Result: null ✓
 * 
 * 2. Single node:
 *    curr = root
 *    No left child, skip
 *    curr = curr.right = null
 *    Result: single node ✓
 * 
 * 3. Only left children:
 *         1
 *        /
 *       2
 *      /
 *     3
 *    Converts to:
 *    1 → 2 → 3 ✓
 * 
 * 4. Only right children:
 *    1 → 2 → 3
 *    Already flat
 *    No left children, just traverse ✓
 * 
 * 5. Full tree:
 *    All nodes processed correctly ✓
 * 
 * Common Mistakes:
 * 
 * 1. Not saving right subtree:
 *    curr.right = curr.left;  // Lost original right!
 *    
 *    Fix: Connect rightmost first
 * 
 * 2. Not finding rightmost:
 *    curr.left.right = curr.right;  // Wrong!
 *    Need to find actual rightmost
 * 
 * 3. Not nulling left:
 *    Forgot curr.left = null
 *    Tree not properly flattened
 * 
 * 4. Modifying curr instead of prev:
 *    while (curr.right != null)  // Wrong!
 *    Should be: while (prev.right != null)
 * 
 * Alternative Approaches:
 * 
 * 1. Reverse Post-order (Elegant):
 *    private TreeNode prev = null;
 *    
 *    public void flatten(TreeNode root) {
 *        if (root == null) return;
 *        flatten(root.right);  // Right first
 *        flatten(root.left);   // Then left
 *        root.right = prev;
 *        root.left = null;
 *        prev = root;
 *    }
 *    
 *    Clever: builds from back to front
 *    Space: O(h) for recursion
 * 
 * 2. Stack-based:
 *    Stack<TreeNode> stack = new Stack<>();
 *    stack.push(root);
 *    
 *    while (!stack.isEmpty()) {
 *        TreeNode curr = stack.pop();
 *        if (curr.right != null) stack.push(curr.right);
 *        if (curr.left != null) stack.push(curr.left);
 *        if (!stack.isEmpty()) curr.right = stack.peek();
 *        curr.left = null;
 *    }
 *    
 *    Intuitive pre-order
 *    Space: O(h) for stack
 * 
 * Morris Traversal is optimal for SPACE!
 * 
 * Pattern: Morris Traversal
 * 
 * Use when:
 * - Need O(1) space tree traversal
 * - In-place modification allowed
 * - Can temporarily modify structure
 * 
 * Key technique:
 * - Threading: temporary links
 * - Rightmost/predecessor finding
 * - In-place rewiring
 * 
 * Similar problems:
 * - Morris In-order Traversal
 * - Morris Pre-order Traversal
 * - Recover Binary Search Tree
 * - Binary Tree to Doubly Linked List
 * 
 * Time Complexity: O(n)
 *   - Each node visited at most twice
 *   - Once as current
 *   - Once when finding rightmost
 *   - Total: O(n)
 * 
 * Space Complexity: O(1)
 *   - No recursion stack
 *   - No explicit stack
 *   - Only few pointers (curr, prev)
 *   - Constant space!
 *   - Achieves follow-up requirement
 */
class Solution {
    public void flatten(TreeNode root) {
        // Start with current node at root
        TreeNode curr = root;

        // Process each node until we reach end
        while (curr != null) {
            // Check if current node has left subtree
            if (curr.left != null) {
                // Find the rightmost node of left subtree
                // This is the predecessor in Morris Traversal
                TreeNode prev = curr.left;
                
                // Keep going right until we find rightmost
                while (prev.right != null) {
                    prev = prev.right;
                }

                // Connect rightmost of left subtree to current's right
                // This saves the right subtree before we overwrite curr.right
                // CRITICAL: Do this BEFORE moving left to right!
                prev.right = curr.right;
                
                // Move left subtree to right
                // Now all of left subtree will be processed before right
                curr.right = curr.left;
                
                // Set left to null (required by problem)
                // This completes the flattening for this node
                curr.left = null;
            }

            // Move to next node (always right since we moved left to right)
            // This simulates pre-order: we'll process what was left subtree next
            curr = curr.right;
        }
    }
}
