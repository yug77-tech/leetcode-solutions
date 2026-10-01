/**
 * LeetCode Problem 1381: Design a Stack with Increment Operation
 * 
 * Solution using array-based stack with top pointer
 * 
 * Time Complexity:
 *   - push: O(1)
 *   - pop: O(1)
 *   - increment: O(min(k, n)) where n is current stack size
 * 
 * Space Complexity: O(maxSize)
 */
class CustomStack {
    int[] stack;
    int top;
    int maxSize;

    public CustomStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
        top = -1;
    }
    
    public void push(int x) {
        if (top < maxSize - 1) {
            top++;
            stack[top] = x;
        }
    }
    
    public int pop() {
        if (top == -1) {
            return -1;
        }
        int value = stack[top];
        top--;
        return value;
    }
    
    public void increment(int k, int val) {
        int limit = Math.min(k, top + 1);
        for (int i = 0; i < limit; i++) {
            stack[i] = stack[i] + val;
        }
    }
}

/**
 * Your CustomStack object will be instantiated and called as such:
 * CustomStack obj = new CustomStack(maxSize);
 * obj.push(x);
 * int param_2 = obj.pop();
 * obj.increment(k,val);
 */
