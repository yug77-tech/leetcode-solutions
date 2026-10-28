# 1122. Relative Sort Array

**Difficulty:** Easy

## Problem Description

Given two arrays `arr1` and `arr2`, the elements of `arr2` are **distinct**, and all elements in `arr2` are also in `arr1`.

Sort the elements of `arr1` such that the relative ordering of items in `arr1` are the same as in `arr2`. Elements that **do not appear** in `arr2` should be placed at the **end** of `arr1` in **ascending order**.

## Examples

**Example 1:**
```
Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
Output: [2,2,2,1,4,3,3,9,6,7,19]
Explanation:
- 2 appears first in arr2, so all 2's come first (2,2,2)
- Then 1 appears in arr2 (1)
- Then 4 appears in arr2 (4)
- Then 3 appears in arr2, so all 3's (3,3)
- Then 9 appears in arr2 (9)
- Then 6 appears in arr2 (6)
- Finally, elements not in arr2 in ascending order (7,19)
```

**Example 2:**
```
Input: arr1 = [28,6,22,8,44,17], arr2 = [22,28,8,6]
Output: [22,28,8,6,17,44]
Explanation:
- Follow arr2 order: 22, 28, 8, 6
- Remaining elements in ascending order: 17, 44
```

## Constraints

- `1 <= arr1.length, arr2.length <= 1000`
- `0 <= arr1[i], arr2[i] <= 1000`
- All the elements of `arr2` are **distinct**.
- Each `arr2[i]` is in `arr1`.

## Solution Approach

The key insight: **Use counting sort with custom ordering defined by arr2!**

### Frequency-Based Custom Sort

**Three-Step Process:**

**Step 1: Count Frequencies**
- Use array to count occurrences of each element in arr1
- Array index = element value, array value = frequency

**Step 2: Place Elements Following arr2 Order**
- Iterate through arr2 in order
- For each element in arr2, place all its occurrences in result
- Decrement frequency counter

**Step 3: Place Remaining Elements in Ascending Order**
- Iterate through frequency array from 0 to 1000
- Place any remaining elements (not in arr2) in ascending order

### Visual Example

**Input:** 
```
arr1 = [2,3,1,3,2,4,6,7,9,2,19]
arr2 = [2,1,4,3,9,6]
```

**Step 1: Build Frequency Array**
```
Count each element in arr1:

freq[1] = 1    (1 appears 1 time)
freq[2] = 3    (2 appears 3 times)
freq[3] = 2    (3 appears 2 times)
freq[4] = 1    (4 appears 1 time)
freq[6] = 1    (6 appears 1 time)
freq[7] = 1    (7 appears 1 time)
freq[9] = 1    (9 appears 1 time)
freq[19] = 1   (19 appears 1 time)
```

**Step 2: Place Elements Following arr2 Order**
```
Process arr2 = [2,1,4,3,9,6]:

num=2: freq[2]=3 → Place 2,2,2
Result: [2,2,2]
freq[2]=0

num=1: freq[1]=1 → Place 1
Result: [2,2,2,1]
freq[1]=0

num=4: freq[4]=1 → Place 4
Result: [2,2,2,1,4]
freq[4]=0

num=3: freq[3]=2 → Place 3,3
Result: [2,2,2,1,4,3,3]
freq[3]=0

num=9: freq[9]=1 → Place 9
Result: [2,2,2,1,4,3,3,9]
freq[9]=0

num=6: freq[6]=1 → Place 6
Result: [2,2,2,1,4,3,3,9,6]
freq[6]=0
```

**Step 3: Place Remaining Elements**
```
Scan freq array for non-zero counts:

i=0: freq[0]=0 → Skip
i=1: freq[1]=0 → Skip (already placed)
...
i=7: freq[7]=1 → Place 7
Result: [2,2,2,1,4,3,3,9,6,7]
...
i=19: freq[19]=1 → Place 19
Result: [2,2,2,1,4,3,3,9,6,7,19]

Final: [2,2,2,1,4,3,3,9,6,7,19] ✓
```

### Step-by-Step Detailed Walkthrough

**Input:** arr1 = [28,6,22,8,44,17], arr2 = [22,28,8,6]

