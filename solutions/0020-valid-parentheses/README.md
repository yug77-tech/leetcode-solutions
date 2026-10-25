# 20. Valid Parentheses

**Difficulty:** Easy

## Problem Description

Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:

1. Open brackets must be closed by the same type of brackets.
2. Open brackets must be closed in the correct order.
3. Every close bracket has a corresponding open bracket of the same type.

## Examples

**Example 1:**
```
Input: s = "()"
Output: true
```

**Example 2:**
```
Input: s = "()[]{}"
Output: true
```

**Example 3:**
```
Input: s = "(]"
Output: false
Explanation: Open bracket '(' but closed with wrong type ']'
```

**Example 4:**
```
Input: s = "([)]"
Output: false
Explanation: Brackets are not closed in the correct order
```

**Example 5:**
```
Input: s = "{[]}"
Output: true
Explanation: Inner brackets closed before outer ones
```

## Constraints

- `1 <= s.length <= 10^4`
- `s` consists of parentheses only `'()[]{}'`.

## Solution Approach

This is a **perfect stack problem!** The key insight: **Last opened bracket must be first closed.**

### Stack-Based Matching

**Why Stack?**
- Stack = LIFO (Last In, First Out)
- When we see closing bracket, it must match the **most recent** opening bracket
- This is exactly what stack gives us!

**Algorithm:**
1. Create empty stack
2. For each character:
   - **Opening bracket** `(`, `{`, `[` → Push to stack
   - **Closing bracket** `)`, `}`, `]` → Check if matches top of stack
     - If stack empty → No matching opening bracket → return false
     - If top doesn't match → Wrong type → return false
     - If matches → Pop from stack, continue
3. After processing all characters:
   - Stack empty → All brackets matched → return true
   - Stack not empty → Unclosed opening brackets → return false

### Visual Example

**Input: "({[]})"**

```
Process '(': Opening → Push
Stack: ['(']

Process '{': Opening → Push
Stack: ['(', '{']

Process '[': Opening → Push
Stack: ['(', '{', '[']

Process ']': Closing
  Top = '[' → Matches! Pop
Stack: ['(', '{']

Process '}': Closing
  Top = '{' → Matches! Pop
Stack: ['(']

Process ')': Closing
  Top = '(' → Matches! Pop
Stack: []

Stack is empty → Valid! ✓
```

**Input: "([)]" (Invalid)**

```
Process '(': Opening → Push
Stack: ['(']

Process '[': Opening → Push
Stack: ['(', '[']

Process ')': Closing
  Top = '[' → Doesn't match ')'! ✗
  Expected ']' but got ')'
  
Return false
```

**Input: "(((" (Invalid)**

```
Process '(': Push
Stack: ['(']

Process '(': Push
Stack: ['(', '(']

Process '(': Push
Stack: ['(', '(', '(']

End of string, stack not empty ✗
Unclosed opening brackets

Return false
```

**Input: ")" (Invalid)**

```
Process ')': Closing
  Stack is empty! ✗
  No opening bracket to match
  
Return false
```

### Why This Works

**Valid sequences have these properties:**
1. Every closing bracket has a matching opening bracket **before** it
2. The **most recent** unmatched opening bracket matches the current closing bracket
3. At the end, **all** opening brackets have been closed

**Stack perfectly tracks this:**
- Push = "I'm waiting to be closed"
- Pop = "I found my match!"
- Empty at end = "Everything matched!"

### Edge Cases Handled

✓ **All opening:** "(((" → Stack not empty at end  
✓ **All closing:** ")))" → Stack empty when trying to pop  
✓ **Wrong type:** "(]" → Type mismatch  
✓ **Wrong order:** "([)]" → Inner bracket closed after outer  
✓ **Single pair:** "()" → Valid  
✓ **Nested:** "{[()]}" → Valid  
✓ **Adjacent:** "()[]{}" → Valid  

## Complexity Analysis

### Stack-Based Approach (Provided):

- **Time Complexity:** O(n)
  - Single pass through string
  - Each character processed once
  - Push/Pop operations are O(1)

- **Space Complexity:** O(n)
  - Worst case: all opening brackets
  - Example: "(((((" → stack size = 5
  - Stack can grow up to n/2 in balanced input

### Why ArrayDeque Over Stack?

Your solution wisely uses `ArrayDeque` instead of `Stack` class!

**ArrayDeque advantages:**
✓ Faster - no synchronization overhead  
✓ Modern - recommended by Java docs  
✓ More flexible - can be used as stack or queue  
✓ Better performance - O(1) amortized operations  

**Stack class issues:**
✗ Synchronized - slower for single-threaded use  
✗ Legacy class - extends Vector  
✗ Deprecated for new code  

```java
// Your choice: ✓
ArrayDeque<Character> stack = new ArrayDeque<>();

// Not recommended: ✗
Stack<Character> stack = new Stack<>();
```

## Alternative Approaches

**1. HashMap for Bracket Matching:**
```java
public boolean isValid(String s) {
    Map<Character, Character> map = new HashMap<>();
    map.put(')', '(');
    map.put('}', '{');
    map.put(']', '[');
    
    ArrayDeque<Character> stack = new ArrayDeque<>();
    
    for (char c : s.toCharArray()) {
        if (map.containsKey(c)) {
            // Closing bracket
            if (stack.isEmpty() || stack.pop() != map.get(c)) {
                return false;
            }
        } else {
            // Opening bracket
            stack.push(c);
        }
    }
    
    return stack.isEmpty();
}
```
- More scalable for many bracket types
- Cleaner logic, but extra space for map

**2. Replace Method (Not Recommended):**
```java
public boolean isValid(String s) {
    while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
        s = s.replace("()", "").replace("{}", "").replace("[]", "");
    }
    return s.isEmpty();
}
```
- Time: O(n²) - inefficient
- Space: O(n) - string copies
- Simple but slow

Your stack approach is **optimal**!

## Common Mistakes to Avoid

❌ **Forgetting to check if stack is empty before popping**
```java
// Wrong:
char top = stack.pop(); // Could throw exception!

// Right:
if (stack.isEmpty()) return false;
char top = stack.pop();
```

❌ **Not checking if stack is empty at the end**
```java
// Wrong:
return true; // What about unclosed brackets?

// Right:
return stack.isEmpty();
```

❌ **Using == instead of matching logic**
```java
// Wrong:
if (c == top) // This doesn't make sense!

// Right:
if (c == ')' && top != '(') return false;
```

## Pattern Recognition

This problem teaches the **Stack Pattern**:

**Use stack when:**
- Need to match pairs
- Last item processed first (LIFO)
- Need to track "pending" or "waiting" items
- Problem involves nesting or proper ordering

**Similar problems:**
- Evaluate expressions
- Decode strings
- Remove duplicate letters
- Next greater element

## Tags

String, Stack
