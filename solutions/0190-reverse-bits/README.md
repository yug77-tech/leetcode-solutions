# 190. Reverse Bits

**Difficulty:** Easy

## Problem Description

Reverse bits of a given 32 bits unsigned integer.

## Note

- Note that in some languages, such as Java, there is no unsigned integer type. In this case, both input and output will be given as a signed integer type. They should not affect your implementation, as the integer's internal binary representation is the same, whether it is signed or unsigned.
- In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in **Example 2** above, the input represents the signed integer `-3` and the output represents the signed integer `-1073741824`.

## Examples

**Example 1:**
```
Input: n = 00000010100101000001111010011100
Output:    964176192 (00111001011110000010100101000000)
Explanation: The input binary string 00000010100101000001111010011100 
represents the unsigned integer 43261596, so return 964176192 which 
its binary representation is 00111001011110000010100101000000.
```

**Example 2:**
```
Input: n = 11111111111111111111111111111101
Output:   3221225471 (10111111111111111111111111111111)
Explanation: The input binary string 11111111111111111111111111111101 
represents the unsigned integer 4294967293, so return 3221225471 which 
its binary representation is 10111111111111111111111111111111.
```

## Constraints

- The input must be a **binary string** of length `32`

## Follow-up

If this function is called many times, how would you optimize it?

## Solution Approach

The key is to process bits one at a time from right to left, building the reversed result from left to right.

### Bit-by-Bit Reversal Algorithm

**Core operations:**
1. Extract rightmost bit of input: `n & 1`
2. Add it to result (shifted left): `result = (result << 1) | bit`
3. Move to next bit in input: `n >>>= 1`
4. Repeat for all 32 bits

### Visual Example

**Input: 00000000000000000000000000001011 (11 in decimal)**

```
Iteration | Input (n)                        | Bit | Result
----------|----------------------------------|-----|--------------------------------
0         | 00000000000000000000000000001011 | 1   | 00000000000000000000000000000001
1         | 00000000000000000000000000000101 | 1   | 00000000000000000000000000000011
2         | 00000000000000000000000000000010 | 0   | 00000000000000000000000000000110
3         | 00000000000000000000000000000001 | 1   | 00000000000000000000000000001101
4-31      | 00000000000000000000000000000000 | 0   | 11010000000000000000000000000000

Final result: 11010000000000000000000000000000
```

### Step-by-Step Breakdown

For each bit position (0 to 31):

1. **Extract bit:** `n & 1`
   - `&` with 1 extracts the rightmost bit
   - Example: `1011 & 1 = 1`, `1010 & 1 = 0`

2. **Shift result left:** `result << 1`
   - Makes room for the new bit on the right
   - Example: `0011 << 1 = 0110`

3. **Add bit to result:** `result | bit`
   - `|` (OR) sets the rightmost bit
   - Example: `0110 | 1 = 0111`

4. **Shift input right:** `n >>>= 1`
   - Move to next bit (unsigned right shift)
   - `>>>` fills with 0s (not sign bit)
   - Example: `1011 >>> 1 = 0101`

### Why Use `>>>` Instead of `>>`?

- `>>`: Arithmetic right shift (preserves sign bit)
- `>>>`: Logical right shift (always fills with 0)

For bit manipulation, we want logical shift!

```
Example: n = -8 (binary: 11111111111111111111111111111000)
  n >> 1  = 11111111111111111111111111111100 (sign extended)
  n >>> 1 = 01111111111111111111111111111100 (zero filled)
```

### Code Flow Example

```java
n = 5 (binary: 00000000000000000000000000000101)

i=0: bit = 5 & 1 = 1
     result = (0 << 1) | 1 = 1
     n = 5 >>> 1 = 2

i=1: bit = 2 & 1 = 0
     result = (1 << 1) | 0 = 2
     n = 2 >>> 1 = 1

i=2: bit = 1 & 1 = 1
     result = (2 << 1) | 1 = 5
     n = 1 >>> 1 = 0

i=3-31: bit = 0
        result keeps shifting left by 1
        
Final: result = 10100000000000000000000000000000
```

## Complexity Analysis

- **Time Complexity:** O(1) - Always exactly 32 iterations (constant)
- **Space Complexity:** O(1) - Only using two variables

Note: While we say O(1), technically it's O(32) = O(1) since 32 is constant.

## Follow-up: Optimization for Multiple Calls

If called many times, use **memoization** with a lookup table:

```java
// Cache for nibbles (4 bits = 16 possible values)
Map<Integer, Integer> cache = new HashMap<>();

public int reverseBits(int n) {
    int result = 0;
    // Process 4 bits at a time using cached reversals
    for (int i = 0; i < 8; i++) {
        result |= reverseNibble(n & 0xF) << (28 - i * 4);
        n >>>= 4;
    }
    return result;
}
```

This reduces operations from 32 to 8, with O(1) lookups.

## Tags

Bit Manipulation, Divide and Conquer
