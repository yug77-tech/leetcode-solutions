# 24. Swap Nodes in Pairs

**Difficulty:** Medium

## Problem Description

Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem **without modifying the values** in the list's nodes (i.e., only nodes themselves may be changed.)

## Examples

**Example 1:**
```
Input: head = [1,2,3,4]
Output: [2,1,4,3]

Visual:
1 → 2 → 3 → 4
becomes
2 → 1 → 4 → 3
```

**Example 2:**
```
Input: head = []
Output: []
```

**Example 3:**
```
Input: head = [1]
Output: [1]
```

**Example 4:**
```
Input: head = [1,2,3]
Output: [2,1,3]

Visual:
1 → 2 → 3
becomes
2 → 1 → 3
(last node unchanged)
```

## Constraints

- The number of nodes in the list is in the range `[0, 100]`.
- `0 <= Node.val <= 100`

## Solution Approach

The key is using a **dummy node** and carefully manipulating pointers!

### Iterative Pointer Manipulation

**Key Ideas:**
1. Use a dummy node to handle edge cases (especially head swap)
2. Track current position with a pointer
3. Identify the two nodes to swap: `first` and `second`
4. Perform the swap using three pointer assignments
5. Move current pointer forward

**The Swap Operation:**
```
Before:  curr → first → second → second.next
After:   curr → second → first → second.next
```

**Three pointer assignments:**
```java
first.next = second.next;   // first points past second
second.next = first;         // second points to first
curr.next = second;          // curr points to second
```

### Visual Example

**Input: 1 → 2 → 3 → 4**

```
Initial state:
dummy → 1 → 2 → 3 → 4
curr

Step 1: Swap (1, 2)
  first = 1, second = 2
  
  1. first.next = second.next
     dummy → 1 → 3 → 4
             ↓
             2
  
  2. second.next = first
     dummy → 1 ← 2 → 3 → 4
  
  3. curr.next = second
     dummy → 2 → 1 → 3 → 4

Result after step 1:
dummy → 2 → 1 → 3 → 4
            curr (move here)

Step 2: Swap (3, 4)
  curr = 1 (from previous)
  first = 3, second = 4
  
  Same three operations:
  dummy → 2 → 1 → 4 → 3

Final result:
dummy → 2 → 1 → 4 → 3
Return dummy.next = [2,1,4,3]
```

### Detailed Walkthrough

**Example: [1, 2, 3, 4]**

```
Setup:
  dummy.next = head
  curr = dummy

Iteration 1:
  curr = dummy (pointing before 1)
  first = 1
  second = 2
  
  Swap:
    first.next = 3       // 1 → 3
    second.next = first  // 2 → 1
    curr.next = second   // dummy → 2
  
  State: dummy → 2 → 1 → 3 → 4
  curr = first (node 1)

Iteration 2:
  curr = node 1
  first = 3
  second = 4
  
  Swap:
    first.next = null    // 3 → null
    second.next = first  // 4 → 3
    curr.next = second   // 1 → 4
  
  State: dummy → 2 → 1 → 4 → 3
  curr = node 3

Exit: curr.next.next = null
Return: dummy.next = node 2
```

### Why Dummy Node?

**Problem:** If we swap the first two nodes, the head changes!

**Without dummy:**
```java
// Complex: need special handling for head
if (head != null && head.next != null) {
    newHead = head.next;
    // ... complex swap logic
    return newHead;
}
```

**With dummy:**
```java
// Simple: dummy always points to new head
dummy.next = head;
// ... uniform swap logic
return dummy.next;  // Always works!
```

### Loop Condition

```java
while (curr.next != null && curr.next.next != null)
```

**Why both conditions?**
- `curr.next != null`: Ensures first node exists
- `curr.next.next != null`: Ensures second node exists
- If either is false, we can't swap (need a pair!)

**Edge cases:**
- Empty list: `head = null` → dummy.next = null → loop never runs
- Single node: `head = [1]` → curr.next.next = null → loop never runs
- Odd length: Last iteration leaves one node unchanged

## Complexity Analysis

### Iterative Solution (Provided):

- **Time Complexity:** O(n)
  - Visit each node once
  - Constant work per pair

- **Space Complexity:** O(1)
  - Only pointer variables (dummy, curr, first, second)
  - No recursion, no extra data structures

### Alternative: Recursive Solution

```java
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    
    ListNode newHead = head.next;
    head.next = swapPairs(newHead.next);
    newHead.next = head;
    
    return newHead;
}
```
- Time: O(n), Space: O(n) - call stack
- Elegant but uses recursion stack

Your iterative solution is **more space-efficient**!

## Common Mistakes to Avoid

❌ **Modifying node values** instead of swapping nodes  
❌ **Losing references** (must save pointers before changing them)  
❌ **Forgetting dummy node** (complicates head handling)  
❌ **Wrong loop condition** (missing null checks)  
❌ **Wrong pointer update order** (must follow correct sequence)  

## Tags

Linked List, Recursion, Pointer Manipulation
