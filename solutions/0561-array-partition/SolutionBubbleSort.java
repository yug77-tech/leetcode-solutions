/**
 * LeetCode Problem 561: Array Partition
 * 
 * Bubble Sort implementation (educational version)
 * 
 * This solution demonstrates manual sorting using bubble sort.
 * 
 * Pros:
 * - O(1) space complexity (in-place sorting)
 * - Educational: shows understanding of sorting algorithms
 * - Includes optimization: early exit when array is sorted
 * 
 * Cons:
 * - O(n²) time complexity (very slow for large inputs)
 * - Not practical for production or competitive programming
 * 
 * Bubble Sort Algorithm:
 * - Compare adjacent elements
 * - Swap if left > right
 * - Repeat until no swaps needed
 * - "Bubbles" larger elements to the end
 * 
 * Optimization:
 * - Track if any swaps occurred
 * - If no swaps in a pass, array is sorted → exit early
 * - Reduces best case to O(n) for already sorted arrays
 * 
 * Example: nums = [1,4,3,2]
 *   Pass 1: [1,3,2,4] (4 bubbles to end)
 *   Pass 2: [1,2,3,4] (3 bubbles to correct position)
 *   Pass 3: No swaps → done!
 *   Result: [1,2,3,4]
 *   Sum even indices: 1 + 3 = 4
 * 
 * Time Complexity: 
 *   - Best: O(n) - already sorted
 *   - Average/Worst: O(n²) - needs many swaps
 * Space Complexity: O(1) - in-place sorting
 */
class Solution {
    public int arrayPairSum(int[] nums) {
        // Bubble Sort with early exit optimization
        for (int i = 0; i < nums.length - 1; i++) {
            boolean swapped = false;
            
            // Compare adjacent elements
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    // Swap elements
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // Early exit: if no swaps occurred, array is sorted
            if (!swapped) {
                break;
            }
        }
        
        // Sum elements at even indices
        int sum = 0;
        for (int i = 0; i < nums.length; i += 2) {
            sum += nums[i];
        }
        
        return sum;
    }
}
