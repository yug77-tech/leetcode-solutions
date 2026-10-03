import java.util.*;

/**
 * LeetCode Problem 1441: Build an Array with Stack Operations
 * 
 * Basic solution without pre-allocation optimization
 * 
 * Algorithm:
 * 1. Iterate through stream [1, n]
 * 2. Always Push current number
 * 3. If current number matches target[j], increment j
 * 4. Otherwise, Pop the number we just pushed
 * 5. Check if target is complete and break
 * 
 * Time Complexity: O(n) - iterate through stream until target is built
 * Space Complexity: O(target.length) - result list size
 */
class Solution {
    public List<String> buildArray(int[] target, int n) {
        
        List<String> result = new ArrayList<>();
        
        int j = 0;
        
        for (int i = 1; i <= n; i++) {
            
            result.add("Push");
            
            if (i == target[j]) {
                j++;
            } else {
                result.add("Pop");
            }
            
            if (j == target.length) {
                break;
            }
        }
        
        return result;
    }
}
