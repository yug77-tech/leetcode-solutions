import java.util.ArrayDeque;
import java.util.Queue;

/**
 * LeetCode Problem 225: Implement Stack using Queues
 * 
 * One-queue solution (alternative approach)
 * 
 * Key Insight:
 * Use a single queue and rotate elements during push!
 * 
 * Strategy:
 * 1. Add new element to back of queue
 * 2. Rotate all previous elements to back
 * 3. Result: new element is now at front
 * 
 * How rotation works:
 * - After adding new element, size = n
 * - Remove and re-add first (n-1) elements
 * - This moves the new element to the front!
 * 
 * Example:
 * Initial: q=[]
 * 
 * push(1):
 *   q.offer(1) → q=[1]
 *   size=1, no rotation needed
 *   q=[1]
 * 
 * push(2):
 *   q.offer(2) → q=[1,2]
 *   size=2, rotate 1 time:
 *     poll 1, offer 1 → q=[2,1]
 *   q=[2,1]
 * 
 * push(3):
 *   q.offer(3) → q=[2,1,3]
 *   size=3, rotate 2 times:
 *     poll 2, offer 2 → q=[1,3,2]
 *     poll 1, offer 1 → q=[3,2,1]
 *   q=[3,2,1]
 * 
 * top(): return q.peek() = 3 ✓
 * pop(): return q.poll() = 3
 *        q=[2,1]
 * 
 * Advantages:
 * - Only one queue needed (simpler)
 * - No queue reference swapping
 * 
 * Same complexity as two-queue approach:
 * - push: O(n)
 * - pop/top: O(1)
 * - Space: O(n)
 * 
 * Time Complexity:
 *   - push: O(n) - rotate (n-1) elements
 *   - pop: O(1) - dequeue from front
 *   - top: O(1) - peek at front
 *   - empty: O(1) - check if empty
 * 
 * Space Complexity: O(n) - single queue
 */
class MyStack {
    
    Queue<Integer> q = new ArrayDeque<>();
    
    public MyStack() {
        // Constructor - queue initialized above
    }
    
    /**
     * Push element to top of stack
     * Add to back, then rotate all other elements to back
     * Time: O(n)
     */
    public void push(int x) {
        q.offer(x);
        
        int size = q.size();
        // Rotate (size-1) times to move new element to front
        for (int i = 1; i < size; i++) {
            q.offer(q.poll());
        }
    }
    
    /**
     * Remove and return element from top of stack
     * Time: O(1)
     */
    public int pop() {
        return q.remove();
    }
    
    /**
     * Return element at top of stack without removing
     * Time: O(1)
     */
    public int top() {
        return q.element();
    }
    
    /**
     * Return true if stack is empty
     * Time: O(1)
     */
    public boolean empty() {
        return q.isEmpty();
    }
}
