# 28. Find the Index of the First Occurrence in a String

**Difficulty:** Easy

## Problem Description

Given two strings `needle` and `haystack`, return the index of the first occurrence of `needle` in `haystack`, or `-1` if `needle` is not part of `haystack`.

## Examples

**Example 1:**
```
Input: haystack = "sadbutsad", needle = "sad"
Output: 0
Explanation: "sad" occurs at index 0 and 6.
The first occurrence is at index 0, so we return 0.
```

**Example 2:**
```
Input: haystack = "leetcode", needle = "leeto"
Output: -1
Explanation: "leeto" did not occur in "leetcode", so we return -1.
```

## Constraints

- `1 <= haystack.length, needle.length <= 10^4`
- `haystack` and `needle` consist of only lowercase English characters.

## Solution Approach

### Built-in Method Approach (Provided Solution)

The simplest and most efficient approach in Java uses the `String.startsWith()` method.

**Algorithm:**
1. Loop through valid starting positions in haystack (0 to n-m)
2. At each position i, check if haystack starts with needle at index i
3. If match found, return i
4. If no match found after all positions, return -1

**Why `i <= n - m`?**
- If haystack length is n and needle length is m
- Last valid starting position is (n - m)
- Example: haystack="hello" (n=5), needle="lo" (m=2)
  - Valid positions: 0,1,2,3 (stop at 3, since 3+2=5)
  - Position 4 would be invalid (would need positions 4,5 but we only have 0-4)

**Example walkthrough:**
```
haystack = "sadbutsad", needle = "sad"
n = 9, m = 3

i=0: haystack.startsWith("sad", 0) → "sad" == "sad" ✓ → return 0
```

**Example with no match:**
```
haystack = "leetcode", needle = "leeto"
n = 8, m = 5

i=0: haystack.startsWith("leeto", 0) → "leetc" != "leeto" ✗
i=1: haystack.startsWith("leeto", 1) → "eetco" != "leeto" ✗
i=2: haystack.startsWith("leeto", 2) → "etcod" != "leeto" ✗
i=3: haystack.startsWith("leeto", 3) → "tcode" != "leeto" ✗
Loop ends → return -1
```

## Complexity Analysis

### Built-in Method Solution (Provided):

- **Time Complexity:** O(n × m) worst case
  - n = haystack length, m = needle length
  - Each startsWith() call compares up to m characters
  - We do this for up to (n-m+1) positions
  
- **Space Complexity:** O(1) - No extra space used

### Alternative Approaches

**1. Manual Character Comparison:**
```java
public int strStr(String haystack, String needle) {
    int n = haystack.length();
    int m = needle.length();
    
    for (int i = 0; i <= n - m; i++) {
        int j = 0;
        while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
            j++;
        }
        if (j == m) return i;
    }
    return -1;
}
```
- Time: O(n × m), Space: O(1)
- Same complexity but no built-in method

**2. Using indexOf() (One-liner):**
```java
return haystack.indexOf(needle);
```
- Built-in Java method
- Same complexity but less explicit
- Good for production, less educational

**3. KMP Algorithm (Knuth-Morris-Pratt):**
- Time: O(n + m) - optimal!
- Space: O(m) - for LPS array
- More complex but faster for large inputs
- Avoids redundant comparisons

**4. Boyer-Moore Algorithm:**
- Time: O(n/m) best case, O(n × m) worst case
- Space: O(|alphabet|)
- Very efficient in practice
- Uses "bad character" and "good suffix" heuristics

**5. Rabin-Karp Algorithm:**
- Time: O(n + m) average, O(n × m) worst
- Space: O(1)
- Uses rolling hash
- Good for multiple pattern matching

## When to Use Each Approach

| Approach | When to Use |
|----------|-------------|
| **Built-in (startsWith/indexOf)** | Production code, interviews (unless asked otherwise) |
| **Manual comparison** | Learning, understanding the basics |
| **KMP** | When time complexity matters, competitive programming |
| **Boyer-Moore** | Large texts, practical string searching (grep, text editors) |
| **Rabin-Karp** | Multiple pattern matching, plagiarism detection |

## Why Your Solution is Good

✓ **Simple and readable** - Easy to understand  
✓ **Correct boundary handling** - `i <= n - m` prevents index out of bounds  
✓ **Efficient enough** - For typical inputs, O(n × m) is acceptable  
✓ **Uses built-in methods** - Leverages Java's optimized implementation  
✓ **Interview-friendly** - Clear logic, easy to explain  

## Tags

String, Two Pointers, String Matching
