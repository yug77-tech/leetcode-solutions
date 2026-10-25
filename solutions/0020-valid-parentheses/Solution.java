import java.util.ArrayDeque;

/**
 * LeetCode Problem 20: Valid Parentheses
 * 
 * Stack-based bracket matching
 * 
 * Problem:
 * Determine if a string of brackets is valid. Valid means:
 * 1. Open brackets closed by same type
 * 2. Closed in correct order
 * 3. Every closing bracket has matching opening bracket
 * 
 * Key Insight:
 * This is a PERFECT stack problem!
 * - Last opened bracket must be first closed (LIFO)
 * - Stack naturally tracks "most recent unmatched" opening bracket
 * 
 * Algorithm:
 * 1. Create empty stack
 * 2. For each character:
 *    - Opening bracket → Push to stack
 *    - Closing bracket → Check if matches top
 *      - Stack empty? → No match, return false
 *      - Top doesn't match? → Wrong type, return false
 *      - Matches? → Pop and continue
 * 3. After all characters:
 *    - Stack empty? → All matched, return true
 *    - Stack not empty? → Unclosed brackets, return false
 * 
 * Why Stack Works:
 * - Opening bracket = "waiting to be closed"
 * - Closing bracket must match "most recent waiting"
 * - Stack top = most recent waiting
 * - Pop = "found my match!"
 * 
 * Example: "({[]})"
 * 
 * '(' → Push
 * Stack: ['(']
 * 
 * '{' → Push
 * Stack: ['(', '{']
 * 
 * '[' → Push
 * Stack: ['(', '{', '[']
 * 
 * ']' → Top='[', matches! Pop
 * Stack: ['(', '{']
 * 
 * '}' → Top='{', matches! Pop
 * Stack: ['(']
 * 
 * ')' → Top='(', matches! Pop
 * Stack: []
 * 
 * Stack empty → Valid! ✓
 * 
 * Example: "([)]" (Invalid)
 * 
 * '(' → Push
 * Stack: ['(']
 * 
 * '[' → Push
 * Stack: ['(', '[']
 * 
 * ')' → Top='[', doesn't match ')' ✗
 * Expected ']' but got ')'
 * Return false
 * 
 * Edge Cases:
 * 
 * 1. All opening: "((("
 *    - Stack: ['(', '(', '(']
 *    - End: stack not empty → false
 * 
 * 2. All closing: ")))"
 *    - First ')': stack empty → false
 * 
 * 3. Wrong type: "(]"
 *    - '(': Push
 *    - ']': Top='(', doesn't match → false
 * 
 * 4. Single valid: "()"
 *    - '(': Push
 *    - ')': Matches, pop
 *    - Stack empty → true
 * 
 * Why ArrayDeque?
 * - ArrayDeque is modern Java stack implementation
 * - Faster than Stack class (no synchronization)
 * - Stack class is legacy, extends Vector
 * - ArrayDeque recommended by Java docs
 * 
 * Time Complexity: O(n)
 *   - Single pass through string
 *   - Each character processed once
 *   - Push/pop are O(1)
 * 
 * Space Complexity: O(n)
 *   - Worst case: all opening brackets
 *   - Example: "(((((" → stack size 5
 *   - Best case: O(1) if alternating "()()()"
 *   - Average: O(n/2) for balanced input
 */
class Solution {
    public boolean isValid(String s) {
        // Use ArrayDeque as stack (better than Stack class)
        ArrayDeque<Character> stack = new ArrayDeque<>();

        // Process each character
        for (char c : s.toCharArray()) {
            // Opening brackets: push to stack (waiting to be closed)
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // Closing bracket found
                
                // Check if there's a matching opening bracket
                if (stack.isEmpty()) {
                    // No opening bracket to match → invalid
                    return false;
                }

                // Pop the most recent opening bracket
                char top = stack.pop();

                // Check if types match
                // ')' must match '('
                // '}' must match '{'
                // ']' must match '['
                if ((c == ')' && top != '(') ||
                    (c == '}' && top != '{') ||
                    (c == ']' && top != '[')) {
                    // Wrong type of bracket → invalid
                    return false;
                }
                
                // If we reach here, brackets matched correctly
                // Continue to next character
            }
        }

        // All characters processed
        // Stack should be empty (all opening brackets closed)
        // If stack not empty → unclosed opening brackets
        return stack.isEmpty();
    }
}
