# 14. Longest Common Prefix

**Difficulty:** Easy

## Problem Description

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string `""`.

## Examples

**Example 1:**
```
Input: strs = ["flower","flow","flight"]
Output: "fl"
```

**Example 2:**
```
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
```

## Constraints

- `1 <= strs.length <= 200`
- `0 <= strs[i].length <= 200`
- `strs[i]` consists of only lowercase English letters.

## Solution Approach

The key insight: **Start with the first string as the prefix, then shrink it until it matches all strings!**

### Horizontal Scanning with Prefix Reduction

**Algorithm:**
1. Take the first string as the initial prefix
2. For each subsequent string:
   - While the string doesn't start with prefix:
     - Remove last character from prefix
     - If prefix becomes empty, return ""
3. Return the remaining prefix

**Why this works:**
- Common prefix must be prefix of ALL strings
- If it's not prefix of current string, it's too long
- By shrinking, we find the longest that works for all

### Visual Example

**Input: ["flower", "flow", "flight"]**

```
Step 1: prefix = "flower" (first string)

Step 2: Check "flow"
  "flow".startsWith("flower")? No
  Shrink: prefix = "flowe"
  "flow".startsWith("flowe")? No
  Shrink: prefix = "flow"
  "flow".startsWith("flow")? Yes ✓
  
  prefix = "flow"

Step 3: Check "flight"
  "flight".startsWith("flow")? No
  Shrink: prefix = "flo"
  "flight".startsWith("flo")? No
  Shrink: prefix = "fl"
  "flight".startsWith("fl")? Yes ✓
  
  prefix = "fl"

Result: "fl"
```

### Step-by-Step Walkthrough

**Example: ["dog", "racecar", "car"]**

```
Initial: prefix = "dog"

Check "racecar":
  "racecar".startsWith("dog")? No
  Shrink: prefix = "do"
  "racecar".startsWith("do")? No
  Shrink: prefix = "d"
  "racecar".startsWith("d")? No
  Shrink: prefix = ""
  prefix is empty → return ""

Result: ""
```

**Example: ["abc", "abc", "abc"]**

```
Initial: prefix = "abc"

Check "abc":
  "abc".startsWith("abc")? Yes ✓
  
Check "abc":
  "abc".startsWith("abc")? Yes ✓

Result: "abc" (all strings identical)
```

### Why This Approach is Great

✓ **Simple logic** - Easy to understand and implement  
✓ **Efficient** - Stops shrinking as soon as match found  
✓ **Handles edge cases** - Empty string, no common prefix  
✓ **Short code** - ~10 lines of actual logic  
✓ **Early termination** - Returns "" immediately when prefix empty  

## Complexity Analysis

### Horizontal Scanning (Provided):

- **Time Complexity:** O(S) where S = sum of all characters
  - Worst case: compare all characters once
  - Example: ["abc", "abc", "abc"] → 9 comparisons
  - Best case: Early termination with no common prefix

- **Space Complexity:** O(1)
  - Only using prefix string (which shrinks)
  - No extra data structures

### Alternative Approaches

**1. Vertical Scanning:**
```java
public String longestCommonPrefix(String[] strs) {
    for (int i = 0; i < strs[0].length(); i++) {
        char c = strs[0].charAt(i);
        for (int j = 1; j < strs.length; j++) {
            if (i == strs[j].length() || strs[j].charAt(i) != c) {
                return strs[0].substring(0, i);
            }
        }
    }
    return strs[0];
}
```
- Time: O(S), Space: O(1)
- Compares column by column
- Stops at first mismatch

**2. Divide and Conquer:**
```java
public String longestCommonPrefix(String[] strs) {
    return divideConquer(strs, 0, strs.length - 1);
}

private String divideConquer(String[] strs, int l, int r) {
    if (l == r) return strs[l];
    int mid = (l + r) / 2;
    String left = divideConquer(strs, l, mid);
    String right = divideConquer(strs, mid + 1, r);
    return commonPrefix(left, right);
}
```
- Time: O(S), Space: O(m log n) - recursion stack
- Elegant but more complex

**3. Binary Search:**
- Find minimum length
- Binary search on prefix length
- Time: O(S log m), Space: O(1)
- Overkill for this problem

Your horizontal scanning is the **most intuitive**!

## Edge Cases Handled

✓ **Single string:** ["hello"] → "hello"  
✓ **No common prefix:** ["dog", "cat"] → ""  
✓ **All identical:** ["abc", "abc"] → "abc"  
✓ **Empty string in array:** ["", "b"] → ""  
✓ **One string is prefix:** ["fl", "flower"] → "fl"  

## Why Use startsWith()?

**Built-in method advantages:**
- ✓ Optimized implementation
- ✓ Readable code
- ✓ Handles edge cases automatically
- ✓ Clear intent

**Alternative (manual check):**
```java
// Instead of: strs[i].startsWith(prefix)
// Could do:
for (int k = 0; k < prefix.length(); k++) {
    if (k >= strs[i].length() || strs[i].charAt(k) != prefix.charAt(k)) {
        // doesn't match
    }
}
```
Your solution is much cleaner!

## Tags

String, Trie
