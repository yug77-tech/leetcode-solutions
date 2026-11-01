# 108. Convert Sorted Array to Binary Search Tree

**Difficulty:** Easy

## Problem Description

Given an integer array `nums` where the elements are sorted in **ascending order**, convert it to a **height-balanced** binary search tree.

A **height-balanced** binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.

## Examples

**Example 1:**
```
Input: nums = [-10,-3,0,5,9]
Output: [0,-3,9,-10,null,5]
Explanation: [0,-10,5,null,-3,null,9] is also accepted:

       0                    0
      / \                  / \
    -3   9               -10  5
    /   /                  \   \
  -10  5                   -3   9
```

**Example 2:**
```
Input: nums = [1,3]
Output: [3,1]
Explanation: [1,null,3] and [3,1] are both height-balanced BSTs.

    3        or       1
   /                   \
  1                     3
```

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is sorted in a **strictly increasing** order.

## Solution Approach

The key insight: **Use the middle element as root to create a balanced BST!**

### Divide and Conquer with Recursion

**Why Middle Element?**
- Array is sorted → inorder traversal of BST
- Middle element ensures balance
- Left half becomes left subtree
- Right half becomes right subtree

**Visual Example: [-10,-3,0,5,9]**

```
Array: [-10, -3, 0, 5, 9]
              ↑
            mid=2
            
Step 1: Root = 0 (middle element)

                0
              /   \
    [-10,-3]       [5,9]
    
Step 2: Build left subtree from [-10,-3]
        mid = 0 (of this subarray)
        Root = -3
        
                0
              /   \
            -3     [5,9]
           /
       [-10]
       
Step 3: Build left of -3 from [-10]
        mid = 0
        Root = -10
        
                0
              /   \
            -3     [5,9]
           /
        -10
        
Step 4: Build right subtree from [5,9]
        mid = 0 (of this subarray)
        Root = 5
        
                0
              /   \
            -3     5
           /        \
        -10          [9]
        
Step 5: Build right of 5 from [9]
        mid = 0
        Root = 9
        
                0
              /   \
            -3     5
           /        \
        -10          9

Final balanced BST!
```

### Algorithm Steps

**Base Case:**
- If left > right: empty range → return null

**Recursive Case:**
1. Find middle index: `mid = left + (right - left) / 2`
2. Create root node with `nums[mid]`
3. Recursively build left subtree from `[left, mid-1]`
4. Recursively build right subtree from `[mid+1, right]`
5. Return root

### Why This Creates Balanced Tree?

**Balance Property:**
- Left subtree has ~n/2 elements
- Right subtree has ~n/2 elements
- Difference ≤ 1 at every level

**Example:**
```
Array length: 7
Left:  3 elements (indices 0-2)
Root:  1 element (index 3)
Right: 3 elements (indices 4-6)

Perfectly balanced!

Array length: 8
Left:  3 elements (indices 0-2)
Root:  1 element (index 3)
Right: 4 elements (indices 4-7)

Difference = 1, still balanced!
```

### Detailed Walkthrough

**Input: nums = [-10,-3,0,5,9]**

```
Call: build(nums, left=0, right=4)
  mid = 0 + (4-0)/2 = 2
  root = TreeNode(nums[2]) = TreeNode(0)
  
  Build left: build(nums, left=0, right=1)
    mid = 0 + (1-0)/2 = 0
    root = TreeNode(nums[0]) = TreeNode(-10)
    
    Build left: build(nums, left=0, right=-1)
      left > right → return null
    
    Build right: build(nums, left=1, right=1)
      mid = 1 + (1-1)/2 = 1
      root = TreeNode(nums[1]) = TreeNode(-3)
      
      Build left: build(nums, left=1, right=0)
        left > right → return null
      
      Build right: build(nums, left=2, right=1)
        left > right → return null
      
      return TreeNode(-3)
    
    TreeNode(-10).right = TreeNode(-3)
    return TreeNode(-10) with right child -3
  
  Wait, let me recalculate correctly...
  
  Build left: build(nums, left=0, right=1)
    mid = 0 + (1-0)/2 = 0
    root = TreeNode(nums[0]) = TreeNode(-10)
    
    Build left: build(nums, left=0, right=-1)
      left > right → return null
    
    Build right: build(nums, left=1, right=1)
      mid = 1
      root = TreeNode(nums[1]) = TreeNode(-3)
      
      Build left: build(nums, left=1, right=0)
        left > right → return null
      
      Build right: build(nums, left=2, right=1)
        left > right → return null
      
      return TreeNode(-3)
    
    TreeNode(-10).right = TreeNode(-3)
    return TreeNode(-10, null, TreeNode(-3))

Actually, I made an error. Let me recalculate:

Array: [-10, -3, 0, 5, 9]
Index:   0    1   2  3  4

build(0, 4):
  mid = 2, root = 0
  
  left = build(0, 1):
    mid = 0, root = -10
    left = build(0, -1) → null
    right = build(1, 1):
      mid = 1, root = -3
      left = build(1, 0) → null
      right = build(2, 1) → null
      return -3
    return -10 with right=-3
  
  Wait, this is getting confusing. Let me trace more carefully.
```

Let me fix the trace:

