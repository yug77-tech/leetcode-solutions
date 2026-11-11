# 39. Combination Sum

**Difficulty:** Medium

## Problem Description

Given an array of **distinct** integers `candidates` and a target integer `target`, return a list of all **unique combinations** of `candidates` where the chosen numbers sum to `target`. You may return the combinations in **any order**.

The **same** number may be chosen from `candidates` an **unlimited number of times**. Two combinations are unique if the **frequency** of at least one of the chosen numbers is different.

The test cases are generated such that the number of unique combinations that sum up to `target` is less than 150 combinations for the given input.

## Examples

**Example 1:**
```
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
7 is a candidate, and 7 = 7.
These are the only two combinations.
```

**Example 2:**
```
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
```

**Example 3:**
```
Input: candidates = [2], target = 1
Output: []
```

## Constraints

- `1 <= candidates.length <= 30`
- `2 <= candidates[i] <= 40`
- All elements of `candidates` are **distinct**.
- `1 <= target <= 40`

## Solution Approach

This is a **classic backtracking problem with unlimited reuse!** The key insight: **For each number, decide whether to use it again or move to next number.**

### Backtracking with Reuse

**The Challenge:**
- Can use same number multiple times
- Need all unique combinations
- Avoid duplicates (e.g., [2,3] and [3,2] are same)

**The Solution:**
- Backtracking to explore all possibilities
- Index parameter to avoid duplicates
- Two choices at each step:
  1. Use current number again (stay at same index)
  2. Move to next number (increment index)

**Decision Tree for candidates=[2,3,6,7], target=7:**

```
                    []
                    ↓
            ┌───────┴───────┐
         Use 2            Skip 2
           [2]              []
      ┌─────┴─────┐         ↓
   Use 2      Skip 2    Use 3...
    [2,2]      [2]
  ┌───┴───┐   ┌─┴─┐
Use 2  Skip [2,3]
[2,2,2]     ↓
  ↓      target=2
target=1  remaining
invalid   ┌──┴──┐
       Use 2  Skip
       [2,2,2,2] [2,3]
       invalid   ↓
               Use 3
              [2,2,3]
                ↓
            target=0
            Found! ✓
```

### Algorithm Steps

**Base Cases:**
1. If target == 0: found valid combination, add to result
2. If target < 0: exceeded target, invalid path
3. If index == length: no more candidates to try

**Recursive Cases:**
For each candidate starting from index:
1. **Include current candidate:**
   - Add to current combination
   - Recursively search with same index (allow reuse)
   - Same target - current value
2. **Exclude current candidate:**
   - Remove from current combination
   - Move to next candidate (index + 1)

### Visual Example

**Input: candidates = [2,3,6,7], target = 7**

```
Call: backtrack(target=7, index=0, current=[])

Include candidates[0]=2:
  current = [2]
  Call: backtrack(target=5, index=0, current=[2])
  
    Include candidates[0]=2:
      current = [2,2]
      Call: backtrack(target=3, index=0, current=[2,2])
      
        Include candidates[0]=2:
          current = [2,2,2]
          Call: backtrack(target=1, index=0, current=[2,2,2])
          
            Include candidates[0]=2:
              current = [2,2,2,2]
              Call: backtrack(target=-1, index=0, current=[2,2,2,2])
              target < 0 → return (invalid)
            Remove 2: current = [2,2,2]
            
            Exclude candidates[0], try next:
              Call: backtrack(target=1, index=1, current=[2,2,2])
              target=1, but candidates[1]=3 > 1
              Eventually returns without finding
          
        Remove 2: current = [2,2]
        
        Exclude candidates[0], try next:
          Call: backtrack(target=3, index=1, current=[2,2])
          
            Include candidates[1]=3:
              current = [2,2,3]
              Call: backtrack(target=0, index=1, current=[2,2,3])
              target == 0 → Add [2,2,3] to result ✓
            Remove 3: current = [2,2]
            
            Exclude candidates[1], try next...

... (continue exploring other branches)

Exclude candidates[0], try next:
  Call: backtrack(target=7, index=1, current=[])
  
    Eventually finds [7] when index=3
    
Final result: [[2,2,3], [7]]
```

