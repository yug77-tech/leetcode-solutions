# 78. Subsets

**Difficulty:** Medium

## Problem Description

Given an integer array `nums` of **unique** elements, return all possible **subsets** (the power set).

The solution set **must not contain duplicate subsets**. Return the solution in **any order**.

## Examples

**Example 1:**
```
Input: nums = [1,2,3]
Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
```

**Example 2:**
```
Input: nums = [0]
Output: [[],[0]]
```

## Constraints

- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`
- All the numbers of `nums` are **unique**.

## Solution Approach

This is a **classic backtracking problem!** The key insight: **For each element, we have two choices: include it or exclude it.**

### Backtracking with Choice Tree

**Power Set:**
- A set with n elements has 2^n subsets
- For [1,2,3]: 2^3 = 8 subsets
- Includes empty set [] and full set [1,2,3]

**Decision Tree for [1,2,3]:**

```
                        []
                    /        \
              Include 1    Exclude 1
                [1]            []
              /    \         /    \
          +2      -2      +2      -2
         [1,2]   [1]    [2]      []
         /  \    /  \   /  \     /  \
       +3  -3  +3  -3 +3  -3   +3  -3
      [1,2,3][1,2][1,3][1][2,3][2][3][]

Result: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
```

**Alternative View - Building Incrementally:**

```
Start: []
  Add to result: []
  
  Try adding 1:
    current = [1]
    Add to result: [1]
    
    Try adding 2:
      current = [1,2]
      Add to result: [1,2]
      
      Try adding 3:
        current = [1,2,3]
        Add to result: [1,2,3]
        Remove 3
      
      Remove 2
    
    Try adding 3 (skip 2):
      current = [1,3]
      Add to result: [1,3]
      Remove 3
    
    Remove 1
  
  Try adding 2 (skip 1):
    current = [2]
    Add to result: [2]
    
    Try adding 3:
      current = [2,3]
      Add to result: [2,3]
      Remove 3
    
    Remove 2
  
  Try adding 3 (skip 1,2):
    current = [3]
    Add to result: [3]
    Remove 3

Result: 8 subsets
```

### Algorithm Steps

**Key Difference from Permutations:**
- Add current subset at EVERY step (not just base case)
- Each subset is valid, even incomplete ones
- Use index to avoid duplicates (don't revisit earlier elements)

**Process:**
1. Add current subset to result (including empty set initially)
2. For each remaining element (from index to end):
   - Include element in current subset
   - Recursively explore with next index
   - Exclude element (backtrack by removing)

### Detailed Walkthrough

**Input: nums = [1,2,3]**

```
Call: backtrack(nums, index=0, current=[], result)
  Add [] to result ✓
  Result: [[]]
  
  Loop i=0 (element 1):
    current.add(1) → [1]
    Call: backtrack(nums, index=1, current=[1], result)
      Add [1] to result ✓
      Result: [[],[1]]
      
      Loop i=1 (element 2):
        current.add(2) → [1,2]
        Call: backtrack(nums, index=2, current=[1,2], result)
          Add [1,2] to result ✓
          Result: [[],[1],[1,2]]
          
          Loop i=2 (element 3):
            current.add(3) → [1,2,3]
            Call: backtrack(nums, index=3, current=[1,2,3], result)
              Add [1,2,3] to result ✓
              Result: [[],[1],[1,2],[1,2,3]]
              
              Loop: i=3 >= 3, no iterations
            Return
            current.remove(3) → [1,2]
        Return
        current.remove(2) → [1]
      
      Loop i=2 (element 3):
        current.add(3) → [1,3]
        Call: backtrack(nums, index=3, current=[1,3], result)
          Add [1,3] to result ✓
          Result: [[],[1],[1,2],[1,2,3],[1,3]]
          
          Loop: i=3 >= 3, no iterations
        Return
        current.remove(3) → [1]
    Return
    current.remove(1) → []
  
  Loop i=1 (element 2):
    current.add(2) → [2]
    Call: backtrack(nums, index=2, current=[2], result)
      Add [2] to result ✓
      Result: [[],[1],[1,2],[1,2,3],[1,3],[2]]
      
      Loop i=2 (element 3):
        current.add(3) → [2,3]
        Call: backtrack(nums, index=3, current=[2,3], result)
          Add [2,3] to result ✓
          Result: [[],[1],[1,2],[1,2,3],[1,3],[2],[2,3]]
          
          Loop: i=3 >= 3, no iterations
        Return
        current.remove(3) → [2]
    Return
    current.remove(2) → []
  
  Loop i=2 (element 3):
    current.add(3) → [3]
    Call: backtrack(nums, index=3, current=[3], result)
      Add [3] to result ✓
      Result: [[],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]
      
      Loop: i=3 >= 3, no iterations
    Return
    current.remove(3) → []

Final Result: [[],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]
```

### Step-by-Step for [1,2]

```
backtrack(nums=[1,2], index=0, current=[], result)
  Add [] → result = [[]]
  
  i=0: current=[1]
    backtrack(index=1, current=[1])
      Add [1] → result = [[],[1]]
      
      i=1: current=[1,2]
        backtrack(index=2, current=[1,2])
          Add [1,2] → result = [[],[1],[1,2]]
          Loop done (i=2 >= 2)
        Remove 2 → [1]
    Remove 1 → []
  
  i=1: current=[2]
    backtrack(index=2, current=[2])
      Add [2] → result = [[],[1],[1,2],[2]]
      Loop done (i=2 >= 2)
    Remove 2 → []

