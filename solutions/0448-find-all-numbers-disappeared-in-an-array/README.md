# 448. Find All Numbers Disappeared in an Array

**Difficulty:** Easy

## Problem Description

Given an array `nums` of `n` integers where `nums[i]` is in the range `[1, n]`, return an array of all the integers in the range `[1, n]` that **do not appear** in `nums`.

## Examples

**Example 1:**
```
Input: nums = [4,3,2,7,8,2,3,1]
Output: [5,6]
Explanation:
Numbers 1-8 should appear, but 5 and 6 are missing.
```

**Example 2:**
```
Input: nums = [1,1]
Output: [2]
Explanation:
Numbers 1-2 should appear, but 2 is missing.
```

## Constraints

- `n == nums.length`
- `1 <= n <= 10^5`
- `1 <= nums[i] <= n`

## Follow-up

Could you do it without extra space and in `O(n)` runtime? You may assume the returned list does not count as extra space.

## Solution Approach

This is a **brilliant in-place marking problem!** The key insight: **Use array indices as a hash map by marking visited numbers with negative signs!**

### In-Place Marking with Negation

**The Challenge:**
- Need O(n) time
- Need O(1) extra space (not counting output)
- Can't use extra HashSet

**The Solution:**
- Use array itself as marker!
- Mark visited numbers by negating values at their indices
- Positive values indicate missing numbers

**Key Observations:**
1. `nums[i]` is in range [1, n]
2. Array has exactly n positions (indices 0 to n-1)
3. Number k should be at index (k-1)
4. Can use sign to mark presence

### Algorithm Steps

**Phase 1: Mark present numbers**
- For each number `num` in array:
  - Find its index: `index = abs(num) - 1`
  - Mark as visited: `nums[index] = -abs(nums[index])`

**Phase 2: Find missing numbers**
- For each index `i`:
  - If `nums[i] > 0` (positive):
    - Number `i+1` is missing

### Visual Example

**Input: [4,3,2,7,8,2,3,1]**

```
Initial array (1-indexed for clarity):
Index:  0  1  2  3  4  5  6  7
Value:  4  3  2  7  8  2  3  1

Phase 1: Mark present numbers

Process nums[0] = 4:
  index = |4| - 1 = 3
  Mark nums[3]: 7 → -7
  [4, 3, 2, -7, 8, 2, 3, 1]

Process nums[1] = 3:
  index = |3| - 1 = 2
  Mark nums[2]: 2 → -2
  [4, 3, -2, -7, 8, 2, 3, 1]

Process nums[2] = -2 (already marked, use abs):
  index = |-2| - 1 = 1
  Mark nums[1]: 3 → -3
  [4, -3, -2, -7, 8, 2, 3, 1]

Process nums[3] = -7:
  index = |-7| - 1 = 6
  Mark nums[6]: 3 → -3
  [4, -3, -2, -7, 8, 2, -3, 1]

Process nums[4] = 8:
  index = |8| - 1 = 7
  Mark nums[7]: 1 → -1
  [4, -3, -2, -7, 8, 2, -3, -1]

Process nums[5] = 2:
  index = |2| - 1 = 1
  nums[1] already negative, skip
  [4, -3, -2, -7, 8, 2, -3, -1]

Process nums[6] = -3:
  index = |-3| - 1 = 2
  nums[2] already negative, skip
  [4, -3, -2, -7, 8, 2, -3, -1]

Process nums[7] = -1:
  index = |-1| - 1 = 0
  Mark nums[0]: 4 → -4
  [-4, -3, -2, -7, 8, 2, -3, -1]

Phase 2: Find missing numbers

Index 0: nums[0] = -4 (negative) → 1 present
Index 1: nums[1] = -3 (negative) → 2 present
Index 2: nums[2] = -2 (negative) → 3 present
Index 3: nums[3] = -7 (negative) → 4 present
Index 4: nums[4] = 8 (positive) → 5 MISSING ✓
Index 5: nums[5] = 2 (positive) → 6 MISSING ✓
Index 6: nums[6] = -3 (negative) → 7 present
Index 7: nums[7] = -1 (negative) → 8 present

Result: [5, 6]
```

### Detailed Walkthrough

**Input: [4,3,2,7,8,2,3,1]**

