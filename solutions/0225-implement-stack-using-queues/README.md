# 225. Implement Stack using Queues

**Difficulty:** Easy

## Problem Description

Implement a **last-in-first-out (LIFO)** stack using only **two queues**. The implemented stack should support all the functions of a normal stack (`push`, `top`, `pop`, and `empty`).

Implement the `MyStack` class:

- `void push(int x)` Pushes element x to the top of the stack.
- `int pop()` Removes the element on the top of the stack and returns it.
- `int top()` Returns the element on the top of the stack.
- `boolean empty()` Returns true if the stack is empty, false otherwise.

## Notes

- You must use **only** standard operations of a queue, which means that only push to back, peek/pop from front, size and is empty operations are valid.
- Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque (double-ended queue) as long as you use only a queue's standard operations.

## Examples

**Example 1:**
```
Input
["MyStack", "push", "push", "top", "pop", "empty"]
[[], [1], [2], [], [], []]

Output
[null, null, null, 2, 2, false]

Explanation
MyStack myStack = new MyStack();
myStack.push(1);
myStack.push(2);
myStack.top();   // return 2
myStack.pop();   // return 2
myStack.empty(); // return False
```

## Constraints

- `1 <= x <= 9`
- At most `100` calls will be made to `push`, `pop`, `top`, and `empty`.
- All the calls to `pop` and `top` are valid.

## Follow-up

Can you implement the stack using only one queue?

## Solution Approach

This is the **reverse problem** of problem 232 (Queue using Stacks)!

### Challenge

**Queue behavior:** FIFO (First In, First Out)
```
Enqueue 1,2,3 → [1,2,3]
Dequeue → 1,2,3 (wrong order for stack!)
```

**Stack behavior:** LIFO (Last In, First Out)
```
Push 1,2,3 → [1,2,3]
Pop → 3,2,1 (correct order!)
```

### Two-Queue Strategy (Push-Heavy)

**Key Insight:** Reverse elements during push, keep newest element at front!

1. **q1:** Main queue (always has elements in reverse order)
2. **q2:** Temporary queue (used during push)

**Push Operation:**
1. Add new element to empty q2
2. Transfer all elements from q1 to q2
3. Swap q1 and q2 references

This ensures the newest element is always at the front of q1!

**Pop/Top Operations:**
- Simply dequeue from q1 (O(1))

### Visual Example

```
Initial: q1=[], q2=[]

push(1):
  q2.offer(1) → q2=[1]
  transfer q1→q2 (nothing to transfer)
  swap: q1=[1], q2=[]

push(2):
  q2.offer(2) → q2=[2]
  transfer q1→q2: q2=[2,1]
  swap: q1=[2,1], q2=[]

push(3):
  q2.offer(3) → q2=[3]
  transfer q1→q2: q2=[3,2,1]
  swap: q1=[3,2,1], q2=[]

top(): return q1.peek() = 3 ✓
pop(): return q1.poll() = 3
       q1=[2,1]
pop(): return q1.poll() = 2
       q1=[1]
```

### Why This Works

- After each push, newest element is at the front of q1
- Pop and top are O(1) - just access front of queue
- All work is done during push operation

## Complexity Analysis

### Two-Queue Solution (Provided):

- **push():** O(n) - transfer all elements
- **pop():** O(1) - dequeue from front
- **top():** O(1) - peek at front
- **empty():** O(1) - check if q1 is empty
- **Space:** O(n) - both queues combined

### Alternative: One-Queue Solution

Can be done with a single queue by rotating elements:
```java
public void push(int x) {
    q.offer(x);
    int size = q.size();
    for (int i = 1; i < size; i++) {
        q.offer(q.poll());
    }
}
```
- Push: O(n), Pop/Top: O(1)
- Space: O(n) - single queue

### Comparison with Problem 232 (Queue using Stacks)

| Problem | Data Structure | Heavy Operation | Light Operations |
|---------|---------------|-----------------|------------------|
| **232: Queue using Stacks** | 2 Stacks | pop/peek: O(n) worst, O(1) amortized | push: O(1) |
| **225: Stack using Queues** | 2 Queues | push: O(n) | pop/top: O(1) |

Both achieve their goal but with different trade-offs!

## Tags

Stack, Queue, Design, Data Structure