### Key Decisions

**1. Allow Reuse:**
```java
// Stay at same index to allow reusing current number
backtrack(candidates, target - candidates[index], index, current, ans);
```

**2. Avoid Duplicates:**
```java
// Only move forward, never backward
// This ensures [2,3] is found but not [3,2]
backtrack(candidates, target, index + 1, current, ans);
```

**3. Pruning:**
```java
// Stop early if target becomes negative
if (target < 0) return;
```

## Complexity Analysis

### Backtracking with Reuse (Provided):

- **Time Complexity:** O(N^(T/M))
  - N = number of candidates
  - T = target value
  - M = minimal value among candidates
  - Each level can choose any of N candidates
  - Maximum depth = T/M (using smallest repeatedly)
  - Approximately O(N^(T/M))

- **Space Complexity:** O(T/M)
  - Recursion depth: O(T/M) in worst case
  - Current combination: O(T/M) elements max
  - Not counting result space

## Alternative Approaches

**1. For Loop Backtracking:**
```java
private void backtrack(int[] candidates, int target, int start,
                      List<Integer> current, List<List<Integer>> result) {
    if (target == 0) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    for (int i = start; i < candidates.length; i++) {
        if (candidates[i] > target) continue;  // Pruning
        
        current.add(candidates[i]);
        backtrack(candidates, target - candidates[i], i, current, result);
        current.remove(current.size() - 1);
    }
}
```
- Cleaner loop structure
- Same logic, different style
- Many prefer this approach

**2. DP Approach (More Complex):**
```java
// For each sum from 1 to target
// Build combinations using previous results
// More complex, less intuitive
```

Your recursive backtracking is **clean and elegant**!

## Edge Cases Handled

✓ **No solution:** target=1, candidates=[2] → []  
✓ **Single number:** target=7, candidates=[7] → [[7]]  
✓ **Multiple uses:** target=8, candidates=[2] → [[2,2,2,2]]  
✓ **Mix of sizes:** Various combinations possible  
✓ **Large target:** Recursion handles depth  

## Pattern Recognition

This problem teaches **Backtracking with Unlimited Reuse**:

**Use this pattern when:**
- Need all combinations (not just count)
- Elements can be reused unlimited times
- Need to avoid duplicate combinations
- Sum/product equals target

**Key techniques:**
- Include/exclude decision tree
- Stay at same index for reuse
- Move forward to avoid duplicates
- Prune invalid branches early

**Similar problems:**
- Combination Sum II (with duplicates)
- Combination Sum III (fixed count)
- Coin Change (count ways)
- Perfect Squares

## Key Insights

✓ **Stay at index** - Allows reuse of same number  
✓ **Move forward only** - Avoids duplicate combinations  
✓ **Backtrack pattern** - Include then exclude  
✓ **Early termination** - target < 0 prunes branch  
✓ **Create new list** - When adding to result  
✓ **Index parameter** - Controls which candidates to consider  

## Why This Avoids Duplicates

**Without index:**
```
Could generate both [2,3] and [3,2]
```

**With index:**
```
When we choose 2, we can pick 2 or 3 next
When we choose 3, we can only pick 3 next (not 2)
This ensures ascending order, no duplicates!
```

## Common Mistakes to Avoid

❌ **Not creating new list when adding to result**
```java
// Wrong: adds reference, all point to same list
if (target == 0) {
    ans.add(current);
}

// Right: creates independent copy
if (target == 0) {
    ans.add(new ArrayList<>(current));
}
```

❌ **Not staying at same index for reuse**
```java
// Wrong: can't reuse same number
backtrack(candidates, target - candidates[index], index + 1, current, ans);

// Right: stay at index to allow reuse
backtrack(candidates, target - candidates[index], index, current, ans);
```

❌ **Forgetting to remove after recursive call**
```java
// Wrong: doesn't backtrack properly
current.add(candidates[index]);
backtrack(...);
// Missing: current.remove(current.size() - 1);
```

## Tags

Array, Backtracking