```
Step 1: Mark phase

i=0, nums[0]=4:
  index = 4-1 = 3
  nums[3] = 7 > 0 → mark negative
  nums = [4,3,2,-7,8,2,3,1]

i=1, nums[1]=3:
  index = 3-1 = 2
  nums[2] = 2 > 0 → mark negative
  nums = [4,3,-2,-7,8,2,3,1]

i=2, nums[2]=-2:
  index = |-2|-1 = 1
  nums[1] = 3 > 0 → mark negative
  nums = [4,-3,-2,-7,8,2,3,1]

i=3, nums[3]=-7:
  index = |-7|-1 = 6
  nums[6] = 3 > 0 → mark negative
  nums = [4,-3,-2,-7,8,2,-3,1]

i=4, nums[4]=8:
  index = 8-1 = 7
  nums[7] = 1 > 0 → mark negative
  nums = [4,-3,-2,-7,8,2,-3,-1]

i=5, nums[5]=2:
  index = 2-1 = 1
  nums[1] = -3 < 0 → already marked, skip
  nums = [4,-3,-2,-7,8,2,-3,-1]

i=6, nums[6]=-3:
  index = |-3|-1 = 2
  nums[2] = -2 < 0 → already marked, skip
  nums = [4,-3,-2,-7,8,2,-3,-1]

i=7, nums[7]=-1:
  index = |-1|-1 = 0
  nums[0] = 4 > 0 → mark negative
  nums = [-4,-3,-2,-7,8,2,-3,-1]

Step 2: Find missing

i=0: nums[0]=-4 < 0 → skip
i=1: nums[1]=-3 < 0 → skip
i=2: nums[2]=-2 < 0 → skip
i=3: nums[3]=-7 < 0 → skip
i=4: nums[4]=8 > 0 → add i+1=5
i=5: nums[5]=2 > 0 → add i+1=6
i=6: nums[6]=-3 < 0 → skip
i=7: nums[7]=-1 < 0 → skip

Result: [5, 6]
```

### Why This Works

**Mapping:**
- Number k maps to index (k-1)
- Index i represents number (i+1)

**Marking:**
- When we see number k, mark index (k-1) negative
- Negative means "this number exists"
- Positive means "this number missing"

**Why use Math.abs?**
- During marking, we may encounter already-negative values
- Need absolute value to find correct index
- Example: nums[2] = -2, abs(-2) = 2, index = 1

## Complexity Analysis

### In-Place Marking (Provided):

- **Time Complexity:** O(n)
  - First loop: O(n) to mark all present numbers
  - Second loop: O(n) to find missing numbers
  - Total: O(n)

- **Space Complexity:** O(1)
  - No extra space (except output list)
  - Modifies input array in-place
  - Output list doesn't count as extra space

## Alternative Approaches

**1. HashSet (Extra Space):**
```java
public List<Integer> findDisappearedNumbers(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
        set.add(num);
    }
    
    List<Integer> result = new ArrayList<>();
    for (int i = 1; i <= nums.length; i++) {
        if (!set.contains(i)) {
            result.add(i);
        }
    }
    
    return result;
}
```
- Time: O(n)
- Space: O(n) - violates follow-up requirement
- Simpler but uses extra space

**2. Cyclic Sort:**
```java
public List<Integer> findDisappearedNumbers(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        int correctIdx = nums[i] - 1;
        if (nums[i] != nums[correctIdx]) {
            // Swap to correct position
            int temp = nums[i];
            nums[i] = nums[correctIdx];
            nums[correctIdx] = temp;
        } else {
            i++;
        }
    }
    
    List<Integer> result = new ArrayList<>();
    for (i = 0; i < nums.length; i++) {
        if (nums[i] != i + 1) {
            result.add(i + 1);
        }
    }
    
    return result;
}
```
- Time: O(n)
- Space: O(1)
- Places each number at its correct index

Your negation approach is **most elegant**!

## Edge Cases Handled

✓ **All numbers present:** [1,2,3] → []  
✓ **All missing except one:** [1,1,1] → [2,3]  
✓ **Duplicates:** [2,2] → [1]  
✓ **Single element:** [1] → []  
✓ **Already in order:** [1,2,3,4] → []  

## Pattern Recognition

This problem teaches **In-Place Index Marking**:

**Use this pattern when:**
- Array values in range [1, n]
- Array length is n
- Need O(1) space solution
- Can modify input array

**Key techniques:**
- Use sign as marker
- Map value to index
- Use Math.abs to handle marked values

**Similar problems:**
- Find the Duplicate Number
- First Missing Positive
- Set Mismatch
- Find All Duplicates in an Array

## Key Insights

✓ **Array as hash map** - Use indices to mark presence  
✓ **Negative as marker** - Doesn't change absolute value  
✓ **Value → Index mapping** - num maps to index (num-1)  
✓ **Index → Value mapping** - index i represents number (i+1)  
✓ **Two-pass algorithm** - Mark, then find  
✓ **O(1) space** - Modifies in-place  

## Why Negation Works

**Requirements:**
1. Mark numbers as visited
2. Still able to read original values
3. No extra space

**Solution:**
- Negative sign is extra bit of information
- Can extract original value with abs()
- Doesn't interfere with indexing
- Perfect for marking without extra space!

## Common Mistakes to Avoid

❌ **Forgetting Math.abs**
```java
// Wrong: may access wrong index
int index = nums[i] - 1;

// Right: handle already-negative values
int index = Math.abs(nums[i]) - 1;
```

❌ **Checking wrong condition**
```java
// Wrong: double negation
if (nums[index] < 0) {
    nums[index] = -nums[index];
}

// Right: only negate if positive
if (nums[index] > 0) {
    nums[index] = -nums[index];
}
```

❌ **Wrong index mapping**
```java
// Wrong: off by one
int index = nums[i];

// Right: array is 0-indexed, numbers are 1-indexed
int index = Math.abs(nums[i]) - 1;
```

## Tags

Array, Hash Table
