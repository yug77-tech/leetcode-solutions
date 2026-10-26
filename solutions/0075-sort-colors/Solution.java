/**
 * LeetCode Problem 75: Sort Colors
 * 
 * Dutch National Flag Algorithm (Three-Way Partitioning)
 * 
 * Problem:
 * Sort array of 0s, 1s, and 2s in-place without using library sort.
 * 0 = red, 1 = white, 2 = blue
 * 
 * Key Insight:
 * Use THREE pointers to partition array into three sections in ONE pass!
 * - low: boundary of 0's region
 * - mid: current element being examined
 * - high: boundary of 2's region
 * 
 * Visual Partitioning:
 * [0,0,0 | 1,1,1 | ?,?,? | 2,2,2]
 *  ^       ^       ^       ^
 *  0      low     mid    high    n-1
 * 
 * Sections:
 * [0, low)       - All 0's (sorted)
 * [low, mid)     - All 1's (sorted)
 * [mid, high]    - Unknown (not yet processed)
 * (high, n)      - All 2's (sorted)
 * 
 * Algorithm:
 * While mid <= high:
 *   Case 1: nums[mid] == 0 (red)
 *     - Belongs in left section
 *     - Swap with nums[low]
 *     - Increment both low and mid
 *     - (Safe to increment mid: nums[low] was 1)
 * 
 *   Case 2: nums[mid] == 1 (white)
 *     - Already in correct section
 *     - Just increment mid
 * 
 *   Case 3: nums[mid] == 2 (blue)
 *     - Belongs in right section
 *     - Swap with nums[high]
 *     - Decrement high
 *     - DON'T increment mid (need to check swapped element)
 * 
 * Why This Works:
 * - Maintain invariant: everything before mid is sorted
 * - Unknown region [mid, high] shrinks with each iteration
 * - When mid > high, entire array is sorted
 * 
 * Example: [2, 0, 2, 1, 1, 0]
 * 
 * Initial: low=0, mid=0, high=5
 * [2, 0, 2, 1, 1, 0]
 *  ^              ^
 *  l,m            h
 * 
 * nums[0]=2 → Swap with high, high--
 * [0, 0, 2, 1, 1, 2]
 *  ^           ^
 *  l,m         h
 * 
 * nums[0]=0 → Swap with low, low++, mid++
 * [0, 0, 2, 1, 1, 2]
 *     ^        ^
 *     l,m      h
 * 
 * nums[1]=0 → Swap with low, low++, mid++
 * [0, 0, 2, 1, 1, 2]
 *        ^     ^
 *        l,m   h
 * 
 * nums[2]=2 → Swap with high, high--
 * [0, 0, 1, 1, 2, 2]
 *        ^  ^
 *        l,m h
 * 
 * nums[2]=1 → mid++
 * [0, 0, 1, 1, 2, 2]
 *        ^     ^
 *        l     h
 *           m
 * 
 * nums[3]=1 → mid++
 * [0, 0, 1, 1, 2, 2]
 *        ^        ^
 *        l        h
 *              m
 * 
 * mid > high → Done!
 * Result: [0, 0, 1, 1, 2, 2] ✓
 * 
 * Why Don't We Increment Mid After Swapping with High?
 * - When swapping with low: we KNOW nums[low] was 1 (processed region)
 *   So safe to increment mid
 * 
 * - When swapping with high: we DON'T know what nums[high] was (unprocessed)
 *   Could be 0, 1, or 2 - need to examine it
 * 
 * Critical Detail:
 * Condition is mid <= high, not mid < high
 * - We need to process element at position high too
 * - When mid == high, that's the last unknown element
 * 
 * Edge Cases:
 * 1. All same: [0,0,0] → Never swap, mid just advances
 * 2. All different: [1,2,0] → Multiple swaps needed
 * 3. Already sorted: [0,1,2] → Minimal swaps
 * 4. Two colors: [0,2] → Works fine, 1's section empty
 * 
 * Why Called "Dutch National Flag"?
 * Named by Edsger Dijkstra (Dutch computer scientist)
 * Dutch flag has three horizontal colors: red, white, blue
 * Problem partitions array into three sections like flag!
 * 
 * Alternative: Counting Sort (Two Pass)
 * - Count 0s, 1s, 2s
 * - Fill array: all 0s, then all 1s, then all 2s
 * - Time O(n), Space O(1), but TWO passes
 * - This solution is better: ONE pass!
 * 
 * Time Complexity: O(n)
 *   - Single pass through array
 *   - mid pointer moves from 0 to n
 *   - Each element examined at most once
 * 
 * Space Complexity: O(1)
 *   - Only three pointer variables (low, mid, high)
 *   - In-place sorting, no extra arrays
 *   - Constant extra space
 */
class Solution {
    public void sortColors(int[] nums) {
        // Three pointers for three-way partitioning
        int low = 0;         // Boundary for 0's
        int mid = 0;         // Current element
        int high = nums.length - 1;  // Boundary for 2's

        // Process until mid crosses high
        while (mid <= high) {
            if (nums[mid] == 0) {
                // Found a 0 (red) - belongs in left section
                // Swap with element at low
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;
                
                // Move both boundaries forward
                // Safe to increment mid: nums[low] was definitely 1
                low++;
                mid++;
                
            } else if (nums[mid] == 1) {
                // Found a 1 (white) - already in correct section
                // Just move forward
                mid++;
                
            } else {
                // Found a 2 (blue) - belongs in right section
                // Swap with element at high
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;
                
                // Move high boundary backward
                // DON'T increment mid - need to check swapped element
                // (we don't know what nums[high] was - could be 0, 1, or 2)
                high--;
            }
        }
        
        // At this point: mid > high
        // All elements sorted: [0,0,0 | 1,1,1 | 2,2,2]
    }
}
