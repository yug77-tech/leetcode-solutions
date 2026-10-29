# 46. Permutations

**Difficulty:** Medium

## Problem Description

Given an array `nums` of **distinct** integers, return all the possible **permutations**. You can return the answer in **any order**.

## Examples

**Example 1:**
```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

**Example 2:**
```
Input: nums = [0,1]
Output: [[0,1],[1,0]]
```

**Example 3:**
```
Input: nums = [1]
Output: [[1]]
```

## Constraints

- `1 <= nums.length <= 6`
- `-10 <= nums[i] <= 10`
- All the integers of `nums` are **unique**.

## Solution Approach

This is a **classic backtracking problem!** The key insight: **Build permutations by swapping elements and exploring all possibilities.**

### Backtracking with In-Place Swapping

**Core Idea:**
- Fix each element at each position
- Recursively permute the remaining elements
- Swap back to restore state (backtrack)

**Visual Tree for [1,2,3]:**

```
                    [1,2,3]
                    index=0
         ┌────────────┼────────────┐
         │            │            │
    Fix 1 at 0   Fix 2 at 0   Fix 3 at 0
    [1,2,3]      [2,1,3]      [3,2,1]
    index=1      index=1      index=1
    ┌──┴──┐      ┌──┴──┐      ┌──┴──┐
    │     │      │     │      │     │
  Fix 2 Fix 3  Fix 1 Fix 3  Fix 2 Fix 1
  [1,2,3][1,3,2][2,1,3][2,3,1][3,2,1][3,1,2]
  index=2 index=2 index=2 index=2 index=2 index=2
    ↓     ↓     ↓     ↓     ↓     ↓
  [1,2,3][1,3,2][2,1,3][2,3,1][3,2,1][3,1,2]

Result: 6 permutations (3! = 6)
```

### Algorithm Steps

**Base Case:**
- When index reaches end of array
- Current arrangement is a complete permutation
- Add to result

**Recursive Case:**
- For each position from index to end:
  - Swap current index with position i
  - Recursively permute remaining elements
  - Swap back (backtrack) to restore original state

### Detailed Walkthrough

**Input: nums = [1,2,3]**

```
Call: backtrack([1,2,3], index=0)

  Loop i=0: Swap nums[0] with nums[0] → [1,2,3]
    Call: backtrack([1,2,3], index=1)
    
      Loop i=1: Swap nums[1] with nums[1] → [1,2,3]
        Call: backtrack([1,2,3], index=2)
        
          Loop i=2: Swap nums[2] with nums[2] → [1,2,3]
            Call: backtrack([1,2,3], index=3)
            
              index==3 → Add [1,2,3] ✓
            
            Swap back: [1,2,3]
      
      Swap back: [1,2,3]
      
      Loop i=2: Swap nums[1] with nums[2] → [1,3,2]
        Call: backtrack([1,3,2], index=2)
        
          Loop i=2: Swap nums[2] with nums[2] → [1,3,2]
            Call: backtrack([1,3,2], index=3)
            
              index==3 → Add [1,3,2] ✓
            
            Swap back: [1,3,2]
      
      Swap back: [1,2,3]
  
  Swap back: [1,2,3]
  
  Loop i=1: Swap nums[0] with nums[1] → [2,1,3]
    Call: backtrack([2,1,3], index=1)
    
      Loop i=1: Swap nums[1] with nums[1] → [2,1,3]
        Call: backtrack([2,1,3], index=2)
        
          Loop i=2: Swap nums[2] with nums[2] → [2,1,3]
            Call: backtrack([2,1,3], index=3)
            
              index==3 → Add [2,1,3] ✓
            
            Swap back: [2,1,3]
      
      Swap back: [2,1,3]
      
      Loop i=2: Swap nums[1] with nums[2] → [2,3,1]
        Call: backtrack([2,3,1], index=2)
        
          Loop i=2: Swap nums[2] with nums[2] → [2,3,1]
            Call: backtrack([2,3,1], index=3)
            
              index==3 → Add [2,3,1] ✓
            
            Swap back: [2,3,1]
      
      Swap back: [2,1,3]
  
  Swap back: [1,2,3]
  
  Loop i=2: Swap nums[0] with nums[2] → [3,2,1]
    Call: backtrack([3,2,1], index=1)
    
      Loop i=1: Swap nums[1] with nums[1] → [3,2,1]
        Call: backtrack([3,2,1], index=2)
        
          Loop i=2: Swap nums[2] with nums[2] → [3,2,1]
            Call: backtrack([3,2,1], index=3)
            
              index==3 → Add [3,2,1] ✓
            
            Swap back: [3,2,1]
      
      Swap back: [3,2,1]
      
      Loop i=2: Swap nums[1] with nums[2] → [3,1,2]
        Call: backtrack([3,1,2], index=2)
        
          Loop i=2: Swap nums[2] with nums[2] → [3,1,2]
            Call: backtrack([3,1,2], index=3)
            
              index==3 → Add [3,1,2] ✓
            
            Swap back: [3,1,2]
      
      Swap back: [3,2,1]
  
  Swap back: [1,2,3]

Result: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,2,1],[3,1,2]]
```

### Why Swap Back? (Backtracking)

**Critical Concept:**
```
Before recursive call: Swap to create new state
After recursive call:  Swap back to restore original state

