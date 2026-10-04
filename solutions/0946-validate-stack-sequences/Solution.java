/**
 * LeetCode Problem 946: Validate Stack Sequences
 * 
 * Space-optimized solution that reuses the input array as a stack
 * 
 * Algorithm:
 * 1. Simulate stack operations using the pushed array itself
 * 2. Use 'top' pointer to track stack position
 * 3. For each element in pushed:
 *    - Push it onto stack (pushed[top++] = element)
 *    - While top matches current popped element:
 *      - Pop from stack (top--)
 *      - Move to next popped element (j++)
 * 4. Valid if stack is empty at the end (top == 0)
 * 
 * Key Insight:
 * Instead of using a separate Stack data structure, we reuse the pushed array
 * as a stack. Elements [0, top-1] represent the current stack contents.
 * This optimization reduces space complexity to O(1).
 * 
 * Time Complexity: O(n) - each element is pushed and popped at most once
 * Space Complexity: O(1) - reuses input array, no extra space
 */
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        
        int top = 0;
        int j = 0;
        
        for (int x : pushed) {
            pushed[top++] = x;
            while (top > 0 && pushed[top - 1] == popped[j]) {
                top--;
                j++;
            }
        }
        
        return top == 0;
    }
}