```
Array: [-10, -3, 0, 5, 9]
Index:   0    1   2  3  4

build(left=0, right=4):
  mid = 0 + (4-0)/2 = 2
  root = TreeNode(0)
  
  root.left = build(left=0, right=1)
    mid = 0 + (1-0)/2 = 0
    root = TreeNode(-10)
    
    root.left = build(left=0, right=-1)
      return null
    
    root.right = build(left=1, right=1)
      mid = 1 + (1-1)/2 = 1
      root = TreeNode(-3)
      
      root.left = build(left=1, right=0)
        return null
      
      root.right = build(left=2, right=1)
        return null
      
      return TreeNode(-3)
    
    return TreeNode(-10, null, TreeNode(-3))
  
  root.right = build(left=3, right=4)
    mid = 3 + (4-3)/2 = 3
    root = TreeNode(5)
    
    root.left = build(left=3, right=2)
      return null
    
    root.right = build(left=4, right=4)
      mid = 4
      root = TreeNode(9)
      
      root.left = build(left=4, right=3)
        return null
      
      root.right = build(left=5, right=4)
        return null
      
      return TreeNode(9)
    
    return TreeNode(5, null, TreeNode(9))
  
  return TreeNode(0, TreeNode(-10,null,-3), TreeNode(5,null,9))

This doesn't look right either. Let me think again...
```

Actually, when mid=0 for range [0,1], root should be -10, but then right subtree is [1,1] which is -3. That doesn't follow BST property where -3 should be GREATER than -10, which it is! So that's correct.

Let me visualize the final tree properly:

```
              0
            /   \
         -10     5
            \     \
            -3     9
```

Wait, that's wrong! -3 > -10, so -3 should be in right subtree of -10. But BST property says left < root < right for the entire tree.

Let me reconsider: The middle approach with left subtree = [0, mid-1] and right = [mid+1, right]:

```
Array: [-10, -3, 0, 5, 9]

Root = 0 (mid=2)
Left subtree = [-10, -3] (indices 0-1)
Right subtree = [5, 9] (indices 3-4)

For left subtree [-10, -3]:
  mid = 0, root = -10
  Left = [] (empty)
  Right = [-3] (index 1)
  
Wait, that gives:
    -10
      \
      -3

For right subtree [5, 9]:
  mid = 3, root = 5
  Left = [] (empty)
  Right = [9] (index 4)
  
That gives:
    5
     \
      9

So final tree:
        0
      /   \
   -10     5
      \     \
      -3     9
```

Hmm, let me recalculate mid for [left=0, right=1]:
mid = 0 + (1-0)/2 = 0 + 0 = 0

So root is nums[0] = -10
Left subtree: [0, -1] → empty
Right subtree: [1, 1] → nums[1] = -3

Yes! So the tree is:
```
        0
      /   \
   -10     5
      \     \
      -3     9
```

This is height-balanced and valid!

## Complexity Analysis

### Divide and Conquer (Provided):

- **Time Complexity:** O(n)
  - Visit each element exactly once
  - Create one node per element
  - No repeated work

- **Space Complexity:** O(log n) or O(n)
  - Recursion stack depth: O(log n) for balanced tree
  - Output tree nodes: O(n) (if counting result)
  - If not counting result: O(log n)

## Alternative Approaches

**1. Inorder with Global Index (Less Clean):**
```java
class Solution {
    private int index = 0;
    
    public TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length);
    }
    
    private TreeNode build(int[] nums, int left, int right) {
        if (left >= right) return null;
        
        int mid = (left + right) / 2;
        TreeNode leftChild = build(nums, left, mid);
        TreeNode root = new TreeNode(nums[index++]);
        TreeNode rightChild = build(nums, mid + 1, right);
        
        root.left = leftChild;
        root.right = rightChild;
        return root;
    }
}
```
- Uses global index
- More complex to understand

Your direct approach is **cleaner and more intuitive**!

## Edge Cases Handled

✓ **Single element:** [0] → Tree with one node  
✓ **Two elements:** [1,3] → Root with one child  
✓ **Odd length:** [1,2,3,4,5] → Perfectly balanced  
✓ **Even length:** [1,2,3,4] → Balanced (diff ≤ 1)  
✓ **Negative numbers:** Works fine  
✓ **Large array:** Efficient O(n)  

## Pattern Recognition

This problem teaches **Divide and Conquer on Sorted Array**:

**Use this pattern when:**
- Need to build balanced tree from sorted data
- Can divide problem into equal halves
- Combine solutions from subproblems

**Similar problems:**
- Convert Sorted List to BST
- Balance a Binary Search Tree
- Construct Binary Tree from Preorder/Inorder
- Merge K Sorted Lists

## Key Insights

✓ **Middle element = root** - Ensures balance  
✓ **Recursively split halves** - Divide and conquer  
✓ **Sorted array = inorder** - BST property  
✓ **No rebalancing needed** - Built balanced from start  
✓ **O(n) construction** - Each element visited once  

## Why Use `left + (right - left) / 2`?

**Prevents integer overflow:**
```java
// Could overflow if left and right are large
int mid = (left + right) / 2;

// Safe: no overflow
int mid = left + (right - left) / 2;
```

Both give same result, but second is safer!

## Tags

Array, Divide and Conquer, Tree, Binary Search Tree, Binary Tree, Recursion