This allows trying different choices at same position!
```

**Example:**
```
State: [1,2,3] at index=0

Try fixing 1 at position 0: [1,2,3]
  Explore...
  Swap back → [1,2,3]
  
Try fixing 2 at position 0: Swap → [2,1,3]
  Explore...
  Swap back → [1,2,3]
  
Try fixing 3 at position 0: Swap → [3,2,1]
  Explore...
  Swap back → [1,2,3]

Without swap back, we couldn't try different choices!
```

### Step-by-Step for [1,2]

```
Initial: [1,2]

backtrack([1,2], index=0)
  
  i=0: Swap nums[0]↔nums[0] → [1,2] (no change)
    backtrack([1,2], index=1)
      i=1: Swap nums[1]↔nums[1] → [1,2]
        backtrack([1,2], index=2)
          index==2 → Add [1,2] ✓
      Swap back → [1,2]
    Return
  Swap back → [1,2]
  
  i=1: Swap nums[0]↔nums[1] → [2,1]
    backtrack([2,1], index=1)
      i=1: Swap nums[1]↔nums[1] → [2,1]
        backtrack([2,1], index=2)
          index==2 → Add [2,1] ✓
      Swap back → [2,1]
    Return
  Swap back → [1,2]

Result: [[1,2],[2,1]]
```

## Complexity Analysis

### Backtracking with Swapping (Provided):

- **Time Complexity:** O(n! × n)
  - Total permutations: n!
  - For n=3: 3! = 6 permutations
  - For n=4: 4! = 24 permutations
  - Copying each permutation to list: O(n)
  - Total: O(n! × n)

- **Space Complexity:** O(n)
  - Recursion depth: O(n)
  - Not counting the result list
  - In-place swapping, no extra visited array needed

### Why n! Permutations?

```
n=1: 1! = 1 permutation  [1]
n=2: 2! = 2 permutations [1,2], [2,1]
n=3: 3! = 6 permutations [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]
n=4: 4! = 24 permutations

Formula: n! = n × (n-1) × ... × 2 × 1
```

## Alternative Approaches

**1. Backtracking with Visited Array:**
```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, new ArrayList<>(), new boolean[nums.length], result);
    return result;
}

private void backtrack(int[] nums, List<Integer> current, 
                      boolean[] used, List<List<Integer>> result) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        
        current.add(nums[i]);
        used[i] = true;
        
        backtrack(nums, current, used, result);
        
        current.remove(current.size() - 1);
        used[i] = false;
    }
}
```
- Time: O(n! × n)
- Space: O(n) for visited array + O(n) for current list
- More intuitive but uses more space

**2. Iterative (Using Previous Permutations):**
```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>());
    
    for (int num : nums) {
        List<List<Integer>> newResult = new ArrayList<>();
        for (List<Integer> perm : result) {
            for (int i = 0; i <= perm.size(); i++) {
                List<Integer> newPerm = new ArrayList<>(perm);
                newPerm.add(i, num);
                newResult.add(newPerm);
            }
        }
        result = newResult;
    }
    
    return result;
}
```
- Time: O(n! × n)
- Space: O(n! × n) for intermediate results
- Builds permutations iteratively

Your swap-based backtracking is **optimal and elegant**!

## Edge Cases Handled

✓ **Single element:** [1] → [[1]]  
✓ **Two elements:** [1,2] → [[1,2],[2,1]]  
✓ **Negative numbers:** [-1,0,1] → All permutations  
✓ **Maximum size:** n=6 → 720 permutations  

## Pattern Recognition

This problem teaches **Backtracking**:

**Use backtracking when:**
- Need to explore all possibilities
- Build solution incrementally
- Can abandon branches early (pruning)
- Decision tree structure

**Backtracking template:**
```
1. Base case: solution complete?
2. Loop through choices
3. Make choice (modify state)
4. Recurse
5. Undo choice (backtrack)
```

**Similar problems:**
- Permutations II (with duplicates)
- Combinations
- Subsets
- N-Queens
- Sudoku Solver

## Key Insights

✓ **Swap-based approach** - Space efficient, no visited array  
✓ **Backtracking essential** - Swap back to try other choices  
✓ **In-place modification** - Only O(n) recursion space  
✓ **Base case at index==n** - Complete permutation formed  
✓ **Loop from index to end** - Try each element at current position  

## Why This Solution is Beautiful

1. **Space Efficient:**
   - No extra visited array
   - In-place swapping
   - Only recursion stack space

2. **Clean Logic:**
   - Simple swap operation
   - Clear base case
   - Natural recursion structure

3. **Complete Exploration:**
   - Generates all n! permutations
   - No duplicates (elements are distinct)
   - Systematic approach

## Common Mistakes to Avoid

❌ **Forgetting to swap back**
```java
// Wrong:
swap(nums, index, i);
backtrack(nums, index + 1, result);
// Missing swap back!
```

❌ **Not creating new list for result**
```java
// Wrong:
result.add(nums); // Adds reference, not copy!

// Right:
List<Integer> list = new ArrayList<>();
for (int num : nums) list.add(num);
result.add(list);
```

❌ **Starting loop from 0 instead of index**
```java
// Wrong:
for (int i = 0; i < nums.length; i++)

// Right:
for (int i = index; i < nums.length; i++)
```

## Tags

Array, Backtracking
