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
 * LeetCode Problem 108: Convert Sorted Array to Binary Search Tree
 * 
 * Divide and conquer to build balanced BST
 * 
 * Problem:
 * Convert sorted array to height-balanced Binary Search Tree.
 * Height-balanced: depth of subtrees differs by at most 1.
 * 
 * Key Insight:
 * Use MIDDLE element as root to ensure balance!
 * - Array is sorted → represents inorder traversal
 * - Middle element splits array evenly
 * - Left half becomes left subtree
 * - Right half becomes right subtree
 * - Recursively apply to subarrays
 * 
 * Why Middle Element?
 * 
 * For balanced tree, want equal-sized subtrees:
 * - Choose middle as root
 * - Left has ~n/2 elements
 * - Right has ~n/2 elements
 * - Height difference ≤ 1
 * 
 * Visual Example: [-10, -3, 0, 5, 9]
 * 
 * Step 1: Root = 0 (middle)
 *                 0
 *               /   \
 *     [-10,-3]       [5,9]
 * 
 * Step 2: Left subtree from [-10,-3]
 *         mid = -10 (when range is [0,1])
 *                 0
 *               /   \
 *            -10     [5,9]
 *               \
 *              [-3]
 * 
 * Step 3: Right of -10 from [-3]
 *                 0
 *               /   \
 *            -10     [5,9]
 *               \
 *               -3
 * 
 * Step 4: Right subtree from [5,9]
 *         mid = 5
 *                 0
 *               /   \
 *            -10     5
 *               \     \
 *               -3    [9]
 * 
 * Step 5: Right of 5 from [9]
 *                 0
 *               /   \
 *            -10     5
 *               \     \
 *               -3     9
 * 
 * Final: Height-balanced BST!
 * 
 * Algorithm:
 * 
 * Base case:
 *   If left > right: empty range → return null
 * 
 * Recursive case:
 *   1. Find middle: mid = left + (right - left) / 2
 *   2. Create root with nums[mid]
 *   3. Build left subtree from [left, mid-1]
 *   4. Build right subtree from [mid+1, right]
 *   5. Return root
 * 
 * Why This Creates Balanced Tree:
 * 
 * At every node:
 * - Left subtree has elements [left, mid-1]
 * - Right subtree has elements [mid+1, right]
 * - Sizes differ by at most 1
 * - Height difference ≤ 1 (balanced!)
 * 
 * Example Trace: [1,2,3]
 * 
 * build(left=0, right=2):
 *   mid = 0 + (2-0)/2 = 1
 *   root = TreeNode(2)
 *   
 *   root.left = build(left=0, right=0):
 *     mid = 0
 *     root = TreeNode(1)
 *     root.left = build(0, -1) → null
 *     root.right = build(1, 0) → null
 *     return TreeNode(1)
 *   
 *   root.right = build(left=2, right=2):
 *     mid = 2
 *     root = TreeNode(3)
 *     root.left = build(2, 1) → null
 *     root.right = build(3, 2) → null
 *     return TreeNode(3)
 *   
 *   return TreeNode(2, TreeNode(1), TreeNode(3))
 * 
 * Result:
 *     2
 *    / \
 *   1   3
 * 
 * Perfectly balanced!
 * 
 * Why left + (right - left) / 2?
 * 
 * Prevents integer overflow:
 * - (left + right) / 2 could overflow if left+right > MAX_INT
 * - left + (right-left)/2 is mathematically equivalent
 * - But safer: (right-left) is smaller, no overflow risk
 * 
 * Example:
 * left=1000000000, right=2000000000
 * (left+right)/2 = 3000000000/2 → overflow!
 * left+(right-left)/2 = 1000000000+500000000 = 1500000000 ✓
 * 
 * Edge Cases:
 * 
 * 1. Single element: [5]
 *    build(0, 0):
 *      mid = 0, root = 5
 *      left = null, right = null
 *      return TreeNode(5)
 * 
 * 2. Two elements: [1,3]
 *    build(0, 1):
 *      mid = 0, root = 1
 *      left = null
 *      right = build(1,1) = TreeNode(3)
 *      return TreeNode(1, null, 3)
 *    
 *    Result: 1
 *             \
 *              3
 * 
 * 3. Empty array: [] (not in constraints)
 *    Would return null
 * 
 * BST Property Check:
 * 
 * For any node:
 * - All left subtree values < node value
 * - All right subtree values > node value
 * 
 * Since array is sorted:
 * - Elements [left, mid-1] < nums[mid]
 * - Elements [mid+1, right] > nums[mid]
 * - BST property maintained!
 * 
 * Height Balance Check:
 * 
 * For array of size n:
 * - Left subtree size: (mid - left)
 * - Right subtree size: (right - mid)
 * - Difference: |(mid-left) - (right-mid)|
 * 
 * For odd n: perfectly balanced
 * For even n: difference = 1
 * 
 * Both satisfy height-balanced requirement!
 * 
 * Divide and Conquer Pattern:
 * 
 * 1. Divide: Split array at middle
 * 2. Conquer: Recursively build left and right subtrees
 * 3. Combine: Create root, attach subtrees
 * 
 * No additional work after recursion (unlike merge sort)
 * 
 * Comparison with Other Approaches:
 * 
 * 1. Iterative with Queue:
 *    - More complex
 *    - Same time complexity
 *    - Uses extra space for queue
 * 
 * 2. Insert elements one by one:
 *    - O(n log n) time
 *    - May need rebalancing (AVL/Red-Black)
 *    - Much slower
 * 
 * This recursive approach is optimal!
 * 
 * Time Complexity: O(n)
 *   - Visit each element exactly once
 *   - Create one TreeNode per element
 *   - No repeated work
 *   - T(n) = 2*T(n/2) + O(1) = O(n)
 * 
 * Space Complexity: O(log n)
 *   - Recursion stack depth
 *   - For balanced tree: log(n) levels
 *   - Each call uses O(1) space
 *   - Not counting output tree (O(n) nodes)
 */
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        // Build BST from entire array
        return build(nums, 0, nums.length - 1);
    }

    /**
     * Recursively build balanced BST from subarray
     * 
     * @param nums  Sorted array
     * @param left  Left boundary (inclusive)
     * @param right Right boundary (inclusive)
     * @return Root of BST for this range
     */
    private TreeNode build(int[] nums, int left, int right) {
        // Base case: empty range
        if (left > right) {
            return null;
        }

        // Find middle element
        // Use left + (right-left)/2 to prevent overflow
        // Same as (left+right)/2 but safer
        int mid = left + (right - left) / 2;

        // Middle element becomes root (ensures balance)
        TreeNode root = new TreeNode(nums[mid]);

        // Recursively build left subtree from left half
        // Range: [left, mid-1]
        // All elements < nums[mid] (BST property)
        root.left = build(nums, left, mid - 1);

        // Recursively build right subtree from right half
        // Range: [mid+1, right]
        // All elements > nums[mid] (BST property)
        root.right = build(nums, mid + 1, right);

        // Return root with left and right subtrees attached
        return root;
    }
}
