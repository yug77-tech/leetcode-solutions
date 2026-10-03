# 1441. Build an Array with Stack Operations

**Difficulty:** Medium

## Problem Description

You are given an integer array `target` and an integer `n`.

You have an empty stack with the two following operations:

- **"Push"**: pushes an integer to the top of the stack.
- **"Pop"**: removes the integer on the top of the stack.

You also have a stream of the integers in the range `[1, n]`.

Use the two stack operations to make the numbers in the stack (from the bottom to the top) equal to target. You should follow the following rules:

- If the stream of the integers is not empty, pick the next integer from the stream and push it to the top of the stack.
- If the stack is not empty, pop the integer at the top of the stack.
- If, at any moment, the elements in the stack (from the bottom to the top) are equal to target, do not read new integers from the stream and do not do more operations on the stack.

Return the stack operations needed to build target following the mentioned rules. If there are multiple valid answers, return any of them.

## Examples

**Example 1:**
```
Input: target = [1,3], n = 3
Output: ["Push","Push","Pop","Push"]
Explanation: Initially the stack is empty. Read 1 from the stream and push it to the stack. Stack: [1].
Read 2 from the stream and push it to the stack. Stack: [1,2].
Pop the integer on the top of the stack. Stack: [1].
Read 3 from the stream and push it to the stack. Stack: [1,3].
```

**Example 2:**
```
Input: target = [1,2,3], n = 3
Output: ["Push","Push","Push"]
Explanation: Initially the stack is empty. Read from the stream and push to the stack until stack is [1,2,3].
```

**Example 3:**
```
Input: target = [1,2], n = 4
Output: ["Push","Push"]
Explanation: Initially the stack is empty. Read 1 from the stream and push to the stack. Stack: [1].
Read 2 from the stream and push to the stack. Stack: [1,2].
Since the stack (from bottom to top) is equal to target, we stop the operations.
```

## Constraints

- `1 <= target.length <= 100`
- `1 <= n <= 100`
- `1 <= target[i] <= n`
- `target` is strictly increasing

## Solution Approach

The key insight is that we iterate through the stream `[1, n]` and:
1. Always **Push** the current number
2. If the current number matches the next target element, move to the next target
3. If the current number doesn't match, immediately **Pop** it
4. Stop when we've built the entire target array

**Algorithm:**
1. Use pointer `j` to track position in target array
2. Iterate `i` from 1 to n
3. Add "Push" operation
4. If `i == target[j]`:
   - Increment j (matched target element)
   - If j reaches target.length, we're done
5. Else:
   - Add "Pop" operation (we don't want this number)

## Optimizations

### Optimized Version:
- Pre-allocates ArrayList with initial capacity `2 * target.length`
- Checks `j == target.length` immediately after incrementing to break early
- Avoids unnecessary ArrayList resizing

### Non-Optimized Version:
- Uses default ArrayList capacity
- Checks `j == target.length` at the end of each iteration

The optimized version provides:
- Better memory allocation (fewer ArrayList resizing operations)
- Slightly cleaner early exit logic

## Complexity Analysis

- **Time Complexity:** O(n) - In worst case, iterate through all numbers from 1 to n
- **Space Complexity:** O(target.length) - Result list stores at most 2 operations per target element

## Tags

Array, Stack, Simulation
