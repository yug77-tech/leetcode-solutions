# 946. Validate Stack Sequences

**Difficulty:** Medium

## Problem Description

Given two integer arrays `pushed` and `popped` each with distinct values, return `true` if this could have been the result of a sequence of push and pop operations on an initially empty stack, or `false` otherwise.

## Examples

**Example 1:**
```
Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
Output: true
Explanation: We might do the following sequence:
push(1), push(2), push(3), push(4),
pop() -> 4,
push(5),
pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
```

**Example 2:**
```
Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
Output: false
Explanation: 1 cannot be popped before 2.
```

## Constraints

- `1 <= pushed.length <= 1000`
- `0 <= pushed[i] <= 1000`
- All the elements of `pushed` are unique.
- `popped.length == pushed.length`
- `popped` is a permutation of `pushed`.

## Solution Approach

The key insight is to **simulate the stack operations** and verify if we can match the `popped` sequence.

**Algorithm:**
1. Use the `pushed` array itself as a stack (space optimization!)
2. Use `top` pointer to track the stack top position
3. For each element in `pushed`:
   - Push it onto the stack (store at `pushed[top]` and increment `top`)
   - While stack is not empty AND top element matches current `popped` element:
     - Pop from stack (decrement `top`)
     - Move to next element in `popped` (increment `j`)
4. If stack is empty at the end (`top == 0`), the sequence is valid

**Key Optimization:**
Instead of using an additional stack data structure, we reuse the `pushed` array as a stack by treating elements `[0, top-1]` as the current stack contents. This achieves O(1) space complexity (excluding input).

## Complexity Analysis

- **Time Complexity:** O(n) - Each element is pushed and popped at most once
- **Space Complexity:** O(1) - Reuses the input array as stack, no extra space needed

## Tags

Array, Stack, Simulation, Two Pointers
