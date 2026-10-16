# 58. Length of Last Word

**Difficulty:** Easy

## Problem Description

Given a string `s` consisting of words and spaces, return the length of the **last** word in the string.

A **word** is a maximal substring consisting of non-space characters only.

## Examples

**Example 1:**
```
Input: s = "Hello World"
Output: 5
Explanation: The last word is "World" with length 5.
```

**Example 2:**
```
Input: s = "   fly me   to   the moon  "
Output: 4
Explanation: The last word is "moon" with length 4.
```

**Example 3:**
```
Input: s = "luffy is still joyboy"
Output: 6
Explanation: The last word is "joyboy" with length 6.
```

## Constraints

- `1 <= s.length <= 10^4`
- `s` consists of only English letters and spaces `' '`.
- There will be at least one word in `s`.

## Solution Approach

The key insight is to **scan from right to left** to find the last word efficiently!

### Two-Phase Algorithm (Optimal)

Your solution uses two separate while loops:

**Phase 1: Skip trailing spaces**
- Start from the end of the string
- Move left while encountering spaces
- Stop when we find the first non-space character (end of last word)

**Phase 2: Count word characters**
- Continue from where Phase 1 stopped
- Count characters while they are non-space
- Stop when we hit a space or reach the beginning

### Visual Example

**Input: `"   fly me   to   the moon  "`**

```
String:  "   fly me   to   the moon  "
Index:    0123456789...           23 24 25

Phase 1: Skip trailing spaces
  i=25: ' ' → skip
  i=24: ' ' → skip
  i=23: 'n' → stop! (found end of last word)

Phase 2: Count characters
  i=23: 'n' → count=1, i=22
  i=22: 'o' → count=2, i=21
  i=21: 'o' → count=3, i=20
  i=20: 'm' → count=4, i=19
  i=19: ' ' → stop! (word ended)

Result: count = 4
```

### Step-by-Step Breakdown

**Example: `"Hello World"`**

```
Index: 01234567890

Initial: i = 10 (last index)
Phase 1:
  i=10: s.charAt(10)='d' (not space) → exit Phase 1

Phase 2:
  i=10: s.charAt(10)='d' (not space) → count=1, i=9
  i=9:  s.charAt(9)='l'  (not space) → count=2, i=8
  i=8:  s.charAt(8)='r'  (not space) → count=3, i=7
  i=7:  s.charAt(7)='o'  (not space) → count=4, i=6
  i=6:  s.charAt(6)='W'  (not space) → count=5, i=5
  i=5:  s.charAt(5)=' '  (space!)    → exit Phase 2

Result: 5
```

### Why This Approach is Optimal

✓ **O(n) time** - Single pass from right to left  
✓ **O(1) space** - Only two variables (i, count)  
✓ **Handles trailing spaces** - Phase 1 skips them  
✓ **Handles leading spaces** - Doesn't matter since we go right-to-left  
✓ **Early termination** - Stops as soon as last word is counted  

## Complexity Analysis

### Optimal Solution (Provided):

- **Time Complexity:** O(n) where n = string length
  - Worst case: scan entire string (all spaces + one word)
  - Best case: O(1) if last word ends at string end
  - Average: O(length of last word + trailing spaces)

- **Space Complexity:** O(1)
  - Only using two variables: `i` and `count`

### Alternative Approaches

**1. Split and Get Last Element:**
```java
public int lengthOfLastWord(String s) {
    String[] words = s.trim().split(" +");
    return words[words.length - 1].length();
}
```
- Time: O(n), Space: O(n) - creates array
- Simple but uses extra space
- Good for readability, not optimal for space

**2. Trim and Scan:**
```java
public int lengthOfLastWord(String s) {
    s = s.trim();
    int count = 0;
    for (int i = s.length() - 1; i >= 0 && s.charAt(i) != ' '; i--) {
        count++;
    }
    return count;
}
```
- Time: O(n), Space: O(n) - trim creates new string
- Similar to optimal but creates extra string

**3. Using Java Streams:**
```java
public int lengthOfLastWord(String s) {
    return s.trim().length() - s.trim().lastIndexOf(' ') - 1;
}
```
- Clever one-liner
- Time: O(n), Space: O(n) - multiple trim() calls

Your solution is the **most space-efficient**!

## Edge Cases Handled

✓ Trailing spaces: `"hello world  "` → 5  
✓ Leading spaces: `"  hello"` → 5  
✓ Multiple spaces: `"a   b"` → 1  
✓ Single word: `"hello"` → 5  
✓ Single character: `"a"` → 1  

## Tags

String, Two Pointers
