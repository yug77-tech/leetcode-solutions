# 771. Jewels and Stones

**Difficulty:** Easy

## Problem Description

You're given strings `jewels` representing the types of stones that are jewels, and `stones` representing the stones you have. Each character in `stones` is a type of stone you have. You want to know how many of the stones you have are also jewels.

Letters are **case sensitive**, so `"a"` is considered a different type of stone from `"A"`.

## Examples

**Example 1:**
```
Input: jewels = "aA", stones = "aAAbbbb"
Output: 3
```

**Example 2:**
```
Input: jewels = "z", stones = "ZZ"
Output: 0
```

## Constraints

- `1 <= jewels.length, stones.length <= 50`
- `jewels` and `stones` consist of only English letters.
- All the characters of `jewels` are **unique**.

## Solution Approach

The problem is essentially: **count how many characters in `stones` exist in `jewels`**.

### Array-Based Hash Set (Optimal - Provided Solution)

Since we're dealing with ASCII characters (letters only), we can use a **boolean array as a hash set**!

**Key Insight:**
- English letters (a-z, A-Z) have ASCII values 65-122
- Use array of size 128 to cover all ASCII characters
- Array lookup is O(1) - faster than HashSet!

**Algorithm:**
1. Create boolean array of size 128
2. Mark all jewel characters as `true` in the array
3. Iterate through stones, count characters marked as jewels

**Why size 128?**
- Covers all standard ASCII characters (0-127)
- 'A' = 65, 'Z' = 90, 'a' = 97, 'z' = 122
- Size 128 ensures all letters are covered

### Visual Example

**Input: jewels = "aA", stones = "aAAbbbb"**

```
Step 1: Build jewel set
  set['a'] = true  (ASCII 97)
  set['A'] = true  (ASCII 65)

Step 2: Count jewels in stones
  's' → set['a'] = true  → count = 1
  't' → set['A'] = true  → count = 2
  'o' → set['A'] = true  → count = 3
  'n' → set['b'] = false → count = 3 (no change)
  'e' → set['b'] = false → count = 3 (no change)
  's' → set['b'] = false → count = 3 (no change)
  
stones = "aAAbbbb"
         ^^^      ← these 3 are jewels

Result: 3
```

### Why This Solution is Optimal

✓ **Time: O(n + m)** - Linear in jewels length + stones length  
✓ **Space: O(1)** - Array size 128 is constant (not dependent on input)  
✓ **Faster than HashSet** - Array access is faster than hash computation  
✓ **No hash collisions** - Direct indexing eliminates collision overhead  
✓ **Cache-friendly** - Contiguous memory access  

## Complexity Analysis

### Array-Based Solution (Provided):

- **Time Complexity:** O(n + m)
  - n = jewels length (to build the set)
  - m = stones length (to count jewels)
  - Both are single-pass operations

- **Space Complexity:** O(1)
  - Array size 128 is constant
  - Not dependent on input size
  - Technically O(128) = O(1)

### Alternative Approaches

**1. HashSet (More Generic):**
```java
public int numJewelsInStones(String jewels, String stones) {
    Set<Character> set = new HashSet<>();
    for (char c : jewels.toCharArray()) {
        set.add(c);
    }
    
    int count = 0;
    for (char c : stones.toCharArray()) {
        if (set.contains(c)) {
            count++;
        }
    }
    return count;
}
```
- Time: O(n + m), Space: O(n)
- More generic but slower hash operations
- Good when character set is unknown

**2. Nested Loop (Brute Force):**
```java
public int numJewelsInStones(String jewels, String stones) {
    int count = 0;
    for (char stone : stones.toCharArray()) {
        for (char jewel : jewels.toCharArray()) {
            if (stone == jewel) {
                count++;
                break;
            }
        }
    }
    return count;
}
```
- Time: O(n × m), Space: O(1)
- Too slow for large inputs

**3. Java Streams (Functional):**
```java
public int numJewelsInStones(String jewels, String stones) {
    Set<Character> set = jewels.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.toSet());
    return (int) stones.chars()
        .filter(c -> set.contains((char) c))
        .count();
}
```
- Time: O(n + m), Space: O(n)
- Concise but less readable

**4. Smaller Array (Optimized for letters only):**
```java
public int numJewelsInStones(String jewels, String stones) {
    boolean[] set = new boolean[58]; // 'z' - 'A' + 1 = 58
    for (char c : jewels.toCharArray()) {
        set[c - 'A'] = true;
    }
    int count = 0;
    for (char c : stones.toCharArray()) {
        if (set[c - 'A']) count++;
    }
    return count;
}
```
- Saves memory (58 vs 128) but same O(1) space
- Requires offset calculation

Your solution (128-size array) is the **best balance** of simplicity and performance!

## Why Array > HashSet Here

| Aspect | Array[128] | HashSet |
|--------|-----------|---------|
| Access Time | O(1) direct | O(1) with hash |
| Memory | 128 bytes | Overhead + n entries |
| Speed | Faster | Slower (hash computation) |
| Cache | Better locality | Scattered memory |

For small, known character sets, **array beats HashSet**!

## Tags

Hash Table, Hash Set, String
