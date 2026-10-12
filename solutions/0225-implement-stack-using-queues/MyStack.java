import java.util.ArrayDeque;
import java.util.Queue;

/**
 * LeetCode Problem 225: Implement Stack using Queues
 * 
 * Two-queue approach to implement LIFO stack
 * 
 * Key Insight:
 * Reverse element order during push to keep newest at front!
 * 
 * Strategy:
 * - q1: Main queue (holds elements in reverse order)
 * - q2: Temporary queue (used during push)
 * 
 * Push operation (O(n)):
 * 1. Add new element to q2
 * 2. Transfer all elements from q1 to q2
 * 3. Swap q1 and q2 references
 * Result: newest element is at front of q1
 * 
 * Pop/Top operations (O(1)):
 * - Simply dequeue/peek from q1
 * 
 * Example:
 * push(1): q1=[1], q2=[]
 * push(2):
 *   q2.offer(2) → q2=[2]
 *   transfer q1→q2 → q2=[2,1]
 *   swap → q1=[2,1], q2=[]
 * push(3):
 *   q2.offer(3) → q2=[3]
 *   transfer q1→q2 → q2=[3,2,1]
 *   swap → q1=[3,2,1], q2=[]
 * 
 * top(): return 3 (front of q1)
 * pop(): return 3, q1=[2,1]
 * 
 * Trade-off:
 * - Push is expensive: O(n)
 * - Pop/Top are cheap: O(1)
 * 
 * Opposite of Problem 232 (Queue using Stacks):
 * - 232: cheap push, expensive pop
 * - 225: expensive push, cheap pop
 * 
 * Why ArrayDeque?
 * - Efficient queue implementation
 * - No synchronization overhead (unlike LinkedList in some cases)
 * - Provides all standard queue operations
 * 
 * Time Complexity:
 *   - push: O(n) - transfer all elements
 *   - pop: O(1) - dequeue from front
 *   - top: O(1) - peek at front
 *   - empty: O(1) - check if empty
 * 
 * Space Complexity: O(n) - elements in both queues
 */
class MyStack {
    
    Queue<Integer> q1 = new ArrayDeque<>();  // Main queue
    Queue<Integer> q2 = new ArrayDeque<>();  // Temporary queue
    
    public MyStack() {
        // Constructor - queues initialized above
    }
    
    /**
     * Push element to top of stack
     * Strategy: Add to q2, transfer q1→q2, then swap
     * Time: O(n)
     */
    public void push(int x) {
        // Add new element to q2
        q2.offer(x);
        
        // Transfer all elements from q1 to q2
        // This puts new element at front
        while (!q1.isEmpty()) {
            q2.offer(q1.poll());
        }
        
        // Swap references: q1 becomes the new queue
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }
    
    /**
     * Remove and return element from top of stack
     * Simply remove from front of q1 (newest element)
     * Time: O(1)
     */
    public int pop() {
        return q1.remove();
    }
    
    /**
     * Return element at top of stack without removing
     * Simply peek at front of q1 (newest element)
     * Time: O(1)
     */
    public int top() {
        return q1.element();
    }
    
    /**
     * Return true if stack is empty
     * Only need to check q1 (q2 is always empty after push)
     * Time: O(1)
     */
    public boolean empty() {
        return q1.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