Final: [[],[1],[1,2],[2]]
```

### Why Add at Beginning of Function?

**Critical Design Choice:**
```java
// Add current subset FIRST
result.add(new ArrayList<>(current));

// Then explore more elements
for (int i = index; i < nums.length; i++) {
    ...
}
```

**Why?**
- Every subset is valid (even partial ones)
- Empty set [] is valid
- Must capture state at every recursion level
- Different from permutations (only complete ones valid)

### Why Start Loop at Index (Not 0)?

**Prevents duplicates:**
```
If we started at 0, we'd get:
[1,2] and [2,1] - these are same subset!

Starting at index ensures:
- Once we skip element, never revisit
- Maintains increasing order
- No duplicate subsets
```

**Example:**
```
At index=1 (already processed 1):
  Can add: 2, 3
  Can't add: 1 (already considered)

This ensures [1,2] is generated but not [2,1]
```

## Complexity Analysis

### Backtracking Approach (Provided):

- **Time Complexity:** O(n × 2^n)
  - Total subsets: 2^n
  - For n=3: 2^3 = 8 subsets
  - Copying each subset: O(n)
  - Total: O(n × 2^n)

- **Space Complexity:** O(n)
  - Recursion depth: O(n)
  - Current list: O(n)
  - Not counting output list
  - Each recursion level adds one element

### Why 2^n Subsets?

```
Each element has 2 choices: include or exclude

n=1: 2^1 = 2 → [], [1]
n=2: 2^2 = 4 → [], [1], [2], [1,2]
n=3: 2^3 = 8 → [], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]

Formula: 2^n
```

## Alternative Approaches

**1. Iterative (Build from Previous):**
```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>());
    
    for (int num : nums) {
        int size = result.size();
        for (int i = 0; i < size; i++) {
            List<Integer> subset = new ArrayList<>(result.get(i));
            subset.add(num);
            result.add(subset);
        }
    }
    
    return result;
}
```
- Time: O(n × 2^n)
- Iteratively builds subsets
- For each element, copy existing subsets and add element

**2. Bit Manipulation:**
```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int n = nums.length;
    
    for (int mask = 0; mask < (1 << n); mask++) {
        List<Integer> subset = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {
                subset.add(nums[i]);
            }
        }
        result.add(subset);
    }
    
    return result;
}
```
- Time: O(n × 2^n)
- Each number from 0 to 2^n-1 represents a subset
- Bit i = 1 means include nums[i]

Your backtracking is **most intuitive and elegant**!

## Edge Cases Handled

✓ **Empty initially:** [] is first subset  
✓ **Single element:** [1] → [[],[1]]  
✓ **Two elements:** [1,2] → [[],[1],[2],[1,2]]  
✓ **Negative numbers:** Works fine  
✓ **Maximum size:** n=10 → 1024 subsets  

## Pattern Recognition

This problem teaches **Backtracking for Combinations**:

**Subsets vs Permutations:**

| Aspect | Subsets | Permutations |
|--------|---------|--------------|
| Order matters? | No | Yes |
| [1,2] vs [2,1] | Same | Different |
| Add to result | Every step | Base case only |
| Total count | 2^n | n! |
| Loop start | index (avoid revisit) | 0 (try all) |

**Use subsets pattern when:**
- Generate all combinations
- Order doesn't matter
- Include/exclude decision for each element

**Similar problems:**
- Subsets II (with duplicates)
- Combination Sum
- Letter Combinations
- Generate Parentheses

## Key Insights

✓ **Add at every recursion level** - All subsets valid  
✓ **Start loop at index** - Avoid duplicates  
✓ **Include/exclude pattern** - Binary choice tree  
✓ **2^n total subsets** - Power set size  
✓ **Empty set included** - Added first (base case)  

## Backtracking Template for Subsets

```java
void backtrack(index, current) {
    // Add current subset (every state is valid)
    result.add(copy(current));
    
    // Try including each remaining element
    for (i = index to end) {
        current.add(nums[i]);      // Include
        backtrack(i + 1, current); // Explore
        current.remove_last();      // Exclude (backtrack)
    }
}
```

## Why This Solution is Beautiful

1. **Complete Exploration:**
   - Generates all 2^n subsets
   - No duplicates
   - Systematic approach

2. **Simple Logic:**
   - One add, one loop
   - Clear recursion pattern
   - Easy to understand

3. **Space Efficient:**
   - Only O(n) extra space
   - Reuses current list
   - No visited array needed

## Common Mistakes to Avoid

❌ **Forgetting to add at beginning**
```java
// Wrong:
if (index == nums.length) {
    result.add(new ArrayList<>(current));
}
// Misses partial subsets!

// Right:
result.add(new ArrayList<>(current));
// Then continue exploring
```

❌ **Starting loop from 0**
```java
// Wrong:
for (int i = 0; i < nums.length; i++)
// Creates duplicates: [1,2] and [2,1]

// Right:
for (int i = index; i < nums.length; i++)
```

❌ **Not creating new list**
```java
// Wrong:
result.add(current); // Reference!

// Right:
result.add(new ArrayList<>(current)); // Copy
```

## Tags

Array, Backtracking, Bit Manipulation
