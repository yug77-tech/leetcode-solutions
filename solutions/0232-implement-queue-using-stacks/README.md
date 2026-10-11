# 232. Implement Queue using Stacks

**Difficulty:** Easy

## Problem Description

Implement a **first in first out (FIFO)** queue using only **two stacks**. The implemented queue should support all the functions of a normal queue (`push`, `peek`, `pop`, and `empty`).

Implement the `MyQueue` class:

- `void push(int x)` Pushes element x to the back of the queue.
- `int pop()` Removes the element from the front of the queue and returns it.
- `int peek()` Returns the element at the front of the queue.
- `boolean empty()` Returns true if the queue is empty, false otherwise.

## Notes

- You must use **only** standard operations of a stack, which means only push to top, peek/pop from top, size, and is empty operations are valid.
- Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque (double-ended queue) as long as you use only a stack's standard operations.

## Examples

**Example 1:**
```
Input
["MyQueue", "push", "push", "peek", "pop", "empty"]
[[], [1], [2], [], [], []]

Output
[null, null, null, 1, 1, false]

Explanation
MyQueue myQueue = new MyQueue();
myQueue.push(1); // queue is: [1]
myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
myQueue.peek();  // return 1
myQueue.pop();   // return 1, queue is [2]
myQueue.empty(); // return false
```

## Constraints

- `1 <= x <= 9`
- At most `100` calls will be made to `push`, `pop`, `peek`, and `empty`.
- All the calls to `pop` and `peek` are valid.

## Follow-up

Can you implement the queue such that each operation is **amortized O(1)** time complexity?

## Solution Approach

The key insight is to use **two stacks** to reverse the order of elements:

### Visual Understanding

**Stack behavior:** LIFO (Last In, First Out)
```
Push 1,2,3 → [3,2,1] (top to bottom)
Pop → 3,2,1 (wrong order for queue!)
```

**Queue behavior:** FIFO (First In, First Out)
```
Push 1,2,3 → [1,2,3]
Pop → 1,2,3 (correct order!)
```

### Two-Stack Strategy

1. **s1 (Input Stack):** Receives all new elements
2. **s2 (Output Stack):** Provides elements in queue order

**Key operation: `move()`**
- When s2 is empty and we need to pop/peek
- Transfer all elements from s1 to s2
- This reverses the order, giving us FIFO behavior!

### Example Walkthrough

```
push(1): s1=[1], s2=[]
push(2): s1=[2,1], s2=[]
push(3): s1=[3,2,1], s2=[]

peek(): 
  move() called → transfer s1 to s2
  s1=[], s2=[1,2,3]
  return s2.peek() = 1

pop():
  s2 not empty, no move needed
  s1=[], s2=[1,2,3]
  return s2.pop() = 1
  s1=[], s2=[2,3]

push(4):
  s1=[4], s2=[2,3]

pop():
  s2 not empty
  return s2.pop() = 2
  s1=[4], s2=[3]
```

### Why This Works

- **Push:** Always to s1 (O(1))
- **Pop/Peek:** From s2 if available, otherwise move from s1
- **Lazy transfer:** Only move when s2 is empty
- **Each element is moved at most once** from s1 to s2

## Complexity Analysis

### Time Complexity

- **push():** O(1) - direct push to s1
- **pop():** Amortized O(1) - each element moves at most once
- **peek():** Amortized O(1) - same as pop
- **empty():** O(1) - check both stacks

**Why Amortized O(1)?**
- In worst case, pop() might transfer n elements (O(n))
- But each element is transferred exactly once
- Over n operations, total transfers = O(n)
- Average per operation = O(n)/n = O(1)

### Space Complexity

- **O(n)** - where n is the number of elements in the queue
- Both stacks together hold all elements

## Implementation Details

Using `ArrayDeque` in Java:
- More efficient than `Stack` class (legacy)
- Provides standard stack operations
- No synchronization overhead

## Tags

Stack, Queue, Design, Data Structure
