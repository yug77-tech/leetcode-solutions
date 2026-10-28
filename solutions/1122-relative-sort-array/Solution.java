import java.util.Arrays;

/**
 * LeetCode Problem 1122: Relative Sort Array
 * 
 * Counting sort with custom ordering
 * 
 * Problem:
 * Sort arr1 such that:
 * 1. Elements appear in the order defined by arr2
 * 2. Elements not in arr2 come last in ascending order
 * 
 * Key Insight:
 * Use COUNTING SORT with custom ordering!
 * - Frequency array for O(n) counting
 * - arr2 defines the priority order
 * - Remaining elements automatically ascending (scan freq array)
 * 
 * Three-Step Algorithm:
 * 
 * Step 1: Count Frequencies
 *   Build frequency array for all elements in arr1
 *   freq[i] = how many times i appears in arr1
 * 
 * Step 2: Place Elements Following arr2 Order
 *   For each element in arr2 (in order):
 *     Place all its occurrences in result
 *     Clear its frequency count
 * 
 * Step 3: Place Remaining Elements in Ascending Order
 *   Scan frequency array from 0 to 1000
 *   Place any remaining elements (not in arr2)
 *   Scanning left-to-right gives ascending order automatically!
 * 
 * Example: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
 * 
 * Step 1: Count frequencies
 * freq[1]=1, freq[2]=3, freq[3]=2, freq[4]=1,
 * freq[6]=1, freq[7]=1, freq[9]=1, freq[19]=1
 * 
 * Step 2: Process arr2 = [2,1,4,3,9,6]
 * 
 * num=2: freq[2]=3 → Place 2,2,2 → index 0,1,2
 * Result: [2,2,2,_,_,_,_,_,_,_,_]
 * 
 * num=1: freq[1]=1 → Place 1 → index 3
 * Result: [2,2,2,1,_,_,_,_,_,_,_]
 * 
 * num=4: freq[4]=1 → Place 4 → index 4
 * Result: [2,2,2,1,4,_,_,_,_,_,_]
 * 
 * num=3: freq[3]=2 → Place 3,3 → index 5,6
 * Result: [2,2,2,1,4,3,3,_,_,_,_]
 * 
 * num=9: freq[9]=1 → Place 9 → index 7
 * Result: [2,2,2,1,4,3,3,9,_,_,_]
 * 
 * num=6: freq[6]=1 → Place 6 → index 8
 * Result: [2,2,2,1,4,3,3,9,6,_,_]
 * 
 * Step 3: Place remaining (not in arr2)
 * Scan freq array: freq[7]=1, freq[19]=1
 * 
 * i=7: Place 7 → index 9
 * Result: [2,2,2,1,4,3,3,9,6,7,_]
 * 
 * i=19: Place 19 → index 10
 * Result: [2,2,2,1,4,3,3,9,6,7,19]
 * 
 * Final: [2,2,2,1,4,3,3,9,6,7,19] ✓
 * 
 * Why This Works:
 * 
 * 1. Counting Sort Foundation:
 *    - Elements in range [0, 1000]
 *    - Small range allows frequency array
 *    - O(n) counting instead of O(n log n) sorting
 * 
 * 2. Custom Ordering via arr2:
 *    - arr2 defines priority
 *    - Process arr2 sequentially to maintain order
 *    - Elements not in arr2 come last
 * 
 * 3. Automatic Ascending for Remaining:
 *    - Frequency array indexed 0 to 1000
 *    - Scanning left to right = ascending order
 *    - No explicit sorting needed!
 * 
 * Why freq[1001]?
 * - Constraint: 0 <= arr1[i] <= 1000
 * - Need indices from 0 to 1000 inclusive
 * - Array size = 1001 (indices 0-1000)
 * 
 * Why Use While Loop in Step 2?
 * - Elements can appear multiple times
 * - Need to place all occurrences
 * - Example: freq[2]=3 → Place 2,2,2
 * 
 * Why Decrement freq[num]?
 * - Mark as processed
 * - Prevents duplicate placement in Step 3
 * - When freq[i]=0, element already placed
 * 
 * Edge Cases:
 * 
 * 1. All elements in arr2:
 *    arr1=[2,1], arr2=[2,1]
 *    Step 3 does nothing, result=[2,1]
 * 
 * 2. No elements in arr2 (hypothetically):
 *    Step 2 does nothing
 *    Step 3 places all in ascending order
 * 
 * 3. Duplicates:
 *    arr1=[2,2,2], arr2=[2]
 *    freq[2]=3, place all three: [2,2,2]
 * 
 * 4. Single element:
 *    arr1=[5], arr2=[5]
 *    freq[5]=1, place once: [5]
 * 
 * Alternative: Custom Comparator
 * - Create HashMap: arr2 element → index
 * - Sort arr1 with custom comparator
 * - Time: O(n log n) - slower!
 * - Space: O(m) for HashMap
 * 
 * This counting sort is optimal: O(n) time!
 * 
 * Pattern Recognition:
 * When you see:
 * - "0 <= arr[i] <= 1000" → Frequency array!
 * - "Custom ordering" → Process in desired order
 * - Need O(n) sort → Counting sort
 * 
 * Time Complexity: O(n + m + k)
 *   - n = arr1.length (count frequencies)
 *   - m = arr2.length (place in order)
 *   - k = 1001 (scan frequency array)
 *   - Simplifies to O(n + m) since k is constant
 *   - Much faster than O(n log n) comparison sort!
 * 
 * Space Complexity: O(k) = O(1001) = O(1)
 *   - Fixed size frequency array
 *   - Constant space regardless of input
 *   - In-place modification of arr1
 */
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        // Step 1: Count frequencies of all elements in arr1
        // Use array as hash map: index = element value, value = frequency
        // Size 1001 because elements are in range [0, 1000]
        int[] freq = new int[1001];

        for (int num : arr1) {
            freq[num]++;
        }

        // Index to place elements in arr1
        int index = 0;

        // Step 2: Place elements following arr2 order
        // For each element in arr2 (in order):
        //   Place all its occurrences from arr1
        for (int num : arr2) {
            // Place this element freq[num] times
            while (freq[num] > 0) {
                arr1[index++] = num;
                freq[num]--;
            }
            // After this loop, freq[num] = 0
            // Element is now completely processed
        }

        // Step 3: Place remaining elements (not in arr2) in ascending order
        // Scan frequency array from 0 to 1000
        // Elements appear in ascending order automatically!
        for (int i = 0; i < freq.length; i++) {
            // If this element still has occurrences
            while (freq[i] > 0) {
                arr1[index++] = i;
                freq[i]--;
            }
        }

        // arr1 is now sorted according to requirements
        return arr1;
    }
}
