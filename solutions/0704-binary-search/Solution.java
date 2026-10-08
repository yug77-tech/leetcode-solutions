/**
 * LeetCode Problem 704: Binary Search
 * 
 * Classic Binary Search implementation
 * 
 * This is the fundamental binary search algorithm - one of the most important
 * algorithms in computer science. It efficiently searches a sorted array by
 * repeatedly dividing the search space in half.
 * 
 * Algorithm:
 * 1. Start with left=0, right=length-1
 * 2. Calculate mid = (left + right) / 2
 * 3. If nums[mid] equals target: found! return mid
 * 4. If nums[mid] < target: search right half (left = mid + 1)
 * 5. If nums[mid] > target: search left half (right = mid - 1)
 * 6. Repeat until found or left > right
 * 
 * Why O(log n)?
 * Each iteration eliminates half the remaining elements.
 * After k iterations: search space = n / 2^k
 * When search space becomes 1: k = log₂(n)
 * 
 * Example: nums = [-1,0,3,5,9,12], target = 9
 *   Step 1: left=0, right=5, mid=2, nums[2]=3 < 9, left=3
 *   Step 2: left=3, right=5, mid=4, nums[4]=9 == 9, return 4
 * 
 * Time Complexity: O(log n) - logarithmic search
 * Space Complexity: O(1) - constant extra space
 */
class Solution {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        
        while (l <= r) {
            int mid = (l + r) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        
        return -1;
    }
}
