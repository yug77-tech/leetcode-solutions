import java.util.ArrayDeque;

/**
 * LeetCode Problem 232: Implement Queue using Stacks
 * 
 * Two-stack approach to implement FIFO queue
 * 
 * Key Insight:
 * Use two stacks to reverse element order:
 * - s1: input stack (receives new elements)
 * - s2: output stack (provides elements in queue order)
 * 
 * Strategy:
 * - Push: Always push to s1
 * - Pop/Peek: Pop/Peek from s2
 *   - If s2 is empty, transfer all elements from s1 to s2
 *   - This reverses order: LIFO → LIFO → FIFO!
 * 
 * Example:
 * push(1): s1=[1], s2=[]
 * push(2): s1=[2,1], s2=[]
 * push(3): s1=[3,2,1], s2=[]
 * 
 * peek(): move() transfers s1→s2
 *   s1=[], s2=[1,2,3]
 *   return 1
 * 
 * pop(): s2 not empty, no transfer needed
 *   return s2.pop() = 1
 *   s1=[], s2=[2,3]
 * 
 * Optimization: Lazy transfer
 * - Only transfer when s2 is empty
 * - Each element moves at most once from s1 to s2
 * - Achieves amortized O(1) for all operations
 * 
 * Why ArrayDeque?
 * - More efficient than Stack class (which is legacy)
 * - No synchronization overhead
 * - Provides all needed stack operations
 * 
 * Time Complexity:
 *   - push: O(1)
 *   - pop: Amortized O(1)
 *   - peek: Amortized O(1)
 *   - empty: O(1)
 * 
 * Space Complexity: O(n) - total elements in both stacks
 */
class MyQueue {
    ArrayDeque<Integer> s1 = new ArrayDeque<>();  // Input stack
    ArrayDeque<Integer> s2 = new ArrayDeque<>();  // Output stack
    
    public MyQueue() {
        // Constructor - stacks initialized above
    }
    
    /**
     * Push element to back of queue
     * Always push to s1 (input stack)
     */
    public void push(int x) {
        s1.push(x);
    }
    
    /**
     * Remove and return element from front of queue
     * Pop from s2 (output stack)
     * Transfer from s1 if s2 is empty
     */
    public int pop() {
        move();
        return s2.pop();
    }
    
    /**
     * Return element at front of queue without removing
     * Peek from s2 (output stack)
     * Transfer from s1 if s2 is empty
     */
    public int peek() {
        move();
        return s2.peek();
    }
    
    /**
     * Return true if queue is empty
     * Queue is empty when both stacks are empty
     */
    public boolean empty() {
        return s1.isEmpty() && s2.isEmpty();
    }
    
    /**
     * Transfer elements from s1 to s2 (if s2 is empty)
     * This reverses the order, converting LIFO to FIFO
     * 
     * Lazy transfer: only when needed
     * Each element is transferred at most once
     */
    void move() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