```
Step 1: Count frequencies
freq[6] = 1
freq[8] = 1
freq[17] = 1
freq[22] = 1
freq[28] = 1
freq[44] = 1

Step 2: Process arr2 = [22,28,8,6]

index = 0

num=22: freq[22]=1
  arr1[0] = 22
  freq[22] = 0
  index = 1

num=28: freq[28]=1
  arr1[1] = 28
  freq[28] = 0
  index = 2

num=8: freq[8]=1
  arr1[2] = 8
  freq[8] = 0
  index = 3

num=6: freq[6]=1
  arr1[3] = 6
  freq[6] = 0
  index = 4

After arr2: [22, 28, 8, 6, ?, ?]

Step 3: Scan freq for remaining elements

i=17: freq[17]=1
  arr1[4] = 17
  freq[17] = 0
  index = 5

i=44: freq[44]=1
  arr1[5] = 44
  freq[44] = 0
  index = 6

Final: [22, 28, 8, 6, 17, 44] ✓
```

### Why This Works

**Key Properties:**

1. **Counting Sort Foundation**
   - Elements are in range [0, 1000]
   - Small range allows frequency array
   - O(n) time for counting

2. **Custom Ordering via arr2**
   - arr2 defines priority order
   - Process arr2 sequentially to maintain order
   - Elements not in arr2 come last

3. **Automatic Ascending Order for Remaining**
   - Frequency array indexed 0 to 1000
   - Scanning left to right gives ascending order
   - No explicit sorting needed!

### Why Use freq[1001]?

```java
int[] freq = new int[1001];
```

- Constraint: 0 <= arr1[i] <= 1000
- Need indices from 0 to 1000 inclusive
- Array size must be 1001 (indices 0-1000)

## Complexity Analysis

### Counting Sort Approach (Provided):

- **Time Complexity:** O(n + m + k)
  - n = length of arr1 (count frequencies)
  - m = length of arr2 (place in order)
  - k = 1001 (scan frequency array)
  - Simplifies to O(n + m) since k is constant
  - Much faster than comparison-based sorting!

- **Space Complexity:** O(k) = O(1001) = O(1)
  - Fixed size frequency array (1001 elements)
  - Constant space regardless of input size
  - In-place modification of arr1

### Alternative Approaches

**1. Custom Comparator (Slower):**
```java
public int[] relativeSortArray(int[] arr1, int[] arr2) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < arr2.length; i++) {
        map.put(arr2[i], i);
    }
    
    return Arrays.stream(arr1)
        .boxed()
        .sorted((a, b) -> {
            if (map.containsKey(a) && map.containsKey(b)) {
                return map.get(a) - map.get(b);
            } else if (map.containsKey(a)) {
                return -1;
            } else if (map.containsKey(b)) {
                return 1;
            } else {
                return a - b;
            }
        })
        .mapToInt(Integer::intValue)
        .toArray();
}
```
- Time: O(n log n) - slower due to sorting
- Space: O(n + m) - HashMap + boxed array
- More complex logic

**2. Manual Comparison Sort:**
- Time: O(n²) with bubble/selection sort
- Not competitive

Your counting sort is **optimal**: O(n) time, O(1) space! 🎯

## Edge Cases Handled

✓ **All elements in arr2:** arr1=[2,1], arr2=[2,1] → [2,1]  
✓ **No elements in arr2:** arr1=[5,6], arr2=[1,2] → [5,6] (ascending)  
✓ **Duplicates:** arr1=[2,2,2], arr2=[2] → [2,2,2]  
✓ **Single element:** arr1=[1], arr2=[1] → [1]  
✓ **All unique:** Works fine  
✓ **Large numbers:** Elements up to 1000 handled  

## Why This Pattern?

This problem teaches **Counting Sort with Custom Order**:

**Use counting sort when:**
- Elements in small fixed range (0-1000)
- Need O(n) sorting
- Can use array as hash map

**Custom ordering when:**
- Priority defined by another array
- Need specific element order

**Similar problems:**
- Sort array by increasing frequency
- Custom sort string
- Rank transform of array
- Sort integers by power value

## Key Insights

✓ **Frequency array = counting sort** - Classic pattern  
✓ **arr2 defines order** - Process sequentially  
✓ **Remaining in ascending** - Free via freq array scan  
✓ **O(n) time** - No comparison-based sorting needed  
✓ **Small range constraint** - Enables frequency array  

## Pattern Recognition

**Counting Sort Prerequisites:**
1. Elements in small, known range
2. Non-negative integers (or can be mapped to them)
3. Need O(n) time complexity

**When you see:**
- "0 <= arr[i] <= 1000" → Think frequency array!
- "Custom ordering" → Process in desired order
- "Sort in linear time" → Counting sort

## Tags

Array, Hash Table, Sorting, Counting Sort
