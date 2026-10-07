/**
 * LeetCode Problem 35: Search Insert Position
 * 
 * Binary Search solution to achieve O(log n) time complexity
 * 
 * Algorithm:
 * 1. Use binary search to find target in sorted array
 * 2. If found, return index
 * 3. If not found, return insertion position
 * 
 * Key Insight:
 * When binary search ends without finding target:
 * - left and right pointers have crossed (left > right)
 * - left points to the correct insertion position
 * - This is the first element greater than target
 * 
 * Example: [1,3,5,6], target=2
 *   Initial: left=0, right=3
 *   mid=1: nums[1]=3 > 2, right=0
 *   mid=0: nums[0]=1 < 2, left=1
 *   Exit: left=1 (insert between 1 and 3)
 * 
 * Example: [1,3,5,6], target=5
 *   Initial: left=0, right=3
 *   mid=1: nums[1]=3 < 5, left=2
 *   mid=2: nums[2]=5 == 5, return 2 (found!)
 * 
 * Time Complexity: O(log n) - binary search
 * Space Complexity: O(1) - constant extra space
 */
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            
            if (nums[mid] == target)
                return mid;
            
            if (nums[mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        
        return left;
    }
}
